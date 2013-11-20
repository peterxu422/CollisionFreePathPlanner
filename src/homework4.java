
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Draw.SceneDrawer;
import Objects.Obstacle;
import Objects.Line;
import Objects.Vertex;
import Solve.DijkstraSolver;
import Solve.VisibilityGraph;

/**
 * 
 * @author Frederik Clinckemaillie
 *
 */
public class homework4 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		//Testing code for Sending commands to create
		ArrayList<Line> p = new ArrayList<Line>(4);
		p.add(new Line(new Vertex(0,0), new Vertex(-2,0)));
		p.add(new Line(new Vertex(-2,0), new Vertex(-2,2)));
		p.add(new Line(new Vertex(-2,2), new Vertex(2,2)));
		p.add(new Line(new Vertex(2,2), new Vertex(2,-3)));
		writeCommands(p, args[2]);
		System.exit(1);
		
		if (args.length != 2)
		{
			System.out.println("Usage: java homework4 obstacleFileName startGoalFileName commandsFilePath");
			return;
		}
		
		ArrayList <Obstacle> obstacles = new ArrayList <Obstacle>(); 
		double lowX = 100, lowY= 100, highX = -100, highY = -100;
		
		//Reading and creation of obstacles
		BufferedReader obsReader = new BufferedReader(new FileReader(new File(args[0])));
		
		int numObstacles = Integer.parseInt(obsReader.readLine());
		for(; numObstacles > 0; numObstacles -- )
		{
			int numVertices = Integer.parseInt(obsReader.readLine());
			
			ArrayList <Vertex> obsVertices = new ArrayList <Vertex>();
			for(; numVertices > 0; numVertices -- )
			{
			 String str = obsReader.readLine();
			 int spaceIndex = str.indexOf(" ");
			 
			 double x =  Double.parseDouble(str.substring(0, spaceIndex));
			 double y =  Double.parseDouble(str.substring(spaceIndex + 1));
			 if (x > highX) 	highX = x;
			 if (x < lowX) 	 	lowX = x;
			 if (y > highY) 	highY = y;
			 if (y < lowY) 	 	lowY = y;
			 
			 obsVertices.add(new Vertex(x, y));
			}
			obstacles.add(new Obstacle(obsVertices));
		}
		
		obsReader.close();

		//Reading of start and endpoint positions
		BufferedReader goalReader = new BufferedReader(new FileReader(new File(args[1])));
		String str = goalReader.readLine();
		int spaceIndex = str.indexOf(" ");
		 
		double x =  Double.parseDouble(str.substring(0, spaceIndex));
		double y =  Double.parseDouble(str.substring(spaceIndex + 1));
		Vertex startPoint = new Vertex(x, y);
		
		str = goalReader.readLine();
		spaceIndex = str.indexOf(" ");
		x =  Double.parseDouble(str.substring(0, spaceIndex));
		y =  Double.parseDouble(str.substring(spaceIndex + 1));
		Vertex endPoint = new Vertex(x, y);
		
		//Calculate the convex Hull points
		int size = obstacles.size();
		ArrayList<Obstacle> expandedObs = new ArrayList<Obstacle>();
		expandedObs.add(obstacles.get(0));
		for(int i=1; i < size; i++)
		{
			obstacles.get(i).ExpandObstacle();
			obstacles.get(i).computeConvexHull();
			expandedObs.add(new Obstacle(obstacles.get(i).getConvexHullPoints()));
		}
		
		//Draw Obstacles
		SceneDrawer aDrawer = new SceneDrawer();
		aDrawer.setDimensions(lowX, lowY, highX, highY);
		aDrawer.drawObstacles(obstacles);
		aDrawer.drawConvexHull(obstacles);
		aDrawer.drawPoint(startPoint, "start");
		aDrawer.drawPoint(endPoint, "end");
		VisibilityGraph visGraph = new VisibilityGraph();
		ArrayList<Line> visLines = visGraph.calculateVisibility(expandedObs,startPoint,endPoint);
		aDrawer.drawLines(visLines, "blue");
		DijkstraSolver aDijkSolver = new DijkstraSolver(visLines);
		ArrayList <Line> path = aDijkSolver.findPath(startPoint, endPoint);
		System.out.println(visLines.size());
		aDrawer.drawLines(path, "green",3);
	}
	
	public static void writeCommands(ArrayList<Line> path, String fpath)  throws IOException
	{
		//String fpath = "C:\\Users\\Peter\\Documents\\MATLAB\\robotics\\iRobotCreateSimulatorToolbox\\commands.txt";
		File f = new File(fpath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		
		if(!f.exists())
			f.createNewFile();
		
		//Assume robot starts along positive x axis initially at (0,0)
		Line curline = path.get(0);
		Vertex st = curline.getStart();
		Vertex end = curline.getEnd();
		
		st = new Vertex(0,0);
		end = new Vertex(-2,0);
		double angle = (double) Math.toDegrees(Math.atan2(end.getY() - st.getY(), end.getX() - st.getX()));
		
		if(angle < 0)
			angle += 360;
		
		bw.write("turn " + angle + "\n"); //Initial turn is always positive angle.
		bw.write("move " + curline.getDist() + "\n");
		
	    System.out.println(angle);
	    
	    Vertex prev, cur, next;
	    Vertex origin = new Vertex(0,0);
	    
	    int psize = path.size();
	    for(int i=1; i < psize; i++)
	    {
		    //Handle Turns first
		    //3 verts: prev, cur, next. Use vector v1=cur-prev and v2=next-cur determines angle to turn to go from cur to next
		    prev = st;
		    cur = end;
		    curline = path.get(i);
		    next = curline.getEnd();
		    Vertex vect1 = new Vertex(cur.getX() - prev.getX(), cur.getY() - prev.getY()); //Treat a Vertex as a vector
		    Vertex vect2 = new Vertex(next.getX() - cur.getX(), next.getY() - cur.getY());
		    
		    //Solve for angle using dot product. Assumes angles on shortest path are less than 180 degrees.
		    double dotprod = vect1.getX()*vect2.getX() + vect1.getY()*vect2.getY();
		    double v1magn = vect1.getDistanceFrom(origin);
		    double v2magn = vect2.getDistanceFrom(origin);
		    angle = Math.toDegrees(Math.abs(Math.acos(dotprod/(v1magn*v2magn)))); //May need to be careful about negative or positive zero here.
		    System.out.println(dotprod/(v1magn*v2magn) + " v1mag:"+v1magn + " v2mag:"+v2magn + " prev:" + prev + " cur:" + cur + " next:" + next
		    		+ " vect1:"+vect1 + " vect2:"+vect2);
		    //Determine if next is left or right of cur prev using isLeft(). Set negative or positive based on that.
		    //For roomba, w>0 is CCwise. w<0 is Cwise
		    
		    if(!Obstacle.isLeft(next, cur, prev)) //If the next vertex is left of the line just traveled, turn CCwise, else Cwise
		    	angle *= -1;
		    
		    //Handle forward motion
		    bw.write("turn " + angle + "\n");
		    bw.write("move " + next.getDistanceFrom(cur) + "\n");
		    st = cur;
		    end = next;
	    }
	    
	    bw.close();
	}
}

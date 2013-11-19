
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Draw.SceneDrawer;
import Objects.Obstacle;
import Objects.Line;
import Objects.Vertex;
import Solve.DijkstraSolver;
import Solve.VisibilityGraph;

public class homework4 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 2)
		{
			System.out.println("Usage: java homework4 obstacleFileName startGoalFileName");
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
		aDrawer.drawLines(path, "green",3);
	}
}

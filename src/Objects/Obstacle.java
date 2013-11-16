package Objects;
import java.util.ArrayList;


public class Obstacle {
	ArrayList <Vertex> myPoints;
	ArrayList <Vertex> myExpandedPoints;
	private final double robotSize = 0.47;
	private Robot robotClone;
	
	public Obstacle (ArrayList<Vertex> points)
	{
		this.myPoints = points;
		robotClone = new Robot(new Vertex(0,0));
		
		myExpandedPoints = null;
		
	}
	
	public Obstacle ()
	{
		this.myPoints = new ArrayList <Vertex> ();
		
		myExpandedPoints = null;
	}
	
	public void addPoint(Vertex aVertex)
	{
		myPoints.add(aVertex);
		//ExpandObstacle();
	}
	
	public ArrayList <Vertex> getPoints()
	{
		return this.myPoints;
	}
	
	public ArrayList <Vertex> getExpandedPoints()
	{
		return this.myExpandedPoints;
	}
	
	
	public void ExpandObstacle()
	{
		myExpandedPoints = new ArrayList<Vertex> (4*myPoints.size());
		//for(Vertex v : myPoints)
			//myExpandedPoints.add(v.clone());
		int j=0;
		//Add expanded points
		for(Vertex v : myPoints) 
		{
			//Set the robot's reference at one of the vertices and reflect it about X and Y
			robotClone = new Robot(v);
			robotClone.reflectX();
			robotClone.reflectY();
			
			Vertex roboVerts[] = robotClone.getVerts();
			for(int i=1; i < roboVerts.length; i++)
				myExpandedPoints.add(roboVerts[i].clone());
			
			//Considers other orientations of the robot by simply reflecting its X and Y values with respect to the reference. Makes use of the
			//robots square symmetry. Limitations are that it only considers robot's orientation for North South East West, but this is sufficient
			//for rectangular obstacles
			robotClone.reflectX();
			robotClone.reflectY();
			for(int i=1; i < roboVerts.length; i++)
				myExpandedPoints.add(roboVerts[i].clone());
			
			robotClone.reflectX();
			for(int i=1; i < roboVerts.length; i++)
				myExpandedPoints.add(roboVerts[i].clone());
			
			robotClone.reflectX();
			robotClone.reflectY();
			for(int i=1; i < roboVerts.length; i++)
				myExpandedPoints.add(roboVerts[i].clone());
			
			
			//Debugging code
			/*
			System.out.println("Obs1stVert:" + v.toString() + ", robotCloneRef" + robotClone.getRef().toString());
			System.out.println(robotClone);
			j++;
			if(j==1)
				break;
			*/
		}
			
	}
	
	public String toString() {
		String s = "myPoints: [";
		for(Vertex v : myPoints)
			s += v.toString() + " ";
		s += "]\n";
		
		if(myExpandedPoints != null) {
			s += "myExpandedPoints: [";
			for(Vertex v : myExpandedPoints)
				s += v.toString() + " ";
			s += "]";
		}
		return s;
	}
}

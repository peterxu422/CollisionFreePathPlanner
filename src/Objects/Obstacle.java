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
		//TODO Implement
		myExpandedPoints = new ArrayList<Vertex> (myPoints.size());
		for(Vertex v : myPoints)
			myExpandedPoints.add(v.clone());
		
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

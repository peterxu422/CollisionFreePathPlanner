package Objects;
import java.util.ArrayList;


public class Obstacle {
	ArrayList <Vertex> myPoints;
	ArrayList <Vertex> myExpandedPoints;
	private final double robotSize = 0.47;
	public Obstacle (ArrayList<Vertex> points)
	{
		this.myPoints = points;
		ExpandObstacle();
	}
	
	public Obstacle ()
	{
		this.myPoints = new ArrayList <Vertex> ();
		myExpandedPoints = new ArrayList<Vertex> ();
	}
	
	public void addPoint(Vertex aVertex)
	{
		myPoints.add(aVertex);
		ExpandObstacle();
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
	}

}

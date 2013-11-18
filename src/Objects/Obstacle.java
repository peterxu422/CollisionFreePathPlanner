package Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

public class Obstacle {
	private ArrayList <Vertex> myPoints;
	private ArrayList <Vertex> myExpandedPoints;
	private ArrayList<Vertex> convexHullPoints;
	private Vertex p0;
	private Stack<Vertex> convexStack;
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
	
	public ArrayList<Vertex> getConvexHullPoints()
	{
		return this.convexHullPoints;
	}
	
	public void ExpandObstacle()
	{
		//TODO Implement
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
	
	public void computeConvexHull()
	{
		//Graham's Algorithm
		
		//Find Rightmost, Lowest point, P0
		p0 = getRightLow();
		
		//Sort other points angularly about P0. Break ties in favor of closeness. Label sorted P1...PN-1
		int n = myExpandedPoints.size();
		assert (n > 1);
		quicksort(0, n-1);
		
		//Stack stuff
		convexStack = new Stack<Vertex>();
		convexStack.push(myExpandedPoints.get(n-1));
		convexStack.push(myExpandedPoints.get(0));
		int i=1;
		while(i < n)
		{
			Vertex ptop = convexStack.pop();
			Vertex ptop1 = convexStack.peek();
			Vertex pi = myExpandedPoints.get(i);
			
			if(isLeft(pi, ptop, ptop1)) 
			{
				convexStack.push(ptop); //push back ptop since it was supposed to be there originally
				convexStack.push(pi);
				i++;
			}
			//if pi is not strictly left, then pop the top of stack. But this is already handled.
		}
		System.out.println("convexStack:"+convexStack);
		//Pop the convex hull points into a list
		convexHullPoints = new ArrayList<Vertex>(convexStack.size());
		int j = 0;
		while(true) {
			System.out.println(j++);
			try
			{
				convexHullPoints.add(convexStack.pop());
			}
			catch(EmptyStackException e)
			{
				break;
			}
		}
	}
	
	public boolean isLeft(Vertex c, Vertex b, Vertex a)
	{
		//Resources: http://mathworld.wolfram.com/CrossProduct.html
		//http://stackoverflow.com/questions/3461453/determine-which-side-of-a-line-a-point-lies
		
		//Take cross product of the vectors formed and determine directionality. Cross product of 2D vector is determinant
		return ((b.getX() - a.getX())*(c.getY() - a.getY()) - (b.getY() - a.getY())*(c.getX() - a.getX())) > 0;
	}
	
	public void quicksort(int low, int high)
	{
		int i = low, j = high;
		Vertex pivot = myExpandedPoints.get(low + (high-low)/2);
		
		while(i <= j)
		{
			while(myExpandedPoints.get(i).compareWrtP0(pivot, p0) == -1)
				i++;
			while(myExpandedPoints.get(j).compareWrtP0(pivot, p0) == 1)
				j--;
			
			if(i <= j) {
				Collections.swap(myExpandedPoints, i, j);
				i++;
				j--;
			}
		}
		
		if(low < j)
			quicksort(low, j);
		if(i < high)
			quicksort(i, high);
	}
	
	public Vertex getRightLow()
	{
		Vertex p0 = myExpandedPoints.get(0);
		for(Vertex v : myExpandedPoints)
			if(v.getY() <= p0.getY() && v.getX() >= p0.getX())
				p0 = v;
		
		return p0;
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

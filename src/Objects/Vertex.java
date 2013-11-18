package Objects;

import java.util.ArrayList;

public class Vertex {
	double X;
	double Y;
	double distanceFromStart;
	int prevNode;
	int nodeIndex;
	private ArrayList <Vertex> nodesLinked;
	
	public Vertex(double x, double y)
	{
		this.distanceFromStart = 1000;
		this.X = x;
		this.Y = y;
		this.prevNode = -1;
		nodesLinked = new ArrayList <Vertex>();
	}
	public Vertex(double x, double y, int index)
	{
		this.distanceFromStart = 1000;
		this.X = x;
		this.Y = y;
		this.prevNode = -1;
		nodesLinked = new ArrayList <Vertex>();
		nodeIndex = index;
	}
	public Vertex()
	{
		X = 0;
		Y = 0;
		this.prevNode = -1;
	}
	
	public double getX()		{return this.X;	}
	public double getY()		{return this.Y;	}
	public int getIndex()		{return this.nodeIndex;}
	public int getPreviousNode() { return this.prevNode;}
	public ArrayList<Vertex> getLinkedNodes () 	{return nodesLinked;}
	public double getDistFromStart () {return this.distanceFromStart;}
	public void setX(double x)	{this.X = x;	}
	public void setY(double y)	{this.Y = y;	}
	public void setXY(double x, double y){this.X = x; this.Y = y;}
	public void setIndex(int index){this.nodeIndex = index;}
	public void setPreviousNode(int index){this.prevNode = index;}
	public void setDistFromStart(double dist){this.distanceFromStart = dist;}
	
	public Vertex clone() {
		Vertex v = new Vertex(X, Y);
		return v;
	}
	
	public double getDistanceFrom(Vertex vert)
	{
		return Math.sqrt(Math.pow(this.X - vert.getX(),2.0)  + 
				Math.pow((this.Y - vert.getY()),2.0));
	}
	
	public boolean compareTo(Vertex vert)
	{
		if(Math.abs(this.X - vert.getX()) < 0.0001 && Math.abs(this.Y -vert.getY()) < 0.0001)
			return true;
		return false;
	}
	
	public int compareDistToP0(Vertex v, Vertex p0)
	{
		double p0x = p0.getX();
		double p0y = p0.getY();
		double sqx = Math.pow((this.getX() - p0x), 2);
		double sqy = Math.pow((this.getY() - p0y), 2);
		double dist = Math.sqrt(sqx + sqy);
		
		double vsqx = Math.pow((v.getX() - p0x), 2);
		double vsqy = Math.pow((v.getY() - p0y), 2);
		double vdist = Math.sqrt(vsqx + vsqy);
		
		if(dist < vdist)
			return -1;
		else if(dist > vdist)
			return 1;
		
		return 0;
	}
	
	public int compareAngToP0(Vertex v, Vertex p0)
	{
		double angle = Math.toDegrees(Math.atan2(this.getY()-p0.getY(), this.getX()-p0.getX()));
		double vangle = Math.toDegrees(Math.atan2(v.getY()-p0.getY(), v.getX() - p0.getX()));
		
		if(angle < vangle)
			return -1;
		else if(angle > vangle)
			return 1;
		
		return 0;
	}
	
	public int compareWrtP0(Vertex v, Vertex p0)
	{
		int compAng = this.compareAngToP0(v, p0);
		if(compAng == -1)
			return -1;
		else if(compAng == 1)
			return 1;
		
		int compDist = this.compareDistToP0(v, p0);
		if(compDist == -1)
			return -1;
		else if(compDist == 1)
			return 1;
		
		return 0;
		
	}
	
	public String toString() {
		return "("+X+","+Y+")";
	}
	public void addLink(int index, Line aLine) {
		Vertex linkedNode = aLine.getEnd();
		linkedNode.setIndex(index);
		nodesLinked.add(linkedNode);
	}
}

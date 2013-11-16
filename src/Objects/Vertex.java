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
	
	public String toString() {
		return "("+X+","+Y+")";
	}
	public void addLink(int index, Line aLine) {
		Vertex linkedNode = aLine.getEnd();
		linkedNode.setIndex(index);
		nodesLinked.add(linkedNode);
	}
}

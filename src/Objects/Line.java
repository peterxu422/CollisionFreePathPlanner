package Objects;

public class Line {
	private Vertex startPoint;
	private Vertex endPoint;
	private double dist;
	
	public Line (Vertex start, Vertex end)
	{
		this.startPoint = start;
		this.endPoint = end;
		this.dist = Math.sqrt(Math.pow(start.getX() - end.getX(),2.0)  + 
					Math.pow((start.getY() - end.getY()),2.0));  
	}
	
	public Line (double x1, double y1, double x2, double y2)
	{
		this.startPoint = new Vertex(x1,y1);
		this.endPoint = new Vertex(x2,y2);
	}
	
	public double getDist(){return dist;}
	public Vertex getStart(){return startPoint;}
	public Vertex getEnd(){return endPoint;}
	
	public String toString() {
		return "start: ("+startPoint.getX()+","+startPoint.getY()+")" + "end: ("+endPoint.getX()+","+endPoint.getY()+")";
	}
}

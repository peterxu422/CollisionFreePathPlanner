package Objects;

public class Line {
	private Vertex startPoint;
	private Vertex endPoint;
	
	public Line (Vertex start, Vertex end)
	{
		this.startPoint = start;
		this.endPoint = end;
	}
	
	public Line (double x1, double y1, double x2, double y2)
	{
		this.startPoint = new Vertex(x1,y1);
		this.endPoint = new Vertex(x2,y2);
	}
	
	public Vertex getStart(){return startPoint;}
	public Vertex getEnd(){return endPoint;}
}

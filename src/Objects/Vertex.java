package Objects;

public class Vertex {
	double X;
	double Y;
	
	public Vertex(double x, double y)
	{
		this.X = x;
		this.Y = y;
	}
	public Vertex()
	{
		X = 0;
		Y = 0;
	}
	
	public double getX()
	{
		return this.X;
	}
	
	public double getY()
	{
		return this.Y;
	}
	
	public void setX(double x)
	{
		this.X = x;
	}
	
	public void setY(double y)
	{
		this.Y = y;
	}
	
	public Vertex clone() {
		Vertex v = new Vertex(X, Y);
		return v;
	}
	
	public String toString() {
		return "("+X+","+Y+")";
	}
}

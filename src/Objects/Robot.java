package Objects;

public class Robot {
	
	//Assume polygonal dimensions of Robot are square where length is equal to Roomba diameter
	private Vertex ref;	//Assume ref is upper left
	private Vertex upRight, lowLeft, lowRight;
	private Vertex verts[]; //{ref, upRight, lowLeft, lowRight}
	private double diameter;
	
	public Robot(Vertex ref) 
	{
		this.ref = ref;
		this.diameter = 1.0;
		upRight = new Vertex(ref.getX()+diameter, ref.getY());
		lowLeft = new Vertex(ref.getX(), ref.getY() - diameter);
		lowRight = new Vertex(ref.getX() + diameter, ref.getY() - diameter);
		verts = new Vertex[4];
		verts[0] = ref;
		verts[1] = upRight;
		verts[2] = lowLeft;
		verts[3] = lowRight;
	}
	
	public Vertex getRef() 			{return ref;}
	public Vertex getUpRight()		{return upRight;}
	public Vertex getLowLeft()		{return lowLeft;}
	public Vertex getLowRight()		{return lowRight;}
	public Vertex[] getVerts()		{return verts;}
	public double getDiameter()		{return diameter;}
	
	public void rotate(double theta) 
	{
		
	}
	
	public void reflectX()
	{
		for(Vertex v : verts) {
			if(v != ref) {
				double x = v.getX()-ref.getX();
				x *= -1;
				x += ref.getX();
				v.setX(x);
				
			}
		}
	}
	
	public void reflectY()
	{
		for(Vertex v : verts) {
			if(v != ref) {
				double y = v.getY()- ref.getY();
				y *= -1;
				y += ref.getY();
				v.setY(y);
			}
		}
	}
	
	public String toString()
	{
		String s = "[";
		for(Vertex v : verts)
			s += "("+v.getX() + ","+v.getY()+") ";
		s += "]\n";
		return s;
	}
}

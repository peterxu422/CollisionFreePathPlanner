package Draw;

import javax.swing.*;

import java.awt.Graphics;

import Objects.Line;
import Objects.Obstacle;
import Objects.Vertex;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
public class SceneDrawer  {
	JPanel panel;
	LineDrawer comp;
	private double lowY;
	private double lowX;
	private double highX;
	private double highY;
	
    public SceneDrawer()
    {
    	JFrame testFrame = new JFrame();
    	testFrame.setTitle("World Map");
        comp = new LineDrawer();
        comp.setPreferredSize(new Dimension(820, 640));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        testFrame.pack();
        testFrame.setVisible(true);
    }
   
	public void setDimensions (double lowX, double lowY, double highX, double highY)
	{
		this.lowX = lowX;
		this.lowY = lowY;
		this.highX = highX;
		this.highY = highY;
	}

    public void drawObstacles(ArrayList <Obstacle> obstacles)
    {
    	
    	for(Obstacle obs: obstacles)
    	{
    		ArrayList <Vertex> vertices = obs.getPoints();
    		for(int vertItt = 0; vertItt< vertices.size(); vertItt ++)
    			comp.addLine(toPixelCoord(vertices.get(vertItt).getX(),'x'),
    						 toPixelCoord(vertices.get(vertItt).getY(), 'y'),
							 toPixelCoord(vertices.get((vertItt + 1)% vertices.size()).getX(),'x'),
							 toPixelCoord(vertices.get((vertItt + 1)% vertices.size()).getY(),'y'));
    		
    	}
    }
    
    public void drawVisiLines(ArrayList<Line> visLines, String strColor) {
    	for(Line line: visLines)
    	{
    		Color color;
    		if (strColor == "blue")
    			color = new Color(0,0,255);
    		else if (strColor == "green")
    			color = new Color(0,255,0);
    		else if (strColor == "yellow")
    			color = new Color(255,255,0);
    		else 
    			color = new Color(0,0,0);
    		
			comp.addLine(toPixelCoord(line.getStart().getX(),'x'),
						 toPixelCoord(line.getStart().getY(), 'y'),
						 toPixelCoord(line.getEnd().getX(),'x'),
						 toPixelCoord(line.getEnd().getY(),'y'),
						 color);
    	}	
	}
    
    public void drawPoint(Vertex point, String strCol)
    {
    	int x = toPixelCoord(point.getX(),'x');
    	int y =  toPixelCoord(point.getY(), 'y');
    			
    	Color color;
    	if(strCol.compareTo("start") == 0)
    		color = new Color(255,0,0);
    	else
    		color = new Color(0,255,0);
    	
    	comp.addLine(x-5,y,x+5,y,color);
    	comp.addLine(x,y-5,x,y+5,color);
    }
    
    private int toPixelCoord(double x1, char axis) {
		double scaling = 1, offset = 0;
		int windowSize;
		 if ((highX - lowX) / 820  > (highY - lowY)/ 640){
			scaling =  (highX - lowX) * 1.2;
			windowSize = 820;
		 }
		 else{
			 scaling = (highY - lowY) * 1.2;
			 windowSize = 640;
		 }
		 
		 if(axis == 'x')
			 offset = 0.5* scaling - ( highX + lowX ) / 2;
		 else
			 offset = 0.5* scaling - ( highY + lowY ) / 2;
			
		return  windowSize - (int)(( x1 + offset)* windowSize / scaling); 
	}    
}
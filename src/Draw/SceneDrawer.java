package Draw;

import javax.swing.*;

import Objects.Obstacle;
import Objects.Vertex;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
public class SceneDrawer  {
	JPanel panel;
	JFrame frame; 
    public SceneDrawer()
    {
         frame= new JFrame();

         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         frame.setTitle("World Map");
         frame.setSize (820, 640);
         frame.setVisible(true);
         panel = new JPanel() {         
        	 public void paintComponent( Graphics g ) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;

                Line2D line = new Line2D.Double(10, 10, 40, 40);
                g2.setColor(Color.blue);
                g2.setStroke(new BasicStroke(4));
                g2.draw(line);
             }

        };
        
        panel.setBackground(new Color(255,255,255));
        
        frame.add(panel);
        frame.setVisible( true );
    }
    public void drawObstacles(ArrayList <Obstacle> obstacles)
    {
    	for(Obstacle obs: obstacles)
    	{
    		ArrayList <Vertex> vertices = obs.getPoints();
    		for(int vertItt = 0; vertItt< vertices.size(); vertItt ++)
    		{
    			Line2D line = new Line2D.Double(vertices.get(vertItt).getX(),
    											vertices.get(vertItt).getY(),
    											vertices.get((vertItt + 1)% vertices.size()).getX(),
    											vertices.get((vertItt + 1)% vertices.size()).getY());
    		}
    	}
    }
    
}
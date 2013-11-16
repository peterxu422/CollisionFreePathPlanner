package Draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import javax.swing.JComponent;

public class LineDrawer  extends JComponent{

		private static class Line{
		    final int x1; 
		    final int y1;
		    final int x2;
		    final int y2;   
		    final Color color;
		    final int thickness;

		    public Line(int x1, int y1, int x2, int y2, Color color,int thickness) {
		        this.x1 = x1;
		        this.y1 = y1;
		        this.x2 = x2;
		        this.y2 = y2;
		        this.color = color;
		        this.thickness = thickness;
		        
		    }               
		}

		private final LinkedList<Line> lines = new LinkedList<Line>();

		public void addLine(int x1, int x2, int x3, int x4) {
		    addLine(x1, x2, x3, x4, Color.black,1);
		}
		public void addLine(int x1, int x2, int x3, int x4, Color color) {
		    lines.add(new Line(x1,x2,x3,x4, color,1));   
		}

		public void addLine(int x1, int x2, int x3, int x4, Color color, int thickness) {
		    lines.add(new Line(x1,x2,x3,x4, color,thickness));   
		    repaint();
		}

		public void clearLines() {
		    lines.clear();
		    repaint();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    for (int i = 0; i< lines.size(); i ++ ) {
		    	 Graphics2D g2 = (Graphics2D) g;
		        g2.setColor(lines.get(i).color);
		        g2.setStroke(new BasicStroke(lines.get(i).thickness));
		        g2.drawLine(lines.get(i).x1,lines.get(i).y1,lines.get(i).x2,lines.get(i).y2);
		    }
		
		}
}

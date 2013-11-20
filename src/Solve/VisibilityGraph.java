package Solve;


import java.awt.geom.Line2D;
import java.util.ArrayList;

import Objects.Line;
import Objects.Obstacle;
import Objects.Vertex;

/**
 * 
 * @author Frederik Clinckemaillie
 * This class calculates the Visibility of points to figure out which path is free of obstacles
 *
 */
public class VisibilityGraph {
	
	public VisibilityGraph(){
	}
	
	/**
	 *  Calculates the visibility between all points in the obstacles and the start and end points
	 * @param obstacles
	 * @param startPoint
	 * @param endPoint
	 * @return
	 */
	public ArrayList<Line> calculateVisibility(ArrayList<Obstacle> obstacles,Vertex startPoint,
											   Vertex endPoint) {
		//Adding an obstacle with just the start and endpoint
		//to add them to the visibility calculations
		Obstacle startPts = new Obstacle();
		Obstacle endPts = new Obstacle();
		startPts.addPoint(startPoint);
		endPts.addPoint(endPoint);
		obstacles.add(startPts);
		obstacles.add(endPts);
		ArrayList<Line> out = new ArrayList <Line>();
		
		for(int startObs =1; startObs < obstacles.size(); startObs++){
			
			ArrayList <Vertex> startPoints = obstacles.get(startObs).getExpandedPoints();
			if(startPoints == null)
				startPoints = obstacles.get(startObs).getPoints();
			for(int startVert = 0; startVert < startPoints.size(); startVert ++){
				for(int endObs =1; endObs < obstacles.size() ; endObs++){
					ArrayList <Vertex> endPoints = obstacles.get(endObs).getExpandedPoints();
					if(endPoints == null)
						endPoints = obstacles.get(endObs).getPoints();
					
					for(int endVert = 0; endVert < endPoints.size(); endVert ++){
						if(startObs != endObs || (endPoints.size() > 1 &&
								((startVert == (endVert + 1) % endPoints.size()) ||
								(endVert == (startVert + 1) % startPoints.size())))){
							
							Vertex vert1 = startPoints.get(startVert);
							Vertex vert2 = endPoints.get(endVert);
							
							if(!intersect(vert1, vert2, obstacles))
							{
								out.add(new Line (vert1, vert2));
							}
						}
					}
				}	
			}
		}
		return out;
	}

/**
 * Finds if the line made from vertex to vertex2 intersects any of the obstacles
 * @param vertex
 * @param vertex2
 * @param obstacles
 * @return
 */
	public boolean intersect(Vertex vertex, Vertex vertex2,ArrayList<Obstacle> obstacles) {
		double x1 = vertex.getX(); double y1 = vertex.getY();
		double x2 = vertex2.getX();double y2 = vertex2.getY();
		
		for(int obsItt = 0; obsItt< obstacles.size(); obsItt ++ ){
			if(obsItt > 0 && isWithin(obstacles.get(obsItt),new Vertex((vertex.getX() + vertex2.getX())/2,
																			(vertex.getY() + vertex2.getY())/2)))
			{
				return true;
			}
			Obstacle obs = obstacles.get(obsItt);
			ArrayList <Vertex> points;
			points = obs.getExpandedPoints();
			if(points == null)
				points = obs.getPoints();
			for(int vertItt = 0; vertItt< points.size(); vertItt ++ )
			{
				double x3 = points.get(vertItt).getX(); 
				double y3 = points.get(vertItt).getY();

				double x4 =	points.get((vertItt + 1) % points.size()).getX(); 
				double y4 = points.get((vertItt + 1) % points.size()).getY();
				
				double d = (y4-y3)*(x2-x1) - (x4-x3)* (y2-y1);
				
				double eps = 0.01;
			    if (Math.abs(d)> 0)
			    {
			   
					double xi = x1 + (((x4-x3)*(y1-y3)-(y4-y3)* (x1 - x3))/d) * (x2 - x1) ;
					double yi = y1 + (((x4-x3)*(y1-y3)-(y4-y3)* (x1 - x3))/d) * (y2 - y1) ;

					if(xi + eps>= Math.min(x1, x2) && xi + eps >= Math.min(x4, x3) &&
						xi- eps <= Math.max(x2, x1) && xi - eps <= (Math.max(x4, x3) ) &&
						yi + eps>= Math.min(y1, y2) && yi + eps >= Math.min(y4, y3) &&
						yi- eps <= Math.max(y2, y1) && yi - eps <= (Math.max(y4, y3) ) &&
						!(Math.abs(x3 - x1) < eps && Math.abs(y3 - y1) < eps ) &&
						!(Math.abs(x3 - x2) < eps && Math.abs(y3 - y2) < eps ) &&
						!(Math.abs(x4 - x1) < eps && Math.abs(y4 - y1) < eps ) &&
						!(Math.abs(x4 - x2) < eps && Math.abs(y4 - y2) < eps ))
						return true;
						
					
			    }else
			    {
			    	//if the lines are vertical and at the same x values, the y values are compared to see if they overlay
			    	if (Math.abs(x2 - x1) < eps )
			    	{
			    		if( Math.abs(x2 - x3)< eps && ((y1 > Math.min(y3, y4) && y1 < Math.max(y3, y4) ) ||
				    			(y2 > Math.min(y3, y4) && y2 < Math.max(y3, y4) )|| 
				    			(y3 > Math.min(y1, y2) && y3 < Math.max(y1, y2) )||
				    			(y4 > Math.min(y2, y1) && y4 < Math.max(y2, y1) )))
			    			return true;
			    	}
			    	else
			    	{
			    		double b1 = y1 - x1 * (y2-y1)/(x2-x1 + eps);
			    		double b2 = y3 - x3 * (y4-y3)/(x4-x3 + eps);
			    		if(Math.abs(b1 - b2)< eps && ((x1 > Math.min(x3, x4) && x1 < Math.max(x3, x4) ) ||
			    			(x2 > Math.min(x3, x4) && x2 < Math.max(x3, x4) )|| 
			    			(x3 > Math.min(x1, x2) && x3 < Math.max(x1, x2) )||
			    			(x4 > Math.min(x2, x1) && x4 < Math.max(x2, x1) )))
			    			return true;
			    	}	
			    }

			}
		}
	
		return false;
	}
/**
 * Finds out if the given vertex is within any of the obstacles.  
 * @param obstacle
 * @param vertex
 * @return
 */
	private boolean isWithin(Obstacle obstacle, Vertex vertex) {
		double eps = 0.01;
		boolean result = isContainedIn(obstacle,new Vertex (vertex.getX()+eps, vertex.getY()+eps)) && 
				 isContainedIn(obstacle,new Vertex (vertex.getX()+eps, vertex.getY() - eps)) &&
				 isContainedIn(obstacle,new Vertex (vertex.getX()-eps, vertex.getY()+eps)) &&
				 isContainedIn(obstacle,new Vertex (vertex.getX()-eps, vertex.getY()+eps));
	return result;
	}
	
	/**
	 * Finds if the given vertex is contained in  any of the obstacles. Helper function to isWithin 
	 * @param obstacle
	 * @param vertex
	 * @return
	 */
	private boolean isContainedIn(Obstacle obstacle, Vertex vertex) {

      int i;
      int j;
  	
    ArrayList<Vertex>	points = obstacle.getPoints();
	  
      boolean result = false;
      for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
	        if ((points.get(i).getY() > vertex.getY()) != (points.get(j).getY() > vertex.getY()) &&
	            (vertex.getX() <= (points.get(i).getX() - 
	            				 points.get(i).getX()) * (vertex.getY() - points.get(i).getY()) / (points.get(j).getY()-points.get(i).getY())
	            				 + points.get(i).getX())) {
	        		if (Line2D.ptSegDist(points.get(i).getX(),points.get(i).getY(),
	        						points.get(j).getX(),points.get(j).getY(), vertex.getX(), vertex.getY()) > 0.01) 
	        		result = !result;
	        }
      }
      return result;
	}
}

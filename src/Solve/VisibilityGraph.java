package Solve;

import java.util.ArrayList;

import Objects.Line;
import Objects.Obstacle;
import Objects.Vertex;

public class VisibilityGraph {
	
	public VisibilityGraph(){
	}
	
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
			for(int startVert = 0; startVert < obstacles.get(startObs).getPoints().size(); startVert ++){
				for(int endObs =1; endObs < obstacles.size() ; endObs++){
					for(int endVert = 0; endVert < obstacles.get(endObs).getPoints().size(); endVert ++){
						
						if(startObs != endObs || (obstacles.get(endObs).getPoints().size() > 1 &&
								((startVert == (endVert + 1) % obstacles.get(endObs).getPoints().size()) ||
								(endVert == (startVert + 1) % obstacles.get(startObs).getPoints().size())))){
							
							Vertex vert1 = obstacles.get(startObs).getPoints().get(startVert);
							Vertex vert2 = obstacles.get(endObs).getPoints().get(endVert);
							
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


	public boolean intersect(Vertex vertex, Vertex vertex2,ArrayList<Obstacle> obstacles) {
		double x1 = vertex.getX(); double y1 = vertex.getY();
		double x2 = vertex2.getX();double y2 = vertex2.getY();
		
		for(int obsItt = 0; obsItt< obstacles.size(); obsItt ++ ){
			Obstacle obs = obstacles.get(obsItt);
			for(int vertItt = 0; vertItt< obs.getPoints().size(); vertItt ++ )
			{
				double x3 = obs.getPoints().get(vertItt).getX(); 
				double y3 = obs.getPoints().get(vertItt).getY();

				double x4 = obs.getPoints().get((vertItt + 1) % obs.getPoints().size()).getX(); 
				double y4 = obs.getPoints().get((vertItt + 1) % obs.getPoints().size()).getY();
				
				double d = (y4-y3)*(x2-x1) - (x4-x3)* (y2-y1);
				
				double eps = 0.01;
			    if (Math.abs(d)> eps)
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

}

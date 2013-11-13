
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Draw.SceneDrawer;
import Objects.Obstacle;
import Objects.Vertex;

public class homework4 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 2)
		{
			System.out.println("Usage: java homework4 obstacleFileName startGoalFileName");
			return;
		}
		
		ArrayList <Obstacle> obstacles = new ArrayList <Obstacle>(); 
		
		
		//Reading and creation of obstacles
		BufferedReader obsReader = new BufferedReader(new FileReader(new File(args[0])));
		
		int numObstacles = Integer.parseInt(obsReader.readLine());
		for(; numObstacles > 0; numObstacles -- )
		{
			int numVertices = Integer.parseInt(obsReader.readLine());
			
			ArrayList <Vertex> obsVertices = new ArrayList <Vertex>();
			for(; numVertices > 0; numVertices -- )
			{
			 String str = obsReader.readLine();
			 int spaceIndex = str.indexOf(" ");
			 
			 double x =  Double.parseDouble(str.substring(0, spaceIndex));
			 double y =  Double.parseDouble(str.substring(spaceIndex + 1));
			 obsVertices.add(new Vertex(x, y));
			}
			obstacles.add(new Obstacle(obsVertices));
		}
		
		obsReader.close();

		//Reading of start and endpoint positions
		BufferedReader goalReader = new BufferedReader(new FileReader(new File(args[1])));
		String str = goalReader.readLine();
		int spaceIndex = str.indexOf(" ");
		 
		double x =  Double.parseDouble(str.substring(0, spaceIndex));
		double y =  Double.parseDouble(str.substring(spaceIndex + 1));
		Vertex startPoint = new Vertex(x, y);
		
		str = goalReader.readLine();
		spaceIndex = str.indexOf(" ");
		x =  Double.parseDouble(str.substring(0, spaceIndex));
		y =  Double.parseDouble(str.substring(spaceIndex + 1));
		Vertex endPoint = new Vertex(x, y);
		
		//Draw Obstacles
		//SceneDrawer aDrawer = new SceneDrawer();
		//aDrawer.drawObstacles(obstacles);
		
	}

}

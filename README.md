CollisionFreePathPlanner
========================

Collision Free Path Planner for Hw4 for Robotics Class

classes: Obstacle, ObstacleReader, Vertices (?), Vgraph, PathPlanner, Map

Class Definitions
<p>
Obstacle:  
<br>
- Members: <br>
			ArrayList/Vector of Vertices  <br>
			ArrayList/Vector of grownVertices  <br>
			static List<Obstacle> obstacles  <br>
  
- Methods: <br>
			void growObstacle(size/dimensions of Roomba)  
</p>

<p>
ObstacleReader: <br>  
  
- Members:   <br>
BufferedReader br  <br>
List<Obstacle> obstacles = Obstacle.obstacles  <br>
		  
- Methods: <br>
constructor ObstacleReader(String datafile)  <br>
			void generateObstacles()  <br>
</p>

<p>
Vgraph:  <br>

- Members:  <br>
	  
- Methods:  <br>
</p>

<p>
PathPlanner:    <br>

- Members:  <br>
    
- Methods:  <br>
</p>

<p>
Vertices:  <br>

- Members: double x, y  <br>
</p>


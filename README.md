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
<a href="http://mathworld.wolfram.com/VisibilityGraph.html">Visibility Graph Ref </a> <br>
<a href="http://en.wikipedia.org/wiki/Visibility_graph">Visibility Graph Ref Wiki </a> <br>
Not Sure how to construct a vgraph? <br>
Vgraph:  <br>

- Members: <br>
List<Obstacle> obstacles = Obstacle.obstacles <br>
	  
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

<p>
Testing for Roborace <br>

1) First run the java program with the appropriate obstacles and start goal files.
run configurations: java homework4 <obstacleFileName> <startGoalFileName> <commandsFilePath> <br>

obstacleFile should be located in one directory level above source code. <br>
startGoalFile should be located in one directory level above source code. <br>
commandsFilePath should be located in the MatLab directory where the Roomba will get the instructions <br>

2) Upload the file with "map" at the end of its name into the SimulatorGUI. Position the robot at (0,0) and orient it along the +x direction.
Load the hw4_team23.m file to let the robot traverse the path. Make sure the correct obstacleFile and startGoalFile was used in the java program.

</p>
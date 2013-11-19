package Solve;

import java.util.ArrayList;

import Objects.Line;
import Objects.Vertex;

public class DijkstraSolver {
	ArrayList<Vertex> nodes;

	public DijkstraSolver(ArrayList<Line> visLines) {
		nodes = new ArrayList <Vertex> ();
	
		nodes.add(visLines.get(0).getStart());
		//First we go through to ensure our node arraylist has every nodes
		//(each line has its inverse in the set, so we only need to add start nodes)
		for(Line aLine: visLines){
			if(!aLine.getStart().compareTo(nodes.get(nodes.size() - 1)))
				nodes.add(aLine.getStart());
		}
		int index = 0;
		for(Line aLine: visLines){
			
			if(!aLine.getStart().compareTo(nodes.get(index)))
				index ++;
			
			boolean foundNodeFlag = false;
			int nodeItt = 0;
			while(!foundNodeFlag && nodeItt < nodes.size())
			{
				if (nodes.get(nodeItt).compareTo(aLine.getEnd()))
					foundNodeFlag = true;
				if (!foundNodeFlag)
					nodeItt ++;
			}
			
			nodes.get(index).addLink(nodeItt, aLine);
		}
	}

	public ArrayList<Line> findPath(Vertex start, Vertex end) {
		
		int startNodeIndex = nodes.size() - 3, endNodeIndex = nodes.size() - 2;
		for (int nodeItt = nodes.size()-1; nodeItt >= 0; nodeItt --)
		{
			
			if (start.compareTo(nodes.get(nodeItt)))
				startNodeIndex = nodeItt;
			if (end.compareTo(nodes.get(nodeItt)))
				endNodeIndex = nodeItt;
		}
		
		nodes.get(startNodeIndex).setDistFromStart(0.0);
		
		ArrayList <Integer> unvisitedNodes = new ArrayList <Integer>();
		
		for( int i = 0; i< nodes.size(); i++)
			unvisitedNodes.add(new Integer(i));
		
		while(unvisitedNodes.size() > 0)
		{
			int smallestDistIndex = getSmallestDistUnvisited(unvisitedNodes);
			unvisitedNodes.remove(new Integer(smallestDistIndex));
			Vertex currentNode = nodes.get(smallestDistIndex);
			for(Vertex neighbor : currentNode.getLinkedNodes()){
				if(unvisitedNodes.contains(new Integer(neighbor.getIndex())))
				{
					double dist = currentNode.getDistFromStart() + currentNode.getDistanceFrom(neighbor);
					if(dist < neighbor.getDistFromStart())
					{
						neighbor.setDistFromStart(dist);
						neighbor.setPreviousNode(currentNode.getIndex());
					}
				}
			}	
		}
		ArrayList <Line> path = new ArrayList<Line>();
		int currentNodeIndex = endNodeIndex;
		Vertex pathStart = nodes.get(currentNodeIndex);
				
		while(currentNodeIndex != startNodeIndex && nodes.get(currentNodeIndex).getPreviousNode() != -1)
		{
			currentNodeIndex = nodes.get(currentNodeIndex).getPreviousNode();
			
			path.add(new Line(pathStart,nodes.get(currentNodeIndex)));
			pathStart = nodes.get(currentNodeIndex);
		}
		
		return path;
	}

	private int getSmallestDistUnvisited(ArrayList<Integer> unvisitedNodes) {
		double smallestDist = 5000;
		int smallestIndex = 0;
	
		for(Integer node: unvisitedNodes)
		{
			int index = node.intValue();
			double dist =nodes.get(index).getDistFromStart(); 
					
			if (dist < smallestDist)
			{
				smallestIndex = index;
				smallestDist = dist;
			}
		}
		return smallestIndex;
	}

}

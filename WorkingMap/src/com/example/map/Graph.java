package com.example.map;


import java.util.List;
import java.util.Vector;

public class Graph {
	
	public Vector<Location> points = new Vector<Location>();
	List<Edge> edges;
	public Vector<Integer> solution = new Vector<Integer>();
	
	Graph (){
		points.add(new Location(	577	,	370	,	0	));
		points.add(new Location(	520	,	315	,	1	));
		points.add(new Location(	539	,	278	,	2	));
		points.add(new Location(	477	,	279	,	3	));
		points.add(new Location(	395	,	205	,	4	));
		
//		edges.add(new Edge(	points.elementAt(0)	,	points.elementAt(1)	));
//		edges.add(new Edge(	points.elementAt(1)	,	points.elementAt(2)	));
//		edges.add(new Edge(	points.elementAt(1)	,	points.elementAt(3)	));
//		edges.add(new Edge(	points.elementAt(3)	,	points.elementAt(4)	));
//		edges.add(new Edge(	points.elementAt(2)	,	points.elementAt(3)	));

		
		findShortestPath(0,4);
		
	}
	
	//Dijkstra's algorithm
	void findShortestPath(int id1, int id2){
		solution.add(0);
		solution.add(1);
		solution.add(3);
		solution.add(4);
		
	}
	
	

}

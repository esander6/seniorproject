package com.example.map;


public class Edge {
	
	private final Location src;
	private final Location dest;
	private final double distance;
	
	public Edge (Location src, Location dest){
		this.src = src;
		this.dest = dest;
		this.distance = distanceBetween(src,dest);
	}
	
	public double distanceBetween(Location one, Location two){
		int xDif=Math.abs(one.x-two.x);
		int yDif=Math.abs(one.y-two.y);
		return Math.sqrt(xDif*xDif + yDif*yDif);
	}

}

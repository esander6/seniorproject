package com.example.map;



public class Location {
	int x;
	int y;
	int id;
	
	Location(int x, int y, int id){
		this.x=x;
		this.y=y;
		this.id=id;
	}
	
	public boolean equals(Location p){
		if(this.id==p.id) return true;
		else return false;
	}

}

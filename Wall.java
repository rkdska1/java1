package com.example.finpro;

public class Wall extends Parent{
	static int pixel=40;
	public int dx;
	public int dy;
    public Wall(int x, int y){
        super(x*pixel,y*pixel);
    	this.dx=y*pixel;
    	this.dy=x*pixel;
    }
}

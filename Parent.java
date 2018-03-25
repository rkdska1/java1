package com.example.finpro;

public abstract class Parent {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private int size;
    

    public Parent(int x, int y){
        this.x= x;
        this.y= y;
    }

    public int get_x(){
        return x;
    }
    public int get_y(){
        return y;
    }
    public void put_x(int dx){
    	x=dx;
    }
    public void put_y(int dy){
    	y=dy;
    }


}

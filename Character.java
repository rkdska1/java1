package com.example.finpro;

/*
 * Character Class, which will represent our hero, 'Ji-Woo'
 * 
 */


public class Character extends Parent implements Movable{		
    public int dx;
    public int dy;
    private int life=5;
    public int pixel= 40;

    public Character(int x, int y){
        super(x,y);
    }
    @Override
    public void move(int dx, int dy){
        super.x += dx;
        super.y += dy;
    }
    public void right_inc(){
    	this.dx += 1;
    }public void left_inc(){
    	this.dx -= 1;
    }public void up_inc(){
    	this.dy += 1;
    }public void down_inc(){
    	this.dy -= 1;
    }

    public void collide(){

    }

    public void coin_in(){
        life++;
    }

    public void die(){
        life--;
    }

}

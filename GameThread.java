package com.example.finpro;

import android.graphics.Canvas;

import com.example.finpro.Maze.MazeFrame;

/*
 * 
 * Thread class created to handle Canvas and Music Player
 * If playing=true thread is to be ran
 * If playing=false, thread is not
 */
public class GameThread extends Thread {		//to get the input for key_pad

    private boolean playing = false;
    private MazeFrame mf;
	   boolean isPaused;
    
    public GameThread(MazeFrame mf) {
          this.mf = mf;
    }
    
    public void startGame(){
    	playing=true;
    }
    public void finishGame(){
    	playing=false;
    }

	@Override
	public void run() {
	    while (playing) {
	    	Canvas canvas = null;
	    	try {
	    		canvas = mf.getHolder().lockCanvas();
	    		synchronized (mf.getHolder()) 	
	    		{
	    			mf.onDraw(canvas);
	    		}
	    	} 
	    	finally 
	    	{
	    		if (canvas != null) 
	    		{
	    			mf.getHolder().unlockCanvasAndPost(canvas);
	    		}
	    	}
	    }
	}	
}

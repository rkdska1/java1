package com.example.finpro;

import JNI.Textlcd;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/*
 *  Main Class Activity
 * 
 */
public class FinProActivity extends Activity {

	Button button;
	public final static String EXTRA_MESSAGE = "Game Started";
    private Textlcd tlcd;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tlcd = new Textlcd();
        tlcd.Write("To Start", "Select Button");
    }
    
	public void start(View v)
	{
		Intent i=new Intent(this, Maze.class);
		startActivity(i);
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.layout.main, menu);
    	return true;
    }
    
}
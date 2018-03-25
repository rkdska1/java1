package com.example.finpro;

import java.util.Timer;
import java.util.Arrays;

import JNI.Keypad;
import JNI.Textlcd;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Maze extends Activity{

    private Character character = new Character(0, 0);
    
    
    Wall wall97, wall1,wall2,wall3, wall4, wall5, wall6, wall7, wall8, wall9, wall10, wall11, wall12, wall13, wall14, wall15, wall16, wall17, wall18, wall19, wall20, wall21, wall22, wall23, wall24, wall25, wall26, wall27, wall28, wall29, wall30, wall31, wall32, wall33, wall34, wall35, wall36, wall37, wall38, wall39, wall40, wall41, wall42, wall43, wall44, wall45, wall46, wall47, wall48, wall49, wall50, wall51, wall52, wall53, wall54, wall55, wall56, wall57, wall58, wall59, wall60, wall61, wall62, wall63, wall64, wall65, wall66, wall67, wall68, wall69, wall70, wall71, wall72, wall73, wall74, wall75, wall76, wall77, wall78, wall79, wall80, wall81, wall82, wall83, wall84, wall85, wall86, wall87, wall88, wall89, wall90, wall91, wall92, wall93, wall94, wall95, wall96 ;
    
    
    private int BOARD[][] = new int[20][12];
    /////////////////////////////
    private Keypad keypad;
    private Textlcd tlcd;
    //////////////////////////////////
    public boolean left=false;
    public boolean right=false;
    public boolean down=false;
    public boolean up=true;
    public final int LEFT= 1;
    public final int RIGHT= 10;
    public final int UP= 100;
    public final int DOWN= 1000;
    int display_x= 480;
    int display_y= 800;
    int pixel = character.pixel;
    int count;    int count_mod;
    int move_factor;
    int countdown=-1;
    private Finish finish = new Finish(display_x-pixel, display_y-pixel);
    int stage=3;
    int exit_num=60;
    
    
    
    
    
    
    GameThread GT;
    
    
    //Initialization
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new MazeFrame(this));
        keypad = new Keypad(getApplicationContext());
        tlcd = new Textlcd();
        tlcd.Clear();
        tlcd.Write("** Being", "Played **");
        
        
    }
	
	/*
	 * Our main layout which will be the graphic background for our game 
	 * 
	 * 
	 */

	public class MazeFrame extends SurfaceView{
	    Bitmap background,  my_wall, pickachu, fin_alert;
	    Bitmap ch_l1, ch_l2, ch_l3, ch_l4, ch_l5, ch_r1, ch_r2, ch_r3, ch_r4, ch_r5, ch_u1, ch_u2, ch_u3, ch_u4, ch_u5, ch_d1, ch_d2, ch_d3, ch_d4, ch_d5; 
	      
	    private SurfaceHolder holder;
	    private int touch_x, touch_y, touched_x, touched_y;
	      
	    

	    public MazeFrame(Context context) {
	    	super(context);
	    	// TODO Auto-generated constructor stub
	    	GT= new GameThread(this);
	    	holder = getHolder();

	    	holder.addCallback(new SurfaceHolder.Callback() {

	    		@Override
	    		public void surfaceDestroyed(SurfaceHolder holder) 
	    		{
//	    			stopGame();
	    		}
              
	    		@Override
	    		public void surfaceCreated(SurfaceHolder holder) 
	    		{

	    			GT.startGame();
	    			GT.start();
	    		}
	    		@Override
	    		public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) 
	    		{
	    		}
	    	});


	    	Display dp = getWindowManager().getDefaultDisplay();
	    	set_bitmap();
	    	set_Wall(1);
	    	}
	
	    
	    
	    
	    
	    	public void move(int movement){										//Set the direction of the character, and the movement
	    		if(movement==0){
	    			left=true;	right=false;	up=false;	down=false;
	    		}else if(movement==1){
	    			right=true;	left=false;		up=false;	down=false;
	    		}
	    		else if(movement==2){
	    			down=true;	up=false;		left=false;		right=false;
	    		}else if(movement==3){
	    			up=true;	left=false;		right=false;	down=false;
	    		}
    			count=pixel;
	    	}
	    
	    	@Override
	    	public boolean onTouchEvent(MotionEvent event) {				//Implmentation of touch input, which will move the character
	    		//when starting the touch
	    		
	    		
	    		if(event.getAction()==MotionEvent.ACTION_DOWN)				//Get the axis when the user touches the screen first
	    		{
	      	  		touch_x=(int)event.getX();
	      	  		touch_y=(int)event.getY();

	      	  	}
	    		
	    		else if(event.getAction() == MotionEvent.ACTION_UP)			//Get the last axis when the user put their fingers off the screen
	    		{
	    			touched_x=(int)event.getX();
	    			touched_y=(int)event.getY();
	    			
	    			if(touched_x+100 < touch_x){//left animation
//	    				if(BOARD[character.dx-1][character.dy] != -1)
	    				if(character.dx >= pixel-1             && check_empty(character.dx-pixel, character.dy)      ){
//	    					character.left_inc();
	    					move(0);
	    				}
	    			}
	    			else if(touched_x-100 > touch_x){//right animation
//	    				if(BOARD[character.dx+1][character.dy] != -1)
	    				if(character.dx <= display_x-2*pixel      &&check_empty(character.dx+pixel, character.dy)  ){
//	    					character.right_inc();
	    					move(1);
	    				}
	    			}
	    			else if(touched_y +100 < touch_y){
//	    				if(BOARD[character.dx][character.dy-1] != -1)
	    				if(character.dy >= pixel-1                   &&check_empty(character.dx, character.dy-pixel)   ){
//	    					character.down_inc();
	    					move(2);
	    				}
	    			}
	    			else if(touched_y -100 > touch_y){
//	    				if(BOARD[character.dx][character.dy+1] != -1)
	    				if(character.dy <= display_y - 2*pixel     &&check_empty(character.dx, character.dy+pixel)    ){
//	    					character.up_inc();
	    					move(3);
	    				}
	    			}
	    		}
	    		
	    		return true;
	    	}
	    	    	
	    	

	    	@Override
	    	protected void onDraw(Canvas canvas) 
		    {
	    		if(countdown>0)
	    			countdown--;
	    		canvas.drawBitmap(background, 0, 0, null);
	    	if(character.dx == finish.get_x() && character.dy == finish.get_y()){
	    		if(countdown==-1)															//For 40 frames wait.
	    		{
		    		keypad.StopPlayer();
	    			countdown=exit_num;
	    		}
	    		if(countdown==exit_num){
		    		tlcd.Write("Success", "Stop playing");
		    		keypad.PlayWin();
	    		
	    		}
//	    			Game_Won();
//	    			GT.finishGame();
	    		canvas.drawBitmap(fin_alert, 0, 0, null);
	    		
	    		
	    		
	    		if(countdown==0)															//Exit the game
	    		System.exit(1);
	    			
	    			
	    			
	    	}
	    	else{
	    		if(count==0){																//Image when the character is not moving
	    			if(left){
	    	    		canvas.drawBitmap(ch_l1, character.dx,  character.dy, null);	    				
	    			}else if(right){
	    	    		canvas.drawBitmap(ch_r1, character.dx,  character.dy, null);	    				
	    			}else if(up){
	    	    		canvas.drawBitmap(ch_d1, character.dx,  character.dy, null);
	    			}else if(down){
	    	    		canvas.drawBitmap(ch_u1, character.dx,  character.dy, null);
	    			}
	    		}
	    		else if(count>0){															//If the character must move, set the animation
	    			count--;
	    			count_mod = count%4;
    	    		char_move(canvas, count_mod);
	    		}
	    		draw_wall(canvas);																	//Draws wall we designed
	    		}
		    }
	    	

	    	
/***********************************************************************************************************************************************
 

    	
    	
    	
    	Dirty code below.... 
    	
    	
    	
    	
    	
    	
    	
***********************************************************************************************************************************************/
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	public boolean check_empty(int xx, int yy){
	    		
	    		if(BOARD[yy/pixel][xx/pixel] != -1){
	    			return true;
	    		}
	    		else
	    			return false;

	    	}

	    	public void set_bitmap(){
		    	fin_alert = BitmapFactory.decodeResource(getResources(), R.drawable.fin);
		    	pickachu = BitmapFactory.decodeResource(getResources(), R.drawable.picka);
		    	
		    	background = BitmapFactory.decodeResource(getResources(), R.drawable.grass);
		    	ch_l1=BitmapFactory.decodeResource(getResources(), R.drawable.left1);
		    	ch_l2=BitmapFactory.decodeResource(getResources(), R.drawable.left2);
		    	ch_l3=BitmapFactory.decodeResource(getResources(), R.drawable.left3);
		    	ch_l4=BitmapFactory.decodeResource(getResources(), R.drawable.left4);
		    	ch_l5=BitmapFactory.decodeResource(getResources(), R.drawable.left5);
		    	ch_r1=BitmapFactory.decodeResource(getResources(), R.drawable.right1);
		    	ch_r2=BitmapFactory.decodeResource(getResources(), R.drawable.right2);
		    	ch_r3=BitmapFactory.decodeResource(getResources(), R.drawable.right3);
		    	ch_r4=BitmapFactory.decodeResource(getResources(), R.drawable.right4);
		    	ch_r5=BitmapFactory.decodeResource(getResources(), R.drawable.right5);
		    	ch_u1=BitmapFactory.decodeResource(getResources(), R.drawable.up1);
		    	ch_u2=BitmapFactory.decodeResource(getResources(), R.drawable.up2);
		    	ch_u3=BitmapFactory.decodeResource(getResources(), R.drawable.up3);
		    	ch_u4=BitmapFactory.decodeResource(getResources(), R.drawable.up4);
		    	ch_u5=BitmapFactory.decodeResource(getResources(), R.drawable.up5);
		    	ch_d1=BitmapFactory.decodeResource(getResources(), R.drawable.bottom1);
		    	ch_d2=BitmapFactory.decodeResource(getResources(), R.drawable.bottom2);
		    	ch_d3=BitmapFactory.decodeResource(getResources(), R.drawable.bottom3);
		    	ch_d4=BitmapFactory.decodeResource(getResources(), R.drawable.bottom4);
		    	ch_d5=BitmapFactory.decodeResource(getResources(), R.drawable.bottom5);
		    	
		    	my_wall=BitmapFactory.decodeResource(getResources(), R.drawable.wall);

		    	
		    	ch_l1=Bitmap.createScaledBitmap(ch_l1, pixel, pixel, true);
		    	ch_l2=Bitmap.createScaledBitmap(ch_l2, pixel, pixel, true);
		    	ch_l3=Bitmap.createScaledBitmap(ch_l3, pixel, pixel, true);
		    	ch_l4=Bitmap.createScaledBitmap(ch_l4, pixel, pixel, true);
		    	ch_l5=Bitmap.createScaledBitmap(ch_l5, pixel, pixel, true);
		    	ch_r1=Bitmap.createScaledBitmap(ch_r1, pixel, pixel, true);
		    	ch_r2=Bitmap.createScaledBitmap(ch_r2, pixel, pixel, true);
		    	ch_r3=Bitmap.createScaledBitmap(ch_r3, pixel, pixel, true);
		    	ch_r4=Bitmap.createScaledBitmap(ch_r4, pixel, pixel, true);
		    	ch_r5=Bitmap.createScaledBitmap(ch_r5, pixel, pixel, true);
		    	ch_u1=Bitmap.createScaledBitmap(ch_u1, pixel, pixel, true);
		    	ch_u2=Bitmap.createScaledBitmap(ch_u2, pixel, pixel, true);
		    	ch_u3=Bitmap.createScaledBitmap(ch_u3, pixel, pixel, true);
		    	ch_u4=Bitmap.createScaledBitmap(ch_u4, pixel, pixel, true);
		    	ch_u5=Bitmap.createScaledBitmap(ch_u5, pixel, pixel, true);
		    	ch_d1=Bitmap.createScaledBitmap(ch_d1, pixel, pixel, true);
		    	ch_d2=Bitmap.createScaledBitmap(ch_d2, pixel, pixel, true);
		    	ch_d3=Bitmap.createScaledBitmap(ch_d3, pixel, pixel, true);
		    	ch_d4=Bitmap.createScaledBitmap(ch_d4, pixel, pixel, true);
		    	ch_d5=Bitmap.createScaledBitmap(ch_d5, pixel, pixel, true);
		    	my_wall=Bitmap.createScaledBitmap(my_wall, pixel, pixel, true);
		    	background=Bitmap.createScaledBitmap(background, display_x,display_y, true);
		    	fin_alert=Bitmap.createScaledBitmap(fin_alert, 480, 800, true);
		    	pickachu = Bitmap.createScaledBitmap(pickachu, pixel, pixel, true);
	    	}

	    	public void set_Wall(int stage){
	    		//this is just for example
	    		//12 x 20
	    		for(int num=0; num<20; num++){
	    			for(int num2=0; num2<12; num2++){
	    				BOARD[num][num2]=0;
	    			}
	    		}
	    		
	    		  wall1 = new Wall(0, 9);
	    	       BOARD[0][9]=-1;       
	    	       wall2 = new Wall(0, 10);
	    	       BOARD[0][10]=-1;
	    	       wall3 = new Wall(0, 11);
	    	       BOARD[0][11]=-1;
	    	       wall4 = new Wall(1, 0);
	    	       BOARD[1][0]=-1;
	    	       wall5 = new Wall(1, 2);
	    	       BOARD[1][2]=-1;
	    	       wall97 = new Wall(1, 4);
	    	       BOARD[1][4]=-1;
	    	       wall6 = new Wall(1, 5);
	    	       BOARD[1][5]=-1;
	    	       wall7 = new Wall(1, 6);
	    	       BOARD[1][6]=-1;
	    	       wall8 = new Wall(1, 7);
	    	       BOARD[1][7]=-1;
	    	       wall9 = new Wall(1, 9);
	    	       BOARD[1][9]=-1; 
	    	       wall10 = new Wall(2, 0);
	    	       BOARD[2][0]=-1; 
	    	       wall11 = new Wall(2, 2);
	    	       BOARD[2][2]=-1; 
	    	       wall12 = new Wall(2, 9);
	    	       BOARD[2][9]=-1; 
	    	       wall13 = new Wall(2, 11);
	    	       BOARD[2][11]=-1; 
	    	       wall14 = new Wall(3, 0);
	    	       BOARD[3][0]=-1;
	    	       wall15 = new Wall(3, 3);
	    	       BOARD[3][3]=-1;
	    	       wall16 = new Wall(3, 4);
	    	       BOARD[3][4]=-1;
	    	       wall17 = new Wall(3, 5);
	    	       BOARD[3][5]=-1;
	    	       wall18 = new Wall(3, 7);
	    	       BOARD[3][7]=-1;
	    	       wall19 = new Wall(3, 11);
	    	       BOARD[3][11]=-1;
	    	       wall20 = new Wall(4, 0);
	    	       BOARD[4][0]=-1;
	    	       wall21 = new Wall(4, 2);
	    	       BOARD[4][2]=-1;
	    	       wall22 = new Wall(4, 6);
	    	       BOARD[4][6]=-1;
	    	       wall23 = new Wall(4, 8);
	    	       BOARD[4][8]=-1;
	    	       wall24 = new Wall(5, 0);
	    	       BOARD[5][0]=-1;
	    	       wall25 = new Wall(5, 2);
	    	       BOARD[5][2]=-1;
	    	       wall26 = new Wall(5, 4);
	    	       BOARD[5][4]=-1;
	    	       wall27 = new Wall(5, 6);
	    	       BOARD[5][6]=-1;
	    	       wall28 = new Wall(5, 9);
	    	       BOARD[5][9]=-1;
	    	       wall29 = new Wall(6, 0);
	    	       BOARD[6][0]=-1;
	    	       wall30 = new Wall(6, 4);
	    	       BOARD[6][4]=-1;
	    	       wall31 = new Wall(6, 10);
	    	       BOARD[6][10]=-1;
	    	       wall32 = new Wall(7, 0);
	    	       BOARD[7][0]=-1;
	    	       wall33 = new Wall(7, 2);
	    	       BOARD[7][2]=-1;
	    	       wall34 = new Wall(7, 3);
	    	       BOARD[7][3]=-1;
	    	       wall35 = new Wall(7, 5);
	    	       BOARD[7][5]=-1;
	    	       wall36 = new Wall(7, 6);
	    	       BOARD[7][6]=-1;
	    	       wall37 = new Wall(7, 7);
	    	       BOARD[7][7]=-1;
	    	       wall38 = new Wall(7, 11);
	    	       BOARD[7][11]=-1;
	    	       wall39 = new Wall(8, 2);
	    	       BOARD[8][2]=-1;
	    	       wall40 = new Wall(9, 0);
	    	       BOARD[9][0]=-1;
	    	       wall41 = new Wall(9, 1);
	    	       BOARD[9][1]=-1;
	    	       wall42 = new Wall(9, 3);
	    	       BOARD[9][3]=-1;
	    	       wall43 = new Wall(9, 4);
	    	       BOARD[9][4]=-1;
	    	       wall44 = new Wall(9, 6);
	    	       BOARD[9][6]=-1;
	    	       wall45 = new Wall(9, 7);
	    	       BOARD[9][7]=-1;
	    	       wall46 = new Wall(9, 8);
	    	       BOARD[9][8]=-1;
	    	       wall47 = new Wall(9, 9);
	    	       BOARD[9][9]=-1;
	    	       wall48 = new Wall(10, 2);
	    	       BOARD[10][2]=-1;
	    	       wall49 = new Wall(10, 10);
	    	       BOARD[10][10]=-1;
	    	       wall50 = new Wall(11, 0);
	    	       BOARD[11][0]=-1;
	    	       wall51 = new Wall(11, 2);
	    	       BOARD[11][2]=-1;
	    	       wall52 = new Wall(11, 3);
	    	       BOARD[11][3]=-1;
	    	       wall53 = new Wall(11, 4);
	    	       BOARD[11][4]=-1;
	    	       wall54 = new Wall(11, 7);
	    	       BOARD[11][7]=-1;
	    	       wall55 = new Wall(11, 8);
	    	       BOARD[11][8]=-1;
	    	       wall56 = new Wall(11, 10);
	    	       BOARD[11][10]=-1;
	    	       wall57 = new Wall(11, 11);
	    	       BOARD[11][11]=-1;
	    	       wall58 = new Wall(12, 0);
	    	       BOARD[12][0]=-1;
	    	       wall59 = new Wall(12, 4);
	    	       BOARD[12][4]=-1;
	    	       wall60 = new Wall(12, 7);
	    	       BOARD[12][7]=-1;
	    	       wall61 = new Wall(13, 1);
	    	       BOARD[13][1]=-1;
	    	       wall62 = new Wall(13, 2);
	    	       BOARD[13][2]=-1;
	    	       wall63 = new Wall(13, 7);
	    	       BOARD[13][7]=-1;
	    	       wall64 = new Wall(13, 8);
	    	       BOARD[13][8]=-1;
	    	       wall65 = new Wall(13, 9);
	    	       BOARD[13][9]=-1;
	    	       wall66 = new Wall(13, 10);
	    	       BOARD[13][10]=-1;
	    	       wall67 = new Wall(14, 4);
	    	       BOARD[14][4]=-1;
	    	       wall68 = new Wall(14, 5);
	    	       BOARD[14][5]=-1;
	    	       wall69 = new Wall(14, 6);
	    	       BOARD[14][6]=-1;
	    	       wall70 = new Wall(14, 8);
	    	       BOARD[14][8]=-1;
	    	       wall71 = new Wall(15, 0);
	    	       BOARD[15][0]=-1;
	    	       wall72 = new Wall(15, 2);
	    	       BOARD[15][2]=-1;
	    	       wall73 = new Wall(15, 4);
	    	       BOARD[15][4]=-1;
	    	       wall74 = new Wall(15, 6);
	    	       BOARD[15][6]=-1;
	    	       wall75 = new Wall(15, 8);
	    	       BOARD[15][8]=-1;
	    	       wall76 = new Wall(15, 10);
	    	       BOARD[15][10]=-1;
	    	       wall77 = new Wall(16, 0);
	    	       BOARD[16][0]=-1;
	    	       wall78 = new Wall(16, 2);
	    	       BOARD[16][2]=-1;
	    	       wall79 = new Wall(16, 4);
	    	       BOARD[16][4]=-1;
	    	       wall80 = new Wall(16, 6);
	    	       BOARD[16][6]=-1;
	    	       wall81 = new Wall(16, 8);
	    	       BOARD[16][8]=-1;
	    	       wall82 = new Wall(16, 10);
	    	       BOARD[16][10]=-1;
	    	       wall83 = new Wall(16, 11);
	    	       BOARD[16][11]=-1;
	    	       wall84 = new Wall(17, 0);
	    	       BOARD[17][0]=-1;
	    	       wall85 = new Wall(17, 2);
	    	       BOARD[17][2]=-1;
	    	       wall86 = new Wall(17, 7);
	    	       BOARD[17][7]=-1;
	    	       wall87 = new Wall(17, 10);
	    	       BOARD[17][10]=-1;
	    	       wall88 = new Wall(18, 0);
	    	       BOARD[18][0]=-1;
	    	       wall89 = new Wall(18, 3);
	    	       BOARD[18][3]=-1;
	    	       wall90 = new Wall(18, 4);
	    	       BOARD[18][4]=-1;
	    	       wall91 = new Wall(18, 5);
	    	       BOARD[18][5]=-1;
	    	       wall92 = new Wall(19, 0);
	    	       BOARD[19][0]=-1;
	    	       wall93 = new Wall(19, 6);
	    	       BOARD[19][0]=-1;
	    	       wall94 = new Wall(19, 8);
	    	       BOARD[19][0]=-1;
	    	       wall95 = new Wall(19, 9);
	    	       BOARD[19][0]=-1;
	    	       wall96 = new Wall(19, 10);
	    	       BOARD[19][0]=-1;
	    		
	    		
	    		
	    	}
	    	
	    	public void draw_wall(Canvas canvas){
	    		canvas.drawBitmap(pickachu, finish.dx, finish.dy, null);
	    	
	    		canvas.drawBitmap(my_wall, wall1.dx, wall1.dy, null);
	    		canvas.drawBitmap(my_wall, wall2.dx, wall2.dy, null);
	    		canvas.drawBitmap(my_wall, wall3.dx, wall3.dy, null);
	    		canvas.drawBitmap(my_wall, wall4.dx, wall4.dy, null);
	    		canvas.drawBitmap(my_wall, wall5.dx, wall5.dy, null);
	    		canvas.drawBitmap(my_wall, wall6.dx, wall6.dy, null);
	    		canvas.drawBitmap(my_wall, wall7.dx, wall7.dy, null);
	    		canvas.drawBitmap(my_wall, wall8.dx, wall8.dy, null);
	    		canvas.drawBitmap(my_wall, wall9.dx, wall9.dy, null);
	    		canvas.drawBitmap(my_wall, wall10.dx, wall10.dy, null);
	    		canvas.drawBitmap(my_wall, wall11.dx, wall11.dy, null);
	    		canvas.drawBitmap(my_wall, wall12.dx, wall12.dy, null);
	    		canvas.drawBitmap(my_wall, wall13.dx, wall13.dy, null);
	    		canvas.drawBitmap(my_wall, wall14.dx, wall14.dy, null);
	    		canvas.drawBitmap(my_wall, wall15.dx, wall15.dy, null);
	    		canvas.drawBitmap(my_wall, wall16.dx, wall16.dy, null);
	    		canvas.drawBitmap(my_wall, wall17.dx, wall17.dy, null);
	    		canvas.drawBitmap(my_wall, wall18.dx, wall18.dy, null);
	    		canvas.drawBitmap(my_wall, wall19.dx, wall19.dy, null);
	    		canvas.drawBitmap(my_wall, wall20.dx, wall20.dy, null);
	    		canvas.drawBitmap(my_wall, wall21.dx, wall21.dy, null);
	    		canvas.drawBitmap(my_wall, wall22.dx, wall22.dy, null);
	    		canvas.drawBitmap(my_wall, wall23.dx, wall23.dy, null);
	    		canvas.drawBitmap(my_wall, wall24.dx, wall24.dy, null);
	    		canvas.drawBitmap(my_wall, wall25.dx, wall25.dy, null);
	    		canvas.drawBitmap(my_wall, wall26.dx, wall26.dy, null);
	    		canvas.drawBitmap(my_wall, wall27.dx, wall27.dy, null);
	    		canvas.drawBitmap(my_wall, wall28.dx, wall28.dy, null);
	    		canvas.drawBitmap(my_wall, wall29.dx, wall29.dy, null);
	    		canvas.drawBitmap(my_wall, wall30.dx, wall30.dy, null);
	    		canvas.drawBitmap(my_wall, wall31.dx, wall31.dy, null);
	    		canvas.drawBitmap(my_wall, wall32.dx, wall32.dy, null);
	    		canvas.drawBitmap(my_wall, wall33.dx, wall33.dy, null);
	    		canvas.drawBitmap(my_wall, wall34.dx, wall34.dy, null);
	    		canvas.drawBitmap(my_wall, wall35.dx, wall35.dy, null);
	    		canvas.drawBitmap(my_wall, wall36.dx, wall36.dy, null);
	    		canvas.drawBitmap(my_wall, wall37.dx, wall37.dy, null);
	    		canvas.drawBitmap(my_wall, wall38.dx, wall38.dy, null);
	    		canvas.drawBitmap(my_wall, wall39.dx, wall39.dy, null);
	    		canvas.drawBitmap(my_wall, wall40.dx, wall40.dy, null);
	    		canvas.drawBitmap(my_wall, wall41.dx, wall41.dy, null);
	    		canvas.drawBitmap(my_wall, wall42.dx, wall42.dy, null);
	    		canvas.drawBitmap(my_wall, wall43.dx, wall43.dy, null);
	    		canvas.drawBitmap(my_wall, wall44.dx, wall44.dy, null);
	    		canvas.drawBitmap(my_wall, wall45.dx, wall45.dy, null);
	    		canvas.drawBitmap(my_wall, wall46.dx, wall46.dy, null);
	    		canvas.drawBitmap(my_wall, wall47.dx, wall47.dy, null);
	    		canvas.drawBitmap(my_wall, wall48.dx, wall48.dy, null);
	    		canvas.drawBitmap(my_wall, wall49.dx, wall49.dy, null);
	    		canvas.drawBitmap(my_wall,wall50.dx,wall50.dy,null);
	    		canvas.drawBitmap(my_wall,wall51.dx,wall51.dy,null);
	    		canvas.drawBitmap(my_wall,wall52.dx,wall52.dy,null);
	    		canvas.drawBitmap(my_wall,wall53.dx,wall53.dy,null);
	    		canvas.drawBitmap(my_wall,wall54.dx,wall54.dy,null);
	    		canvas.drawBitmap(my_wall,wall55.dx,wall55.dy,null);
	    		canvas.drawBitmap(my_wall,wall56.dx,wall56.dy,null);
	    		canvas.drawBitmap(my_wall,wall57.dx,wall57.dy,null);
	    		canvas.drawBitmap(my_wall,wall58.dx,wall58.dy,null);
	    		canvas.drawBitmap(my_wall,wall59.dx,wall59.dy,null);
	    		canvas.drawBitmap(my_wall,wall60.dx,wall60.dy,null);
	    		canvas.drawBitmap(my_wall,wall61.dx,wall61.dy,null);
	    		canvas.drawBitmap(my_wall,wall62.dx,wall62.dy,null);
	    		canvas.drawBitmap(my_wall,wall63.dx,wall63.dy,null);
	    		canvas.drawBitmap(my_wall,wall64.dx,wall64.dy,null);
	    		canvas.drawBitmap(my_wall,wall65.dx,wall65.dy,null);
	    		canvas.drawBitmap(my_wall,wall66.dx,wall66.dy,null);
	    		canvas.drawBitmap(my_wall,wall67.dx,wall67.dy,null);
	    		canvas.drawBitmap(my_wall,wall68.dx,wall68.dy,null);
	    		canvas.drawBitmap(my_wall,wall69.dx,wall69.dy,null);
	    		canvas.drawBitmap(my_wall,wall70.dx,wall70.dy,null);
	    		canvas.drawBitmap(my_wall,wall71.dx,wall71.dy,null);
	    		canvas.drawBitmap(my_wall,wall72.dx,wall72.dy,null);
	    		canvas.drawBitmap(my_wall,wall73.dx,wall73.dy,null);
	    		canvas.drawBitmap(my_wall,wall74.dx,wall74.dy,null);
	    		canvas.drawBitmap(my_wall,wall75.dx,wall75.dy,null);
	    		canvas.drawBitmap(my_wall,wall76.dx,wall76.dy,null);
	    		canvas.drawBitmap(my_wall,wall77.dx,wall77.dy,null);
	    		canvas.drawBitmap(my_wall,wall78.dx,wall78.dy,null);
	    		canvas.drawBitmap(my_wall,wall79.dx,wall79.dy,null);
	    		canvas.drawBitmap(my_wall,wall80.dx,wall80.dy,null);
	    		canvas.drawBitmap(my_wall,wall81.dx,wall81.dy,null);
	    		canvas.drawBitmap(my_wall,wall82.dx,wall82.dy,null);
	    		canvas.drawBitmap(my_wall,wall83.dx,wall83.dy,null);
	    		canvas.drawBitmap(my_wall,wall84.dx,wall84.dy,null);
	    		canvas.drawBitmap(my_wall,wall85.dx,wall85.dy,null);
	    		canvas.drawBitmap(my_wall,wall86.dx,wall86.dy,null);
	    		canvas.drawBitmap(my_wall,wall87.dx,wall87.dy,null);
	    		canvas.drawBitmap(my_wall,wall88.dx,wall88.dy,null);
	    		canvas.drawBitmap(my_wall,wall89.dx,wall89.dy,null);
	    		canvas.drawBitmap(my_wall,wall90.dx,wall90.dy,null);
	    		canvas.drawBitmap(my_wall,wall91.dx,wall91.dy,null);
	    		canvas.drawBitmap(my_wall,wall92.dx,wall92.dy,null);
	    		canvas.drawBitmap(my_wall,wall93.dx,wall93.dy,null);
	    		canvas.drawBitmap(my_wall,wall94.dx,wall94.dy,null);
	    		canvas.drawBitmap(my_wall,wall95.dx,wall95.dy,null);
	    		canvas.drawBitmap(my_wall,wall96.dx,wall96.dy,null); 
	    		canvas.drawBitmap(my_wall,wall97.dx,wall97.dy,null); 
	    	}
	    	
	    	public void char_move(Canvas canvas, int count_mod){
    			if(left){
    	    		character.left_inc();
    	    		if(count_mod==0)
    	    			canvas.drawBitmap(ch_l2, character.dx,  character.dy, null);
    	    		else if(count_mod==1)
    	    			canvas.drawBitmap(ch_l3, character.dx,  character.dy, null);
    	    		else if(count_mod==2)
    	    			canvas.drawBitmap(ch_l4, character.dx,  character.dy, null);
    	    		else if(count_mod==3)
    	    			canvas.drawBitmap(ch_l5, character.dx,  character.dy, null);
    			}else if(right){
    				character.right_inc();
    				if(count_mod==0)
    	    			canvas.drawBitmap(ch_r2, character.dx,  character.dy, null);
    	    		else if(count_mod==1)
    	    			canvas.drawBitmap(ch_r3, character.dx,  character.dy, null);
    	    		else if(count_mod==2)
    	    			canvas.drawBitmap(ch_r4, character.dx,  character.dy, null);
    	    		else if(count_mod==3)
    	    			canvas.drawBitmap(ch_r5, character.dx,  character.dy, null);
    			}else if(up){
    				character.up_inc();
    				if(count_mod==0)
    	    			canvas.drawBitmap(ch_d2, character.dx,  character.dy, null);
    	    		else if(count_mod==1)
    	    			canvas.drawBitmap(ch_d3, character.dx,  character.dy, null);
    	    		else if(count_mod==2)
    	    			canvas.drawBitmap(ch_d4, character.dx,  character.dy, null);
    	    		else if(count_mod==3)
    	    			canvas.drawBitmap(ch_d5, character.dx,  character.dy, null);
    			}else if(down){
    				character.down_inc();
    				if(count_mod==0)
    	    			canvas.drawBitmap(ch_u2, character.dx,  character.dy, null);
    	    		else if(count_mod==1)
    	    			canvas.drawBitmap(ch_u3, character.dx,  character.dy, null);
    	    		else if(count_mod==2)
    	    			canvas.drawBitmap(ch_u4, character.dx,  character.dy, null);
    	    		else if(count_mod==3)
    	    			canvas.drawBitmap(ch_u5, character.dx,  character.dy, null);
    			}
	    	}
	    	
	}
	
	public void Game_Won(){
//			GT.finishGame();

			
	}

}

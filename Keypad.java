package JNI;



//import android.R;
import com.example.finpro.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Keypad {
 static {
  System.loadLibrary("keypad");
 }

 Context mContext;
 private int key;
 private MediaPlayer mp;
 int key11;

 public native int Open();

 public native int Close();

 public native int GetValue();

 public Keypad(Context context) {
  mContext=context;
  int res = Open();
  Log.e("MMMAZE", "file open " + res);

  PlayNormal();

  new Thread(new Runnable() {
   public void run() {
    while (true) {
     try {
      Thread.sleep(300);
     } catch (Throwable t) {
     }
     key11 = Update();
     Message m = Message.obtain();
     m.what = key11;
     handler.sendMessage(m);
    }
   }
  }).start();
 }

 public int Update() {
  key = GetValue();
  Log.e("MMMAZE", "file open " + key);
  return key;
 }

 Handler handler = new Handler() {
  public void handleMessage(Message msg) {

   if (msg.what == 2) {
    StopPlayer();
    mp = MediaPlayer.create(mContext, R.raw.carrider);
    mp.setLooping(true);
    mp.start();
   }
   if (msg.what == 3) {
    StopPlayer();
    mp = MediaPlayer.create(mContext,R.raw.hon);
    mp.setLooping(true);
    mp.start();
   }
   if (msg.what == 4) {
    StopPlayer();
    mp = MediaPlayer.create(mContext, R.raw.crazy);
    mp.setLooping(true);
    mp.start();
   }
   if (msg.what == 6) {
    StopPlayer();

   }
   if (msg.what == 9) {
	   StopPlayer();
	    mp = MediaPlayer.create(mContext, R.raw.maple);
	    mp.setLooping(true);
	    mp.start();
   }

  }
 };

 public void StopPlayer(){
  if(mp.isPlaying()){
	  mp.stop();
//	  mp.pause();
//   mp.release();
//   mp=null;
  }
 }
 public void PlayWin(){ 
  mp=MediaPlayer.create(mContext, R.raw.pika);
  mp.setLooping(true);
  mp.start();
 }
 public void PlayLose(){
  mp=MediaPlayer.create(mContext, R.raw.crazy);
  mp.setLooping(false);
  mp.start();
 }
 public void PlayNormal(){
  mp=MediaPlayer.create(mContext, R.raw.maple);
  mp.setLooping(true);
  mp.start();
 }
} 

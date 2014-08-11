package com.example.owners;



import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Music extends Activity {
	MediaPlayer song ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		song= MediaPlayer.create(Music.this, R.raw.on);
		song.start();
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
					
					
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent changeactivity= new Intent("com.example.owners.MainActivity");
					startActivity(changeactivity);
				}
				
			}
		};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		song.stop();
		finish();
	}
	

}

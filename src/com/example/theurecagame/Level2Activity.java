package com.example.theurecagame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

//activity for level 2
public class Level2Activity extends Activity {
	private Frame frame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//initialize the frame for level 2
		frame = new Frame(2,this,3,3);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        // making it full screen
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // setting game frame as the view
	     
		setContentView(frame);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level_panel, menu);
		return true;
	}

}
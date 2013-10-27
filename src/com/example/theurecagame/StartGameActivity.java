package com.example.theurecagame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class StartGameActivity extends Activity {
	private Frame frame;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		frame = new Frame(1,this,4,4);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        // making it full screen
	     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // setting LevelPanel as the view
	     
		setContentView(frame);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_level_panel, menu);
		return true;
	}

}

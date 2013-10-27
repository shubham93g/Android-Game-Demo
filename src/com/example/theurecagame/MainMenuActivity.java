package com.example.theurecagame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		final Button new_game = (Button) findViewById(R.id.new_game);
		final Button resume = (Button) findViewById(R.id.resume);
		final Button how_to_play = (Button) findViewById(R.id.how_to_play);
		
		new_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 // Perform action on click
                  Intent intent = new Intent(MainMenuActivity.this, StartGameActivity.class);
                  Log.d("URECA", "New Game starting : ");
                  MainMenuActivity.this.startActivity(intent);
             }
          }
		);
		
	}

}

package com.example.theurecagame;

import android.graphics.Canvas;
import android.graphics.Paint;

public class HUD {
	
	int xPixel;
	int yPixel;
	int width;
	int height;
	Score score;
	long startTime;
	
	public HUD(Score score, int height, Frame frame){
		this.score = score;
		this.height = height;
		width = frame.getWidth();
		xPixel = 0;
		yPixel = 0;
		startTime = System.currentTimeMillis();
		
		
	}
	
	public Score get_score(){
		return this.score;
	}
	
	public int get_height(){
		return this.height;
	}
	
	public int get_xPixel(){
		return this.xPixel;
	}
	
	public int get_yPixel(){
		return this.yPixel;
	}
	
	public void set_score(Score score){
		this.score = score;
	}
	
	public void draw(Canvas canvas, Paint paint, float textSize, Paint paint_point, float pointTextSize){
		canvas.drawText("SCORE : "+score.get_currentScore() + " TIME : "+ ((System.currentTimeMillis()-startTime)/10)/100.0 , 10, (height-textSize), paint);
		if(score.get_popCount()!=0)
		canvas.drawText("+"+score.get_previousPoint(),width-60,height-pointTextSize,paint_point);
		canvas.drawLine(0, height, width, height, paint);
	}

}

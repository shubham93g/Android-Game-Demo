package com.example.theurecagame;


import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Ball {
	private int xPixel;
	private int xLocation;
	private int yPixel;
	private int yLocation;
	private boolean visibility;
	private int color;
	private Bitmap bitmap;
	private int xLocationNext;
	private int yLocationNext;
	
	public Ball(int xLocation, int yLocation, boolean visibility){
		this.xLocation = xLocation;
		this.yLocation = yLocation;
		this.visibility = visibility;
		
		randomizeColor();
		Log.d("URECA", "URECA : Ball [i][j]=["+xLocation+"]["+yLocation+"] color = " + color);
		}
	
	private void randomizeColor(){
		Random random = new Random();
		this.color = random.nextInt(6);
	}
	
	public int get_xLocation(){
		return this.xLocation;
		}
	
	public int get_yLocation(){
		return this.yLocation;
		}
	
	public int get_xPixel(){
		return this.xPixel;
	}
	
	public int get_yPixel(){
		return this.yPixel;
	}
	
	public boolean get_visibility(){	
		return this.visibility;
		}
	
	public int get_color(){
		return this.color; 
	}
	
	public int get_xLocationNext(){
		return this.xLocationNext;
	}
	
	public int get_yLocationNext(){
		return this.yLocationNext;
	}
	
	public void setPixel(int xPixel, int yPixel){
		this.xPixel = xPixel;
		this.yPixel = yPixel;
	}
	
	public void setLocationNext(int xLocationNext, int yLocationNext){ 
		//must be called only AFTER updateLocation()
		this.xLocationNext = xLocationNext;
		this.yLocationNext = yLocationNext;
	}
	
	public void set_color(int color){
		this.color = color;
	}
	
	public void set_bitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	
	public void updateLocation(){
		this.xLocation = this.xLocationNext;
		this.yLocation = this.yLocationNext;
	}
	
	 public void draw(Canvas canvas, Frame frame) {
		 if(this.get_visibility())
			canvas.drawBitmap(bitmap, xPixel+frame.get_xPixel() + frame.get_xPixelOffset(), yPixel+frame.get_yPixel()+frame.get_yPixelOffset(), null);
		}
	 
	 public void locationToPixel(){
			xPixel = bitmap.getWidth()*yLocation;
		 	yPixel = bitmap.getHeight()*xLocation;
	 	}
	 
	 public void set_visibility(boolean visibility){
		 	this.visibility = visibility;
	 }
	 
	 public boolean isTouched(int xInput, int yInput, Frame frame){
		 if( //ball is touched
				 (frame.get_xPixel() + frame.get_xPixelOffset() + this.xPixel <= xInput) &&
				 (xInput < frame.get_xPixel() + frame.get_xPixelOffset() + this.xPixel + this.bitmap.getWidth()) &&
				 (frame.get_yPixel() + frame.get_yPixelOffset() + this.yPixel <= yInput) &&
				 (yInput < frame.get_yPixel() + frame.get_yPixelOffset() + this.yPixel + this.bitmap.getHeight())
			)
		 {
			 Log.d("URECA", "URECA : Ball ["+xLocation+"]["+yLocation+"] color : "+this.get_color()+" touched");
			 return true;
		 }
			 return false;
	 }
	 
	 
}

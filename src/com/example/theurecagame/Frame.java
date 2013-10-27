package com.example.theurecagame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class Frame extends SurfaceView implements SurfaceHolder.Callback{
	
	private SoundPool sounds;
	private int sPop;
	private int xNum;
	private int yNum;
	private int width;
	private int height;
	private int xPixel;
	private int yPixel;
	private int xPixelOffset;
	private int yPixelOffset;
	private Ball [][] ballFrame;
	private Speed speed;
	private Bitmap bitmap;
	private FrameThread frameThread;
	private int [] color;
	private HUD hud;
	private Paint paint;
	private Paint paint_point;
	private Score score;
	private int balls;
	private int level;

	//create frame with the level number and by specifying the dimensions of the ball array
	public Frame(int level, Context context, int xNum, int yNum){
		super(context);
		getHolder().addCallback(this);
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		sPop = sounds.load(context, R.raw.pop, 1);
		this.level = level;
		this.xNum = xNum;
		this.yNum = yNum;
		this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red); //for reference, since all bitmaps hold the same size
		this.height = bitmap.getHeight()*yNum;
		this.width = bitmap.getWidth()*xNum;
		this.xPixelOffset = 0;
		this.yPixelOffset = 0;
		this.ballFrame = new Ball[xNum][yNum];
		if(level==2)
			speed = new Speed(2,1,1,1);
		if(level==1)
			speed = new Speed(0,0,1,1);
		color =  new int[6];
		paint = new Paint();
		paint_point = new Paint();
		// draw some text using FILL style
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		//turn antialiasing on
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint_point.setStyle(Paint.Style.FILL);
		paint_point.setColor(Color.GREEN);
		//turn antialiasing on
		paint_point.setAntiAlias(true);
		paint_point.setTextSize(30);
		score = new Score();
		balls = 0;
		Log.d("URECA", "URECA : Frame xNum = " + xNum + " yNum = " + yNum);
		Log.d("URECA", "URECA : Frame height = " + height + " width = " + width);
		
	}
	
	public int get_xPixel(){
		return this.xPixel;
	}
	
	public int get_xPixelOffset(){
		return this.xPixelOffset;
	}
	
	public int get_yPixel(){
		return this.yPixel;
	}
	
	public int get_yPixelOffset(){
		return this.yPixelOffset;
	}
	
	public void render(Canvas canvas){
		try{
		int i=0,j=0;
		canvas.drawColor(Color.BLACK);
		
		hud.draw(canvas,paint,paint.getTextSize(), paint_point, paint_point.getTextSize());
		while(i<this.xNum)
		{
			j=0;
			while(j<this.yNum)
			{
				if(ballFrame[i][j].get_visibility())
					ballFrame[i][j].draw(canvas, this);	
				j++;
			}
			i++;
		}
		
		}
		catch(Exception e){
			Log.d("URECA", "URECA : Rendering error");
		}
	}
	
	//select bitmap based on the color
	public void selectBitmap(Ball ball){
		switch(ball.get_color()){
		case 0: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red));
				color[0]++;
				break;
		case 1: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blue));
				color[1]++;
				break;
		case 2: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.green));
				color[2]++;
				break;
		case 3: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.purple));
				color[3]++;
				break;
		case 4: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.orange));
				color[4]++;
				break;
		case 5: ball.set_bitmap(BitmapFactory.decodeResource(getResources(), R.drawable.yellow));
				color[5]++;
				break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		hud = new HUD(score,getHeight()/10, this);
		for(int i=0;i<6;i++)
			color[i]=0;
		this.xPixel = (getWidth() - this.width)/2;
		this.yPixel = (getHeight()- this.height)/2;
		Log.d("URECA", "URECA : View height = " + getHeight() + " width = " + getWidth());
		Log.d("URECA", "URECA : Frame xPixel = " + xPixel + " yPixel = " + yPixel);
		int i=0,j=0;
		Log.d("URECA", "URECA : Initialize balls");
		while(i<this.xNum)
		{ 
			j=0;
			while(j<this.yNum)
			{
				ballFrame[i][j] = new Ball(i,j,true);
				balls++;
				selectBitmap(ballFrame[i][j]);
				ballFrame[i][j].locationToPixel();
				Log.d("URECA", "URECA : Ball xPixel = " + ballFrame[i][j].get_xPixel() + " yPixel = " + ballFrame[i][j].get_yPixel()); 
				j++;
			}
			i++;
		}
		Log.d("URECA", "URECA : Initialization over");
		
		// create the game loop thread
		frameThread = new FrameThread(getHolder(), this);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		frameThread.setRunning(true);
		frameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// tell the thread to shut down and wait for it to finish
				// this is a clean shutdown
				boolean retry = true;
				while (retry) {
					try {
						frameThread.setRunning(false);
						frameThread.join();
						retry = false;
					} catch (InterruptedException e) {
						// try again shutting down the thread
					}
				}
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if( //input is inside the frame
				(xPixel+xPixelOffset<=event.getX()) && (event.getX()<=xPixel+width+xPixelOffset) &&
				(yPixel+yPixelOffset<=event.getY()) && (event.getY()<=yPixel+height+yPixelOffset)
				)
		{
			int i=0;
			int j=0;
		Log.d("URECA", "URECA : Frame input [" + event.getX() + "][" + event.getY() + "]");
		while(i<xNum)
		{ 	boolean found = false;
			j=0;
			while(j<yNum)
			{
				if(ballFrame[i][j].get_visibility() && ballFrame[i][j].isTouched((int)event.getX(), (int)event.getY(), this) )
				{
					score.updateScore(ballFrame[i][j], System.currentTimeMillis());
					ballFrame[i][j].set_visibility(false);
					sounds.play(sPop, 1.0f, 1.0f, 0, 0, 1.5f);
					color[ballFrame[i][j].get_color()]--;
					balls--;
					Log.d("URECA", "URECA : Ball left: " + color[ballFrame[i][j].get_color()] + " color: " + ballFrame[i][j].get_color());
					found = true;
					break;
				}
				if(found)
					break;
				j++;
			}
			if(found)
				break;
			i++;
		}
		if(balls==0)
		{
			frameThread.setRunning(false);
			AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Level "+level+" complete ! Your score was :" + score.get_currentScore());
            builder1.setCancelable(false);
            builder1.setPositiveButton("Proceed",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	if(level==1)
                	{
                	Intent intent = new Intent(getContext(), Level2Activity.class);
                    Log.d("URECA", "Level 2 starting : ");
                    ((Activity)getContext()).startActivity(intent);
                    ((Activity)getContext()).finish();
                	}
                	if(level==2)
                		dialog.cancel();
                }
            });
            builder1.setNegativeButton("Quit",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	((Activity)getContext()).finish();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
		}
		}
		else
			Log.d("URECA", "URECA : Out-of-Bound input [" + event.getX() + "][" + event.getY() + "]");
		return super.onTouchEvent(event);
	}
	
	//update fram location based on ball speed
	public void updateFrameLocation(){
		xPixelOffset += speed.get_xSpeed()*speed.get_xDirection();
		yPixelOffset += speed.get_xSpeed()*speed.get_yDirection();
		//calculating bounce
		if(xPixel+xPixelOffset <= 0 || xPixel+xPixelOffset+width>=getWidth())
			speed.set_xDirection(speed.get_xDirection()*-1);
		if(yPixel+yPixelOffset <= getHeight()/10 || yPixel+yPixelOffset+height>=getHeight())
			speed.set_yDirection(speed.get_yDirection()*-1);
	}
	
}

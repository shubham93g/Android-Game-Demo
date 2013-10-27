package com.example.theurecagame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class FrameThread extends Thread{
	// Surface holder that can access the physical surface
				private SurfaceHolder surfaceHolder;
				// The actual view that handles inputs
				// and draws to the surface
				private Frame frame;
				// flag to hold game state 
				private boolean running;
				
				public void setRunning(boolean running) {
					this.running = running;
				}

				public FrameThread(SurfaceHolder surfaceHolder, Frame frame) {
					super();
					this.surfaceHolder = surfaceHolder;
					this.frame = frame;
				}
				
				public void run() {
					Canvas canvas;
					Log.d("URECA", "URECA : Thread Running");
					while (running) {
						canvas = null;
						// try locking the canvas for exclusive pixel editing on the surface
						try {
							canvas = this.surfaceHolder.lockCanvas();
							synchronized (surfaceHolder) {
								// update game state
								this.frame.updateFrameLocation();
								// draws the canvas on the panel
								this.frame.render(canvas);
								
									
							}
						} finally {
							// in case of an exception the surface is not left in
							// an inconsistent state
							if (canvas != null) {
								surfaceHolder.unlockCanvasAndPost(canvas);
							}
						}	// end finally
					}
					Log.d("URECA", "URECA : Thread Stopped");
				}

}

package com.example.theurecagame;

//class to manage the scoring system
public class Score {
	private int currentScore; //total points
	private int previousPositivePoint;
	private int previousColor;
	private long previousPopTime;
	private int popCount;
	
	public Score(){
		popCount = 0;
		currentScore = 0;
	}
	
	public void updateScore(Ball ball, long time){
		if(popCount!=0){
			if(ball.get_color() == previousColor)//if its the same ball
			{
				if(time-previousPopTime <=500){		//if the reaction time is less than half a second
					currentScore+=previousPositivePoint*2; //give double the previous points
					previousPositivePoint*=2;
					previousPopTime = time;
					popCount++;
				}
				else{//if you reacted in more than half a second
					currentScore+=2;
					previousPositivePoint = 2;
					previousPopTime = time;
					popCount++;
				}
			}
			else //if its not the same ball
			{
				currentScore++;
				previousPositivePoint = 1;
				previousPopTime = time;
				previousColor = ball.get_color();
				popCount++;
			}
		}
		else //if popping bubble for the first time
		{
			currentScore++;
			previousPositivePoint = 1;
			previousPopTime = time;
			previousColor = ball.get_color();
			popCount++;
		}
	}
	
	public int get_currentScore(){
		return this.currentScore;
	}
	
	public int get_previousPoint(){
		return this.previousPositivePoint;
	}
	
	public int get_popCount(){
		return this.popCount;
	}

}

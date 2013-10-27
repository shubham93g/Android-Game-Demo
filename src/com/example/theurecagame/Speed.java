package com.example.theurecagame;

public class Speed {
	private int xSpeed;
	private int ySpeed;
	private int xDirection;
	private int yDirection;
	
	
	public Speed(int xSpeed,int ySpeed,int xDirection, int yDirection){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.xDirection = xDirection;
		this.yDirection = yDirection;
	}
	
	public int get_xSpeed(){
		return this.xSpeed;
	}
	
	public int get_ySpeed(){
		return this.ySpeed;
	}
	
	public void set_xSpeed(int xSpeed){
		this.xSpeed = xSpeed;
	}
	
	public void set_ySpeed(int ySpeed){
		this.ySpeed = ySpeed;
	}
	
	public int get_xDirection(){
		return this.xDirection;
	}
	
	public int get_yDirection(){
		return this.yDirection;
	}
	
	public void set_xDirection(int xDirection){
		this.xDirection = xDirection;
	}
	
	public void set_yDirection(int yDirection){
		this.yDirection = yDirection;
	}
}

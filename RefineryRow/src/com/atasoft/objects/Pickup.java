package com.atasoft.objects;

import com.badlogic.gdx.math.*;

public class Pickup extends Vehicle {

	//acceleration rate
	public final static float ACCEL = 2.5f;
	//rate of turn
	public final static float TURN_RATE = 80;
	//fast driving speed
	public final static float FAST_SPEED = 3;
	//slow driving speed
	public final static float SLOW_SPEED = 1;
	//if targer is closer than this (squared for performance) then close enough
	public final static float CLOSE_DISTSQ = 0.25f * 0.25f;
	//if target is further than this go fast
	public final static float SLOW_DISTSQ = 2 * 2;
	//if bearing is less than this stop trying to turn
	public final static float TURN_THRESH = 0.1f;
	//If bearing is greater than this slow for turn
	public final static float TURN_SLOW_MIN = 10;
	
	public Pickup(Vector2 spawnPos, float spawnBear){
		this.size = new Vector2(300, 300); 
		this.vehGridSize = new Vector2(0.75f, 0.75f);
		this.position = spawnPos;
		this.heading = spawnBear;
		this.vehType = TYPE_PICKUP;
	}
	
	public static float[] getVehStats() {
		float[] vStats = {ACCEL, TURN_RATE, FAST_SPEED, SLOW_SPEED, CLOSE_DISTSQ, SLOW_DISTSQ, TURN_THRESH, TURN_SLOW_MIN};
		return vStats;
	}
}


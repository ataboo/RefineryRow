package com.atasoft.objects;

import com.atasoft.objects.*;
import com.badlogic.gdx.math.*;

public class Pickup extends Vehicle {
	public static final int vehType = TYPE_PICKUP;
	
	public Pickup(Vector2 spawnPos, float spawnBear){
		this.size = new Vector2(300, 300); 
		this.vehGridSize = new Vector2(0.75f, 0.75f);
		this.position = spawnPos;
		this.heading = spawnBear;
	}
}


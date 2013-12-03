package com.atasoft.objects;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Vehicle {
	Texture bodyTex;
	public Vector2 position;
	public float heading;
	public Sprite bodySprite;
	public Vector2 size;
	
	public class Pickup extends Vehicle {
		public Pickup(Vector2 spawnPos, float spawnBear){
			size = new Vector2(300, 300);
			position = spawnPos;
			heading = spawnBear;
		}
	}
		
		
	public void facePoint(Vector2 target){
		heading = position.sub(target).angle();
		return;
	}
	public void setHeading(float newHead) {
		while(newHead >= 360) newHead -= 360; //don't judge me.
		while(newHead < 0) heading += 360;
		heading = newHead;
		return;
	}
}
	


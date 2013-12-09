package com.atasoft.objects;
import com.atasoft.helpers.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;

public class Vehicle {
	public static final int TYPE_EMPTY = 0; //Used as place holder in vehicle index
	public static final int TYPE_PICKUP = 1;
	//yada yada
	
	//----------changed externally
	public int vehType;
	public Vector2 position;  //should change a bunch of these to methods
	public float heading;
	public Vector2 size;
	public Vector2 vehGridSize;
	public float speed = 0;
	public Vector2 targetPos;
	public boolean isMoving;
	
	//----------getter/setter action
	private boolean isSelected = false;
	private int vinNumber;
	private Vector3 sPos;
	
	public Vehicle() {
		this.vehType = TYPE_EMPTY;
	}
	
	//Set Position and Heading.  Called by Vehicle Manager for spawn / movement
	public void setPosHead(Vector2 newPos, float newHeading) {
		this.heading = AtaMathUtils.cageEuler(newHeading);
		this.position = newPos;
	}
	
	//Set on spawn by vehicle manager
	public void setVinNumber(int vin) {
		this.vinNumber = vin;
	}
	
	public int getVinNumber() {
		return vinNumber;
	}
	
	public void setSPos(Vector3 sPos) {
		this.sPos = sPos;
	}
	
	public Vector3 getSPos() {
		return sPos;
	}
	
	public Vector2 getSizeV() {
		return size;
	}
	
	public Vector2 getGridPos() {
		return position;
	}
	
	public Vector2 getGridSize() {
		return vehGridSize;
	}
	
	private boolean colChange = false;
	public void changeColour(boolean change) {
		this.colChange = change;
	}
	
	public void die() {
		// other dieing stuff
		this.dispose();
	}
	
	public void dispose(){
		// call vehrenderer dispose
	};
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void select(boolean select) {
		this.isSelected = select;
	}
	
	public float[] getVehStats(int type) {
		float[] vStats;
		switch(type){
			case TYPE_PICKUP:
				vStats = Pickup.getVehStats();
				break;
			default:
				vStats = null;
		}
	return vStats;
	}
}
	


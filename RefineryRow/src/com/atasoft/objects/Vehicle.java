package com.atasoft.objects;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import src.com.atasoft.helpers.*;

public class Vehicle {
	public static final int TYPE_PICKUP = 0;
	//yada yada
	
	public int vehType;
	public float TURN_RATE;
	public float CLOSE_HEAD;
	public float CLOSE_DIST;
	public Texture bodyTex;
	public Vector2 position;  //should change a bunch of these to methods
	public float heading;
	public Vector2 size;
	private int vinNumber;
	private int arrIndex;
	private Vector3 sPos;
	public Vector2 vehGridSize;
	private boolean isSelected = false;
	public float speed = 0;
	public Vector2 targetPos;
	public boolean isMoving;
	
	public void setPosHead(Vector2 newPos, float newHeading) {
		this.heading = AtaMathUtils.cageEuler(newHeading);
		this.position = newPos;
	}
	
	public void facePoint(Vector2 target){
		heading = position.sub(target).angle();
		return;
	}
	
	public void setVinNumber(int vin) {
		this.vinNumber = vin;
	}
	
	public float getHeading() {
		return heading;
	}
	
	public int getVinNumber() {
		return vinNumber;
	}
	
	public void setArrIndex(int ind) {
		this.arrIndex = ind;
	}
	
	public int getArrIndex() {
		return arrIndex;
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
	
	private boolean spinning = true;
	public void spinning(boolean spin){
		this.spinning = spin;
	}
	
	public boolean isSpinning(){
		return spinning;
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
}
	


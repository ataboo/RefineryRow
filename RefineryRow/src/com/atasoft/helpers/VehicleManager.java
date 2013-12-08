package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.atasoft.helpers.*;

public class VehicleManager {
	public static final int TYPE_PICKUP = 0;
	// digger, blah...
	
	private RenderVehicles renderVehicles;
	private Array<Pickup> pickups;
	private int vinCount;
	private Vector2 screenSize;
	private Vehicle selectedV;
	
	public VehicleManager(RenderVehicles renderVehicles) {
		this.renderVehicles = renderVehicles;
		this.screenSize = renderVehicles.getScreenSize();
		pickups = new Array<Pickup>();
		vinCount = 0;
	}
	
	public int addVehicle(int type, Vector2 pos, float bearing) {
		switch(type) {
			case TYPE_PICKUP:
				makePickup(pos, bearing);
				break;
			default:
				return -1;
		}
		return 0;
	}
	
	public boolean removePickup(int vinNumber) {
		for(Vehicle p: pickups) {
			if (p.getVinNumber() == vinNumber) { 
				pickups.removeIndex(p.getArrIndex());
				p.die();
				return true;
			}
		}
		return false;
	}
	
	private int makePickup(Vector2 pos, float bearing) {
		Pickup pickup = new Pickup(pos, bearing);
		pickups.add(pickup);
		pickup.setArrIndex(0);
		pickup.setVinNumber(vinCount);
		vinCount++;
		return pickup.getVinNumber();
	}
	
	public void update(float delta) {
		for(Pickup p: pickups) {
			if(p.vehType == Pickup.TYPE_PICKUP) {
				//if(p.isSpinning()) truckSpin(delta, p);
				movePickup(p, delta);
				renderVehicles.renderPickup(p);	
			}
		}
	}

	//move me to movement manager or pickup class	
	private void truckSpin(float delta, Pickup truck) {
		float SPIN_SPEED = 80;
		truck.heading = AtaMathUtils.cageEuler(truck.heading + delta * SPIN_SPEED);
		//Gdx.app.log("atlas", String.format("heading %2f", testTruck.heading));
	}
	
	public Vehicle checkOverlap(Vector2 gridClick) {
		for(Pickup p: pickups) {
			if(overlapVehicle(gridClick, 
			    p.getGridPos(), 
				p.getGridSize())) return p;
		}
		return null;
	}
	
	private boolean overlapVehicle(Vector2 pt, Vector2 pos, Vector2 size) {
		Vector2 tempPos = pos.cpy();
		//tempPos.add(new Vector2(0, screenSize.y / 2));
		tempPos.sub(pt);
		Gdx.app.log("poscheck", String.format("tempPos: %.2f %.2f", tempPos.x, tempPos.y));
		Gdx.app.log("poscheck", String.format("pos: %2f %2f", pos.x, pos.y));
		//Gdx.app.log("poscheck", String.format("size: %2f %2f", size.x, size.y));
		//Gdx.app.log("poscheck", String.format("pt: %.2f %.2f", pt.x, pt.y));
		//Gdx.app.log("poscheck", String.format("screen: %.2f %.2f", screenSize.x, screenSize.y));
		if(Math.abs(tempPos.x) < size.x / 2 &&
			Math.abs(tempPos.y) < size.y / 2) return true;
		return false;
	}

	public void selectVehicle(Vehicle v) {
		if(selectedV == null) selectedV = v;
		if (v != null) {
			selectedV.select(false);
			v.select(true);
			selectedV = v;
		} else {
			selectedV.select(false);
			selectedV = null;
		}
	}
	
	public Vehicle getSelectedV() {
		return selectedV;
	}
	
	private void movePickup(Pickup p, float delta) {
		if(p.isMoving) {
				//pathfinder will update target pos eventually 
				moveVehicle(p, delta);
		}
	}
	
	public void moveSelected(float x, float y){
		selectedV.targetPos = new Vector2(x, y);
		selectedV.isMoving = true;
	}
	
	private void moveVehicle(Vehicle v, float delta) {
		Vector2 targetCopy = v.targetPos.cpy();
		Vector2 pos = v.position;
		Vector2 headVect = new Vector2(1, 0).rotate(v.heading); //counterclockwise default
		Vector2 bearVect = targetCopy.sub(pos);
		float distSq = bearVect.len2();
		bearVect.nor();
		Gdx.app.log("vehManager", String.format("bearVect: %.2f, %.2f", bearVect.x, bearVect.y));
		
		
		float newHead = rotateTowards(v.heading, bearVect.angle(), delta * Pickup.TURN_RATE);
		float targSpeed = Pickup.SLOW_SPEED;
		
		if (v.speed < 0.01 && distSq < Pickup.CLOSE_DISTSQ) {
			v.speed = 0;
			v.isMoving = false;
			return;
		}
		if (AtlasGen.deltaEuler(newHead, v.heading) < AtlasGen.deltaEuler(bearVect.angle(), v.heading) ||
			distSq < Pickup.SLOW_DISTSQ) {
			targSpeed = Pickup.SLOW_SPEED;
			if(distSq < Pickup.CLOSE_DISTSQ) targSpeed = 0;
			
		} else {
		    targSpeed = Pickup.FAST_SPEED;
			newHead = v.heading;
		}
		
		v.speed = AtaMathUtils.derpLerp(v.speed, targSpeed, delta * Pickup.ACCEL);
		
		Vector2 newPos = pos.cpy();
		newPos = moveTowards(newPos, headVect, delta * v.speed);
		v.setPosHead(newPos, newHead);
	}
	
	private Vector2 moveTowards(Vector2 pos, Vector2 head, float rate) {
		head.scl(rate);
		pos.add(head);
		return pos;
	}
	
	private float rotateTowards(float head, float bear, float rate){
		float dif = head - bear;
		int direction = 1;
		if((-180 > dif) || 
			(0 < dif && dif <= 180)) {
			direction = -1;
		}
		head += rate * direction;
		//Gdx.app.log("rotate", String.format("dif: %.2f, sign: %2d, bearing %.2f, rate %.2f, newHeading: %.2f", dif, direction, bear, rate, head));
		return AtlasGen.cageEuler(head);
	}
}

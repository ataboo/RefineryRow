package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class VehicleManager {
	public static final int TYPE_PICKUP = 0;
	// digger, blah...
	
	private RenderVehicles renderVehicles;
	private Array<Pickup> pickups;
	private int vinCount;
	private Vector2 screenSize;
	
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
				if(p.isSpinning()) truckSpin(delta, p);
				renderVehicles.renderPickup(p);	
			}
		}
	}

	//move me to movement manager or pickup class	
	private void truckSpin(float delta, Pickup truck) {
		float SPIN_SPEED = 80;
	 	truck.setHeading(truck.heading + delta * SPIN_SPEED);
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
}

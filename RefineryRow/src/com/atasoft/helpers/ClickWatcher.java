package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.badlogic.gdx.graphics.*;

public class ClickWatcher
{
	private VehicleManager vehicleManager;
	private CameraManager camManager;
	private Vector2 screenToGridPt;
	private OrthographicCamera cam;
	final static Plane xzPlane = new Plane(new Vector3(0,1,0), 0);
	
	public ClickWatcher(VehicleManager vehicleManager, CameraManager camManager) {
		this.vehicleManager = vehicleManager;
		this.camManager = camManager;
		this.cam = camManager.getCam();
	}
	
	public void clickCheck(int x, int y) {
		Gdx.app.log("ClickWatcher", String.format("x, y: %2d, %2d", x, y));
		screenToGridPt = camManager.getClickOnPlane(new Vector2(x, y));
		Gdx.app.log("ClickWatcher", String.format("screentoGridPt: %2f, %2f", screenToGridPt.x, screenToGridPt.y));
		Vehicle v  = vehicleManager.checkOverlap(screenToGridPt);
		if(v != null) {
			v.spinning(!v.isSpinning());
			//RenderUI.clicked(v);
		}
		
	}
	
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	
	public boolean touchDragged (int x, int y, int pointer) { //relayed by inputhandeler
		 cam.update();
		 Ray pickRay = cam.getPickRay(x, y);
		 Intersector.intersectRayPlane(pickRay, xzPlane, curr);

		 if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
		 pickRay = cam.getPickRay(last.x, last.y);
		 Intersector.intersectRayPlane(pickRay, xzPlane, delta);			
		 delta.sub(curr);
		 cam.position.add(delta.x, delta.y, delta.z);
		 }
		 last.set(x, y, 0);
		 
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {  //call relayed by inputhandler
		last.set(-1, -1, -1);
		return false;
	}
}

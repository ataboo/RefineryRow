package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.badlogic.gdx.graphics.*;

public class ClickWatcher
{
	private RenderUI renderUI;
	private VehicleManager vehicleManager;
	private CameraManager camManager;
	private Vector2 screenToGridPt;
	private OrthographicCamera cam;
	final static Plane xzPlane = new Plane(new Vector3(0,1,0), 0);
	
	final static int LIFTED = 0;
	final static int HOLDING = 1;
	final static int POPPING = 2;
	final static int SELECTING = 3;
	final static int SCROLLING = 4;
	private int clickState = LIFTED;
	
	private float holdTimer = 0f;
	public static float CLICK_SQ = 0.005f;
	public static float LONGCLICK = 0.3f;
	private float screenHeight;
	private Vector2 touchSpot = new Vector2();
	private Vehicle selectedV;
	
	public ClickWatcher(VehicleManager vehicleManager, CameraManager camManager, RenderUI renderUI) {
		this.vehicleManager = vehicleManager;
		this.camManager = camManager;
		this.renderUI = renderUI;
		this.cam = camManager.getCam();
		this.screenHeight = camManager.getScreenSize().y;
	}
	
	public void clickCheck(int x, int y) {
		//Gdx.app.log("ClickWatcher", String.format("x, y: %2d, %2d", x, y));
		screenToGridPt = camManager.getClickOnPlane(new Vector2(x, y));
		//Gdx.app.log("ClickWatcher", String.format("screentoGridPt: %2f, %2f", screenToGridPt.x, screenToGridPt.y));
		Vehicle v  = vehicleManager.checkOverlap(screenToGridPt);
		if(v != null) {
			selectedV = v;
			//v.spinning(!v.isSpinning());
			//renderUI.popPickup(new Vector2(x, camManager.getScreenSize().y - y));
		}
		clickState = HOLDING;
		holdTimer = 0;
		touchSpot.set(x, screenHeight - y);
	}
	
	
	public boolean touchDragged (int x, int y, int pointer) { //relayed by inputhandeler 
		switch(clickState) {
			case SCROLLING:
				scrollScreen(x, y);
				break;
			case HOLDING:
				scrollScreen(x, y);
				break;
			case POPPING:
				selectPop(x, y);
				break;
			default:
				break;
		}
		return false;	 
	}

	public boolean touchUp(int x, int y, int pointer, int button) {  //call relayed by inputhandler
		switch(clickState) {
			case POPPING:
				renderUI.runPop((int) screenToGridPt.x, (int) screenToGridPt.y);
				break;
			case HOLDING:
				selectItem(x, y);
				break;
			default:
				break;
		}
		last.set(-1, -1, -1);
		clickState = LIFTED;
		
		return false;
	}
	
	public void update(float deltaT) {
		switch(clickState){
			case HOLDING:
				longClick(deltaT);
				break;
			default:
				break;
		}
	}
	
	private void longClick(float deltaT) {
		holdTimer += deltaT;
		if(holdTimer > LONGCLICK) {
			selectedV = vehicleManager.getSelectedV();
			if(selectedV != null) {
				switch(selectedV.vehType) {
					case Pickup.TYPE_PICKUP:
						renderUI.popVehicle(touchSpot, vehicleManager.getSelectedV());
						break;
				}
				clickState = POPPING;
			}
		}
	}
	
	private void selectPop(int x, int y) {
		renderUI.highlightPop(x, (int) screenHeight - y);
	}
	
	private void selectItem(int x, int y) {
		if(selectedV != null) vehicleManager.selectVehicle(selectedV);
	}
	
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	private void scrollScreen(int x, int y) {
		cam.update();
		Ray pickRay = cam.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, xzPlane, curr);

		if(last.z != -1) {
			pickRay = cam.getPickRay(last.x, last.y);
			Intersector.intersectRayPlane(pickRay, xzPlane, delta);			
			delta.sub(curr);
			cam.position.add(delta.x, delta.y, delta.z);
		}
		last.set(x, y, 0);
		//Gdx.app.log("clickWatcher", Float.toString(delta.len2()));
		
		//if delta length is over the "click square" threshold set scroll mode.
		if(delta.len2() > CLICK_SQ) {
			renderUI.highlightPop(true);
			selectedV = null;
			clickState = SCROLLING;
		}		 
	}
}

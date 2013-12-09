package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.badlogic.gdx.graphics.*;

//---------------------------------------------------------------------------------
/* ClickWatcher has its methods called by the input manager and handles the logic
for UI interaction, then relays to the other managers and renderers. "PopButtons"
can be spawned for a type of wheel menu for object interaction. */
//---------------------------------------------------------------------------------
public class ClickWatcher
{
	private RenderUI renderUI;
	private VehicleManager vehicleManager;
	private CameraManager camManager;
	private Vector2 screenToGridPt;
	private OrthographicCamera cam;
	final static Plane xzPlane = new Plane(new Vector3(0,1,0), 0);
	
	final static int LIFTED = 0;  //No touch down
	final static int HOLDING = 1;  //Holding with long click timer but not exclusively scrolling
	final static int POPPING = 2;  //PopButtons up to select
	final static int SELECTING = 3;
	final static int SCROLLING = 4;  //Scrolling view
	private int clickState = LIFTED;  //current state of touch events used as a switch
	
	private float holdTimer = 0f;  //counts up to check for long click
	public static float DRAG_THRESHSQ = 0.005f;  //Distance view is scrolled to disable long click (squared for performance)
	public static float LONGCLICK = 0.3f;  //delay in seconds for long click
	private float screenHeight;
	private Vector2 touchSpot = new Vector2();
	private Vehicle selectedV;  //selected vehicle; set on touchdown
	
	public ClickWatcher(VehicleManager vehicleManager, CameraManager camManager, RenderUI renderUI) {
		this.vehicleManager = vehicleManager;
		this.camManager = camManager;
		this.renderUI = renderUI;
		this.cam = camManager.getCam();
		this.screenHeight = camManager.getScreenSize().y;
	}
	
	//Basically touchDown gotta rename eventually
	public void clickCheck(int x, int y) {
		//Gdx.app.log("ClickWatcher", String.format("x, y: %2d, %2d", x, y));
		screenToGridPt = camManager.getClickOnPlane(new Vector2(x, y));
		//Gdx.app.log("ClickWatcher", String.format("screentoGridPt: %2f, %2f", screenToGridPt.x, screenToGridPt.y));
		Vehicle v  = vehicleManager.checkOverlap(screenToGridPt);
		selectedV = v;
		clickState = HOLDING;
		holdTimer = 0;
		touchSpot.set(x, screenHeight - y);
	}
	
	
	public boolean touchDragged (int x, int y, int pointer) { //relayed by inputhandeler 
		switch(clickState) {
			case SCROLLING:  //scrolling view around
				scrollScreen(x, y);
				break;
			case HOLDING:  //counting up long click and drag limit not exceded
				scrollScreen(x, y);
				break;
			case POPPING:  //popping up popButtons for object
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
				checkLongClick(deltaT);
				break;
			default:
				break;
		}
	}
	
	private void checkLongClick(float deltaT) {
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
		
		//if delta length is over the "dragging threshold square", set scroll mode.
		if(delta.len2() > DRAG_THRESHSQ) {
			renderUI.highlightPop(true);
			selectedV = null;
			clickState = SCROLLING;
		}		 
	}
}

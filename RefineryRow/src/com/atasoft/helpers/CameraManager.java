package com.atasoft.helpers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class CameraManager
{
	private static int WIDTH;
	private static int HEIGHT;
	private static Vector2 screenSize;
	public OrthographicCamera cam;
	public Matrix4 isoMatrix = new Matrix4();
	public Matrix4 uiMatrix = new Matrix4();
	public Matrix4 combinedMatrix = new Matrix4();
	
	public CameraManager(float x, float z) {
		setupCam(x, z);
		
	}
	
	private void setupCam(float x, float z) {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		screenSize = new Vector2(WIDTH, HEIGHT);
		this.cam = new OrthographicCamera(10, 10 * (HEIGHT / (float)WIDTH));		
		cam.position.set(x, 5, z);
		cam.direction.set(-1, -1, -1);
		cam.near = 1;
		cam.far = 40;
		
		isoMatrix.setToRotation(new Vector3(1, 0, 0), 90);
		uiMatrix = cam.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT);
		
		
	}
	
	public Vector2 getScreenSize(){
		return screenSize;
	}
	
	public OrthographicCamera getCam() {
		return cam;
	}
	
	public void update() {
		cam.update();
		combinedMatrix = cam.combined;
		uiMatrix = cam.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT);
		
		return;
	}
		
	public void moveTo(float x, float z) {
		//MAP_SIZE[0] / 2, 5, (MAP_SIZE[1] / 2) + 5
		cam.position.set(x, 5, z);
	}
	
	public Vector3 getScreenPos(Vector3 wPos) {
		cam.project(wPos);
		return wPos;
	}
	
	private Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	private Vector3 intersection = new Vector3();
	public Vector2 getClickOnPlane(Vector2 screen){
		Ray pickRay = cam.getPickRay(screen.x, screen.y);
		Gdx.app.log("CamManager", String.format("screen.x, y %2f, %2f", screen.x, screen.y));
		Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
		if(intersection != null) Gdx.app.log("intersect", String.format("intersection: %.2f %.2f", intersection.x, intersection.z));
		return (new Vector2(intersection.x, intersection.z));
	}
	
	public Matrix4 getUIMatrix() {
		return uiMatrix;
	}
	
	public Matrix4 getIsoMatrix() {
		return isoMatrix;	
	}
	
	public Matrix4 getCombinedMatrix() {
		return combinedMatrix;
	}
	
}

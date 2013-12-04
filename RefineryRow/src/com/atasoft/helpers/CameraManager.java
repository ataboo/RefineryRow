package com.atasoft.helpers;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class CameraManager
{
	public static int WIDTH;
	public static int HEIGHT;
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

		this.cam = new OrthographicCamera(10, 10 * (HEIGHT / (float)WIDTH));		
		cam.position.set(x, 5, z);
		cam.direction.set(-1, -1, -1);
		cam.near = 1;
		cam.far = 40;
		
		isoMatrix.setToRotation(new Vector3(1, 0, 0), 90);
		uiMatrix = cam.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT);
		
		
	}
	
	public Camera getCam() {
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

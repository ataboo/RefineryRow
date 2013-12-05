package com.atasoft.screens;

import com.atasoft.helpers.*;
import com.atasoft.objects.*;
import com.atasoft.refineryrow.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class IsoScreen implements Screen {
	RefineryAct game;
	public static final int[] MAP_SIZE = {32, 32};	
	InputHandler inHandle;
	AtlasGen atlasGen;
	CameraManager camManager;
	RenderMap renderMap;
	VehicleManager vehicleManager;
	ClickWatcher clickWatcher;
	RenderVehicles renderVehicles;
	
	public IsoScreen (RefineryAct game) {
		this.game = game;
		camManager = new CameraManager(MAP_SIZE[0] / 2 + 5, MAP_SIZE[1] / 2 + 5);
		atlasGen = new AtlasGen();
		renderMap = new RenderMap(atlasGen, camManager);
		renderVehicles = new RenderVehicles(atlasGen, camManager);
		vehicleManager = new VehicleManager(renderVehicles);
		clickWatcher = new ClickWatcher(vehicleManager, camManager);
		
		setupTestTruck();	
		InputHandler inHandle = new InputHandler(this, clickWatcher);
		Gdx.input.setInputProcessor(inHandle);
	}
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camManager.update();
		renderMap.update();
		vehicleManager.update(delta);
		//updateTimeToaster(delta); // Logging Camera Position
		//checkTileTouched();
	}
	
	private void setupTestTruck() {
		for (int i = 0; i < 5; i++){
			vehicleManager.addVehicle(vehicleManager.TYPE_PICKUP, 
				new Vector2(10 + i * 2,16), 0);
		}
		
		/*
		if(tempVin != -1) {
			///blahblahblah add logic
		}
		*/
	}
	

	float time = 0;
	private static float WAIT_TIME = 1f;
	private void updateTimeToaster(float delta){  //Sounds like some sweet scifi
		time += delta;
		if (time >= WAIT_TIME) {
			//do stuff
			time -= WAIT_TIME;
	    }
	}
	
	/*
	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;
	
	private void checkTileTouched() {  //call relayed by inputhandler
		if(Gdx.input.justTouched()) {
			Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
			int x = (int)intersection.x;
			int z = (int)intersection.z;
			if(x >= 0 && x < MAP_SIZE && z >= 0 && z < MAP_SIZE) {
				if(lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
				Sprite sprite = sprites[x][z];
				sprite.setColor(1, 0, 0, 1);
				Gdx.app.log("tile select", String.format("x: %2d z: %2d", x, z));
				lastSelectedTile = sprite;
			}
		}
	}
	*/
	
	
	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		atlasGen.dispose();
		renderMap.dispose();
		// camManager.dispose();  //doesn't need it?
	}

}

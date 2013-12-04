package com.atasoft.screens;

import com.atasoft.helpers.*;
import com.atasoft.objects.*;
import com.atasoft.refineryrow.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.atasoft.helpers.*;

public class GameLoopScreen implements Screen {
	RefineryAct game;
	public static final int[] MAP_SIZE = {32, 32};	
	InputHandler inHandle;
	AtlasGen atlasGen;
	CameraManager camManager;
	RenderMap renderMap;
	
	public GameLoopScreen (RefineryAct game) {
		this.game = game;
		camManager = new CameraManager(MAP_SIZE[0] / 2 + 5, MAP_SIZE[1] / 2 + 5);
		atlasGen = new AtlasGen();
		renderMap = new RenderMap(atlasGen, camManager);
		setupTestTruck();	
		InputHandler inHandle = new InputHandler(this);
		Gdx.input.setInputProcessor(inHandle);
	}
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camManager.update();
		renderMap.update();
		
		updateVehicles(delta);
		//updateTimeToaster(delta); // Logging Camera Position
		//checkTileTouched();
	}
	
	
	private void updateVehicles(float delta) {
		// todo for vehicle list blah
		float SPIN_SPEED = 80;
		Sprite truckSprite = atlasGen.getVehBody("pickup", testTruck.heading);
		
		truckSprite.setSize(testTruck.size.x, testTruck.size.y);
		
		
		Vector3 wPos = camManager.getScreenPos(new Vector3(testTruck.position.x, 0, testTruck.position.y));
		truckSprite.setPosition(wPos.x - testTruck.size.x / 2, wPos.y - testTruck.size.y / 2);
		truckBatch.setProjectionMatrix(camManager.getUIMatrix());
		truckBatch.begin();
		truckSprite.draw(truckBatch);
		truckBatch.end();
		
		testTruck.setHeading(testTruck.heading + delta * SPIN_SPEED);
		
		//Gdx.app.log("atlas", String.format("heading %2f", testTruck.heading));
	}
	
	
	
	Vehicle testTruck;
	SpriteBatch truckBatch;
	private void setupTestTruck() { // added test truck
		testTruck = new Vehicle().new Pickup(new Vector2(16,16), 0);
		truckBatch = new SpriteBatch();	
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
	
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	public boolean touchDragged (int x, int y, int pointer) { //relayed by inputhandeler
		/*
		Ray pickRay = cam.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, xzPlane, curr);

		if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
			pickRay = cam.getPickRay(last.x, last.y);
			Intersector.intersectRayPlane(pickRay, xzPlane, delta);			
			delta.sub(curr);
			cam.position.add(delta.x, delta.y, delta.z);
		}
		last.set(x, y, 0);
		*/
		return false;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {  //call relayed by inputhandler
		//last.set(-1, -1, -1);
		return false;
	}
	

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

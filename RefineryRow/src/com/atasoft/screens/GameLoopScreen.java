package com.atasoft.screens;

import com.atasoft.refineryrow.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;
import com.atasoft.objects.*;

public class GameLoopScreen implements Screen {
	RefineryAct game;
	public static final int MAP_SIZE = 32;	
	InputHandler inHandle;
	AtlasGen atlasGen;
	
	public GameLoopScreen (RefineryAct game) {
		this.game = game;
		setupCam();
		setupAtlas();
		setupTiles();
		
		InputHandler inHandle = new InputHandler(this);
		Gdx.input.setInputProcessor(inHandle);
	}
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		updateSpriteBatch(tileBatches[0], coffeeSprites);
		updateSpriteBatch(tileBatches[1], appleSprites);
		updateSpriteBatch(tileBatches[2], batSprites);
		updateVehicles(delta);
		
		updateTimeToaster(delta); // Logging Camera Position
		//checkTileTouched();
	}
	OrthographicCamera cam;
	int WIDTH;
	int HEIGHT;
	private void setupCam() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(10, 10 * (HEIGHT / (float)WIDTH));		
		cam.position.set(MAP_SIZE / 2, 5, (MAP_SIZE / 2) + 5);
		cam.direction.set(-1, -1, -1);
		cam.near = 1;
		cam.far = 40;		
	}
	
	private void updateVehicles(float delta) {
		// todo for vehicle list blah
		float SPIN_SPEED = 80;
		Matrix4 uiMatrix = cam.combined.cpy();
		uiMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT);
		Sprite truckSprite = atlasGen.getVehBody("pickup", testTruck.heading);
		
		truckSprite.setSize(testTruck.size.x, testTruck.size.y);
		Vector3 wPos = new Vector3(testTruck.position.x, 0, testTruck.position.y);
		cam.project(wPos);
		truckSprite.setPosition(wPos.x - testTruck.size.x / 2, wPos.y - testTruck.size.y / 2);
		truckBatch.setProjectionMatrix(uiMatrix);
		truckBatch.begin();
		truckSprite.draw(truckBatch);
		truckBatch.end();
		
		testTruck.setHeading(testTruck.heading + delta * SPIN_SPEED);
		
		//Gdx.app.log("atlas", String.format("heading %2f", testTruck.heading));
	}
	
	public final static int TILE_COUNT = 3;
	final int[][] tileType = new int[MAP_SIZE][MAP_SIZE];
	final Sprite[][] coffeeSprites = new Sprite[MAP_SIZE][MAP_SIZE];
	final Sprite[][] appleSprites = new Sprite[MAP_SIZE][MAP_SIZE];
	final Sprite[][] batSprites = new Sprite[MAP_SIZE][MAP_SIZE];
	final Matrix4 matrix = new Matrix4();	
	final SpriteBatch[] tileBatches = new SpriteBatch[TILE_COUNT];
	Texture[] tileTex = new Texture[TILE_COUNT];
	private void setupTiles() {
		tileTex[0] = new Texture(Gdx.files.internal("data/coffeecup.png"));
		tileTex[1] = new Texture(Gdx.files.internal("data/apple.png"));
		tileTex[2] = new Texture(Gdx.files.internal("data/battery.png"));
		
		tileBatches[0] = new SpriteBatch();
		tileBatches[1] = new SpriteBatch();
		tileBatches[2] = new SpriteBatch();
		
		MapGenerator tileMap = new MapGenerator();
		double[][] mapArr = trimMap(tileMap.makeMap(MAP_SIZE + 1));
		
		matrix.setToRotation(new Vector3(1, 0, 0), 90);

		for(int i = 0; i < MAP_SIZE; i++) {
			for(int j = 0; j < MAP_SIZE; j++){
				double mapVal = mapArr[i][j] / 100;
				//Gdx.app.log("MapVal", String.format("%2f", mapVal));
				if(mapVal < 3) {
					coffeeSprites[i][j] = setSprite(i, j, 0);
				} else {
					if(mapVal > 7) {
						appleSprites[i][j] = setSprite(i, j, 1);
						
					} else {
						batSprites[i][j] = setSprite(i, j, 2);
					}
				}
				
				
				/*
				coffeeSprites[i][j] = setSprite(i,j,0);
				float colVal = (float) mapArr[i][j] / 1000;
				coffeeSprites[i][j].setColor(colVal, 1, 1 - colVal, 1);
				*/
			}
		}
	}
	
	private double[][] trimMap(double[][] inArr) {
		int len = inArr[0].length - 1;
		double[][] retArr = new double[len][len];
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				retArr[i][j] = inArr[i][j];
			}
		}
		return retArr;
	}
	
	Vehicle testTruck;
	SpriteBatch truckBatch;
	private void setupAtlas() { // added test truck
		atlasGen = new AtlasGen();
		
		testTruck = new Vehicle().new Pickup(new Vector2(16,16), 0);
		truckBatch = new SpriteBatch();
		
		
	}
	
	private Sprite setSprite(int x, int y, int texInd) {
		Sprite retSprite = new Sprite(tileTex[texInd]);
		retSprite.setPosition(x,y);
		retSprite.setSize(1, 1);
		retSprite.flip(false, true);
		
		return retSprite;
	}
	
	float time = 0;
	private static float WAIT_TIME = 1f;
	
	private void updateTimeToaster(float delta){  //Sounds like some sweet scifi
		time += delta;
		if (time >= WAIT_TIME) {
			//Gdx.app.log("RefRow Debug", String.format("delta = %2f, heading = %2f", delta, testTruck.heading));
			//testTruck.setHeading(testTruck.heading + 3);
			time -= WAIT_TIME;
	    }
	}
	
	private void updateSpriteBatch(SpriteBatch batch, Sprite[][] sprites) {		
		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(matrix);
		batch.begin();
		for(int z = 0; z < MAP_SIZE; z++) {
			for(int x = 0; x < MAP_SIZE; x++) {
				if(sprites[x][z] != null) {
					sprites[x][z].draw(batch);
				}
			}
		}
		batch.end();
	}
	
	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;
	
	/*
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
		for(int i = 0; i < tileTex.length; i++){
			tileTex[i].dispose();
		}
		
		atlasGen.dispose();
		
	}

}

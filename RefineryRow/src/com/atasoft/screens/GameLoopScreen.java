package com.atasoft.screens;

import com.atasoft.refineryrow.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class GameLoopScreen implements Screen {
	RefineryAct game;
	Texture texture;
	OrthographicCamera cam;
	SpriteBatch batch;	
	final Sprite[][] sprites = new Sprite[10][10];
	final Matrix4 matrix = new Matrix4();	
	InputHandler inHandle;
	
	public GameLoopScreen (RefineryAct game) {
		this.game = game;
		

		texture = new Texture(Gdx.files.internal("data/coffeecup.png"));		
		cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));			
		cam.position.set(5, 5, 10);
		cam.direction.set(-1, -1, -1);
		cam.near = 1;
		cam.far = 100;		
		matrix.setToRotation(new Vector3(1, 0, 0), 90);

		for(int z = 0; z < 10; z++) {
			for(int x = 0; x < 10; x++) {
				sprites[x][z] = new Sprite(texture);
				sprites[x][z].setPosition(x,z);
				sprites[x][z].setSize(1, 1);
			}
		}
		batch = new SpriteBatch();
		InputHandler inHandle = new InputHandler(this);
		Gdx.input.setInputProcessor(inHandle);
		
		
	}
	
		
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.update();		

		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(matrix);
		batch.begin();
		for(int z = 0; z < 10; z++) {
			for(int x = 0; x < 10; x++) {
				sprites[x][z].draw(batch);
			}
		}
		batch.end();

		checkTileTouched();
	}
	
	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;
	
	private void checkTileTouched() {  //call relayed by inputhandler
		if(Gdx.input.justTouched()) {
			Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
			int x = (int)intersection.x;
			int z = (int)intersection.z;
			if(x >= 0 && x < 10 && z >= 0 && z < 10) {
				if(lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
				Sprite sprite = sprites[x][z];
				sprite.setColor(1, 0, 0, 1);
				lastSelectedTile = sprite;
			}
		}
	}
	
	
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
		texture.dispose();
		
	}

}

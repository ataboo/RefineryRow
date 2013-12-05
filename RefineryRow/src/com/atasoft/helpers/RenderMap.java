package com.atasoft.helpers;

import com.atasoft.screens.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.atasoft.helpers.*;

public class RenderMap
{
	private MapGenerator mapGen;
	private AtlasGen atlasGen;  //todo: ground textures to atlas
	private CameraManager camManager;
	public final static int[] MAP_SIZE = IsoScreen.MAP_SIZE;
	public RenderMap(AtlasGen atlasGen, CameraManager camManager){
		this.camManager = camManager;
		this.atlasGen =  atlasGen;
		setupTiles();	
	}
	
	final int[][] tileType = new int[MAP_SIZE[0]][MAP_SIZE[1]];
	final Sprite[][] coffeeSprites = new Sprite[MAP_SIZE[0]][MAP_SIZE[1]];
	final Sprite[][] appleSprites = new Sprite[MAP_SIZE[0]][MAP_SIZE[1]];
	final Sprite[][] batSprites = new Sprite[MAP_SIZE[0]][MAP_SIZE[1]];
	
	final static int TILE_COUNT = 3;
	Texture[] tileTex = new Texture[TILE_COUNT];
	final SpriteBatch[] tileBatches = new SpriteBatch[TILE_COUNT];
	
	private void setupTiles() {
		mapGen = new MapGenerator();

		tileTex[0] = new Texture(Gdx.files.internal("data/coffeecup.png"));
		tileTex[1] = new Texture(Gdx.files.internal("data/apple.png"));
		tileTex[2] = new Texture(Gdx.files.internal("data/battery.png"));

		tileBatches[0] = new SpriteBatch();
		tileBatches[1] = new SpriteBatch();
		tileBatches[2] = new SpriteBatch();

		double[][] mapArr = trimMap(mapGen.makeMap(MAP_SIZE[0] + 1)); //diamond square makes has bad last row and column so it's trimmed

		for(int i = 0; i < MAP_SIZE[0]; i++) {
			for(int j = 0; j < MAP_SIZE[1]; j++){
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


				/*  for testing mapGen
				 coffeeSprites[i][j] = setSprite(i,j,0);
				 float colVal = (float) mapArr[i][j] / 1000;
				 coffeeSprites[i][j].setColor(colVal, 1, 1 - colVal, 1);
				 */
			}
		}
	}

	private double[][] trimMap(double[][] inArr) {  //trims off last row and column off mapgen because it's sub-par
		int len = inArr[0].length - 1;
		double[][] retArr = new double[len][len];
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				retArr[i][j] = inArr[i][j];
			}
		}
		return retArr;
	}
	
	private Sprite setSprite(int x, int y, int texInd) {  //helps setup
		Sprite retSprite = new Sprite(tileTex[texInd]);
		retSprite.setPosition(x,y);
		retSprite.setSize(1, 1);
		retSprite.flip(false, true);

		return retSprite;
	}
	
	//-------------Update stuff----------------
	
	public void update() {
		updateSpriteBatch(tileBatches[0], coffeeSprites);
		updateSpriteBatch(tileBatches[1], appleSprites);
		updateSpriteBatch(tileBatches[2], batSprites);
	}
	
	private void updateSpriteBatch(SpriteBatch batch, Sprite[][] sprites) {		
		batch.setProjectionMatrix(camManager.getCombinedMatrix());
		batch.setTransformMatrix(camManager.getIsoMatrix());
		batch.begin();
		for(int z = 0; z < MAP_SIZE[1]; z++) {
			for(int x = 0; x < MAP_SIZE[0]; x++) {
				if(sprites[x][z] != null) {
					sprites[x][z].draw(batch);
				}
			}
		}
		batch.end();
	}
	
	public void dispose() {
		for(Texture i: tileTex) {
			i.dispose();
		}
	}
	
	
	
}

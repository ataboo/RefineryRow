package com.atasoft.helpers;



import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class AtlasGen
 {
	Texture[] textures;
	private TextureAtlas atlas;
	
	private static final String PICKUP_TEX = "data/pickup/truckspin.png";
	private static final int[] PICKUP_FRAMES = {8, 8, 128};
	
	private static final String BUT_TEX = "data/ui/popbut.png";
	private static final int[] BUT_FRAMES = {2, 2, 128};
	
	public AtlasGen() {
		getTextures(new String[]{PICKUP_TEX, BUT_TEX});
		atlas = new TextureAtlas();
		addVehicleRegion(textures[0], PICKUP_FRAMES);
		addButRegions(textures[1]);
	}
	
	private void getTextures(String[] texString) {
		textures = new Texture[texString.length];
		
		for(int i = 0; i < texString.length; i++){
			textures[i] = new Texture(texString[i]);
		}
		return;
	}
	
	private void addVehicleRegion(Texture texMex, int[] frames) {
		//frames[column count, row count, size]
		for(int i = 0; i < frames[1]; i++){
			for(int j = 0; j < frames[0]; j++) {
				//atlas.addRegion(string, texture, x, y, wid, hei)
				String sequence = Integer.toString(j + i * frames[1]);
				atlas.addRegion("pickup," + sequence, texMex, j * 
				frames[2], i * frames[2], 
				frames[2], frames[2]);
			}
		}
	}
	
	private void addButRegions(Texture texMex) {
		// popbuttons = first 4 columns by first (LAST_TYPE) rows
		Vector2 size = PopButton.BOX_SIZE;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
			    atlas.addRegion("pop" + Integer.toString(i) + "," + j, 
				    texMex,
					j * (int) size.x, i * (int) size.y, 
					(int) size.x, (int) size.y);
			}
		}
		
		//other buttons
	}
	
	public Sprite getVehBody(String type, float angle) {
		float roof = 0;
		type = type.trim();
		angle = cageEuler(angle);
		if(type == "pickup") roof = PICKUP_FRAMES[0] * PICKUP_FRAMES[1];
		int spriteInd = Math.round((roof - 1) / (float)360 * angle);
		//Gdx.app.log("atlas", "type: " + type + "," + spriteInd);
		//Gdx.app.log("atlas", "spriteInd: " + spriteInd);
		//Gdx.app.log("truckspin", String.format("Angle: %.4f, Sprite Index: %2d", angle, spriteInd));
		Sprite retSprite = atlas.createSprite(type + "," + spriteInd);
		return retSprite;
	}
	//Takes a float and returns a proper float angle (0, 360)
	public static float cageEuler(float angle) {
		int intAngle = (int) angle;
		float remain = angle - intAngle;
		intAngle = intAngle % 360;
		if(intAngle < 0) {
			intAngle += 360;
			remain *= -1;
		}
		
		//Gdx.app.log("atlasGen/cageEuler", String.format("Turned angle %.2f", angle));
		angle = (float) intAngle + remain;
		//Gdx.app.log("atlasGen/cageEuler", String.format("Into %.2f", angle));
		return angle;
	}
	
	public static float deltaEuler(float alpha, float beta) {
		float dif;
		alpha = cageEuler(alpha);
		beta = cageEuler(beta);
		dif = Math.abs(alpha - beta);
		if(dif > 180) {
			dif = 360 - dif;
		}
		
		//Gdx.app.log("AtlasGen/deltaEuler", String.format("The difference between %.2f and %.2f is %.2f.", alpha, beta, dif));
		return dif;
	}
	
	public Sprite getPopBut(PopButton p, int state) {
		String lookup = p.getAtlasName() + 
		    "," + Integer.toString(state);
		return atlas.createSprite(lookup);
	}
	
	public void dispose(){
		for(Texture i : textures) {
			i.dispose();
		}
		atlas.dispose();
	}


}

package com.atasoft.helpers;



import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class AtlasGen
 {
	Texture[] textures;
	public TextureAtlas atlas;
	
	private static final String PICKUP_TEX = "data/pickup/truckspin.png";
	private static final int[] PICKUP_FRAMES = {8, 8, 128};
	
	public AtlasGen() {
		getTextures(new String[]{PICKUP_TEX});
		atlas = new TextureAtlas();
		addVehicleRegion(atlas, textures[0], PICKUP_FRAMES);
	}
	
	private void getTextures(String[] texString) {
		textures = new Texture[texString.length];
		
		for(int i = 0; i < texString.length; i++){
			textures[i] = new Texture(texString[i]);
		}
		return;
	}
	
	private TextureAtlas addVehicleRegion(TextureAtlas atlas, Texture texmex, int[] frames) {
		//frames[column count, row count, size]
		for(int i = 0; i < frames[1]; i++){
			for(int j = 0; j < frames[0]; j++) {
				//atlas.addRegion(string, texture, x, y, wid, hei)
				String sequence = Integer.toString(j + i * frames[1]);
				atlas.addRegion("pickup," + sequence, texmex, j * frames[2], i * frames[2], frames[2], frames[2]);
			}
		}

		return atlas;
	}
	
	public Sprite getVehBody(String type, float angle) {
		float roof = 0;
		type = type.trim();
		if(type == "pickup") roof = PICKUP_FRAMES[0] * PICKUP_FRAMES[1];
		int spriteInd = Math.round((roof - 1) / (float)360 * angle);
		//Gdx.app.log("atlas", "type: " + type + "," + spriteInd);
		//Gdx.app.log("atlas", "spriteInd: " + spriteInd);
		Gdx.app.log("truckspin", String.format("Angle: %.4f, Sprite Index: %2d", angle, spriteInd));
		Sprite retSprite = atlas.createSprite(type + "," + spriteInd);
		//retSprite.flip(false, true);
		return retSprite;
	}
	
	public void dispose(){
		for(Texture i : textures) {
			i.dispose();
		}
		atlas.dispose();
	}


}

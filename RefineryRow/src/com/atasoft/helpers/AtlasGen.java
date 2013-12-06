package com.atasoft.helpers;



import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import src.com.atasoft.objects.*;

public class AtlasGen
 {
	Texture[] textures;
	private TextureAtlas atlas;
	
	private static final String PICKUP_TEX = "data/pickup/truckspin.png";
	private static final int[] PICKUP_FRAMES = {8, 8, 128};
	
	private static final String BUT_TEX = "data/ui/buttons.png";
	private static final int[] BUT_FRAMES = {3, 12, 128};
	
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
		PopButton dummy = new PopButton();
		Vector2 size = dummy.BOX_SIZE;
		for(int i = 0; i < dummy.LAST_TYPE; i++) {
			for(int j = 0; j < 4; j++) {
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
		if(type == "pickup") roof = PICKUP_FRAMES[0] * PICKUP_FRAMES[1];
		int spriteInd = Math.round((roof - 1) / (float)360 * angle);
		//Gdx.app.log("atlas", "type: " + type + "," + spriteInd);
		//Gdx.app.log("atlas", "spriteInd: " + spriteInd);
		//Gdx.app.log("truckspin", String.format("Angle: %.4f, Sprite Index: %2d", angle, spriteInd));
		Sprite retSprite = atlas.createSprite(type + "," + spriteInd);
		return retSprite;
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

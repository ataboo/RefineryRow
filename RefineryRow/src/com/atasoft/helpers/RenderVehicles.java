package com.atasoft.helpers;

import com.atasoft.objects.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class RenderVehicles
{
	private SpriteBatch pickupBatch;
	private Vector2 screenSize;
	
	private CameraManager camManager;
	private AtlasGen atlasGen;
	
	
	public RenderVehicles(AtlasGen atlasGen, CameraManager camManager) {
		this.camManager = camManager;
		this.screenSize = camManager.getScreenSize();
		this.atlasGen = atlasGen;
		setupBatches();
	}
	
	private void setupBatches() {
		pickupBatch = new SpriteBatch();
	}
		
	public void renderPickup(Pickup truck) {
		Sprite truckSprite = atlasGen.getVehBody("pickup", truck.heading - 90);
		//Gdx.app.log("renderVeh", String.format("heading: %.2f", truck.getHeading()));
		truckSprite.setSize(truck.size.x, truck.size.y);
		
		truck.setSPos(camManager.getScreenPos(new Vector3(truck.position.x, 0, truck.position.y)));
		truckSprite.setPosition(truck.getSPos().x - truck.size.x / 2, truck.getSPos().y - truck.size.y / 2);
		if(truck.isSelected()) truckSprite.setColor(0.2f, 1f, 0.2f, 1);
		pickupBatch.setProjectionMatrix(camManager.getUIMatrix());
		pickupBatch.begin();
		truckSprite.draw(pickupBatch);
		pickupBatch.end();
	}
	public Vector2 getScreenSize(){
		return screenSize;
	}
	
	public void dispose(){
		pickupBatch.dispose();
	}
}

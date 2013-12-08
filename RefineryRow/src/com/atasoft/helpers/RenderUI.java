package com.atasoft.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

import java.util.*;

import com.atasoft.objects.*;

public class RenderUI
{
	private CameraManager camManager;
	private AtlasGen atlasGen;
	private VehicleManager vehicleManager;
	
	public static final int BUT_NORM = 0;
	public static final int BUT_PUSH = 1;
	private SpriteBatch uiBatch;
	
	public RenderUI(CameraManager camManager, AtlasGen atlasGen, VehicleManager vehicleManager){
		this.camManager = camManager;
		this.atlasGen = atlasGen;
		this.vehicleManager = vehicleManager;
		setupPops();
	}
	
	public void render() {
		if(popOpen) drawPopButs();
	}
	
	public static final Vector2 OFFSET_TOP = new Vector2(0, 0 + 96);
	public static final Vector2 OFFSET_BOTTOM = new Vector2(0, 0 - 96);
	
	private boolean popOpen = false;
	private ArrayList<PopButton> popList;
	
	private PopButton moveBut;
	private PopButton stopBut;
	private PopButton repairBut;
	private PopButton activePop;
	private Vector2 highlightPos;
	
	private Vector2 topPos;
	private Vector2 bottomPos;
	
	private void setupPops(){
		popList = new ArrayList<PopButton>();
		uiBatch = new SpriteBatch();
		moveBut = new PopButton(PopButton.MOVE_ORDER, Vector2.Zero);
		stopBut = new PopButton(PopButton.STOP, Vector2.Zero);
		repairBut = new PopButton(PopButton.REPAIR, Vector2.Zero);
		popList.add(moveBut);
		popList.add(stopBut);
		popList.add(repairBut);
	}
	
	public void popVehicle(Vector2 pos, Vehicle v) {
		popOpen = true;
		topPos = pos.cpy();
		topPos.sub(new Vector2(PopButton.BOX_SIZE.x / 2, PopButton.BOX_SIZE.y / 2));
		bottomPos = topPos.cpy();
		topPos.add(OFFSET_TOP);
		bottomPos.add(OFFSET_BOTTOM);
		
		moveBut.setVisible(true);
		moveBut.setParent(vehicleManager);
		moveBut.setPos(topPos);
		stopBut.setVisible(true);
		stopBut.setParent(vehicleManager);
		stopBut.setPos(bottomPos);
	}
	
	public void runPop(int x, int y){
		if(activePop != null) activePop.run((float) x, (float) y);
		for(PopButton p: popList){
			p.setVisible(false);
		}	
		highlightPop(true);
	}
	
	private void drawPopButs(){
		//Sprite butSprite;
		activePop = null;
		uiBatch.begin();
		for(PopButton p: popList) {
			if(p.isVisible()){
				Sprite butSprite = atlasGen.getPopBut(p, BUT_NORM);
				butSprite.setColor(1,1,1,0.6f);
				Vector2 tempPos = p.getPos();
				if(highlightPos != null) {
					Rectangle tempRect = new Rectangle(tempPos.x, tempPos.y, PopButton.BOX_SIZE.x, PopButton.BOX_SIZE.y);
					if (tempRect.contains(highlightPos)) {
						butSprite = atlasGen.getPopBut(p, BUT_PUSH);
						activePop = p;
					}
				}
				
				butSprite.setBounds(tempPos.x, tempPos.y, PopButton.BOX_SIZE.x, PopButton.BOX_SIZE.y); 
				butSprite.draw(uiBatch);
			    Gdx.app.log("RenderUI", String.format("%2d is visible", p.getFunction()));
			}
		}
		uiBatch.end();
	}
	
	public void highlightPop (int x, int y) {
		highlightPos = new Vector2(x, y);
	}
	
	public void highlightPop (boolean killPoint) {
		highlightPos = null;
	}
	
	public void dispose(){
		uiBatch.dispose();
	}
	
	
}

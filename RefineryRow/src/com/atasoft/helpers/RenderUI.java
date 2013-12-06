package com.atasoft.helpers;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import java.util.*;
import src.com.atasoft.objects.*;

public class RenderUI
{
	private CameraManager camManager;
	private AtlasGen atlasGen;
	
	public static final int BUT_NORM = 0;
	public static final int BUT_PUSH = 1;
	private SpriteBatch uiBatch;
	
	public RenderUI(CameraManager camManager, AtlasGen atlasGen){
		this.camManager = camManager;
		this.atlasGen = atlasGen;
		setupPops();
	}
	
	public void render() {
		if(popOpen) drawPopButs();
	}
	
	public static final Vector2 OFFSET_TOP = new Vector2(0, -200);
	public static final Vector2 OFFSET_BOTTOM = new Vector2(0, 200);
	
	private boolean popOpen = false;
	private ArrayList<PopButton> popList;
	
	private PopButton moveBut;
	private PopButton stopBut;
	private PopButton repairBut;
	private void setupPops(){
		uiBatch = new SpriteBatch();
		PopButton dummy = new PopButton();
		Vector2 zeroVector = new Vector2(0, 0);
		moveBut = new PopButton(dummy.MOVE_ORDER, zeroVector);
		stopBut = new PopButton(dummy.STOP, zeroVector);
		repairBut = new PopButton(dummy.REPAIR, zeroVector);
		popList.add(moveBut);
		popList.add(stopBut);
		popList.add(repairBut);
	}
	
	public void popPickup(Vector2 pos) {
		hidePops();
		popOpen = true;
		moveBut.setVisible(true);
		moveBut.setPos((new Vector2(pos.cpy())).add(OFFSET_TOP));
		stopBut.setVisible(true);
		stopBut.setPos((new Vector2(pos.cpy())).add(OFFSET_BOTTOM));
	}
	
	private void hidePops(){
		for(PopButton p: popList){
			p.setVisible(false);
		}	
	}
	
	private void drawPopButs(){
		Sprite butSprite;
		uiBatch.begin();
		for(PopButton p: popList) {
			if(p.isVisible()){
				butSprite = atlasGen.getPopBut(p, BUT_NORM);
			    butSprite.draw(uiBatch);
			}
		}
		uiBatch.end();
	}
	
	
}

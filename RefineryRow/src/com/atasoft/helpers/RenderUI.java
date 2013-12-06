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
	
	public static final Vector2 OFFSET_TOP = new Vector2(0 - 64, 0 + 64);
	public static final Vector2 OFFSET_BOTTOM = new Vector2(0 - 64, 0 - 192);
	
	private boolean popOpen = false;
	private ArrayList<PopButton> popList;
	
	private PopButton moveBut;
	private PopButton stopBut;
	private PopButton repairBut;
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
	
	public void popPickup(Vector2 pos) {
		hidePops();
		popOpen = true;
		moveBut.setVisible(true);
		Vector2 tempPos = pos.cpy();
		Vector2 tempPos2 = pos.cpy();
		moveBut.setPos(tempPos.add(OFFSET_TOP));
		stopBut.setVisible(true);
		stopBut.setPos(tempPos2.add(OFFSET_BOTTOM));
	}
	
	public void hidePops(){
		for(PopButton p: popList){
			p.setVisible(false);
		}	
	}
	
	private void drawPopButs(){
		//Sprite butSprite;
		uiBatch.begin();
		for(PopButton p: popList) {
			if(p.isVisible()){
				Sprite butSprite = atlasGen.getPopBut(p, BUT_NORM);
				butSprite.setBounds(p.getPos().x, p.getPos().y, PopButton.BOX_SIZE.x, PopButton.BOX_SIZE.y);
			    butSprite.draw(uiBatch);
			    Gdx.app.log("RenderUI", String.format("%2d is visible", p.getFunction()));
			}
		}
		uiBatch.end();
	}
	
	
}

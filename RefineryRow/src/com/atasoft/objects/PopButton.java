package com.atasoft.objects;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class PopButton
{
	public static final int MOVE_ORDER = 0;
	public static final int STOP = 1;
	public static final int REPAIR = 2;
	public static final int LAST_TYPE = 2; //Highest int (REPAIR)
	
	public static final Vector2 BOX_SIZE = new Vector2(128, 128);		
	private String atlasName;
	private Vector2 position;
	private int function;
	private boolean visible = false;
	private Sprite sprite;
	
	public PopButton(int function, Vector2 pos){
		this.position = pos;
		this.function = function;
		switch(function) {
			case MOVE_ORDER:
				this.atlasName = "pop" + Integer.toString(MOVE_ORDER);
				break;
			case STOP:
				this.atlasName = "pop" + Integer.toString(STOP);
				break;
			case REPAIR:
				this.atlasName = "pop" + Integer.toString(REPAIR);
				break;
			default:
				this.atlasName = "pop" + Integer.toString(MOVE_ORDER);
				break;
		}
	}
	
	public void setPos(Vector2 pos) {
		this.position = pos;
		return;
	}
	
	public Vector2 getPos() {
		return position;
	}

	public int getFunction() {
		return this.function;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setSprite(Sprite sprite){
		this.sprite = sprite;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public String getAtlasName() {
		return atlasName;
	}
}


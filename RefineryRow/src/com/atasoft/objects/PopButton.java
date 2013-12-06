package src.com.atasoft.objects;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class PopButton
{
	public static final int DUMMY = 0;
	public static final int MOVE_ORDER = 1;
	public static final int STOP = 2;
	public static final int REPAIR = 3;
	public static final int LAST_TYPE = 3; //Highest int (REPAIR)
	
	public static final Vector2 BOX_SIZE = new Vector2(400, 400);		
	private String atlasName;
	private Vector2 position;
	private int function;
	private boolean visible = false;
	private Sprite sprite;
	
	public PopButton() {  //called to get a dummy to get some values
		this.position = new Vector2(0,0);
		this.function = DUMMY;
	}
	public PopButton(int function, Vector2 pos){
		this.position = pos;
		this.function = function;
		switch(function) {
			case DUMMY:
				Gdx.app.log("PopButton.java", "This Popbutton is just a dummy... oops.");
				this.atlasName = "pop" + Integer.toString(DUMMY);
				break;
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
				this.atlasName = "pop" + Integer.toString(DUMMY);
				break;
		}
	}
	
	public void setPos(Vector2 pos) {
		this.position = pos;
		return;
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


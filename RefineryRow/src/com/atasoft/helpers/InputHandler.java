package com.atasoft.helpers;

import com.atasoft.screens.*;
import com.badlogic.gdx.*;

public class InputHandler implements InputProcessor {
	IsoScreen loopScreen;
	ClickWatcher clickWatcher;
	
	public InputHandler(IsoScreen loopScreen, ClickWatcher clickWatcher) {
		this.loopScreen = loopScreen;
		this.clickWatcher = clickWatcher;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		clickWatcher.clickCheck(screenX, screenY);
		//Gdx.app.log("InputHandler", String.format("screenX: %2d, screenY: %2d", screenX, screenY));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		clickWatcher.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		clickWatcher.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

package com.atasoft.refineryrow;

import com.atasoft.screens.GameLoopScreen;
import com.badlogic.gdx.Game;

public class RefineryAct extends Game
{
	GameLoopScreen loopScreen;

	@Override 
	public void create() {
		loopScreen = new GameLoopScreen(this);
        setScreen(loopScreen);  
	}
	
	@Override 
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		
	}

}
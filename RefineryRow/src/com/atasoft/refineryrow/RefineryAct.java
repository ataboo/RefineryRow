package com.atasoft.refineryrow;

import com.atasoft.screens.*;
import com.badlogic.gdx.Game;

public class RefineryAct extends Game
{
	IsoScreen isoScreen;

	@Override 
	public void create() {
		isoScreen = new IsoScreen(this);
        setScreen(isoScreen);
	}
	
	@Override 
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}

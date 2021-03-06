package com.atasoft.refineryrow;

import android.os.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.android.*;
//import com.badlogic.gdx.backends.android.AndroidApplication;
//import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


public class MainActivity extends AndroidApplication {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
		initialize(new RefineryAct(), cfg);
    }

}

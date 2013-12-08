package src.com.atasoft.helpers;

import com.badlogic.gdx.*;

public class AtaMathUtils
{
	public static float derpLerp(float current, float end, float delta) {
		float dif = end - current;
		if(Math.abs(dif) < delta) { 
			current = end;
		} else {
			current += Math.signum(dif) * delta;
			//if(dif > 0.1) Gdx.app.log("MathUtil/derpLerp", String.format("End: %.2f, Delta: %.2f, Current: %.2f",
			 //end, delta,  current));		
		}
		return current;
	}

	public static float cageEuler(float angle) {
		int intAngle = (int) angle;
		float remain = angle - intAngle;
		intAngle = intAngle % 360;
		if(intAngle < 0) {
			intAngle += 360;
			remain *= -1;
		}

		//Gdx.app.log("atlasGen/cageEuler", String.format("Turned angle %.2f", angle));
		angle = (float) intAngle + remain;
		//Gdx.app.log("atlasGen/cageEuler", String.format("Into %.2f", angle));
		return angle;
	}
}

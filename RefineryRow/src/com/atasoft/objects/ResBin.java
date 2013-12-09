package com.atasoft.objects;

import com.badlogic.gdx.*;

public class ResBin
 {
	//---------Hold Presets-------------
	public static final int HOLD_NONE = 0;
	public static final int HOLD_ALL = 1;
	public static final int HOLD_TIMBER = 2;
	public static final int HOLD_HOPPER = 3;
	public static final int HOLD_TANK = 4;
	 
	//---------Bin Types----------------
	public static final int GRAVEL = 0; //Gravel (raw)
	public static final int AGG = 1;
	public static final int SAND = 2;
	public static final int DIRT = 3;
	//washplant Minerals
	public static final int GOLD = 4;
	public static final int GEMS = 5;
	//metals
	public static final int SILVER = 6;
	public static final int CHROME = 7;
	public static final int IRON = 8;
	public static final int NICKEL = 9;
	public static final int ALUMINUM = 10;
	public static final int LEAD = 11;
	public static final int URANIUM = 12;
	public static final int POTASH = 13;
	//carbon resources
	public static final int COAL = 14;
	public static final int NATGAS = 15;
	public static final int CRUDE = 16;
	public static final int BITUMEN = 17;
	public static final int PETROLEUM = 18;
	public static final int DIESEL = 19;
	public static final int SULFUR = 20;
	//Logging resources
	public static final int TIMBER = 21;
	public static final int BRUSH = 22;
	public static final int WATER = 23;  //Make sure it's the last one

	public static final int BIN_COUNT = WATER + 1;
	
	private float[] binArr;
	private boolean[] holdsType;
	private float capacity;

	public ResBin() {
		binArr = new float[BIN_COUNT];
		holdsType = new boolean[BIN_COUNT];
		holdPreset(HOLD_NONE);
	}
	
	public static float[] getEmptyBinArr() {
		return new float[BIN_COUNT];
	}
	
	public static boolean[] holdPreset(int preset) {
		boolean[] retBool = new boolean[BIN_COUNT];
		switch (preset){
			case HOLD_NONE:
				retBool = setAll(retBool, false);
				break;
			case HOLD_TIMBER:  //ex: Logging trailer
				retBool = setAll(retBool, false);
				retBool[TIMBER] = retBool[BRUSH] = true;
				break;
			case HOLD_HOPPER:  //ex: Sand Hopper, dump truck
				setAll(retBool, true);
				retBool[WATER] = retBool[CRUDE] = retBool[TIMBER] = retBool[BRUSH] 
					= retBool[NATGAS] = retBool[DIESEL] = retBool[PETROLEUM]
					= retBool[SULFUR] = false;
				break;
			default:
				retBool = setAll(retBool, true);
				break;
		}
		return retBool;
	}
	
	public static boolean[] setAll(boolean[] retBool, boolean bool) {
		for(int i = 0; i < retBool.length; i++) {
			retBool[i] = bool;
		}
		return retBool;
	}
	
	public static boolean[] setFromInt(int[] indices, boolean bool) {
		boolean[] retBool = new boolean[BIN_COUNT];
		
		for(int i = 0; i < indices.length; i++){
			retBool[indices[i]] = bool;
		}
		return retBool;
	}
	
	public float[] getBinArr() {
		return binArr;
	}
	
	public void setBinArr(int index, float amount) {
		this.binArr[index] = amount;
		return;
	}
	
	public void setBinArr(float[] inArr) {
		this.binArr = inArr;
		return;
	}
	
	public void addToBin(int index, float amount) {  //can be negative
		this.binArr[index] += amount;
		return;
	}
	
	//adds inputArr according to capacity by total volume
	public float[] addToBin(float[] inputArr) {
		float factor = checkCapacity(inputArr);
		for(int i = 0; i < binArr.length; i++) {
			if(this.holdsType[i]) {
			this.binArr[i] += inputArr[i] * factor;
			inputArr[i] -= inputArr[i] * factor;
			}
			//if capacity stuff
		}
		return inputArr;
	}
	
	//returns fraction of inputArr that binArr has room for (0 - 1)
	private float checkCapacity(float[] inputArr) {
		float localRoom = this.capacity - getLocalBinSum();
		float inSum = getBinSum(inputArr, true);  //input arr might be negative.
		if(inSum == -1) return 0;
		
		if(localRoom > inSum) return 1;
		return localRoom / inSum;
	}
	
	public float getLocalBinSum() {
		return getBinSum(this.binArr, true, true);
	}
	
	public float getBinSum(float[] inpArr, boolean errNegatives) {
		return getBinSum(inpArr, errNegatives, false);
	}
	
	//Gets volume sum of all bins. Returns -1 if(errNeg) and any bins are negative. Fixes negative bins if(local) and returns sum.
	private float getBinSum(float[] inpArr, boolean errNeg, boolean local) {
		float sum = 0;
		boolean errFlag = false;
		for (int i = 0; i < inpArr.length; i++) {
			if (errNeg && inpArr[i] < 0)  {
				errFlag = true;
				Gdx.app.error("ResBin", String.format("Bin %2d is unexpectadly being negative.  Cheer up bin.", i));
				if(local) {
					Gdx.app.error("ResBin", "The negative bin is local and was zeroed.");
					this.binArr[i] = 0;
				}
			}			
			sum += inpArr[i];
		}
		if(errFlag && !local) return -1;
		
		return sum;
	}
}

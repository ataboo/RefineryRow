package com.atasoft.objects;

import com.atasoft.objects.*;

public class Pile
{
	public static final int EMPTY = 0; //dummy for placeholding in lists
	//Piled resources
	public static final int P_SAND_ORE = 1;
	public static final int P_AGG_ORE = 2;
	public static final int P_HARD_ORE = 3;  
	public static final int P_TIMBER = 4;  //Cut timber
	public static final int P_BRUSH = 5;  //Cut bushes
	public static final int P_DIRT = 6;
	
	public int pileType;
	public float[] position;
	public float[] size = new float[2];
	public int heading;
	
	public ResBin resBin;
	
	public Pile(int type, float[] location, int heading) {
		this.position = new float[]{location[0], location[1]};
		this.pileType = type;
		this.heading = heading;
		
		switch(type) {
			default:
				this.size[0] = this.size[1] = 1;
				this.resBin = new ResBin();
				break;
		}
	}
}

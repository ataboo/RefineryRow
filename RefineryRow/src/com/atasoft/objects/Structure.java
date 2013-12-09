package com.atasoft.objects;

//------------------------------------------------------------------------
/* Parent class for all interactable structures that aren't vehicles. They
cover atleast one square grid when placed.
ex: rubble pile, factory, resource nodes.*/
//------------------------------------------------------------------------
import com.atasoft.objects.*;

public class Structure
{
	public static final int EMPTY = 0; //dummy type for placeholding in lists
	//Buildings
	public static final int B_PBOILER = 1;  //Package boiler
	public static final int B_SML_SIFTER = 2;  //Small gravel sifter
	public static final int B_SML_WORKSHOP = 3;  //Small workshop (quansit)
	public static final int B_SML_LAYDOWN = 4;  //Laydown yard
	
	public int structType;
	public int[] size; //in grid squares
	public int[] position; //grid
	public int heading; //0-3 = North - West
	public ResBin resBin;  //Resources held in this structure
	
	public Structure(int type, int[] gridPos, int heading) {
		this.structType = type;
		this.position = new int[]{gridPos[0], gridPos[1]};
		this.heading = heading;
		
		switch(type) {
			default:
				this.size[0] = this.size[1] = 1;
				this.resBin = new ResBin();
				break;
		}
	}
	
	
	
}

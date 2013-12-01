package src.com.atasoft.refineryrow;

import java.util.*;

public class MapGenerator
{
	private double hVal = 500;
	private static final double passes = 2;
	private static final double seed = 1000;

	public MapGenerator() {
			}
	
	public double[][] makeMap(int size) {
		double[][] hArr = new double[size][size];
		Random rand = new Random();
		hArr[0][0] = Math.abs(seed * rand.nextDouble());
		hArr[size - 1][0] = seed * rand.nextDouble();
		hArr[size - 1][size - 1] = seed * rand.nextDouble();
		hArr[0][size - 1] = seed * rand.nextDouble();

		for(int sideLength = size-1; sideLength >= 2;
		//each iteration we are looking at smaller squares
		//diamonds, and we decrease the variation of the offset
		sideLength /= 2, hVal /= 1.3){
			//half the length of the side of a square
			//or distance from diamond center to one corner
			//(just to make calcs below a little clearer)
			int halfSide = sideLength/2;

			//generate the new square values
			for(int x=0;x<size-1;x+=sideLength){
				for(int y=0;y<size-1;y+=sideLength){
					//x, y is upper left corner of square
					//calculate average of existing corners
					double avg = hArr[x][y] + //top left
						hArr[x+sideLength][y] +//top right
						hArr[x][y+sideLength] + //lower left
						hArr[x+sideLength][y+sideLength];//lower right
					avg /= 4.0;

					//center is average plus random offset
					hArr[x+halfSide][y+halfSide] = 
						//We calculate random value in range of 2h
						//and then subtract h so the end value is
						//in the range (-h, +h)
						avg + (rand.nextDouble()*2*hVal) - hVal;
				}
			}

			//generate the diamond values
			//since the diamonds are staggered we only move x
			//by half side
			//NOTE: if the data shouldn't wrap then x < DATA_SIZE
			//to generate the far edge values
			for(int x=0;x<size-1;x+=halfSide){
				//and y is x offset by half a side, but moved by
				//the full side length
				//NOTE: if the data shouldn't wrap then y < DATA_SIZE
				//to generate the far edge values
				for(int y=(x+halfSide)%sideLength;y<size-1;y+=sideLength){
					//x, y is center of diamond
					//note we must use mod  and add DATA_SIZE for subtraction 
					//so that we can wrap around the array to find the corners
					double avg = 
						hArr[(x-halfSide+size)%size][y] + //left of center
						hArr[(x+halfSide)%size][y] + //right of center
						hArr[x][(y+halfSide)%size] + //below center
						hArr[x][(y-halfSide+size)%size]; //above center
					avg /= 4.0;

					//new value = average plus random offset
					//We calculate random value in range of 2h
					//and then subtract h so the end value is
					//in the range (-h, +h)
					avg = avg + (rand.nextDouble()*2*hVal) - hVal;
					//update value for center of diamond
					hArr[x][y] = avg;
				}
			}
		}

		return hArr;
		
	}
	
	
	
}

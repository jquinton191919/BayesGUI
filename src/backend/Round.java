package backend;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    if (bd.doubleValue() > 1.0) {
	    	return 1.0;
	    }
	    return bd.doubleValue();
	}
	
	public static void bubbleSort(int [] array, boolean desc) {
		boolean isChange;
		int placeHolder;
		
		do {
			isChange = false;
			for(int i=0; i < array.length-1; i++) {
				if(desc) {
					if(array[i] < array[i+1]) {
						placeHolder = array[i];
						array[i] = array[i+1];
						array[i+1] = placeHolder;
						isChange = true;
					}
				}
				else {
					if(array[i] > array[i+1]) {
						placeHolder = array[i];
						array[i] = array[i+1];
						array[i+1] = placeHolder;
						isChange = true;
					}
				}
			}
			
		} while(isChange);
	}
	
	public static void main(String [] args) {
		int [] a = new int [] {0, 1234, 432, 4, 1};
		bubbleSort(a, false);
		
		for(int i=0; i < a.length; i++) {
			System.out.println(a[i]);
		}
		
	}

}

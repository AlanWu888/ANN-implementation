package skelton_ai;

import java.text.DecimalFormat;

public class runners {
	static double KEY_DIFFERENCE = 0.0005;		// the difference between each normalised date value
	
	public static double[] validate(double[] result) {
		double[] result_validation = new double[data_sets.VALIDATION_DATA.length];
		/*
		 *  for each date in validation set, find the 3 before it (if possible)			[DONE]
		 *  then find a correlation for those 3 points
		 *  if negative, average the difference and sub
		 *  if positive, avarage the difference and add
		 *  
		 */
		DecimalFormat df = new DecimalFormat("#.####");
		double currDate = 0;
		
		for(int i=0; i<data_sets.VALIDATION_DATA.length; i++) {		// iterate through each row in validation data
			int[] indexSearch = new int [] {0,0,0};
			currDate = Double.parseDouble(df.format(data_sets.VALIDATION_DATA[i][0][0]));		// get the date, but have to round some as ..01 and ..999 values
			int j = 0;
			for(int k=0; k<data_sets.TRAINING_DATA.length; k++) {
				for(int n=0; n<10; n++) {		// n < 10 because there may not be 3 consecutive lagged results; have to be opportunistic in lagging results
												// this is the case because I split the data set using modular division
					if((Double.parseDouble(df.format(data_sets.TRAINING_DATA[k][0][0]))-n*KEY_DIFFERENCE) == currDate) {
						indexSearch[j] = k;
						if (j==2) {
							break;
						} else {
							j++;
						}
					}
				}				
			}
			/* link to the training data set now
			 * 	if the list contains NO zeroes (or 3 lagged results), then work out correlation & average
			 */
			if(j==2) {		// if there are 3 lagged results
				double average = Math.abs(result[indexSearch[0]] - ((result[indexSearch[0]] + result[indexSearch[1]] + result[indexSearch[2]]) / 3));
				if(average>result[indexSearch[0]]) {
					// positive correlation so add average to last value
					result_validation[i] = result[indexSearch[2]] + average;
				} else {
					result_validation[i] = result[indexSearch[2]] - average;
				}
			}
			if (result_validation[i] < 0.1) {
				result_validation[i] = 0.11;
			}
		}
		
		return result_validation;
	}
	
	public static double[] test(double[] result) {
		double[] result_test = new double[data_sets.TEST_DATA.length];
		/*
		 *  for each date in validation set, find the 3 before it (if possible)
		 *  then find a correlation for those 3 points
		 *  if negative, average the difference and sub
		 *  if positive, avarage the difference and add
		 *  
		 */
		DecimalFormat df = new DecimalFormat("#.####");
		double currDate = 0;
		
		for(int i=0; i<data_sets.TEST_DATA.length; i++) {		// iterate through each row in validation data
			int[] indexSearch = new int [] {0,0,0};
			currDate = Double.parseDouble(df.format(data_sets.TEST_DATA[i][0][0]));		// get the date, but have to round some as ..01 and ..999 values
			int j = 0;
			for(int k=0; k<data_sets.TRAINING_DATA.length; k++) {
				for(int n=0; n<10; n++) {	// n < 10 because there may not be 3 consecutive lagged results; have to be opportunistic in lagging results
											// this is the case because I split the data set using modular division
					if((Double.parseDouble(df.format(data_sets.TRAINING_DATA[k][0][0]))-n*KEY_DIFFERENCE) == currDate) {
						indexSearch[j] = k;
						if (j==2) {
							break;
						} else {
							j++;
						}
					}
				}				
			}
			/* link to the training data set now
			 * 	if the list contains NO zeroes (or 3 lagged results), then work out correlation & average
			 */
			if(j==2) {		// if there are 3 lagged results
				double average = Math.abs(result[indexSearch[0]] - ((result[indexSearch[0]] + result[indexSearch[1]] + result[indexSearch[2]]) / 3));
				if(average>result[indexSearch[0]]) {
					// positive correlation so add average to last value
					result_test[i] = result[indexSearch[2]] + average;
				} else {
					result_test[i] = result[indexSearch[2]] - average;
				}
			}
			if (result_test[i] < 0.1) {
				result_test[i] = 0.11;
			}
		}
		
		return result_test;
	}
}

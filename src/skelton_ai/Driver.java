package skelton_ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
	static int NUMB_OF_EPOCHS = 9000; 
	static double KEY_DIFFERENCE = 0.0005;
	public static void main(String[] args) throws IOException {
		NeuralNetwork neuralNetwork = new NeuralNetwork(4);	// construct a network based on the length of our data set
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		List<Double> rmse = new ArrayList<Double>();
		double[] result = new double[data_sets.TRAINING_DATA.length];
		double[] result_validation = new double[data_sets.VALIDATION_DATA.length];
		
		boolean flag = true;
		while (flag) {
			System.out.println("> What do you want to do (run, train, exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
				case "run":
					// double[] result = new double[data_sets.TRAINING_DATA.length];
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
																.fProp(data_sets.TRAINING_DATA[i][0])
																.getLayers()[2].getNeurons()[0]
																.getOutput());
					writeResults(result);
					break;
					
				case "run_validation":
					// double[] result_validation = new double[data_sets.TRAINING_DATA.length];
					/*
					System.out.println(result[10]);
					IntStream.range(0, data_sets.VALIDATION_DATA.length).forEach(i -> result_validation[i] = neuralNetwork
																.fProp(data_sets.VALIDATION_DATA[i][0])
																.getLayers()[2].getNeurons()[0]
																.getOutput());
					writeResults(result_validation);
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
						// Double.parseDouble(df.format(data_sets.TRAINING_DATA[k][0][0]))-((j)*KEY_DIFFERENCE) == currDate
						int j = 0;
						for(int k=0; k<data_sets.TRAINING_DATA.length; k++) {
							for(int n=0; n<10; n++) {
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
						System.out.println(currDate + "		" + Arrays.toString(indexSearch));
						
						/* link to the training data set now
						 * 	if the list contains NO zeroes, then work out correlation & average
						 */
						if(j==2) {	
							double average = (data_sets.TRAINING_DATA[indexSearch[0]][0][1] 
											+ data_sets.TRAINING_DATA[indexSearch[1]][0][1]
											+ data_sets.TRAINING_DATA[indexSearch[2]][0][1]) / 3;
							System.out.println(average);
							if(average>data_sets.TRAINING_DATA[indexSearch[0]][0][1]) {
								// positive correlation so add average to last value
								result_validation[i] = data_sets.TRAINING_DATA[indexSearch[2]][0][1] + average;
							} else {
								result_validation[i] = data_sets.TRAINING_DATA[indexSearch[2]][0][1] - average;
							}
						}
					}
					writeResults(result_validation);
					break;
					
				case "train":
					double[] result_rmse = new double[data_sets.TRAINING_DATA.length];
					IntStream.range(0,  NUMB_OF_EPOCHS).forEach(i -> {
						System.out.print("[epoch "+i+"]		");
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(j -> neuralNetwork
																.fProp(data_sets.TRAINING_DATA[j][0])
																.backpropError(data_sets.TRAINING_DATA[j][1][0]));
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(k -> result_rmse[k] = neuralNetwork
								.getLayers()[2].getNeurons()[0]
								.getOutput());
						System.out.println("Error: " + NeuralNetwork.calcRMSE(result_rmse));
						rmse.add(NeuralNetwork.calcRMSE(result_rmse));
					});
					excel_handler.writeErrors(rmse);	
					System.out.println("[done training]");
					break;
					
				case "exit":
					flag = false;
					break;
			}
		}
		System.exit(0);
	}
	
	static void writeResults(double[] result) {
		excel_handler.writeResults(result, result.length);	
	}
	
	/*
	static void printResult(double[] result) {
		System.out.println("   Date    |    Flow    | Target Result |  Result    ");
		System.out.println("-------------------------------------------------------------");
		IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> {
			IntStream.range(0, data_sets.TRAINING_DATA[0][0].length).forEach(j -> System.out.print("    "+ data_sets.TRAINING_DATA[i][0][j] + "      |  "));
			System.out.print("    "+ data_sets.TRAINING_DATA[i][1][0] + "      |  " + String.format("%.5f", result[i]) + "  \n");
		});
	}
	*/
}


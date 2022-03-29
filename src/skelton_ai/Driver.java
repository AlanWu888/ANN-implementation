package skelton_ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	static int NUMB_OF_EPOCHS = 5000; 	// 5000 epochs defined as a constant
	public static void main(String[] args) throws IOException {
		NeuralNetwork neuralNetwork = new NeuralNetwork(4);	// construct a network with 4 hidden neurons
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		List<Double> rmse = new ArrayList<Double>();	// array of doubles to keep track of the overall rmse
		double[] result = new double[data_sets.TRAINING_DATA.length];	// ann training outputs to be stored here
		
		boolean flag = true;
		while (flag) {
			System.out.println("> What do you want to do (run, run_validation, run_test, train, exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
				case "run":
					// double[] result = new double[data_sets.TRAINING_DATA.length];
					// Forward propagate  through all training data to get outputs
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
																.fProp(data_sets.TRAINING_DATA[i][0])
																.getLayers()[2].getNeurons()[0]
																.getOutput());
					
					excel_handler.writeResults(result);	// write to the excel file called "results"
					System.out.println("[results ready]");
					break;
					
				case "run_validation":
					// Forward propagate  through all training data to get outputs
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
						.fProp(data_sets.TRAINING_DATA[i][0])
						.getLayers()[2].getNeurons()[0]
						.getOutput());
					
					excel_handler.writeValidation(runners.validate(result));	// write to the excel file called "validation"
					System.out.println("[results ready]");
					break;
					
				case "run_test":
					// Forward propagate  through all training data to get outputs
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
					.fProp(data_sets.TRAINING_DATA[i][0])
					.getLayers()[2].getNeurons()[0]
					.getOutput());
				
					excel_handler.writeTest(runners.test(result));	// write to the excel file called "test"
					System.out.println("[results ready]");
					break;
					
				case "train":
					double[] result_rmse = new double[data_sets.TRAINING_DATA.length];
					IntStream.range(0,  NUMB_OF_EPOCHS).forEach(i -> {
						System.out.print("[epoch "+i+"]		");
						// for every epoch, go through training set, and for each row in training set forward propagate the inputs
						// and backpropagate the target result
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(j -> neuralNetwork
																.fProp(data_sets.TRAINING_DATA[j][0])
																.backpropError(data_sets.TRAINING_DATA[j][1][0]));
						// get the error on the output neuron and store in result_rmse so that the error can be tracked
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(k -> result_rmse[k] = neuralNetwork
								.getLayers()[2].getNeurons()[0]
								.getOutput());
						System.out.println("Error: " + NeuralNetwork.calcRMSE(result_rmse));
						rmse.add(NeuralNetwork.calcRMSE(result_rmse));
					});
					excel_handler.writeErrors(rmse);		// write the rmse array to excel file so it can be analysed and so a graph can be made from it
					System.out.println("[done training]");
					break;
					
				case "exit":
					flag = false;	// set flag to false, which will exit the while loop which runs program
					break;
			}
		}
		System.exit(0);			// terminate program
	}
}


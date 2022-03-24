package xor_problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Driver {
	static int NUMB_OF_EPOCHS = 2000; 
		
	public static void main(String[] args) throws NumberFormatException, IOException {
		// System.out.println(Arrays.toString(data_sets.TRAINING_DATA[0][0]));	// [1, 2, ,3]
		// System.out.println(Double.toString(data_sets.TRAINING_DATA[0][1][0]));	// 4
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		// System.out.println("> Please enter # of neurons in the hidden layer");
		// NeuralNetwork neuralNetwork = new NeuralNetwork(Integer.parseInt(bufferedReader.readLine()));
		
		NeuralNetwork neuralNetwork = new NeuralNetwork(data_sets.TRAINING_DATA.length);	// create ann with n amount of hidden nuerons where n is the number of rows in the data set being used
		boolean flag = true;
		while (flag) {
			System.out.println("> What do you want to do (run, train, exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
				case "run":
					double[] result = new double[winter_sets.WINTER_TRAINING.length];
					IntStream.range(0, winter_sets.WINTER_TRAINING.length).forEach(i ->result[i] = neuralNetwork.fProp(winter_sets.WINTER_TRAINING[i][0]).getLayers()[2].getNeurons()[0].getOutput());
					printResult(result);
					break;
				case "train":
					IntStream.range(0,  NUMB_OF_EPOCHS).forEach(i -> {
						System.out.println("[epoch "+i+"]");
						IntStream.range(0, winter_sets.WINTER_TRAINING.length).forEach(j ->
							neuralNetwork.fProp(winter_sets.WINTER_TRAINING[j][0]).backpropError(winter_sets.WINTER_TRAINING[j][1][0]));
					});
					System.out.println("[done training]");
					break;
				case "exit":
					flag = false;
					break;
			}
		}
		System.exit(0);
	}
	static void printResult(double[] result) {
		// excel_handler.getOuts(result, data_sets.TRAINING_DATA.length);
		excel_handler.writeResults(result, winter_sets.WINTER_TRAINING.length);
		/*
		IntStream.range(0, data_sets.TRAINING_DATA[0][0].length).forEach(x -> System.out.print("  Input "+x+"  |"));
		System.out.println(" Target Result |  Result    ");
		IntStream.range(0, data_sets.TRAINING_DATA[0][0].length).forEach(x -> System.out.print("-------------"));
		System.out.println("--------------------------");
		IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> {
			IntStream.range(0, data_sets.TRAINING_DATA[0][0].length).forEach(j -> System.out.print("    "+ data_sets.TRAINING_DATA[i][0][j] + "    |"));
			System.out.print("     "+data_sets.TRAINING_DATA[i][1][0] + "       |  " + String.format("%.5f", result[i]) + "  \n");
		});
		*/
		
	}
}

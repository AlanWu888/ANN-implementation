package xor_problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
public class Driver {
	static int NUMB_OF_EPOCHS = 10000; 
	static double TRAINING_DATA[][][] = new double[][][] {{{1,0,0},{0}},
														  {{1,0,1},{1}},
												          {{1,1,0},{1}},
												          {{1,1,1},{0}}};
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("> Please enter # of neurons in the hidden layer");
		NeuralNetwork neuralNetwork = new NeuralNetwork(Integer.parseInt(bufferedReader.readLine()));
		boolean flag = true;
		while (flag) {
			System.out.println("> What do you want to do (run, train, exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
				case "run":
					double[] result = new double[ Driver.TRAINING_DATA.length];
					IntStream.range(0, Driver.TRAINING_DATA.length).forEach(i ->
						result[i] = neuralNetwork.fProp(Driver.TRAINING_DATA[i][0])
								                 .getLayers()[2].getNeurons()[0].getOutput());
					printResult(result);
					break;
				case "train":
					IntStream.range(0,  NUMB_OF_EPOCHS).forEach(i -> {
						System.out.println("[epoch "+i+"]");
						IntStream.range(0,  TRAINING_DATA.length).forEach(j ->
							System.out.println(neuralNetwork.fProp(Driver.TRAINING_DATA[j][0])
									                        .backpropError(Driver.TRAINING_DATA[j][1][0])));
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
		IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print("  Input "+x+"  |"));
		System.out.println(" Target Result |  Result    ");
		IntStream.range(0, TRAINING_DATA[0][0].length).forEach(x -> System.out.print("-------------"));
		System.out.println("--------------------------");
		IntStream.range(0, TRAINING_DATA.length).forEach(i -> {
			IntStream.range(0, TRAINING_DATA[0][0].length).forEach(j -> System.out.print("    "+TRAINING_DATA[i][0][j] + "    |"));
			System.out.print("     "+TRAINING_DATA[i][1][0] + "       |  " + String.format("%.5f", result[i]) + "  \n");
		});
	}
}

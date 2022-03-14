import java.util.stream.IntStream;

public class NeuralNetwork {
	final static int NO_INPUT_NEURONS = Driver.TRAINING_DATA[0][0].length;
	final static int NO_OUTPUT_NEURONS = 1;
	final static double LEARNING_RATE = 0.8;	// how fast the network learns
	private int noHiddenNeurons;
	private Layer[] layers = new Layer[e_layerType.values().length];
	
	// CONSTRUCTOR
	public NeuralNetwork(int noHiddenNeurons) {
		/* initialises the number of hidden neurons and will also populate the layers array with each layer type
		 * (an input layer, a hidden layer and an output layer)
		 */
		this.noHiddenNeurons = noHiddenNeurons;
		layers[0] = new Layer(this, e_layerType.INPUT);
		layers[1] = new Layer(this, e_layerType.HIDDEN);
		layers[2] = new Layer(this, e_layerType.OUTPUT);
	}
	
	// GETTER
	public int getNoHiddrenNeurons() {
		return noHiddenNeurons;
	}
	
	// TOSTRING
	public String toString() {
		// return info about the network
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, layers.length).forEach(x -> returnValue.append(layers[x] + " "));
		return returnValue.toString();
	}
	
	public NeuralNetwork fProp(double input[]) {
		// forward propagate network which runs the network by taking in a vector input
		for (int i=0; i<layers.length; i++) {	// go through all layers in the network
			switch (layers[i].getLayerType()) {
			case INPUT:
				// if we encounter an input layer, we go through all input neurons and set their outputs to the input that is coming in
				for (int j=0; j<layers[i].getNeurons().length; j++) {
					layers[i].getNeurons()[j].setOutput(input[j]);
				}
				break;
			case HIDDEN:
				// if we encounter a hidden layer, we go through all hidden neurons and we calculate the neuron's weighted sum
				// which then has the sigmoid activation function applied to it in order to calculate the output for each neuron
				for (int j=0; j <layers[i].getNeurons().length; j++) {
					double weightedSum = 0;
					for (int k = 0; k < layers[i].getNeurons()[0].getWeights().length; k++) {
						weightedSum += layers[i].getNeurons()[j].getWeights()[k]*layers[i-1].getNeurons()[k].getOutput();
					}
					layers[i].getNeurons()[0].applySigmoid(weightedSum);
				}
				break;
			case OUTPUT:
				// if we encounter the output layer, we calculate the weighted sum for the output neuron and pass this
				// weighted sum to the sigmoid activation function which will then give us the output of the neuron
				double weightedSum = 0;
				for (int k = 0; k <layers[i].getNeurons()[0].getWeights().length; k++) {
					weightedSum += layers[i].getNeurons()[0].getWeights()[k]*layers[i-1].getNeurons()[k].getOutput();
				}
				layers[i].getNeurons()[0].applySigmoid(weightedSum);
				break;
			}
		}
		return this;
	}
	
	public NeuralNetwork bPropError(double targetResult) {
		// backpropagation method which takes in a target result and backpropagates the error
		Neuron[] inputNeuron = layers[0].getNeurons();					// all of our input neurons
		Neuron[] hiddenNeuron = layers[1].getNeurons();					// all of our hidden neurons
		Neuron outputNeuron = layers[layers.length-1].getNeurons()[0];	// all of our output neurons
		
		outputNeuron.setError((targetResult - outputNeuron.getOutput()) * outputNeuron.calculateDerivative());
		// calculate error on output neuron and use this error in order to calculate the weight for the output neuron

		for (int j=0; j<outputNeuron.getWeights().length; j++) {
			outputNeuron.getWeights()[j] = outputNeuron.getWeights()[j] + LEARNING_RATE * outputNeuron.getError() * hiddenNeuron[j].getOutput();
		}
		
		// then we back propagate the error to the hidden neurons
		for (int i=0; i<hiddenNeuron.length; i++) {
			hiddenNeuron[i].setError(outputNeuron.getWeights()[i] * outputNeuron.getError() * hiddenNeuron[i].calculateDerivative());
			// we use this to then calculate the error on the hidden neurons
			// we use hidden neuron errors to calculate the weight on the hidden neurons
			for (int j=0; j<hiddenNeuron[0].getWeights().length; j++) {
				hiddenNeuron[i].getWeights()[j] = hiddenNeuron[i].getWeights()[j] + LEARNING_RATE * hiddenNeuron[i].getError() * inputNeuron[j].getOutput();
			}
		}
		
		return this;
	}
	
}
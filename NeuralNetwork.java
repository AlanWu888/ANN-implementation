package skelton_ai;

import java.util.stream.IntStream;
public class NeuralNetwork {
	static final double LEARNING_RATE = 0.01;
	final static int NUMB_OF_INPUT_NEURONS = data_sets.TRAINING_DATA[0][0].length;	// will become 2 input neurons as we have 2 inputs: the date and the flow of skelton on that given day 
	final static int NUMB_OF_OUTPUT_NEURONS = 1;
	private int numbOfHiddenNeurons;
	private Layer[] layers = new Layer[e_layerTypes.values().length];
	
	public NeuralNetwork(int numbOfHiddenNeurons) {
		/* initialises the number of hidden neurons and will also populate the layers array with each layer type
		 * (an input layer, a hidden layer and an output layer)
		 */
		this.numbOfHiddenNeurons = numbOfHiddenNeurons;
		layers[0] = new Layer(this, e_layerTypes.INPUT);
		layers[1] = new Layer(this, e_layerTypes.HIDDEN);
		layers[2] = new Layer(this, e_layerTypes.OUTPUT);
	}
	
	public NeuralNetwork fProp(double input[]) { 
		// System.out.println("total neurons: " + (layers[0].getNeurons().length + layers[1].getNeurons().length + layers[2].getNeurons().length));
		// forward propagate network which runs the network by taking in a vector input
		for (int i = 0; i < layers.length; i++) {
			switch (layers[i].getLayerType()) {
				case INPUT:
					// if we encounter an input layer, we go through all input neurons and set their outputs to the input that is coming in
					for (int j = 0; j < layers[i].getNeurons().length; j++) {
						layers[i].getNeurons()[j].setOutput(input[j]);
					}
						
	    			break;
	    			
		    	case HIDDEN:
		    		// if we encounter a hidden layer, we go through all hidden neurons and we calculate the neuron's weighted sum
					// which then has the sigmoid activation function applied to it in order to calculate the output for each neuron
		    		for (int j = 0; j < layers[i].getNeurons().length; j++) {
		    			double weightedSum = 0;
		    			for (int k = 0; k < layers[i].getNeurons()[0].getWeights().length; k++)
				    		weightedSum += layers[i].getNeurons()[j].getWeights()[k] * layers[i-1].getNeurons()[k].getOutput();
		    			layers[i].getNeurons()[j].applySigmoid(weightedSum);
		    		}
		    		break;
		    		
		    	case OUTPUT:
		    		// if we encounter the output layer, we calculate the weighted sum for the output neuron and pass this
					// weighted sum to the sigmoid activation function which will then give us the output of the neuron
		    		double weightedSum = 0;
		    		for (int k = 0; k < layers[i].getNeurons()[0].getWeights().length; k++)
		    			weightedSum += layers[i].getNeurons()[0].getWeights()[k] * layers[i-1].getNeurons()[k].getOutput();
		    		layers[i].getNeurons()[0].applySigmoid(weightedSum);
		    		break;
			}
		}
		return this;
	}
	
	public NeuralNetwork backpropError(double targetResult) {
		Neuron[] inputNeuron = layers[0].getNeurons();
		Neuron[] hiddenNeuron = layers[1].getNeurons();
		Neuron outputNeuron = layers[layers.length-1].getNeurons()[0];
		// calculate error on output neuron and use this error in order to calculate the weight for the output neuron
		outputNeuron.setError((targetResult - outputNeuron.getOutput()) * outputNeuron.calcDerivative());
		for (int j = 0; j < outputNeuron.getWeights().length; j++)
			outputNeuron.getWeights()[j] = outputNeuron.getWeights()[j] + LEARNING_RATE * outputNeuron.getError() * hiddenNeuron[j].getOutput();
		// then we back propagate the error to the hidden neurons
		for (int i = 0; i < hiddenNeuron.length; i++) {
			hiddenNeuron[i].setError((outputNeuron.getWeights()[i] * outputNeuron.getError()) * hiddenNeuron[i].calcDerivative());
			// we use this to then calculate the error on the hidden neurons
			// we use hidden neuron errors to calculate the weight on the hidden neurons
			for (int j = 0; j < hiddenNeuron[0].getWeights().length; j++)
				hiddenNeuron[i].getWeights()[j] = hiddenNeuron[i].getWeights()[j] + LEARNING_RATE * hiddenNeuron[i].getError() * inputNeuron[j].getOutput();
		}
		return this;
	}
	
	public int getNumbOfHiddenNeurons() { 
		return numbOfHiddenNeurons; 
	}
	public Layer[] getLayers() { 
		return layers;
	}
	
	public String toString() { 
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, layers.length).forEach(x -> returnValue.append(layers[x] + "  "));
		return returnValue.toString();
	}
}
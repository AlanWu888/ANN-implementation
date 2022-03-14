package xor_problem;

import java.util.stream.IntStream;

public class Layer {	// Represents a layer in the network
	private Neuron[] neurons = null;	// each layer has an array of neurons that belong to it
	private e_layerTypes layerType;		// each layer also has a layer type
	
	public Layer(NeuralNetwork neuralNetwork, e_layerTypes layerType) { 	// initialises layer type, will do different things for different layer types
		this.layerType = layerType;
		switch (layerType) {
			case INPUT:
				// populate layer's neuron array with input neurons
				neurons = new Neuron[NeuralNetwork.NUMB_OF_INPUT_NEURONS];
				IntStream.range(0, NeuralNetwork.NUMB_OF_INPUT_NEURONS).forEach(i -> neurons[i] = new Neuron(layerType, 0));
				break;
	    	case HIDDEN:
	    		// populate layer's neuron array with hidden neurons
	    		neurons = new Neuron[neuralNetwork.getNumbOfHiddenNeurons()];
				IntStream.range(0, neuralNetwork.getNumbOfHiddenNeurons()).forEach(i -> 
													neurons[i] = new Neuron(layerType,NeuralNetwork.NUMB_OF_INPUT_NEURONS));
	    		break;
	    	case OUTPUT:
	    		// populate layer's neuron array with an output neurons
	    		neurons = new Neuron[NeuralNetwork.NUMB_OF_OUTPUT_NEURONS];
				neurons[0] = new Neuron(layerType, neuralNetwork.getNumbOfHiddenNeurons());
	    		break;
		}
	}
	public Neuron[] getNeurons() { return neurons; }
	public e_layerTypes getLayerType() { return layerType; }
	public String toString() { 
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, neurons.length).forEach(x -> returnValue.append(neurons[x] + " "));
		return returnValue.toString();
	}
}

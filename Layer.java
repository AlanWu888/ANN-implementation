package xor_problem;

import java.util.stream.IntStream;

public class Layer {
	private Neuron[] neurons = null;
	private e_layerTypes layerType;
	public Layer(NeuralNetwork neuralNetwork, e_layerTypes layerType) { 
		this.layerType = layerType;
		switch (layerType) {
			case INPUT:
				neurons = new Neuron[NeuralNetwork.NUMB_OF_INPUT_NEURONS];
				IntStream.range(0, NeuralNetwork.NUMB_OF_INPUT_NEURONS).forEach(i -> neurons[i] = new Neuron(layerType, 0));
				break;
	    	case HIDDEN:
	    		neurons = new Neuron[neuralNetwork.getNumbOfHiddenNeurons()];
				IntStream.range(0, neuralNetwork.getNumbOfHiddenNeurons()).forEach(i -> 
													neurons[i] = new Neuron(layerType,NeuralNetwork.NUMB_OF_INPUT_NEURONS));
	    		break;
	    	case OUTPUT:
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

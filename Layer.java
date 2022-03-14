import java.util.stream.IntStream;

// Represents a layer in the network
public class Layer {
	private Neuron[] neurons = null;	// each layer has an array of neurons that belong to it
	private e_layerType layerType;		// each layer also has a layer type
	
	public Layer(NeuralNetwork neuralNetwork, e_layerType layerType) {
		// initialises layer type, will do different things for different layer types
		this.layerType = layerType;
		switch(layerType) {
		case INPUT:
			// populate layer's neuron array with input neurons
			neurons = new Neuron[NeuralNetwork.NO_INPUT_NEURONS];
			IntStream.range(0, NeuralNetwork.NO_INPUT_NEURONS).forEach(i -> neurons[i] = new Neuron(layerType, 0));
			break;
		case HIDDEN:
			// populate layer's neuron array with hidden neurons
			neurons = new Neuron[neuralNetwork.getNoHiddrenNeurons()];
			IntStream.range(0, neuralNetwork.getNoHiddrenNeurons()).forEach(i -> neurons[i] = new Neuron(layerType, NeuralNetwork.NO_INPUT_NEURONS));
			break;
		case OUTPUT:
			// populate layer's neuron array with an output neurons
			neurons = new Neuron[NeuralNetwork.NO_OUTPUT_NEURONS];
			neurons[0] = new Neuron(layerType, neuralNetwork.getNoHiddrenNeurons());
			break;
		}
	}
	
	public Neuron[] getNeurons() {
		return neurons;
	}
	public e_layerType getLayerType() {
		return layerType;
	}
	
	public String toString() {
		StringBuffer returnValue = new StringBuffer();
		IntStream.range(0, neurons.length).forEach(x -> returnValue.append(neurons[x] + " "));
		return returnValue.toString();
	}
}

package skelton_ai;

import java.util.stream.IntStream;


public class Neuron {
	private double[] weights = null;
	private double output = 0;
	private double error = 0;
	private e_layerTypes layerType = null;
	
	public Neuron(e_layerTypes layerType, int numbOfWeights) { 
		/*
		 * Initialises a neuron; if the neuron does not belong to the input layer, randomly generate a set of weights for it
		 */
		this.layerType = layerType; 
		if (layerType != e_layerTypes.INPUT) {
			weights = new double[numbOfWeights];
			IntStream.range(0, numbOfWeights).forEach(x -> weights[x] = 0.5 - Math.random());
		}
	}
	
	public void applySigmoid(double weightedSum) { 	// apply the Sigmoid transfer function to calculate the output
		output =  1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}
	
	public double calcDerivative() { 	// uses output to calculate derivative
		return output * (1.0 - output);
	}
	public double[] getWeights() { 
		return weights; 
	}
	public double getOutput() { 
		return output; 
	}
	public void setOutput(double output) { 
		this.output = output; 
	}
	public double getError() { 
		return error; 
	}
	public void setError(double error) { 
		this.error = error; 
	}
	
	public String toString() {
		// toString method to display information about a neuron
		StringBuffer returnValue = new StringBuffer("(" + layerType + ", ");
		if (layerType == e_layerTypes.INPUT) returnValue.append(String.format("%.2f", output)+")");
		else {
			IntStream.range(0, weights.length).forEach(x -> returnValue.append(String.format("%.2f",weights[x])+", "));
			if (layerType == e_layerTypes.HIDDEN) returnValue.append(String.format("%.2f",output)+")");
			else returnValue.append(String.format("%.5f",output)+")");	
		}
		return returnValue.toString();
	}
}
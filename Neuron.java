import java.util.stream.IntStream;

public class Neuron {
	private double[] weights = null;
	private double output = 0;
	private double error = 0;
	private e_layerType layerType = null;
	
	// CONSTRUCTOR
	public Neuron(e_layerType layerType, int noWeights) {
		/* initialises layer type of a given neuron
		 * if the neuron belongs to the hidden or output layer, we will randomly initialise a weight for the neuron
		 */
		this.layerType = layerType;
		if (layerType != layerType.INPUT) {
			weights = new double[noWeights];
			IntStream.range(0, noWeights).forEach(x -> weights[x] = 0.5 - Math.random());
		}
	}
	
	// GETTERS
	public double[] getWeights() {
		return weights;
	}
	public double getOutput() {
		return output;
	}
	public double getError() {
		return error;
	}
	
	// SETTER
	public void setOutput(double output) {
		this.output = output;
	}
	public void setError(double error) {
		this.error = error;
	}
	
	// TOSTRING
	public String toString() {
		StringBuffer returnValue = new StringBuffer("(" + layerType + ", ");
		if (layerType == e_layerType.INPUT) {
			returnValue.append(String.format("%.2f", output) + ")");
		} else {
			IntStream.range(0, weights.length).forEach(x -> returnValue.append(String.format("%.2f", weights[x])+", "));
			if (layerType == e_layerType.HIDDEN) {
				returnValue.append(String.format("%.2f", output) + ")");
			} else {
				returnValue.append(String.format("%.5f", output) + ")");
			}
		}
		return returnValue.toString();
	}
	
	// METHODS
	public void applySigmoid(double weightedSum) {
		// apply the Sigmoid transfer function to calculate the output
		output = 1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}
	
	public double calculateDerivative() {
		// uses output to calculate derivative
		return output * (1.0 - output);
	}
}
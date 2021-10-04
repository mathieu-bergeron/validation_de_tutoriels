package tutoriels.core.performance_app;

import ca.ntro.core.system.debug.T;

public class TestParameters {
	
	private int minInputSize;
	private int maxInputSize;
	private int desiredSamples;
	private double estimatedMaxExecutionTime;
	private boolean animate;

	public TestParameters(int minInputSize, int maxInputSize, int numberOfSamples, double estimatedMaxExecutionTime, boolean animate) {
		super();
		T.call(this);
		this.minInputSize = minInputSize;
		this.maxInputSize = maxInputSize;
		this.desiredSamples = numberOfSamples;
		this.estimatedMaxExecutionTime = estimatedMaxExecutionTime;
		this.animate = animate;
	}

	public int getMinInputSize() {
		return minInputSize;
	}

	public void setMinInputSize(int minInputSize) {
		this.minInputSize = minInputSize;
	}

	public int getMaxInputSize() {
		return maxInputSize;
	}

	public void setMaxInputSize(int maxInputSize) {
		this.maxInputSize = maxInputSize;
	}
	
	public int getDesiredSamples() {
		return desiredSamples;
	}

	public void setDesiredSamples(int desiredSamples) {
		this.desiredSamples = desiredSamples;
	}

	public double getEstimatedMaxExecutionTime() {
		return estimatedMaxExecutionTime;
	}

	public void setEstimatedMaxExecutionTime(double estimatedMaxExecutionTime) {
		this.estimatedMaxExecutionTime = estimatedMaxExecutionTime;
	}
	
	public boolean getAnimate() {
		return animate;
	}
}

package tutoriels.core.performance_app.views;

import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.TestParameters;

public interface PerformanceGraphView extends NtroView {
	
	int addDataPoint(String seriesName, int inputSize);
	void setFinalExecutionTime(int id, double executionTimeSeconds);
	void setTestParameters(TestParameters parameters);

}

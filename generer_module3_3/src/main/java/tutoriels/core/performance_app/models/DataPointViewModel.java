package tutoriels.core.performance_app.models;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class DataPointViewModel extends DataPointModel implements ViewModel {
	private static final long serialVersionUID = 7911389583659316826L;

	public DataPointViewModel(int inputSize, double executionTimeSeconds) {
		super(inputSize, executionTimeSeconds);
		T.call(this);
	}

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		PerformanceGraphView graphView = (PerformanceGraphView) view;
		
		getObservableExecutionTimeSeconds().observe(new ValueObserver<Double>() {
			
			@Override
			public void onDeleted(Double lastValue) {
			}
			
			@Override
			public void onValue(Double value) {
			}
			
			@Override
			public void onValueChanged(Double oldValue, Double newValue) {
				T.call(this);
				graphView.setFinalExecutionTime(getId(), newValue);
			}
		});
		
	}

}

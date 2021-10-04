package tutoriels.core.performance_app.models;

import java.util.List;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class DataCategoryViewModel extends DataCategoryModel implements ViewModel {
	private static final long serialVersionUID = -6452661591741606335L;

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);

		PerformanceGraphView graphView = (PerformanceGraphView) view;
		
		getObservableTitle().get(new ValueListener<String>() {
			@Override
			public void onValue(String seriesName) {

				getDataPoints().observe(new ListObserver<DataPointViewModel>() {
					public void onItemRemoved(int index, DataPointViewModel item) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onItemAdded(int index, DataPointViewModel item) {
						T.call(this);
						int id = graphView.addDataPoint(seriesName, item.getInputSize());
						item.setId(id);
						item.observeAndDisplay(graphView);
					}

					@Override
					public void onItemUpdated(int index, DataPointViewModel item) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onDeleted(List<DataPointViewModel> lastValue) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onValue(List<DataPointViewModel> value) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onValueChanged(List<DataPointViewModel> oldValue, List<DataPointViewModel> newValue) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
	}
}

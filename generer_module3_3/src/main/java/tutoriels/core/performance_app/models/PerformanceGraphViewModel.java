package tutoriels.core.performance_app.models;



import java.util.List;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class PerformanceGraphViewModel extends PerformanceGraphModel implements ViewModel {
	private static final long serialVersionUID = -526955338839690480L;

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);

		PerformanceGraphView graphView = (PerformanceGraphView) view;
		
		getObservableTestParameters().observe(new ValueObserver<TestParameters>() {
			
			@Override
			public void onDeleted(TestParameters lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(TestParameters value) {
				graphView.setTestParameters(value);
			}
			
			@Override
			public void onValueChanged(TestParameters oldValue, TestParameters newValue) {
				graphView.setTestParameters(newValue);
			}
		});
		
		getObservableDataCategories().observe(new ListObserver<DataCategoryViewModel>() {
			
			@Override
			public void onItemRemoved(int index, DataCategoryViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, DataCategoryViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, DataCategoryViewModel item) {
				T.call(this);
				
				item.observeAndDisplay(graphView);
			}
			
			@Override
			public void onDeleted(List<DataCategoryViewModel> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<DataCategoryViewModel> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<DataCategoryViewModel> oldValue, List<DataCategoryViewModel> newValue) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
	}


}

package tutoriels.core.performance_app.models;


import java.util.List;

import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.list.ListObserver;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.MainPerformanceAppView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class PerformanceTestsViewModel extends PerformanceTestsModel implements ViewModel {
	private static final long serialVersionUID = -2849937081961960593L;

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);
		
		MainPerformanceAppView mainView = (MainPerformanceAppView) view;
		
		getPerformanceGraphs().observe(new ListObserver<PerformanceGraphViewModel>() {
			
			@Override
			public void onItemRemoved(int index, PerformanceGraphViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemUpdated(int index, PerformanceGraphViewModel item) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onItemAdded(int index, PerformanceGraphViewModel item) {
				T.call(this);

				PerformanceGraphView graphView = mainView.addGraphView(item.getTitle());
				item.observeAndDisplay(graphView);
			}
			
			@Override
			public void onDeleted(List<PerformanceGraphViewModel> lastValue) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValue(List<PerformanceGraphViewModel> value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onValueChanged(List<PerformanceGraphViewModel> oldValue, List<PerformanceGraphViewModel> newValue) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}

	
	
}

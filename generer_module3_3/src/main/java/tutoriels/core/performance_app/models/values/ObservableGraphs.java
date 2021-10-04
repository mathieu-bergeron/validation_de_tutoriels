package tutoriels.core.performance_app.models.values;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.models.PerformanceGraphViewModel;

public class ObservableGraphs extends ObservableList<PerformanceGraphViewModel> {
	private static final long serialVersionUID = 6368851819599265589L;

	public ObservableGraphs(List<PerformanceGraphViewModel> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return PerformanceGraphViewModel.class;
	}



}

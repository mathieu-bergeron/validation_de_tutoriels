package tutoriels.core.performance_app.models.values;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.models.DataPointViewModel;

@SuppressWarnings("serial")
public class ObservableDataPoints extends ObservableList<DataPointViewModel> {


	public ObservableDataPoints(List<DataPointViewModel> value) {
		super(value);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return DataPointViewModel.class;
	}

}

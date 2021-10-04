package tutoriels.core.performance_app.models.values;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.models.DataCategoryViewModel;

public class ObservableDataCategories extends ObservableList<DataCategoryViewModel> {
	private static final long serialVersionUID = -6368903140498397238L;

	public ObservableDataCategories(List<DataCategoryViewModel> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return DataCategoryViewModel.class;
	}
}

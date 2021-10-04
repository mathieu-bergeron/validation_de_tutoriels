package tutoriels.core.performance_app.models.values;

import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import tutoriels.core.performance_app.TestParameters;

public class ObservableTestParameters extends ObservableProperty<TestParameters>{
	private static final long serialVersionUID = 6189533484500081758L;

	@Override
	protected Class<?> getValueType() {
		return TestParameters.class;
	}

}

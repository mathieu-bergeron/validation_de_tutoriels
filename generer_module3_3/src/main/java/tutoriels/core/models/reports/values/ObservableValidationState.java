package tutoriels.core.models.reports.values;

import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.system.debug.T;

@SuppressWarnings("serial")
public class ObservableValidationState extends ObservableProperty<ValidationState>{
	
	public ObservableValidationState(ValidationState value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return ValidationState.class;
	}

}

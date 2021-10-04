package ca.ntro.core.models.properties.observable.simple;

import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;

public class ObservableValue<M extends ModelValue> extends ObservableProperty<M> {
	
	public ObservableValue() {
		super();
		T.call(this);
	}

	public ObservableValue(M value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return ModelValue.class;
	}
}

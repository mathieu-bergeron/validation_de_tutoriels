package ca.ntro.core.models.properties.observable.simple;

import ca.ntro.core.system.debug.T;

public class ObservableBoolean extends ObservableProperty<Boolean> {

	public ObservableBoolean() {
		super();
		T.call(this);
	}

	public ObservableBoolean(Boolean value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		return Boolean.class;
	}
}

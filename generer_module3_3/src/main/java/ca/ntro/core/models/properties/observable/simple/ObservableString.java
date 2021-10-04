package ca.ntro.core.models.properties.observable.simple;

import ca.ntro.core.system.debug.T;

public class ObservableString extends ObservableProperty<String> {

	public ObservableString() {
		super();
		T.call(this);
	}

	public ObservableString(String value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		return String.class;
	}
}

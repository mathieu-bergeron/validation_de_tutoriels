package ca.ntro.core.models.properties.observable.simple;

import ca.ntro.core.system.debug.T;

public class ObservableDouble extends ObservableProperty<Double> {

	public ObservableDouble() {
		super();
		T.call(this);
	}

	public ObservableDouble(double value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		return Double.class;
	}
}

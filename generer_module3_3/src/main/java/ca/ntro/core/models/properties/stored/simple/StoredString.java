package ca.ntro.core.models.properties.stored.simple;

import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.system.debug.T;

public class StoredString extends StoredProperty<String> {
	
	public StoredString() {
		super("");
		T.call(this);
	}

	public StoredString(String value) {
		super(value);
		T.call(this);
	}


	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return String.class;
	}

	public void test() {
		T.call(this);
	}


}

package ca.ntro.core.models.properties.stored.simple;

import ca.ntro.core.system.debug.T;

public class StoredBoolean extends StoredProperty<Boolean> {
	
	public StoredBoolean() {
		super(false);
		T.call(this);
	}

	public StoredBoolean(Boolean value) {
		super(value);
		T.call(this);
	}


	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return Boolean.class;
	}


}

package ca.ntro.core.models;

import ca.ntro.core.models.stores.ValuePath;

public interface StoreConnectable {
	
	void connectToStore(ValuePath valuePath, ModelStore modelStore);

}

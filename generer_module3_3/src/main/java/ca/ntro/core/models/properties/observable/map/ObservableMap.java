package ca.ntro.core.models.properties.observable.map;

import java.util.Map;

import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.system.debug.T;

public abstract class ObservableMap<V extends Object> extends StoredProperty<Map<String, V>> {

	public ObservableMap(Map<String, V> value) {
		super(value);
		T.call(this);
	}

	public void addEntry(String key, V value) {
		
		//getValue().put(key, value);
		
		// FIXME: go through the  Backend
		//getObserver().onEntryAdded(key, value);
		
	}
	
	public void removeEntry(String key) {
		
		//V value = getValue().get(key);
		//getValue().remove(key);
		
		// FIXME: go through the Backend
		//getObserver().onEntryRemoved(key, value);
		
	}
	

}

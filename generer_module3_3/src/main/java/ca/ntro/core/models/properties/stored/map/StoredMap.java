package ca.ntro.core.models.properties.stored.map;

import java.util.Map;

import ca.ntro.core.models.properties.observable.map.ObservableMap;
import ca.ntro.core.system.debug.T;

public abstract class StoredMap<V extends Object> extends ObservableMap<V> {

	public StoredMap(Map<String, V> value) {
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

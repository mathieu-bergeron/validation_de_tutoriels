package ca.ntro.core.models.properties.observable.map;

import java.util.Map;

import ca.ntro.core.models.properties.observable.simple.ValueObserver;

public interface MapObserver<V extends Object> extends ValueObserver<Map<String, V>> {
	
	void onEntryAdded(String key, V value);
	void onEntryRemoved(String key, V value);

}

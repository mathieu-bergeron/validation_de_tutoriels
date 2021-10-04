package ca.ntro.core.models.properties.observable.simple;

public interface ValueListener<V extends Object> {
	
	void onValue(V value);

}

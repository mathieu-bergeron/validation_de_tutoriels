package ca.ntro.core.models.properties.observable.simple;

public interface ValueObserver<V extends Object> extends ValueListener<V>, DeletionListener<V> {
	
	void onValueChanged(V oldValue, V value);

}

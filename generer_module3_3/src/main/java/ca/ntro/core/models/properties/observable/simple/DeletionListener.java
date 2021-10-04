package ca.ntro.core.models.properties.observable.simple;

public interface DeletionListener<V extends Object> {
	
	void onDeleted(V lastValue);

}

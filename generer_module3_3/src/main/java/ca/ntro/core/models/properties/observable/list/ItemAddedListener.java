package ca.ntro.core.models.properties.observable.list;

public interface ItemAddedListener<I extends Object>{
	
	void onItemAdded(int index, I item);

}

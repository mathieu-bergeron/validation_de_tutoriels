package ca.ntro.core.models.properties.observable.list;

public interface ItemRemovedListener<I extends Object>{
	
	void onItemRemoved(int index, I item);

}

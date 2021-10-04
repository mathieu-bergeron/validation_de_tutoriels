package ca.ntro.core.models.properties.observable.list;

public interface ItemUpdatedListener<I extends Object>{

	void onItemUpdated(int index, I item);

}

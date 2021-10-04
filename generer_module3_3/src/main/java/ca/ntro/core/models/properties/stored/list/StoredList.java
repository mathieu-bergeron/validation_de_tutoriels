package ca.ntro.core.models.properties.stored.list;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ItemAddedListener;
import ca.ntro.core.models.properties.observable.list.ItemRemovedListener;
import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.system.debug.T;

public abstract class StoredList<I extends Object> extends ObservableList<I> {
	
	public StoredList(List<I> value) {
		super(value);
		T.call(this);
	}

	public StoredList<I> slice(int firstIndex, int lastIndex){
		
		// TODO
		return null;
	}
	
	public int size() {
		
		// TODO
		return 0;
	}
	
	public StoredProperty<I> first(){
		
		return get(0);
	}

	public StoredProperty<I> last(){
		
		return get(size()-1);
	}

	public StoredProperty<I> get(int index){
		
		// TODO
		return null;
	}

	public void addItem(I item) {
		
		// FIXME: this has to go through
		//        the backend
		//getValue().add(item);
		//getObserver().onItemAdded(item);
	}

	public void removeItem(I item) {
		
		// FIXME: this has to go through
		//        the backend
		//int index = getValue().indexOf(item);
		//getValue().remove(item);
		//getObserver().onItemRemoved(index, item);
	}
	
	public void setOnItemAddedListener(ItemAddedListener<I> itemAddedListener) {
		
	}

	public void setOnItemRemovedListener(ItemRemovedListener<I> itemRemovedListener) {
		
	}
}

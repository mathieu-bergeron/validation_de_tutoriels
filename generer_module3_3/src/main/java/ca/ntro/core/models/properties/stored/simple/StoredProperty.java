package ca.ntro.core.models.properties.stored.simple;

import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.StoreConnectable;
import ca.ntro.core.models.properties.observable.simple.DeletionListener;
import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.models.stores.ValuePath;
import ca.ntro.core.system.debug.T;

public abstract class StoredProperty<V extends Object> extends ObservableProperty<V> implements StoreConnectable {
	

	private ValueListener<V> valueListener;
	private DeletionListener<V> deletionListener;
	private ValueObserver<V> observer;
	
	private ValuePath valuePath;
	private ModelStore modelStore;
	
	public void setValuePath(ValuePath valuePath) {
		T.call(this);
		
		this.valuePath = valuePath;
	}

	public StoredProperty() {
		super();
		T.call(this);
	}

	public StoredProperty(V value) {
		super(value);
		T.call(this);
	}
	
	
	public void get(ValueListener<V> valueListener) {
		// TODO
		//storedJsonValue.get(valueListener);
		
	}
	

	public void onDeleted(DeletionListener<V> deletionListener) {
		//storedJsonValue.onDeleted(deletionListner);
	}

	public void set(V newValue) {
		T.call(this);
		
		// FIXME: the store should call the observer
		/*
		V oldValue = getValue();
		if(oldValue != null) {
			if(observer != null) {
				observer.onValueChanged(oldValue, newValue);
			}
		}else {
			if(observer != null) {
				observer.onValue(newValue);
			}
		}*/
		
		setValue(newValue);
		
		if(valuePath != null && modelStore != null) {
			
			modelStore.setValue(valuePath, this);
		}
	}
	
	public void observe(ValueObserver<V> observer) {
		T.call(this);

		this.observer = observer;
		
		if(getValue() != null) {
			
			// FIXME: this should not be necessary
			if(getValue() instanceof ObservableProperty) {
				
				observer.onValue((V) ((ObservableProperty)getValue()).getValue());
				
			}else {
				
				observer.onValue(getValue());
			}
		}
	}

	@Override
	public void connectToStore(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);
		
		this.valuePath = valuePath;
		this.modelStore = modelStore;
		
		modelStore.addValueListener(valuePath, new ValueListener<V>() {

			@Override
			public void onValue(V newValue) {
				T.call(this);

				V oldValue = getValue();
				setValue(newValue);
				
				if(valueListener != null) {
					valueListener.onValue(newValue);
				}
				
				if(observer != null) {
					
					if(oldValue == null) {
						
						observer.onValue(newValue);
						
					}else {
						
						observer.onValueChanged(oldValue, newValue);
						
					}

				}
			}
		});
	}
}

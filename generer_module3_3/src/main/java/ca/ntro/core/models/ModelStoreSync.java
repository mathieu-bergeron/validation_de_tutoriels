package ca.ntro.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.Factory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ExternalUpdateListener;
import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.stored.simple.StoredProperty;
import ca.ntro.core.models.stores.ValuePath;
import ca.ntro.core.system.debug.T;

@SuppressWarnings("unchecked")
public abstract class ModelStoreSync extends ModelStore {
	
	// XXX: not using multimap as not sure how JSweet would handle it
	// FIXME: store the ValuePath hierarchy here? When updating a.b.c.d, we also need to call listener a.b.c
	private Map<ValuePath, List<ValueListener>> listeners = new HashMap<>();
	
	// XXX: must keep an object cache. 
	//      when a model exists in memory, we want to return the exact same object
	//      otherwise, observers won't work (as we'll observe a different object that the one being modified
	private Map<Class<? extends Model>, Map<String, Model>> modelCache = new HashMap<>();
	
	public ModelStoreSync() {
		super();
		T.call(this);
		
		installExternalUpdateListener(new ExternalUpdateListener() {
			
			@Override
			public void onExternalUpdate() {
				T.call(this);

				for(ValuePath valuePath : listeners.keySet()) {
					
					List<ValueListener> theseListeners = listeners.get(valuePath);
					
					if(theseListeners != null && theseListeners.size() > 0) {

						Object value = getValue(valuePath);
						
						for(ValueListener listener : theseListeners) {

							listener.onValue(value);
						}
					}
				}
			}
		});
	}
	
	public abstract void close();

	protected abstract void installExternalUpdateListener(ExternalUpdateListener updateListener);
	
	public <M extends Model> M getModel(Class<M> modelClass, String modelId){
		T.call(this);
		
		M model = getModelFromCache(modelClass, modelId);
		
		if(model == null) {
			
		  model = getModelFromStoredData(modelClass, modelId);
		  
		  addModelToCache(modelClass, modelId, model);
		}
		
		return model;
	}

	private <M extends Model> M getModelFromStoredData(Class<M> modelClass, String modelId) {
		T.call(this);

		M model = null;
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), modelId);
		
		JsonObject jsonObject = getJsonObject(documentPath);
		
		model = Factory.newInstance(modelClass);
		
		model.setId(modelId);
		
		model.setOrigin(this);
		
		if(!jsonObject.isEmpty()) {
			model.loadFromJsonObject(jsonObject);
		}

		model.connectStoredValues();
		
		return model;
	}
	
	private <M extends Model> M  getModelFromCache(Class<M> modelClass, String modelId) {
		
		M result = null;
		
		Map<String, Model> innerCache = modelCache.get(modelClass);
		
		if(innerCache != null) {
			
			result = (M) innerCache.get(modelId);
		}
		
		return result;
	}

	private <M extends Model> void addModelToCache(Class<M> modelClass, String modelId, M model) {

		Map<String, Model> innerCache = modelCache.get(modelClass);
		
		if(innerCache == null) {
			
			innerCache = new HashMap<>();
			modelCache.put(modelClass, innerCache);
		}
		
		innerCache.put(modelId, model);
	}

	protected abstract JsonObject getJsonObject(DocumentPath documentPath);

	public Object getValue(ValuePath valuePath) {
		T.call(this);
		
		DocumentPath documentPath = valuePath.extractDocumentPath();
		
		JsonObject storedObject = getJsonObject(documentPath);
		
		return valuePath.getValueFrom(storedObject);
	}

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		T.call(this);
		
		List<ValueListener> theseListeners = listeners.get(valuePath);
		
		if(theseListeners == null) {
			
			theseListeners = new ArrayList<>();
			listeners.put(valuePath, theseListeners);
		}
		
		theseListeners.add(valueListener);
		
		Object value = getValue(valuePath);
		
		valueListener.onValue(value);
	}

	@Override
	public <V extends Object> void setValue(ValuePath valuePath, V value) {
		T.call(this);

		DocumentPath documentPath = valuePath.extractDocumentPath();
		
		JsonObject storedObject = getJsonObject(documentPath);
		
		// FIXME: ulgy
		Object valueToSave = value;
		if(value instanceof JsonObjectIO) {
			valueToSave =  (((JsonObjectIO)value).toJsonObject().toMap());
		}
		
		Object oldValue = valuePath.updateObject(storedObject, valueToSave);
		
		saveJsonObject(documentPath, storedObject);
		
		// FIXME: ulgy
		Object valueForListeners = value;
		if(value instanceof ObservableProperty) {
			valueForListeners = ((ObservableProperty) value).getValue();
		}
		
		callListeners(valuePath, valueForListeners);

		// XXX: in JSweet, the onStorage event is NOT CALLED when we 
		//      change the storage, only when some other tab
		//      changes it
		
	}

	private <V> void callListeners(ValuePath valuePath, V value) {
		T.call(this);

		List<ValueListener> theseListeners = listeners.get(valuePath);
		
		if(theseListeners != null) {
			
			for(ValueListener listener : theseListeners) {
				listener.onValue(value);
			}
		}
	}
}

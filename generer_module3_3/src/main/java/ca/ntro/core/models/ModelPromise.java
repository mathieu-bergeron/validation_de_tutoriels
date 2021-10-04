package ca.ntro.core.models;

import ca.ntro.core.Factory;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;
import ca.ntro.core.system.debug.T;

public class ModelPromise<M extends Model> implements Promise<M>{
	
	private Class<M> modelClass;
	private Promise<JsonObject> jsonObjectPromise;
	private String modelId;
	private ModelStore store;
	
	public ModelPromise(Class<M> modelClass, String modelId, ModelStore store, Promise<JsonObject> jsonObjectPromise) {
		T.call(this);
		this.modelClass = modelClass;
		this.modelId = modelId;
		this.jsonObjectPromise = jsonObjectPromise;
		this.store = store;
	}

	@Override
	public void onValueOrError(ValueListener<M> valueListener, ErrorListener errorListener) {
		T.call(this);
		
		jsonObjectPromise.onValueOrError(new ValueListener<JsonObject>() {

			@Override
			public void onValue(JsonObject jsonObject) {
				T.call(this);
				
				M model = Factory.newInstance(modelClass);
				
				model.setId(modelId);
				
				model.setOrigin(store);
				
				if(!jsonObject.isEmpty()) {
					
					model.loadFromJsonObject(jsonObject);
					
				}

				model.connectStoredValues();
				
				valueListener.onValue(model);
			}
		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
				errorListener.onError();
				
			}
		});
		
	}

}

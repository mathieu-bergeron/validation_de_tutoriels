package ca.ntro.core.models;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.system.debug.T;

public abstract class ModelStoreAsync extends ModelStore {

	public <M extends Model> Promise<M> getModel(Class<M> modelClass, String modelId){
		T.call(this);
		
		Promise<M> modelPromise = null;
		
		DocumentPath documentPath = new DocumentPath(modelClass.getSimpleName(), modelId);
		
		Promise<JsonObject> promiseJsonObject = getJsonObject(documentPath);
		
		modelPromise = new ModelPromise<M>(modelClass, modelId, this, promiseJsonObject);
		
		return modelPromise;
	}

	protected abstract Promise<JsonObject> getJsonObject(DocumentPath documentPath);

	protected abstract void saveJsonObject(JsonObject jsonObject, DocumentPath documentPath);
	


}

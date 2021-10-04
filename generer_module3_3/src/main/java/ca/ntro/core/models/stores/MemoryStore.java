package ca.ntro.core.models.stores;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.Model;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.debug.T;

public class MemoryStore extends ModelStoreSync {
	
	// XXX: we need ConcurrentHashMap as MemoryStore is accessed from different threads
	private Map<DocumentPath, JsonObject> values = NtroCollections.concurrentHashMap(new HashMap<DocumentPath, JsonObject>());
	
	private static MemoryStore instance = new MemoryStore();

	// XXX: must synchronize here as get can be called from multiple threads
	public synchronized static <M extends Model> M get(Class<M> modelClass, String modelId) {
		T.call(MemoryStore.class);

		M result = instance.getModel(modelClass, modelId);
		
		return result;
	}

	public synchronized static void clearStore() {
		T.call(MemoryStore.class);

		instance.values.clear();
	}

	@Override
	protected JsonObject getJsonObject(DocumentPath documentPath) {
		T.call(this);
		
		JsonObject result = values.get(documentPath);
		
		// XXX: creates a new JsonObject when null
		if(result == null) {
			
			result = JsonParser.jsonObject();

			values.put(documentPath, result);
		}

		return result;
	}

	@Override
	public void addValueListener(ValuePath valuePath, ValueListener valueListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <V extends Object> void setValue(ValuePath valuePath, V value) {
		T.call(this);
		
		JsonObject document = values.get(valuePath.extractDocumentPath());
		
		valuePath.updateObject(document, value);
	}

	@Override
	protected void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void installExternalUpdateListener(ExternalUpdateListener updateListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		T.call(this);
	}


}

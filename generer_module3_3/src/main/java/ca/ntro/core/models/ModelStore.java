package ca.ntro.core.models;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.stores.DocumentPath;
import ca.ntro.core.models.stores.ValuePath;

public abstract class ModelStore {
	
	public static final String MODEL_ID_KEY="modelId";
	public static final String MODEL_DATA_KEY="modelData";

	public abstract void addValueListener(ValuePath valuePath, ValueListener valueListener);

	// XXX: value could be a JsonObjectIO or a plain Java value
	public abstract <V extends Object> void setValue(ValuePath valuePath, V value);

	protected abstract void saveJsonObject(DocumentPath documentPath, JsonObject jsonObject);


}

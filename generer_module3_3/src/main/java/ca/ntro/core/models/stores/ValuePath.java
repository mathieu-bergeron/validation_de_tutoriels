package ca.ntro.core.models.stores;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.system.debug.T;

public class ValuePath {
	
	private String collection;
	private String documentId;
	private List<String> fieldPath = new ArrayList<>();
	
	private ValuePath(String collection) {
		this.collection = collection;
		
	}
	
	public static ValuePath collection(String collection) {
		T.call(ValuePath.class);
		return new ValuePath(collection);
	}
	
	public ValuePath documentId(String documentId) {
		T.call(this);
		this.documentId = documentId;
		return this;
	}
	
	public ValuePath field(String... fieldPath) {
		T.call(this);
		
		for(String fieldName : fieldPath) {
			this.fieldPath.add(fieldName);
		}

		return this;
	}
	
	public DocumentPath extractDocumentPath() {
		T.call(this);
		return new DocumentPath(collection, documentId);
	}

	public void addFieldName(String fieldName) {
		T.call(this);
		
		this.fieldPath.add(fieldName);
	}

	public Object updateObject(JsonObject storedObject, Object value) {
		T.call(this);
		
		return storedObject.updateValue(fieldPath, value);
	}

	public Object getValueFrom(JsonObject storedObject) {
		T.call(this);

		return storedObject.getValue(fieldPath);
	}

}

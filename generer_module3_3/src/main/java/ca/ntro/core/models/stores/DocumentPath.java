package ca.ntro.core.models.stores;

public class DocumentPath {
	
	private String collection;
	private String documentId;
	
	public DocumentPath(String collection, String documentId) {
		this.collection = collection;
		this.documentId = documentId;
	}

	public String getCollection() {
		return collection;
	}

	public String getId() {
		return documentId;
	}

}

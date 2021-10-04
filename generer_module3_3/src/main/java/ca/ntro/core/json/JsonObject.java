package ca.ntro.core.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.system.debug.T;

@SuppressWarnings("serial")
public class JsonObject implements Serializable {

	public static final String TYPE_KEY = "_T";

	private Map<String, Object> map;
	
	public JsonObject(Map<String, Object> map) {
		T.call(this);

		this.map = map;
	}
	
	public String getTypeName() {
		T.call(this);
		
		return (String) this.get(TYPE_KEY);
	}

	public void setTypeName(String typeName) {
		T.call(this);
		
		this.put(TYPE_KEY, typeName);
	}
	
	public Map<String, Object> toMap() {
		T.call(this);
		return map;
	}
	
	@Override
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		for(String key : map.keySet()) {
			
			Object value = map.get(key);
			
			System.out.println(key + " " + value + ", ");
		}
		
		
		return builder.toString();
	}

	public int size() {
		T.call(this);
		return map.size();
	}

	public boolean isEmpty() {
		T.call(this);
		return map.isEmpty();
	}

	public boolean containsKey(Object key) {
		T.call(this);
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		T.call(this);
		return map.containsValue(value);
	}

	public Object get(Object key) {
		T.call(this);
		return map.get(key);
	}

	@SuppressWarnings("unchecked")
	public JsonObject getSubObject(Object key) {
		T.call(this);
		return new JsonObject((Map<String, Object>) map.get(key));
	}

	public Object put(String key, Object value) {
		T.call(this);
		return map.put(key, value);
	}

	public Object remove(Object key) {
		T.call(this);
		return map.remove(key);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		T.call(this);
		map.putAll(m);
	}

	public void clear() {
		T.call(this);
		map.clear();
	}

	public Set<String> keySet() {
		T.call(this);
		return map.keySet();
	}

	public Collection<Object> values() {
		T.call(this);
		return map.values();
	}

	public Object updateValue(List<String> fieldPath, Object value) {
		T.call(this);
		
		Object oldValue = null;
		
		if(fieldPath.size() == 1) {

			String fieldName = fieldPath.get(0);

			oldValue = this.put(fieldName, value);
			
		}else {

			String fieldName = fieldPath.get(0);
			List<String> subFieldPath = fieldPath.subList(1, fieldPath.size()-1);
			JsonObject subObject = getSubObject(fieldName);
			
			oldValue = subObject.updateValue(subFieldPath, value);
		}
		
		return oldValue;
	}

	public Object getValue(List<String> fieldPath) {
		T.call(this);

		Object value = null;
		
		if(fieldPath.size() == 1) {

			String fieldName = fieldPath.get(0);
			value = this.get(fieldName);
			
		}else {

			String fieldName = fieldPath.get(0);
			List<String> subFieldPath = fieldPath.subList(1, fieldPath.size()-1);
			JsonObject subObject = getSubObject(fieldName);
			
			value = subObject.getValue(subFieldPath);
		}
		
		return value;
	}
}

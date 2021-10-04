package ca.ntro.core.json;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class JsonObjectIO implements Serializable {
	
	// FIXME: would be better package-private if possible
	public void loadFromJsonObject(JsonObject jsonObject) {
		T.call(this);

		for(String fieldName : jsonObject.keySet()) {
			
			Object jsonValue = jsonObject.get(fieldName);

			Method setter = Introspector.findSetter(this.getClass(), fieldName);
			
			if(setter != null) {

				try {

					Object setterValue = Introspector.buildValueForSetter(setter, jsonValue);
					
					setter.invoke(this, setterValue);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					
					Log.fatalError("[JsonObjectIO] Cannot invoke setter " + setter.getName(), e);
				}
			}
		}
	}
	
	public JsonObject toJsonObject() {
		T.call(this);
		
		JsonObject jsonObject = JsonParser.jsonObject();

		jsonObject.setTypeName(this.getClass().getName());
		
		for(Method getter : Introspector.userDefinedGetters(this)) {
			
			Object value = null;
			
			try {
				
				value = getter.invoke(this);
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Cannot invoke getter " + getter.getName(), e);

			}

			String fieldName = Introspector.fieldNameForGetter(getter);
			
			// TODO: go inside a Map or a List to look
			//       for user-defined values (JsonObjectIO)
			Object jsonValue = buildJsonValue(value);
			
			jsonObject.put(fieldName, jsonValue);
		}
		
		return jsonObject;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object buildJsonValue(Object value) {
		
		Object jsonValue = value;

		if(value instanceof JsonObjectIO) {

			jsonValue = ((JsonObjectIO) value).toJsonObject().toMap();

		}else if(value instanceof Map) {
			
			HashMap result = new HashMap();
			
			Map map = (Map) value;
			
			Set keySet = map.keySet();
			
			for(Object key : keySet) {
				
				Object mapValue = map.get(key);
				
				Object jsonMapValue = buildJsonValue(mapValue);
				
				result.put(key, jsonMapValue);
			}
			
			jsonValue = result;
			
		}else if(value instanceof List) {
			
			List result = new ArrayList();
			
			List list = (List) value;
			
			for(Object item : list) {
				
				Object jsonItem = buildJsonValue(item);
				
				result.add(jsonItem);
			}
			
			jsonValue = result;
		}
		
		return jsonValue;
	}

}

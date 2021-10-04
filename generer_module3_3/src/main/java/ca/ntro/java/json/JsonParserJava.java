package ca.ntro.java.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.debug.T;

public class JsonParserJava extends JsonParser {

	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	protected JsonObject jsonObjectImpl() {
		T.call(this);
		return new JsonObject(new HashMap<String, Object>());
	}

	@Override
	protected JsonObject fromStringImpl(String jsonString) {
		T.call(this);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = gson.fromJson(jsonString, HashMap.class);
		
		return new JsonObject(jsonMap);
	}

	@Override
	protected JsonObject fromStreamImpl(InputStream jsonStream) {
		T.call(this);
		
		InputStreamReader jsonReader = new InputStreamReader(jsonStream);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = gson.fromJson(jsonReader, HashMap.class);
		
		return new JsonObject(jsonMap);
	}

	@Override
	protected String toStringImpl(JsonObject jsonObject) {
		T.call(this);
		return gson.toJson(jsonObject.toMap());
	}
}

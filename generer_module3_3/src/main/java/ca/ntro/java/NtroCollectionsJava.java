package ca.ntro.java;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ca.ntro.core.system.NtroCollections;

@SuppressWarnings({"unchecked", "rawtypes"})
public class NtroCollectionsJava extends NtroCollections {

	@Override
	public List synchronizedListImpl(List elements) {
		return Collections.synchronizedList(elements);
		
	}

	@Override
	public Map concurrentHashMapImpl(Map elements) {
		Map concurrentHashMap = new ConcurrentHashMap();
		concurrentHashMap.putAll(elements);
		return concurrentHashMap;
	}
}

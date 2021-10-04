package ca.ntro.core.system;

import java.util.List;
import java.util.Map;

import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

@SuppressWarnings("rawtypes")
public abstract class NtroCollections {
	
	private static NtroCollections instance;
	
	public static void initialize(NtroCollections instance) {
		T.call(NtroCollections.class);
		
		NtroCollections.instance = instance;
	}

	public abstract <I extends Object> List<I> synchronizedListImpl(List<I> elements);

	public static <I extends Object> List<I> synchronizedList(List<I> elements) {
		T.call(NtroCollections.class);
		
		List<I> synchronizedList = null;
		
		try {
			
			synchronizedList = instance.synchronizedListImpl(elements);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}
		
		return synchronizedList;
	}

	public abstract <K extends Object, V extends Object> Map<K,V> concurrentHashMapImpl(Map<K,V> elements);

	public static <K extends Object, V extends Object> Map<K,V> concurrentHashMap(Map<K,V> elements) {
		T.call(NtroCollections.class);
		
		Map<K,V> concurrentHashMap = null;
		
		try {
			
			concurrentHashMap = instance.concurrentHashMapImpl(elements);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(NtroCollections.class.getSimpleName() + " must be initialized");
		}

		return concurrentHashMap;
	}

}

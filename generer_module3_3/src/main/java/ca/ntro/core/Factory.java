package ca.ntro.core;

import ca.ntro.core.system.log.Log;

public class Factory {
	
	public static <O extends Object> O newInstance(Class<O> instanceType) {
		
		O instance = null;
		
		try {

			instance = instanceType.newInstance();

		} catch (InstantiationException | IllegalAccessException e) {
			
			Log.fatalError("FATAL cannot instantiate " + instanceType.getSimpleName(), e);

		}
		
		return instance;
	}
	
	
	

}

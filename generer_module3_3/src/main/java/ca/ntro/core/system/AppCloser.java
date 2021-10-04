package ca.ntro.core.system;

import ca.ntro.core.system.debug.T;

public abstract class AppCloser {
	
	private static AppCloser instance;
	
	public static void initialize(AppCloser instance) {
		AppCloser.instance = instance;
	}
	
	protected abstract void closeImpl();

	public static void close() {
		T.call(AppCloser.class);
		
		try {
			
			instance.closeImpl();

		}catch(NullPointerException e) {
			
			System.out.println("FATAL");
			
		}
	}


}

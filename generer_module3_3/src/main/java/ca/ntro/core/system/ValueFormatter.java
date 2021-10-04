package ca.ntro.core.system;

import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class ValueFormatter {
	
	private static ValueFormatter instance;
	private static boolean isHtml = false;
	
	public static void initialize(ValueFormatter instance) {
		T.call(ValueFormatter.class);
		
		ValueFormatter.instance = instance;
	}

	public static void setIsHtml(boolean isHtml){
		T.call(ValueFormatter.class);
		
		ValueFormatter.isHtml = isHtml;
	}
	
	public abstract void formatImpl(StringBuilder builder, boolean isHtml, Object... values);

	public static void format(StringBuilder builder, Object... values) {
		T.call(ValueFormatter.class);
		
		try {
			
			instance.formatImpl(builder, isHtml, values);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(ValueFormatter.class.getSimpleName() + " must be initialized");
			
		}
	}
	

}

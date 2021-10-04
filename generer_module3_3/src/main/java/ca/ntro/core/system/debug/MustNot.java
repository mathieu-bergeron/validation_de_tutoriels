package ca.ntro.core.system.debug;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class MustNot {
	
	public static void beNull(Object value) {
		if(value == null) {
			
			StringBuilder builder = new StringBuilder();
			
			int stackOffset = 1;
			
			// TODO: refactor to print better info
			StackFrame tracedFrame = StackAnalyzer.getTracedFrame(null, stackOffset);
			tracedFrame.printSourceLocation(builder);

			Log.fatalError("null value [" + builder.toString() + "]  ");
		}
	}

	public static void beTrue(boolean value) {
		if(value) {
			
			Log.fatalError("true value");
		}
	}

}

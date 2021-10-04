package ca.ntro.core.system.log;

import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class Log {

	public static void warning(String[] messages) {

	}

	public static void error(String[] messages) {

	}

	
	public static void fatalError(String message, Exception... causedBy) {
		int currentDepth = 1;
		StackFrame tracedFrame = StackAnalyzer.getTracedFrame(null, currentDepth);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("#FATAL | " );
		
		builder.append(message);

		builder.append(" (" );

		tracedFrame.printSourceLocation(builder);

		builder.append(")" );

		
		if(causedBy != null && causedBy.length > 0) {

			builder.append("\ncaused by\n");
			System.out.println(builder.toString());

			causedBy[0].printStackTrace();
			
		}else {

			System.out.println(builder.toString());
			
		}
		
		
		AppCloser.close();
	}

}

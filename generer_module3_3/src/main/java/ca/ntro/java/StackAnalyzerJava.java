package ca.ntro.java;

import ca.ntro.core.system.source.SourceFileLocation;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.core.system.stack.StackFrame;

public class StackAnalyzerJava extends StackAnalyzer {

	@Override
	protected StackFrame getTracedFrameImpl(String className, int finalStackOffset) {
		
		StackTraceElement frame = Thread.currentThread().getStackTrace()[finalStackOffset];
		
		return new StackFrame(className, frame.getMethodName(),new SourceFileLocation(frame.getFileName(), frame.getLineNumber()));

	}

	@Override
	protected int getInitialStackOffset() {
		return 2;
	}

}

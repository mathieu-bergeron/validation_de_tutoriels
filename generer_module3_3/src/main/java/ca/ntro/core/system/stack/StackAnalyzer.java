package ca.ntro.core.system.stack;

public abstract class StackAnalyzer {
	
	private static StackAnalyzer instance;
	
	public static void initialize(StackAnalyzer instance) {
		StackAnalyzer.instance = instance;
	}

	public static StackFrame getTracedFrame(String className, int stackOffset) {
		stackOffset++;
		
		StackFrame result = null;
		
		try {
			
			int finalStackOffset = instance.getInitialStackOffset() + stackOffset;
			result = instance.getTracedFrameImpl(className, finalStackOffset);
			
		}catch(NullPointerException e) {
			
			System.out.println("[FATAL]" + StackAnalyzer.class.getSimpleName() + " must be initialized");
			System.out.println("\ncaused by");
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected abstract StackFrame getTracedFrameImpl(String className, int finalStackOffset);
	protected abstract int getInitialStackOffset();

}

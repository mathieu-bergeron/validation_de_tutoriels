package ca.ntro.core.system.stack;

import ca.ntro.core.system.source.SourceFileLocation;

public class StackFrame {

	private String className;
	private String methodName;
	private SourceFileLocation location;

	public StackFrame(String className, String methodName, SourceFileLocation location) {
		this.className = className;
		this.methodName = methodName;
		this.location = location;
	}

	public void printFrame(StringBuilder builder) {
		builder.append(className);
		builder.append(".");
		builder.append(methodName);
		builder.append(" (");
		printSourceLocation(builder);
		builder.append(")");
	}
	
	public void printSourceLocation(StringBuilder builder) {
		builder.append(location);
	}


}

package ca.ntro.core.system.debug;

import ca.ntro.core.system.source.SourceFileLocation;

public class LabelFormater {
	
	public static void format(StringBuilder builder, String className, String methodName, String message, SourceFileLocation location) {
		builder.append("#");
		builder.append(className);
		builder.append(".");
		builder.append(methodName);
		builder.append(" | ");
		builder.append(message);
		builder.append(" (");
		builder.append(location.toString());
		builder.append(")");
	}

}

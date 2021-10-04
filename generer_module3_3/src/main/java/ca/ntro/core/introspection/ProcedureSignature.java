package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;

public abstract class ProcedureSignature extends ModelValue {
	private static final long serialVersionUID = -2238640107383781844L;
	
	/*
	private static List<String> possibleModifiers = new ArrayList<>();
	static {
		possibleModifiers.add("public");
		possibleModifiers.add("protected");
		possibleModifiers.add("private");
		possibleModifiers.add("transient");
		possibleModifiers.add("volatile");
		possibleModifiers.add("static");
		possibleModifiers.add("final");
		possibleModifiers.add("strict");
		possibleModifiers.add("abstract");
		possibleModifiers.add("interface");
	}*/

	private String name;
	private List<String> argumentTypes = new ArrayList<>();
	private List<String> modifiers = new ArrayList<>();

	public ProcedureSignature(String name, List<String> argumentTypes, List<String> modifiers) {
		T.call(this);
		
		this.name = name;
		this.argumentTypes = argumentTypes;
		this.modifiers = modifiers;
	}
	
	@Override
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		formatModifiers(builder);
		
		formatName(builder);

		formatArguments(builder);
		
		return builder.toString();
	}

	protected void formatArguments(StringBuilder builder) {
		T.call(this);

		builder.append("(");
		
		if(argumentTypes.size() > 0) {
			builder.append(argumentTypes.get(0).toString());
			
			for(int i = 1; i < argumentTypes.size(); i++) {
				builder.append(", ");
				builder.append(argumentTypes.get(i).toString());
			}
		}

		builder.append(")");
	}

	protected void formatName(StringBuilder builder) {
		T.call(this);

		builder.append(" ");
		builder.append(name);
	}

	protected void formatModifiers(StringBuilder builder) {
		T.call(this);

		for(String modifier : modifiers) {
			builder.append(modifier);
			builder.append(" ");
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArgumentTypes() {
		return argumentTypes;
	}

	public void setArgumentTypes(List<String> argumentTypes) {
		this.argumentTypes = argumentTypes;
	}

	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ProcedureSignature) {
			
			ProcedureSignature other = (ProcedureSignature) obj;
			
			return other.toString().equals(this.toString());
			
		}else {
			
			return false;
		}
	}
	
	public List<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}
	
	public boolean isPrivate() {
		return modifiers.contains("private");
	}

}

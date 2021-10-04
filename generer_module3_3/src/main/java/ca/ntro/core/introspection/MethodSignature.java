package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.debug.T;

public class MethodSignature extends ProcedureSignature {
	private static final long serialVersionUID = -375163358194142628L;
	
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

	private String returnType;

	public MethodSignature(String name, List<String> argumentTypes, String returnType, List<String> modifiers) {
		super(name, argumentTypes, modifiers);
		T.call(this);

		this.returnType = returnType;
	}

	@Override
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		//formatModifiers(builder);

		builder.append(returnType.toString());
		
		formatName(builder);

		formatArguments(builder);
		
		return builder.toString();
	}
	
	public static MethodSignature fromString(String signatureString) {
		T.call(MethodSignature.class);
		
		List<String> modifiers = new ArrayList<>();
		
		String[] splitName = signatureString.split("\\(");
		String modifiersReturnTypeName = splitName[0];
		
		String[] modifiersReturnTypeNameSplit = modifiersReturnTypeName.split(" ");
		
		for(int i = 0; i < modifiersReturnTypeNameSplit.length - 2; i++) {
			modifiers.add(modifiersReturnTypeNameSplit[i]);
		}
		
		String returnType = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 2].replace(" ", "");

		String name = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 1].replace(" ", "");

		String args = splitName[1];
		args = args.replace(")", "");
		
		String[] argTypes = args.split(", ");
		
		List<String> argumentTypes = new ArrayList<>();
		for(String argType : argTypes) {
			argumentTypes.add(argType.replace(" ", ""));
		}

		return new MethodSignature(name, argumentTypes, returnType, modifiers);
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}

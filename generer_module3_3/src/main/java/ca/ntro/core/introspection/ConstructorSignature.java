package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.debug.T;

public class ConstructorSignature extends ProcedureSignature {
	private static final long serialVersionUID = -5864624951077473662L;

	public ConstructorSignature(String name, List<String> argumentTypes, List<String> modifiers) {
		super(name, argumentTypes, modifiers);
		T.call(this);
	}

	public static ConstructorSignature fromString(String signatureString) {
		T.call(ConstructorSignature.class);
		
		List<String> modifiers = new ArrayList<>();
		
		String[] splitName = signatureString.split("\\(");
		String modifiersReturnTypeName = splitName[0];
		
		String[] modifiersReturnTypeNameSplit = modifiersReturnTypeName.split(" ");
		
		for(int i = 0; i < modifiersReturnTypeNameSplit.length - 1; i++) {
			modifiers.add(modifiersReturnTypeNameSplit[i]);
		}

		String name = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 1].replace(" ", "");

		String args = splitName[1];
		args = args.replace(")", "");
		
		String[] argTypes = args.split(", ");
		
		List<String> argumentTypes = new ArrayList<>();
		for(String argType : argTypes) {
			argumentTypes.add(argType.replace(" ", ""));
		}

		return new ConstructorSignature(name, argumentTypes, modifiers);
	}
}

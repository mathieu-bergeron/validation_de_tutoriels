package ca.ntro.core.introspection;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;

public class FieldSignature extends ModelValue {
	private static final long serialVersionUID = -2238640107383781844L;

	private String name;
	private String type;
	private List<String> modifiers = new ArrayList<>();


	public FieldSignature(String name, String type, List<String> modifiers) {
		T.call(this);
		
		this.name = name;
		this.type = type;
		this.modifiers = modifiers;
	}
	
	public String toString() {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();

		for(String modifier : modifiers) {
			
			builder.append(modifier);
			builder.append(" ");
		}
		
		builder.append(type);
		
		builder.append(" ");
		builder.append(name);
		
		return builder.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static FieldSignature fromString(String signatureString) {
		T.call(FieldSignature.class);

		List<String> modifiers = new ArrayList<>();
		String name = null;
		String type = null;
		
		String[] elements = signatureString.split(" ");
		
		for(int i = 0; i < elements.length -2; i++) {
			modifiers.add(elements[i]);
		}
		
		type = elements[elements.length - 2];
		name = elements[elements.length - 1];

		return new FieldSignature(name, type, modifiers);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FieldSignature) {
			
			FieldSignature other = (FieldSignature) obj;
			
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isPrivate() {
		return modifiers.contains("private");
	}

}

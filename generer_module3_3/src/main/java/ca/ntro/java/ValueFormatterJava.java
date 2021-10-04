package ca.ntro.java;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Formatter;
import java.util.List;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.debug.T;

public class ValueFormatterJava extends ValueFormatter {

	@Override
	public void formatImpl(StringBuilder builder, boolean isHtml, Object... values) {
		T.call(this);
		
		if(values.length > 0) {
			
			Object firstValue = values[0];
			
			formatValue(builder, isHtml, firstValue);
			
			for(int i = 1; i < values.length; i++) {
				
				Object value = values[i];
				
				builder.append(", ");
				
				formatValue(builder, isHtml, value);
			}
		}
	}
	
	private void formatValue(StringBuilder builder, boolean isHtml, Object value) {
		T.call(this);
		
		if(isHtml) {
			builder.append("<code>");
		}
		
		if(value == null) {
			
			builder.append("null");

		}else if(value instanceof String) {
			
			builder.append("\"");
			builder.append(value);
			builder.append("\"");

		}else if(value instanceof Double || value.getClass().equals(double.class)) {

			builder.append(String.format("%.2f", value));

		}else if(value instanceof Float || value.getClass().equals(float.class)) {

			builder.append(String.format("%.2f", value));

		}else if(value instanceof Boolean || value.getClass().equals(boolean.class)) {

			builder.append(value);

		}else if(value instanceof Integer || value.getClass().equals(int.class)) {

			builder.append(value);

		}else if(value instanceof Long || value.getClass().equals(long.class)) {

			builder.append(value);

		}else if(overridesToString(value)) {
			
			builder.append(value.toString());

		}else {
		
			builder.append(value.getClass().getSimpleName());
			builder.append("@");
			builder.append(intToHex(System.identityHashCode(value)));
		}

		if(isHtml) {
			builder.append("</code>");
		}
	}
	
	private boolean overridesToString(Object value) {
		T.call(this);
		
		boolean result = false;
		
		List<Method> userDefinedMethods = Introspector.userDefinedMethodsFromObject(value);
		
		for(Method userDefinedMethod : userDefinedMethods) {
			
			if(userDefinedMethod.getName().equals("toString")) {
				
				result = true;
				break;
			}
		}

		return result;
	}
	
	private String intToHex(int input) {
		T.call(this);
		
		String result;
		
		BigInteger bigInt = BigInteger.valueOf(input);
		byte[] bytes = bigInt.toByteArray();
		
	    Formatter formatter = new Formatter();

	    for (byte b : bytes) {
	        formatter.format("%02x", b);
	    }

	    result = formatter.toString();
	    formatter.close();

		return result;
	}
	

	
	
	
	
	
	
}

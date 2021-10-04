package ca.ntro.java.introspection;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.ConstructorSignature;
import ca.ntro.core.introspection.FieldSignature;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public class IntrospectorJava extends Introspector {

	@Override
	protected Object buildValueForSetterImpl(Method setter, Object rawValue) {
		T.call(this);
		
		Object result = null;
		
		Type setterType = setterType(setter);
		
		result = buildValue(setterType, rawValue);
		
		return result;
	}

	protected Object buildValue(Type type, Object rawValue) {
		T.call(this);
		
		Object result = null;
		
		if(JsonParser.isUserDefined(rawValue)) {
			
			result = JsonParser.buildUserDefined(rawValue);
			
		}else if(isAList(type)) {
			
			result = buildList(type, rawValue);
			
		}else if(isAMap(type)) {
			
			result = buildMap(type, rawValue);

		}else {
			
			result = buildSimpleValue(type, rawValue);
		}
		
		return result;
	}
	
	private Object buildSimpleValue(Type type, Object rawValue) {
		T.call(this);
		
		Object result = null;
		
		if(rawValue == null) {
			
			result = null;

		}else if(type.equals(Object.class)) {
			
			result = rawValue;
			
		}else if(type.equals(rawValue.getClass())) {
			
			result = rawValue;
			
		}else if(type.equals(String.class)) {
			
			result = String.valueOf(rawValue.toString());

		}else if(type.equals(Boolean.class) || type.equals(boolean.class)) {
			
			result = Boolean.valueOf(rawValue.toString());

		}else if(type.equals(Double.class) || type.equals(double.class)) {
			
			result = Double.valueOf(rawValue.toString());

		}else if(type.equals(Float.class) || type.equals(float.class)) {
			
			result = Float.valueOf(rawValue.toString());
			
		}else if(type.equals(Integer.class) || type.equals(int.class)) {
			
			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (int) Math.round(resultDouble);

		}else if(type.equals(Long.class) || type.equals(long.class)) {
			
			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (long) Math.round(resultDouble);

		}else if(type.equals(Character.class) || type.equals(char.class)) {
			
			Double resultDouble = Double.parseDouble(rawValue.toString());
			result = (char) Math.round(resultDouble);

		}else if(type instanceof Class){
			
			Class<?> _class = (Class<?>) type;
			
			try {
				
				result = _class.cast(rawValue);
				
			}catch(ClassCastException e) {
				
				Log.fatalError("Cannot cast rawValue into type " + type + " for rawValue " + rawValue + " of type " + rawValue.getClass());
			}
			
				
		}else {

			Log.fatalError("Unable to build simple value " + type + " for rawValue " + rawValue + " of type " + rawValue.getClass());
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> buildMap(Type targetType, Object value) {
		T.call(this);

		Map<String, Object> result = new HashMap<>();

		Type valueType = mapValueType(targetType);

		Map<String, Object> inputMap = (Map<String, Object>) value;
		
		for(String key : inputMap.keySet()) {
			
			Object inputValue = inputMap.get(key);
			
			Object outputValue = buildValue(valueType, inputValue);
			
			result.put(key, outputValue);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Object> buildList(Type targetType, Object value) {
		T.call(this);

		List<Object> result = new ArrayList<>();
		
		Type itemType = listItemType(targetType);

		List<Object> inputList = (List<Object>) value;
		
		for(Object inputItem : inputList) {

			Object outputValue = buildValue(itemType, inputItem);

			result.add(outputValue);
		}
		return result;
	}


	private Type listItemType(Type type) {
		T.call(this);
		return parameterType(type, 0);
	}

	private Type mapValueType(Type type) {
		T.call(this);
		return parameterType(type, 1);
	}

	private Type parameterType(Type type, int index) {
		T.call(this);
		
		Type result = null;
		
		try {
			
			result = ((ParameterizedType)type).getActualTypeArguments()[index];
			
		}catch(ClassCastException | IndexOutOfBoundsException | NullPointerException e) {}
		
		return result;
	}

	private boolean isAList(Type type) {
		T.call(this);
		
		boolean result = false;
		
		if(type instanceof ParameterizedType) {
			
			Type rawType = ((ParameterizedType) type).getRawType();

			result = rawType.equals(List.class);
			
		}else if(type instanceof Class<?>) {
			
			result = doesImplement((Class<?>) type, List.class);
			
		}

		return result;
	}

	private boolean isAMap(Type type) {
		T.call(this);

		boolean result = false;
		
		if(type instanceof ParameterizedType) {
			
			Type rawType = ((ParameterizedType) type).getRawType();
			
			result = rawType.equals(Map.class);
			
		}else if(type instanceof Class<?>) {
			
			result = doesImplement((Class<?>) type, Map.class);
			
		}

		return result;
	}
	
	private boolean doesImplement(Class<?> typeClass, Class<?> targetInterface) {
		T.call(this);
		
		boolean doesImplement = false;

		for(Class<?> _interface : typeClass.getInterfaces()) {
			if(_interface.equals(targetInterface)) {
				doesImplement = true;
			}
		}
		
		return doesImplement;
	}

	private Type setterType(Method setter) {
		T.call(this);
		
		Type result = null;
		
		Type[] parameterTypes = setter.getGenericParameterTypes();
		
		if(parameterTypes != null && parameterTypes.length > 0) {
			
			result = parameterTypes[0];
		}
		
		return result;
	}

	@Override
	public Object buildValueForTypeImpl(Class<?> superType, Object rawValue) {
		T.call(this);
		
		return buildValue(superType, rawValue);

	}

	private static String simpleTypeName(String typeName) {
		T.call(IntrospectorJava.class);

		String[] nameElements = typeName.split("\\.");
		typeName = nameElements[nameElements.length-1];

		return typeName;
	}

	@SuppressWarnings("rawtypes")
	public static ConstructorSignature constructorSignature(Constructor constructor) {
		T.call(IntrospectorJava.class);

		List<String> argumentTypes = new ArrayList<>();
		
		for(Type argumentType : constructor.getGenericParameterTypes()) {
			argumentTypes.add(simpleTypeName(argumentType.getTypeName()));
		}
		
		String name = simpleTypeName(constructor.getName());

		return new ConstructorSignature(name, argumentTypes, modifiers(constructor));
	}

	@Override
	protected MethodSignature methodSignatureImpl(Method method) {
		T.call(this);
		
		List<String> argumentTypes = new ArrayList<>();
		
		for(Type argumentType : method.getGenericParameterTypes()) {
			argumentTypes.add(simpleTypeName(argumentType.getTypeName()));
		}

		return new MethodSignature(method.getName(), argumentTypes, simpleTypeName(method.getGenericReturnType().getTypeName()), modifiers(method));
	}

	public static FieldSignature fieldSignature(Field field) {
		T.call(IntrospectorJava.class);
		
		return new FieldSignature(field.getName(), simpleTypeName(field.getGenericType().getTypeName()), modifiers(field));
	}

	@SuppressWarnings("rawtypes")
	private static List<String> modifiers(Constructor constructor){
		T.call(IntrospectorJava.class);
		
		return modifiers(constructor.getModifiers());
	}
	
	private static List<String> modifiers(Method method){
		T.call(IntrospectorJava.class);
		
		return modifiers(method.getModifiers());
	}

	private static List<String> modifiers(Field field){
		T.call(IntrospectorJava.class);
		
		return modifiers(field.getModifiers());
	}

	private static List<String> modifiers(int intModifiers){
		T.call(IntrospectorJava.class);
		
		List<String> modifiers = new ArrayList<>();
		
		if(Modifier.isPublic(intModifiers)) {
			modifiers.add("public");
		}else if(Modifier.isProtected(intModifiers)) {
			modifiers.add("protected");
		}else if(Modifier.isPrivate(intModifiers)) {
			modifiers.add("private");
		}

		return modifiers;
	}

	@Override
	protected List<Method> userDefinedMethodsFromClassImpl(Class<?> _class) {
		T.call(this);

		List<Method> result = new ArrayList<>();
		
		for(Method m : _class.getDeclaredMethods()) {
			result.add(m);
		}
		
		Class<?> superClass = _class.getSuperclass();
		
		while(superClass != null && !superClass.equals(Object.class)) {
			
			for(Method m : superClass.getDeclaredMethods()) {
				if(!Modifier.isAbstract(m.getModifiers()) && !Modifier.isPrivate(m.getModifiers())) {
					result.add(m);
				}
			}

			superClass = superClass.getSuperclass();
		}
		
		return result;
	}

	@Override
	protected List<FieldSignature> userDefinedFieldsFromClassImpl(Class<?> _class) {
		T.call(this);

		List<FieldSignature> result = new ArrayList<>();
		
		for(Field f : _class.getDeclaredFields()) {
			result.add(fieldSignature(f));
		}
		
		Class<?> superClass = _class.getSuperclass();
		
		while(superClass != null && !superClass.equals(Object.class)) {
			
			for(Field f : superClass.getDeclaredFields()) {
				result.add(fieldSignature(f));
			}

			superClass = superClass.getSuperclass();
		}
		
		return result;
	}
}

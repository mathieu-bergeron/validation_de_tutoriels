package ca.ntro.core.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class Introspector {
	
	private static Introspector instance;
	
	public static void initialize(Introspector instance) {
		T.call(Introspector.class);
		
		Introspector.instance = instance;
	}

	protected abstract MethodSignature methodSignatureImpl(Method method);

	public static MethodSignature methodSignature(Method method) {
		T.call(Introspector.class);
		
		MethodSignature result = null;

		try {
			
			result = instance.methodSignatureImpl(method);

		}catch(NullPointerException e) {
			
			Log.fatalError(Introspector.class + " must be initialized", e);

		}
		
		return result;
	}


	public static Method findMethodBySignature(Class<?> currentClass, MethodSignature methodSignature) {
		T.call(Introspector.class);
		
		Method result = null;
		
		for(Method candidate : userDefinedMethodsFromClass(currentClass)) {
			
			MethodSignature candidateSignature = methodSignature(candidate);
			
			if(candidateSignature.equals(methodSignature)) {
				
				result = candidate;
				break;
			}
		}

		return result;
	}

	protected abstract Object buildValueForSetterImpl(Method setter, Object rawValue);

	public static Object buildValueForSetter(Method setter, Object rawValue) {
		T.call(Introspector.class);

		Object result = null;
		
		try {
			
			result = instance.buildValueForSetterImpl(setter, rawValue);

		}catch(NullPointerException e) {
			
			Log.fatalError(Introspector.class + " must be initialized", e);

		}
		
		return result;
	}

	public abstract Object buildValueForTypeImpl(Class<?> type, Object rawValue);

	public static Object buildValueForType(Class<?> type, Object rawValue) {
		T.call(Introspector.class);
		
		Object result = null;
		
		try {
			
			result = instance.buildValueForTypeImpl(type, rawValue);

		}catch(NullPointerException e) {
			
			Log.fatalError(Introspector.class + " must be initialized", e);

		}
		
		return result;
	}
	
	
	public static Method findMethodByName(Class<?> _class, String methodName) {
		T.call(Introspector.class);
		
		Method result = null;
		
		for(Method method : userDefinedMethodsFromClass(_class)) {
			
			if(method.getName().equals(methodName)) {
				
				result = method;
				break;
			}
		}
		
		return result;
	}

	public static Class<?> getClassFromName(String className){
		T.call(Introspector.class);

		Class<? extends Object> _class = null;
		
		try {

			_class = Class.forName(className);

		} catch (ClassNotFoundException e) {
			
			Log.fatalError("Cannot find class " + className, e);

		}
		
		return _class;
		
	}

	private static String setterName(String fieldName) {
		T.call(Introspector.class);
		return "set" + capitalize(fieldName);
	}
	
	private static String capitalize(String value) {
		T.call(Introspector.class);
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}


	private static String unCapitalize(String value) {
		T.call(Introspector.class);
		return value.substring(0, 1).toLowerCase()+ value.substring(1);
	}

	public static String fieldNameForGetter(Method method) {
		T.call(Introspector.class);

		String methodName = method.getName();

		// remove get
		String fieldName = methodName.substring(3);
		
		fieldName = unCapitalize(fieldName);
		
		return fieldName;
	}
	

 
	public static Method findSetter(Class<?> _class, String fieldName) {
		T.call(Introspector.class);
		
		Method result = null;
		
		String setterName = setterName(fieldName);
		
		result = findMethodByName(_class, setterName);
		
		return result;
	}

	public static boolean isAGetter(Method method) {
		T.call(Introspector.class);

		boolean isNotGetClass = !method.getName().equals("getClass");
		
		return isASetterOrSetter(method, "get") && isNotGetClass;
	}

	public static boolean isASetter(Method method) {
		T.call(Introspector.class);
		
		return isASetterOrSetter(method, "set");
	}

	private static boolean isASetterOrSetter(Method method, String prefix) {
		T.call(Introspector.class);

		String methodName = method.getName();
		
		boolean methodStartsWithPrefix = methodName.startsWith(prefix);
		
		boolean upperCaseAfterPrefix = false;
		
		if(methodName.length() > prefix.length()) {
			
			String letterAfterGet = methodName.substring(prefix.length(), prefix.length()+1);
			
			upperCaseAfterPrefix = letterAfterGet != null && letterAfterGet != "" && letterAfterGet.toUpperCase().equals(letterAfterGet);
		}
		
		
		return methodStartsWithPrefix && upperCaseAfterPrefix;
	}



	public static List<Method> userDefinedMethodsFromObject(Object object) {
		T.call(Introspector.class);
		
		// FIXME: does not work in JSweet
		//MustNot.beTrue(object instanceof Class);
		
		return userDefinedMethodsFromClass(object.getClass());
	}

	protected abstract List<Method> userDefinedMethodsFromClassImpl(Class<?> _class);

	public static List<Method> userDefinedMethodsFromClass(Class<?> _class) {
		T.call(Introspector.class);

		List<Method> result = null;
		
		try {

			result = instance.userDefinedMethodsFromClassImpl(_class);

		}catch(NullPointerException e) {
			
			Log.fatalError(Introspector.class + " must be initialized", e);

		}
		
		return result;
	}

	protected abstract List<FieldSignature> userDefinedFieldsFromClassImpl(Class<?> _class);
	
	public static List<FieldSignature> userDefinedFieldsFromClass(Class<?> _class) {
		T.call(Introspector.class);

		List<FieldSignature> result = null;
		
		try {

			result = instance.userDefinedFieldsFromClassImpl(_class);

		}catch(NullPointerException e) {
			
			Log.fatalError(Introspector.class + " must be initialized", e);

		}
		
		return result;
	}

	public static List<Method> userDefinedSetters(Object object) {

		MustNot.beTrue(object instanceof Class);
		
		List<Method> allSetters = new ArrayList<>();
		
		for(Method method : userDefinedMethodsFromObject(object)) {
			
			System.out.println("method: " + method.getName());

			if(isASetter(method)) {

				System.out.println("method/setter: " + method.getName());

				allSetters.add(method);
			}
		}
		
		return allSetters;
	}

	public static List<Method> userDefinedGetters(Object object) {
		T.call(Introspector.class);

		// FIXME: does not work in JSweet
		// MustNot.beTrue(object instanceof Class);
		
		List<Method> allGetters = new ArrayList<>();
		
		for(Method method : userDefinedMethodsFromObject(object)) {

			if(isAGetter(method)) {

				allGetters.add(method);
			}
		}
		
		return allGetters;
	}
}

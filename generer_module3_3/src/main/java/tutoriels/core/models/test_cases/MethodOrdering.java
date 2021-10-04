package tutoriels.core.models.test_cases;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodOrdering {
	
	public static List<String> desiredMethodOrder = new ArrayList<>();

	public static List<Method> reorderMethods(List<Method> methods) {
		List<Method> orderedMethods = new ArrayList<>();
		
		for(String methodName : desiredMethodOrder) {
			
			Method toAdd = null;
			
			for(Method candidate : methods) {
				if(candidate.getName().equals(methodName)) {
					
					toAdd = candidate;
					break;
				}
			}
			
			if(toAdd != null) {
				methods.remove(toAdd);
				orderedMethods.add(toAdd);
			}
		}

		orderedMethods.addAll(methods);
		
		return orderedMethods;
	}

}

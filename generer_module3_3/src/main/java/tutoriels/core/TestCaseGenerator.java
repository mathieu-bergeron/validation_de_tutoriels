package tutoriels.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.Factory;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.app.Exercise;
import tutoriels.core.models.test_cases.ProviderMethodsTestCases;

public abstract class TestCaseGenerator {

	public static <G extends TestCaseGenerator, E extends Exercise> void generateTestCases(Class<G> generatorClass, Class<E> exerciseClass) {
		T.call(TestCaseGenerator.class);
		
		E solution = Factory.newInstance(exerciseClass);
		G generator = Factory.newInstance(generatorClass);
		
		MustNot.beNull(solution);
		MustNot.beNull(generator);

		ProviderMethodsTestCases testCases = LocalStore.get(ProviderMethodsTestCases.class, CurrentExercise.getId());
		
		generator.generateTestCases(solution, testCases);
		
		testCases.save();
	}

	public void generateTestCases(Exercise solution, ProviderMethodsTestCases testCases) {
		T.call(TestCaseGenerator.class);
		
		for(Method method : providerMethods(solution)) {
			testCases.generateTestCases(method, solution, this);
		}
	}

	// FIXME: eventually, we would need arguments to providerMethod
	public abstract List<List<Object>> generateMultipleInputArguments(String providerMethodName, Object providedObject, Method methodToTest);

	public static List<Method> providerMethods(Exercise exercise){
		T.call(TestCaseGenerator.class);
		
		List<Method> methods = new ArrayList<>();
		
		for(Method method : Introspector.userDefinedMethodsFromObject(exercise)) {
			
			if(exercise.isAProviderMethod(method)) {
				
				methods.add(method);
				
			}
		}
		
		return methods;
	}

}

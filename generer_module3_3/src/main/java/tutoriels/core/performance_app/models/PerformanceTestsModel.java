package tutoriels.core.performance_app.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.swing.SwingWorker;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.Model;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.app.Exercise;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.core.performance_app.models.values.ObservableGraphs;

public class PerformanceTestsModel extends Model {
	private static final long serialVersionUID = -8412558529252583538L;
	
	private ObservableGraphs performanceGraphs = new ObservableGraphs(new ArrayList<>());
	
	public static Semaphore testNextMethodSemaphore = new Semaphore(1, true);

	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public void testPerformance(String providerMethodPrefix,
								List<Method> providerMethods, 
								Exercise exercise, 
								PerformanceTestsDriver driver) {
		T.call(this);

		Map<String, Object> providedObjects = providedObjects(providerMethods, exercise);

		testPerformanceForProvidedObjects(providerMethodPrefix, providedObjects, exercise, driver);
	}

	private Map<String, Object> providedObjects(List<Method> providerMethods, Exercise exercise) {
		T.call(this);

		Map<String, Object> providedObjects = new HashMap<>();
		
		for(Method providerMethod : providerMethods) {
			
			try {
				
				Object providedObject = providerMethod.invoke(exercise);
				
				if(providedObject == null) {
					System.err.println(String.format("[FATAL] la méthode %s ne doit pas retourner null", providerMethod.getName()));
					AppCloser.close();
				}
				
				String suffix = exercise.providerMethodSuffix(providerMethod);

				providedObjects.put(suffix, providedObject);
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Log.fatalError("Cannot invoke provider method " + providerMethod, e);
			}
		}
		
		return providedObjects;
	}

	public void testPerformanceForProvidedObjects(String providerMethodPrefix,
								Map<String, Object> providedObjects, 
								Exercise exercise, 
								PerformanceTestsDriver driver) {
		T.call(this);
		
		
		Set<String> methodsToTest = methodsToTest(providedObjects);

		testPerformanceOfMethod(providerMethodPrefix, providedObjects, methodsToTest, driver);
	}
	
	private Set<String> methodsToTest(Map<String,Object> providedObjects){
		Set<String> methodsToTest = new HashSet<>();
		
		for(Object providedObject : providedObjects.values()) {
			methodsToTest.addAll(methodsToTest(providedObject));
		}
		
		return methodsToTest;
	}


	private List<String> methodsToTest(Object providedObject) {
		T.call(this);
		
		List<String> methodsToTest = new ArrayList<>();
		
		for(Method method : Introspector.userDefinedMethodsFromObject(providedObject)) {
			
			if(Modifier.isPublic(method.getModifiers())) {
				methodsToTest.add(method.getName());
			}
		}
		
		return methodsToTest;
	}

	public void testPerformanceOfMethod(String providerMethodPrefix,
								Map<String, Object> providedObjects, 
								Set<String> methodsToTest,
								PerformanceTestsDriver driver) {
		T.call(this);
		
		List<String> desiredMethodOrder = driver.desiredMethodOrder();
		
		List<String> methodsInOrder = new ArrayList<>();
		
		for(String desiredMethod : desiredMethodOrder) {
			if(methodsToTest.contains(desiredMethod)) {
				methodsInOrder.add(desiredMethod);
				methodsToTest.remove(desiredMethod);
			}
		}
		
		methodsInOrder.addAll(methodsToTest);
		
		for(String methodName : methodsInOrder) {

			TestParameters testParameters = driver.getTestParametersFor(providerMethodPrefix, methodName);
			
			if(testParameters != null) {
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
					@Override
					protected Void doInBackground() throws Exception {
						T.call(this);
						try {

							testNextMethodSemaphore.acquire();

							System.out.println("METHODE: " + methodName);

							PerformanceGraphViewModel performanceGraph = new PerformanceGraphViewModel();
							performanceGraph.setTitle(methodName);
							
							getPerformanceGraphs().addItem(performanceGraph);
							performanceGraph.testPerformance(providerMethodPrefix, providedObjects, methodName, driver, testParameters);

						} catch (InterruptedException e) {
							Log.fatalError("Error when testing method", e);
						}
						return null;
					}
				};

				worker.execute();
			}
		}
	}

	public ObservableGraphs getPerformanceGraphs() {
		return performanceGraphs;
	}

	public void setPerformanceGraphs(ObservableGraphs performanceGraphs) {
		this.performanceGraphs = performanceGraphs;
	}
}

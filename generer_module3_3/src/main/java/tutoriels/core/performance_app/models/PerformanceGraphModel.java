package tutoriels.core.performance_app.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.Model;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.core.performance_app.models.values.ObservableDataCategories;
import tutoriels.core.performance_app.models.values.ObservableTestParameters;

public class PerformanceGraphModel extends Model {
	private static final long serialVersionUID = 3720228033506866616L;
	
	private String title;

	private ObservableDataCategories observableDataCategories = new ObservableDataCategories(new ArrayList<>());
	private Map<String, DataCategoryModel> dataCategories = new HashMap<>();

	private ObservableTestParameters observableTestParameters = new ObservableTestParameters();

	@Override
	public void initializeStoredValues() {
		T.call(this);
		// XXX: none
	}

	public void testPerformance(String providerMethodPrefix,
								Map<String, Object> providedObjects, 
								String methodToTest,
								PerformanceTestsDriver driver, 
								TestParameters testParameters) {
		T.call(this);

			
		createCategories(providedObjects, methodToTest);
		
		observableTestParameters.set(testParameters);

		testPerformance(providerMethodPrefix, testParameters, providedObjects, methodToTest, driver);
	}


	public void testPerformance(String providerMethodPrefix,
			                    TestParameters testParameters,
								Map<String, Object> providedObjects, 
								String methodToTest,
								PerformanceTestsDriver driver) {
		T.call(this);
		
		int minSize = testParameters.getMinInputSize();
		int maxSize = testParameters.getMaxInputSize();
		int step = (maxSize - minSize) / testParameters.getDesiredSamples();
		
		int currentSize = minSize;

	    testPerformanceWorker(providerMethodPrefix,
			                  testParameters,
			                  providedObjects, 
			                  methodToTest, 
			                  driver,
			                  minSize,
			                  maxSize,
			                  step,
			                  currentSize);
	}

	public void testPerformanceWorker(String providerMethodPrefix,
			                    TestParameters testParameters,
								Map<String, Object> providedObjects, 
								String methodToTest,
								PerformanceTestsDriver driver,
								int minSize,
								int maxSize,
								int step,
								int currentSize) {
		T.call(this);

		SwingWorker<List<Object>, Void> worker = new SwingWorker<List<Object>, Void>() {
			@Override
			protected List<Object> doInBackground() throws Exception {
				T.call(this);
				List<Object> arguments = driver.generateArgumentsFor(providerMethodPrefix, methodToTest, currentSize);
				return arguments;
			}

			@Override
			protected void done() {
				T.call(this);
				try {

					List<Object> arguments = get();
					
					int currentProvidedObject = 0;
					
					testPerformanceForArguments(providerMethodPrefix, 
												testParameters,
												providedObjects, 
							                    arguments, 
							                    methodToTest, 
							                    driver,
							                    minSize,
							                    maxSize,
							                    step,
							                    currentSize,
							                    currentProvidedObject);

				} catch (InterruptedException | ExecutionException e) {
					Log.fatalError("Error invoking worker thread", e);
				}
			}
		};

		worker.execute();
	}


	public void testPerformanceForArguments(String providerMethodPrefix,
			                    			TestParameters testParameters,
											Map<String, Object> providedObjects, 
										    List<Object> arguments,
											String methodToTest,
											PerformanceTestsDriver driver,
											int minSize,
											int maxSize,
											int step,
											int currentSize,
											int currentObject) {
		T.call(this);
		
		String categoryName = (String) providedObjects.keySet().toArray()[currentObject];
		
		if(dataCategories.containsKey(categoryName)) {
			testPerformanceForArgumentsWhenMethodExists(providerMethodPrefix, 
					                                    testParameters, 
					                                    providedObjects, 
					                                    arguments, 
					                                    methodToTest, 
					                                    driver, 
					                                    minSize, 
					                                    maxSize, 
					                                    step, 
					                                    currentSize, 
					                                    currentObject, 
					                                    categoryName);
		}else {
			testForNextObjectOrNextInputSize(providerMethodPrefix, 
										     testParameters, 
										     providedObjects, 
										     arguments, 
										     methodToTest, 
										     driver, 
										     minSize, 
										     maxSize, 
										     step, 
										     currentSize, 
										     currentObject);
		}
	}

	private void testPerformanceForArgumentsWhenMethodExists(String providerMethodPrefix, 
		                                                  	 TestParameters testParameters, 
		                                                  	 Map<String, Object> providedObjects, 
		                                                  	 List<Object> arguments, 
		                                                  	 String methodToTest, 
		                                                  	 PerformanceTestsDriver driver, 
		                                                  	 int minSize, 
		                                                  	 int maxSize, 
		                                                  	 int step, 
		                                                  	 int currentSize, 
		                                                  	 int currentObject, 
		                                                  	 String categoryName) {
		
		T.call(this);

		Object providedObject = providedObjects.get(categoryName);
		
		List<Object> argumentsForObject = driver.adaptArgumentsFor(providedObject, methodToTest, arguments);

		DataCategoryModel category = dataCategories.get(categoryName);

		DataPointModel dataPoint  = category.createDataPoint(currentSize);
		
		SwingWorker<Double, Void> worker = new SwingWorker<Double, Void>(){

			@Override
			protected Double doInBackground() throws Exception {
				T.call(this);
				return category.testPerformance(providedObject, argumentsForObject, methodToTest, currentSize);
			}

			@Override
			protected void done(){
				
				try {

					double executionTimeSeconds = get();
					dataPoint.setExecutionTime(executionTimeSeconds);
					
					//System.gc();

					testForNextObjectOrNextInputSize(providerMethodPrefix, 
									  testParameters, 
									  providedObjects, 
									  arguments, 
									  methodToTest, 
									  driver, 
									  minSize, 
									  maxSize, 
									  step, 
									  currentSize, 
									  currentObject);

				} catch (InterruptedException | ExecutionException e) {
					Log.fatalError("Error invoking worker thread", e);
				}
			}

		};
		
		worker.execute();
	}

	private void testForNextObjectOrNextInputSize(String providerMethodPrefix, 
			                                      TestParameters testParameters, 
			                                      Map<String, Object> providedObjects, 
			                                      List<Object> arguments, 
			                                      String methodToTest, 
			                                      PerformanceTestsDriver driver, 
			                                      int minSize, 
			                                      int maxSize, 
			                                      int step, 
			                                      int currentSize, 
			                                      int currentObject) {

		T.call(this);

		int nextObject = currentObject + 1;
		
		if(nextObject < providedObjects.size()) {
			testPerformanceForArguments(providerMethodPrefix, 
										testParameters,
										providedObjects, 
										arguments, 
										methodToTest, 
										driver,
										minSize,
										maxSize,
										step,
										currentSize,
										nextObject);
		}else {
			
			int nextSize = currentSize + step;
			
			if(nextSize <= maxSize) {
				testPerformanceWorker(providerMethodPrefix,
									  testParameters,
									  providedObjects, 
									  methodToTest, 
									  driver,
									  minSize,
									  maxSize,
									  step,
									  nextSize);

			}else {

				PerformanceTestsModel.testNextMethodSemaphore.release();
			}
		}
	}


	private void createCategories(Map<String, Object> providedObjects, String methodToTest) {
		T.call(this);

		for(String categoryName : providedObjects.keySet()) {
			
			Object providedObject = providedObjects.get(categoryName);
			
			if(Introspector.findMethodByName(providedObject.getClass(), methodToTest) != null) {
				DataCategoryViewModel category = new DataCategoryViewModel();
				category.setTitle(categoryName);

				dataCategories.put(categoryName, category);
				observableDataCategories.addItem(category);
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ObservableDataCategories getObservableDataCategories() {
		return observableDataCategories;
	}

	public void setObservableDataCategories(ObservableDataCategories observableDataCategories) {
		this.observableDataCategories = observableDataCategories;
	}

	public ObservableTestParameters getObservableTestParameters() {
		return observableTestParameters;
	}

	public void setObservableTestParameters(ObservableTestParameters observableTestParameters) {
		this.observableTestParameters = observableTestParameters;
	}
}

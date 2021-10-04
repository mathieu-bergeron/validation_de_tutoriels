package tutoriels.core.performance_app.models;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.models.properties.observable.simple.ObservableString;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.performance_app.models.values.ObservableDataPoints;

public class DataCategoryModel extends ModelValue {
	private static final long serialVersionUID = 1960156387230057657L;

	private ObservableString observableTitle = new ObservableString();
	private ObservableDataPoints dataPoints = new ObservableDataPoints(new ArrayList<>());

	public ObservableDataPoints getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(ObservableDataPoints dataPoints) {
		this.dataPoints = dataPoints;
	}
	
	public void setTitle(String title) {
		T.call(this);
		
		observableTitle.set(title);
	}

	public ObservableString getObservableTitle() {
		return observableTitle;
	}

	public void setObservableTitle(ObservableString observableTitle) {
		this.observableTitle = observableTitle;
	}

	public DataPointViewModel createDataPoint(int inputSize) {
		T.call(this);

		DataPointViewModel dataPoint = new DataPointViewModel(inputSize, 0);
		
		getDataPoints().addItem(dataPoint);
		
		return dataPoint;
	}

	public double testPerformance(Object providedObject, List<Object> arguments, String methodName, int inputSize) {
		T.call(this);
		
		return invokeMethod(providedObject, arguments, methodName);
	}

	private double invokeMethod(Object providedObject, List<Object> arguments, String methodName) {
		T.call(this);

		Object[] argumentArray = arguments.toArray();
		Method method = Introspector.findMethodByName(providedObject.getClass(), methodName);
		
		//long startTimeMs = System.currentTimeMillis();
		
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		long startTimeNs = threadBean.getCurrentThreadCpuTime();

		try {

			method.invoke(providedObject, argumentArray);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			Log.fatalError("Could not invoke " + method.getName() + " during performance test", e);

		}

		//long currentTimeMs = System.currentTimeMillis();
		long currentTimeNs = threadBean.getCurrentThreadCpuTime();

		//double executionTimeSeconds = ((double) (currentTimeMs - startTimeMs)) / 1E3 ;
		double executionTimeSeconds = ((double) (currentTimeNs - startTimeNs)) / 1E9 ;

		return executionTimeSeconds;
	}
}

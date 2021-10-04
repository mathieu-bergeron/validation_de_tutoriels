package tutoriels.core.performance_app.models;

import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableDouble;
import ca.ntro.core.system.debug.T;

public class DataPointModel extends Model {
	private static final long serialVersionUID = 7834919545798734024L;
	
	private int inputSize;
	private ObservableDouble observableExecutionTimeSeconds = new ObservableDouble(0.0);
	private int id;
	
	@Override
	public void initializeStoredValues() {
		// TODO Auto-generated method stub
	}
	
	public DataPointModel(int inputSize, double executionTimeSeconds) {
		super();
		T.call(this);
		this.inputSize = inputSize;
		this.observableExecutionTimeSeconds = new ObservableDouble(executionTimeSeconds);
	}

	public int getInputSize() {
		return inputSize;
	}
	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}
	
	public void setExecutionTime(double executionTimeSeconds) {
		T.call(this);
		this.observableExecutionTimeSeconds.set(executionTimeSeconds);
	}

	public ObservableDouble getObservableExecutionTimeSeconds() {
		return observableExecutionTimeSeconds;
	}

	public void setObservableExecutionTimeSeconds(ObservableDouble observableExecutionTimeSeconds) {
		this.observableExecutionTimeSeconds = observableExecutionTimeSeconds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

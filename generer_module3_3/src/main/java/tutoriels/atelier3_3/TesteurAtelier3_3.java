package tutoriels.atelier3_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;

public class TesteurAtelier3_3 extends PerformanceTestsDriver {

	private static Random random = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		
		TestParameters testsParameters = null;

		if(methodName.equals("trier")) {
			
			testsParameters = new TestParameters((int) 1E3, (int) 1E5, 30, 35.0, true);
		}

		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		List<Object> arguments = new ArrayList<>();
		
		if(methodName.equals("trier")) {

			Tableau<Integer> tableau = randomIntegerArray(desiredInputSize);
			arguments.add(tableau);

		}

		return arguments;
	}

	private Tableau<Integer> randomIntegerArray(int size){
		Integer[] result = new Integer[size];
		
		for(int i = 0; i < size; i++) {
			result[i] = random.nextInt(size);
		}
		
		return new MonTableau<>(result);
	}

	@Override
	public List<Object> adaptArgumentsFor(Object providedObject, String methodName, List<Object> arguments) {
		return arguments;
	}

	@Override
	public List<String> desiredMethodOrder() {
		return new ArrayList<>();
	}

}

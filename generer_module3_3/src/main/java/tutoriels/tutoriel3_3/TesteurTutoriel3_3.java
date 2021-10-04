package tutoriels.tutoriel3_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;

public class TesteurTutoriel3_3 extends PerformanceTestsDriver {

	private static Random random = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		
		TestParameters testsParameters = null;

		if(methodName.equals("trouverIndicePourValeur")) {
			
			testsParameters = new TestParameters((int) 1E7, (int) 1E8, 30, 2.0, true);
		}
		
		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		List<Object> arguments = new ArrayList<>();
		
		if(methodName.equals("trouverIndicePourValeur")) {

			int toFind = random.nextInt(100);
			Tableau<Integer> tableau = randomIntegerArray(desiredInputSize, toFind);
			arguments.add(tableau);
			arguments.add(toFind);

		}

		return arguments;
	}

	public static Tableau<Integer> randomIntegerArray(int size, int toFind){
		
		
		Integer[] result = new Integer[size];
		
		for(int i = 0; i < size; i++) {
			int toInsert;
			do {
				toInsert = random.nextInt(100);
			}while(toInsert == toFind);

			result[i] = toInsert;
		}
		
		result[size-1] = toFind;
		
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

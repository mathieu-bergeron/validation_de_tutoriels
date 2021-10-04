package tutoriels.generer_theorie3_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.TestParameters;


public class TesteurTheorie3_3 extends PerformanceTestsDriver {
	
	private static Random random = new Random();

	@Override
	public TestParameters getTestParametersFor(String providerMethodPrefix, String methodName) {
		
		TestParameters testsParameters = null;

		if(methodName.equals("lineaire")) {
			
			// 01
			//testsParameters = new TestParameters((int) 10, (int) 100, 30, 30.0, false);

			// 02
			testsParameters = new TestParameters((int) 10, (int) 1000, 30, 30.0, false);
		}

		else if(methodName.equals("quadratique")) {
			
			testsParameters = new TestParameters((int) 10, (int) 100, 30, 30.0, false);
		}

		else if(methodName.equals("exponentiel")) {
			
			// 01
			//testsParameters = new TestParameters((int) 1, (int) 10, 5, 30.0, false);
			
			// 02
			testsParameters = new TestParameters((int) 1, (int) 24, 23, 30.0, false);
		}

		else if(methodName.equals("trier")) {
			
			testsParameters = new TestParameters((int) 1E3, (int) (1E4*1.5), 10, 30.0, false);
		}
		
		return testsParameters;
	}

	@Override
	public List<Object> generateArgumentsFor(String providerMethodPrefix, String methodName, int desiredInputSize) {
		
		List<Object> arguments = new ArrayList<>();
		
		if(methodName.equals("trier")) {
			
			char[] entree = new char[desiredInputSize];
			
			for(int i = 0; i < desiredInputSize; i++) {
				entree[i] = (char) random.nextInt(100);
			}
			
			arguments.add(entree);
			
		}else {
			
			arguments.add(desiredInputSize);
			
		}
		

		return arguments;
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

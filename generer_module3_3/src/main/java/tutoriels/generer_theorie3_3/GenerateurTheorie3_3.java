package tutoriels.generer_theorie3_3;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.ntro.core.system.debug.T;
import tutoriels.core.TestCaseGenerator;

public class GenerateurTheorie3_3 extends TestCaseGenerator {

	static {
		// XXX: this provokes initialization
		new SolutionTheorie3_3();
	}
	
	public static void main(String[] args) {
		T.call(GenerateurTheorie3_3.class);

		TestCaseGenerator.generateTestCases(GenerateurTheorie3_3.class, SolutionTheorie3_3.class);
	}

	private Random random = new Random();

	@Override
	public List<List<Object>> generateMultipleInputArguments(String providerMethodName, Object providedObject,
			Method methodToTest) {
		
		List<List<Object>> listOfInputArguments = new ArrayList<>();
		
		if(methodToTest.getName().equals("trier")) {
			
			//int minSize = (int) 1E4;
			//int maxSize = (int) 1E6;
			int minSize = (int) 10;
			int maxSize = (int) 100;
			int numberOfSamples = 10;
			int step = (maxSize - minSize) / numberOfSamples;
			
			for(int i = minSize; i < maxSize; i += step) {
				List<Object> arguments = new ArrayList<>();
				
				char[] entree = new char[i];
				
				for(int j = 0; j < i; j++) {
					entree[j] = (char) random.nextInt(100);
				}
				
				arguments.add(entree);

				listOfInputArguments.add(arguments);
			}

		}else {
			List<Object> arguments = new ArrayList<>();
			listOfInputArguments.add(arguments);
		}

		return listOfInputArguments;
	}
}

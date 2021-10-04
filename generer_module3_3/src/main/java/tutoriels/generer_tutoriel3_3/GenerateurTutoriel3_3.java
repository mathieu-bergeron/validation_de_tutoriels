package tutoriels.generer_tutoriel3_3;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.ntro.core.system.debug.T;
import tutoriels.core.TestCaseGenerator;
import tutoriels.generer_tutoriel3_3.solution.SolutionTutoriel3_3;
import tutoriels.tutoriel3_3.MonTableau;
import tutoriels.tutoriel3_3.Tableau;
import tutoriels.tutoriel3_3.TesteurTutoriel3_3;

public class GenerateurTutoriel3_3 extends TestCaseGenerator {

	static {
		// XXX: this provokes initialization
		new SolutionTutoriel3_3();
	}
	
	public static void main(String[] args) {
		T.call(GenerateurTutoriel3_3.class);

		TestCaseGenerator.generateTestCases(GenerateurTutoriel3_3.class, SolutionTutoriel3_3.class);
	}

	private Random random = new Random();

	@Override
	public List<List<Object>> generateMultipleInputArguments(String providerMethodName, Object providedObject,
			Method methodToTest) {
		
		List<List<Object>> listOfInputArguments = new ArrayList<>();
		
		if(methodToTest.getName().equals("trouverIndicePourValeur")) {
			
			//int maxSize = (int) 1E6;
			//int minSize = (int) 1E4;
			int maxSize = (int) 20;
			int minSize = (int) 4;
			int numberOfSamples = 10;
			int step = (maxSize - minSize) / numberOfSamples;
			
			for(int i = minSize; i < maxSize; i += step) {
				List<Object> arguments = new ArrayList<>();
				
				System.out.println("Generating for size: " + i);
				
				int toFind = random.nextInt(100);
				Tableau<Integer> tableau = TesteurTutoriel3_3.randomIntegerArray(i, toFind);
				arguments.add(tableau);
				arguments.add(toFind);

				listOfInputArguments.add(arguments);
			;}
		}

		return listOfInputArguments;
	}
}

package tutoriels.generer_atelier3_3;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.ntro.core.system.debug.T;
import tutoriels.atelier3_3.MonTableau;
import tutoriels.atelier3_3.Tableau;
import tutoriels.core.TestCaseGenerator;
import tutoriels.generer_atelier3_3.solution.SolutionAtelier3_3;

public class GenerateurAtelier3_3 extends TestCaseGenerator {
	
	static {
		// XXX: this provokes initialization
		new SolutionAtelier3_3();
	}
	
	public static void main(String[] args) {
		T.call(GenerateurAtelier3_3.class);

		TestCaseGenerator.generateTestCases(GenerateurAtelier3_3.class, SolutionAtelier3_3.class);
	}
	
	private Random random = new Random();

	@Override
	public List<List<Object>> generateMultipleInputArguments(String providerMethodName, Object providedObject,
			Method methodToTest) {
		
		List<List<Object>> listOfInputArguments = new ArrayList<>();
		
		if(methodToTest.getName().equals("trier")) {
			
			int maxSize = (int) 20;
			int minSize = (int) 2;
			int numberOfSamples = 10;
			int step = (maxSize - minSize) / numberOfSamples;
			
			for(int i = minSize; i < maxSize; i += step) {
				List<Object> arguments = new ArrayList<>();
				
				System.out.println("Generating for size: " + i);
				
				//arguments.add(randomVehiculeArray(i));
				arguments.add(randomIntegerArray(i));

				listOfInputArguments.add(arguments);
			}
		}

		return listOfInputArguments;
	}
	
	private Tableau<Integer> randomIntegerArray(int size){
		
		Integer[] result = new Integer[size];
		
		for(int i = 0; i < size; i++) {
			result[i] = random.nextInt(100);
		}
		
		return new MonTableau<>(result);
	}
	
	
	
	
	
}

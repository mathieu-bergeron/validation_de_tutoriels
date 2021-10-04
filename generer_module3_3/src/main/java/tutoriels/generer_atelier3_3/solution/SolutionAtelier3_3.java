package tutoriels.generer_atelier3_3.solution;

import tutoriels.atelier3_3.Atelier3_3;
import tutoriels.atelier3_3.Trieur;
import tutoriels.core.performance_app.PerformanceTestsDriver;

public class SolutionAtelier3_3 extends Atelier3_3 {
	
	public static void main(String[] args) {
		
		(new SolutionAtelier3_3()).valider();
	}

	@Override
	public <C extends Comparable<C>> Trieur<C> fournirTrieurNaif() {
		return new TrieurNaif<C>();
		//return new TrieurMoinsNaif<C>();
	}

}

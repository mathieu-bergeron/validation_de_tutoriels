package tutoriels.generer_tutoriel3_3.solution;

import tutoriels.tutoriel3_3.Chercheur;
import tutoriels.tutoriel3_3.Tutoriel3_3;

public class SolutionTutoriel3_3 extends Tutoriel3_3 {
	
	public static void main(String[] args) {
		
		new SolutionTutoriel3_3().valider();
	}

	@Override
	public <C extends Comparable<C>> Chercheur<C> fournirChercheurA() {
		return new ChercheurA<C>();
	}

	@Override
	public <C extends Comparable<C>> Chercheur<C> fournirChercheurB() {
		return new ChercheurB<C>();
	}



}

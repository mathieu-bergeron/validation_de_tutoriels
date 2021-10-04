package tutoriels.generer_theorie3_3;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.tutoriel3_3.Chercheur;

public abstract class Theorie3_3 extends Atelier {

	static {

		new InitializerExercise().initialize(Theorie3_3.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}

	@Override
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return new TesteurTheorie3_3();
	}

	public abstract Object fournirLineaire();
	public abstract Object fournirQuadratique();
	public abstract Object fournirExponentiel();
	public abstract Object fournirTrieurRaccourci();
	public abstract Object fournirTrieurLong();
	
	public static void perdreDuTemps(double facteur) {
		for(int j = 0; j < ((int) (100* facteur)); j++) System.out.println("");
	}

}

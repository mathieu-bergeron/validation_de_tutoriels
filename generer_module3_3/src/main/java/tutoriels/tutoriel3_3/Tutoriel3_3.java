package tutoriels.tutoriel3_3;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.performance_app.PerformanceTestsDriver;

public abstract class Tutoriel3_3 extends Atelier {

	static {

		new InitializerExercise().initialize(Tutoriel3_3.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}

	@Override
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return new TesteurTutoriel3_3();
	}

	public abstract <C extends Comparable<C>> Chercheur<C> fournirChercheurA();
	public abstract <C extends Comparable<C>> Chercheur<C> fournirChercheurB();

}

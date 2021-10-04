package tutoriels.atelier3_3;

import tutoriels.core.app.Atelier;
import tutoriels.core.app.InitializerExercise;
import tutoriels.core.performance_app.PerformanceTestsDriver;

public abstract class Atelier3_3 extends Atelier {

	static {

		new InitializerExercise().initialize(Atelier3_3.class);
	}

	@Override 
	public boolean siExecutable() {return false;}

	@Override 
	public void executer() {}

	@Override
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return new TesteurAtelier3_3();
	}
	
	public abstract <C extends Comparable<C>> Trieur<C> fournirTrieurNaif();

}

package tutoriels.core.app;

import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.debug.T;
import ca.ntro.java.InitializerJava;

public class InitializerExercise extends InitializerJava {
	
	static {
		
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");

		InitializerJava.disableTracingIfInJar();

	}
	
	public void initialize(Class<? extends Atelier> exerciseClass) {

		CurrentExercise.setId(exerciseClass);
		
		super.initialize();
	}

	@Override
	protected ModelStoreSync provideLocalStore() {
		T.call(this);

		return new NitriteStoreExercise();
		//return new FilesStore();
	}




}

package tutoriels.core.app;

import ca.ntro.nitrite.NitriteStore;

public class NitriteStoreExercise extends NitriteStore {

	@Override
	protected String getFileName() {
		return CurrentExercise.getId() + ".db";
	}


}

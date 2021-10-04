package ca.ntro.core.models.stores;

import ca.ntro.core.models.Model;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;

public abstract class LocalStore {
	
	private static ModelStoreSync instance;
	
	public static void initialize(ModelStoreSync instance) {
		LocalStore.instance = instance;
	}

	public static <M extends Model> M get(Class<M> modelClass, String modelId) {
		
		M result = null;
		
		try {
			
			result = (M) instance.getModel(modelClass, modelId);
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
		return result;
	}

	public static void close() {
		T.call(LocalStore.class);

		try {
			
			instance.close();
			
		}catch(NullPointerException e) {
			
			Log.fatalError(LocalStore.class.getSimpleName() + " must be initialized", e);
			
		}
		
	}

}

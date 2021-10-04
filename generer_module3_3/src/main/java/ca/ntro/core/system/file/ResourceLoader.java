package ca.ntro.core.system.file;


import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ResourceAsStringPromise;

public abstract class ResourceLoader {
	
	private static ResourceLoader instance;
	
	public static void initialize(ResourceLoader instance) {
		ResourceLoader.instance = instance;
	}

	public static Promise<String> getResourceAsString(String resourcePath) {
		
		Promise<Resource> resourcePromise = getResource(resourcePath);
		
		return new ResourceAsStringPromise(resourcePromise);
	}
	
	public static Promise<Resource> getResource(String resourcePath) {
		
		Promise<Resource> result = null;
		
		try {
			
			result = instance.getResourcesImpl(resourcePath);
			
		}catch(NullPointerException e){

			System.out.println("[FATAL]" + ResourceLoader.class.getSimpleName() + " must be initialized");
			
		}
		
		return result;
	}
	
	protected abstract Promise<Resource> getResourcesImpl(String resourcePath);

}

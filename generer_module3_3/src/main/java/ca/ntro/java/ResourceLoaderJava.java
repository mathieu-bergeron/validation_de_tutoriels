package ca.ntro.java;

import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.PromiseSync;
import ca.ntro.core.system.file.Resource;
import ca.ntro.core.system.file.ResourceLoader;

public class ResourceLoaderJava extends ResourceLoader {

	@Override
	protected Promise<Resource> getResourcesImpl(String resourcePath) {
		return new PromiseSync<Resource>(new ResourceSwing(ResourceLoaderJava.class.getResourceAsStream(resourcePath)));
	}


}

package ca.ntro.core.system.file;

import ca.ntro.core.promises.Promise;

public interface Resource {
	
	Promise<String> getString();

}

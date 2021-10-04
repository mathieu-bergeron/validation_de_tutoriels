package ca.ntro.java;

import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.debug.T;

public class AppCloserJava extends AppCloser {

	@Override
	protected void closeImpl() {
		T.call(this);
		System.exit(0);
	}

}

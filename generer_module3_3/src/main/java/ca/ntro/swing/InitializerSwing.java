package ca.ntro.swing;

import ca.ntro.core.system.file.ResourceLoader;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.java.ResourceLoaderJava;
import ca.ntro.java.StackAnalyzerJava;

public class InitializerSwing {
	
	public static void initialize() {

		StackAnalyzer.initialize(new StackAnalyzerJava());
		ResourceLoader.initialize(new ResourceLoaderJava());
		
	}

}

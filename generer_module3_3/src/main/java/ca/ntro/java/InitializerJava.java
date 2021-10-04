package ca.ntro.java;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import ca.ntro.core.app.Initializer;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.debug.LabelFormater;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.file.ResourceLoader;
import ca.ntro.core.system.source.SourceFileLocation;
import ca.ntro.core.system.stack.StackAnalyzer;
import ca.ntro.java.ResourceLoaderJava;
import ca.ntro.java.StackAnalyzerJava;
import ca.ntro.java.introspection.IntrospectorJava;
import ca.ntro.java.json.JsonParserJava;

public abstract class InitializerJava extends Initializer {

	public static void disableTracingIfInJar() {
		try {
			String thisFilePath = new File(InitializerJava.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getCanonicalPath();
			if(thisFilePath.endsWith(".jar")) {
				T.setActive(false);
			}
		} catch (IOException | URISyntaxException e) {}
	}

	@Override
	protected StackAnalyzer provideStackAnalyzer() {
		// T.call(this);
		// fakeCallTrace();

		return new StackAnalyzerJava();
	}

	private void fakeCallTrace() {
		StringBuilder label = new StringBuilder();
		LabelFormater.format(label, 
				             "T", 
				             "call", 
				             "InitializerJava.provideStackAnalyzer",
				             new SourceFileLocation("InitializerJava.java", 19));
		System.out.println(label.toString());
	}

	@Override
	protected AppCloser provideAppCloser() {
		T.call(this);
		return new AppCloserJava();
	}

	@Override
	protected ResourceLoader provideResourceLoader() {
		T.call(this);
		return new ResourceLoaderJava();
	}

	@Override
	protected JsonParser provideJsonParser() {
		T.call(this);
		return new JsonParserJava();
	}

	@Override
	protected Introspector provideIntrospector() {
		T.call(this);
		
		return new IntrospectorJava();
	}

	@Override
	protected ValueFormatter provideValueFormatter() {
		T.call(this);
		
		return new ValueFormatterJava();
	}
	
	@Override
	protected NtroCollections provideNtroCollections() {
		return new NtroCollectionsJava();
	}



}

package ca.ntro.core.app;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.NtroCollections;
import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.file.ResourceLoader;
import ca.ntro.core.system.stack.StackAnalyzer;

public abstract class Initializer {
	
	public void initialize() {

		StackAnalyzer.initialize(provideStackAnalyzer());
		AppCloser.initialize(provideAppCloser());
		ResourceLoader.initialize(provideResourceLoader());
		LocalStore.initialize(provideLocalStore());
		JsonParser.initialize(provideJsonParser());
		Introspector.initialize(provideIntrospector());
		ValueFormatter.initialize(provideValueFormatter());
		NtroCollections.initialize(provideNtroCollections());
	}
	
	protected abstract StackAnalyzer provideStackAnalyzer();
	protected abstract AppCloser provideAppCloser();
	protected abstract ResourceLoader provideResourceLoader();
	protected abstract ModelStoreSync provideLocalStore();
	protected abstract JsonParser provideJsonParser();
	protected abstract Introspector provideIntrospector();
	protected abstract ValueFormatter provideValueFormatter();
	protected abstract NtroCollections provideNtroCollections();
	

}

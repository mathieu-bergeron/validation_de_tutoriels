package ca.ntro.core.app;

import ca.ntro.core.OnAppReadyListener;

public abstract class NtroApp {
	
	private static OnAppReadyListener onAppReadyListener;
	
	public static void setOnAppReadyListener(OnAppReadyListener onAppReadyListener) {
		NtroApp.onAppReadyListener = onAppReadyListener;
	}
	
	public static void notifyAppReady() {
		onAppReadyListener.onAppReady();
	}
	
	public NtroApp() {
		
		step01InstallViewPromises();
		step02InstallModelPromises();

	}
	
	protected abstract void step01InstallViewPromises();
	protected abstract void step02InstallModelPromises();
}

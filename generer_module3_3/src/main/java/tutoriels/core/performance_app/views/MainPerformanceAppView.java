package tutoriels.core.performance_app.views;

import ca.ntro.core.views.NtroView;

public interface MainPerformanceAppView extends NtroView {
	
	PerformanceGraphView addGraphView(String title);
}

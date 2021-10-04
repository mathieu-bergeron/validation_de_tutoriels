package tutoriels.core.views;

import ca.ntro.core.views.NtroView;
import tutoriels.core.models.reports.values.ValidationState;

public interface ReportNodeView extends NtroView {
	
	void displayTitle(String title);
	
	void displayValidationState(ValidationState state);

	ReportNodeView appendNewChild(long id);

	ReportNodeView insertChild(int index, long id);

	void removeChild(long id);
	

}

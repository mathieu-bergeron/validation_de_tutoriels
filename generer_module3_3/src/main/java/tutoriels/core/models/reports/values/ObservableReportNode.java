package tutoriels.core.models.reports.values;

import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeViewModel;

public class ObservableReportNode extends ObservableProperty<ReportNodeViewModel>{
	private static final long serialVersionUID = 5634334774038378777L;

	public ObservableReportNode(ReportNodeViewModel value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);
		return ReportNodeViewModel.class;
	}
}

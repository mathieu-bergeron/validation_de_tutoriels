package tutoriels.core.models.reports.values;

import java.util.List;

import ca.ntro.core.models.properties.observable.list.ObservableList;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeModel;
import tutoriels.core.models.reports.ReportNodeViewModel;

@SuppressWarnings("serial")
public class ObservableReportList extends ObservableList<ReportNodeViewModel> {

	public ObservableReportList(List<ReportNodeViewModel> value) {
		super(value);
		T.call(this);
	}

	@Override
	protected Class<?> getValueType() {
		T.call(this);

		return ReportNodeViewModel.class;
	}



}

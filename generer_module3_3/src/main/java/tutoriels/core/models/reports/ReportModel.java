package tutoriels.core.models.reports;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableProperty;
import ca.ntro.core.models.properties.observable.simple.ObservableString;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.stored.simple.StoredString;
import ca.ntro.core.system.debug.T;

public class ReportModel extends Model {
	
	// FIXME: this should be parametrized
	//        a model should exists w/oi
	private ReportNodeViewModel rootReportNode = ReportViewModel.newSubReport();
	
	@Override
	public void initializeStoredValues() {
		T.call(this);
		// XXX: none
	}
	
	public void setReportTitle(String title) {
		T.call(this);
		
		rootReportNode.setTitle(title);
	}

	public void addSubReport(ReportNodeViewModel subReport) {
		T.call(this);
		
		rootReportNode.addSubReport(subReport);
	}


	public ReportNodeViewModel getRootReportNode() {
		return rootReportNode;
	}

	public void setRootReportNode(ReportNodeViewModel rootReportNode) {
		this.rootReportNode = rootReportNode;
	}

}

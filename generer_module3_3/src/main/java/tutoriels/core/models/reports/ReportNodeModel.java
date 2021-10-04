package tutoriels.core.models.reports;


import static tutoriels.core.models.reports.values.ValidationState.*;

import java.util.ArrayList;

import ca.ntro.core.models.Model;
import ca.ntro.core.models.properties.observable.simple.ObservableString;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.values.ObservableReportList;
import tutoriels.core.models.reports.values.ObservableValidationState;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.core.performance_app.models.DataPointModel;
import tutoriels.core.performance_app.models.PerformanceGraphViewModel;

@SuppressWarnings("serial")
public abstract class ReportNodeModel extends Model {

	private ObservableString title = new ObservableString();
	private ObservableValidationState state = new ObservableValidationState(ValidationState.WAITING);

	private ObservableString htmlPage = new ObservableString();

	private ObservableReportList subReports = new ObservableReportList(new ArrayList<>());
	private int expectedNumberOfSubReports = 0;
	
	private int indexOfNextErrorNode = 0;

	private int numberOfOkNodes = 0;

	private ReportNodeModel parent;
	
	public void setTitle(String title) {
		T.call(this);

		// FIXME: " " should be a View thing
		this.title.set(title + " ");
	}

	public void setHtmlPage(String htmlContent) {
		T.call(this);
		
		this.htmlPage.set(htmlContent);
	}
	
	public void setState(ValidationState state) {
		T.call(this);
		
		this.state.set(state);
		

		if(parent != null) {
			parent.reactToSubReportState(this, state);
		}
	}

	protected void reactToSubReportState(ReportNodeModel subReport, ValidationState childState) {
		T.call(this);
		
		moveSubReportOnErrorState(subReport, childState);
		
		this.state.get(new ValueListener<ValidationState>() {
			@Override
			public void onValue(ValidationState myState) {
				T.call(this);
				adjustStateAccordingToSubReportState(childState, myState);
			}
		});
		
	}
	
	private void moveSubReportOnErrorState(ReportNodeModel subReport, ValidationState subReportState) {
		T.call(this);

		if(subReportState == TIMEOUT || subReportState == CRASH || subReportState == ERROR) {
			
			int currentSubReportIndex = getSubReports().indexOf((ReportNodeViewModel) subReport);
			
			// on TIMEOUT, the subreport might already be moved
			if(currentSubReportIndex > indexOfNextErrorNode) {

				getSubReports().removeItem((ReportNodeViewModel) subReport);
				
				getSubReports().insertItem(indexOfNextErrorNode, (ReportNodeViewModel) subReport);
				
				indexOfNextErrorNode++;
			}
		}
	}

	private void adjustStateAccordingToSubReportState(ValidationState childState, ValidationState myState) {
		T.call(this);
		if(myState != TIMEOUT && childState == TIMEOUT) {

			ReportNodeModel.this.setState(TIMEOUT);
			
		}else if(myState != TIMEOUT && myState != CRASH && childState == CRASH) {

			ReportNodeModel.this.setState(CRASH);

		}else if(myState != TIMEOUT && myState != CRASH && myState != ERROR && childState == ERROR) {

			ReportNodeModel.this.setState(ERROR);

		}else if(myState == WAITING && childState == OK) {
			
			numberOfOkNodes++;
			
			if(expectedNumberOfSubReports > 0 && numberOfOkNodes >= expectedNumberOfSubReports) {

				ReportNodeModel.this.setState(OK);

			}else if(numberOfOkNodes >= subReports.size()) {

				ReportNodeModel.this.setState(OK);

			}
		}
	}

	public void addSubReport(ReportNodeViewModel subReport) {
		T.call(this);

		subReport.setParent(this);
		
		subReports.addItem(subReport);
	}

	@Override
	public void initializeStoredValues() {
		T.call(this);
		// XXX: not needed here?
	}
	
	public ObservableString getTitle() {
		return title;
	}

	public void setTitle(ObservableString title) {
		this.title = title;
	}

	public ObservableValidationState getState() {
		return state;
	}

	public void setState(ObservableValidationState state) {
		this.state = state;
	}

	public ObservableString getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(ObservableString htmlPage) {
		this.htmlPage = htmlPage;
	}

	public ObservableReportList getSubReports() {
		return subReports;
	}

	public void setSubReports(ObservableReportList subReports) {
		this.subReports = subReports;
	}

	public ReportNodeModel getParent() {
		return parent;
	}

	public void setParent(ReportNodeModel parent) {
		this.parent = parent;
	}

	public void setExpectedNumberOfSubReports(int expectedNumberOfSubReports) {
		T.call(this);

		this.expectedNumberOfSubReports = expectedNumberOfSubReports;
	}
} 	 

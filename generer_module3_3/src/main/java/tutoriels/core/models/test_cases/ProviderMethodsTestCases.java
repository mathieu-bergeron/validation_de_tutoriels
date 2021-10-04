package tutoriels.core.models.test_cases;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.Model;
import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.TestCaseGenerator;
import tutoriels.core.app.Exercise;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class ProviderMethodsTestCases extends Model {
	private static final long serialVersionUID = -7883125055663886931L;
	
                                                                 // XXX: LinkedHashMap to get testCases in order as best as possible
	private Map<String, ProvidedObjectTestCases> testCases = new LinkedHashMap<>();

	public void generateTestCases(Method providerMethod, Exercise solution, TestCaseGenerator testCaseGenerator) {
		T.call(this);

		Object providedObject = providedObject(solution, providerMethod);

		MustNot.beNull(providedObject);
		
		ProvidedObjectTestCases testCasesForProviderMethod = new ProvidedObjectTestCases();
		
		String providerMethodName = providerMethod.getName();

		testCases.put(providerMethodName, testCasesForProviderMethod);
		
		testCasesForProviderMethod.generateTestCases(providerMethodName, providedObject, testCaseGenerator);
	}

	private Object providedObject(Exercise solution, Method providerMethod) {
		T.call(this);
		
		Object providedObject = null;
		
		try {

			providedObject = providerMethod.invoke(solution, new Object[] {});

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			Log.fatalError("Cannot invoke provider method " + providerMethod, e);
		}
		
		return providedObject;
	}

	public void validate(Exercise exercise, ReportViewModel validationReport) {
		T.call(this);
		
		Map<String, Object> providedObjects = providedObjects(exercise);

		addSubReports(providedObjects, validationReport);
	}

	private class TitleAndReport{
		public String title;
		public ReportNodeViewModel report;
		
		public TitleAndReport(String title, ReportNodeViewModel report) {
			this.title = title;
			this.report = report;
		}
		
	}

	private void addSubReports(Map<String, Object> providedObjects, ReportViewModel validationReport) {

		Map<String, String> titlesAlreadySelectedByAProviderMethod = new HashMap<>();
		
		Map<String, ReportNodeViewModel> subReports = new LinkedHashMap<>();
		
		for(String providerMethodName : providedObjects.keySet()) {
			
			Object providedObject = providedObjects.get(providerMethodName);
			
			TitleAndReport titleAndReport = createProvidedObjectSubReport(providerMethodName, providedObject, validationReport);
			
			makeTitlesUnique(titlesAlreadySelectedByAProviderMethod, subReports, providerMethodName, titleAndReport);
		}
	}

	private void makeTitlesUnique(Map<String, String> titlesAlreadySelectedByAProviderMethod,
			Map<String, ReportNodeViewModel> subReports, String providerMethodName, TitleAndReport titleAndReport) {

		T.call(this);
		
		String title = titleAndReport.title;
		ReportNodeViewModel report = titleAndReport.report;
		
		if(titlesAlreadySelectedByAProviderMethod.containsKey(title)) {
			
			String otherProviderMethodName = titlesAlreadySelectedByAProviderMethod.get(title);
			
			report.setTitle(title + " (<code>" + providerMethodName + "</code>)");
			subReports.get(otherProviderMethodName).setTitle(title + " (<code>" + otherProviderMethodName + "</code>)");

		}else {
			
			titlesAlreadySelectedByAProviderMethod.put(title, providerMethodName);
		}

		subReports.put(providerMethodName, report);
	}
	
	private TitleAndReport createProvidedObjectSubReport(String providerMethodName, Object providedObject, ReportViewModel validationReport) {
		T.call(this);
		
		TitleAndReport titleAndReport;

		if(providedObject != null) {
			
			ProvidedObjectTestCases objectTestCases = testCases.get(providerMethodName);
			titleAndReport = createProvidedObjectSubReport(objectTestCases, providedObject, validationReport);
			
		}else {

			titleAndReport = createNullSubReport(providerMethodName, validationReport);
		}
		
		return titleAndReport;
	}

	private TitleAndReport createProvidedObjectSubReport(ProvidedObjectTestCases objectTestCases,
			Object providedObject, ReportViewModel validationReport) {
		T.call(this);

		String title = providedObject.getClass().getSimpleName();
		
		ReportNodeViewModel report = ReportViewModel.newSubReport();
		validationReport.addSubReport(report);
		
		report.setTitle("<code>" + title + "</code>");
		
		objectTestCases.validate(providedObject, report);
		
		return new TitleAndReport(title, report);
	}

	private TitleAndReport createNullSubReport(String providerMethodName, ReportViewModel validationReport) {
		T.call(this);
		
		String title = providerMethodName;

		ReportNodeViewModel report = ReportViewModel.newSubReport();
		validationReport.addSubReport(report);
		
		report.setTitle("<code>" + title + "</code>");
		
		report.setState(ValidationState.CRASH);

		report.setHtmlPage("Le méthode " + providerMethodName + " a retourné la valeur <code>null</code>");

		return new TitleAndReport(title, report);
	}

	private Map<String, Object> providedObjects(Exercise exercise){
		T.call(this);

		Map<String, Object> providedObjects = new LinkedHashMap<>();
		
		for(String providerMethodName : testCases.keySet()) {
			
			Method providerMethod = Introspector.findMethodByName(exercise.getClass(), providerMethodName);
			
			MustNot.beNull(providerMethod);
			
			Object providedObject = providedObject(exercise, providerMethod);
			
			providedObjects.put(providerMethodName, providedObject);
		}
		
		return providedObjects;
	}
	
	public Map<String, ProvidedObjectTestCases> getTestCases() {
		return testCases;
	}

	public void setTestCases(Map<String, ProvidedObjectTestCases> testCases) {
		this.testCases = testCases;
	}
	
	
	@Override
	public void initializeStoredValues() {
		T.call(this);
	}

	public boolean isEmpty() {
		T.call(this);
		
		return testCases.isEmpty();
	}


}

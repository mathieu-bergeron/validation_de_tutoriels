package tutoriels.core.models.test_cases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class ObjectTestCases extends ClassTestCases {
	private static final long serialVersionUID = 5491422169374607423L;

	// XXX: String == MethodSignature.toString
	private Map<String, MethodTestCases> testCasesByMethod = new LinkedHashMap<>();
	
	private Object providedObject;

	public Map<String, MethodTestCases> getTestCasesByMethod() {
		return testCasesByMethod;
	}

	public void setTestCasesByMethod(Map<String, MethodTestCases> testCasesByMethod) {
		this.testCasesByMethod = testCasesByMethod;
	}

	public void validate(Object providedObject, ReportNodeViewModel report) {
		T.call(this);
		
		// XXX: memorize to validate method testcases
		this.providedObject = providedObject;

		validateClass(providedObject.getClass(), report);
	}

	@Override
	protected void validateMethod(Method method, ReportNodeViewModel report) {
		T.call(this);
		
		MethodTestCases methodTestCases = testCasesByMethod.get(Introspector.methodSignature(method).toString());
		
		if(methodTestCases != null) {
			
			//report.initializePerformanceGraph();

			methodTestCases.validate(providedObject, method, report);
			
		}else {

			report.setState(ValidationState.OK);
		}
	}

}

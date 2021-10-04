package tutoriels.core.models.test_cases;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class MethodTestCases extends ModelValue {
	private static final long serialVersionUID = -7235780255880815953L;

	private String methodName;
	private List<MethodTestCase> testCases = new ArrayList<>();
	
	public MethodTestCases() {
		super();
		T.call(this);
	}

	public MethodTestCases(String methodName) {
		super();
		T.call(this);
		
		this.methodName = methodName;
	}

	public String getMethodName() {
		T.call(this);
		return methodName;
	}

	public void setMethodName(String methodName) {
		T.call(this);
		this.methodName = methodName;
	}
	
	public List<MethodTestCase> getTestCases() {
		T.call(this);

		return testCases;
	}

	public void setTestCases(List<MethodTestCase> testCases) {
		T.call(this);

		this.testCases = testCases;
	}
	
	public void add(MethodTestCase testCase) {
		T.call(this);
		
		testCases.add(testCase);
	}

	public void validate(Object providedObject, Method methodToTest, ReportNodeViewModel methodNode) {
		T.call(this);

		methodNode.setState(ValidationState.WAITING);
		methodNode.setExpectedNumberOfSubReports(testCases.size());
		
		if(testCases.isEmpty()) {
			
			methodNode.setState(ValidationState.OK);
			
		}else {

			for(MethodTestCase testCase : testCases) {

				// XXX: the will block if the method does not return
				testCase.validate(providedObject, methodToTest, methodNode);
			}
		}
	}
	
}

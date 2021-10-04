package tutoriels.core.models.test_cases;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.SerializationUtils;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.ValueFormatter;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.models.reports.ReportNodeModel;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

import static tutoriels.core.Constants.EXECUTION_TIMEOUT_MILISECONDS;

public class MethodTestCase extends ModelValue {
	private static final long serialVersionUID = -4444678156470900885L;

	private String methodName;
	
	// FIXME: this is too loosely typed
	//        for Json serialization
	//        we will not get correct type back
	//        e.g. if arguments is (int arg0)
	//        then we will get 1.0 in the Json and 
	//        we will call the method with a double!
	//
	//        one fix would be to improve
	//        method invokation to include
	//        the re-casting of arguments
	private List<Object> arguments;
	private Object returnValue;
	

	public MethodTestCase() {
		T.call(this);
	}
	
	public MethodTestCase(String methodName, List<Object> arguments, Object returnValue) {
		T.call(this);
		
		this.setMethodName(methodName);
		this.setArguments(arguments);
		this.setReturnValue(returnValue);
	}

	public String getMethodName() {
		T.call(this);
		return methodName;
	}

	public void setMethodName(String methodName) {
		T.call(this);
		this.methodName = methodName;
	}

	public List<Object> getArguments() {
		T.call(this);
		return arguments;
	}

	public void setArguments(List<Object> arguments) {
		T.call(this);
		this.arguments = arguments;
	}

	public Object getReturnValue() {
		T.call(this);
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		T.call(this);
		this.returnValue = returnValue;
	}

	public static Object[] argumentArray(Method method, List<Object> arguments) {
		T.call(MethodTestCase.class);
		
		Object[] argumentArray = new Object[arguments.size()];
		
		for(int i = 0; i < arguments.size(); i++) {
			
			Object argument = arguments.get(i);
			
			argument = Introspector.buildValueForType(method.getParameterTypes()[i], argument);
			
			argument = SerializationUtils.clone((Serializable)argument);
			
			argumentArray[i] = argument;
		}
		
		return argumentArray;
	}


	public void performValidation(Object testableObject, 
			                      Method method, 
			                      ReportNodeModel methodReport, 
			                      ReportNodeModel caseReport) {
		T.call(this);

		Timer timeoutTimer = new Timer();

		startTimeoutTimer(timeoutTimer, method, caseReport);

		invokeMethodAndValidate(testableObject, 
								method, 
								methodReport,
								caseReport, 
								timeoutTimer);
	}

	private void invokeMethodAndValidate(Object testableObject, 
										 Method method, 
										 ReportNodeModel methodReport, 
										 ReportNodeModel caseReport, 
										 Timer timeoutTimer) {
		T.call(this);

		try {
			
			if(testableObject instanceof CloneableObject) {
				testableObject = ((CloneableObject) testableObject).cloneObject();
			}

			Object actualReturnValue = method.invoke(testableObject, argumentArray(method, arguments));
			
			actualReturnValue = Introspector.buildValueForType(method.getReturnType(), actualReturnValue);

			validateReturnValue(caseReport, method, actualReturnValue);

		} catch (IllegalAccessException | IllegalArgumentException e) {

			
			Log.fatalError("Could not invoke " + method.getName() + " during validation", e);

		} catch(InvocationTargetException e) {

			reportCrash(caseReport, method, e.getCause());

		}finally {

			timeoutTimer.cancel();
		}
	}

	private void startTimeoutTimer(Timer timeoutTimer, Method method, ReportNodeModel report) {
		T.call(this);

		timeoutTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				T.call(this);

				report.setState(ValidationState.TIMEOUT);
				
				StringBuilder builder = new StringBuilder();

				builder.append("Délai expiré");
				if(method.getParameterTypes().length > 0) {
					builder.append(" pour les arguments: ");
					formatArguments(method, builder);
				}
				builder.append("<br><br>");
				builder.append("Boucle infinie?");
				
				report.setHtmlPage(builder.toString());

			}
		}, EXECUTION_TIMEOUT_MILISECONDS);
	}

	private void reportCrash(ReportNodeModel report, Method method, Throwable e) {
		T.call(this);
		
		report.setState(ValidationState.CRASH);
		
		StringWriter writer = new StringWriter();
		
		PrintWriter printer = new PrintWriter(writer);
		
		e.printStackTrace(printer);

		StringBuilder builder = new StringBuilder();
		
		builder.append("Plantage");
		if(method.getParameterTypes().length > 0) {
			builder.append(" pour les arguments: ");
			formatArguments(method, builder);
		}
		builder.append("<br><br>");
		builder.append("<pre>" + writer.toString() + "</pre>");
		
		report.setHtmlPage(builder.toString());
	}

	private void validateReturnValue(ReportNodeModel report, Method method, Object returnValue) {
		T.call(this);

		if(this.returnValue == null && returnValue == null) {
			
			reportTestPassed(report, method);
			
		}else if(this.returnValue.equals(returnValue)) {

			reportTestPassed(report, method);
			
		}else {

			reportTestFailed(report, method, returnValue);

		}
	}
	
	private void reportTestPassed(ReportNodeModel report, Method method) {
		T.call(this);
		
		StringBuilder builder = new StringBuilder();
		
		if(method.getParameterTypes().length > 0) {
			builder.append("Pour les arguments: ");
			formatArguments(method, builder);
			builder.append("<br><br>");
		}

		builder.append("La valeur de retour est correcte: ");
		formatValue(returnValue, builder);

		report.setHtmlPage(builder.toString());

		report.setState(ValidationState.OK);
		
	}

	private void reportTestFailed(ReportNodeModel report, Method method, Object returnValue) {
		T.call(this);

		StringBuilder builder = new StringBuilder();

		if(method.getParameterTypes().length > 0) {
			builder.append("Pour les arguments: ");
			formatArguments(method, builder);
			builder.append("<br><br>");
		}
		
		builder.append("La valeur de retour ");

		formatValue(returnValue, builder);

		builder.append(" est erronée.<br><br>La valeur attendue était: ");
		
		formatValue(this.returnValue, builder);
		
		report.setHtmlPage(builder.toString());

		report.setState(ValidationState.ERROR);
	}

	private void formatValue(Object value, StringBuilder builder) {
		T.call(this);
		
		ValueFormatter.setIsHtml(true);
		ValueFormatter.format(builder, value);
		ValueFormatter.setIsHtml(false);
	}

	private void formatArguments(Method method, StringBuilder builder) {
		T.call(this);
		
		Object[] arguments = argumentArray(method, this.arguments);

		ValueFormatter.setIsHtml(true);
		ValueFormatter.format(builder, arguments);
		ValueFormatter.setIsHtml(false);
	}
	
	public void validate(Object testableObject, Method method, ReportNodeViewModel methodReport) {
		T.call(this);

		ReportNodeViewModel caseReport = ReportViewModel.newSubReport();

		methodReport.addSubReport(caseReport);
		
		caseReport.setUseStateAsTitle(true);

		performValidation(testableObject, method, methodReport, caseReport);
	}
}
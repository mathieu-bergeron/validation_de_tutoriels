package ca.ntro.examples.values;

import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;

public class TestValue01 extends ModelValue {
	
	private String testString = "TEST";

	public TestValue01() {
		T.call(this);
	}

	public TestValue01(String testString) {
		T.call(this);
		
		this.testString = testString;
	}

	public String getTestString() {
		T.call(this);
		return testString;
	}

	public void setTestString(String testString) {
		T.call(this);
		this.testString = testString;
	}

}

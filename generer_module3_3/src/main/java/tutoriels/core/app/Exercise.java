package tutoriels.core.app;

import java.lang.reflect.Method;

public abstract class Exercise {

	public abstract boolean isAProviderMethod(Method method);

	public abstract String providerMethodSuffix(Method method);
	public abstract String providerMethodPrefix(Method method);

	
	
	
}

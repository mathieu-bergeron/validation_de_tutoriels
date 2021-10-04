package tutoriels.core.app;

import java.io.File;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.stores.LocalStore;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.system.AppCloser;
import ca.ntro.core.system.debug.T;
import tutoriels.core.Validator;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.core.models.test_cases.ProviderMethodsTestCases;
import tutoriels.core.performance_app.PerformanceTestsDriver;
import tutoriels.core.performance_app.swing.PerformanceApp;
import tutoriels.core.swing.ValidationApp;

public abstract class Atelier extends Exercise {
	
	private final String PREFIX = "fournir";
	private final String SUFFIX_PATTERN = "[A-Z][a-z]*$";
	private final Pattern suffixPattern = Pattern.compile(SUFFIX_PATTERN);
	
	@Override
	public boolean isAProviderMethod(Method method) {
		T.call(this);
		
		return method.getName().startsWith(PREFIX);
	}

	@Override
	public String providerMethodSuffix(Method method) {

		String methodName = method.getName();

		Matcher matcher = suffixPattern.matcher(methodName);
		matcher.find();

		String suffix = matcher.group(0);
		
		return suffix;
	}

	@Override
	public String providerMethodPrefix(Method method) {

		String methodName = method.getName();
		methodName = methodName.replace(PREFIX, "");
		
		String suffix = providerMethodSuffix(method);
		
		methodName = methodName.replace(suffix, "");
		
		return methodName;
	}
	
	protected abstract void executer();
	protected abstract boolean siExecutable();

	public void valider() {
		T.call(this);
		
		checkIfDatabaseExists();

		launchValidationApp();
		performValidation();

		//PerformanceTestsDriver driver = createPerformanceTestsDriver();
		//testPerformance(driver);
		
	}
	
	protected void performValidation() {
		T.call(this);

		Validator.validate(this);
	}

	private void launchValidationApp() {
		T.call(this);

		new ValidationApp(new MainWindowClosedListener() {

			@Override
			public void mainWindowClosed() {
				T.call(this);
				
				ReportViewModel validationReport = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
				validationReport.getRootReportNode().getState().get(new ValueListener<ValidationState>() {

					@Override
					public void onValue(ValidationState value) {
						T.call(this);
						reagirEtatValidation(value);
					}
				});
			}
		});
	}

	protected void checkIfDatabaseExists() {
		T.call(this);

		// XXX: close as soon as possible
		ProviderMethodsTestCases testCases = LocalStore.get(ProviderMethodsTestCases.class, CurrentExercise.getId());
		LocalStore.close();
		
		if(testCases.isEmpty()) {
			T.setActive(false);
			
			String dbFileName = CurrentExercise.getId() + ".db";

			// XXX: delete file in case it was created
			File dbFile = new File(dbFileName);
			dbFile.delete();
			
			System.err.println("Base de données manquante ou corrompue.\n\n >  SVP re-télécharger " + dbFileName + " et\n >  placer le fichier à la racine du projet" );
			
			AppCloser.close();
			System.exit(0);
		}
	}
	
	private void reagirEtatValidation(ValidationState etat) {
		T.call(this);
		
		if(etat == ValidationState.OK /*&& siExecutable()*/) {
			//System.out.println("[La validation est réussie. L'application va maintenant s'exécuter.]\n");

			PerformanceTestsDriver driver = createPerformanceTestsDriver();
			
			if(driver != null) {
				
				System.out.println("[La validation est réussie. Les tests de performance vont maintenant s'exécuter]");
				testPerformance(driver);
				
			}else {

				System.out.println("[La validation est réussie]");
			}
			
			
			//executer();
		/*
		}else if(siExecutable()) {
			System.err.println("La validation est échouée. L'application ne va pas s'exécuter. Désolé.");
		}*/
		}else{
			System.err.println("[La validation est échouée. Les tests de performance ne seront pas exécutés]");
			AppCloser.close();
		}
		
	}
	
	private void testPerformance(PerformanceTestsDriver driver) {
		T.call(this);

		cleanUpAsMuchAsPossible();

		launchPerformanceApp();
		

		driver.testPerformance(this);
	}
	
	private void cleanUpAsMuchAsPossible() {
		T.call(this);

		MemoryStore.clearStore();
		System.gc();
	}

	private void launchPerformanceApp() {
		T.call(this);

		new PerformanceApp(new MainWindowClosedListener() {
			@Override
			public void mainWindowClosed() {
				T.call(this);
				AppCloser.close();
			}
		});
	}
	
	public PerformanceTestsDriver createPerformanceTestsDriver() {
		return null;
	}
}

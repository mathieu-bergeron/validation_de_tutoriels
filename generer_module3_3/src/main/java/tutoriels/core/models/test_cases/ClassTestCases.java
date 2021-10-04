package tutoriels.core.models.test_cases;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.ntro.core.introspection.FieldSignature;
import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.core.models.properties.ModelValue;
import ca.ntro.core.system.debug.T;
import ca.ntro.java.introspection.IntrospectorJava;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class ClassTestCases extends ModelValue {
	private static final long serialVersionUID = -5614773520914619289L;

	private String className;
	private List<String> typeVariables = new ArrayList<>();
	private Map<String, String> typeBounds = new HashMap<>();         // FIXME: only one bound supported for now

	private boolean shouldBeAbstract;
	private boolean shouldBeInterface;
	private List<String> interfacesToImplement = new ArrayList<>();
	private static final List<String> interfacesToExclude = new ArrayList<>();
	static {
		interfacesToExclude.add("Serializable");
	}

	//XXX: String == MethodSignature.toString()
	private List<String> implementedMethods = new ArrayList<>();      // Methods that is not from a superclass
	private List<String> inheritedMethods = new ArrayList<>();        // Methods from a superclass and not overriden
	private List<String> overridenMethods = new ArrayList<>();        // Methods from a superclass that is overriden
	
	//XXX: String == MethodSignature.toString()
	private List<String> constructorsToImplement = new ArrayList<>();


	//XXX: String == FieldSignature.toString()
	private List<String> implementedFields = new ArrayList<>();       // Fields that are not from a superclass
	private List<String> inheritedFields = new ArrayList<>();         // Fields that are from a superclass

	private ClassTestCases parentTestCase;

	private Map<String, ClassTestCases> interfacesTestCases = new LinkedHashMap<>();


	public void generateTestCases(Object providedObject) {
		T.call(this);
		
		generateTestCases(providedObject.getClass());
	}

	private void generateTestCases(Class<?> classToTest) {
		T.call(this);
		
		analyzeClassSignature(classToTest);

		// XXX: dropped after module 3_3
		analyzeConstructors(classToTest);

		analyzeMethods(classToTest);

		// XXX: not necessary after module 3_3
		analyzeFields(classToTest);
		
		analyzeSuperClass(classToTest);

		analyzeInterfaces(classToTest);
	}

	@SuppressWarnings("rawtypes")
	private void analyzeClassSignature(Class<?> classToTest) {
		T.call(this);
		
		for(TypeVariable typeVariable : classToTest.getTypeParameters()) {
			
			String variableName = typeVariable.getName();

			typeVariables.add(variableName);
			
			// FIXME: only one bound supported
			if(typeVariable.getBounds().length > 0) {
				String boundName = typeVariable.getBounds()[0].getTypeName();
				typeBounds.put(variableName, simpleTypeName(boundName));
			}
		}
		
		
		
		this.className = classToTest.getSimpleName();
		
		this.shouldBeAbstract = Modifier.isAbstract(classToTest.getModifiers());
		this.shouldBeInterface = Modifier.isInterface(classToTest.getModifiers());
	}

	private String simpleTypeName(String typeName) {
		T.call(this);

		String[] nameElements = typeName.split("\\.");
		typeName = nameElements[nameElements.length-1];

		return typeName;
	}

	@SuppressWarnings("rawtypes")
	private void analyzeConstructors(Class<?> classToTest) {
		T.call(this);
		
		for(Constructor constructor : classToTest.getConstructors()) {
			constructorsToImplement.add(IntrospectorJava.constructorSignature(constructor).toString());
		}
	}

	private void analyzeInterfaces(Class<?> classToTest) {
		T.call(this);

		for(Class<?> _interface : classToTest.getInterfaces()) {
			if(!interfacesToExclude.contains(_interface.getSimpleName())) {
				interfacesToImplement.add(_interface.getSimpleName());
				
				ClassTestCases interfaceTestCases = new ClassTestCases();
				interfaceTestCases.generateTestCases(_interface);
			
				interfacesTestCases.put(_interface.getSimpleName(), interfaceTestCases);
			}
		}
	}

	private void analyzeSuperClass(Class<?> classToTest) {
		T.call(this);

		if(classToTest.getSuperclass() != null && !classToTest.getSuperclass().equals(Object.class)) {
			parentTestCase = new ClassTestCases();
			parentTestCase.generateTestCases(classToTest.getSuperclass());
		}
	}

	private void analyzeMethods(Class<?> classToTest) {
		T.call(this);

		Set<MethodSignature> declaredMethods = declaredMethods(classToTest);

		Set<MethodSignature> superMethods = superMethods(classToTest);
		
		analyzeDeclaredMethods(declaredMethods, superMethods, overridenMethods, implementedMethods);

		analyzeSuperMethods(declaredMethods, superMethods, inheritedMethods);
	}

	private void analyzeFields(Class<?> classToTest) {
		T.call(this);

		Set<FieldSignature> declaredFields = declaredFields(classToTest);

		Set<FieldSignature> superFields = superFields(classToTest);
		
		analyzeDeclaredFields(declaredFields, implementedFields);
		
		analyzeSuperFields(declaredFields, superFields, inheritedFields);
	}

	private void analyzeDeclaredMethods(Set<MethodSignature> declaredMethods, 
			                            Set<MethodSignature> superMethods,
			                            List<String> overridenMethods,
			                            List<String> implementedMethods) {
		T.call(this);

		for(MethodSignature declaredMethod : declaredMethods) {
			
			if(superMethods.contains(declaredMethod)) {
				
				overridenMethods.add(declaredMethod.toString());

			}else {

				implementedMethods.add(declaredMethod.toString());
			}
		}
	}

	private void analyzeDeclaredFields(Set<FieldSignature> declaredFields, 
			                            List<String> implementedFields) {
		T.call(this);

		for(FieldSignature declaredField : declaredFields) {
			implementedFields.add(declaredField.toString());
		}
	}

	private void analyzeSuperMethods(Set<MethodSignature> declaredMethods, 
			                         Set<MethodSignature> superMethods,
			                         List<String> inheritedMethods) {
		T.call(this);

		for(MethodSignature superMethod : superMethods) {

			if(!declaredMethods.contains(superMethod)) {
				inheritedMethods.add(superMethod.toString());
			}
		}
	}

	private void analyzeSuperFields(Set<FieldSignature> declaredFields, 
			                         Set<FieldSignature> superFields,
			                         List<String> inheritedFields) {
		T.call(this);

		for(FieldSignature superField : superFields) {

			if(!declaredFields.contains(superField)) {
				inheritedFields.add(superField.toString());
			}
		}
	}
	
	private Set<MethodSignature> declaredMethods(Class<?> classToTest){
		T.call(this);

		Set<MethodSignature> declaredMethods = new LinkedHashSet<>();

		for(Method method : classToTest.getDeclaredMethods()) {

			// XXX: exclude private methods from atelier3_3
			if(!Modifier.isPrivate(method.getModifiers())) {
				declaredMethods.add(Introspector.methodSignature(method));
			}
		}
		
		return declaredMethods;
	}

	private Set<FieldSignature> declaredFields(Class<?> classToTest){
		T.call(this);

		Set<FieldSignature> declaredFields = new LinkedHashSet<>();

		for(Field field : classToTest.getDeclaredFields()) {
			declaredFields.add(IntrospectorJava.fieldSignature(field));
		}
		
		return declaredFields;
	}

	private Set<FieldSignature> superFields(Class<?> classToTest) {
		T.call(this);
		
		Set<FieldSignature> superFields = new LinkedHashSet<>();
		
		Class<?> superClass = classToTest.getSuperclass();
		
		if(superClass!= null && !superClass.equals(Object.class)) {
			
			for(FieldSignature superField : declaredFields(superClass)) {
				if(!superField.isPrivate()) {
					superFields.add(superField);
				}
			}

			superFields.addAll(superFields(superClass));
		}
		
		return superFields;
	}

	private Set<MethodSignature> superMethods(Class<?> classToTest) {
		T.call(this);
		
		Set<MethodSignature> superMethods = new LinkedHashSet<>();
		
		Class<?> superClass = classToTest.getSuperclass();
		
		if(superClass!= null && !superClass.equals(Object.class)) {
			
			for(MethodSignature superMethod : declaredMethods(superClass)) {
				if(!superMethod.isPrivate()) {
					superMethods.add(superMethod);
				}
			}

			superMethods.addAll(superMethods(superClass));
		}
		
		return superMethods;
	}

	protected void validateClass(Class<?> currentClass, ReportNodeViewModel report) {
		T.call(this);

		validateClassSignature(currentClass, report);

		// XXX: dropped after module 3_3
		validateConstructors(currentClass, report);

		validateMethods(currentClass, report);

		// XXX: dropped after module 3_3
		validateFields(currentClass, report);

		// XXX: à partir atelier 2.5
	    validateInterfaces(currentClass, report);
		
		validateSuperClass(currentClass, report);
	}

	@SuppressWarnings("rawtypes")
	public void validateConstructors(Class<?> currentClass, ReportNodeViewModel report) {
		
		List<String> actualConstructors = new ArrayList<>();
		
		for(Constructor actualConstructor : currentClass.getConstructors()) {
			actualConstructors.add(IntrospectorJava.constructorSignature(actualConstructor).toString());
		}
		
		List<String> correctConstructors = new ArrayList<>();
		List<String> missingConstructors = new ArrayList<>();
		List<String> superfluousConstructors = new ArrayList<>();
		
		for(String actualConstructor : actualConstructors) {
			if(constructorsToImplement.contains(actualConstructor)) {
				correctConstructors.add(actualConstructor);
			}else {
				superfluousConstructors.add(actualConstructor);
			}
		}
		
		for(String constructorToImplement : constructorsToImplement) {
			if(!actualConstructors.contains(constructorToImplement)) {
				missingConstructors.add(constructorToImplement);
			}
		}
		
		if(!constructorsToImplement.isEmpty() || !missingConstructors.isEmpty() || !superfluousConstructors.isEmpty()) {

			ReportNodeViewModel constructorsNode = ReportViewModel.newSubReport();
			report.addSubReport(constructorsNode);
			constructorsNode.setTitle("<i>constructeurs</i>");
			constructorsNode.setState(ValidationState.WAITING);

			for(String constructor : missingConstructors) {
				MethodSignature constructorSignature = MethodSignature.fromString(constructor);
				ReportNodeViewModel constructorNode = ReportViewModel.newSubReport();
				constructorsNode.addSubReport(constructorNode);
				constructorNode.setTitle("<code>"+ constructorSignature.getName() +"</code>");
				constructorNode.setState(ValidationState.ERROR);
				constructorNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> devrait implanter le constructeur <code>"+ constructor +"</code>");
			}

			for(String constructor : superfluousConstructors) {
				MethodSignature constructorSignature = MethodSignature.fromString(constructor);
				ReportNodeViewModel constructorNode = ReportViewModel.newSubReport();
				constructorsNode.addSubReport(constructorNode);
				constructorNode.setTitle("<code>"+ constructorSignature.getName() +"</code>");
				constructorNode.setState(ValidationState.ERROR);
				constructorNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> ne devrait pas implanter le constructeur <code>"+ constructor +"</code>");
			}

			for(String constructor : correctConstructors) {
				MethodSignature constructorSignature = MethodSignature.fromString(constructor);
				ReportNodeViewModel constructorNode = ReportViewModel.newSubReport();
				constructorsNode.addSubReport(constructorNode);
				constructorNode.setTitle("<code>"+ constructorSignature.getName() +"</code>");
				constructorNode.setState(ValidationState.OK);
				constructorNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> doit effectivement implanter le constructeur <code>"+ constructor +"</code>");
			}
		}
	}

	public void validateInterfaces(Class<?> currentClass, ReportNodeViewModel report) {
		
		List<Class<?>> actualInterfaces = new ArrayList<>();
		List<String> actualInterfacesNames = new ArrayList<>();
		
		for(Class<?> _interface : currentClass.getInterfaces()) {
			if(!interfacesToExclude.contains(_interface.getSimpleName())) {
				actualInterfaces.add(_interface);
				actualInterfacesNames.add(_interface.getSimpleName());
			}
		}
		
		List<Class<?>> correctInterfaces = new ArrayList<>();
		List<String> missingInterfaces = new ArrayList<>();
		List<String> superfluousInterfaces = new ArrayList<>();
		
		for(Class<?> actualInterface : actualInterfaces) {
			if(interfacesToImplement.contains(actualInterface.getSimpleName())) {
				correctInterfaces.add(actualInterface);
			}else {
				superfluousInterfaces.add(actualInterface.getSimpleName());
			}
		}
		
		for(String _interface : interfacesToImplement) {
			if(!actualInterfacesNames.contains(_interface)) {
				missingInterfaces.add(_interface);
			}
		}
		
		if(!interfacesToImplement.isEmpty() || !missingInterfaces.isEmpty() || !superfluousInterfaces.isEmpty()) {

			ReportNodeViewModel interfacesNode = ReportViewModel.newSubReport();
			report.addSubReport(interfacesNode);
			interfacesNode.setTitle("<i>interfaces</i>");
			interfacesNode.setState(ValidationState.WAITING);
			
			for(String _interface : missingInterfaces) {
				ReportNodeViewModel interfaceNode = ReportViewModel.newSubReport();
				interfacesNode.addSubReport(interfaceNode);
				interfaceNode.setTitle("<code>"+ _interface +"</code>");
				interfaceNode.setState(ValidationState.ERROR);
				interfaceNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> devrait implanter l'interface <code>"+_interface+"</code>");
			}

			for(String _interface : superfluousInterfaces) {
				ReportNodeViewModel interfaceNode = ReportViewModel.newSubReport();
				interfacesNode.addSubReport(interfaceNode);
				interfaceNode.setTitle("<code>"+ _interface +"</code>");
				interfaceNode.setState(ValidationState.ERROR);
				interfaceNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> ne devrait pas implanter l'interface <code>"+_interface+"</code>");
			}

			for(Class<?> _interface : correctInterfaces) {
				
				String interfaceName = _interface.getSimpleName();
				
				ReportNodeViewModel interfaceNode = ReportViewModel.newSubReport();
				interfacesNode.addSubReport(interfaceNode);
				interfaceNode.setTitle("<code>"+ interfaceName +"</code>");
				interfaceNode.setState(ValidationState.OK);

				interfaceNode.setHtmlPage("La classe <code>"+currentClass.getSimpleName()+"</code> doit effectivement implanter l'interface <code>"+interfaceName+"</code>");
				
				ClassTestCases interfaceTestCases = interfacesTestCases.get(interfaceName);
				
				if(interfaceTestCases != null) {
					interfaceTestCases.validateClass(_interface, interfaceNode);
				}
			}
		}
	}

	public void validateSuperClass(Class<?> currentClass, ReportNodeViewModel report) {
		T.call(this);
		
		if(currentClass.getSuperclass() == null) {
			return;
		}
		
		if(parentTestCase == null && !currentClass.getSuperclass().equals(Object.class)) {

			ReportNodeViewModel superClassNode = ReportViewModel.newSubReport();
			report.addSubReport(superClassNode);
			superClassNode.setTitle("<i>superclasse</i>");
			
			superClassNode.setState(ValidationState.ERROR);
			
			superClassNode.setHtmlPage("La classe <code>" + currentClass.getSimpleName() + "</code> ne devrait pas hériter d'une autre classe");

		}else if(parentTestCase != null && currentClass.getSuperclass().equals(Object.class)) {

			ReportNodeViewModel superClassNode = ReportViewModel.newSubReport();
			report.addSubReport(superClassNode);
			superClassNode.setTitle("<i>superclasse</i>");
			
			superClassNode.setState(ValidationState.ERROR);
			
			superClassNode.setHtmlPage("La classe <code>" + currentClass.getSimpleName() + "</code> devrait hériter de <code>" + parentTestCase.getClassName() +"</code>");

		}else if(parentTestCase != null && !currentClass.getSuperclass().getSimpleName().equals(parentTestCase.getClassName())) {

			ReportNodeViewModel superClassNode = ReportViewModel.newSubReport();
			report.addSubReport(superClassNode);
			superClassNode.setTitle("<i>superclasse</i>");
			
			superClassNode.setState(ValidationState.ERROR);
			
			superClassNode.setHtmlPage("La classe <code>" + currentClass.getSimpleName() + "</code> devrait hériter de <code>" + parentTestCase.getClassName() + "</code> et non de <code>" + currentClass.getSuperclass().getSimpleName() + "</code>");

		}else if(parentTestCase != null && currentClass.getSuperclass().getSimpleName().equals(parentTestCase.getClassName())) {

			ReportNodeViewModel superClassNode = ReportViewModel.newSubReport();
			report.addSubReport(superClassNode);
			superClassNode.setTitle("<i>superclasse</i>");

			ReportNodeViewModel parentTestCaseNode = ReportViewModel.newSubReport();
			superClassNode.addSubReport(parentTestCaseNode);
			
			parentTestCaseNode.setTitle("<code>" + parentTestCase.getClassName() + "</code>");
			parentTestCaseNode.setState(ValidationState.OK);
			
			if(currentClass.getSuperclass() != null) {
				parentTestCase.validateClass(currentClass.getSuperclass(), parentTestCaseNode);
			}
		}
	}

	protected void validateClassSignature(Object providedObject, ReportNodeViewModel reportNode) {
		T.call(this);
		
		validateClassSignature(providedObject.getClass(), reportNode);
	}

	@SuppressWarnings("rawtypes")
	private void validateClassSignature(Class<?> currentClass, ReportNodeViewModel reportNode) {
		T.call(this);
		
		boolean isValid = true;

		boolean isActuallyAbstract = Modifier.isAbstract(currentClass.getModifiers());
		boolean isActuallyInterface = Modifier.isInterface(currentClass.getModifiers());
		
		List<String> actualTypeVariables = new ArrayList<>();
		Map<String, String> actualTypeBounds = new HashMap<>();

		for(TypeVariable typeVariable : currentClass.getTypeParameters()) {
			
			String variableName = typeVariable.getName();

			actualTypeVariables.add(variableName);
			
			// FIXME: only one bound supported
			if(typeVariable.getBounds().length > 0) {
				String boundName = typeVariable.getBounds()[0].getTypeName();
				actualTypeBounds.put(variableName, simpleTypeName(boundName));
			}
		}

		List<String> correctTypeVariables = new ArrayList<>();
		List<String> missingTypeVariables = new ArrayList<>();
		List<String> superfluousTypeVariables = new ArrayList<>();
		
		for(String actualTypeVariable : actualTypeVariables) {
			if(typeVariables.contains(actualTypeVariable)) {
				correctTypeVariables.add(actualTypeVariable);
			}else {
				superfluousTypeVariables.add(actualTypeVariable);
			}
		}
		
		for(String neededTypeVariable : typeVariables) {
			if(!actualTypeVariables.contains(neededTypeVariable)) {
				missingTypeVariables.add(neededTypeVariable);
			}
		}
		
		StringBuilder builder = new StringBuilder();
		
		String theClass = "La classe";
		String ofTheClass = "de la classe";
		if(shouldBeInterface) {
			theClass = "L'interface";
			ofTheClass = "de l'interface";
		}
		
		
		if(currentClass.getSimpleName().equals(className)) {

			builder.append("Le nom de " + ofTheClass + " est correct: <code>"+ currentClass.getSimpleName()+"</code>");

		}else {
			
			builder.append("Le nom de " + ofTheClass + " est <code>"+ currentClass.getSimpleName()+"</code>, mais devrait être <code>" + className + "</code>");
			isValid = false;
		}
		
		boolean isInterface = false;

		if(isActuallyInterface && shouldBeInterface) {

			builder.append("<br><br>");
			builder.append("<code>"+ currentClass.getSimpleName()+"</code> doit effectivement être une interface");
			isInterface = true;

		}else if(isActuallyInterface && !shouldBeInterface) {

			builder.append("<br><br>");
			builder.append("<code>"+ currentClass.getSimpleName()+"</code> ne devrait pas être une interface");
			isValid = false;
			
		}else if(!isActuallyInterface && shouldBeInterface) {
			
			builder.append("<br><br>");
			builder.append("<code>"+ currentClass.getSimpleName()+"</code> devrait être une interface");
			isValid = false;
		}
		
		if(!isInterface && isActuallyAbstract && shouldBeAbstract) {
			
			builder.append("<br><br>");
			builder.append(theClass + " <code>"+ currentClass.getSimpleName()+"</code> doit effectivement être abstraite");

		}else if(!isInterface && isActuallyAbstract && !shouldBeAbstract) {

			builder.append("<br><br>");
			builder.append(theClass + " <code>"+ currentClass.getSimpleName()+"</code> ne devrait pas être abstraite");
			isValid = false;
				
		}else if(!isInterface && !isActuallyAbstract && shouldBeAbstract) {

			builder.append("<br><br>");
			builder.append(theClass + " <code>"+ currentClass.getSimpleName()+"</code> devrait être abstraite");
			isValid = false;
				
		}
		
		if(!correctTypeVariables.isEmpty() || !missingTypeVariables.isEmpty() || !superfluousTypeVariables.isEmpty()) {
			
			for(String typeVariable : correctTypeVariables) {
				
				String neededBound = typeBounds.get(typeVariable);
				
				if(neededBound != null) {

					String actualBound = actualTypeBounds.get(typeVariable);
					
					if(neededBound.equals(actualBound)) {

						builder.append("<br><br>");
						builder.append("Ce paramètre de type est correct: <code>&lt;" + typeVariable + " extends "+ neededBound +"&gt;</code>");
						
					}else{

						builder.append("<br><br>");
						builder.append("Ce paramètre de type est incorrect <code>&lt;" + typeVariable + " extends "+ actualBound +"&gt;</code>");
						builder.append("et devrait plutôt être: &lt;" + typeVariable + " extends "+ neededBound +"&gt;");
						isValid = false;
					}
					
				}else {

					builder.append("<br><br>");
					builder.append("Ce paramètre de type est correct: &lt;" + typeVariable + "&gt;");
				}
			}
			
			for(String typeVariable : missingTypeVariables) {

				String neededBound = typeBounds.get(typeVariable);

				if(neededBound != null) {

					builder.append("<br><br>");
					builder.append("Il manque ce paramètre de type: <code>&lt;" + typeVariable + " extends "+ neededBound +"&gt;</code>");
					isValid = false;

				}else {

					builder.append("<br><br>");
					builder.append("Il manque ce paramètre de type: <code>&lt;" + typeVariable + "&gt;</code>");
					isValid = false;
				}
			}

			for(String typeVariable : superfluousTypeVariables) {

				String actualBound = actualTypeBounds.get(typeVariable);

				if(actualBound != null) {

					builder.append("<br><br>");
					builder.append("Ce paramètre de type est erroné: <code>&lt;" + typeVariable + " extends "+ actualBound +"&gt;</code>");
					isValid = false;

				}else {

					builder.append("<br><br>");
					builder.append("Ce paramètre de type est erroné: <code>&lt;" + typeVariable + "&gt;</code>");
					isValid = false;
				}
			}
			
		}
		

		reportNode.setHtmlPage(builder.toString());
		
		if(isValid) {
			
			reportNode.setState(ValidationState.OK);
			
		}else {

			reportNode.setState(ValidationState.ERROR);
		}
	}

	protected void validateMethodTestCases(Object providedObject, String methodSignature, ReportNodeViewModel report) {
		T.call(this);
		
		// XXX: nothing to do here. This only works in ObjectTestCases
	}

	private void validateFields(Class<?> currentClass, ReportNodeViewModel report) {
		T.call(this);

		Set<FieldSignature> actualDeclaredFields = declaredFields(currentClass);
		Set<FieldSignature> actualSuperFields = superFields(currentClass);
		
		List<String> actualInheritedFields = new ArrayList<>();
		List<String> actualImplementedFields = new ArrayList<>();

		analyzeDeclaredFields(actualDeclaredFields, actualImplementedFields);

		analyzeSuperFields(actualDeclaredFields, actualSuperFields, actualInheritedFields);
		
		validateRequiredFields(currentClass, inheritedFields, actualInheritedFields, "attributs hérités", "hériter de", report);
		validateRequiredFields(currentClass, implementedFields, actualImplementedFields, "attributs définis", "définir", report);
	}

	private void validateMethods(Class<?> currentClass, ReportNodeViewModel report) {
		T.call(this);

		Set<MethodSignature> actualDeclaredMethods = declaredMethods(currentClass);
		Set<MethodSignature> actualSuperMethods = superMethods(currentClass);
		
		List<String> actualInheritedMethods = new ArrayList<>();
		List<String> actualOverridenMethods = new ArrayList<>();
		List<String> actualImplementedMethods = new ArrayList<>();

		analyzeDeclaredMethods(actualDeclaredMethods, actualSuperMethods, actualOverridenMethods, actualImplementedMethods);

		analyzeSuperMethods(actualDeclaredMethods, actualSuperMethods, actualInheritedMethods);
		
		validateRequiredMethods(currentClass, inheritedMethods, actualInheritedMethods, "méthodes héritées", "hériter de", report);
		validateRequiredMethods(currentClass, overridenMethods, actualOverridenMethods, "méthodes redéfinies", "redéfinir", report);
		
		String implemented = "implantées";
		String implement = "implanter";
		if(shouldBeInterface) {
			implemented = "définies";
			implement = "définir";
		}
		
		validateRequiredMethods(currentClass, implementedMethods, actualImplementedMethods, "méthodes " + implemented, implement, report);
		
		/*
		List<String> allMethods = new ArrayList<>();
		allMethods.addAll(inheritedMethods);
		allMethods.addAll(overridenMethods);
		allMethods.addAll(implementedMethods);

		List<String> actualMethods = new ArrayList<>();
		actualMethods.addAll(actualInheritedMethods);
		actualMethods.addAll(actualOverridenMethods);
		actualMethods.addAll(actualImplementedMethods);

		validateRequiredMethods(currentClass, allMethods, actualMethods, "méthodes", "définir ou hériter", report);
		*/
	}

	private void validateRequiredFields(Class<?> currentClass, 
			List<String> requiredSignatures,
			List<String> actualSignatures,
			String nodeTitle,
			String verb,
			ReportNodeViewModel report) {

		T.call(this);
		
		List<FieldSignature> correctFields = new ArrayList<>();
		List<FieldSignature> missingFields = new ArrayList<>();
		List<FieldSignature> superfluousFields = new ArrayList<>();
		
		for(String signatureString : requiredSignatures) {

			if(actualSignatures.contains(signatureString)) {

				FieldSignature signature = FieldSignature.fromString(signatureString);

				correctFields.add(signature);

			}else {

				missingFields.add(FieldSignature.fromString(signatureString));
			}
		}

		for(String signatureString : actualSignatures) {
			if(!requiredSignatures.contains(signatureString)) {
				
				superfluousFields.add(FieldSignature.fromString(signatureString));
			}
		}
		
		if(!correctFields.isEmpty() || !missingFields.isEmpty() || !superfluousFields.isEmpty()) {

			ReportNodeViewModel requiredMethodsNode = ReportViewModel.newSubReport();
			report.addSubReport(requiredMethodsNode);
			requiredMethodsNode.setTitle("<i>" + nodeTitle + "</i>");
			
			for(FieldSignature fieldSignature : missingFields) {

				ReportNodeViewModel methodNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(methodNode);
				methodNode.setTitle("<code>" + fieldSignature.getName() + "</code>");
				
				methodNode.setState(ValidationState.ERROR);
				methodNode.setHtmlPage("La classe <code>" + className + "</code> devrait " + verb + " l'attribut <code>" + fieldSignature + "</code>");
			}

			for(FieldSignature fieldSignature : superfluousFields) {

				ReportNodeViewModel methodNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(methodNode);
				methodNode.setTitle("<code>" + fieldSignature.getName() + "</code>");
				
				methodNode.setState(ValidationState.ERROR);
				methodNode.setHtmlPage("La classe <code>" + className + "</code> ne doit pas " + verb + " l'attribut <code>" + fieldSignature + "</code>");
			}

			for(FieldSignature fieldSignature : correctFields) {
				
				ReportNodeViewModel fieldNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(fieldNode);
				fieldNode.setTitle("<code>" + fieldSignature.getName() + "</code>");
				fieldNode.setState(ValidationState.OK);
				fieldNode.setHtmlPage("La classe <code>" + className + "</code> doit effectivement " + verb + " l'attribut <code>" + fieldSignature + "</code>");
			}
		}
	}

	private void validateRequiredMethods(Class<?> currentClass, 
			List<String> requiredSignatures,
			List<String> actualSignatures,
			String nodeTitle,
			String verb,
			ReportNodeViewModel report) {

		T.call(this);
		
		
		List<Method> correctMethods = new ArrayList<>();
		List<MethodSignature> missingMethods = new ArrayList<>();
		List<MethodSignature> superfluousMethods = new ArrayList<>();
		
		for(String signatureString : requiredSignatures) {

			if(actualSignatures.contains(signatureString)) {

				MethodSignature signature = MethodSignature.fromString(signatureString);

				Method method = Introspector.findMethodBySignature(currentClass, signature);

				if(method != null) {
					correctMethods.add(method);
				}

			}else {

				missingMethods.add(MethodSignature.fromString(signatureString));
			}
		}

		for(String signatureString : actualSignatures) {
			if(!requiredSignatures.contains(signatureString)) {
				
				superfluousMethods.add(MethodSignature.fromString(signatureString));
			}
		}
		
		if(!correctMethods.isEmpty() || !missingMethods.isEmpty() || !superfluousMethods.isEmpty()) {

			String theClass = "La classe";
			if(shouldBeInterface) {
				theClass = "L'interface";
			}

			ReportNodeViewModel requiredMethodsNode = ReportViewModel.newSubReport();
			report.addSubReport(requiredMethodsNode);
			requiredMethodsNode.setTitle("<i>" + nodeTitle + "</i>");
			requiredMethodsNode.setState(ValidationState.WAITING);
			requiredMethodsNode.setExpectedNumberOfSubReports(correctMethods.size());
			
			for(MethodSignature methodSignature : missingMethods) {

				ReportNodeViewModel methodNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(methodNode);
				methodNode.setTitle("<code>" + methodSignature.getName() + "</code>");
				
				methodNode.setState(ValidationState.ERROR);
				methodNode.setHtmlPage(theClass + " <code>" + className + "</code> devrait " + verb + " la méthode <code>" + methodSignature + "</code>");
			}

			for(MethodSignature methodSignature : superfluousMethods) {

				ReportNodeViewModel methodNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(methodNode);
				methodNode.setTitle("<code>" + methodSignature.getName() + "</code>");
				
				methodNode.setState(ValidationState.ERROR);
				methodNode.setHtmlPage(theClass + " <code>" + className + "</code> ne doit pas " + verb + " la méthode <code>" + methodSignature + "</code>");
			}
			
			correctMethods = MethodOrdering.reorderMethods(correctMethods);

			for(Method correctMethod : correctMethods) {
				
				MethodSignature methodSignature = Introspector.methodSignature(correctMethod);

				ReportNodeViewModel methodNode = ReportViewModel.newSubReport();
				requiredMethodsNode.addSubReport(methodNode);
				methodNode.setTitle("<code>" + methodSignature.getName() + "</code>");
				methodNode.setHtmlPage(theClass + " <code>" + className + "</code> doit effectivement " + verb + " la méthode <code>" + methodSignature + "</code>");

				validateMethod(correctMethod, methodNode);
			}
		}
	}


	protected void validateMethod(Method method, ReportNodeViewModel methodNode) {
		T.call(this);
		
		// XXX: in ObjectTestCases, we validate against testcases
		//      here the method is defacto OK
		methodNode.setState(ValidationState.OK);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public ClassTestCases getParentTestCase() {
		return parentTestCase;
	}

	public void setParentTestCase(ClassTestCases parentTestCase) {
		this.parentTestCase = parentTestCase;
	}

	public List<String> getImplementedMethods() {
		return implementedMethods;
	}

	public void setImplementedMethods(List<String> implementedMethods) {
		this.implementedMethods = implementedMethods;
	}

	public List<String> getInheritedMethods() {
		return inheritedMethods;
	}

	public void setInheritedMethods(List<String> inheritedMethods) {
		this.inheritedMethods = inheritedMethods;
	}

	public List<String> getOverridenMethods() {
		return overridenMethods;
	}

	public void setOverridenMethods(List<String> overridenMethods) {
		this.overridenMethods = overridenMethods;
	}

	public List<String> getImplementedFields() {
		return implementedFields;
	}

	public void setImplementedFields(List<String> implementedFields) {
		this.implementedFields = implementedFields;
	}

	public List<String> getInheritedFields() {
		return inheritedFields;
	}

	public void setInheritedFields(List<String> inheritedFields) {
		this.inheritedFields = inheritedFields;
	}

	public boolean getShouldBeAbstract() {
		return shouldBeAbstract;
	}

	public void setShouldBeAbstract(boolean shouldBeAbstract) {
		this.shouldBeAbstract = shouldBeAbstract;
	}

	public boolean getShouldBeInterface() {
		return shouldBeInterface;
	}

	public void setShouldBeInterface(boolean shouldBeInterface) {
		this.shouldBeInterface = shouldBeInterface;
	}

	public List<String> getInterfacesToImplement() {
		return interfacesToImplement;
	}

	public void setInterfacesToImplement(List<String> interfacesToImplement) {
		this.interfacesToImplement = interfacesToImplement;
	}

	public Map<String, ClassTestCases> getInterfacesTestCases() {
		return interfacesTestCases;
	}

	public void setInterfacesTestCases(Map<String, ClassTestCases> interfacesTestCases) {
		this.interfacesTestCases = interfacesTestCases;
	}

	public List<String> getConstructorsToImplement() {
		return constructorsToImplement;
	}

	public void setConstructorsToImplement(List<String> constructorsToImplement) {
		this.constructorsToImplement = constructorsToImplement;
	}

	public List<String> getTypeVariables() {
		return typeVariables;
	}

	public void setTypeVariables(List<String> typeVariables) {
		this.typeVariables = typeVariables;
	}

	public Map<String, String> getTypeBounds() {
		return typeBounds;
	}

	public void setTypeBounds(Map<String, String> typeBounds) {
		this.typeBounds = typeBounds;
	}

}

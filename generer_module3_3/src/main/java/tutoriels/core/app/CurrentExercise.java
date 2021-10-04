package tutoriels.core.app;


public class CurrentExercise {
	
	private static String id;
	
	public static void setId(Class<? extends Atelier> exerciseClass) {
		String exerciseName = exerciseClass.getSimpleName();
		
		String lowerCaseName = exerciseName.substring(0,1).toLowerCase() + exerciseName.substring(1);
		
		CurrentExercise.id = lowerCaseName;
	}
	
	public static String getId() {
		return CurrentExercise.id;
	}


}

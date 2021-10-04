package tutoriels.generer_theorie3_3;

public class Quadratique {

	public void quadratique(int tailleEntree) {
		for(long i = 0; i < Math.pow(tailleEntree, 2); i++) {
			Theorie3_3.perdreDuTemps(0.9);
		}
	}

}

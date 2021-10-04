package tutoriels.generer_theorie3_3;

public class TrieurLong {
	
	public char[] trier(char[] entree) {
		char[] resultat = new char[0];
		
		while(!siVide(entree)) {

			char valeurMinimale = valeurMinimale(entree);
			
			entree = retirer(entree, valeurMinimale);
			
			resultat = ajouter(resultat, valeurMinimale);
		}
		
		return resultat;
	}

	private boolean siVide(char[] tableau) {
		return tableau.length == 0;
	}

	private char[] ajouter(char[] tableau, char valeur) {
		char[] resultat = new char[tableau.length+1];
		
		for(int i = 0; i < tableau.length; i++) {
			resultat[i] = tableau[i];
		}
		
		resultat[resultat.length-1] = valeur;

		return resultat;
	}

	private char[] retirer(char[] tableau, char valeur) {
		char[] resultat = new char[tableau.length-1];
		int incrementSiTrouve = 0;
		
		for(int i = 0; i < resultat.length; i++) {
			if(tableau[i] == valeur && incrementSiTrouve == 0) {
				incrementSiTrouve = 1;
			}
			resultat[i] = tableau[i+incrementSiTrouve];
		}

		return resultat;
	}

	private char valeurMinimale(char[] entree) {
		char valeurMinimale = entree[0];
		
		for(int i = 1; i < entree.length; i++) {
			if(entree[i] < valeurMinimale) {
				valeurMinimale = entree[i];
			}
		}
		
		return valeurMinimale;
	}

}

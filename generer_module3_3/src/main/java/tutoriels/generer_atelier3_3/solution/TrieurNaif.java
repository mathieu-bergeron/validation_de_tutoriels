package tutoriels.generer_atelier3_3.solution;

import tutoriels.atelier3_3.MonTableau;
import tutoriels.atelier3_3.Tableau;
import tutoriels.atelier3_3.Trieur;

public class TrieurNaif<C extends Comparable<C>> implements Trieur<C> {

	@Override
	public Tableau<C> trier(Tableau<C> entree) {
		Tableau<C> resultat = new MonTableau<C>();

		while(!entree.siVide()) {

			C valeurMinimale = valeurMinimale(entree);
			entree.retirer(valeurMinimale);
			resultat.ajouter(valeurMinimale);
		}

		return resultat;
	}
	
	private C valeurMinimale(Tableau<C> valeurs) {
		C valeurMinimale = null;
		
		if(valeurs.longueur() > 0) {
			valeurMinimale = valeurs.obtenirValeur(0);
		}
		
		for(int i = 1; i < valeurs.longueur(); i++) {
			if(valeurs.obtenirValeur(i).compareTo(valeurMinimale) < 0) {
				valeurMinimale = valeurs.obtenirValeur(i);
			}
		}
		
		return valeurMinimale;
	}
}

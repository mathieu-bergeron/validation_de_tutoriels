package tutoriels.generer_atelier3_3.solution;

import tutoriels.atelier3_3.Tableau;
import tutoriels.atelier3_3.Trieur;

public class TrieurMoinsNaif<C extends Comparable<C>> implements Trieur<C> {

	@Override
	public Tableau<C> trier(Tableau<C> entree) {
		Tableau<C> resultat = entree.cloner();

		for(int i = 0; i < entree.longueur(); i++) {
			
			int indiceValeurMinimale = indiceValeurMinimale(entree);
			
			C valeurMinimale = entree.obtenirValeur(indiceValeurMinimale);

			entree.modifierValeur(indiceValeurMinimale, null);

			resultat.modifierValeur(i, valeurMinimale);
		}
		
		return resultat;
	}
	
	private int indiceValeurMinimale(Tableau<C> valeurs) {
		int indiceValeurMinimale = 0;
		C valeurMinimale = null;
		
		while(valeurs.obtenirValeur(indiceValeurMinimale) == null) {
			indiceValeurMinimale++;
		}
		
		valeurMinimale = valeurs.obtenirValeur(indiceValeurMinimale);
		
		for(int i = indiceValeurMinimale+1; i < valeurs.longueur(); i++) {
			if(valeurs.obtenirValeur(i) != null) {
				if(valeurs.obtenirValeur(i).compareTo(valeurMinimale) < 0) {
					indiceValeurMinimale = i;
					valeurMinimale = valeurs.obtenirValeur(i);
				}
			}
		}

		return indiceValeurMinimale;
	}
}

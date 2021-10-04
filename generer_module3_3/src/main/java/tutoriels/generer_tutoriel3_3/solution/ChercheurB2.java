package tutoriels.generer_tutoriel3_3.solution;

import tutoriels.tutoriel3_3.Chercheur;
import tutoriels.tutoriel3_3.Tableau;

public class ChercheurB2<C extends Comparable<C>> implements Chercheur<C> {

	@Override
	public int trouverIndicePourValeur(Tableau<C> tab, C v) {
		int i,l;

		// Correct, mais inefficace
		// (le problème est que i est trouvé l fois)
		for(i=0,l=l(tab);l>0;i=eq(v,v(tab,i%l(tab)))?(--l>0?i++:i):++i);
		
		// Correct et efficace
		//for(i=0,l=l(tab);--l>0;i=eq(v,v(tab,i))?i:++i);

		return i;
	}
	
	private boolean eq(C v1, C v2) {
		return v1.compareTo(v2) == 0;
	}

	private C v(Tableau<C> tab, int i) {
		return tab.obtenirValeur(i);
	}

	private int l(Tableau<C> tab) {
		return tab.longueur();
	}


}

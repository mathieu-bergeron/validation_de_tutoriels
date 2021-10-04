package tutoriels.atelier3_3;

public interface Tableau<C extends Comparable<C>> extends Cloneable {

	boolean siVide();
	
	int longueur();

	C obtenirValeur(int indice);

	void modifierValeur(int indice, C nouvelleValeur);

	void ajouter(C nouvelleValeur);

	void retirer(int indice);

	void retirer(C valeur);
	
	Object clone();

	Tableau<C> cloner();

}

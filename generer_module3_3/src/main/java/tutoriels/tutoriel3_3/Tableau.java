package tutoriels.tutoriel3_3;

public interface Tableau<C extends Comparable<C>> {

	int longueur();

	C obtenirValeur(int indice);
	
	Tableau<C> cloner();

}

package tutoriels.tutoriel3_3;

public interface Chercheur<C extends Comparable<C>> {
	
	int trouverIndicePourValeur(Tableau<C> tableau, C valeur);

}

package tutoriels.atelier3_3;

public interface Trieur<C extends Comparable<C>> {
	
	Tableau<C> trier(Tableau<C> entree);
}

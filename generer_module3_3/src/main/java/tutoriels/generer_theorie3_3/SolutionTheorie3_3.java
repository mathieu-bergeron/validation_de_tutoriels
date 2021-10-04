package tutoriels.generer_theorie3_3;


public class SolutionTheorie3_3 extends Theorie3_3 {
	
	public static void main(String[] args) {
		
		new SolutionTheorie3_3().valider();
	}

	@Override
	public Object fournirLineaire() {
		return new Lineaire();
	}

	@Override
	public Object fournirQuadratique() {
		return new Quadratique();
	}

	@Override
	public Object fournirExponentiel() {
		return new Exponentiel();
	}

	@Override
	public Object fournirTrieurRaccourci() {
		return new TrieurRaccourci();
	}

	@Override
	public Object fournirTrieurLong() {
		return new TrieurLong();
	}

}

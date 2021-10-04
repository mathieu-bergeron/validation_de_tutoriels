package tutoriels.generer_theorie3_3;

public class TrieurRaccourci {

	public char[] trier(char[] e) {
		char[] r=new char[0];

		while(e.length>0) {
			// trouver la valeur minimale
			char vm=e[0];
			for(int i=-1;++i<e.length;vm=e[i]<vm?e[i]:vm);

			// retirer la valeur minimale de l'entrée
			char[] tmp=new char[e.length-1];
			int inc=0;
			for(int i=-1;++i<tmp.length;inc=e[i]==vm&&inc==0?0:1)
				tmp[i]=e[i+inc];
			e=tmp;

			// ajouter la valeur minimale au résultat
			tmp=new char[r.length+1];
			tmp[0]=vm;
			for(int i=-1;++i<r.length;tmp[i+1]=r[i]);
			r = tmp;
		}

		return r;
	}


	/*
	public char[] trier(char[] entree) {
		char[] resultat = new char[0];

		while(entree.length > 0) {
			char valeurMinimale = entree[0];
			for(int i = -1; ++i < entree.length;valeurMinimale = entree[i] < valeurMinimale ? entree[i] : valeurMinimale);

			char[] tmp = new char[entree.length-1];
			int incrementSiTrouve = 0;
			for(int i = 0; i < tmp.length; i++) {
				if(entree[i] == valeurMinimale && incrementSiTrouve == 0) {
					incrementSiTrouve = 1;
				}
				tmp[i] = entree[i+incrementSiTrouve];
			}
			entree = tmp;

			tmp = new char[resultat.length+1];
			tmp[0] = valeurMinimale;
			for(int i = 0; i < resultat.length; i++) {
				tmp[i+1] = resultat[i];
			}
			resultat = tmp;
		}
		
		return resultat;
	}*/

}

import java.util.Comparator;

public class ComparableManhattanProfondeur implements Comparator<MaillonManhattan> {
	/**
	 * méthode de comparaison de deux maillonManhattan par leur distance de Manhattan et leur profondeur
	 * pour l'utilisation de la file à priorité
	 */
	public int compare (MaillonManhattan taquin1 , MaillonManhattan taquin2){
		if ((taquin1.distanceManhattan+taquin1.profondeur) < (taquin2.distanceManhattan+taquin2.profondeur)){
			return -1;
		}else{
			if ((taquin1.distanceManhattan+taquin1.profondeur) > (taquin2.distanceManhattan+taquin2.profondeur)){
				return 1;
			}else{
				return 0;
			}
		}
	}
}

import java.util.Comparator;

public class ComparableManhattanProfondeur implements Comparator<MaillonManhattan> {
	/**
	 * m�thode de comparaison de deux maillonManhattan par leur distance de Manhattan et leur profondeur
	 * pour l'utilisation de la file � priorit�
	 */
	public int compare (MaillonManhattan taquin1 , MaillonManhattan taquin2){
		if ((taquin1.distanceManhattan+taquin1.mouvements.length()) < (taquin2.distanceManhattan+taquin2.mouvements.length())){
			return -1;
		}else{
			if ((taquin1.distanceManhattan+taquin1.mouvements.length()) > (taquin2.distanceManhattan+taquin2.mouvements.length())){
				return 1;
			}else{
				return 0;
			}
		}
	}
}

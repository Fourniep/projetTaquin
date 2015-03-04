import java.util.*;
public class ComparableManhattan implements Comparator<MaillonManhattan> {
	/**
	 * méthode de comparaison de deux maillonManhattan pour l'utilisation de la file à priorité
	 */
	public int compare (MaillonManhattan taquin1 , MaillonManhattan taquin2){
		if (taquin1.distanceManhattan < taquin2.distanceManhattan){
			return -1;
		}else{
			if (taquin1.distanceManhattan > taquin2.distanceManhattan){
				return 1;
			}else{
				return 0;
			}
		}
	}
}

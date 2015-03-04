import java.util.Comparator;

public class ComparableManhattanProfondeur implements Comparator<MaillonManhattanProfondeur> {
	public int compare (MaillonManhattanProfondeur taquin1 , MaillonManhattanProfondeur taquin2){
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

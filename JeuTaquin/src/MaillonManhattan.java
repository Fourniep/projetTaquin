
public class MaillonManhattan extends Maillon {
	int distanceManhattan;	//distance de Manhattan associé à la position du jeu de Taquin du maillon
	/**
	 * Constructeur de MaillonManhattan , c'est un maillon classique enrichi d'un champ distance Manhattan décrit plus haut
	 * @param taquin
	 */
	public MaillonManhattan (Taquin taquin,int profondeur){
		super(taquin,profondeur);
		distanceManhattan = taquin.distanceDeManhattan();
	}
}

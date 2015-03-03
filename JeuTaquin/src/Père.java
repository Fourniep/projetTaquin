
public class Père {
	Taquin position ;  // dans notre utilisation correspond à la position dont est issué la cle 
	// de notre HashMap
	String mouvement ; // mouvement permettant d'allé de positionPère à position clé de la HashMap
	/**
	 * Constructeur de père
	 * @param position voir déclaration de la class
	 * @param mouvement voir déclaration de la class
	 */
	public Père (Taquin position , String mouvement){
		this.position = position;
		this.mouvement = mouvement;
	}
}

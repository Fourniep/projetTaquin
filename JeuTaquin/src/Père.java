
public class P�re {
	Taquin position ;  // dans notre utilisation correspond � la position dont est issu� la cle 
	// de notre HashMap
	String mouvement ; // mouvement permettant d'all� de positionP�re � position cl� de la HashMap
	/**
	 * Constructeur de p�re
	 * @param position voir d�claration de la class
	 * @param mouvement voir d�claration de la class
	 */
	public P�re (Taquin position , String mouvement){
		this.position = position;
		this.mouvement = mouvement;
	}
	public String toString (){
		String s= ""+position+"\n"+mouvement;
		return s;
	}
}

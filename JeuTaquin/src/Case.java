
public class Case {
	int valeur;  // valeur de la case
	int position; // position de la case dans le jeu de taquin
	int hauteur; // coordonnée en hauteur de la case dans le jeu de taquin
	int largeur;  // coordonnée en largeur de la case dans le jeu de taquin
	/**
	 * Constructeur vide
	 */
	public Case (){
		
	}
	/**
	 * Constructeur de case
	 * @param valeur  voir déclaration de la class
	 * @param position   voir déclaration de la class
	 * @param hauteur  voir déclaration de la class
	 * @param largeur  voir déclaration de la class
	 */
	public Case (int valeur, int position , int hauteur, int largeur ){
		this.valeur = valeur;
		this.position = position;
		this.hauteur = hauteur;
		this.largeur = largeur;
	}
	/**
	 * méthode toString pour l'affichage
	 */
	public String toString (){
		return ""+this.valeur;
	}
	/**
	 * retourne une copie de la Case qui appelle cette méthode
	 */
	public Case clone (){
		Case clone = new Case ();
		clone.valeur = valeur;
		clone.position = position;
		clone.largeur = largeur;
		clone.hauteur =hauteur;
		return clone;
	}
	/**
	 * méthode equals qui test l'égalité de deux cases
	 */
	public boolean equals(Object autre){
		if (!(autre instanceof Case)){
			return false;
		}else{
			Case other = (Case) autre;
			if (this.valeur!= other.valeur){
				return false;
			}
			if (this.position != other.position){
				return false;
			}
			if (this.hauteur != other.hauteur){
				return false;
			} 
			if (this.largeur != other.largeur){
				return false;
			}
			return true;
		}
		
	}
}

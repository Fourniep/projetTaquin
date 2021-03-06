
public class Pile implements Atrait� {
	Maillon sommet; // dernier maillon ajout� et le prochain a �tre trait� (principe FIFO)
	/**
	 * Constructeur pile vide
	 */
	public Pile (){
		sommet = null;
	}
	/**
	 * Constructeur pile
	 * @param maillon premier maillon a empiler
	 */
	public Pile (Maillon maillon){
		sommet = maillon;
	}
	/**
	 * r�cup�re le maillon au sommet de la pile et le retire de la structure
	 */
	public  Maillon retirerMaillon (){
		if (!this.estVide()){
			Maillon aD�piler = sommet;
			sommet = sommet.suivant;
			return aD�piler;
		}else{
			return null;
		}
	}
	/**
	 * ajoute une position du jeu de taquin � sommet de la pile
	 */
	public void ajouterMaillon (Taquin taquin){
		Maillon aEmpiler = new Maillon (taquin);
		if (this.estVide()){
			sommet = aEmpiler;
		}else{
			aEmpiler.suivant = sommet;
			sommet = aEmpiler;
		}	
	}
	/**
	 * ajoute une position du jeu de taquin � la pile
	 */
	public void ajouterMaillon (Successeur successeur , String chemin){
		Maillon aEmpiler = new Maillon (successeur,chemin);
		if (this.estVide()){
			sommet = aEmpiler;
		}else{
			aEmpiler.suivant = sommet;
			sommet = aEmpiler;
		}	
	}
	/**
	 * test si la pile est vide
	 */
	public boolean estVide (){
		return (sommet==null);
	}
	/**
	 * retourne le dernier maillon ajouter sans le retirer de la structure
	 * il est logiquement notre position de victoire dans notre utilisation
	 */
	public Maillon positionVictoire () throws Exception{
		Maillon victoire = this.sommet;
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajout� a Atrait� n'est pas la position de victoire !");
		}else{
			return victoire;
		}
	}
	
}

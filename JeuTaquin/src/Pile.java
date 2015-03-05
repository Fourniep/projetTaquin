
public class Pile implements Atraité {
	Maillon sommet; // dernier maillon ajouté et le prochain a être traité (principe FIFO)
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
	 * récupére le maillon au sommet de la pile et le retire de la structure
	 */
	public  Maillon retirerMaillon (){
		if (!this.estVide()){
			Maillon aDépiler = sommet;
			sommet = sommet.suivant;
			return aDépiler;
		}else{
			return null;
		}
	}
	/**
	 * ajoute une position du jeu de taquin à sommet de la pile
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
	 * ajoute une position du jeu de taquin à la pile
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
			throw new Exception ("Dernier maillon ajouté a Atraité n'est pas la position de victoire !");
		}else{
			return victoire;
		}
	}
	
}

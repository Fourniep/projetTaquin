import java.util.*;
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
	 * test si la pile est vide
	 */
	public boolean estVide (){
		return (sommet==null);
	}
	/**
	 * méthode toString pour l'affichage
	 */
	public String toString (){
		String s = "";
		Maillon pointeur = sommet ;
		while (pointeur != null){
			s+= "\n"+pointeur ;
			pointeur = pointeur.suivant;
		}
		return s;
	}
	/**
	 * retourne le dernier maillon ajouter sans le retirer de la structure
	 */
	public Maillon dernierAjout (){
		return sommet;
	}
	
}

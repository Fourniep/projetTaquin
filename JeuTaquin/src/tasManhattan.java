import java.util.*;
public class tasManhattan implements Atrait� {
	PriorityQueue<MaillonManhattan> tas;
	/**
	 * Constructeur de tas Manhattan on remarque qu'on utilise ComparableManhattan pour prioris� les taches
	 */
	public tasManhattan (){
		Comparator<MaillonManhattan> comparator = new ComparableManhattan();
		tas = new PriorityQueue<MaillonManhattan>(comparator);
	}
	/**
	 * r�cup�re le maillon au sommet de la pile et le retire de la structure
	 */
	public Maillon retirerMaillon(){
		return tas.poll();
	}
	/**
	 * ajoute une position du jeu de taquin � sommet de la pile
	 */
	public void ajouterMaillon(Taquin taquin){
		MaillonManhattan ajouter = new MaillonManhattan (taquin);
		tas.add(ajouter);
	}
	/**
	 * test si la pile est vide
	 */
	public boolean estVide(){
		return tas.size()==0 ;
	}
	/**
	 * retourne le dernier maillon ajout� � la file sans le retirer de la structure
	 * il est logiquement la position de victoire dans notre utilisation 
	 */
	public Taquin positionVictoire() throws Exception{
		Maillon victoire =  tas.peek();
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajout� a Atrait� n'est pas la position de victoire !");
		}else{
			return victoire.position;
		}
	}
}

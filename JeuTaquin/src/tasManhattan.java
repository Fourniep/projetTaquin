import java.util.*;
public class tasManhattan implements Atraité {
	PriorityQueue<MaillonManhattan> tas;
	/**
	 * Constructeur de tas Manhattan on remarque qu'on utilise ComparableManhattan pour priorisé les taches
	 */
	public tasManhattan (){
		Comparator<MaillonManhattan> comparator = new ComparableManhattan();
		tas = new PriorityQueue<MaillonManhattan>(comparator);
	}
	/**
	 * récupére le maillon au sommet de la pile et le retire de la structure
	 */
	public Maillon retirerMaillon(){
		return tas.poll();
	}
	/**
	 * ajoute une position du jeu de taquin à la structure
	 */
	public void ajouterMaillon(Taquin taquin){
		MaillonManhattan ajouter = new MaillonManhattan (taquin);
		tas.add(ajouter);
	}
	/**
	 * ajoute une position du jeu de taquin à la structure
	 */
	public void ajouterMaillon(Successeur successeur,String chemin){
		MaillonManhattan ajouter = new MaillonManhattan (successeur,chemin);
		tas.add(ajouter);
	}
	/**
	 * test si la structure est vide
	 */
	public boolean estVide(){
		return tas.size()==0 ;
	}
	/**
	 * retourne le dernier maillon ajouté à la structure sans le retirer de la structure
	 * il est logiquement la position de victoire dans notre utilisation 
	 */
	public Maillon positionVictoire() throws Exception{
		Maillon victoire =  tas.peek();
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajouté a Atraité n'est pas la position de victoire !");
		}else{
			return victoire;
		}
	}
}

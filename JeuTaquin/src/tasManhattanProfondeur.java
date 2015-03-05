import java.util.Comparator;
import java.util.PriorityQueue;


public class tasManhattanProfondeur implements Atraité {
	PriorityQueue<MaillonManhattan> tas;
	/**
	 * Constructeur de tas Manhattan + Profondeur on remarque qu'on utilise ComparableManhattanProfondeur
	 *  pour priorisé les taches
	 */
	public tasManhattanProfondeur (){
		Comparator<MaillonManhattan> comparator = new ComparableManhattanProfondeur();
		tas = new PriorityQueue<MaillonManhattan>(comparator);
	}
	/**
	 * récupére le maillon au sommet de la pile et le retire de la structure
	 */
	public Maillon retirerMaillon(){
		return tas.poll();
	}
	/**
	 * test si la structure est vide
	 */
	public boolean estVide(){
		return tas.size()==0 ;
	}
	public Maillon maillonVictoire(){
		return tas.peek();
	}
	/**
	 * retourne le dernier maillon ajouté à la structure sans le retirer de la structure
	 * il est logiquement la position de victoire dans notre utilisation 
	 */
	public Taquin positionVictoire() throws Exception{
		Maillon victoire =  this.maillonVictoire();
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajouté a Atraité n'est pas la position de victoire !");
		}else{
			return victoire.position;
		}
	}
	/**
	 * ajoute une position du jeu de taquin à sommet de la pile
	 */
	public void ajouterMaillon(Taquin taquin,int profondeur){
		MaillonManhattan ajouter = new MaillonManhattan (taquin,profondeur);
		tas.add(ajouter);
	}
}

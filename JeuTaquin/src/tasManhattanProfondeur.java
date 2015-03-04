import java.util.Comparator;
import java.util.PriorityQueue;


public class tasManhattanProfondeur implements Atraité {
	PriorityQueue<MaillonManhattanProfondeur> tas;
	public tasManhattanProfondeur (){
		Comparator<MaillonManhattanProfondeur> comparator = new ComparableManhattanProfondeur();
		tas = new PriorityQueue<MaillonManhattanProfondeur>(comparator);
	}
	public Maillon retirerMaillon(){
		return tas.poll();
	}
	public boolean estVide(){
		return tas.size()==0 ;
	}
	public Taquin positionVictoire() throws Exception{
		Maillon victoire =  tas.peek();
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajouté a Atraité n'est pas la position de victoire !");
		}else{
			return victoire.position;
		}
	}
	// implémentation de la méthode ajouter Maillon non possible (lié au problème du constructeur)
}

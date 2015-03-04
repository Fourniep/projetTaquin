import java.util.Comparator;
import java.util.PriorityQueue;


public class tasManhattanProfondeur implements Atrait� {
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
			throw new Exception ("Dernier maillon ajout� a Atrait� n'est pas la position de victoire !");
		}else{
			return victoire.position;
		}
	}
	// impl�mentation de la m�thode ajouter Maillon non possible (li� au probl�me du constructeur)
}

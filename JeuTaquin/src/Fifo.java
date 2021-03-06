
public class Fifo implements Atrait� {
	Maillon t�te ; // t�te de file le prochain a sortir (principe FIFO)
	Maillon queue ; // dernier maillon ajout� � la file
	/**
	 * Constructeur vide
	 */
	public Fifo (){
		t�te = null;
		queue = null;
	}
	/**
	 * test si la file est vide
	 */
	public boolean estVide(){
		return (t�te==null) ;
	}
	/**
	 * ajoute un maillon � la file
	 */
	public void ajouterMaillon(Taquin taquin){
		Maillon aEnfiler = new Maillon (taquin);
		if (this.estVide()){
			t�te = aEnfiler;
			queue = aEnfiler;
		}else{
			aEnfiler.suivant= queue;
			queue = aEnfiler;
		}
	}
	/**
	 * ajoute un maillon a la file
	 */
	public void ajouterMaillon (Successeur successeur , String chemin){
		Maillon aEnfiler = new Maillon (successeur , chemin);
		if (this.estVide()){
			t�te = aEnfiler;
			queue = aEnfiler;
		}else{
			aEnfiler.suivant= queue;
			queue = aEnfiler;
		}
	}
	/**
	 * recure le maillon de t�te � la file et le retire de la structure
	 */
	public Maillon retirerMaillon(){
		if(this.estVide()){
			return null;
		}else{
			if (queue == t�te){
				Maillon defile = t�te ;
				t�te = null;
				queue = null;
				return defile;
			}else{
				Maillon pointeur= queue;
				while ((pointeur.suivant!= null)&&(pointeur.suivant!=t�te)){
					pointeur = pointeur.suivant;
				}
				Maillon defile = pointeur.suivant;
				pointeur.suivant = null;
				t�te = pointeur;
				return defile;
			}	
			
		}
	}
	/**
	 * retourne le dernier maillon ajout� � la structure sans le retirer de la structure
	 * il est logiquement la position de victoire dans notre utilisation 
	 */
	public Maillon positionVictoire () throws Exception{
		Maillon victoire = this.queue ;
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajout� a Atrait� n'est pas la position de victoire !");
		}else{
			return victoire;
		}
	}
}

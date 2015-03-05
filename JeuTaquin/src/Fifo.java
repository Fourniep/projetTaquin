
public class Fifo implements Atraité {
	Maillon tête ; // tête de file le prochain a sortir (principe FIFO)
	Maillon queue ; // dernier maillon ajouté à la file
	/**
	 * Constructeur vide
	 */
	public Fifo (){
		tête = null;
		queue = null;
	}
	/**
	 * test si la file est vide
	 */
	public boolean estVide(){
		return (tête==null) ;
	}
	/**
	 * ajoute un maillon à la file
	 */
	public void ajouterMaillon(Taquin taquin){
		Maillon aEnfiler = new Maillon (taquin);
		if (this.estVide()){
			tête = aEnfiler;
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
			tête = aEnfiler;
			queue = aEnfiler;
		}else{
			aEnfiler.suivant= queue;
			queue = aEnfiler;
		}
	}
	/**
	 * recure le maillon de tête à la file et le retire de la structure
	 */
	public Maillon retirerMaillon(){
		if(this.estVide()){
			return null;
		}else{
			if (queue == tête){
				Maillon defile = tête ;
				tête = null;
				queue = null;
				return defile;
			}else{
				Maillon pointeur= queue;
				while ((pointeur.suivant!= null)&&(pointeur.suivant!=tête)){
					pointeur = pointeur.suivant;
				}
				Maillon defile = pointeur.suivant;
				pointeur.suivant = null;
				tête = pointeur;
				return defile;
			}	
			
		}
	}
	/**
	 * retourne le dernier maillon ajouté à la structure sans le retirer de la structure
	 * il est logiquement la position de victoire dans notre utilisation 
	 */
	public Maillon positionVictoire () throws Exception{
		Maillon victoire = this.queue ;
		if (!victoire.position.testVictoire()){
			throw new Exception ("Dernier maillon ajouté a Atraité n'est pas la position de victoire !");
		}else{
			return victoire;
		}
	}
}

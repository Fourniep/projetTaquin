
public class Maillon {
	Maillon suivant ;
	Taquin position ;
	String mouvements;
	public Maillon (Taquin position){
		this.position = position ;
		this.mouvements ="";
	}
	public Maillon (Successeur successeur , String chemin){
		position = successeur.position;
		mouvements = chemin+successeur.mouvement.charAt(0);
	}
}


public interface Atraité {
	public Maillon retirerMaillon ();  // récupérer le maillon accessible selon la structure de donnée
	public void ajouterMaillon(Taquin taquin); // ajoute un maillon de position Taquin a la structure de donnée
	public void ajouterMaillon(Successeur successeur , String chemin); // ajoute un maillon a la structure
	public boolean estVide(); // test si la structure de donnée est vide
	public Maillon positionVictoire() throws Exception; // récupére la position de victoire sans la retiré
}

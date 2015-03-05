
public interface Atraité {
	public Maillon retirerMaillon ();  // récupérer le maillon accessible selon la structure de donnée
	public void ajouterMaillon(Taquin taquin,int profondeur); // ajoute un maillon de position Taquin a la structure de donnée
	public boolean estVide(); // test si la structure de donnée est vide
	public Taquin positionVictoire() throws Exception; // récupére la position de victoire sans la retiré
	public Maillon maillonVictoire() throws Exception;
}

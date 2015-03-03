
public interface Atraité {
	public Maillon retirerMaillon ();  // récupérer le maillon accessible selon la structure de donnée
	public void ajouterMaillon(Taquin taquin); // ajoute un maillon de position Taquin a la structure de donnée
	public boolean estVide(); // test si la structure de donnée est vide
	public Maillon dernierAjout(); // récupére le derniere Maillon ajouté à la structure
}

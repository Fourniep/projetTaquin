
public interface Atrait� {
	public Maillon retirerMaillon ();  // r�cup�rer le maillon accessible selon la structure de donn�e
	public void ajouterMaillon(Taquin taquin); // ajoute un maillon de position Taquin a la structure de donn�e
	public boolean estVide(); // test si la structure de donn�e est vide
	public Maillon dernierAjout(); // r�cup�re le derniere Maillon ajout� � la structure
}

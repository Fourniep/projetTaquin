import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,P�re> Marqu�; // double fonction ! : 1) test si une position a d�j� �t� explor�
	// 2) joue le r�le de tableau des p�re
	Atrait� atrait�; // structure qui g�re les maillon en attente de traitement
	long stats ;
	/**
	 * Constructeur de graphe
	 * @param initial la position de d�part du jeu de taquin donn� par le fichier
	 * @param delai temps � partir du quel l'�x�cution de la m�thode doit �tre interrompu
	 * @param algo choix de la structure de donn�e pour atrait�
	 */
	public Graphe (Taquin initial,int delai , String algo) throws Exception {
		switch (algo){
		case "pile":
			atrait� = new Pile();
			break;
		case "file":
			atrait� = new Fifo();
			break;
		case "manhattan":
			atrait� = new tasManhattan();
			break;
		case "pmanhattan":
			atrait� = new tasManhattanProfondeur();
		}
		 long temps; 
         long start= System.currentTimeMillis();
        Marqu� = new HashMap<Taquin,P�re>();
		Marqu�.put(initial,null);
		atrait�.ajouterMaillon(initial);
		boolean solution = false;
		while ((!atrait�.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 System.exit(0);// stoppe l'�x�cution du programme si le temps  d'�x�cution devient sup�rieur au d�lai impos�
			 }
			Taquin enTraitement = atrait�.retirerMaillon().position;
			ArrayList<Successeur> successeurs = enTraitement.positionsSuivantes();
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				if (!Marqu�.containsKey(successeurEnTraitement.position)){
					Marqu�.put(successeurEnTraitement.position, new P�re (enTraitement , successeurEnTraitement.mouvement ));
					atrait�.ajouterMaillon(successeurEnTraitement.position);
					solution = successeurEnTraitement.position.testVictoire();
				}
			}
		}
		stats = System.currentTimeMillis()-start;
		if (!solution){
			throw new Exception ("Pas de solution trouv�");
		}
	}
	public void afficherSolution ()throws Exception{
		Pile r�solution = new Pile();
		r�solution.ajouterMaillon(this.atrait�.positionVictoire());
		while (Marqu�.get(r�solution.sommet.position)!=null){
			r�solution.ajouterMaillon(Marqu�.get(r�solution.sommet.position).position);
		}
		while (!r�solution.estVide()){
			Maillon enTraitement = r�solution.retirerMaillon();
			if (Marqu�.get(enTraitement.position)!=null){
				System.out.println(Marqu�.get(enTraitement.position).mouvement);
			}
			System.out.println(enTraitement.position);
		}
	}
	/*/**
	 * en partant d'un graphe qui a trouv� la position de solution retourne une pile contenant
	 * les positions succesives permettant d'y parvenir
	 * @return pile contenant les �tapes permettant de parvenir � la solution
	 */
	/*public Pile Solution ()throws Exception{
		Pile solution = new Pile ();
		Taquin next = atrait�.positionVictoire().position;
		solution.ajouterMaillon(next);
		while (Marqu�.get(next)!= null){
			next = Marqu�.get(next).position;
			solution.ajouterMaillon(next);
		}
		return solution;
	}*/
}

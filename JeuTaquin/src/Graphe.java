import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,P�re> Marqu�; // double fonction ! : 1) test si une position a d�j� �t� explor�
	// 2) joue le r�le de tableau des p�re
	Atrait� atrait�; // structure qui g�re les maillon en attente de traitement
	/**
	 * Constructeur de graphe
	 * @param initial la position de d�part du jeu de taquin donn� par le fichier
	 * @param delai temps � partir du quel l'�x�cution de la m�thode doit �tre interrompu
	 * @param algo choix de la structure de donn�e pour atrait�
	 */
	public Graphe (Taquin initial,int delai , String algo) {
		switch (algo){
		case "pile":
			atrait� = new Pile();
			break;
		case "file":
			atrait� = new Fifo();
			break;
		}
		 long temps; 
         long start= System.currentTimeMillis();
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
		if (!solution){
			System.out.println("Pas de solution");
		}else{
			Pile r�solution = this.Solution();
		}
	}
	/**
	 * en partant d'un graphe qui a trouv� la position de solution retourne une pile contenant
	 * les positions succesives permettant d'y parvenir
	 * @return pile contenant les �tapes permettant de parvenir � la solution
	 */
	public Pile Solution (){
		Pile solution = new Pile ();
		Taquin next = atrait�.dernierAjout().position;
		solution.ajouterMaillon(next);
		while (Marqu�.get(next)!= null){
			next = Marqu�.get(next).position;
			solution.ajouterMaillon(next);
		}
		return solution;
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,Père> Marqué; // double fonction ! : 1) test si une position a déjà été exploré
	// 2) joue le rôle de tableau des père
	Atraité atraité; // structure qui gère les maillon en attente de traitement
	/**
	 * Constructeur de graphe
	 * @param initial la position de départ du jeu de taquin donné par le fichier
	 * @param delai temps à partir du quel l'éxécution de la méthode doit être interrompu
	 * @param algo choix de la structure de donnée pour atraité
	 */
	public Graphe (Taquin initial,int delai , String algo) {
		switch (algo){
		case "pile":
			atraité = new Pile();
			break;
		case "file":
			atraité = new Fifo();
			break;
		}
		 long temps; 
         long start= System.currentTimeMillis();
		Marqué.put(initial,null);
		atraité.ajouterMaillon(initial);
		boolean solution = false;
		while ((!atraité.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 System.exit(0);// stoppe l'éxécution du programme si le temps  d'éxécution devient supérieur au délai imposé
			 }
			Taquin enTraitement = atraité.retirerMaillon().position;
			ArrayList<Successeur> successeurs = enTraitement.positionsSuivantes();
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				if (!Marqué.containsKey(successeurEnTraitement.position)){
					Marqué.put(successeurEnTraitement.position, new Père (enTraitement , successeurEnTraitement.mouvement ));
					atraité.ajouterMaillon(successeurEnTraitement.position);
					solution = successeurEnTraitement.position.testVictoire();
				}
			}
		}
		if (!solution){
			System.out.println("Pas de solution");
		}else{
			Pile résolution = this.Solution();
		}
	}
	/**
	 * en partant d'un graphe qui a trouvé la position de solution retourne une pile contenant
	 * les positions succesives permettant d'y parvenir
	 * @return pile contenant les étapes permettant de parvenir à la solution
	 */
	public Pile Solution (){
		Pile solution = new Pile ();
		Taquin next = atraité.dernierAjout().position;
		solution.ajouterMaillon(next);
		while (Marqué.get(next)!= null){
			next = Marqué.get(next).position;
			solution.ajouterMaillon(next);
		}
		return solution;
	}
}

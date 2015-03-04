import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,Père> Marqué; // double fonction ! : 1) test si une position a déjà été exploré
	// 2) joue le rôle de tableau des père
	Atraité atraité; // structure qui gère les maillon en attente de traitement
	long stats ;
	/**
	 * Constructeur de graphe
	 * @param initial la position de départ du jeu de taquin donné par le fichier
	 * @param delai temps à partir du quel l'éxécution de la méthode doit être interrompu
	 * @param algo choix de la structure de donnée pour atraité
	 */
	public Graphe (Taquin initial,int delai , String algo) throws Exception {
		switch (algo){
		case "pile":
			atraité = new Pile();
			break;
		case "file":
			atraité = new Fifo();
			break;
		case "manhattan":
			atraité = new tasManhattan();
			break;
		case "pmanhattan":
			atraité = new tasManhattanProfondeur();
		}
		 long temps; 
         long start= System.currentTimeMillis();
        Marqué = new HashMap<Taquin,Père>();
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
		stats = System.currentTimeMillis()-start;
		if (!solution){
			throw new Exception ("Pas de solution trouvé");
		}
	}
	public void afficherSolution ()throws Exception{
		Pile résolution = new Pile();
		résolution.ajouterMaillon(this.atraité.positionVictoire());
		while (Marqué.get(résolution.sommet.position)!=null){
			résolution.ajouterMaillon(Marqué.get(résolution.sommet.position).position);
		}
		while (!résolution.estVide()){
			Maillon enTraitement = résolution.retirerMaillon();
			if (Marqué.get(enTraitement.position)!=null){
				System.out.println(Marqué.get(enTraitement.position).mouvement);
			}
			System.out.println(enTraitement.position);
		}
	}
	/*/**
	 * en partant d'un graphe qui a trouvé la position de solution retourne une pile contenant
	 * les positions succesives permettant d'y parvenir
	 * @return pile contenant les étapes permettant de parvenir à la solution
	 */
	/*public Pile Solution ()throws Exception{
		Pile solution = new Pile ();
		Taquin next = atraité.positionVictoire().position;
		solution.ajouterMaillon(next);
		while (Marqué.get(next)!= null){
			next = Marqué.get(next).position;
			solution.ajouterMaillon(next);
		}
		return solution;
	}*/
}

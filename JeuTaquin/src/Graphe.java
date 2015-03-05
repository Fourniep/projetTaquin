import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,Père> Marqué; // double fonction ! : 1) test si une position a déjà été exploré
	// 2) joue le rôle de tableau des père
	Atraité atraité; // structure qui gère les maillon en attente de traitement
	long tempsExecution ; // temps d'éxécution pour le parcourt 
	int nbPositionsParcourus ; // nbDePositionsParcouru lors du parcourt
	int nbCoups ; // nbCoups joué pour arrivé a la solution 
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
			break;
		default:
			throw new Exception ("Structure pour Atraité non indentifié");
		}
		 long temps; 
         long start= System.currentTimeMillis();
        Marqué = new HashMap<Taquin,Père>();
		Marqué.put(initial,null);
		atraité.ajouterMaillon(initial,0);
		boolean solution = false;
		while ((!atraité.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 throw new DelaiExecutionException("Délai d'éxécution dépassé");// stoppe l'éxécution du programme si le temps  d'éxécution devient supérieur au délai imposé
			 }
			Maillon ET = atraité.retirerMaillon();
			Taquin enTraitement = ET.position;
			ArrayList<Successeur> successeurs = enTraitement.positionsSuivantes();
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				if (!Marqué.containsKey(successeurEnTraitement.position)){
					Marqué.put(successeurEnTraitement.position, new Père (enTraitement , successeurEnTraitement.mouvement ));
					atraité.ajouterMaillon(successeurEnTraitement.position,ET.profondeur+1);
					solution = successeurEnTraitement.position.testVictoire();
				}
			}
		}
		nbPositionsParcourus = Marqué.size();
		tempsExecution = System.currentTimeMillis()-start;
		
		nbCoups = atraité.maillonVictoire().profondeur;
		if (!solution){
			throw new Exception ("Pas de solution trouvé");
		}
	}
	/**
	 * récupère la solution sous forme d'ArrayList
	 * @throws Exception
	 */
	public ArrayList<Taquin> récupererSolution () throws Exception{
		ArrayList<Taquin> résolution = new ArrayList<Taquin>();
		Taquin enTraitement = this.atraité.positionVictoire();
		résolution.add(0,enTraitement);
		Père pèreNoeudEnTraitement = Marqué.get(enTraitement);
		while (pèreNoeudEnTraitement!=null){
			résolution.add(0,pèreNoeudEnTraitement.position);
			enTraitement = résolution.get(0);
			pèreNoeudEnTraitement = Marqué.get(enTraitement);
		}
		return résolution;
		
	}
	public void afficherSolution () throws Exception{
		ArrayList<Taquin> résolution = this.récupererSolution();
		for (int i=0;i<résolution.size() ; i++){
			Taquin aAfficher = résolution.get(i);
			if (Marqué.get(aAfficher)!=null){
				System.out.println(Marqué.get(aAfficher).mouvement);
			}
			System.out.println(aAfficher);
		}
	}	
	
}

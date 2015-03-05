import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,ArrayList<Successeur>> Marqué; // double fonction ! : 1) test si une position a déjà été exploré
	// 2) joue le rôle de tableau des père
	Atraité atraité; // structure qui gère les maillon en attente de traitement
	long tempsExecution ; // temps d'éxécution pour le parcourt 
	int nbPositionsParcourus ; // nbDePositionsParcouru lors du parcourt
	int nbCoups ; // nbCoups joué pour arrivé a la solution 
	Taquin initial; // position depuis laquel on a lancé le jeu de taquin
	/**
	 * Constructeur de graphe
	 * @param initial la position de départ du jeu de taquin donné par le fichier
	 * @param delai temps à partir du quel l'éxécution de la méthode doit être interrompu
	 * @param algo choix de la structure de donnée pour atraité
	 */
	public Graphe (Taquin initial,int delai , String algo) throws Exception {
		this.initial=initial;
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
        Marqué = new HashMap<Taquin,ArrayList<Successeur>>();
		Marqué.put(initial,initial.positionsSuivantes());
		atraité.ajouterMaillon(initial);
		boolean solution = false;
		while ((!atraité.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 throw new DelaiExecutionException("Délai d'éxécution dépassé");// stoppe l'éxécution du programme si le temps  d'éxécution devient supérieur au délai imposé
			 }
			Maillon enTraitement = atraité.retirerMaillon();
			//Taquin enTraitement = ET.position;
			ArrayList<Successeur> successeurs = Marqué.get(enTraitement.position);
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				Taquin     positionEnTraitement = successeurEnTraitement.position;
				if (!Marqué.containsKey(successeurEnTraitement.position)){
					Marqué.put(positionEnTraitement,positionEnTraitement.positionsSuivantes() );
					atraité.ajouterMaillon(successeurEnTraitement,enTraitement.mouvements);
					solution = positionEnTraitement.testVictoire();
				}
			}
		}
		nbPositionsParcourus = Marqué.size();
		tempsExecution = System.currentTimeMillis()-start;
		nbCoups = atraité.positionVictoire().mouvements.length();
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
		String chemin = this.atraité.positionVictoire().mouvements;
		Taquin parcourt = initial.clone();
		résolution.add(parcourt);
		for(int i=0 ; i<chemin.length(); i++){
			parcourt = parcourt.clone();
			parcourt.permutation(chemin.charAt(i));
			résolution.add(parcourt);
		}
		return résolution;
			
	}
	/**
	 * affiche la solution 
	 * @throws Exception
	 */
	public void afficherSolution () throws Exception{
		ArrayList<Taquin> résolution = this.récupererSolution();
		for (int i=0;i<résolution.size() ; i++){
			System.out.println(résolution.get(i));
		}
	}	
	
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,ArrayList<Successeur>> Marqu�; // double fonction ! : 1) test si une position a d�j� �t� explor�
	// 2) joue le r�le de tableau des p�re
	Atrait� atrait�; // structure qui g�re les maillon en attente de traitement
	long tempsExecution ; // temps d'�x�cution pour le parcourt 
	int nbPositionsParcourus ; // nbDePositionsParcouru lors du parcourt
	int nbCoups ; // nbCoups jou� pour arriv� a la solution 
	Taquin initial; // position depuis laquel on a lanc� le jeu de taquin
	/**
	 * Constructeur de graphe
	 * @param initial la position de d�part du jeu de taquin donn� par le fichier
	 * @param delai temps � partir du quel l'�x�cution de la m�thode doit �tre interrompu
	 * @param algo choix de la structure de donn�e pour atrait�
	 */
	public Graphe (Taquin initial,int delai , String algo) throws Exception {
		this.initial=initial;
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
			break;
		default:
			throw new Exception ("Structure pour Atrait� non indentifi�");
		}
		 long temps; 
         long start= System.currentTimeMillis();
        Marqu� = new HashMap<Taquin,ArrayList<Successeur>>();
		Marqu�.put(initial,initial.positionsSuivantes());
		atrait�.ajouterMaillon(initial);
		boolean solution = false;
		while ((!atrait�.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 throw new DelaiExecutionException("D�lai d'�x�cution d�pass�");// stoppe l'�x�cution du programme si le temps  d'�x�cution devient sup�rieur au d�lai impos�
			 }
			Maillon enTraitement = atrait�.retirerMaillon();
			//Taquin enTraitement = ET.position;
			ArrayList<Successeur> successeurs = Marqu�.get(enTraitement.position);
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				Taquin     positionEnTraitement = successeurEnTraitement.position;
				if (!Marqu�.containsKey(successeurEnTraitement.position)){
					Marqu�.put(positionEnTraitement,positionEnTraitement.positionsSuivantes() );
					atrait�.ajouterMaillon(successeurEnTraitement,enTraitement.mouvements);
					solution = positionEnTraitement.testVictoire();
				}
			}
		}
		nbPositionsParcourus = Marqu�.size();
		tempsExecution = System.currentTimeMillis()-start;
		nbCoups = atrait�.positionVictoire().mouvements.length();
		if (!solution){
			throw new Exception ("Pas de solution trouv�");
		}
	}
	/**
	 * r�cup�re la solution sous forme d'ArrayList
	 * @throws Exception
	 */
	public ArrayList<Taquin> r�cupererSolution () throws Exception{
		ArrayList<Taquin> r�solution = new ArrayList<Taquin>();
		String chemin = this.atrait�.positionVictoire().mouvements;
		Taquin parcourt = initial.clone();
		r�solution.add(parcourt);
		for(int i=0 ; i<chemin.length(); i++){
			parcourt = parcourt.clone();
			parcourt.permutation(chemin.charAt(i));
			r�solution.add(parcourt);
		}
		return r�solution;
			
	}
	/**
	 * affiche la solution 
	 * @throws Exception
	 */
	public void afficherSolution () throws Exception{
		ArrayList<Taquin> r�solution = this.r�cupererSolution();
		for (int i=0;i<r�solution.size() ; i++){
			System.out.println(r�solution.get(i));
		}
	}	
	
}

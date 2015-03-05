import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Graphe {
	HashMap<Taquin,P�re> Marqu�; // double fonction ! : 1) test si une position a d�j� �t� explor�
	// 2) joue le r�le de tableau des p�re
	Atrait� atrait�; // structure qui g�re les maillon en attente de traitement
	long tempsExecution ; // temps d'�x�cution pour le parcourt 
	int nbPositionsParcourus ; // nbDePositionsParcouru lors du parcourt
	int nbCoups ; // nbCoups jou� pour arriv� a la solution 
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
			break;
		default:
			throw new Exception ("Structure pour Atrait� non indentifi�");
		}
		 long temps; 
         long start= System.currentTimeMillis();
        Marqu� = new HashMap<Taquin,P�re>();
		Marqu�.put(initial,null);
		atrait�.ajouterMaillon(initial,0);
		boolean solution = false;
		while ((!atrait�.estVide())&&(!solution)){
			temps= System.currentTimeMillis()-start;
			 if(temps>=delai){ 
                 throw new DelaiExecutionException("D�lai d'�x�cution d�pass�");// stoppe l'�x�cution du programme si le temps  d'�x�cution devient sup�rieur au d�lai impos�
			 }
			Maillon ET = atrait�.retirerMaillon();
			Taquin enTraitement = ET.position;
			ArrayList<Successeur> successeurs = enTraitement.positionsSuivantes();
			Iterator<Successeur> it = successeurs.iterator();
			while ((it.hasNext())&&(!solution)){
				Successeur successeurEnTraitement = it.next();
				if (!Marqu�.containsKey(successeurEnTraitement.position)){
					Marqu�.put(successeurEnTraitement.position, new P�re (enTraitement , successeurEnTraitement.mouvement ));
					atrait�.ajouterMaillon(successeurEnTraitement.position,ET.profondeur+1);
					solution = successeurEnTraitement.position.testVictoire();
				}
			}
		}
		nbPositionsParcourus = Marqu�.size();
		tempsExecution = System.currentTimeMillis()-start;
		
		nbCoups = atrait�.maillonVictoire().profondeur;
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
		Taquin enTraitement = this.atrait�.positionVictoire();
		r�solution.add(0,enTraitement);
		P�re p�reNoeudEnTraitement = Marqu�.get(enTraitement);
		while (p�reNoeudEnTraitement!=null){
			r�solution.add(0,p�reNoeudEnTraitement.position);
			enTraitement = r�solution.get(0);
			p�reNoeudEnTraitement = Marqu�.get(enTraitement);
		}
		return r�solution;
		
	}
	public void afficherSolution () throws Exception{
		ArrayList<Taquin> r�solution = this.r�cupererSolution();
		for (int i=0;i<r�solution.size() ; i++){
			Taquin aAfficher = r�solution.get(i);
			if (Marqu�.get(aAfficher)!=null){
				System.out.println(Marqu�.get(aAfficher).mouvement);
			}
			System.out.println(aAfficher);
		}
	}	
	
}

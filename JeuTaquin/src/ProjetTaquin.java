import java.io.IOException;
import java.util.*;
public class ProjetTaquin {
	
	/**
	 * méthode appeler pour trouver une solution au jeu de taquin
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @param delai delai au bout du quel on stop l'éxécution de la méthode
	 * @param algo structure de la donnée utilisé pour la résolution
	 * @throws Exception 
	 */
	public static Graphe ParcourtDuGraphe (String delai , String algo , String fichier) throws NonSolvableException , IOException , Exception{
		Taquin taquin = new Taquin (fichier);
			if (!taquin.solvable()){
				throw new NonSolvableException ("Pas de solution !");
			}else{
				int delaiConverti = Integer.parseInt(delai);
				Graphe  graphe = new Graphe (taquin,delaiConverti,algo);
				return graphe;
			}	
		
	}
	/**
	 * affiche la solution 
	 * @param g graphe parcouru
	 * @throws Exception
	 */
	public static void Solution (Graphe g) throws Exception{
		g.afficherSolution();
	}
	/**
	 * récupere les stats du graphe passé en paramètre (le graphe est déjà parcouru)  
	 * @param g graphe parcouru
	 */
	public static String récupérerStats (Graphe g){
		String stats = g.nbCoups+" "+g.nbPositionsParcourus+" "+g.tempsExecution+" ms";
		return stats;
	}
	/**
	 * afficher les stats du graphe parcouru 
	 * @param g le graphe parcouru 
	 */
	public static void afficherStats (Graphe g){
		String stats= récupérerStats(g);
		Scanner sc = new Scanner (stats);
		System.out.println("Nombres de coups pour atteindre la solution : "+sc.next());
		System.out.println("Nombres de positions parcouru lors de la recherche de solution : "+sc.next());
		System.out.println("Temps d'éxécution en ms : "+sc.next());
		sc.close();
		
	}
	public static void afficherStatsHTML (String[][] stats){
		
	}
	/**
	 * méthode qui test la solvabilité du jeu de taquin créé a partir du fichier donnée
	 * en paramètre
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @return vrai si le jeu est solvable / faux sinon
	 * @throws IOException pour s'assurer de la bonne lecture du fichier
	 */
	public static boolean Solvable (String fichier) throws IOException {
		Taquin taquin = new Taquin (fichier);
		return taquin.solvable();
	}
	/**
	 * méthode permettant de jouer au jeu de taquin contenu dans le fichier via le terminal
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @throws IOException pour s'assurer de la bonne lecture du fichier
	 * @throws NonSolvableException pour s'assurer que le jeu de taquin proposé admet
	 * une solution
	 */
	public static void Jouer (String fichier) throws IOException , NonSolvableException  {
		Taquin taquin = new Taquin (fichier);
		System.out.println(taquin);
		int compteurDeCoup = 0 ;
		boolean abandon = false;
		boolean victoire = taquin.testVictoire();
		while ((!victoire)&&(!abandon)){
			System.out.println("Quel case voulez vous déplacer sur la case vide ?");
			String choixCase = taquin.choixDeplacement();
			if (choixCase.equals("Abandonner")){
				abandon = true;
			}else{
				taquin.permutation(choixCase);
				compteurDeCoup++;
				System.out.println (taquin);
				if (taquin.vide.position == 0 ){    // testVictoire() étant une méthode assez gourmande on limite son utilisation ainsi
					victoire = taquin.testVictoire();
				}
			}	
		}
		if (taquin.testVictoire()){
			System.out.println("Bravo vous avez gagné !");
			System.out.println("Nombre de coup : "+compteurDeCoup);
		}else{
			System.out.println("Partie terminé vous avez abandonné");
		}
	}
	public static void main (String [] args){
		ArrayList<String> algo = new ArrayList<String> ();
		algo.add("pile");
		algo.add("file");
		algo.add("manhattan");
		algo.add("pmanhattan");
		switch (args[0]){
		case ("-name"):
			System.out.println("Ferre Donovan");
			System.out.println("Fournier Paul");
			System.out.println("Label Louis ");
			break;
		case ("-h"):
			System.out.println("-h : affiche une aide interactive");
			System.out.println("-name : affiche les noms des concepteurs du programme");
			System.out.println("-sol 'fichier.taq' -j : retourne true si le jeu de taquin "
					+ "du fichier 'fichier.taq' est solvable false sinon");
			System.out.println("-joue 'fichier.taq' : permet de jouer au jeu de taquin "
					+ "avec les données du fichier 'fichier.taq'");
			System.out.println("-cal 'delai' 'algo' 'fichier.taq' : donne une solution au jeu "
					+ "de taquin donné en paramètre par 'fichier.taq' en utilisant l'algo 'algo"
					+ " l'éxécution du programme s'arrète si on dépasse le délai 'délai'");
			break;
		case ("-sol"):
			try {
				Solvable (args[1]);
			}catch (Exception ioe){
				System.out.println(ioe);
			}
			break;
		case ("-joue"):
			try {
				Jouer (args[1]);
			}catch (Exception e){
				System.out.println(e);
			}
			break;
		case ("-cal"):
			try {
				Graphe g = ParcourtDuGraphe (args[1],args[2],args[3]);
				Solution (g);
			}catch (Exception e){
				System.out.println (e);
			}
			break;
		case ("-stat"):
			if (algo.contains(args[2])){
				try {
					Graphe g = ParcourtDuGraphe (args[1],args[2],args[3]);
					afficherStats(g);
				}catch (Exception e){
					System.out.println (e);
				}
			}else{
				try {
					String[][] tableauxStats = new String[algo.size()][2];
					Iterator<String>it = algo.iterator();
					int positionTableaux = 0;
					while (it.hasNext()){
						String algoAjouté = it.next();
						tableauxStats[positionTableaux][0]= algoAjouté;
						Graphe algoEnTraitement = ParcourtDuGraphe (args[1],algoAjouté,args[2]);
						tableauxStats[positionTableaux][1]= récupérerStats(algoEnTraitement);
						positionTableaux ++;
					}
					afficherStatsHTML(tableauxStats);
				}catch (Exception e){
					System.out.println (e);
				}
				
				
			}
			break;	
		}

	}
}

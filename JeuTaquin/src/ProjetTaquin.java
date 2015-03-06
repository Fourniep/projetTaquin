import java.io.*;
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
	public static void afficherSolutionHTML (Graphe g)throws Exception{
		ArrayList<Taquin>solution = g.récupererSolution();
		File f = new File ("TaquinDeplacement.php");
		f.delete();
		FileWriter fw = new FileWriter(f);
		fw.write("<?php \n");
		fw.write("$hauteur="+g.initial.hauteur+";\n");
		fw.write("$largeur="+g.initial.largeur+";\n");
		fw.write("$array=array(");
		for(int i=0; i<solution.size();i++){
			if(i==solution.size()-1){
				fw.write("'"+solution.get(i)+"',");
			} else {
				fw.write("'"+solution.get(i)+"'");
			}
			}
		fw.write(");\n?>");
		fw.close();
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
	/**
	 * remplit le fichier HTML contenant les stats
	 * @param stats fichiers contenant les info a reporter dans le fichier
	 * @throws IOException assure que l'écriture du fichier se fait correctement
	 */
	public static void afficherStatsHTML (String[][] stats)throws IOException{
		File f = new File ("Stats.php");
		f.delete();
		FileWriter fw = new FileWriter(f);
		fw.write("<?php $pile=true;$file=true;$manhattan=true;$pmanhattan=true;");
		Scanner sc;
		int temps=0;
		for(int i=0; i<stats.length;i++){
			sc = new Scanner(stats[i][1]);
			if(stats[i][1].equals("Délai d'éxécution dépassé")){
				fw.write("$Taille"+i+"=null;");
				fw.write("$Nombre"+i+"=null;");
				fw.write("$H"+i+"=null;");
				fw.write("$M"+i+"=null;");
				fw.write("$S"+i+"=null;");
				fw.write("$MS"+i+"=null;");
				
			}else{
				fw.write("$Taille"+i+"="+sc.next()+";");
				fw.write("$Nombre"+i+"="+sc.next()+";");
				temps=Integer.parseInt(sc.next());
				int ms = temps%1000;
				temps = temps/1000;
				int s = temps%60;
				temps = temps/60;
				int m = temps%60;
				int h = temps/60;
				fw.write("$H"+i+"="+h+";");
				temps=temps/1000;
				fw.write("$M"+i+"="+m+";");
				temps=temps/60;
				fw.write("$S"+i+"="+s+";");
				temps=temps/60;
				fw.write("$MS"+i+"="+ms+";");
			}
			sc.close();
		}
		fw.write("?>");
		fw.close();
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
		ArrayList<String> algo = new ArrayList<String> (); // on ajoute nos algo ici
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
						try {	
							Graphe algoEnTraitement = ParcourtDuGraphe (args[1],algoAjouté,args[2]);
							tableauxStats[positionTableaux][1]= récupérerStats(algoEnTraitement);
						}catch(DelaiExecutionException dee){
							tableauxStats[positionTableaux][1] = ""+dee;
						}
						positionTableaux ++;
					}
					afficherStatsHTML(tableauxStats);
				}catch (Exception e){
					System.out.println (e);
				}
				
				
			}
			break;	
		case ("-anime"):
			if (algo.contains(args[2])){
				try{
					Graphe g = ParcourtDuGraphe (args[1],args[2],args[3]);
					afficherSolutionHTML(g);
				}catch(Exception e){
					System.out.println(e);
				}
			}
		}

	}
}

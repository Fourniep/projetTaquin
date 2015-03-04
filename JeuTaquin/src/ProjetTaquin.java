import java.io.IOException;
import java.util.*;
public class ProjetTaquin {
	
	/**
	 * m�thode appeler pour trouver une solution au jeu de taquin
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @param delai delai au bout du quel on stop l'�x�cution de la m�thode
	 * @param algo structure de la donn�e utilis� pour la r�solution
	 * @throws Exception 
	 */
	public static void Solution (String fichier , String delai , String algo) throws NonSolvableException , IOException , Exception{
		Taquin taquin = new Taquin (fichier);
			if (!taquin.solvable()){
				throw new NonSolvableException ("Pas de solution !");
			}else{
				int delaiConverti = Integer.parseInt(delai);
				Graphe  graphe = new Graphe (taquin,delaiConverti,algo);
				graphe.afficherSolution();
			}	
		
	}
	/**
	 * m�thode qui test la solvabilit� du jeu de taquin cr�� a partir du fichier donn�e
	 * en param�tre
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @return vrai si le jeu est solvable / faux sinon
	 * @throws IOException pour s'assurer de la bonne lecture du fichier
	 */
	public static boolean Solvable (String fichier) throws IOException {
		Taquin taquin = new Taquin (fichier);
		return taquin.solvable();
	}
	/**
	 * m�thode permettant de jouer au jeu de taquin contenu dans le fichier via le terminal
	 * @param fichier nom du fichier contenant le jeu de taquin
	 * @throws IOException pour s'assurer de la bonne lecture du fichier
	 * @throws NonSolvableException pour s'assurer que le jeu de taquin propos� admet
	 * une solution
	 */
	public static void Jouer (String fichier) throws IOException , NonSolvableException  {
		Taquin taquin = new Taquin (fichier);
		System.out.println(taquin);
		int compteurDeCoup = 0 ;
		boolean abandon = false;
		boolean victoire = taquin.testVictoire();
		while ((!victoire)&&(!abandon)){
			System.out.println("Quel case voulez vous d�placer sur la case vide ?");
			String choixCase = taquin.choixDeplacement();
			if (choixCase.equals("Abandonner")){
				abandon = true;
			}else{
				taquin.permutation(choixCase);
				compteurDeCoup++;
				System.out.println (taquin);
				if (taquin.vide.position == 0 ){    // testVictoire() �tant une m�thode assez gourmande on limite son utilisation ainsi
					victoire = taquin.testVictoire();
				}
			}	
		}
		if (taquin.testVictoire()){
			System.out.println("Bravo vous avez gagn� !");
			System.out.println("Nombre de coup : "+compteurDeCoup);
		}else{
			System.out.println("Partie termin� vous avez abandonn�");
		}
	}
	public static void main (String [] args){
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
					+ "des donn�es du fichier 'fichier.taq' est solvable false sinon");
			System.out.println("-joue 'fichier.taq' : permet de jouer au jeu de taquin "
					+ "avec les donn�es du fichier 'fichier.taq'");
			
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
				Solution (args[1],args[2],args[3]);
			}catch (Exception e){
				System.out.println (e);
			}
		}

	}
}

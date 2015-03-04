import java.util.*;
import java.io.*;
public class Taquin {
	int largeur ; // Largeur de la grille de taquin
	int hauteur ; // Hauteur de la grille de taquin
	Case[][] grille ; // grille du jeu de taquin contenant les cases (�quivalent a la position du jeu de taquin)
	Case vide; // La case vide qui peux �tre d�plac�
	int nbCases; // nombre de cases jouables dans le jeu (ce param�tre nous servira a effectuer certains controles)
	public static Scanner in = new Scanner (System.in);
	/**
	 * Constructeur vide
	 */
	public Taquin (){
		largeur = 0 ;
		hauteur = 0 ;
		grille = null;
		vide = null;
		nbCases = 0;
	}
	/**
	 * Constructeur de jeu de taquin 
	 * @param fichier	la cha�ne de caract�re fichier correspond au nom du fichier rentr� en 
	 * param�tre par l'utilisateur depuis lequel sera cr�� le jeu de taquin
	 * @throws IOException utile pour �viter les probl�mes li� a la lecture du fichier
	 */
	public Taquin (String fichier) throws IOException {
		File file = new File (fichier);
		Scanner sc = new Scanner (file);
		boolean caseVidePlace = false;
		ArrayList<Case> caseDejaPlace = new ArrayList<Case>();
		if (sc.hasNextInt()){
			largeur = sc.nextInt();
		}else{
			sc.close();
			throw new IOException ("Informations manquantes dans le fichier");
		}
		if (sc.hasNextInt()){
			hauteur = sc.nextInt();
		}else{
			sc.close();
			throw new IOException ("Informations manquantes dans le fichier");
		}
		nbCases = 0;
		int[][]stock = new int [hauteur][largeur];
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur ; j++){
				if (sc.hasNextInt()){
					stock[i][j] = sc.nextInt();
					nbCases ++;
				}else{
					sc.close();
					throw new IOException ("Informations manquantes dans le fichier");
				}
			}
		}	
		sc.close();
		int compteurPosition= 1;
		grille = new Case [hauteur][largeur];
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur ; j++){
				if (stock[i][j]>= 0){	
					if (compteurPosition==nbCases){
						compteurPosition = 0;
					}
					grille[i][j] = new Case(stock[i][j],compteurPosition,i,j);
					compteurPosition ++;
					if (grille[i][j].valeur==0){
						vide = grille[i][j];
						caseVidePlace = true;
					}else{
						if((grille[i][j].valeur<0)||(grille[i][j].valeur>= nbCases)){
							sc.close();
							throw new IOException ("Une des cases a une valeur incoh�rente");
						}else{
							if(caseDejaPlace.contains(grille[i][j])){
								sc.close();
								throw new IOException ("Une case a �t� plac� deux fois");
							}else{
								caseDejaPlace.add(grille[i][j]);
							}
						}
					}
				}	
				
			}
		}
		if (!caseVidePlace){
			throw new IOException ("Pas de case vide dans le jeu");
		}
	}
	/**
	 * L'appel de cette m�thode sur un jeu de taquin sert a tester sa solvabilit�
	 * @return vrai si le jeu est solvable / faux sinon
	 */
	public boolean solvable(){
		Taquin test = this.clone();
		return (pariteCaseVide() == test.pariteNombreDeplacement());
	}
	/**
	 * test la parit� de la position de la case vide (utilis� pour tester la solvabilit�)
	 * @return vrai si la case est sur une position pair / faux sinon
	 */
	public boolean pariteCaseVide (){
		int positionZero=(vide.hauteur*largeur)+(vide.largeur);
		return ((positionZero%2)==0);
	}
	/**
	 * test la parit� du nombre de d�placements n�cessaire pour atteindre la position de solution
	 * (utilis� pour tester la solvabilit�)
	 * @return vrai si le nombre de d�placements / faux sinon
	 */
	public boolean pariteNombreDeplacement (){
		int nbDeplacement=0;
		for (int i=0;i<hauteur;i++){
			for(int j=0;j<largeur;j++){
				if(grille[i][j].valeur != grille[i][j].position ){
					nbDeplacement++;
					permutation (grille[i][j],retrouverParPosition(grille[i][j].position));
				}
			}
		}
		return ((nbDeplacement%2)==0);
	}
	/**
	 * cette m�thode permet de retrouver une case du jeu de taquin gr�ce a sa valeur
	 * dans le jeu
	 * @param position : position dans le jeu de la case � trouver (pour plus de pr�cision sur 
	 * la position d'une case voir la class Case)
	 * @return la case li� a la position donn� en param�tre
	 */
	public Case retrouverParPosition(int position){
		Case aTrouver = new Case();
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur; j++){
				if(grille[i][j].valeur==position){
					aTrouver = grille[i][j];
				}
			}
		}
		return aTrouver;
	}
	public Case retrouverParValeur (int valeur){
		Case aTrouver = new Case();
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur; j++){
				if(grille[i][j].position==valeur){
					aTrouver = grille[i][j];
				}
			}
		}
		return aTrouver;
	}
	/**
	 * m�thode toString classique pour l'affichage
	 */
	public String toString (){
		String s ="";
		for (int i=0 ; i<hauteur ;i++){
			for (int j=0 ; j<largeur ; j++){
				s+= "| "+grille[i][j];
			}
			s+= " | \n";
		}
		return s;
	}
	/**
	 * Test si la position du jeu de taquin est une position de victoire
	 * @return vrai si la position est gagnante / faux sinon
	 */
	public boolean testVictoire (){
		boolean test = true;
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j <largeur ; j++){
				if((grille[i][j]!= null)&&(grille[i][j].valeur != grille[i][j].position )){
					test = false;
				}
			}
		}
		return test;
	}
	/**
	 * donne les coups jouables depuis une position du jeu de taquin
	 * @return les coups jouables sous la forme d'une liste de mouvement (en cha�ne de caract�re)
	 */
	public ArrayList<String> choixPossibles(){
		ArrayList<String> choixPossibles = new ArrayList<String>();
		if ((vide.largeur-1>=0)&&(grille[vide.hauteur][vide.largeur-1]!=null)){ // le -1 servira pour les grille non rectangulaire
			choixPossibles.add("Gauche");
		}
		if ((vide.largeur+1<largeur)&&(grille[vide.hauteur][vide.largeur+1]!=null)){ // le -1 servira pour les grille non rectangulaire
			choixPossibles.add("Droite");
		}
		if ((vide.hauteur-1>=0)&&(grille[vide.hauteur-1][vide.largeur]!=null)){ // le -1 servira pour les grille non rectangulaire
			choixPossibles.add("Haut");
		}
		if ((vide.hauteur+1<hauteur)&&(grille[vide.hauteur+1][vide.largeur]!=null)){ // le -1 servira pour les grille non rectangulaire
			choixPossibles.add("Bas");
		}
		return choixPossibles;
	}
	/**
	 * permet de s'assurer que l'utilisateur rentre un mouvement possible
	 * @return un mouvement possible ou l'instruction d'abandon sous
	 *  forme de cha�ne de caract�re 
	 */
	public String choixDeplacement (){
		ArrayList<String> choixPossibles = this.choixPossibles();
		Iterator<String> it = choixPossibles.iterator();
		while (it.hasNext()){
			String choix = it.next();
			System.out.println(choix);
		}
		System.out.println("Abandonner");
		choixPossibles.add("Abandonner");
		String choix = "";
		do{
			choix = in.nextLine();
			if (!choixPossibles.contains(choix)){
				System.out.println("Erreur de saisie v�rifiez la syntaxe "
						+ "de la case choisie (attention aux majuscules)");
			}
		}while (!choixPossibles.contains(choix));
		return choix;
	}
	/**
	 * change la position d'une case avec la case vide
	 * @param arriv� : la case a �chang� avec la case vide
	 */
	public void permutation (Case arriv�){
		this.vide.valeur = arriv�.valeur;
		arriv�.valeur = 0;
		this.vide = arriv�;
	}
	/**
	 * �change la position de deux cases
	 * @param d�part premiere case a �changer
	 * @param arriv� deuxi�me case a �changer
	 */
	public void permutation (Case d�part,Case arriv�){
		if (d�part==vide){
			permutation(arriv�);
		}else{
			if (arriv� == vide){
				permutation(d�part);
			}else{
				int valeur = d�part.valeur;
				d�part.valeur = arriv�.valeur;
				arriv�.valeur = valeur;
			}
		}
		
	}
	/**
	 * d�place la case vide dans une direction
	 * @param choixCase la direction dans laquelle va �tre d�plac� la case vide
	 */
	public void permutation (String choixCase){
		switch (choixCase){
		case ("Gauche"):
			this.permutation(this.grille[this.vide.hauteur][this.vide.largeur-1]);
			break;
		case ("Droite"):
			this.permutation(this.grille[this.vide.hauteur][this.vide.largeur+1]);
			break;
		case ("Haut"):
			this.permutation(this.grille[this.vide.hauteur-1][this.vide.largeur]);
			break;
		case ("Bas"):
			this.permutation(this.grille[this.vide.hauteur+1][this.vide.largeur]);
			break;	
		}
	}
	/**
	 * On a red�fini un HashCode pour pouvoir utilis� le containsKey sur une hashMap ayant
	 * en cl� des positions du jeu de taquin , d'une position � une autre dans notre utilisation
	 * de la class Taquin la seule chose qui doit chang� c'est le contenu de la grille et non
	 * sa forme , on a donc trouv� un moyen de test� l'�galit� du contenu de deux tableaux .
	 * 
	 * Pour se faire on s'est inspir� de la m�thode utilis� pour �crire les nombres en base 2,
	 * 8 ou 16 .
	 * 
	 * Par exemple pour un jeu de Taquin 2x2 de cette forme :
	 * contenu : |2|1|  position : |1|2|  nbCases = 4 
	 *           |3|0|             |3|0|
	 *           
	 * le hashCode nous donne : valeurCase1 + valeurCase2 + valeurCase3 + valeurCase0
	 * = (2*4^1) + (1*4^2) + (3*4^3) + (0*4^0)
	 * 
	 * De cette sorte chaque contenu poss�de son hashCode unique mais deux instances de taquin
	 * ayant le m�me contenu on �galement le m�me hashCode
	 */
	@Override
	public int hashCode() {
		int result = 0;
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur ; j++){
				result += this.grille[i][j].valeur*Math.pow(this.nbCases,this.grille[i][j].position);
			}
		}
		return result;
	}
	/**
	 * m�thode equals bas� sur le contenu du jeu de Taquin
	 */
	@Override
	public boolean equals (Object autre){
		if (!(autre instanceof Taquin)){
			return false;
		}else{
			Taquin other = (Taquin) autre;
			if (largeur!=other.largeur){
				return false;
			}
			if (hauteur!=other.hauteur){
				return false;
			}
			if (nbCases!=other.nbCases){
				return false;
			}
			if (!vide.equals(other.vide)){
				return false;
			}
			for (int i=0 ; i<hauteur ; i++){
				for (int j=0 ; j<largeur ; j++){
					if (!grille[i][j].equals(other.grille[i][j])){
						return false;
					}
				}
			}
			return true;
		}
	}
	/**
	 * ressort une copie de l'instance de jeu de taquin qui appel cette m�thode
	 */
	public Taquin clone(){
		Taquin clone = new Taquin ();
		clone.hauteur = hauteur;
		clone.largeur = largeur;
		clone.grille = new Case[hauteur][largeur];
		clone.nbCases = nbCases;
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j<largeur ; j++){
				clone.grille[i][j]= grille[i][j].clone();
				if (clone.grille[i][j].valeur == 0){
					clone.vide = clone.grille[i][j];
				}
			}
		}
		return clone;
	}
	/**
	 * cette m�thode donne la liste des successeurs de l'instance de Taquin 
	 * qui appel cette m�thode
	 * @return la liste des successeurs
	 */
	public ArrayList<Successeur> positionsSuivantes(){
		ArrayList<Successeur>Suivants = new ArrayList<Successeur>();
		ArrayList<String>D�placements = this.choixPossibles();
		Iterator<String> it = D�placements.iterator();
		while (it.hasNext()){
			String d�placement = it.next();
			Taquin clone = this.clone();
			
			clone.permutation(d�placement);
			
			Suivants.add(new Successeur (clone,d�placement));
		}
		return Suivants;
	}
	/**
	 * Calcul la distance de manhattan du jeu de taquin qui vaut la somme des distances de
	 * Manhattan de toute les cases du jeu 
	 * @return distance de Manhattan de la position du jeu de taquin qui appel la m�thode
	 */
	public int distanceDeManhattan (){
		int distanceDeManhattan = 0;
		for (int i = 0 ; i < hauteur ; i++){
			for (int j=0 ; j<largeur ; j++){
				if ((grille[i][j]!=null)&&(grille[i][j].position!=grille[i][j].valeur)){
					Case aAtteindre = this.retrouverParValeur(grille[i][j].valeur);
					distanceDeManhattan += Math.abs(grille[i][j].hauteur-aAtteindre.hauteur)+Math.abs(grille[i][j].largeur-aAtteindre.largeur);
				}
			}
		}
		return distanceDeManhattan;
	}
	
	
}	
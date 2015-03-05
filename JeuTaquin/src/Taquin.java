import java.util.*;
import java.io.*;
public class Taquin {
	int largeur ; // Largeur de la grille de taquin
	int hauteur ; // Hauteur de la grille de taquin
	Case[][] grille ; // grille du jeu de taquin contenant les cases (équivalent a la position du jeu de taquin)
	Case vide; // La case vide qui peux être déplacé
	int nbCases; // nombre de cases jouables dans le jeu (ce paramètre nous servira a effectuer certains controles)
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
	 * @param fichier	la chaîne de caractère fichier correspond au nom du fichier rentré en 
	 * paramètre par l'utilisateur depuis lequel sera créé le jeu de taquin
	 * @throws IOException utile pour éviter les problèmes lié a la lecture du fichier
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
							throw new IOException ("Une des cases a une valeur incohérente");
						}else{
							if(caseDejaPlace.contains(grille[i][j])){
								sc.close();
								throw new IOException ("Une case a été placé deux fois");
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
	 * L'appel de cette méthode sur un jeu de taquin sert a tester sa solvabilité
	 * @return vrai si le jeu est solvable / faux sinon
	 */
	public boolean solvable(){
		Taquin test = this.clone();
		return (pariteCaseVide() == test.pariteNombreDeplacement());
	}
	/**
	 * test la parité de la position de la case vide (utilisé pour tester la solvabilité)
	 * @return vrai si la case est sur une position pair / faux sinon
	 */
	public boolean pariteCaseVide (){
		int positionZero=(vide.hauteur*largeur)+(vide.largeur);
		return ((positionZero%2)==0);
	}
	/**
	 * test la parité du nombre de déplacements nécessaire pour atteindre la position de solution
	 * (utilisé pour tester la solvabilité)
	 * @return vrai si le nombre de déplacements / faux sinon
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
	 * cette méthode permet de retrouver une case du jeu de taquin grâce a sa valeur
	 * dans le jeu
	 * @param position : position dans le jeu de la case à trouver (pour plus de précision sur 
	 * la position d'une case voir la class Case)
	 * @return la case lié a la position donné en paramètre
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
	 * méthode toString classique pour l'affichage
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
		for (int i=0 ; i<hauteur ; i++){
			for (int j=0 ; j <largeur ; j++){
				if((grille[i][j]!= null)&&(grille[i][j].valeur != grille[i][j].position )){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * donne les coups jouables depuis une position du jeu de taquin
	 * @return les coups jouables sous la forme d'une liste de mouvement (en chaîne de caractère)
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
	 *  forme de chaîne de caractère 
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
				System.out.println("Erreur de saisie vérifiez la syntaxe "
						+ "de la case choisie (attention aux majuscules)");
			}
		}while (!choixPossibles.contains(choix));
		return choix;
	}
	/**
	 * change la position d'une case avec la case vide
	 * @param arrivé : la case a échangé avec la case vide
	 */
	public void permutation (Case arrivé){
		this.vide.valeur = arrivé.valeur;
		arrivé.valeur = 0;
		this.vide = arrivé;
	}
	/**
	 * échange la position de deux cases
	 * @param départ premiere case a échanger
	 * @param arrivé deuxième case a échanger
	 */
	public void permutation (Case départ,Case arrivé){
		if (départ==vide){
			permutation(arrivé);
		}else{
			if (arrivé == vide){
				permutation(départ);
			}else{
				int valeur = départ.valeur;
				départ.valeur = arrivé.valeur;
				arrivé.valeur = valeur;
			}
		}
		
	}
	/**
	 * déplace la case vide dans une direction
	 * @param choixCase la direction dans laquelle va être déplacé la case vide
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
	 * effectue une permutation sur le jeu de taquin relativement au caractère entré en paramètre
	 * @param choixCase première lettre de la direction dans laquel on se dirige
	 */
	public void permutation (char choixCase){
		switch(choixCase){
		case ('D'):
			this.permutation("Droite");
		    break;
		case ('G'):
			this.permutation("Gauche");
			break;
		case ('H'):
			this.permutation("Haut");
			break;
		case ('B'):
			this.permutation("Bas");
			break;
		}
	}
	/**
	 * On a redéfini un HashCode pour pouvoir utilisé le containsKey sur une hashMap ayant
	 * en clé des positions du jeu de taquin , d'une position à une autre dans notre utilisation
	 * de la class Taquin la seule chose qui doit changé c'est le contenu de la grille et non
	 * sa forme , on a donc trouvé un moyen de testé l'égalité du contenu de deux tableaux .
	 * 
	 * Pour se faire on s'est inspiré de la méthode utilisé pour écrire les nombres en base 2,
	 * 8 ou 16 .
	 * 
	 * Par exemple pour un jeu de Taquin 2x2 de cette forme :
	 * contenu : |2|1|  position : |1|2|  nbCases = 4 
	 *           |3|0|             |3|0|
	 *           
	 * le hashCode nous donne : valeurCase1 + valeurCase2 + valeurCase3 + valeurCase0
	 * = (2*4^1) + (1*4^2) + (3*4^3) + (0*4^0)
	 * 
	 * De cette sorte chaque contenu possède son hashCode unique mais deux instances de taquin
	 * ayant le même contenu on également le même hashCode
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
	 * méthode equals basé sur le contenu du jeu de Taquin
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
	 * ressort une copie de l'instance de jeu de taquin qui appel cette méthode
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
	 * cette méthode donne la liste des successeurs de l'instance de Taquin 
	 * qui appel cette méthode
	 * @return la liste des successeurs
	 */
	public ArrayList<Successeur> positionsSuivantes(){
		ArrayList<Successeur>Suivants = new ArrayList<Successeur>();
		ArrayList<String>Déplacements = this.choixPossibles();
		Iterator<String> it = Déplacements.iterator();
		while (it.hasNext()){
			String déplacement = it.next();
			Taquin clone = this.clone();
			
			clone.permutation(déplacement);
			
			Suivants.add(new Successeur (clone,déplacement));
		}
		return Suivants;
	}
	/**
	 * Calcul la distance de manhattan du jeu de taquin qui vaut la somme des distances de
	 * Manhattan de toute les cases du jeu 
	 * @return distance de Manhattan de la position du jeu de taquin qui appel la méthode
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
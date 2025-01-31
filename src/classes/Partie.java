package classes;

import java.awt.FontFormatException;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Cette classe représente une partie du jeu Pocket Imperium. Elle permet de
 * commencer une nouvelle partie ou de reprendre une ancienne partie
 * sauvegardée. Elle gère le déroulement général du jeu.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Partie extends Observable implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Liste des joueurs associés à la partie en cours. Il s'agit d'une instance
     * d'ArrayList qui contient les références des objets {@link Joueur}.
     */
    private List<classes.Joueur> listeJoueurs = new ArrayList<classes.Joueur>(); //Référence et objet de type ArrayList

    /**
     * Donne le numéro du tour en cours.
     */
    int tourActuel;
    /**
     * Donne la phase en cours.
     */
    int phaseActuelle;
    /**
     * Donne la moment actuel du tour/de la partie. On l'initialise au premier
     * placement des joueurs sur le plateau.
     */
    Moment moment = Moment.PremierPlacement;

    /**
     * Liste des actions dans l'ordre a effectuer à chaque tour. Il s'agit d'une
     * instance d'ArrayList qui contient les références des objets
     * {@link Action}.
     */
    ArrayList<Action> listeActions = new ArrayList<>();

    /**
     * Plateau de jeu représenté par une matrice hexagonale. - Il s'agit d'un
     * tableau 2D de dimensions fixes : 9 lignes et 6 colonnes. - Chaque cellule
     * contient une instance de la classe {@link Hexagone}. Utilisé pour gérer
     * les cases du plateau et leur état dans le jeu.
     *
     * Les coordonnées sont accessibles via `plateauJeu[x][y]`, où `x`
     * représente la ligne et `y` la colonne.
     *
     */
    private Hexagone[][] plateauJeu = new Hexagone[9][6];
    /**
     * Tableau 2D de dimensions fixes : 3 lignes et 3 colonnes. Chaque cellule
     * contient une valeur entière qui est de 0 ou de 1. Utilisé pour gérer la
     * position des 9 cartes Secteurs. Si la celulle contient un 1 : le secteur
     * est déjà placé
     */
    private int[][] positionSecteurs = new int[3][3];
    /**
     * Liste des secteurs qui consitutuent le plateau. Il s'agit d'une instance
     * d'ArrayList qui contient les références des objets {@link Secteur}.
     */
    private List<classes.Secteur> listeSecteurs = new ArrayList<classes.Secteur>();
    /**
     *
     * Instance unique de la classe {@link Partie}, utilisée dans le cadre du
     * pattern Singleton.
     *
     * - Cette variable est statique pour garantir qu'une seule instance de
     * {@link Partie} existe tout au long du cycle de vie de l'application.
     */
    private static Partie instance;
    /**
     * Index du joueur actuellement en cours de jeu.
     */
    private int joueurEnCours;

    /**
     * Indicateur de la priorité des cartes en cours.
     */
    private int prioEnCours;

    /**
     * Tableau du nombre d'actions que le joueur 1 peu réalisé pour ses 3
     * cartes.
     *
     * Structure : tableau 2D de dimensions 3x3. Initialisé à vide (toutes les
     * valeurs à 0) et modifié au cours du jeu.
     */
    private int[][] nbActionsJoueur1 = new int[3][3];

    /**
     * Tableau du nombre d'actions que le joueur 2 peu réalisé pour ses 3
     * cartes.
     *
     * Structure : tableau 2D de dimensions 3x3. Initialisé à vide (toutes les
     * valeurs à 0) et modifié au cours du jeu.
     */
    private int[][] nbActionsJoueur2 = new int[3][3];

    /**
     * Tableau du nombre d'actions que le joueur 3 peu réalisé pour ses 3
     * cartes.
     *
     * Structure : tableau 2D de dimensions 3x3. Initialisé à vide (toutes les
     * valeurs à 0) et modifié au cours du jeu.
     */
    private int[][] nbActionsJoueur3 = new int[3][3];
    /**
     * Tableau du nombre d'actions que le joueur 1 peu réalisé pour ses 3
     * cartes.
     *
     * Structure : tableau 2D de dimensions 3x3. Initialisé à vide (toutes les
     * valeurs à 0) et modifié au cours du jeu.
     */
    /**
     * Matrice 3x3 contenant des entiers en focntions du nombre de joueur ayant
     * choisi une carte à une certaine priorité. nbJoueursCarte[0][1] : renvoi
     * le nombre de joueur ayant choisi la carte développement en 2 ème
     * posistion
     */
    int[][] nbJoueursCarte = new int[3][3];
    /**
     * Dictionnaire qui permet de faire la corespondance entre le numéro du
     * secteur et sa réelle posisition sur le plateau. Il s'agit d'une instance
     * d'HashMap qui contient des clés : un objet de type {@link Secteur} et la
     * valeur entière associé à la position du secteur sur le plateau.
     */

    private Map<Secteur, Integer> map = new HashMap<Secteur, Integer>();
    /**
     * Dictionnaire inverse de {@link map}qui permet de faire la corespondance
     * entre le numéro du secteur et sa réelle posisition sur le plateau. Il
     * s'agit d'une instance d'HashMap qui contient des clés : valeur entière
     * associé à la position du secteur sur le plateau et un objet de type
     * {@link Secteur}. le numéro est utilisé par le joueur pour simplifier
     * l'écriture en ligne de commande
     */
    private Map<Integer, Secteur> map2 = new HashMap<Integer, Secteur>();

    /**
     * FenetrePrincipale du jeu qui permet d'afficher la vue du plateau de type
     * {@link Fenetre}.
     */
    private Fenetre FenetrePrincipale;

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Partie}
     */
    public Partie() {
        this.listeJoueurs.add(new classes.Joueur(null, null, null, 0));
        this.listeJoueurs.add(new classes.Joueur(null, null, null, 1));
        this.listeJoueurs.add(new classes.Joueur(null, null, null, 2));
    }

    /**
     * Retourne le joueur en cours.
     *
     * @return un entier
     */
    public int getJoueurEnCours() {
        return this.joueurEnCours;
    }

    /**
     * Met à jour la valeur du joueur en cours, qui agit.
     *
     * @param joueurEnCours
     */
    public void setJoueurEnCours(int joueurEnCours) {
        this.joueurEnCours = joueurEnCours;
    }

    /**
     * Retourne la priorité en cours.
     *
     * @return un entier
     */
    public int getPrioEnCours() {
        return this.prioEnCours;
    }

    /**
     * Met à jour la valeur de la priorité en cours.
     *
     * @param prioEnCours
     */
    public void setPrioEnCours(int prioEnCours) {
        this.prioEnCours = prioEnCours;
    }

    /**
     * Retourne l'unique instance de la classe {@link Partie} si elle n'existe
     * pas déjà elle est crée.
     *
     * @return instance unique de Partie
     */
    public static Partie getInstance() {
        if (Partie.instance == null) {
            Partie.instance = new Partie();
        }
        return Partie.instance;
    }

    /**
     * Retourne le tableau représentant le nombre de joueurs par carte.
     *
     * @return Un tableau 2D d'entiers (`int[][]`) contenant les informations
     * sur le nombre de joueurs ayant choisi telle carte i pour la priorité j.
     * int[i][j]
     */
    public int[][] getNbJoueursCarte() {
        return this.nbJoueursCarte;
    }

    /**
     * Méthode qui incremente le nombre de joueurs qui ont choisi la carte à une
     * certaine position
     *
     * @param position
     * @param carte
     */
    public void incrementNbJoueursCarte(int position, int carte) {
        this.nbJoueursCarte[carte][position] = this.nbJoueursCarte[carte][position] + 1;
    }

    /**
     * Méthode qui permet de récupérer la liste des joueurs
     *
     * @return ArrayList avec l'ensemble des joueurs de type {@link Joueur}.
     */
    public List<classes.Joueur> getListeJoueurs() {
        return this.instance.listeJoueurs;
    }

    /**
     * Méthode qui permet de récupérer la liste des secteurs
     *
     * @return ArrayList avec l'ensemble des secteurs de type {@link Secteur}.
     */
    public List<classes.Secteur> getListeSecteurs() {
        return this.instance.listeSecteurs;
    }

    /**
     * Retourne le tour en cours.
     *
     * @return un entier
     */
    public int getTour() {
        return this.instance.tourActuel;
    }

    /**
     * Met à jour la valeur du tour en cours.
     *
     * @param t un entier
     */
    public void setTour(int t) {
        this.instance.tourActuel = t;
    }

    /**
     * Retourne le joueur en cours.
     *
     * @return un entier
     */
    public int getPhase() {
        return this.instance.phaseActuelle;
    }

    /**
     * Met à jour la valeur de la phase en cours.
     *
     * @param val
     */
    public void setPhase(int val) {
        this.instance.phaseActuelle = val;
    }

    /**
     * Retourne le Moment en cours.
     *
     * @return {@link Moment}
     */
    public Moment getMoment() {
        return this.instance.moment;
    }

    /**
     * Met à jour la valeur du Moment en cours.
     *
     * @param moment de type {@link Moment}
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'exécution de la méthode.
     * @throws FontFormatException Si un problème lié au format de police
     * survient.
     */
    public void setMoment(Moment moment) throws IOException, FontFormatException {
        this.instance.moment = moment;
        this.instance.setChanged();
        this.instance.notifyObservers();
    }

    /**
     * Rtourne le nom d'un joueur i parmi les joueurs de la partie.
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @return Chaine de caractères
     */
    public String getNomJoueur(int i) {
        return this.instance.listeJoueurs.get(i).getNom();
    }

    /**
     * Retourne secteur en fonction de son numero.
     *
     * @param num
     * @return Secteur de type {@link Secteur}
     */
    public Secteur getSecteurByNum(int num) {
        return this.instance.getInstance().getMap2().get(num);
    }

    /**
     * Retourne le numero du joueur qui possède le tri time. (renvoie -1 si
     * personne ne le possède)
     *
     * @return Numéro du joueur (ou -1)
     */
    public int getJoueurTriTime() {
        for (int i = 0; i < 3; i++) {
            if (this.getInstance().listeJoueurs.get(i).possedeTritime()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retourne le nombre de Vaisseaux que possède un joueur i
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @return entier
     */
    public int getNbVaisseaux(int i) {
        return this.instance.listeJoueurs.get(i).getNbVaisseaux();
    }

    /**
     * Retourne la vue du plateau.
     *
     * @return vue du plateau de type {@link Fenetre}
     */
    public Fenetre getFenetrePrincipale() {
        return FenetrePrincipale;
    }

    /**
     * Met à jour la fenetre du jeu.
     *
     * @param fenetrePrincipale {@link Fenetre}
     */
    public void setFenetrePrincipale(Fenetre fenetrePrincipale) {
        FenetrePrincipale = fenetrePrincipale;
    }

    /**
     * Retourn le nombre de points d'un joueur i
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @return entier
     */
    public int getNbPoints(int i) {
        return this.instance.listeJoueurs.get(i).getNbPoints();
    }

    /**
     * Met à jour le nombre de points d'un joueur i
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @param nbpoints
     */
    public void setNbPoints(int i, int nbpoints) {
        this.instance.listeJoueurs.get(i).setNbPoints(nbpoints);
    }

    /**
     * Retourn la couleur du joueur
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @return Couleur de type {@link Couleur}
     */
    public classes.Couleur getCouleurJoueur(int i) {
        return this.instance.listeJoueurs.get(i).getCouleur();
    }

    /**
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @param C Nouvelle couleur du joueur de type {@link Couleur}
     */
    public void setCouleurJoueur(int i, classes.Couleur C) {
        this.instance.listeJoueurs.get(i).setCouleur(C);
    }

    /**
     * Met à jour le nom d'un joueur i.
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @param Nom Chaine de caractères : nouveau nom du joueur
     */
    public void setNomJoueur(int i, String Nom) {
        this.instance.listeJoueurs.get(i).setNom(Nom);
    }

    /**
     * Met à jour le nom d'un joueur i
     *
     * @see #setNomJoueur(int, String) Pour plus de détails sur l actualisation
     * du nom d'un joueur avec un nom personnalisé.
     *
     * @param i L'index du joueur dans la liste des joueurs
     */
    public void setNomJoueur(int i) {
        this.instance.listeJoueurs.get(i).setNom();
    }

    /**
     * Met à jour le type d'un joueur i
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @param T Le nouveau type du joueur de type {@link TypeJoueur}
     */
    public void setTypeJoueur(int i, classes.TypeJoueur T) {
        this.instance.listeJoueurs.get(i).setType(T);
    }

    /**
     * Met à jour la stratégie d'un joueur i
     *
     * @param i L'index du joueur dans la liste des joueurs
     * @param strategie chaine de caractères : Nouvelle stratégie du joueur
     */
    public void setStrategie(int i, String strategie) {
        this.instance.listeJoueurs.get(i).setStrategie(strategie);
    }

    /**
     * Déplace en début de partie un vaisseau d'un joueur vers une nouvelle
     * position innocupée sur le plateau.
     *
     * Cette méthode permet de déplacer la flotte d'un joueur spécifié en
     * indiquant les nouvelles coordonnées pour le secteur et l'hexagone cible.
     * Elle met à jour la position du vaisseau dans la liste du joueur.
     *
     * @param i L'index du joueur dans la liste des joueurs, représentant le
     * joueur qui effectue le déplacement.
     * @param xSecteur La coordonnée X du secteur de destination.
     * @param ySecteur La coordonnée Y du secteur de destination.
     * @param xHexa La coordonnée X de l'hexagone de destination.
     * @param yHexa La coordonnée Y de l'hexagone de destination.
     * @param vaisseau L'index du vaisseau dans la liste du joueur à déplacer.
     *
     * @throws ExceptionZone Si l'hexagone cible est déjà contrôlé, empêchant le
     * déplacement du vaisseau.
     */
    public void deplacerFlotteJoueur(int i, int xSecteur, int ySecteur, int xHexa, int yHexa, int vaisseau) throws ExceptionZone {
        this.instance.listeJoueurs.get(i).getListeVaisseaux().get(vaisseau).deplacerFlotte(xSecteur, ySecteur, xHexa, yHexa);
    }

    /**
     * Développe un vaisseau pour un joueur spécifique Cette méthode ajoute un
     * vaisseau sur la zone que le joueur possède déjà
     *
     * @param i L'indice du joueur dans la liste des joueurs
     * @param prio La priorité de la carte déevleoppemnt
     * @param xSecteur La coordonnée x du secteur où le vaisseau doit être
     * développé.
     * @param ySecteur La coordonnée y du secteur où le vaisseau doit être
     * développé.
     * @param xHexa La coordonnée x de la cellule hexagonale où le vaisseau sera
     * ajouté.
     * @param yHexa La coordonnée y de la cellule hexagonale où le vaisseau sera
     * ajouté.
     *
     * @throws ExceptionZone Si la zone spécifiée pour le développement du
     * vaisseau est invalide ou non autorisée.
     */
    public void developperVaisseauJoueur(int i, int prio, int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionZone {
        this.instance.listeJoueurs.get(i).getCarteDeveloppement().ajouterVaisseau(prio, xSecteur, ySecteur, xHexa, yHexa);
    }

    /**
     * Explore, donc déplace un vaisseau ou plusieurs vaisseaux de l'hexagone
     * selectionnée pour un joueur spécifique vers une zone choisie en ligne de
     * commande.
     *
     * @param i L'indice du joueur dans la liste des joueurs
     * @param prio La priorité de la carte exploration
     * @param xSecteur La coordonnée x du secteur du/des vaisseau(x) ui
     * explore(nt).
     * @param ySecteur La coordonnée y du secteur du/des vaisseau(x) qui
     * explore(nt).
     * @param xHexa La coordonnée x de la cellule hexagonale du/des vaisseau(x)
     * qui explore(nt).
     * @param yHexa La coordonnée y de la cellule hexagonale du/des vaisseau(x)
     * qui explore(nt).
     *
     * @throws ExceptionZone Si la zone spécifiée pour le développement du
     * vaisseau est invalide ou non autorisée.
     * @throws ExceptionManqueVaisseauxDisponibles Si tous les vaisseaux du
     * joueur on été impliqué dans un déplacement durant de tour
     */
    public void explorerFlotteJoueur(int i, int prio, int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionZone, ExceptionManqueVaisseauxDisponibles {
        this.instance.listeJoueurs.get(i).getCarteExploration().deplacerFlotte(prio, xSecteur, ySecteur, xHexa, yHexa);

    }

    /**
     * Attaque un hexagone Cette méthode fait démarrer un combat entre 2
     * joueurs, le joueur attaqué et le joueur qui possède l'hexagone initial
     * selcetionné. Le joeuur attaqué contrôle l'hexagone choisi en ligne de
     * commande.
     *
     * @param i L'indice du joueur dans la liste des joueurs
     * @param prio La priorité de la carte déevleoppemnt
     * @param xSecteur La coordonnée x du secteur du/des vaisseau(x) qui
     * attaque(nt).
     * @param ySecteur La coordonnée y du secteur du/des vaisseau(x) qui
     * attaque(nt).
     * @param xHexa La coordonnée x de la cellule hexagonale du/des vaisseau(x)
     * qui attaque(nt).
     * @param yHexa La coordonnée y de la cellule hexagonale du/des vaisseau(x)
     * qui attaque(nt).
     *
     * @throws ExceptionZone Si la zone spécifiée pour le développement du
     * vaisseau est invalide ou non autorisée.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'exécution de la méthode.
     * @throws FontFormatException Si un problème lié au format de police
     * survient.
     *
     */
    public void attaquerFlotteJoueur(int i, int prio, int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionZone, IOException, FontFormatException {
        this.instance.listeJoueurs.get(i).getCarteAttaque().envahirFlotte(prio, xSecteur, ySecteur, xHexa, yHexa);

    }

    /**
     * Retourne le tableaux à deux dimensions contenant des 1 ou des 0 selon si
     * un secteur est déjà positionné à cette emplacmeent.
     *
     * @return Le tableaux à deux dimensions.
     */
    public int[][] getPositionSecteurs() {
        return this.instance.positionSecteurs;
    }

    /**
     * Initialise la position des secteurs sur le plateau de manière aléatoire.
     */
    public void setPositionSecteurs() {
        //choix aléatoire du placement du secteur 
        for (int i = 0; i < 9; i++) {
            Secteur secteur = this.instance.listeSecteurs.get(i);
            secteur.choixPlacement(this.instance.positionSecteurs);
        }

        // faire la correspondance entre le numéro et la coordonnée du secteur pour faciliter le choix du secteur par le joueur en ligne de commande : initialisation de map et map2
        for (int i = 0; i < 9; i++) {
            //pour chaque secteur de listeSecteurs
            Secteur secteur = this.instance.listeSecteurs.get(i);
            int[] tab = {secteur.getPosition().getPositionX(), secteur.getPosition().getPositionY()}; //récupère le secteur 

            if (tab[0] == 0 && tab[1] == 0) {
                this.instance.map2.put(1, secteur);
                this.instance.map.put(secteur, 1);
            } else if (tab[0] == 0 && tab[1] == 1) {
                this.instance.map.put(secteur, 2);
                this.instance.map2.put(2, secteur);

            } else if (tab[0] == 0 && tab[1] == 2) {
                this.instance.map.put(secteur, 3);
                this.instance.map2.put(3, secteur);
            } else if (tab[0] == 1 && tab[1] == 0) {
                this.instance.map.put(secteur, 4);
                this.instance.map2.put(4, secteur);

            } else if (tab[0] == 1 && tab[1] == 1) {
                this.instance.map2.put(5, secteur);
                this.instance.map.put(secteur, 5);

            } else if (tab[0] == 1 && tab[1] == 2) {
                this.instance.map.put(secteur, 6);
                this.instance.map2.put(6, secteur);

            } else if (tab[0] == 2 && tab[1] == 0) {
                this.instance.map.put(secteur, 7);
                this.instance.map2.put(7, secteur);

            } else if (tab[0] == 2 && tab[1] == 1) {
                this.instance.map.put(secteur, 8);
                this.instance.map2.put(8, secteur);

            }
            if (tab[0] == 2 && tab[1] == 2) {
                this.instance.map.put(secteur, 9);
                this.instance.map2.put(9, secteur);
                //System.out.println("poisitoj"+tab[0]+tab[1]+this.instance.map2.get(9).getType());

            }
        }
    }

    /**
     * Méthode qui permet d'initialiser le plateau de jeu
     */
    public void initialisePlateauJeu() {
        //après avoir determiné la position des secteurs sur le plateau dans l'ordre des numéros des secteurs on ajoute chaque hexagone à la matrice

        for (int i = 1; i < 10; i++) { //Parcourir les 9 secteurs
            for (int h = 0; h < 3; h++) {
                for (int l = 0; l < 2; l++) { //parcourir tous les hexagones du secteur de coordonnée h,l
                    Secteur secteur = this.instance.map2.get(i); //récupère le secteur associé au secteur numéro i
                    Hexagone hexagone = secteur.getHexagone(h, l); //récuperer chaque hexagone du seceur numéro i
                    int x = secteur.getPosition().getPositionX();
                    int y = secteur.getPosition().getPositionY();
                    this.instance.plateauJeu[x * 3 + h][y * 2 + l] = hexagone; //ajout de l'hexagone dans le plateau de jeu à la matrice qui représente le pleateau
                }
            }
        }
    }

    /**
     * Méthode qui permet de récupérer la matrice qui représente le plateau de
     * jeu
     *
     * @return Matrice d'{@link Hexagone}
     */
    public Hexagone[][] getPlateauJeu() {
        return this.plateauJeu;
    }

    /**
     * Initialise le nombre d'actions que peut faire le joueur
     *
     * @see #nbActionsJoueur1
     */
    public void InitialiserNbActionsJoueurs() {
        for (int i = 0; i < this.getInstance().getNbJoueursCarte().length; i++) {
            for (int j = 0; j < this.getInstance().getNbJoueursCarte()[0].length; j++) {
                this.instance.nbActionsJoueur1[i][j] = 4 - this.getInstance().getNbJoueursCarte()[i][j];
                this.instance.nbActionsJoueur2[i][j] = 4 - this.getInstance().getNbJoueursCarte()[i][j];
                this.instance.nbActionsJoueur3[i][j] = 4 - this.getInstance().getNbJoueursCarte()[i][j];
            }
        }
    }

    /**
     * Retourne le nombre d'actions du joueur1
     *
     * @return une matrice d'entier
     */
    public int[][] getNbActionsJoueur1() {
        return nbActionsJoueur1;
    }

    /**
     * Retourne le nombre d'actions du joueur2
     *
     * @return une matrice d'entier
     */
    public int[][] getNbActionsJoueur2() {
        return nbActionsJoueur2;
    }

    /**
     * Retourne le nombre d'actions du joueur3
     *
     * @return une matrice d'entier
     */
    public int[][] getNbActionsJoueur3() {
        return nbActionsJoueur3;
    }

    /**
     * Décrémenter le nombre d'actions du joueur1
     *
     * @param i numéro de carte
     * @param j numéro de priorité
     */
    public void diminuerNbActionsJoueur1(int i, int j) {
        this.nbActionsJoueur1[i][j]--;
    }

    /**
     * Décrémenter le nombre d'actions du joueur2
     *
     * @param i numéro de carte
     * @param j numéro de priorité
     */
    public void diminuerNbActionsJoueur2(int i, int j) {
        this.nbActionsJoueur2[i][j]--;
    }

    /**
     * Décrémenter le nombre d'actions du joueur3
     *
     * @param i numéro de carte
     * @param j numéro de priorité
     */
    public void diminuerNbActionsJoueur3(int i, int j) {
        this.nbActionsJoueur3[i][j]--;
    }

    /**
     * Met à jour le tableau du nombre de joueur qui ont choisi les cartes.
     *
     * @see #nbJoueursCarte
     * @param nbJoueursCarte
     */
    public void setNbJoueursCarte(int[][] nbJoueursCarte) {
        this.nbJoueursCarte = nbJoueursCarte;
    }

    /**
     * Méthode qui permet de démarrer une nouvelle partie.
     */
    public void commencerNouvellePartie(classes.TypeJoueur[] Types, String[] Infos) {
        this.instance = Partie.getInstance();

        //initialisation des couleurs des joueurs
        this.instance.setCouleurJoueur(0, classes.Couleur.ROUGE);
        this.instance.setCouleurJoueur(1, classes.Couleur.BLEU);
        this.instance.setCouleurJoueur(2, classes.Couleur.JAUNE);

        this.instance.phaseActuelle = 1;

        for (int i = 0; i < 3; i++) { //parcourir les 3 joueurs pour initialiser leur type, leur stratégie et leur nom
            if (Types[i] == TypeJoueur.HUMAIN) {
                this.instance.setTypeJoueur(i, TypeJoueur.HUMAIN);
                this.instance.listeJoueurs.get(i).setNom(Infos[i]);
                this.instance.instance.instance.listeJoueurs.get(i).setStrategie("StrategieHumain");
            } else {
                this.instance.setTypeJoueur(i, classes.TypeJoueur.VIRTUEL);
                this.instance.listeJoueurs.get(i).setNom("Joueur " + (i + 1));
                this.instance.instance.instance.listeJoueurs.get(i).setStrategie(Infos[i]);
            }
        }
        //Affichage des joueurs : 
        for (int j = 0; j < 3; j++) {
            System.out.println(this.instance.listeJoueurs.get(j));
        }

        //intstallation du plateau
        //création des 9 cartes secteurs : 
        //6 cartes BORD        
        for (int i = 0; i < 6; i++) {
            String nomImage = "Bord" + (i + 1);
            this.instance.listeSecteurs.add(new Secteur(Type.BORD, nomImage));

        }
        //initialiser les points des cartes secteurs bords
        //bord haut gauche
        this.instance.listeSecteurs.get(0).getHexagone(0, 0).setPoints(2);
        this.instance.listeSecteurs.get(0).getHexagone(1, 0).setPoints(1);
        this.instance.listeSecteurs.get(0).getHexagone(2, 1).setPoints(1);
//bord haut milieu
        this.instance.listeSecteurs.get(1).getHexagone(0, 0).setPoints(1);
        this.instance.listeSecteurs.get(1).getHexagone(2, 0).setPoints(2);
        this.instance.listeSecteurs.get(1).getHexagone(1, 0).setPoints(1);
        //bord haut droite
        this.instance.listeSecteurs.get(2).getHexagone(0, 0).setPoints(1);
        this.instance.listeSecteurs.get(2).getHexagone(0, 1).setPoints(1);
        this.instance.listeSecteurs.get(2).getHexagone(2, 1).setPoints(2);

        // bord bas gauche
        this.instance.listeSecteurs.get(3).getHexagone(2, 0).setPoints(1);
        this.instance.listeSecteurs.get(3).getHexagone(1, 0).setPoints(2);
        this.instance.listeSecteurs.get(3).getHexagone(2, 1).setPoints(1);

        // bord bas milieu
        this.instance.listeSecteurs.get(4).getHexagone(0, 0).setPoints(1);
        this.instance.listeSecteurs.get(4).getHexagone(1, 0).setPoints(2);
        this.instance.listeSecteurs.get(4).getHexagone(2, 0).setPoints(1);

        // bord bas droit
        this.instance.listeSecteurs.get(5).getHexagone(0, 0).setPoints(1);
        this.instance.listeSecteurs.get(5).getHexagone(1, 0).setPoints(2);
        this.instance.listeSecteurs.get(5).getHexagone(0, 1).setPoints(1);

        //2 cartes COTE
        for (int i = 0; i < 2; i++) {
            String nomImage = "Cote" + (i + 1);
            this.instance.listeSecteurs.add(new Secteur(Type.COTE, nomImage));
        }
        //initialiser les points des cartes secteurs bords

        //coté gauche
        this.instance.listeSecteurs.get(6).getHexagone(0, 1).setPoints(2);
        this.instance.listeSecteurs.get(6).getHexagone(0, 0).setPoints(-1);
        this.instance.listeSecteurs.get(6).getHexagone(2, 0).setPoints(-1);
        this.instance.listeSecteurs.get(6).getHexagone(1, 0).setPoints(1);
        this.instance.listeSecteurs.get(6).getHexagone(2, 1).setPoints(1);

        //cote droit
        this.instance.listeSecteurs.get(7).getHexagone(0, 1).setPoints(1);
        this.instance.listeSecteurs.get(7).getHexagone(1, 1).setPoints(2);
        this.instance.listeSecteurs.get(7).getHexagone(2, 1).setPoints(1);

        //1 carte TRITIME
        this.instance.listeSecteurs.add(new Secteur(Type.TRITIME, "TriTime"));
        this.instance.listeSecteurs.get(8).getHexagone(1, 0).setPoints(3);
        this.instance.listeSecteurs.get(8).getHexagone(0, 1).setPoints(3);
        this.instance.listeSecteurs.get(8).getHexagone(1, 1).setPoints(3);
        this.instance.listeSecteurs.get(8).getHexagone(2, 1).setPoints(3);

        setPositionSecteurs();
        initialisePlateauJeu();

        System.out.println("deroulement phases");

        try {
            this.instance.FenetrePrincipale = new Fenetre(this);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Retourne la correspondance entre les coordonnées du secteur et son numéro
     * utilisé par le joueur pour simplifier l'écriture
     *
     * @return
     */
    public Map<Integer, Secteur> getMap2() {
        return map2;
    }

    /**
     * Retourne la correspondance entre les coordonnées du secteur et son numéro
     * utilisé par le joueur pour simplifier l'écriture
     *
     * @return
     */
    public Map<Secteur, Integer> getMap() {
        return map;
    }

    /**
     * Méthode qui permet de savoir s'il faut commencer un nouveau tour ou non
     *
     * @return boolean
     */
    public boolean estTermine() {
        if ((this.instance.tourActuel <= 9) && (this.instance.listeJoueurs.get(0).getNbVaisseaux() > 0) && (this.instance.listeJoueurs.get(1).getNbVaisseaux() > 0) && (this.instance.listeJoueurs.get(2).getNbVaisseaux() > 0)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Méthode qui fait les actions du début du tour (reset les etats etc)
     */
    public void debutTour() {
        nbJoueursCarte = new int[3][3];
        nbActionsJoueur1 = new int[3][3];
        nbActionsJoueur2 = new int[3][3];
        nbActionsJoueur3 = new int[3][3];

        //remise à 0 des états des vaisseaux sur les attaques et les explorations
        for (int i = 0; i < this.instance.listeJoueurs.size(); i++) { //parcourir tous les joueurs
            for (int j = 0; j < this.instance.listeJoueurs.get(i).getListeVaisseaux().size(); j++) { //parcourir tous les vaisseaux
                this.instance.listeJoueurs.get(i).getListeVaisseaux().get(j).setAExplore(false);
                this.instance.listeJoueurs.get(i).getListeVaisseaux().get(j).setAAttaque(false);
            }
        }
        //remise à 0 des priorité des cartes
        for (int i = 0; i < this.instance.listeJoueurs.size(); i++) { //parcourir tous les joueurs
            this.listeJoueurs.get(i).initCartes();
        }
        //Remise a zero des secteurs comptabilisés au scoring
        for (int i = 1; i < 10; i++) {
            Secteur secteur = this.getInstance().getMap2().get(i);
            secteur.setAEteCompte(false);
        }

    }

    /**
     * Méthode qui fait les actions de la phase 1
     *
     * @param numeroJoueur
     * @param prioriteDeveloppement
     * @param prioriteExploration
     * @param prioriteAttaque
     * @throws ExceptionChoixOrdre Si le choix de l'ordre n'est pas cohérent.
     *
     */
    public void phase1(int numeroJoueur, int prioriteDeveloppement, int prioriteExploration, int prioriteAttaque) throws ExceptionChoixOrdre {

        this.phaseActuelle = 1;
        if (numeroJoueur == 0) {
            this.debutTour();//Si c'est le premier joueur du tour, on remet tout a zero
        }//On lance le choix de l'ordre du joueur
        this.instance.listeJoueurs.get(numeroJoueur).choixOrdre(prioriteDeveloppement, prioriteExploration, prioriteAttaque);
    }

    /**
     * Méthode permet de set la liste des actions a realiser dans l'ordre
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'exécution de la méthode.
     * @throws FontFormatException Si un problème lié au format de police
     * survient.
     */
    public void setListeAction() throws IOException, FontFormatException {
        List<Joueur> joueurs = Partie.getInstance().getListeJoueurs();
        if (this.listeActions.size() != 9) { //Si pas encore remplie
            for (int j = 1; j <= 3; j++) {
                for (int i = 0; i <= 2; i++) {
                    Joueur joueur = joueurs.get(i);
                    //Developpement
                    if (joueurs.get(i).getPrioriteDeveloppement() == j) {
                        listeActions.add(new Action(joueurs.get(i), "Developpe", j));
                    }

                    //Exploration
                    if (joueurs.get(i).getPrioriteExploration() == j) {
                        listeActions.add(new Action(joueurs.get(i), "Explore", j));
                    }

                    //Attaque
                    if (joueurs.get(i).getPrioriteAttaque() == j) {
                        listeActions.add(new Action(joueurs.get(i), "Attaque", j));
                    }
                }
            }
            //On trie la collection en fonction de la pririté, des types d'actions puis du joueur
            Collections.sort(listeActions, Comparator
                    .comparing(Action::getPriorite)
                    .thenComparing(Action::getNumAction)
                    .thenComparing(Action::getNumJoueur));

        }
    }

    public ArrayList<Action> getListeActions() {
        return this.listeActions;
    }

    /**
     * Remet a zero la liste des actions
     */
    public void resetListeActions() {
        this.listeActions.clear();
    }

    /**
     * Permet de regler l'affichage en fct de l'action a effectuer
     *
     * @param numeroAction
     */
    public void phase2(int numeroAction) {
        this.phaseActuelle = 2;
        if (numeroAction > 8) {
            this.phaseActuelle = 3;
            this.FenetrePrincipale.setCompteurJoueur(0);
            this.moment = Moment.Scoring;
        } else {
            Action action = listeActions.get(numeroAction);//On recupere l'action a effectuer
            switch (action.getNumAction()) {
                case 1://Si c'est develeppement
                    this.moment = Moment.Developpement;
                    break;
                case 2: //Si c'est Exploration
                    this.moment = Moment.Exploration;
                    break;
                case 3: //Si c'est attaque
                    this.moment = Moment.Attaque;
                    break;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Vérifie la durabilité du jeu en fonction des vaisseaux présents sur le
     * plateau. Cette méthode parcourt chaque hexagone du plateau et effectue
     * des actions de durabilité en supprimant des vaisseaux si nécessaire pour
     * assurer un équilibre entre les points et le nombre de vaisseaux.
     *
     * Pour chaque hexagone, si un contrôleur est présent, un vaisseau est
     * supprimé jusqu'à ce que les points de l'hexagone ne dépassent pas le
     * nombre de vaisseaux autorisés.
     *
     * Elle affiche des informations sur les hexagones contrôlés et les actions
     * effectuées lors de cette vérification.
     *
     * @note Cette méthode répond à une règle du jeu mais n'a pas été appliqué
     * pour le moment. Elle nécessite des modifications
     */
    public void sustainable() {
        for (Hexagone[] hex : this.instance.plateauJeu) {
            for (Hexagone hexa : hex) {
                if (hexa.getControler() != null) {
                    do {
                        hexa.delVaisseaux(hexa.getVaisseaux().getLast());
                    } while (hexa.getPoints() + 1 > hexa.getNbVaisseaux());
                }
            }
        }
    }

    /**
     * Permet de gerer la phase de scoring
     *
     * @param numJoueur
     * @param Xsecteur La coordonnée x du secteur que le joueur veut scorer
     * développé.
     * @param Ysecteur La coordonnée y du secteur que le joueur veut scorer
     * @throws ExceptionScoring Si un problème lié au scoring survient.
     */
    public void scoring(int numJoueur, int Xsecteur, int Ysecteur) throws ExceptionScoring {
        Joueur joueur = this.getInstance().listeJoueurs.get(numJoueur);
        int numSecteur = 3 * Ysecteur + Xsecteur % 3 + 1;
        Secteur secteur = this.getInstance().getMap2().get(numSecteur);
        this.phaseActuelle = 3;

        if (secteur.isEstOccupe() && !secteur.getAEteCompte() && secteur.getType() != Type.TRITIME) { //secteur occupé mais pas déjà compté et n'est pas le tritime
            this.scorerSecteur(secteur);
        } else if (!secteur.isEstOccupe()) {
            ExceptionScoring e = new ExceptionScoring("Ce secteur n'est pas occupe");
            throw e;
        } else if (secteur.getAEteCompte()) {
            ExceptionScoring e = new ExceptionScoring("Ce secteur a deja ete comptabilise");
            throw e;
        } else if (secteur.getType() == Type.TRITIME) {
            ExceptionScoring e = new ExceptionScoring("Le secteur TriTime n'est pas selectionnable");
            throw e;
        }
        if (!this.instance.secteurDispo()) {
            ExceptionScoring e = new ExceptionScoring("Plus de secteur disponible");
            throw e;
        }

    }

    /**
     * Vérifie s'il existe des secteurs occupés qui n'ont pas encore été
     * comptabilisés.
     *
     * Cette méthode parcourt la liste des secteurs, identifie ceux qui sont
     * occupés, puis vérifie si tous les secteurs occupés ont été scorés. Si un
     * secteur occupé n'a pas été comptabilisé, la méthode retourne `true`,
     * indiquant qu'il reste des secteurs à scorer. Si tous les secteurs occupés
     * ont été comptabilisés, elle retourne `false`.
     *
     * @return `true` si au moins un secteur occupé n'a pas été comptabilisé,
     * sinon `false`.
     */
    public boolean secteurDispo() {
        //initialiser les secteurs occupés
        List<Secteur> SecteursOccupes = new ArrayList<Secteur>();
        for (Secteur secteur : this.instance.listeSecteurs) {
            if (secteur.isEstOccupe()) {
                SecteursOccupes.add(secteur);
            }
        }
        //parcourir les secteurs occupés et vois si tous on été scoré
        for (Secteur secteur : SecteursOccupes) {
            if (!secteur.getAEteCompte()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permet de faire le scoring d'un secteur (sans conditions)
     *
     * @param secteur
     */
    public void scorerSecteur(Secteur secteur) throws ExceptionScoring {

        secteur.setAEteCompte(true);
        //parcourir tous les hexagones du secteur choisi par le joueur
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 2; y++) {
                Hexagone hex = secteur.getHexagone(x, y);
                //donner le points correspondants à chaque joueurs qui controles ces hexagones
                if (hex.getControler() != null) {
                    int anciensPoints = hex.getControler().getNbPoints();
                    System.out.println(hex.getPoints() + "points");
                    hex.getControler().setNbPoints(anciensPoints + hex.getPoints());//incrémenter le nombre de points
                }

            }
        }
    }

    /**
     * Permet de faire le dernier scoring de la partie
     *
     * @param secteur
     */
    public void dernierScoring() {
        for (int i = 1; i < 10; i++) {//On parcours chaque secteur
            Secteur secteur = this.getInstance().getMap2().get(i);
            if (secteur.getType() != Type.TRITIME) { //Si ce n'est pas le tritime
                //parcourir tous les hexagones du secteur
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 2; y++) {
                        Hexagone hex = secteur.getHexagone(x, y);
                        //donner le double des points correspondants à chaque joueurs qui controles les hexagones
                        if (hex.getControler() != null) {
                            System.out.println(hex.getPoints());
                            int anciensPoints = hex.getControler().getNbPoints();
                            System.out.println(hex.getPoints());
                            hex.getControler().setNbPoints(anciensPoints + hex.getPoints() * 2);//incrémenter le nombre de points
                        }

                    }
                }
            }
        }
        //On ajoute 6 points au joueur qui possede le tritime
        if (this.getInstance().getJoueurTriTime() != -1) { //Si un joueur a le tri time
            Joueur joueurTriTime = this.getInstance().listeJoueurs.get(this.getInstance().getJoueurTriTime());
            int anciensPoints = joueurTriTime.getNbPoints();
            joueurTriTime.setNbPoints(anciensPoints + 6); //On lui ajoute 6 points
        }

    }

    /**
     * Méthode qui permet d'effectue le classement, et l'affiche.
     *
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'exécution de la méthode.
     * @throws FontFormatException Si un problème lié au format de police
     * survient.
     *
     */
    public void effectuerClassement() {
        List<Joueur> classement = new ArrayList<>();
        classement.add(this.instance.listeJoueurs.get(0));
        classement.add(this.instance.listeJoueurs.get(1));
        classement.add(this.instance.listeJoueurs.get(2));
        //trier la liste en fonction des points des joueurs en utilisant une reference de methode
        classement.sort(Comparator.comparingInt(Joueur::getNbPoints).reversed());

        //affichage du classement ! 
        try {
            FenetreClassement f = new FenetreClassement(classement.get(0).getNom(), classement.get(0).getNbPoints(), classement.get(1).getNom(), classement.get(1).getNbPoints(), classement.get(2).getNom(), classement.get(2).getNbPoints());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Problème d'affichage rencontré, voici le classement final :");
            System.out.println("Le grand gagnant est : " + classement.get(0).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
            System.out.println("Suivi de : " + classement.get(1).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
            System.out.println("Et : " + classement.get(2).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
        }
    }

    /**
     * Méthode qui permet d'executer les différentes étapes/phases du jeu.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'exécution de la méthode.
     * @throws FontFormatException Si un problème lié au format de police
     * survient.
     */
    public void deroulementPhases() throws IOException, FontFormatException {

        while ((this.instance.tourActuel <= 9) && (this.instance.listeJoueurs.get(0).getNbVaisseaux() > 0) && (this.instance.listeJoueurs.get(1).getNbVaisseaux() > 0) && (this.instance.listeJoueurs.get(2).getNbVaisseaux() > 0)) { //vérifier si un joueur a perdu tous ses vaisseaux

            Phase phase2 = new Phase(2, 0, this.instance.listeJoueurs.get(0), this.instance.listeJoueurs.get(1), this.instance.listeJoueurs.get(2)); // phase 2

            setChanged();
            notifyObservers();

            System.out.println("\n---------------------------\nPHASE 3");
            phaseActuelle = 3;
            for (int j = 0; j < 3; j++) {
                Phase phase3 = new Phase(3, j, this.instance.listeJoueurs.get(0), this.instance.listeJoueurs.get(1), this.instance.listeJoueurs.get(2)); // phase 3 de planification
            }
            setChanged();
            notifyObservers();
            tourActuel++;
        }
        if ((this.instance.tourActuel > 9) || (this.instance.listeJoueurs.get(0).getNbVaisseaux() < 0) || (this.instance.listeJoueurs.get(1).getNbVaisseaux() < 0) || (this.instance.listeJoueurs.get(2).getNbVaisseaux() < 0)) {
            System.out.println("------------ La partie est terminée !!! ------------");
            if (this.instance.tourActuel < 9) {
                for (int i = 0; i < 2; i++) {
                    if (this.instance.listeJoueurs.get(i).getNbVaisseaux() < 0) {
                        System.out.println(this.instance.listeJoueurs.get(i).getNom() + " n'a plus de vaisseaux !");
                    }
                }
            }

        }

        List<Joueur> classement = new ArrayList<>();
        classement.add(this.instance.listeJoueurs.get(0));
        classement.add(this.instance.listeJoueurs.get(1));
        classement.add(this.instance.listeJoueurs.get(2));
        //trier la liste en fonction des points des joueurs en utilisant une reference de methode
        classement.sort(Comparator.comparingInt(Joueur::getNbPoints).reversed());

        //affichage du classement ! 
        try {
            FenetreClassement f = new FenetreClassement(classement.get(0).getNom(), classement.get(0).getNbPoints(), classement.get(1).getNom(), classement.get(1).getNbPoints(), classement.get(2).getNom(), classement.get(2).getNbPoints());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Problème d'affichage rencontré, voici le classement final :");
            System.out.println("Le grand gagnant est : " + classement.get(0).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
            System.out.println("Suivi de : " + classement.get(1).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
            System.out.println("Et : " + classement.get(2).getNom() + " avec " + classement.get(0).getNbPoints() + " points.");
        }

        this.instance.tourActuel++;
    }

    /**
     * Sauvegarde l'état actuel de la partie dans un fichier sérialisé.
     *
     * Cette méthode sérialise l'objet `instance` et l'enregistre dans un
     * fichier nommé "sauvegarde_partie.ser" dans le répertoire "AUTRES
     * RESSOURCES". Si une erreur d'entrée/sortie se produit pendant la
     * sérialisation ou l'écriture du fichier, une exception de type
     * `IOException` est lancée.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de
     * l'écriture du fichier de sauvegarde.
     *
     * @note La sauvegarde peut se faire seulement entre chaque tour
     */
    public void sauvegarderPartie() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AUTRES RESSOURCES" + File.separator + "sauvegarde_partie.ser"))) {
            oos.writeObject(this.instance); // Sérialise l'objet instance
            System.out.println("Partie sauvegardée dans le fichier : " + "AUTRES RESSOURCES" + File.separator + "sauvegarde_partie.ser");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Charge et restaure l'état d'une partie à partir d'un fichier de
     * sauvegarde sérialisé.
     *
     * Cette méthode tente de charger l'objet `Partie` sérialisé à partir du
     * fichier de sauvegarde situé dans le répertoire "AUTRES RESSOURCES". Si la
     * partie est correctement chargée, elle met à jour l'interface utilisateur
     * en créant une nouvelle fenêtre et en appelant la méthode `updateFenetre`.
     * En cas d'erreur lors du chargement du fichier (fichier non trouvé, erreur
     * de désérialisation, etc.), un message d'erreur est affiché.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
     * lecture du fichier de sauvegarde.
     * @throws ClassNotFoundException Si la classe de l'objet sérialisé ne peut
     * pas être trouvée pendant la désérialisation.
     * @throws FontFormatException Si un problème de format de police survient
     * lors de la mise à jour de l'interface.
     */
    public void commencerPartieSauvegardee() throws IOException, ClassNotFoundException, FontFormatException {
        File fichierSauvegarde = new File("AUTRES RESSOURCES" + File.separator + "sauvegarde_partie.ser");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichierSauvegarde))) {
            this.instance = (Partie) ois.readObject(); // Désérialise l'objet
            System.out.println("Partie chargée : " + this.instance);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la partie sauvegardée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        if (fichierSauvegarde.exists()) {
            this.FenetrePrincipale = new Fenetre(this);
            this.FenetrePrincipale.updateFenetre();
        }

    }

    public static void main(String[] args) throws IOException {
        Partie partie = new Partie();
        FenetreMenu menu = new FenetreMenu(partie);

    }
}

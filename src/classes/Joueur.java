package classes;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cette classe représente un joueur. Elle permet de mettre à jour les éléments
 * que possède le joueur.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Joueur implements Serializable {

    /**
     * Numéro du joueur
     */
    private int numero;
    /**Èø
     * Nom du joueur
     */
    private String nom;
    /**
     * Couleur du joueur (Rouge, Bleu Jaune)
     */
    private classes.Couleur couleur;
    /**
     * Type du joueur : Viturelle ou Humain
     *
     * @note Un joueur virtuel ne choisis pas l'ordre de ses cartes, celui-ci
     * sera automatique en fonction de sa stratégie adoptée. Une mise à jour et
     * des modif peuvent etre ajouter afin de rendre réelemment autonome un
     * joueur virtuel. Notamment pour le choix des zones.
     */
    private classes.TypeJoueur type;
    /**
     * Nombre de vaisseaux que le joueur a encore ( initialisé à 15)
     */
    private int nbVaisseaux;
    /**
     * Nombre de points du joueur (initialisé à 0)
     */
    private int nbPoints;
    /**
     * stratégie adoptée par le joueur s'il est virtuel notamment
     *
     * @see #type
     */
    private Strategie strategie;

    /**
     * Liste des 3 cartes du joeuuer. Instance de type ArrayList contenant des
     * objets de types {@link Carte}
     */
    private List<classes.Carte> listeCartes = new ArrayList<classes.Carte>();
    /**
     * Liste des planetes controlees par le joueur. Instance de type ArrayList
     * contenant des objets de types {@link Hexagone}
     */
    private List<Hexagone> listePlanetesControlees = new ArrayList<Hexagone>();
    /**
     * Liste vaisseaux encore vivants du joeuuer. Instance de type ArrayList
     * contenant des objets de types {@link Vaisseau}
     */
    private List<classes.Vaisseau> listeVaisseaux = new ArrayList<classes.Vaisseau>();

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Joueur}
     *
     * @param Type
     * @param Nom
     * @param Couleur
     */
    public Joueur(classes.TypeJoueur Type, String Nom, classes.Couleur Couleur, int numero) {
        this.nom = Nom;
        this.type = Type;
        this.couleur = Couleur;
        this.nbVaisseaux = 15;
        this.nbPoints = 0;
        this.numero = numero;

        this.listeCartes.add(new classes.Developpement(this, 0));
        this.listeCartes.add(new classes.Exploration(this, 0));
        this.listeCartes.add(new classes.Attaque(this, 0));

        for (int i = 0; i < 16; i++) {
            this.listeVaisseaux.add(new Vaisseau(this));
        }
    }

    /**
     * Définit le type d'un joueur
     *
     * @param Type le type du joueur qui une valeur de type Type.
     */
    public void setType(classes.TypeJoueur Type) {
        this.type = Type;
    }

    /**
     * Obtient le type du joueur
     *
     * @return Une valeur de type Type.
     */
    public classes.TypeJoueur getType() {
        return this.type;
    }

    /**
     * Obtient le numero du joueur
     *
     * @return Une valeur de type Entier.
     */
    public int getNumero() {
        return this.numero;
    }

    /**
     * Obtient le nom du joueur
     *
     * @return Une chaine de caractères qui est le nom du joueur.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Définit le nom du joueur entrée en ligne de commande.
     * @see #setNom(String)
     */
    public void setNom() {
        Scanner scan = new Scanner(System.in);
        // Sélection du nom
        System.out.println("    Entrez votre nom :");
        String nom = scan.nextLine();
        this.nom = nom;
    }

    /**
     * Définit le nom du joueur.
     *
     * @param Nom Le nom du joueur.
     * @see #setNom()
     */
    public void setNom(String Nom) {
        this.nom = Nom;
    }

    /**
     * Obtient la couleur du joueur
     *
     * @return Une valeur de type Couleur.
     */
    public classes.Couleur getCouleur() {
        return this.couleur;
    }

    /**
     * Définit la couleur des vaisseaux du joueur.
     *
     * @param Couleur Le couleur de type Couleur.
     */
    public void setCouleur(classes.Couleur Couleur) {
        this.couleur = Couleur;
    }

    /**
     * Obtient le nombre de vaisseaux que possède la joueur.
     *
     * @return Un entier pour le nombre de vaisseaux possédé par le joueur.
     */
    public int getNbVaisseaux() {
        return this.nbVaisseaux;
    }

    /**
     * Met à jour le nombre de vaisseaux en décrémentant le nombre de vaisseaux
     * que le joueur possède
     *
     * @param N entier nombre de vaisseau que le joueur perd durant l'attaque
     */
    public void perdreVaisseaux(int N) {
        this.nbVaisseaux -= N;
    }

    /**
     * Met à jour le nombre de vaisseaux que possède le joueur.
     */
    public void setNbVaisseaux(int nbVaisseaux) {
        this.nbVaisseaux = nbVaisseaux;
    }

    /**
     * Obtient le nombre de points que possède le joueur.
     *
     * @return Une chaine de caractères qui est le nom du joueur.
     */
    public int getNbPoints() {
        return this.nbPoints;
    }

    /**
     * Met à jour le nombre de points que possède le joueur.
     */
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getPrioriteDeveloppement() {
        return this.listeCartes.get(0).getPriorite();
    }

    public Developpement getCarteDeveloppement() {
        return (Developpement) this.listeCartes.get(0);
    }

    public Exploration getCarteExploration() {
        return (Exploration) this.listeCartes.get(1);
    }

    public Attaque getCarteAttaque() {
        return (Attaque) this.listeCartes.get(2);
    }

    public void initCartes() {
        this.listeCartes.get(0).setPriorite(0);
        this.listeCartes.get(1).setPriorite(0);
        this.listeCartes.get(2).setPriorite(0);
    }

    public void setPrioriteDeveloppement(int P) {
        this.listeCartes.get(0).setPriorite(P);
    }

    public int getPrioriteExploration() {
        return this.listeCartes.get(1).getPriorite();
    }

    public void setPrioriteExploration(int P) {
        this.listeCartes.get(1).setPriorite(P);
    }

    public int getPrioriteAttaque() {
        return this.listeCartes.get(2).getPriorite();
    }

    public void setPrioriteAttaque(int P) {
        this.listeCartes.get(2).setPriorite(P);
    }

    public Vaisseau getVaisseauDisponible() {
        for (int i = 0; i < this.listeVaisseaux.size(); i++) {
            if (listeVaisseaux.get(i).getPositionVaisseau() == null) {
                return listeVaisseaux.get(i);
            }
        }
        return null;
    }

    /**
     * Methode qui permet de chosir l'ordre d'utilisation des cartes durant le
     * tour Elle utilise la méthode du même nom de la classe Stratégie
     *
     * @throws ExceptionChoixOrdre
     */
    public void choixOrdre(int prioriteDeveloppement, int prioriteExploration, int prioriteAttaque) throws ExceptionChoixOrdre {
        this.strategie.choixOrdre(this, prioriteDeveloppement, prioriteExploration, prioriteAttaque);
    }

    /**
     * Methode qui permet au joueur de choisir une zone qu'il possède pour les
     * utiliser pour une attaque, une exploration ou un développement
     *
     * @return renvoi l'Hexagone choisi
     * @see #choisirZone()
     */
    public Hexagone choisirZone(int xSecteur, int ySecteur, int xHexa, int yHexa) {

        int numSecteur = 3 * ySecteur + xSecteur % 3 + 1;
        int x = yHexa % 3;
        int y = xHexa % 2;

        Secteur secteur = Partie.getInstance().getMap2().get(numSecteur);
        return secteur.getHexagone(x, y);
    }

     /**
     * Methode temporaire pour la selection d'un autre hexagone en ligne de commande pour l'exploration et l'attaque
     * @return renvoi l'Hexagone choisi
     * @see #choisirZone(int, int, int, int)
     */
    public Hexagone choisirZone() {
        Scanner scan = new Scanner(System.in);
        
        int numSecteur = 0;
        int x = 0;
        int y = 0;

        do {
            System.out.println("Quel secteur ? \n1|2|3\n4|5|6\n7|8|9 ");
            numSecteur = scan.nextInt();
            if (numSecteur > 9 || numSecteur < 1) {
                System.out.println("Valeur non valide, veuillez réessayer.");
            }
        } while (numSecteur > 9 || numSecteur < 1);

        do {
            System.out.println("     Quel hexagone ? coordonnée x (1,2 ou 3) ? ");
            x = scan.nextInt();
            if (x > 3 || x < 1) {
                System.out.println("Valeur non valide, veuillez réessayer.");
            }
        } while (x > 3 || x < 1);

        do {
            System.out.println("     Quel hexagone ? coordonnée y (1 ou 2) ? ");
            y = scan.nextInt();
            if (y > 2 || y < 1) {
                System.out.println("Valeur non valide, veuillez réessayer.");
            }
        } while (y > 2 || y < 1);

        Secteur secteur = Partie.getInstance().getMap2().get(numSecteur);

        return secteur.getHexagone(x - 1, y - 1);

    } 

    
    /**
     * Methode qui de vérifier si un joueur possède le tritime
     * @return boolean
     */
    public boolean possedeTritime() {
        for (Hexagone hexagone : this.listePlanetesControlees) {
            if (hexagone.getSecteur().getType() == Type.TRITIME) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Obtient la liste des cartes du joueur
     *
     * @return Une collection de type list avec les cartes du joueur.
     */
    public List<classes.Carte> getListeCartes() {
        return listeCartes;
    }

    public Strategie getStrategie() {
        return this.strategie;
    }

    public void setStrategie(String Strategie) {
        if (this.type == TypeJoueur.HUMAIN) {
            this.strategie = new StrategieHumain(this);
        } else { //joueur virtuel
            if (Strategie.equals("Agressive")) {
                this.strategie = new StrategieAgressive(this);
            } else if (Strategie.equalsIgnoreCase("Defensive") || Strategie.equalsIgnoreCase("Défensive")) {
                this.strategie = new StrategieDefensive(this);
            } else {
                System.out.println("Type non valide, veuillez réessayer.");
            }

        }

    }

    /**
     * Obtient la liste des coordonnées contrôlées par le joueur.
     *
     * @return Une collection de type list avec les coordonnées des hexagones
     * contrôlés.
     */
    public List<Hexagone> getListePlanetesControlees() {
        return listePlanetesControlees;
    }

    /**
     * Met à jour la liste des coordonnées contrôlées par le joueur.
     */
    public void addListePlanetesControlees(classes.Hexagone planete) {
        this.listePlanetesControlees.add(planete);
    }

    /**
     * Met à jour la liste des coordonnées contrôlées par le joueur.
     */
    public void delListePlanetesControlees(classes.Hexagone planete) {
        this.listePlanetesControlees.remove(planete);
    }

    /**
     * Obtient la liste des vaisseaux du joueur
     *
     * @return Une collection de type list avec les vaisseaux que possède le
     * joueur.
     */
    public List<classes.Vaisseau> getListeVaisseaux() {
        return listeVaisseaux;
    }

    /**
     * Met à jour la liste des vaisseaux que possède le joueur en enlevant un
     * vaisseau mort.
     *
     * @param vaisseau Vaisseau mort durant l'attaque
     * @throws FontFormatException
     * @throws IOException
     */
    public void perdreVaisseaux(classes.Vaisseau vaisseau) throws IOException, FontFormatException {
        this.listeVaisseaux.remove(vaisseau);
        this.nbVaisseaux--; // décrémenter le nombre de vaisseau vivant
        //actualiser la fenetre
        Partie.getInstance().getFenetrePrincipale().updateFenetre();

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("\n**************************************************************** \n");
        sb.append(this.nom + " est un joueur " + this.type + ". ");
        if (this.type == TypeJoueur.VIRTUEL) {
            sb.append("Il a choisi la stratégie " + this.strategie + ".");
        }

        sb.append(" Il possède les vaisseaux " + this.couleur.toString3() + ". \n");
        sb.append("Il a " + this.nbVaisseaux + " vaisseaux et " + this.nbPoints + " points.");
        sb.append("\n**************************************************************** \n");
        return sb.toString();
        //ajouter les zones contrôlées
    }

}

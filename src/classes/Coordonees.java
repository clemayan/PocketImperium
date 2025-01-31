package classes;
//importation des bibliothèques :

import java.io.Serializable;
import java.util.Random;

/**
 *
 * Cette classe représente les coordonnées d'un secteur.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Coordonees implements Serializable {

    /**
     * Coordonnée X du secteur
     */
    private int positionX;

    /**
     * Coordonnée Y du secteur
     */
    private int positionY;

    /**
     * Secteur associé aux coordonnées x et y
     */
    private Secteur secteur;

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Coordonees}
     */
    public Coordonees() {
        //Prépare les coordonnées en (0;0) en attente d'une affectation
        this.positionX = 0;
        this.positionY = 0;
    }

    /**
     * Définit les coordonnées d'un secteur TriTime.
     *
     * @param secteurs La matrice des coordonées déjà prises par un secteurs.
     */
    public void setTriTime(int[][] secteurs) {
        //Change les coordonnées en coordonnée d'une carte TriTime (2;2)
        this.positionX = 1;
        this.positionY = 1;
        secteurs[1][1] = 1;

    }

    /**
     * Définit les coordonnées d'un secteur Cote.
     *
     * @param secteurs La matrice des coordonées déjà prises par un secteurs.
     */
    public void setCote(int[][] secteurs) {
        //Change les coordonnées en coordonnée d'une carte Cote (x;1) avec x compris entre 0 et 
        //génrération aléatoire de x en vérifiant que la coordonnées est libre
        int y;
        do {
            Random random = new Random();
            y = random.nextBoolean() ? 0 : 2; //choix entre 0 et 2
        } while (secteurs[y][1] != 0);

        this.positionX = 1;
        this.positionY = y;
        secteurs[y][1] = 1; //mise à jour du tableau
    }

    /**
     * Définit les coordonnées d'un secteur Bord.
     *
     * @param secteurs La matrice des coordonées déjà prises par un secteurs.
     */
    public int[][] setBord(int[][] secteurs) {
        // Change les coordonnées en coordonnée d'une carte Bord (x;y)avec y=1 ou y=3 et x=1,2 ou 3
        int x;
        int y;
        do {
            Random random = new Random();
            y = random.nextInt(3);
            x = random.nextBoolean() ? 0 : 2; //choix entre 0 et 2
        } while (secteurs[y][x] != 0);

        this.positionX = x;
        this.positionY = y;
        secteurs[y][x] = 1; //mise à jour du tableau
        return secteurs;
    }

    /**
     * Retourne le secteur 
     * @return
     */
    public Secteur getSecteur() {
        return this.secteur;
    }

    /**
     * Met à jour le secteur qui se trouve aux coordonnée x et y
     */
    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    /**
     * Renvoie la valeur de x
     */
    public int getPositionX() {
        return this.positionX;
    }

    /**
     * Renvoie la valeur de y
     */
    public int getPositionY() {
        return this.positionY;
    }

    /**
     * change la valeur de x
     *
     * @param x .
     */
    public void setPositionX(int x) {
        this.positionX = x;
    }

    /**
     * change la valeur de y
     *
     * @param y .
     */
    public void setPositionY(int y) {
        this.positionY = y;
    }

    /**
     * Méthode qui détermine si 2 secteurs ont la même position
     *
     * @param o Objet dont on compare les coordonnées
     * @return renvoi true si les deux objets ont les mêmes coordonnées, false
     * sinon
     */
    public boolean equals(Object o) {
        if (o instanceof Coordonees) {
            Coordonees obj = (Coordonees) o;
            return (obj.positionX == this.positionX) & (obj.positionY == this.positionY);
        } else {
            return false;
        }
    }

    /**
     * Affichage des coordonnées.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + this.positionX + ";" + this.positionY + ")");
        return sb.toString();
    }

    public static void main(String[] args) {
        //TEST DES METHODES
        Coordonees abc = new classes.Coordonees();
        System.out.println(abc);
    }

}

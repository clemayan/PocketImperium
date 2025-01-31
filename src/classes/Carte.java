package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Cette classe représente une carte du jeu. Elle permet de mettre à jour les
 * attributs de celle-ci.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Carte implements Serializable {

    /**
     * Joueur de type {@link Joueur}associé à la carte
     */
    protected classes.Joueur joueur;
    /**
     * numéro de priorité de la carte (de 0 à 2)
     */
    protected int priorite;
    /**
     * nombre de joueur ayant choisi chaque la carte
     * @see Partie#nbJoueursCarte 
     */
    protected List<Integer> nbJoueurs = new ArrayList<Integer>();

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Carte}
     *Initialise le nombre de joueur ayant choisi chaque la carte à 0
     * @param joueur le joueur qui possède la carte.
     * @param priorite la position de la carte dans l'ordre d'execution des
     * cartes du joueur.
     */
    public Carte(classes.Joueur joueur, int priorite) {
        this.joueur = joueur;
        this.priorite = priorite;
        this.nbJoueurs.add(0);
        this.nbJoueurs.add(0);
        this.nbJoueurs.add(0);
    }

    /**
     * Obtient la priorité de la carte.
     *
     * @return la priorité de la carte.
     */
    public int getPriorite() {
        return this.priorite;
    }

    /**
     * Définit la nouvelle priorité de la carte.
     *
     * @param priorite la nouvelle valeur de priorité.
     */
    public void setPriorite(int priorite) {
        this.priorite = priorite;
        if (priorite != 0) { //sinon c'est l'initalisation des cartes à chaque tour
            this.incrementNbJoueur(priorite); // incrémenter le nombre de joueur ayant choisi la carte en priorité = priorite
        }
    }

    /**
     * Obtient le joueur la carte.
     *
     * @return le joueur qui possède la carte en question.
     */
    public classes.Joueur getJoueur() {
        return this.joueur;
    }

    /**
     * Définit le joueur la carte.
     *
     * @param priorite le joueur qui possède la carte.
     */
    public void setJoueur(classes.Joueur joueur) {
        this.joueur = joueur;
    }

    /**
     * Obtient la liste contenant le nombre de joueurs par priorité.
     *
     * @return une liste des nombres de joueurs pour chaque priorité.
     */
    public List<Integer> getNbJoueurs() {
        return this.nbJoueurs;
    }

    /**
     * Incrémente le nombre de joueurs pour une priorité donnée.
     *
     * @param priorite la priorité pour laquelle le nombre de joueurs doit être
     * incrémenté. La priorité est interprétée comme un indice (priorite - 1).
     */
    public void incrementNbJoueur(int priorite) {
        this.nbJoueurs.set(priorite - 1, this.nbJoueurs.get(priorite - 1)); //incrémente la valeur à l'indice priorité-1

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" de " + this.joueur.getNom()); //peut-être enlever ??
        sb.append(" choisie en position ");
        sb.append(this.priorite);
        return sb.toString();
    }

    public static void main(String[] args) {

    }
}

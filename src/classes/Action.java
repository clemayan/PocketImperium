package classes;

import java.io.Serializable;
/**
*
* Cette classe représente une action pour un tour. Elle permet de pouvoir 
* classer les actions d'un tour en fonction de leurs caractéristiques et 
* des critères definis dans les règles du jeu
*
* @author Maya Mazuet et Aurélien Musset
* @version 1.0
*/
public class Action implements Serializable {

    /**
     * Joueur associé à l'action de type {@link Joueur}
     */
    private Joueur joueur;
    /**
     * chaine de caractères : nom de l'action donc de la carte
     */
    private String action;
    /**
     * numéro de priorité de l'action (de 0 à 8)
     */
    private int priorite;
    /**
     * numéro de l'ordre de l'action en fonction du type de la carte qui agit
     */
    private int numOrdre;

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Action}
     * initialise le numér de l'ordre de l'action en fonction du type de la
     * carte
     *
     * @param joueur
     * @param action
     * @param priorite
     */
    public Action(Joueur joueur, String action, int priorite) {
        this.joueur = joueur;
        this.action = action;
        this.priorite = priorite;
        switch (action) {
            case "Developpe":
                this.numOrdre = 1;
                break;
            case "Explore":
                this.numOrdre = 2;
                break;
            case "Attaque":
                this.numOrdre = 3;
                break;
        }
    }

    /**
     * Retourne le numéro du joueur
     *
     * @return
     */
    public int getNumJoueur() {
        return joueur.getNumero();
    }

    /**
     * Retourne le numéro de l'action
     *
     * @return entier numOrdre
     */
    public int getNumAction() {
        return this.numOrdre;
    }

    /**
     * Retourne l'action
     *
     * @return chaine de caractères : titre de la carte
     */
    public String getAction() {
        return action;
    }

    /**
     * Retourne le numéro de priorité de la carte
     *
     * @return entier priorite
     */
    public int getPriorite() {
        return this.priorite;
    }

    /**
     * Retourne l'intitulé de l'action
     *
     * @return chaine de caractère qui décrit le joueur et son action
     */
    public String getIntitule() {
        return this.joueur.getNom() + " " + this.action;
    }

    
    /**
     * Renvoie un texte compréhensible des attributs de l'objet
     */
    public String toString() {
        return "Joueur " + joueur.getNom() + " " + action + " (Priorité " + priorite + ")";
    }
}

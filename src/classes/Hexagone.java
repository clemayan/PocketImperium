package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Cette classe représente les differents hexagones du plateau
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Hexagone implements Serializable {

    /**
     * Coordonnée X de l'hexagone
     */
    private int x;
    /**
     * Coordonnée Y de l'hexagone
     */
    private int y;
    /**
     * Secteur associé à l'Hexagone
     */
    private Secteur secteur;
    /**
     * Dit si l'Hexagone est occupé
     */
    private boolean estControle;
    /**
     * Joueur qui contrôle l'hexagone
     */
    private Joueur controler;
    /**
     * Le nombre de vaisseaux présents sur l'hexagone
     */
    private int nbVaisseaux;
    /**
     * le nombre de points associé à l'hexagone
     */
    private int points;

    /**
     * Liste des hexagones voisins directs de hexagone. Instance de type
     * ArrayList contenant des objets de type {@link Hexagone}
     */
    private List<Hexagone> voisins = new ArrayList<Hexagone>();
    /**
     * Liste des hexagones attegnables depuis hexagone. Instance de type
     * ArrayList contenant des objets de type {@link Hexagone}
     */
    private List<Hexagone> attegnables = new ArrayList<Hexagone>();
    /**
     * L'ensemble des vaisseaux qui sont sur l'Hexagone .Instance de type
     * ArrayList contenant des objets de type {@link Vaisseau}
     */
    private List<Vaisseau> vaisseaux = new ArrayList<Vaisseau>();

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Hexagone}
     *
     *
     * @param x coordonée x
     * @param y coorodnnée y
     * @param secteur secteur auqel l'hexagone appartient
     * @param points le nombre de point de la plene
     */
    public Hexagone(int x, int y, Secteur secteur, int points) {
        this.x = x;
        this.y = y;
        this.estControle = false;
        this.controler = null;
        this.secteur = secteur;
        this.points = points;
    }

    // Getters et setters
    /**
     * Obtient la coordonnée x de l'hexagone.
     *
     * @return la valeur de x
     */
    public int getX() {
        return x;
    }

    /**
     * Met à jour la valeur de x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtient la coordonnée y de l'hexagone.
     *
     * @return la valeur de y
     */
    public int getY() {
        return y;
    }

    /**
     * Met à jour la valeur de y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retourne le nombre de points de l'Hexagone
     *
     * @return
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Met à jour le nombre de points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Retourne le secteur associé
     *
     * @return
     */
    public Secteur getSecteur() {
        return this.secteur;
    }

    /**
     * Met à jour le secteur
     */
    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public void setEstControle(boolean controle) {
        this.estControle = controle;
    }

    public boolean getEstControle() {
        return this.estControle;
    }

    /**
     * Met à jour le joueur qui controle l'hexagone
     */
    public void setControler(Joueur controler) {
        this.controler = controler;
    }

    /**
     * Renvoi le joueur qui contrôle l'hexagone
     *
     * @return le joueur
     */
    public Joueur getControler() {
        return this.controler;
    }

    /**
     * Renvoi le nombre de vaisseaux présents sur l'hexagone
     *
     * @return un enier
     */
    public int getNbVaisseaux() {
        this.nbVaisseaux = this.vaisseaux.size();
        return this.nbVaisseaux;
    }

    /**
     * Determine si deux Hexagones ont les mêmes coordonnées
     *
     * @return un booleen : true si les deux hexagones sont égaux, false sinon
     */
    public boolean equals(Hexagone o) {
        if (o instanceof Hexagone) {
            Hexagone obj = (Hexagone) o;
            return (obj.x == this.x) && (obj.y == this.y) && (obj.secteur == this.secteur);
        } else {
            return false;
        }
    }

    /**
     * Renvoi une liste des vaisseaux présent sur l'hexagone
     *
     * @return liste conteant les vaisseaux
     */
    public List<Vaisseau> getVaisseaux() {
        for (int i = 0; i < this.controler.getListeVaisseaux().size(); i++) {//parcours les vaisseaux du joueur
            if ((this.equals(this.controler.getListeVaisseaux().get(i).getPositionVaisseau()) && (!this.vaisseaux.contains(this.controler.getListeVaisseaux().get(i))))) { //si le vaisseau est sur l'hexagone
                this.vaisseaux.add(this.controler.getListeVaisseaux().get(i)); //ajoute le vaisseau à la liste des vaisseaux qui sont sur l'hexagone

            }
        }

        //considérer les vaisseaux du tritime tous dans le même hexagone
        if (Arrays.equals(new int[]{3, 3}, this.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, this.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, this.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, this.getPositionHexagone())) {
            if (Arrays.equals(new int[]{3, 3}, this.getPositionHexagone()) == false) {
                for (int i = 0; i < this.secteur.getHexagone(3, 3).getNbVaisseaux(); i++) {
                    if (!this.vaisseaux.contains(this.controler.getListeVaisseaux().get(i))) {
                        this.vaisseaux.add(this.secteur.getHexagone(3, 3).getVaisseaux().get(i));
                    }
                }
            }
            if (Arrays.equals(new int[]{4, 3}, this.getPositionHexagone()) == false) {
                for (int i = 0; i < this.secteur.getHexagone(4, 3).getNbVaisseaux(); i++) {
                    if (!this.vaisseaux.contains(this.controler.getListeVaisseaux().get(i))) {
                        this.vaisseaux.add(this.secteur.getHexagone(4, 3).getVaisseaux().get(i));
                    }
                }
            }
            if (Arrays.equals(new int[]{4, 2}, this.getPositionHexagone()) == false) {
                for (int i = 0; i < this.secteur.getHexagone(4, 2).getNbVaisseaux(); i++) {
                    if (!this.vaisseaux.contains(this.controler.getListeVaisseaux().get(i))) {
                        this.vaisseaux.add(this.secteur.getHexagone(4, 2).getVaisseaux().get(i));
                    }
                }
            }
            if (Arrays.equals(new int[]{5, 3}, this.getPositionHexagone()) == false) {
                for (int i = 0; i < this.secteur.getHexagone(5, 3).getNbVaisseaux(); i++) {
                    if (!this.vaisseaux.contains(this.controler.getListeVaisseaux().get(i))) {
                        this.vaisseaux.add(this.secteur.getHexagone(5, 3).getVaisseaux().get(i));
                    }
                }
            }
        }

        return this.vaisseaux;
    }

    /**
     * Ajoute un vaisseau à la liste des vaisseaux présent sur la zone
     */
    public void delVaisseaux(Vaisseau vaisseau) {

        for (int i = 0; i < this.vaisseaux.size(); i++) {

            if (vaisseau.equals(this.vaisseaux.get(i))) {
                this.vaisseaux.remove(i);
            }
        }
    }

    /**
     * Ajoute un vaisseau à la liste des vaisseaux présent sur la zone
     */
    public void setVaisseaux(Vaisseau vaisseau) {

        this.vaisseaux.add(vaisseau);
    }

    /**
     * Met à jour les voissins de l'hexagone
     */
    public void setVoisins(List<Hexagone> voisins) {
        this.voisins = voisins;
    }

    /**
     * Renvoi une liste conteant les zones atteignables en 2 sauts à partir de
     * l'hexagone
     *
     * @return liste conteant les zones atteignables
     */
    public List<Hexagone> getAttegnables() {
        return attegnables;
    }

    /**
     * Permet d'obtenir la position d'un hexagone sur le plateau de jeu
     *
     * @return tableau contenant la position x et la position y sur le plateau
     * de jeu
     */
    public int[] getPositionHexagone() {
        Hexagone[][] plateau = Partie.getInstance().getPlateauJeu();
        int[] tab = new int[2];
        for (int i = 0; i < plateau.length; i++) { //parcourir les lignes
            for (int j = 0; j < plateau[0].length; j++) { //parcourir les colonnes
                if (plateau[i][j].equals(this)) { //on repère l'hexagone sur le plateau
                    tab[0] = i;
                    tab[1] = j;
                }
            }
        }

        return tab;
    }

    /**
     * Détermine si un hexagone donné est atteignable à partir de cet hexagone.
     * Un hexagone est considéré comme atteignable si :
     * <ul>
     * <li>C'est un voisin direct de cet hexagone.</li>
     * <li>Ou si c'est un voisin de l'un des voisins de cet hexagone.</li>
     * </ul>
     *
     * @param hexa l'hexagone cible à vérifier.
     * @return {@code true} si l'hexagone cible est atteignable, {@code false}
     * sinon.
     */
    public boolean estAtteignable(Hexagone hexa) {
        this.voisins = this.getVoisins();
        if (this.getVoisins().contains(hexa)) { //voisin direct
            return true;
        } else {
            for (int i = 0; i < this.voisins.size(); i++) { //voir si c'est un voisin de voisin
                if (this.voisins.get(i).getVoisins().contains(hexa)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Récupère la liste des hexagones voisins de cet hexagone. La méthode
     * identifie les hexagones adjacents en fonction de leur position sur le
     * plateau de jeu, et inclut des règles spéciales pour des positions
     * particulières (par exemple, la zone centrale du Tritime).
     *
     * @return la liste des hexagones voisins.
     */
    public List<Hexagone> getVoisins() {
        Hexagone[][] plateau = Partie.getInstance().getPlateauJeu();

        int[] positionThis = this.getPositionHexagone();

        System.out.println(positionThis[0] + "x" + positionThis[1] + "y");

        //ajout des hexagones voisins
        if (positionThis[0] != 0) { //pas en haut
            this.voisins.add(plateau[positionThis[0] - 1][positionThis[1]]); //hexagone au dessus
        }
        if (positionThis[0] != 8) { //pas en bas
            this.voisins.add(plateau[positionThis[0] + 1][positionThis[1]]);//hexagone en dessous
        }
        if (positionThis[1] != 0) { //pas à gauche
            this.voisins.add(plateau[positionThis[0]][positionThis[1] - 1]);//hexagone à gauche
        }
        if (positionThis[1] != 5) { //pas à droite
            this.voisins.add(plateau[positionThis[0]][positionThis[1] + 1]);//hexagone à droite
        }

        if (positionThis[0] % 2 == 0) {//hexagone sur ligne impaire
            if (positionThis[1] != 0) { //pas à gauche
                if (positionThis[0] != 0) { //pas en haut
                    this.voisins.add(plateau[positionThis[0] - 1][positionThis[1] - 1]);
                }
                if (positionThis[0] != 8) { //pas en bas
                    this.voisins.add(plateau[positionThis[0] + 1][positionThis[1] - 1]);
                }
            }
        } else {//hexagone sur lgine paire
            if (positionThis[0] != 5) { //pas à droite
                if (positionThis[1] != 0) { //pas à gauche
                    this.voisins.add(plateau[positionThis[0] - 1][positionThis[1] + 1]);
                }
                if (positionThis[0] != 8) { //pas en bas
                    this.voisins.add(plateau[positionThis[0] + 1][positionThis[1] + 1]);
                }

            }
        }

        //ajout des voisins si le joueur est sur la case du milieu du Tritime : composée de 4 cases
        if (Arrays.equals(new int[]{3, 3}, positionThis) || Arrays.equals(new int[]{4, 3}, positionThis) || Arrays.equals(new int[]{4, 2}, positionThis) || Arrays.equals(new int[]{5, 3}, positionThis)) {
            System.out.println("tritime");
            this.voisins.add(this.secteur.getHexagone(0, 1));
            this.voisins.add(this.secteur.getHexagone(1, 1));
            this.voisins.add(this.secteur.getHexagone(1, 0));
            this.voisins.add(this.secteur.getHexagone(2, 1));
        }

        return this.voisins;
    }

    /**
     * Met à jour les zones atteignable en 2 sauts à partir de l'hexagone
     */
    public void setAttegnables(List<Hexagone> attegnables) {
        this.attegnables = attegnables;
    }

    /**
     * Metode qui determine si au moins un vaisseau qui compose l'hexagone peut
     * faire l'action demandé (0 ou 1)
     *
     * @param val 1 pour l'exploration et 2 pour l'attaque
     * @return le nombre de vaisseaux pouvant agir
     */
    public int getNbVaisseauxPeutAgir(int val) {
        int nb = 0;
        int nbVaisseaux = this.getNbVaisseaux();
        for (int i = 0; i < nbVaisseaux; i++) {
            if (val == 1 & this.getVaisseaux().get(i).getAExplore() == false) { //un vaisseau peut explorer
                nb++;
                System.out.println("nb" + nb);
            } else if (val == 2 & this.getVaisseaux().get(i).getAAttaque() == false) {//un vaisseau peut attaquer
                nb++;
            }
        }
        return nb;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("L'hexagone " + "(" + x + ", " + y + ") est controllé par " + this.getControler() + ".\nIl y a " + this.getNbVaisseaux() + " vaisseaux.");
        return sb.toString();
    }

}

package classes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Cette classe représente les vaisseaux Elle permet de
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Vaisseau implements Serializable {

    private classes.Hexagone positionVaisseau;
    private boolean estVivant;
    private classes.Joueur joueur;
    private classes.Secteur secteur;
    private boolean aExplore;
    private boolean aAttaque;

    public Vaisseau(Joueur joueur) {
        this.estVivant = true;
        this.joueur = joueur;
        this.positionVaisseau = null;
        this.secteur = null;
        this.aAttaque = false;
        this.aExplore = false;
    }

    public classes.Hexagone getPositionVaisseau() {
        return positionVaisseau;
    }

    public void setPositionVaisseau(classes.Hexagone positionVaisseau) {
        this.positionVaisseau = positionVaisseau;
        this.secteur = positionVaisseau.getSecteur();
    }

    public boolean isEstVivant() {
        return estVivant;
    }

    public void setEstVivant(boolean estVivant) {
        this.estVivant = estVivant;
        if (estVivant == false) {//vaisseaux mort
            this.positionVaisseau = null;
            this.positionVaisseau = null;
        }

    }

    public boolean getAExplore() {
        return this.aExplore;
    }

    public boolean getAAttaque() {
        return this.aAttaque;
    }

    public void setAExplore(boolean a) {
        this.aExplore = a;
    }

    public void setAAttaque(boolean a) {
        this.aAttaque = a;
    }

    public classes.Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(classes.Joueur joueur) {
        this.joueur = joueur;
    }

    public classes.Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(classes.Secteur secteur) {
        this.secteur = secteur;
    }

    /**
     * Méthode qui permet de déplacer un vaisseau afin d'envahir et de contrôler
     * une nouvelle zone par une exploration ou par une attaque
     *
     * @param anciennePosition Hexagone de l'ancienne position du vaisseau qui
     * se déplace
     * @return renvoi un Hexagone que le vaisseau souhaite envahir
     * @throws ExceptionZone
     */
    public Hexagone deplacerFlotte(Hexagone anciennePosition, int xSecteur, int ySecteur, int xHexa, int yHexa, int val) throws ExceptionZone {

        Scanner scan = new Scanner(System.in);
        boolean zoneValide = false;
        Hexagone coo = new Hexagone(0, 0, null, 0);
        boolean HexagonAtteignable = false;

        if (val == 1) {//exploration
            boolean HexagoneOccupe = true;
            System.out.println("explo");
            do {
                int q = -1;
                int r = -1;

                //System.out.println("secccct"+);
                System.out.println("     La flotte se trouve sur le secteur " + Partie.getInstance().getMap().get(this.secteur) + ".\nChoisir la zone à envahir");
                coo = this.joueur.choisirZone();
                //coo = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa);
// Vérification si la zone est pas null (demi hexagone)
                if (coo.getPoints() == -1) {
                    System.out.println("     Cette zone n'est pas accessible ");

                } else if ((Arrays.equals(new int[]{3, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, coo.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, coo.getPositionHexagone())) && ((Arrays.equals(new int[]{3, 3}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, anciennePosition.getPositionHexagone())))) {//si le joueur choisi une zone du tritime où il est déjà
                    System.out.println("     Le joueur se trouve déjà sur cette zone ");
                    ExceptionZone e = new ExceptionZone("Vous vous trouvez déjà sur cette zone");
                    throw e;
                } else {
                    //verifier que coo est un hexagone voisin 

                    if (anciennePosition.estAtteignable(coo)) {
                        HexagonAtteignable = true;

                        // Vérification si la zone est exploitée
                        if ((coo.getControler() != null) && (coo.getControler() != this.joueur)) { //zone occupée
                            HexagoneOccupe = true;
                            //System.out.println("     Cette zone est occupée par " + coo.getControler().getNom() + ". Il y a " + coo.getNbVaisseaux() + " vaisseaux.");
                            ExceptionZone e = new ExceptionZone("Un joueur se trouve déjà sur cette zone");
                            throw e;
                        } else if (coo.getControler() == this.joueur) {
                            //System.out.println("     Vous controlez déjà cette zone.");
                            ExceptionZone e = new ExceptionZone("Vous vous trouvez déjà sur cette zone");
                            throw e;
                        } else if (coo.getControler() == null) {
                            System.out.println("     Cette zone est vide");
                            HexagoneOccupe = false;
                        }
                    } else {
                        System.out.println("     Cette zone n'est pas attegnable ");
                        ExceptionZone e = new ExceptionZone("Cette zone n'est pas attegnable");
                        throw e;
                    }
                }

            } while (HexagoneOccupe || !HexagonAtteignable);

        } else {//attaque
            boolean HexagoneOccupe = false;
            System.out.println("attaque");
            do {
                int q = -1;
                int r = -1;

                //System.out.println("secccct"+);
                System.out.println("     La flotte se trouve sur le secteur " + Partie.getInstance().getMap().get(this.secteur) + ".\nChoisir la zone à envahir");
                coo = this.joueur.choisirZone();
                //coo = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa);
// Vérification si la zone est pas null (demi hexagone)
                if (coo.getPoints() == -1) {
                    System.out.println("     Cette zone n'est pas accessible ");

                } else if ((Arrays.equals(new int[]{3, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, coo.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, coo.getPositionHexagone())) && ((Arrays.equals(new int[]{3, 3}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, anciennePosition.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, anciennePosition.getPositionHexagone())))) {//si le joueur choisi une zone du tritime où il est déjà
                    System.out.println("     Le joueur se trouve déjà sur cette zone ");
                    ExceptionZone e = new ExceptionZone("Le joueur se trouve déjà sur cette zone");
                    throw e;
                } else {
                    //verifier que coo est un hexagone voisin 

                    if (anciennePosition.estAtteignable(coo)) {
                        System.out.println("zone atteignable");
                        HexagonAtteignable = true;

                        // Vérification si la zone est exploitée
                        if ((coo.getControler() != null) && (coo.getControler() != this.joueur)) { //zone occupée
                            HexagoneOccupe = true;
                            //System.out.println("     Cette zone est occupée par " + coo.getControler().getNom() + ". Il y a " + coo.getNbVaisseaux() + " vaisseaux.");
                            ExceptionZone e = new ExceptionZone("Un joueur se trouve déjà sur cette zone");
                            throw e;
                        } else if (coo.getControler() == this.joueur) {
                            //System.out.println("     Vous controlez déjà cette zone.");
                            ExceptionZone e = new ExceptionZone("Vous vous trouvez déjà sur cette zone");
                            throw e;
                        } else if (coo.getControler() == null) {
                            System.out.println("     Cette zone est vide");
                        }
                    } else {
                        System.out.println("     Cette zone n'est pas attegnable ");
                        ExceptionZone e = new ExceptionZone("Cette zone n'est pas attegnable");
                        throw e;
                    }
                }

            } while (!HexagoneOccupe || !HexagonAtteignable);
        }

        /* this.joueur.delListePlanetesControlees(this.getPositionVaisseau()); //déplace le vaisseau si c'était le seul sur sa zone ou que le joeur déplace tous ces vaisseaux
        this.joueur.addListePlanetesControlees(coo); */
        //mise à jour du vaisseau
        coo.setEstControle(true);
        coo.setControler(this.joueur);

        //dire que le joueur contrôle les 4 cases du tritime s'il sontrôle une zone de celui-ci
        if (Arrays.equals(new int[]{3, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 3}, coo.getPositionHexagone()) || Arrays.equals(new int[]{4, 2}, coo.getPositionHexagone()) || Arrays.equals(new int[]{5, 3}, coo.getPositionHexagone())) {

            coo.getSecteur().getHexagone(3, 3).setControler(this.joueur);
            coo.getSecteur().getHexagone(5, 3).setControler(this.joueur);
            coo.getSecteur().getHexagone(4, 3).setControler(this.joueur);
            coo.getSecteur().getHexagone(4, 2).setControler(this.joueur);
        }

        anciennePosition.delVaisseaux(this);
        //coo.setVaisseaux(this);
        this.setPositionVaisseau(coo);
        coo.setVaisseaux(this);
        System.out.println("test");
        return coo;
    }

    /**
     * Déplacer un vaisseau dans une zone vide.
     *
     * @param xSecteur La coordonnée X du secteur de destination.
     * @param ySecteur La coordonnée Y du secteur de destination.
     * @param xHexa La coordonnée X de l'hexagone de destination.
     * @param yHexa La coordonnée Y de l'hexagone de destination.
     *
     * @throws ExceptionZone Si l'hexagone cible est déjà contrôlé,
     * empêchant le déplacement du vaisseau.
     */
    public void deplacerFlotte(int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionZone {
        Hexagone coo = new Hexagone(0, 0, null, 0);

        coo = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa);
        
        // Vérification si la zone est exploitée
        if (coo.getControler() == null) { //zone libre
            coo.setEstControle(true);
            coo.setControler(this.joueur);
            coo.setVaisseaux(this);
            this.joueur.delListePlanetesControlees(this.getPositionVaisseau()); //déplace le vaisseau

            this.joueur.addListePlanetesControlees(coo);
            this.setPositionVaisseau(coo);

        } else if (coo.getControler() == this.joueur) {
            if (coo.getNbVaisseaux() == 2) {
                ExceptionZone e = new ExceptionZone("Vous possédez déjà cette zone");
                throw e;
            } else {
                coo.setVaisseaux(this);
                this.setPositionVaisseau(coo);
            }

        } else if (coo.getControler() != this.joueur) {
            ExceptionZone e = new ExceptionZone("Cette zone est contrôlée par " + coo.getControler().getNom());
            throw e;
        }
    }

    /**
     * déplacer un vaisseau d'une même flotte au même endroit que viasseau1
     *
     * @param vaisseau1
     */
    public void deplacerFlotte(Vaisseau vaisseau1) {

        this.setPositionVaisseau(vaisseau1.getPositionVaisseau());//met à jour la position du vaisseau en fonction d'un autre vaisseau1

        vaisseau1.getPositionVaisseau().setVaisseaux(this);

    }
}

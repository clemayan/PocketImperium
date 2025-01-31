package classes;

import java.io.Serializable;

/**
 * Cette classe représente la carte Developpement elle hérite de la classe
 * Carte. Elle permet d'ajouter des vaisseaux.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Developpement extends Carte implements Serializable {

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Developpement}
     *
     * @param joueur le joueur qui possède la carte
     * @param priorite la position de la carte dans l'ordre d'execution des
     * cartes du joueur
     */
    public Developpement(Joueur joueur, int priorite) {
        super(joueur, priorite);
    }

    /**
     * Méthode qui permet de développer un zone choisi en y ajoutant un vaisseau
     *
     * @param priorite position de la carte développement dans la main du joueur
     * @throws ExceptionZoneNonControlee
     */
    public void ajouterVaisseau(int priorite, int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionZone {
        if (this.joueur.getVaisseauDisponible() != null) {//vérifier que le joueur a encore des vaisseaux non placés     

            Hexagone hexa = new Hexagone(-1, -1, null, 0);
            hexa = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa); // le joueur choisi la zone à développer
            if (hexa.getControler() != this.joueur) {
                ExceptionZone e = new ExceptionZone("Vous ne contôlez pas cette zone");
                throw e;
            }

            this.joueur.getVaisseauDisponible().deplacerFlotte(hexa.getVaisseaux().get(0)); //déplacer un vaisseau pas encore placé sur le plateau à l'endroit choisi

        } else {
            System.out.println("Vous n'avez plus de vaisseaux disponibles. Tous vos vaisseaux sont déjà sur le plateau.");
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("carte Développement");
        sb.append(super.toString());
        return sb.toString();
    }
}

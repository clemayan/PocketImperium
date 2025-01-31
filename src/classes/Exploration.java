package classes;

import java.util.Scanner;

/**
 * Cette classe représente la carte Exploration. Elle permet de déplacer des
 * vaisseaux sur une nouvelle zone elle hérite de la classe Carte.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Exploration extends Carte {

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Exploration}
     *
     * @param joueur le joueur qui possède la carte
     * @param priorite la position de la carte dans l'ordre d'execution des
     * cartes du joueur
     * @note L'exploration fonctionne suelement en déplacement 1 vaisseau. Elle nécessite une mise à jour qui gère le déplacmeent de plusieurs vaisseaux. De plus, des problèmes d'affichage perciste toujours
     */
    public Exploration(Joueur joueur, int priorite) {
        super(joueur, priorite);
    }

    /**
     *
     * @param position le moment où on applique le déplacement : la position de
     * la carte exploration dans choix de l'ordre
     * @throws ExceptionZone
     */
    public void deplacerFlotte(int position, int xSecteur, int ySecteur, int xHexa, int yHexa) throws ExceptionManqueVaisseauxDisponibles, ExceptionZone {

        int nbVaisseauxUtilisables = 0;
        Hexagone hexa = new Hexagone(-1, -1, null, 0);
        int nbVaisseauxFlotte = 1;
        hexa = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa);
        if (hexa.getControler() != this.joueur) {
            ExceptionZone e = new ExceptionZone("Vous ne contôlez pas cette zone");
            throw e;
        }
        nbVaisseauxUtilisables = hexa.getNbVaisseauxPeutAgir(1);

        if (nbVaisseauxUtilisables == 0) {
            ExceptionManqueVaisseauxDisponibles e = new ExceptionManqueVaisseauxDisponibles("Aucun vaisseau de cette zone ne peut explorer car tous ont déjà été impliqués dans une exploration durant le tour !");
            throw e;

        }
        if (hexa.getNbVaisseaux() >= 2) {

            Scanner scan = new Scanner(System.in);
            System.out.println("Sur cette zone, " + nbVaisseauxUtilisables + " vaisseaux peuvent explorer.");
            do {
                System.out.println("     Combien de vaisseaux voulez-vous déplacer ?");
                nbVaisseauxFlotte = scan.nextInt();
                if (nbVaisseauxFlotte > hexa.getNbVaisseaux() || nbVaisseauxFlotte < 1) {
                    System.out.println("Valeur non valide.");
                } else if (nbVaisseauxFlotte > nbVaisseauxUtilisables) {
                    System.out.println("Il y a trop peu de vaisseaux pouvant explorer.");
                }
            } while (nbVaisseauxFlotte < 1 || nbVaisseauxFlotte > nbVaisseauxUtilisables);
        }

        Hexagone coo = hexa.getVaisseaux().get(0).deplacerFlotte(hexa, xSecteur, ySecteur, xHexa, yHexa, 1);//demander la valeur du nouvel hexagone pour au moins un vaisseau

        hexa.getVaisseaux().get(0).setAExplore(true);

        if (nbVaisseauxFlotte > 1 || nbVaisseauxFlotte == hexa.getNbVaisseaux()) {//on supprimer la zone si c'était le seul sur sa zone ou que le joueur déplace tous ces vaisseaux
            this.joueur.delListePlanetesControlees(hexa);
        }
        this.joueur.addListePlanetesControlees(coo);
        if (nbVaisseauxFlotte > 1) {
            int j = 0;
            do {
                if (hexa.getVaisseaux().get(j).getAExplore() == false) {//le vaisseaux peut explorer
                    hexa.getVaisseaux().get(j).deplacerFlotte(hexa.getVaisseaux().get(0));
                    hexa.getVaisseaux().get(j).setAExplore(true);//les vaisseaux ont exploré une fois dans le tour
                    hexa.delVaisseaux(hexa.getVaisseaux().get(j));//supprimer le vaisseaux de l'ancien hexagone
                    j++;
                }
            } while (j < nbVaisseauxFlotte - 1 || nbVaisseauxFlotte > nbVaisseauxUtilisables);
        }

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("carte Exploration");
        sb.append(super.toString());
        return sb.toString();
    }

}

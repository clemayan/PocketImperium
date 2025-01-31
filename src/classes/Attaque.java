package classes;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Cette classe représente la carte Attaque elle hérite de la classe Carte. Elle
 * permet d'envahir une zone.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Attaque extends Carte implements Serializable {

    /**
     * Constructeur qui instancie un nouvel objet de type {@link Attaque}
     *
     * @param joueur le joueur qui possède la carte
     * @param priorite la position de la carte dans l'ordre d'execution des
     * cartes du joueur
     */
    public Attaque(Joueur joueur, int priorite) {
        super(joueur, priorite);
    }

    /**
     * Méthode qui permet d'envahir une nouvelle zone afind d'attaquer un
     * adversaire
     *
     * @param priorite position dans l'ordre des cartes du joueur
     * @throws ExceptionZoneDejaControlee
     * @throws FontFormatException
     * @throws IOException
     * @throws ExceptionZoneNonAtteignable
     */
    public void envahirFlotte(int priorite, int xSecteur, int ySecteur, int xHexa, int yHexa) throws IOException, FontFormatException, ExceptionZone {

        Partie partie = Partie.getInstance();
        int nbVaisseauxUtilisables = 0; //nombres de vaisseaux qui peuvent attaquer l'adversaire
        boolean controleHexagone = false;
        Hexagone hexa = new Hexagone(-1, -1, null, 0);
        int nbVaisseauxFlotte = 1;

        hexa = this.joueur.choisirZone(xSecteur, ySecteur, xHexa, yHexa);

        Scanner scan = new Scanner(System.in);
        if (hexa.getControler() != this.joueur) {
            ExceptionZone e = new ExceptionZone("Vous ne contôlez pas cette zone");
            throw e;
        } else {
            //vérifier que la zone ne contient pas que des vaisseaux qui ont déjà été impliqué dans une attaque
            nbVaisseauxUtilisables = hexa.getNbVaisseauxPeutAgir(2);
            if (nbVaisseauxUtilisables == 0) {
                System.out.println("Aucun vaisseau de cette zone ne peut attaquer car tous ont déjà été impliqués dans une attaque durant le tour !");
            } else {
                controleHexagone = true;
                if (hexa.getNbVaisseaux() > 1) {
                    System.out.println("Sur cette zone, " + nbVaisseauxUtilisables + " vaisseaux peuvent attaquer.");
                    do {
                        System.out.println("     Combien de vaisseaux voulez-vous utilser pour l'attaque ?");
                        nbVaisseauxFlotte = scan.nextInt();
                        if (nbVaisseauxFlotte > hexa.getNbVaisseaux() || nbVaisseauxFlotte < 1) {
                            System.out.println("Valeur non valide.");
                        } else if (nbVaisseauxFlotte > nbVaisseauxUtilisables) {
                            System.out.println("Il y a trop peu de vaisseaux pouvant attaquer.");
                        }
                    } while (nbVaisseauxFlotte > hexa.getNbVaisseaux() || nbVaisseauxFlotte < 1 || nbVaisseauxFlotte > nbVaisseauxUtilisables);
                } else {
                    System.out.println("Un seul vaisseau peut attaquer.");
                }
            }
        }

        //demander l'hexagone à envahir
        Hexagone coo = hexa.getVaisseaux().get(0).deplacerFlotte(hexa, xSecteur, ySecteur, xHexa, yHexa, 2);//demander la valeur du nouvel hexagone à attquer pour au moins un vaisseau
        hexa.getVaisseaux().get(0).setAAttaque(true);//les vaisseaux ont attaqué une fois dans le tour

        if (nbVaisseauxFlotte > 1 || nbVaisseauxFlotte == hexa.getNbVaisseaux()) {//déplace le vaisseau si c'était le seul sur sa zone ou que le joueur déplace tous ces vaisseaux
            this.joueur.delListePlanetesControlees(hexa);//l'ancienne planete n'est plus controlée par le joueur
        }

        //comparer le nombre de flotte avec celles sur la zone
        if (nbVaisseauxFlotte > coo.getNbVaisseaux()) { //le joueur qui envahi a une flotte plus puissante que son adversaire
            int j = 1;
            do {
                if (hexa.getVaisseaux().get(j).getAAttaque() == false) {//le vaisseaux qui peut attaquer va être supprimé
                    //supprime les vaisseaux morts lors de l'attaque pour les 2 joueurs
                    this.joueur.perdreVaisseaux(hexa.getVaisseaux().get(j));
                    coo.getControler().perdreVaisseaux(coo.getVaisseaux().get(j));
                    j++;
                }
            } while (j < coo.getNbVaisseaux());

            coo.getControler().perdreVaisseaux(coo.getVaisseaux().get(0)); //le premier vaisseau est mort

            //changer la liste des zones controlées des joueurs
            coo.getControler().delListePlanetesControlees(coo);//supprimer la planete de la liste des controlées de l'adversaire perdant

            hexa.getVaisseaux().get(0).setPositionVaisseau(coo); //déplacer le premier vaisseau du joueur vainqueur

            //changer les positions des vaisseaux encore vivant qui font l'attaque
            int k = 1;
            do {
                if (hexa.getVaisseaux().get(k).getAAttaque() == false && hexa.getVaisseaux().get(k).getJoueur() == this.joueur) {//le vaisseaux pouvant attaquer et toujours vivant va être déplacé
                    hexa.getVaisseaux().get(k).deplacerFlotte(hexa.getVaisseaux().get(0));
                    hexa.getVaisseaux().get(k).setAAttaque(true);//les vaisseaux ont exploré une fois dans le tour
                    k++;
                }
            } while (k < nbVaisseauxFlotte);

            System.out.println("Suite à l'attaque," + coo.getControler().getNom() + "contrôle la zone !");

        } else { //le joueur qui attaque est perdant
            int j = 1;
            do {
                if (hexa.getVaisseaux().get(j).getAAttaque() == false) {//le vaisseaux peut attaquer
                    this.joueur.perdreVaisseaux(hexa.getVaisseaux().get(j)); //supprimer les vaisseaux lors de l'attaque
                    coo.getControler().perdreVaisseaux(coo.getVaisseaux().get(j));
                    j++;
                }
            } while (j < nbVaisseauxFlotte);

            this.joueur.perdreVaisseaux(hexa.getVaisseaux().get(0));
            if (nbVaisseauxFlotte == coo.getNbVaisseaux()) {//flottes egales
                coo.getControler().delListePlanetesControlees(coo);//supprimer la planete de la liste des controlées de l'adversaire
                System.out.println("Les joueurs ont autant de vaisseaux, la zone reste incontrôlée !");
            } else {
                System.out.println(coo.getControler().getNom() + "contrôle la zone !");
            }
        }

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("carte Attaque");
        sb.append(super.toString());
        return sb.toString();
    }

}

package classes;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Cette classe représente les phases de chaque partie. Elle permet de faire
 * appel aux méthodes correspondant à chaque phase : Plannifier, Exécuter et
 * Exploiter.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class Phase implements Serializable{

    private int numero;
    private Joueur joueurActuel;

    /**
     * Constructeur qui permet d'instancier un nouvel objet de la classe Phase
     *
     * @param numero
     * @param numeroJoueurActuel
     * @param joueur0
     * @param joueur1
     * @param joueur2
          * @throws FontFormatException 
          * @throws IOException 
          */
         public Phase(int numero, int numeroJoueurActuel, Joueur joueur0, Joueur joueur1, Joueur joueur2) throws IOException, FontFormatException {
        this.numero = numero;
        switch (numeroJoueurActuel) {
            case 0:
                this.joueurActuel = joueur0;
                break;
            case 1:
                this.joueurActuel = joueur1;
                break;
            case 2:
                this.joueurActuel = joueur2;
                break;

        }

    }

    /**
     * Méthode qui permet de faire appel aux autres méthode en fonction de la
     * carte choisi par le joueur
     * @throws FontFormatException 
     * @throws IOException 
     */
    public void revelationCarte() throws IOException, FontFormatException {
        List<Joueur> joueurs = Partie.getInstance().getListeJoueurs();

        for (int j = 1; j <= 3; j++) {
            for (int i = 0; i <= 2; i++) {
                Partie.getInstance().setJoueurEnCours(i);
                Partie.getInstance().setPrioEnCours(j);
                //Developpement
                if (joueurs.get(i).getPrioriteDeveloppement() == j) {
                    System.out.println(joueurs.get(i).getNom() + " developpe !");
                    //joueurs.get(i).getCarteDeveloppement().ajouterVaisseau(j); 
                    Partie.getInstance().setMoment(Moment.Developpement);
                }

                //Exploration
                if (joueurs.get(i).getPrioriteExploration() == j) {
                    System.out.println(joueurs.get(i).getNom() + " explore !");
                    //joueurs.get(i).getCarteExploration().deplacerFlotte(j);
                    Partie.getInstance().setMoment(Moment.Exploration);
                }

                //Attaque
                if (joueurs.get(i).getPrioriteAttaque() == j) {
                    System.out.println(joueurs.get(i).getNom() + " attaque !");
                    Partie.getInstance().setMoment(Moment.Attaque);
                }
            }
        }
    }

    
}

package classes;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Cette classe représente uen stratégie défensive adoptée par un joueur
 * virtuel. Elle permet de choisir l'ordre des cartes en commençant par un
 * développement.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class StrategieHumain implements Strategie, Serializable{

    public StrategieHumain(Joueur joueur) {
    }
    
    /**
     * Méthode permet à l'utilisateur de choisir l'ordre d'utilisation de ses
     * cartes
     * @throws ExceptionChoixOrdre 
     */
    public void choixOrdre(Joueur joueur, int prioriteDeveloppement,int prioriteExploration, int prioriteAttaque) throws ExceptionChoixOrdre {
    	int[] listeChoix= new int[] {prioriteDeveloppement, prioriteExploration, prioriteAttaque};
    	for (int i = 0; i < 3; i++) { // parcourir les 3 cartes
                int choix = listeChoix[i]; //valeur de la priorite entrée par l'utilisateur
                if (choix != 1 & choix != 2 & choix != 3) {
                	ExceptionChoixOrdre e = new ExceptionChoixOrdre("");
                	
                    throw e;
                } else {
                    
                	//On verifie qu'on a bien un 1, un 2 et un 3
                    if (prioriteAttaque*prioriteDeveloppement*prioriteExploration!=6) {
                    	ExceptionChoixOrdre e = new ExceptionChoixOrdre("");
                        throw e;
                    } else {
                        switch (i) { // mettre à jour la valeur de priorité
                            case 0:
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 0);
                                joueur.setPrioriteDeveloppement(choix);
                                break;
                            case 1:
                                joueur.setPrioriteExploration(choix);
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 1);
                                break;
                            case 2:
                                joueur.setPrioriteAttaque(choix);
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 2);
                                break;
                        }
                    }
                }
        }
    }
    
    

    /**
     * Méthode permet à l'utilisateur de choisir l'ordre d'utilisation de ses
     * cartes
     */
    public void choixOrdre(Joueur joueur) {
        System.out.println("     " + joueur.getNom());
        Scanner scan = new Scanner(System.in);
        System.out.println("     Choisir l'ordre de vos carte :");
        for (int i = 0; i < 3; i++) { // parcourir les 3 cartes
            boolean prioriteValide = false;
            do {
                switch (i) { // affichage du texte selon la carte qu'on traite
                    case 0:
                        System.out.print("     Développement");
                        break;

                    case 1:
                        System.out.print("     Exploration");
                        break;
                    case 2:
                        System.out.print("     Attaque");
                        break;
                }
                System.out.println("(1, 2 ou 3) : ");
                int choix = scan.nextInt(); //valeur de la priorite entrée par l'utilisateur
                if (choix != 1 & choix != 2 & choix != 3) {
                    System.out.println("choix non valide, veuillez réessayer.");
                } else {
                    // Vérification si la valeur est déjà attribuée
                    boolean prioriteDejaAttribuee = false;
                    for (int j = 0; j < 3; j++) {
                        if (joueur.getListeCartes().get(j).getPriorite() == choix) {
                            prioriteDejaAttribuee = true;
                            break;
                        }
                    }

                    if (prioriteDejaAttribuee) {
                        System.out.println("Cette priorite est déjà attribuée. Choisissez-en une autre.");
                    } else {
                        switch (i) { // mettre à jour la valeur de priorité
                            case 0:
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 0);
                                joueur.setPrioriteDeveloppement(choix);
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 0);
                                prioriteValide = true;
                                break;
                            case 1:
                                joueur.setPrioriteExploration(choix);
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 1);
                                prioriteValide = true;
                                break;
                            case 2:
                                joueur.setPrioriteAttaque(choix);
                                Partie.getInstance().incrementNbJoueursCarte(choix - 1, 2);
                                prioriteValide = true;
                                break;
                        }
                    }
                }
            } while (!prioriteValide);
        }
    }

}

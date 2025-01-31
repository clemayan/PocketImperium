package classes;

import java.io.Serializable;

/**
 * Cette classe représente uen stratégie défensive adoptée par un joueur
 * virtuel. Elle permet de choisir l'ordre des cartes en commençant par un
 * développement.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class StrategieDefensive implements Strategie, Serializable {

    public StrategieDefensive(Joueur joueur) {
        this.choixOrdre(joueur);
    }

    /**
	 * Méthode qui prédéfinis l'ordre d'utilisation des cartes pour un joueur virtuel avec une stratégie défensive
	 */
    public void choixOrdre(Joueur joueur) {
        Partie.getInstance().incrementNbJoueursCarte(0, 0);
        joueur.getListeCartes().get(0).setPriorite(1); //carte Developpement en 1ere position
        Partie.getInstance().incrementNbJoueursCarte(1, 1);
        joueur.getListeCartes().get(1).setPriorite(2); //carte Exploration en 2 ème position
        Partie.getInstance().incrementNbJoueursCarte(2, 2);
        joueur.getListeCartes().get(2).setPriorite(3); //carte Aqttaque en 3 ème position

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("défensive");
        return sb.toString();
    }

	@Override
	public void choixOrdre(Joueur joueur, int a, int b, int c) throws ExceptionChoixOrdre {
		// TODO Auto-generated method stub
		
	}
}

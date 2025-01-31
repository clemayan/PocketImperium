package classes;

import java.io.Serializable;

/**
 * Cette classe représente uen stratégie agressive adoptée par un joueur virtuel.
 * Elle permet de choisir l'ordre des cartes en commençant par une attaque.
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class StrategieAgressive implements Strategie, Serializable{

    public StrategieAgressive(Joueur joueur) {
		this.choixOrdre(joueur);
    }
	

	/**
	 * Méthode qui prédéfinis l'ordre d'utilisation des cartes pour un joueur virtuel avec une stratégie agressive
	 */
	public void choixOrdre(Joueur joueur){
		Partie.getInstance().incrementNbJoueursCarte(0,2);
		joueur.getListeCartes().get(2).setPriorite(1); //carte Attaque en 1ere position
		Partie.getInstance().incrementNbJoueursCarte(1,1);
		joueur.getListeCartes().get(1).setPriorite(2); //carte Exploration en 2 ème position
		Partie.getInstance().incrementNbJoueursCarte(2,0);
		joueur.getListeCartes().get(0).setPriorite(3); //carte Développement en 3 ème position
	}

	public String toString() {
        StringBuffer sb = new StringBuffer();
		sb.append("agressive");
		return sb.toString();
    }


	@Override
	public void choixOrdre(Joueur joueur, int a, int b, int c) throws ExceptionChoixOrdre {
		// TODO Auto-generated method stub
		
	}
}   

package classes;
/**
 * Cette interface représente la stratégie adoptée par un joueur.
 * Elle permet de définir le choix des cartes selon la stratégie adoptée par un joueur virtuel.
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public interface Strategie {
	public void choixOrdre(Joueur joueur);
	public void choixOrdre(Joueur joueur, int a, int b ,int c) throws ExceptionChoixOrdre;
}   

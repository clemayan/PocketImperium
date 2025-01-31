package classes;

/**
 * Cette classe énumération représente la couleur des vaisseaux d'un joueur.
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public enum Couleur {
    ROUGE, JAUNE, BLEU;

	/**
	 * Améliore l'affichage de la couleur des vaisseaux
	 */
    public String toString2() {
        StringBuffer sb = new StringBuffer();
		sb.append(this);
		return sb.toString();
	}
    public String toString3() {
        StringBuffer sb = new StringBuffer();
        
        switch (this) {
            case JAUNE:
                sb.append("jaunes");
				break;
            case ROUGE:
                sb.append("rouges");
				break;
            case BLEU:
                sb.append("bleus");
				break;
        }

		return sb.toString();
    }

}

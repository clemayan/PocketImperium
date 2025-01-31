package classes;
/**
 * Cette classe énumération représente le type du joueur : virtuel ou humain.
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public enum TypeJoueur {
	VIRTUEL, HUMAIN;
	
	/**
	 * améliore l'affichage du type de joueur
	 */
	public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (this) {
            case VIRTUEL:
                sb.append("virtuel");
				break;
            case HUMAIN:
                sb.append("humain");
				break;
        }

		return sb.toString();
    }
}

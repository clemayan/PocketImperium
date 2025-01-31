package classes;

import java.io.Serializable;

/**
 * Cette classe crée les exceptions qui peuvent être levés en cas de problème lorsque
 * le joueur choisit l'ordre de ses cartes
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ExceptionChoixOrdre extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructeur qui instancie un nouvel objet de type {@link ExceptionChoixOrdre}
	 * @param info
	 */
	public ExceptionChoixOrdre(String info){
		super(info);
	}
	
}

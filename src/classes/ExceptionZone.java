package classes;
/**
 * Cette classe les exceptions qui peuvent être levés en cas de problème lorsque
 * le joueur choisit une zone
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ExceptionZone extends Exception {
	private static final long serialVersionUID = 1L;
	/**
     * Constructeur qui instancie un nouvel objet de type {@link ExceptionZone}
	 * @param info
	 */
	public ExceptionZone(String info){
		super(info);
	}
	
}

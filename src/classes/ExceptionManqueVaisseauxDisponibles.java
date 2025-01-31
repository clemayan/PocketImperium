package classes;
/**
 * Cette classe crée les exceptions qui peuvent être levés en cas de problème 
 * lorsque les vaisseaux ne sont pas disponibles
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ExceptionManqueVaisseauxDisponibles extends Exception {
	private static final long serialVersionUID = 1L;
	/**
     * Constructeur qui instancie un nouvel objet de type {@link ExceptionManqueVaisseauxDisponibles}
	 * @param info
	 */
	public ExceptionManqueVaisseauxDisponibles(String info){
		super(info);
	}
	
}

package classes;
/**
 * Cette classe les exceptions qui peuvent être levés en cas de problème lors
 * du scoring supplémentaire accordé au joueur qui possède le tri time
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ExceptionScoringCoupSupplementaire extends Exception {
	private static final long serialVersionUID = 1L;
	/**
     * Constructeur qui instancie un nouvel objet de type {@link ExceptionScoringCoupSupplementaire}
	 * @param info
	 */
	public ExceptionScoringCoupSupplementaire(String info){
		super(info);
	}
	
}

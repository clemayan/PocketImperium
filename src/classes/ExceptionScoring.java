package classes;

/**
 * Cette classe les exceptions qui peuvent être levés en cas de problème lors du scoring
 *
 * @author Maya Mazuet et Aurélien Musset
 * @version 1.0
 */
public class ExceptionScoring extends Exception {
	private static final long serialVersionUID = 1L;
	/**
     * Constructeur qui instancie un nouvel objet de type {@link ExceptionScoring}
	 * @param info
	 */
	public ExceptionScoring(String info){
		super(info);
	}
	
}

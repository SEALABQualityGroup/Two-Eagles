package elementiBaseQN;

/**
 * Rappresenta un'eccezione sollevata quando un elemento di tipo base
 * non è stato inizializzato correttamente.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ElementoBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public ElementoBaseException() {
		super();
	}

	public ElementoBaseException(String message) {
		super(message);
	}

	public ElementoBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementoBaseException(Throwable cause) {
		super(cause);
	}
}

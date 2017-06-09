package mappingElementiBaseIntoPMIF;

/**
 * Eccezione utilizzata per la gestione degli errori di mapping
 * di elementi base di una rete di code.
 *
 * @author Mirko
 *
 */
public class MappingElementiBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public MappingElementiBaseException() {
		super();
	}

	public MappingElementiBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public MappingElementiBaseException(String message) {
		super(message);
	}

	public MappingElementiBaseException(Throwable cause) {
		super(cause);
	}
}
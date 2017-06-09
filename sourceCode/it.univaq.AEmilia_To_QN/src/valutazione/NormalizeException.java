package valutazione;

/**
 * Questa eccezione viene sollevata quando si hanno problemi
 * nella normalizzazione di una specifica AEmilia, dovuti dalla
 * struttura del tipo architetturale o errori di tipo.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class NormalizeException extends Exception {

	private static final long serialVersionUID = 1L;

	public NormalizeException(Throwable cause)
		{
		super(cause);
		}

	public NormalizeException()
		{
		super();
		}

	public NormalizeException(String message)
		{
		super(message);
		}
}

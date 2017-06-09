package restrizioniGenerali;

/**
 * Eccezione che può essere sollevata quando si verificano le regole di restrizione
 * sintattiche generali.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class RestrizioniGenException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestrizioniGenException()
		{
		super();
		}

	public RestrizioniGenException(String message, Throwable cause)
		{
		super(message, cause);
		}

	public RestrizioniGenException(String message)
		{
		super(message);
		}

	public RestrizioniGenException(Throwable cause)
		{
		super(cause);
		}
}

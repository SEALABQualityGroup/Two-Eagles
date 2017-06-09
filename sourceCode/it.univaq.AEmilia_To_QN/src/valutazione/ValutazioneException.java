package valutazione;

/**
 * Questa eccezione viene sollevata quando non è possibile valutare
 * un identificatore, di solito perchè è una variabile non inizializzata o
 * è una variabile locale appartenente ad un comportamento.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ValutazioneException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValutazioneException(Throwable cause)
		{
		super(cause);
		}

	public ValutazioneException()
		{
		super();
		}

	public ValutazioneException(String message)
		{
		super(message);
		}
}

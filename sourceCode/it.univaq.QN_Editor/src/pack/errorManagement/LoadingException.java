package pack.errorManagement;

/**
 * Nel caso in cui non possa essere caricato il modello nell'editor, 
 * perchè i vari campi non possono essere inizializzati,
 * viene sollevata una loading exception. 
 * Tale eccezione viene sollevata quando non c'è la definizione corrispondente ad un riferimento di elemento.
 * 
 * @author Mirko
 *
 */
public class LoadingException 
	extends Exception 
	{

	private static final long serialVersionUID = 1L;

	public LoadingException() 
		{
		super();
		}

	public LoadingException(String arg0, Throwable arg1) 
		{
		super(arg0, arg1);
		}

	public LoadingException(String arg0) 
		{
		super(arg0);
		}

	public LoadingException(Throwable arg0) 
		{
		super(arg0);
		}
	}

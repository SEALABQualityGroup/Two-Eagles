package pack.errorManagement;

/**
 * Questa eccezione viene sollevata quando il modello non può essere salvato
 * in xml perchè non è sintatticamente corretto.
 * 
 * @author Mirko
 *
 */
public class SavingException extends Exception 
	{

	private static final long serialVersionUID = 1L;

	public SavingException() 
		{
		super();
		}

	public SavingException(String arg0, Throwable arg1) 
		{
		super(arg0, arg1);
		}

	public SavingException(String arg0) 
		{
		super(arg0);
		}

	public SavingException(Throwable arg0) 
		{
		super(arg0);
		}	
	}

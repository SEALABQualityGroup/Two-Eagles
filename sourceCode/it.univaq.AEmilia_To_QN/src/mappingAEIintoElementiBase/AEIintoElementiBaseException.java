package mappingAEIintoElementiBase;

/**
 * Eccezione utilizzata per gestire gli errori che si possono 
 * presentare durante la fase di mapping delle istanze AEmilia
 * in elementi base.
 * 
 * @author Mirko
 *
 */
public class AEIintoElementiBaseException extends Exception {

	private static final long serialVersionUID = 1L;

	public AEIintoElementiBaseException() 
		{
		super();
		}

	public AEIintoElementiBaseException(String message, Throwable cause) 
		{
		super(message, cause);
		}

	public AEIintoElementiBaseException(String message) 
		{
		super(message);
		}

	public AEIintoElementiBaseException(Throwable cause) 
		{
		super(cause);
		}	
}

package restrizioniSpecifiche;

public class RestrizioniSpecException extends Exception {

	private static final long serialVersionUID = 1L;

	public RestrizioniSpecException()
		{
		super();
		}

	public RestrizioniSpecException(String message, Throwable cause)
		{
		super(message, cause);
		}

	public RestrizioniSpecException(String message)
		{
		super(message);
		}

	public RestrizioniSpecException(Throwable cause)
		{
		super(cause);
		}
}

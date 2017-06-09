package valutazione;

public class InterezzaException extends Exception {

	private static final long serialVersionUID = 1L;

	public InterezzaException()
		{
		super();
		}

	public InterezzaException(String message, Throwable cause)
		{
		super(message, cause);
		}

	public InterezzaException(String message)
		{
		super(message);
		}

	public InterezzaException(Throwable cause)
		{
		super(cause);
		}
}

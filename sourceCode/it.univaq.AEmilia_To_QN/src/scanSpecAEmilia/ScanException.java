package scanSpecAEmilia;

public class ScanException extends Exception {

	private static final long serialVersionUID = 1L;

	public ScanException()
		{
		super();
		}

	public ScanException(String message, Throwable cause)
		{
		super(message, cause);
		}

	public ScanException(String message)
		{
		super(message);
		}

	public ScanException(Throwable cause)
		{
		super(cause);
		}
}

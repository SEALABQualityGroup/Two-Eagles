package scanSpecAEmilia;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe utilizzata per scannerizzare un identificatore.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class ScanIdent {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un identificatore.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isIdent(String specifiche)
		{
		Scanner s = new Scanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*[a-zA-Z](\\w*)\\s*");
//		return s.hasNext("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
		}

	/**
	 * Crea un oggetto String, ottenute attraverso la
	 * scannerizzazione di un identificatore.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto String.
	 * @throws ScanException
	 */

	public static String scanIdent(String specifiche)
		throws ScanException
		{
		Scanner s = new Scanner(specifiche);
		String identificatore = new String();
		try {
			identificatore =
				s.next("\\s*[a-zA-Z](\\w*)\\s*");
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not identifier",e);
			}
		return identificatore;
		}
}

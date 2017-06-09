package scanSpecAEmilia;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import personalScanner.MyScanner;

import specificheAEmilia.BehavEquation;

/**
 * Classe utilizzata per scannerizzare ogni parte di
 * un'equazione comportamentale, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Un'equazione comportamentale EMPA ha la seguente forma:
 *
 * <behav_equation_header> "=" <process_term>
 */

public class ScanBehavEquation {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'equazione comportamentale secondo la grammatica
	 * AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isBehavEquation(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\=\\s*");
		String pt = new String();
		String beh = new String();
		String u = new String();
		try {
			// si scannerizza specifiche fin quando la parte
			// sinistra di un segno di uguale ш un'intestazione
			// e la parte destra ш un termine di processo
			while (s.hasNext())
				{
				// beh contiene l'intestazione di un'equazione
				// comportamento
				beh = beh + s.next();
				s.useDelimiter("\\s*\\z");
				if (!s.hasNext("\\s*\\=(.)*")) return false;
				s.skip("\\s*\\=\\s*");
				u = MyScanner.precMatch(s);
				// pt contiene un termine di processo
				pt = s.next();
				if (ScanIntestazione.isIntestazione(beh) &&
						ScanProcessTerm.isProcessTerm(pt))
					return true;
				else {
					// nel caso in cui non ш stato trovato un
					// termine di processo e un'intestazione
					// si aggiunge l'uguale scannerizzato
					// all'intestazione
					beh = beh + u;
					s = new MyScanner(specifiche);
					s.useDelimiter("\\s*\\z");
					// si scannerizza la parte dell'intestazione
					// giра esaminata
					s.skip(Pattern.quote(beh));
					s.useDelimiter("\\s*\\=\\s*");
					}
				}
			return false;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto BehavEquation, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto BehavEquation.
	 * @throws ScanException
	 */
	public static BehavEquation scanBehavEquation(String specifiche)
		throws ScanException
		{
		BehavEquation be = new BehavEquation();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\=\\s*");
		String pt = new String();
		String beh = new String();
		String u = new String();
		try {
			// si scannerizza specifiche fin quando la parte
			// sinistra di un segno di uguale ш un'intestazione
			// e la parte destra ш un termine di processo
			while (s.hasNext())
				{
				// beh contiene l'intestazione di un'equazione
				// comportamento
				beh = beh + s.next();
				s.useDelimiter("\\s*\\z");
				if (!s.hasNext("\\s*\\=(.)*"))
					throw new ScanException(specifiche+" is not behavioral equation");
				s.skip("\\s*\\=\\s*");
				u = MyScanner.precMatch(s);
				// pt contiene un termine di processo
				pt = s.next();
				// se abbiamo trovato un'intestazione e un
				// termine di processo allora restituiamo
				// un oggetto BehavEquation
				if (ScanIntestazione.isIntestazione(beh) &&
						ScanProcessTerm.isProcessTerm(pt))
					{
					be.setBehavHeader(ScanIntestazione.scanIntestazione(beh));
					be.setTermineProcesso(ScanProcessTerm.scanProcessTerm(pt));
					return be;
					}
				else {
					// nel caso in cui non ш stato trovato un
					// termine di processo e un'intestazione
					// si aggiunge l'uguale scannerizzato
					// all'intestazione (si ш ancora all'interno dell'intestazione)
					beh = beh + u;
					s = new MyScanner(specifiche);
					s.useDelimiter("\\s*\\z");
					// si scannerizza la parte dell'intestazione
					// giра esaminata
					s.skip(Pattern.quote(beh));
					s.useDelimiter("\\s*\\=\\s*");
					}
				}
			return null;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not BehavEquation object",e);
			}
		}
}

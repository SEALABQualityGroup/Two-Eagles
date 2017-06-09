package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.BehavEquation;

/**
 * Classe utilizzata per scannerizzare ogni parte di un
 * comportamento di un tipo di elemento architetturale, dettato
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Il comportamento di un AET ha la seguente sintassi:
 * "BEHAVIOR" <behav_equation_sequence> dove
 * <behav_equation_sequence> è una sequenza non vuota di equazioni
 * comportamentali EMPA (oggetti BehavEquation) separate da punti
 * e virgole. La prima equazione comportamentale nella sequenza
 * rappresenta il comportamento iniziale per l'AET. Ogni altra
 * equazione comportamentale possibile nella sequenza deve
 * descrivere un comportamento che può essere direttamente o
 * indirettamente invocato da quella iniziale.
 */

public class ScanAETbehavior {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza non
	 * vuota di equazioni comportamentali EMPA
	 * (oggetti BehavEquation) separate da punti e virgola.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isBES(String specifiche)
		{
		/*
		 * <behav_equation_sequence> è una sequenza non vuota di equazioni
		 * comportamentali EMPA (oggetti BehavEquation) separate da punti e virgole.
		 */
		// non sono accettate sequenze vuote
		if (specifiche.matches(new String())) return false;
		// non sono accettate sequenze che iniziano con un
		// punto e virgola
		if (specifiche.matches("\\s*\\;(.)*")) return false;
		// non sono accettate sequenze che terminano con un
		// punto e virgola
		if (specifiche.matches("(.)*\\;\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		String be = new String();
		try {
			// si verifica che ogni sottostringa tra due punti
			// e virgola (senza considerare il punto e virgola
			// dell'intestazione) sia un'equazione comportamentale
			int c = 0;
			while (s.hasNext())
				{
				// bisogna considerare anche il ; dell'beh
				be = s.next()+";";
				be = be + s.next();
				if (!ScanBehavEquation.isBehavEquation(be)) return false;
				c++;
				}
			if (c == 0) return false;
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di equazioni comportamentali
	 * EMPA separate da punti e virgola, generando un array
	 * di oggetti BehavEquation.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti BehavEquation.
	 * @throws ScanException
	 */
	private static BehavEquation[] scanBES(String specifiche)
		throws ScanException
		{
		/*
		 * <behav_equation_sequence> è una sequenza non vuota di equazioni
		 * comportamentali EMPA (oggetti BehavEquation) separate da punti e virgole.
		 */
		BehavEquation[] bes = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		String b = new String();
		int c = 0;
		try {
			// non sono accettate sequenze vuote
			if (specifiche.matches(new String()))
				throw new InputMismatchException();
			// non sono accettate sequenze che iniziano con un
			// punto e virgola
			if (specifiche.matches("\\s*\\;(.)*"))
				throw new InputMismatchException();
			// non sono accettate sequenze che terminano con un
			// punto e virgola
			if (specifiche.matches("(.)*\\;\\s*\\z"))
				throw new InputMismatchException();
			// si conta il numero di equazioni comportamentali
			while (s.hasNext())
				{
				// bisogna considerare anche il ; dell'beh
				b = s.next()+";";
				b = b + s.next();
				c++;
				}
			// il numero delle equazioni comportamentali deve essere
			// maggiore di 1
			if (c == 0) throw new ScanException("The behavioral equations number "+
				" must be greater than zero");
			bes = new BehavEquation[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\;\\s*");
			// si scannerizzano tutte le equazioni
			// comportamentali
			for (int i = 0; i < bes.length; i++)
				{
				b = s.next()+";";
				b = b + s.next();
				bes[i] = ScanBehavEquation.scanBehavEquation(b);
				}
			return bes;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not BehavEquation objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche contiene la
	 * definizione del comportamento di un tipo di elemento
	 * architetturale secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isAETbehavior(String specifiche)
		{
		/*
		 * Il comportamento di un AET ha la seguente sintassi:
		 * "BEHAVIOR" <behav_equation_sequence>
		 */
		try {
			MyScanner s = new MyScanner(specifiche);
			s.skip("\\s*BEHAVIOR\\s*");
			s.useDelimiter("\\s*\\z");
			// bes indica una sequenza di equazioni comportamentali
			String bes = s.next();
			return isBES(bes);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto AETbehavior, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AETbehavior.
	 * @throws ScanException
	 */
	public static AETbehavior scanAETbehavior(String specifiche)
		throws ScanException
		{
		/*
		 * Il comportamento di un AET ha la seguente sintassi:
		 * "BEHAVIOR" <behav_equation_sequence>
		 */
		AETbehavior ab = new AETbehavior();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.skip("\\s*BEHAVIOR\\s*");
			s.useDelimiter("\\s*\\z");
			// bes indica una sequenza di equazioni comportamentali
			String bes = s.next();
			ab.setBehaviors(scanBES(bes));
			return ab;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AETbehavior object",e);
			}
		}
}

package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiElemInstances;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di istanze di elementi architetturali, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Le istanze degli AET definite nella prima sezione di una
 * specifica AEmilia sono dichiarate come segue:
 *
 * "ARCHI_ELEM_INSTANCES" <AEI_decl_sequence>
 *
 * dove <AEI_decl_sequence> è una sequenza non vuota di
 * dichiarazioni AEI separate da punti e virgole.
 */

public class ScanArchiElemInstances {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di istanze di elementi architetturali separati
	 * da punti e virgola.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isADS(String specifiche)
		{
		// non viene accettata una sequenza di dichiarazioni
		// vuota
		if (specifiche.matches("")) return false;
		// la sequenza di dichiarazioni non può iniziare con
		// un punto e virgola
		if (specifiche.matches("\\s*\\;(.)*")) return false;
		// la sequenza di dichiarazioni non può terminare con
		// un punto e virgola
		if (specifiche.matches("(.)*\\;\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		String adss = new String();
		try {
			// si verificano tutte le dichiarazioni tra istanze
			// di elementi architetturali separate da punti e
			// virgola
			int c = 0;
			while (s.hasNext())
				{
				adss = s.next();
				if (!ScanAEIdecl.isAEIdecl(adss)) return false;
				c++;
				}
			if (c==0) return false;
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di dichiarazioni di istanze
	 * di elementi architetturali separati da punti e virgola,
	 * generando un array di oggetti AEIdecl.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti AEIdecl.
	 * @throws ScanException
	 */
	private static AEIdecl[] scanADS(String specifiche)
		throws ScanException
		{
		AEIdecl[] ads = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		String aad = new String();
		int c = 0;
		try {
			// non viene accettata una sequenza di dichiarazioni
			// vuota
			if (specifiche.matches(""))
				throw new InputMismatchException();
			// la sequenza di dichiarazioni non può iniziare con
			// un punto e virgola
			if (specifiche.matches("\\s*\\;(.)*"))
				throw new InputMismatchException();
			// la sequenza di dichiarazioni non può terminare con
			// un punto e virgola
			if (specifiche.matches("(.)*\\;\\s*\\z"))
				throw new InputMismatchException();
			// si conta il numero di dichiarazioni
			while (s.hasNext())
				{
				aad = s.next();
				c++;
				}
			// Deve esserci almeno una dichiarazione di istanza di
			// elemento architetturale
			if (c == 0) throw new ScanException("Must be at least one declaration of architectural element instance");
			ads = new AEIdecl[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\;\\s*");
			// si scannerizzano le dichiarazioni di istanze
			// di elementi architetturali
			for (int i = 0; i < c; i++)
				{
				aad = s.next();
				ads[i] = ScanAEIdecl.scanAEIdecl(aad);
				}
			return ads;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AEIdecl objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde alle
	 * interazioni di un tipo di elemento architetturale secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isArchiElemInstances(String specifiche)
		{
		String aas = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_ELEM_INSTANCES\\s*");
			s.useDelimiter("\\s*\\z");
			// aas è una sequenza di dichiarazioni di istanze
			// di elementi architetturali separate da punti
			// e virgola
			aas = s.next();
			return isADS(aas);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiElemInstances, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiElemInstances.
	 * @throws ScanException
	 */
	public static ArchiElemInstances scanArchiElemInstances(String specifiche)
		throws ScanException
		{
		ArchiElemInstances aei = new ArchiElemInstances();
		String aas = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_ELEM_INSTANCES\\s*");
			s.useDelimiter("\\s*\\z");
			// aas è una sequenza di dichiarazioni di istanze
			// di elementi architetturali separate da punti
			// e virgola
			aas = s.next();
			aei.setAEIdeclSeq(scanADS(aas));
			return aei;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ArchiElemInstances object",e);
			}
		}
}
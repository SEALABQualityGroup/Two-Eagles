package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ArchiElemTypes;
import specificheAEmilia.ElemType;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di tipi di elementi architetturali, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * La prima sezione di una specifica AEmilia inizia con la parola
 * chiave ARCHI_ELEM_TYPES ed è composta da una sequenza non
 * vuota di definizioni AET (oggetti ElemType)
 */

public class ScanArchiElemTypes {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di tipi di elementi architetturali.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isETS(String specifiche)
		{
		// specifiche deve iniziare con ELEM_TYPE
		if (!specifiche.matches("\\s*ELEM_TYPE\\s*(.)+"))
			return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*ELEM_TYPE\\s*");
		String et = new String();
		try {
			// si verifica se specifiche è composta da una
			// sequenza di dichiarazioni di tipi di elementi
			// architetturali
			int c = 0;
			while (s.hasNext())
				{
				et = "ELEM_TYPE "+s.next();
				if (!ScanElemType.isElemType(et)) return false;
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
	 * Scannerizza una sequenza di dichiarazioni di tipi
	 * di elementi architetturali, generando un array di oggetti
	 * ElemType.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti ElemType.
	 * @throws ScanException
	 */
	private static ElemType[] scanETS(String specifiche)
		throws ScanException
		{
		ElemType[] aets = null;
		MyScanner s = new MyScanner(specifiche);
		s.skip("\\s*ELEM_TYPE\\s*");
		s.useDelimiter("\\s*ELEM_TYPE\\s*");
		String et = new String();
		try {
			int c = 0;
			// si contano le dichiarazioni di tipi elemento
			while (s.hasNext())
				{
				et = s.next();
				c++;
				}
			// il numero di tipi di elementi architetturali
			// deve essere maggiore di zero
			if (c == 0) throw new ScanException(
					"The architectural elements types number must be greater than zero");
			aets = new ElemType[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*ELEM_TYPE\\s*");
			// si scannerizzano i tipi di elementi
			for (int i = 0; i < c; i++)
				{
				et = "ELEM_TYPE "+s.next();
				aets[i] = ScanElemType.scanElemType(et);
				}
			return aets;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ElemType objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * a dichiarazioni di tipi di elementi architetturali secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isArchiElemTypes(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		String ets = new String();
		try {
			s.skip("\\s*ARCHI_ELEM_TYPES\\s*");
			s.useDelimiter("\\s*\\z");
			// ets indica una sequenza di dichiarazioni di tipi
			// di elementi architetturali
			ets = s.next();
			return isETS(ets);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiElemTypes, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiElemTypes.
	 * @throws ScanException
	 */
	public static ArchiElemTypes scanArchiElemTypes(String specifiche)
		throws ScanException
		{
		ArchiElemTypes aet = new ArchiElemTypes();
		MyScanner s = new MyScanner(specifiche);
		String ets = new String();
		try {
			s.skip("\\s*ARCHI_ELEM_TYPES\\s*");
			s.useDelimiter("\\s*\\z");
			// ets indica una sequenza di dichiarazioni di tipi
			// di elementi architetturali
			ets = s.next();
			aet.setElementTypes(scanETS(ets));
			return aet;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ArchiElemTypes object",e);
			}
		}
}
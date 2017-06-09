package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ArchiInteractions;
import specificheAEmilia.InteractionDecl;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di interazioni architetturali, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Le interazioni architetturali sono dichiarati attraverso la
 * seguente sintassi:
 *
 * "ARCHI_INTERACTIONS" <pe_architectural_interaction_decl>
 *
 * dove <pe_architectural_interaction_decl> è o void o una
 * sequenza non vuota di dichiarazioni di interazioni
 * architetturali separate da punti e virgola. Una dichiarazione
 * di interazione architetturale ha la seguente forma:
 *
 * <architectural_interaction_decl> ::= <identifier> ["[" <expr> "]"] "." <identifier>
 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 * <identifier> "[" <expr> "]" "." <identifier>
 */

public class ScanArchiInteractions {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di interazioni architetturali.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isPAID(String specifiche)
		{
		// specifiche non può iniziare con un punto e virgola
		if (specifiche.matches("\\s*\\;(.)*")) return false;
		// specifiche non può terminare con un punto e virgola
		if (specifiche.matches("(.)*\\;\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		if (s.hasNext("\\s*void\\s*\\z")) return true;
		s.useDelimiter("\\s*\\;\\s*");
		String aid = new String();
		try {
			// verifica se è una sequenza di dichiarazioni
			// di interazioni architetturali
			while (s.hasNext())
				{
				aid = s.next();
				if (!ScanInteractionDecl.isInteractionDecl(aid))
					return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di dichiarazioni di interazioni,
	 * generando un array di oggetti InteractionDecl.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti InteractionDecl.
	 * @throws ScanException
	 */
	private static InteractionDecl[] scanPAID(String specifiche)
		throws ScanException
		{
		InteractionDecl[] ids = null;
		MyScanner s = new MyScanner(specifiche);
		if (s.hasNext("\\s*void\\s*\\z")) return null;
		s.useDelimiter("\\s*\\;\\s*");
		String aid = new String();
		int c = 0;
		try {
			// specifiche non può iniziare con un punto e virgola
			if (specifiche.matches("\\s*\\;(.)*"))
				throw new InputMismatchException();
			// specifiche non può terminare con un punto e virgola
			if (specifiche.matches("(.)*\\;\\s*\\z"))
				throw new InputMismatchException();
			// si contano le dichiarazioni
			while (s.hasNext())
				{
				aid = s.next();
				c++;
				}
			ids = new InteractionDecl[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\;\\s*");
			// si scannerizzano le dichiarazioni
			for (int i = 0; i < c; i++)
				{
				aid = s.next();
				ids[i] = ScanInteractionDecl.scanInteractionDecl(aid);
				}
			return ids;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not InteractionDecl objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * a dichiarazioni di interazioni architetturali secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isArchiInteractions(String specifiche)
		{
		String ais = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_INTERACTIONS\\s*");
			s.useDelimiter("\\s*\\z");
			// ais è una sequenza di dichiarazioni di
			// interazioni architetturali separate da punti e
			// virgola
			ais = s.next();
			return isPAID(ais);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiInteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiInteractions.
	 * @throws ScanException
	 */
	public static ArchiInteractions scanArchiInteractions(String specifiche)
		throws ScanException
		{
		ArchiInteractions ai = new ArchiInteractions();
		String ais = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_INTERACTIONS\\s*");
			s.useDelimiter("\\s*\\z");
			// ais è una sequenza di dichiarazioni di
			// interazioni architetturali separate da punti e
			// virgola
			ais = s.next();
			ai.setInteractions(scanPAID(ais));
			return ai;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ArchiInteractions object",e);
			}
		}
}

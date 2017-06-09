package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.AttacDecl;

/**
 * Classe utilizzata per scannerizzare ogni parte delle
 * dichiarazioni di collegamenti architetturali, secondo la
 * grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * I collegamenti architetturali sono dichiarati attraverso la
 * seguente sintassi:
 *
 * "ARCHI_ATTACHMENTS" <pe_architectural_attachment_decl>
 *
 * dove <pe_architectural_attachment_decl> è o void o una sequenza
 * non vuota di dichiarazioni di collegamenti architetturali
 * separati da punti e virgole.
 *
 * <architectural_attachment_decl> ::=
 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 * ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 */

public class ScanArchiAttac {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza non
	 * vuota di dichiarazioni di collegamenti architetturali
	 * separate da punti e virgola.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isPAAD(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		// la sequenza di collegamenti architetturali
		// può essere vuota
		if (s.hasNext("\\s*void\\s*\\z")) return true;
		// la sequenza di collegamenti architeturali non può
		// iniziare con un punto e virgola
		if (specifiche.matches("\\s*\\;(.)*")) return false;
		// la sequenza di collegamenti architetturali non può
		// terminare con un punto e virgola
		if (specifiche.matches("(.)*\\;\\s*\\z")) return false;
		s.useDelimiter("\\s*\\;\\s*");
		String aad = new String();
		try {
			// si verifica se tra punti e virgola ci sono
			// collegamenti architetturali
			while (s.hasNext())
				{
				aad = s.next();
				if (!ScanAttacDecl.isAttacDecl(aad)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di dichiarazioni di
	 * collegamenti architetturali separate da punti e virgola,
	 * generando un array di oggetti AttacDecl.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti AttacDecl.
	 * @throws ScanException
	 */
	private static AttacDecl[] scanPAAD(String specifiche)
		throws ScanException
		{
		AttacDecl[] aas = null;
		MyScanner s = new MyScanner(specifiche);
		// void corrisponde a null
		if (s.hasNext("\\s*void\\s*\\z")) return null;
		s.useDelimiter("\\s*\\;\\s*");
		String aad = new String();
		int c = 0;
		try {
			// la sequenza non può iniziare con un punto e
			// virgola
			if (specifiche.matches("\\s*\\;(.)*"))
				throw new InputMismatchException();
			// la sequenza non può terminare con un punto e
			// virgola
			if (specifiche.matches("(.)*\\;\\s*\\z"))
				throw new InputMismatchException();
			// vengono contate le dichiarazioni di collegamenti
			// architetturali
			while (s.hasNext())
				{
				aad = s.next();
				c++;
				}
			aas = new AttacDecl[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\;\\s*");
			// vengono scannerizzate le dichiarazioni di
			// collegamenti architetturali
			for (int i = 0; i < c; i++)
				{
				aad = s.next();
				aas[i] = ScanAttacDecl.scanAttacDecl(aad);
				}
			return aas;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AttacDecl objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde a
	 * dichiarazioni di collegamenti architetturali secondo la
	 * grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isArchiAttac(String specifiche)
		{
		String aas = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_ATTACHMENTS\\s*");
			s.useDelimiter("\\s*\\z");
			// aas contiene la sequenza di dichiarazioni di
			// collegamenti architetturali
			aas = s.next();
			return isPAAD(aas);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiAttachments, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiAttachments.
	 * @throws ScanException
	 */
	public static ArchiAttachments scanArchiAttac(String specifiche)
		throws ScanException
		{
		ArchiAttachments aa = new ArchiAttachments();
		String aas = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_ATTACHMENTS\\s*");
			s.useDelimiter("\\s*\\z");
			// aas contiene la sequenza di dichiarazioni di
			// collegamenti architetturali
			aas = s.next();
			aa.setAttachments(scanPAAD(aas));
			return aa;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ArchiAttachments object",e);
			}
		}
}
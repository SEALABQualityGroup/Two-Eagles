package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.AEIdeclInd;
import specificheAEmilia.Expression;

/**
 * Classe utilizzata per scannerizzare ogni parte di una
 * dichiarazione di istanza di un elemento architetturale,
 * dettata dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
 *
 * Nella sua forma piÃ¹ semplice, una dichiarazione AEI contiene
 * l'identificatore di un AEI, un'espressione intera racchiusa tra
 * parentesi quadre, che rappresenta un selettore e deve essere
 * libero da identificatori non dichiarati e invocazioni a
 * generatori di numeri pseudo-casuali, l'identificatore dell'AET
 * relativo, che deve essere stato definito nella prima sezione
 * della specifica AEmilia, e una sequenza possibilmente vuota di
 * espressioni libere da invocazioni a generatori di numeri
 * pseudo-casuali, che forniscono i valori attuali per i parametri
 * formali costanti dell'AET. I soli identificatori che si possono
 * presentare nell'espressione di selezione e nei parametri
 * attuali sono i parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale.
 * La seconda forma è utile per dichiarare in maniera concisa
 * diverse istanze dello stesso AET attraverso un meccanismo di
 * indicizzazione. Questa richiede la specifica
 * dell'identificatore indice, che si può presentare
 * nell'espressione di selezione e nei parametri attuali, insieme
 * con il suo intervallo, che è dato da due espressioni intere.
 * Queste due espressioni devono essere libere da identificatori
 * non dichiarati e invocazioni a generatori di numeri
 * pseudo-casuali, con il valore della prima espressione non piÃ¹
 * grande del valore della seconda espressione. Anche nella forma
 * piÃ¹ semplice di una dichiarazione di AEI l'identificatore
 * di un AEI può essere aumentato con un'espressione di selezione.
 * Questo è utile ogni volta è desiderabile dichiarare un insieme
 * di istanze indicizzate dello stesso AET, ma solo qualcuna di
 * loro ha un'espressione di selezione comune.
 */

public class ScanAEIdecl {

	/**
	 * Restituisce true se e solo se specifiche è una sequenza
	 * di espressioni separate da virgole.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isExprSeq(String specifiche)
		{
		// la sequenza di espressioni può essere vuota
		if (specifiche.equals(new String())) return true;
		// la sequenza di espressioni non può iniziare
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// la sequenza di espressioni non può terminare
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		String expra = new String();
		try {
			// si verifica se ogni sottostringa tra due virgole
			// sia un'espressione
			while (s.hasNext())
				{
				expra = s.next();
				if (!ScanExp.isEspressione(expra)) return false;
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return true;
		}

	/**
	 * Restituisce un array di oggetti Expression, dettato dalla
	 * stringa specifiche.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti Expression.
	 * @throws ScanException
	 */
	private static Expression[] scanExprSeq(String specifiche)
		throws ScanException
		{
		Expression[] ExprSeq = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		int c = 0;
		try {
			// la sequenza di espressioni non può iniziare
			// con una virgola
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			// la sequenza di espressioni non può terminare
			// con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			// si contano le espressioni presenti nella sequenza
			while (s.hasNext())
				{
				c++;
				s.next();
				}
			ExprSeq = new Expression[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			String expra = new String();
			// si scannerizzano tutte le espressioni
			for (int i = 0; i < c; i++)
				{
				expra = s.next();
				ExprSeq[i] = ScanExp.scanEspressione(expra);
				}
			return ExprSeq;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Expression objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una dichiarazione
	 * di un'istanza di elemento architetturale semplice secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isAEIdeclP(String specifiche)
		{
		/*
		 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
		 */
		MyScanner s = new MyScanner(specifiche);
		String i = new String();
		String e1 = new String();
		String it = new String();
		String se = new String();
		boolean ris = false;
		try {
			s.useDelimiter("\\s*\\:\\s*");
			// i contiene il nome dell'AEI con o senza selettore
			i = s.next();
			if (i.contains("["))
				{
				MyScanner s1 = new MyScanner(i);
				s1.useDelimiter("\\s*\\[\\s*");
				i = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// e1 contiene il selettore di AEI
				e1 = s1.next();
				}
			s.useDelimiter("\\s*\\(\\s*");
			s.skip("\\s*\\:\\s*");
			// it contiene il nome dell'AET
			it = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// la sequenza di parametri attuali per istanziare
			// un AET può essere vuota
			if (s.hasNext()) se = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\)\\s*");
			// il selettore può non esserci
			if (e1.equals(""))
				{
				ris = ScanIdent.isIdent(i) && ScanIdent.isIdent(it)
				&& isExprSeq(se);
				}
			else
				{
				ris = ScanIdent.isIdent(i) && ScanIdent.isIdent(it)
				&& isExprSeq(se) && ScanExp.isEspressione(e1);
				}
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un oggetto AEIdecl con le stesse informazioni
	 * contenute in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AEIdecl.
	 * @throws ScanException
	 */
	private static AEIdecl scanAEIdeclP(String specifiche)
		throws ScanException
		{
		/*
		 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
		 */
		AEIdecl ad = new AEIdecl();
		MyScanner s = new MyScanner(specifiche);
		String i = new String();
		String e1 = new String();
		String it = new String();
		String se = new String();
		try {
			s.useDelimiter("\\s*\\:\\s*");
			// i contiene il nome dell'AEI con o senza selettore
			i = s.next();
			if (i.contains("["))
				{
				MyScanner s1 = new MyScanner(i);
				s1.useDelimiter("\\s*\\[\\s*");
				i = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// e1 contiene il selettore di AEI
				e1 = s1.next();
				}
			s.useDelimiter("\\s*\\(\\s*");
			s.skip("\\s*\\:\\s*");
			// it contiene il nome dell'AET
			it = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// la sequenza di parametri attuali per istanziare
			// un AET puÃ  essere vuota
			if (s.hasNext()) se = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\)\\s*");
			ad.setName(ScanIdent.scanIdent(i));
			ad.setAET(ScanIdent.scanIdent(it));
			ad.setActualParams(scanExprSeq(se));
			// il selettore può non esserci
			if (!e1.equals(""))
				ad.setSelector(ScanExp.scanEspressione(e1));
			return ad;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AEIdecl object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche indica una
	 * dichiarazione di istanze di elementi architetturali
	 * indicizzata.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isAEIdeclInd(String specifiche)
		{
		/*
		 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
		 */
		MyScanner s = new MyScanner(specifiche);
		String ind = new String();
		String ei = new String();
		String ef = new String();
		String aei = new String();
		try {
			s.skip("\\s*FOR_ALL\\s*");
			// ind contiene il nome dell'indice
			ind = s.next();
			s.skip("\\s*IN\\s*");
			s.useDelimiter("\\s*\\.\\.\\s*");
			// ei contiene l'espressione iniziale
			ei = s.next();
			s.useDelimiter("\\s*[a-zA-Z_&&[^0-9<>=!()]](\\w*)\\s*\\[");
			s.skip("\\s*\\.\\.\\s*");
			// ef contiene l'espressione finale
			ef = s.next();
			s.useDelimiter("\\s*\\z");
			// aei contiene il nome di un AET con una sequenza
			// di parametri attuali
			aei = s.next();
			return ScanIdent.isIdent(ind) && ScanExp.isEspressione(ei)
			&& ScanExp.isEspressione(ef) && isAEIdeclP(aei);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un oggetto AEIdeclInd generato dalla scansione
	 * di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AEIdeclInd.
	 * @throws ScanException
	 */
	private static AEIdeclInd scanAEIdeclInd(String specifiche)
		throws ScanException
		{
		/*
		 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
		 */
		AEIdeclInd adi = new AEIdeclInd();
		MyScanner s = new MyScanner(specifiche);
		String ind = new String();
		String ei = new String();
		String ef = new String();
		String aei = new String();
		AEIdecl decl = new AEIdecl();
		try {
			s.skip("\\s*FOR_ALL\\s*");
			// ind contiene il nome dell'indice
			ind = s.next();
			s.skip("\\s*IN\\s*");
			s.useDelimiter("\\s*\\.\\.\\s*");
			// ei contiene l'espressione iniziale
			ei = s.next();
			s.useDelimiter("\\s*[a-zA-Z_&&[^0-9]](\\w*)\\s*\\[");
			s.skip("\\s*\\.\\.\\s*");
			// ef contiene l'espressione finale
			ef = s.next();
			s.useDelimiter("\\s*\\z");
			// aei contiene il nome di un AET con una sequenza
			// di parametri attuali
			aei = s.next();
			adi.setIndex(ScanIdent.scanIdent(ind));
			adi.setBeginningExpr(ScanExp.scanEspressione(ei));
			adi.setEndingExpr(ScanExp.scanEspressione(ef));
			decl = scanAEIdeclP(aei);
			adi.setName(decl.getName());
			adi.setActualParams(decl.getActualParams());
			adi.setSelector(decl.getSelector());
			adi.setAET(decl.getAET());
			return adi;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AEIdeclInd object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una dichiarazione
	 * di istanza di elemento architetturale secondo la
	 * grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isAEIdecl(String specifiche)
		{
		/*
		 * <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
		 */
		return isAEIdeclP(specifiche) || isAEIdeclInd(specifiche);
		}

	/**
	 * Crea un oggetto AEIdecl scannerizzando specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AEIdecl.
	 * @throws ScanException
	 */
	public static AEIdecl scanAEIdecl(String specifiche)
		throws ScanException
		{
		AEIdecl d = new AEIdecl();
		if (isAEIdeclP(specifiche)) d = scanAEIdeclP(specifiche);
		else if (isAEIdeclInd(specifiche))
			d = scanAEIdeclInd(specifiche);
		else throw new ScanException(specifiche+" is not architectural instances declaration");
		return d;
		}
}
package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ActionProcess;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.Boolean;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.Stop;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di un termine di processo, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * Un termine di processo segue l'intestazione di equazione comportamentale ed è
 * definita nel seguente modo:
 *
 * <process_term> ::= "stop"
 * | <action> "." <process_term_1>
 * | "choice" "{" <process_term_2_sequence> "}"
 * <process_term_1> ::= <process_term>
 * | <identifier> "(" <actual_par_sequence> ")"
 * <process_term_2> ::= ["cond" "(" <expr> ")" "->"] <process_term>
 *
 * <process_term_2_sequence> è una sequenza di almeno due termini di processo separati
 * da virgole, ognuna possibilmente preceduta da un'espressione booleana che stabilisce
 * la condizione sotto cui è disponibile.
 * <actual_par_sequence> è una sequenza possibilmente vuota di espressioni.
 */

public class ScanProcessTerm {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un termine di processo la cui esecuzione può essere
	 * condizionata da un'espressione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isProcTerm2(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\-\\s*\\>\\s*");
		try {
			if (s.hasNext("\\s*cond\\s*\\((.)*\\)\\s*"))
				{
				s.skip("\\s*cond\\s*\\(\\s*");
				s.useDelimiter("\\s*\\)\\s*\\-\\s*\\>\\s*");
				String e = s.next();
				s.skip("\\s*\\)\\s*\\-\\s*\\>\\s*");
				s.useDelimiter("\\s*\\z");
				String pt = s.next();
				return ScanExp.isEb(e) && isProcessTerm(pt);
				}
			s.useDelimiter("\\s*\\z");
			String st = s.next();
			return isProcessTerm(st);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ProcessTerm, includendo informazioni
	 * ottenute attraverso la scannerizzazione di un termine
	 * di processo la cui esecuzione può essere condizionata
	 * da un'espressione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ProcessTerm.
	 * @throws ScanException
	 */

	public static ProcessTerm scanProcTerm2(String specifiche)
		throws ScanException
		{
		// <process_term_2> ::= ["cond" "(" <expr> ")" "->"] <process_term>
		ProcessTerm pt = new ProcessTerm();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\-\\s*\\>\\s*");
		try {
			if (s.hasNext("\\s*cond\\s*\\((.)*\\)\\s*"))
				{
				s.skip("\\s*cond\\s*\\(\\s*");
				s.useDelimiter("\\s*\\)\\s*\\-\\s*\\>\\s*");
				String e = s.next();
				s.skip("\\s*\\)\\s*\\-\\s*\\>\\s*");
				s.useDelimiter("\\s*\\z");
				String st = s.next();
				pt = scanProcessTerm(st);
				pt.setCondition(ScanExp.scanEspressione(e));
				return pt;
				}
			s.useDelimiter("\\s*\\z");
			String st = s.next();
			return scanProcessTerm(st);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ProcessTerm object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di parametri attuali separate da virgole.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isActParSeq(String specifiche)
		{
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		try {
			while (s.hasNext())
				{
				String st = s.next();
				if (!ScanExp.isEspressione(st)) return false;
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return true;
		}

	/**
	 * Scannerizza una sequenza di parametri attuali,
	 * generando un array di oggetti Expression.
	 *
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti Expression.
	 * @throws ScanException
	 */

	private static Expression[] scanActParSeq(String specifiche)
		throws ScanException
		{
		// <actual_par_sequence>
		Expression[] pars = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		try {
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			int contExp = 0;
			while (s.hasNext())
				{
				contExp++;
				s.next();
				}
			pars = new Expression[contExp];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			for (int i = 0; i < contExp; i++)
				{
				String st = s.next();
				pars[i]=ScanExp.scanEspressione(st);
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not actual parameters sequence",e);
			}
		return pars;
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di termini di processi eventalmente
	 * condizionati da un'espressione, separate da virgole.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isProcTerm2Seq(String specifiche)
		{
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		String pt = new String();
		try {
			int c = 0;
			while (s.hasNext())
				{
				pt = s.next();
				while (!isProcTerm2(pt))
					{
					try {
						if (s.hasNext())
							{
							pt = pt + "," + s.next();
							}
						else return false;
						}
					catch (NoSuchElementException e)
						{
						return false;
						}
					}
				c++;
				}
			if (c < 2) return false;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return true;
		}

	/**
	 * Scannerizza una sequenza di dichiarazioni di termini di
	 * processo eventualmente condizionati da un'espressione,
	 * generando un array di oggetti ProcessTerm.
	 *
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti ProcessTerm.
	 * @throws ScanException
	 */

	private static ProcessTerm[] scanProcTerm2Seq(String specifiche)
		throws ScanException
		{
		/*
    	 * <process_term_2_sequence> è una sequenza di almeno due termini di processo separati
		 * da virgole, ognuna possibilmente preceduta da un'espressione booleana che stabilisce
		 * la condizione sotto cui è disponibile.
		 */
		ProcessTerm[] pta = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		String pt = new String();
		int contpt = 0;
		try {
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			while (s.hasNext())
				{
				pt = s.next();
				while (!isProcTerm2(pt))
					{
					pt = pt + "," + s.next();
					}
				contpt++;
				}
			// i termini di processo ne devono essere 2
			if (contpt < 2) throw new ScanException("Must be two alternative process");
			pta = new ProcessTerm[contpt];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			for (int i = 0; i < contpt; i++)
				{
				pt = s.next();
				while (!isProcTerm2(pt))
					{
					pt = pt + "," + s.next();
					}
				pta[i] = scanProcTerm2(pt);
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ProcessTerm object sequence",e);
			}
		return pta;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un termine di processo o alla chiamata di un
	 * comportamento.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isProcTerm1(String specifiche)
		{
		return isProcessTerm(specifiche) || isBehavProcess(specifiche);
		}

	/**
	 * Crea un oggetto ProcessTerm, includendo informazioni
	 * ottenute attraverso la scannerizzazione di un termine
	 * di processo o di una chiamata di un comportamento.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ProcessTerm.
	 * @throws ScanException
	 */

	private static ProcessTerm scanProcTerm1(String specifiche)
		throws ScanException
		{
		/*
		 * <process_term_1> ::= <process_term>
		 * | <identifier> "(" <actual_par_sequence> ")"
		 */
		ProcessTerm pt = new ProcessTerm();
		if (isProcessTerm(specifiche)) pt = scanProcessTerm(specifiche);
		else if (isBehavProcess(specifiche)) pt = scanBehavProcess(specifiche);
		else throw new ScanException(specifiche+" is not process term");
		return pt;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un termine di processo secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isProcessTerm(String specifiche)
		{
		return isStop(specifiche) || isActionProcess(specifiche)
		|| isChoiceProcess(specifiche);
		}

	/**
	 * Crea un oggetto ProcessTerm, includendo informazioni
	 * ottenute attraverso la scannerizzazione di un termine
	 * di processo secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ProcessTerm.
	 * @throws ScanException
	 */

	public static ProcessTerm scanProcessTerm(String specifiche)
		throws ScanException
		{
		/*
		 * <process_term> ::= "stop"
		 * | <action> "." <process_term_1>
		 * | "choice" "{" <process_term_2_sequence> "}"
		 * <process_term_1> ::= <process_term>
		 * | <identifier> "(" <actual_par_sequence> ")"
		 * <process_term_2> ::= ["cond" "(" <expr> ")" "->"] <process_term>
		 */
		ProcessTerm pt = new ProcessTerm();
		if (isStop(specifiche)) pt = scanStop(specifiche);
		else if (isActionProcess(specifiche)) pt = scanActionProcess(specifiche);
		else if (isChoiceProcess(specifiche)) pt = scanChoiceProcess(specifiche);
		else throw new ScanException(specifiche+" is not process term");
		return pt;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'azione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isActionProcess(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\>\\s*\\.\\s*");
		try {
			String a = s.next()+">";
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\>\\s*\\.\\s*");
			String pt1 = s.next();
			return ScanAction.isAction(a) && isProcTerm1(pt1);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ActionProcess, includendo informazioni
	 * ottenute attraverso la scannerizzazione di un'azione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionProcess.
	 * @throws ScanException
	 */

	private static ActionProcess scanActionProcess(String specifiche)
		throws ScanException
		{
		// <action> "." <process_term_1>
		ActionProcess ap = new ActionProcess();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\>\\s*\\.\\s*");
			String a = s.next()+">";
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\>\\s*\\.\\s*");
			String pt1 = s.next();
			ap.setCondition(new specificheAEmilia.Boolean(true));
			ap.setAzione(ScanAction.scanAction(a));
			ap.setProcesso(scanProcTerm1(pt1));
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionProcess object",e);
			}
		return ap;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una chiamata di comportamento.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isBehavProcess(String specifiche)
		{
		boolean ris = true;
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\(\\s*");
			String st = s.next();
			if (!ScanIdent.isIdent(st))
				ris = false;
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			if (s.hasNext())
				{
				String aps = s.next();
				ris = ris && isActParSeq(aps);
				}
			else ris = ris && true;
			s.skip("\\s*\\)\\s*");
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto BehavProcess, includendo informazioni
	 * ottenute attraverso la scannerizzazione di una
	 * chiamata di comportamento.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto BehavProcess.
	 * @throws ScanException
	 */

	private static BehavProcess scanBehavProcess(String specifiche)
		throws ScanException
		{
		// <identifier> "(" <actual_par_sequence> ")"
		BehavProcess bp = new BehavProcess();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\(\\s*");
		try {
			String st = s.next();
			String i = ScanIdent.scanIdent(st);
			bp.setName(i);
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			bp.setExprs(new Expression[0]);
			if (s.hasNext())
				{
				String aps = s.next();
				bp.setExprs(scanActParSeq(aps));
				}
			bp.setCondition(new specificheAEmilia.Boolean(true));
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not BehavProcess object",e);
			}
		return bp;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una scelta condizionata tra termini di processo.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isChoiceProcess(String specifiche)
		{
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\{\\s*");
			s.next("\\s*choice\\s*");
			s.useDelimiter("\\s*\\}\\s*\\z");
			s.skip("\\s*\\{\\s*");
			String pt2s = s.next();
			s.skip("\\s*\\}\\s*");
			return isProcTerm2Seq(pt2s);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ChoiceProcess, includendo informazioni
	 * ottenute attraverso la scannerizzazione di una
	 * scelta tra termini di processo.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ChoiceProcess.
	 * @throws ScanException
	 */

	private static ChoiceProcess scanChoiceProcess(String specifiche) throws ScanException
		{
		// "choice" "{" <process_term_2_sequence> "}"
		ChoiceProcess cp = new ChoiceProcess();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\}\\s*\\z");
		try {
			s.skip("\\s*choice\\s*\\{\\s*");
			String st = s.next();
			cp.setProcesses(scanProcTerm2Seq(st));
			cp.setCondition(new Boolean(true));
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ChoiceProcess object",e);
			}
		return cp;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al termine stop.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isStop(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*stop\\s*");
		}

	/**
	 * Crea un oggetto Stop attraverso la scannerizzazione di un
	 * termine stop.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Stop.
	 */

	private static Stop scanStop(String specifiche)
		{
		// "stop"
		Stop s = new Stop();
		s.setCondition(new Boolean(true));
		return s;
		}
}
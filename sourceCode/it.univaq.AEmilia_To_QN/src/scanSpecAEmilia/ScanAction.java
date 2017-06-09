package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.Action;
import specificheAEmilia.ActionInput;
import specificheAEmilia.ActionOutput;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Expression;
import specificheAEmilia.RateExp;
import specificheAEmilia.RateInf;
import specificheAEmilia.Rate_;

/**
 * Classe utilizzata per scannerizzare ogni parte di un'azione
 * presente in una specifica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class ScanAction {

	/**
	 * Restituisce true se e solo se specifiche Ë conforme alla
	 * grammatica AEmilia per un'azione
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isAction(String specifiche)
		{
		/*
		 * <action> ::= "<" <action_type> "," <action_rate> ">"
		 *
		 * <action_type> ::= <identifier>
		 * | <identifier> "?" "(" <local_var_sequence> ")"
		 * | <identifier> "!" "(" <expr_sequence> ")"
		 * <action_rate> ::= "exp" "(" <expr> ")"
		 * | "inf" "(" <expr> "," <expr> ")"
		 * | "inf"
		 * | "_" "(" <expr> "," <expr> ")"
		 * | "_"
		 */
		try {
			MyScanner s = new MyScanner(specifiche);
			s.skip("\\s*<\\s*");
			// il delimitatore cambia a seconda se in specifiche
			// sia presente un'azione semplice o un'azione
			// di input o output
			if (specifiche.contains("?")||specifiche.contains("!"))
				{
				s.useDelimiter("\\s*\\)\\s*,\\s*");
				}
			else
				{
				s.useDelimiter("\\s*,\\s*");
				}
			String tipoAzione = s.next();
			// tipoAzione contiene la specifica di un
			// ActionType
			if (tipoAzione.contains("(")) tipoAzione = tipoAzione + ")";
			s.useDelimiter("\\s*>\\s*");
			s.skip("\\s*\\)?\\s*,\\s*");
			// tassoAzione contiene la specifica di un
			// ActionRate
			String tassoAzione = s.next();
			return isActionType(tipoAzione) && isActionRate(tassoAzione);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un oggetto Action che contiene informazioni
	 * di un'azione presente nelle specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto Action.
	 * @throws ScanException
	 */
	public static Action scanAction(String specifiche)
		throws ScanException
		{
		/*
		 * <action> ::= "<" <action_type> "," <action_rate> ">"
		 *
		 * <action_type> ::= <identifier>
		 * | <identifier> "?" "(" <local_var_sequence> ")"
		 * | <identifier> "!" "(" <expr_sequence> ")"
		 * <action_rate> ::= "exp" "(" <expr> ")"
		 * | "inf" "(" <expr> "," <expr> ")"
		 * | "inf"
		 * | "_" "(" <expr> "," <expr> ")"
		 * | "_"
		 */
		Action a = new Action();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.skip("\\s*<\\s*");
			// il delimitatore cambia a seconda se in specifiche
			// sia presente un'azione semplice o un'azione
			// di input o output
			if (specifiche.contains("?")||specifiche.contains("!"))
				{
				s.useDelimiter("\\s*\\)\\s*,\\s*");
				}
			else
				{
				s.useDelimiter("\\s*,\\s*");
				}
			String tipoAzione = s.next();
			// tipoAzione contiene la specifica di un
			// ActionType
			if (tipoAzione.contains("(")) tipoAzione = tipoAzione + ")";
			s.useDelimiter("\\s*>\\s*");
			s.skip("\\s*\\)?\\s*,\\s*");
			// tassoAzione contiene la specifica di un
			// ActionRate
			String tassoAzione = s.next();
			ActionType t = ScanAction.scanActionType(tipoAzione);
			ActionRate r = ScanAction.scanActionRate(tassoAzione);
			a.setType(t);
			a.setRate(r);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Action object",e);
			}
		return a;
		}

	/**
	 * Restituisce true se e solo se specifiche definisce
	 * un'azione di input secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isActionInput(String specifiche)
		{
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\?\\s*");
			// i contiene l'identificatore dell'azione
			// di input
			String i = s.next();
			s.useDelimiter("\\s*\\)(\\s)*\\z");
			s.skip("\\s*\\?\\s*\\(\\s*");
			// lsv contiene la sequenza di variabili locali
			String lvs = s.next();
			return ScanIdent.isIdent(i) && isLocVarSeq(lvs);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche Ë una sequenza
	 * di variabili locali separate da virgole.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isLocVarSeq(String specifiche)
		{
		try {
			// non viene accettata una sequenza di variabili
			// locali che inizia con una virgola
			if (specifiche.matches("\\s*\\,(.)*")) return false;
			// non viene accettata una sequenza di variabili
			// locali che termina con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			// ogni stringa tra due virgole deve essere un
			// identificatore
			while (s.hasNext())
				{
				String st = s.next();
				if (!ScanIdent.isIdent(st)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un array di identificatori che costituisce
	 * una sequenza di variabili locali separate da virgole,
	 * come indicato in specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti String.
	 * @throws ScanException
	 */
	private static String[] scanLocVarSeq(String specifiche)
		throws ScanException
		{
		try {
			// non viene accettata una sequenza di variabili
			// locali che inizia con una virgola
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			// non viene accettata una sequenza di variabili
			// locali che termina con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			// si contano quante variabili locali ci sono
			int i = 0;
			while (s.hasNext())
				{
				s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				i++;
				}
			String[] ris = new String[i];
			i = 0;
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*,\\s*");
			// ogni stringa tra due virgole deve essere un
			// identificatore
			while (s.hasNext())
				{
				String st = s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				ris[i] = ScanIdent.scanIdent(st);
				i++;
				}
			return ris;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not local variable sequence",e);
			}
		}

	/**
	 * Restituisce un'azione di input secondo le informazioni
	 * contenute in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionInput.
	 * @throws ScanException
	 */
	private static ActionInput scanActionInput(String specifiche)
		throws ScanException
		{
		/*
		 * <identifier> "?" "(" <local_var_sequence> ")"
		 */
		/*
		 * istruzioni per il testing.
		 System.out.println("la specifica per scanActionInput Ë: "+specifiche);
		 */
		ActionInput ai = new ActionInput();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\?\\s*\\(\\s*");
			// st1 contiene l'identificatore dell'azione di input
			String st1 = s.next();
			ai.setName(st1);
			s.skip("\\s*\\?\\s*\\(\\s*");
			s.useDelimiter("\\s*\\)(\\s)*\\z");
			// lvs contiene la sequenza di variabili locali
			String lvs = s.next();
			ai.setInputVariables(scanLocVarSeq(lvs));
			return ai;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionInput object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche Ë una sequenza
	 * di espressioni separate da virgole.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isExprSeq(String specifiche)
		{
		try {
			// non viene accettata una sequenza di espressioni
			// che inizi con una virgola
			if (specifiche.matches("\\s*\\,(.)*")) return false;
			// non viene accettata una sequenza di espressioni
			// che termini con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			// si verifica che ogni sottostringa tra due
			// virgole sia un'espressione
			while (s.hasNext())
				{
				String st = s.next();
				if (!ScanExp.isEspressione(st)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un array di espressioni, scansionando una
	 * sequenza di espressioni separate da virgole.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti Expression.
	 * @throws ScanException
	 */
	private static Expression[] scanExprSeq(String specifiche)
		throws ScanException
		{
		try {
			// non viene accettata una sequenza di espressioni
			// che inizi con una virgola
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			// non viene accettata una sequenza di espressioni
			// che termini con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			// si contano le sottostringhe tra due virgole
			int i = 0;
			while (s.hasNext())
				{
				i++;
				s.next();
				}
			Expression[] v = new Expression[i];
			i = 0;
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*,\\s*");
			// si scannerizzano come espressioni le sottostringhe
			// tra due virgole
			while (s.hasNext())
				{
				String st = s.next();
				v[i] = ScanExp.scanEspressione(st);
				i++;
				}
			return v;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not expressions sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche rappresenta
	 * un'azione di output secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isActionOutput(String specifiche)
		{
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\!\\s*");
			// i contiene il nome dell'azione di output
			String i = s.next();
			s.useDelimiter("\\s*\\)(\\s)*\\z");
			s.skip("\\s*\\!\\s*\\(\\s*");
			// lvs contiene una sequenza di espressioni di output
			// separate da virgole
			String lvs = s.next();
			return ScanIdent.isIdent(i) && isExprSeq(lvs);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un oggetto ActionOutput contenente informazioni
	 * presenti in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionOutput.
	 * @throws ScanException
	 */
	private static ActionOutput scanActionOutput(String specifiche)
		throws ScanException
		{
		/*
		 * <identifier> "!" "(" <expr_sequence> ")"
		 */
		// rendere pi√π leggibile con l'utilizzo di opportuni pattern
		ActionOutput ao = new ActionOutput();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\!\\s*");
			// st1 contiene il nome dell'azione di output
			String st1 = s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			ao.setName(st1);
			s.useDelimiter("\\s*\\)(\\s)*\\z");
			s.skip("\\s*\\!\\s*\\(\\s*");
			// lvs contiene una sequenza di espressioni
			// di output separate da virgole
			String lvs = s.next();
			ao.setOutputExprs(scanExprSeq(lvs));
			return ao;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionOutput object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche Ë un tasso azione
	 * secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isActionRate(String specifiche)
		{
		return isRateExp(specifiche) ||
		isRateInf(specifiche) ||
		isRate_(specifiche);
		}

	/**
	 * Restituisce un oggetto ActionRate in accordo alle
	 * informazioni contenute in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionRate.
	 * @throws ScanException
	 */
	private static ActionRate scanActionRate(String specifiche)
		throws ScanException
		{
		/*
		 * <action_rate> ::= "exp" "(" <expr> ")"
		 * | "inf" "(" <expr> "," <expr> ")"
		 * | "inf"
		 * | "_" "(" <expr> "," <expr> ")"
		 * | "_"
		 */
		ActionRate r = new ActionRate();
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*[(>]\\s*");
			// se specifiche contiene exp allora corrisponde
			// ad un tasso di un'azione temporizzata
			// esponenzialmente
			if (s.hasNext("\\s*exp\\s*"))
				r = ScanAction.scanRateExp(specifiche);
			// se specifiche contiene inf allora corrisponde
			// al tasso di un'azione immediata
			else if (s.hasNext("\\s*inf\\s*"))
				r = ScanAction.scanRateInf(specifiche);
			// se specifiche contiene _ allora corrisponde
			// al tasso di un'azione passiva
			else if (s.hasNext("\\s*_\\s*"))
				r = ScanAction.scanRate_(specifiche);
			else throw new ScanException(specifiche+
				" is not ActionRate object");
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionRate object",e);
			}
		return r;
		}

	/**
	 * Restituisce true se e solo se specifiche Ë il tasso di
	 * un'azione passiva secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isRate_(String specifiche)
		{
		/*
  		 * "_" "(" <expr> "," <expr> ")"
		 * | "_"
		 */
		boolean ris = true;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*_\\s*");
			if (s.hasNext("\\s*\\((.)*"))
				{
				s.useDelimiter("\\s*,\\s*");
				s.skip("\\s*\\(\\s*");
				// st corriponde alla priorit√† dell'azione
				String st = s.next();
				if (ScanExp.isEa(st)) ris = ris && true;
				else ris = ris && false;
				s.useDelimiter("\\s*\\)\\s*\\z");
				s.skip("\\s*,\\s*");
				// st1 corrisponde al peso dell'azione
				String st1 = s.next();
				if (ScanExp.isEa(st1)) ris = ris && true;
				else ris = ris && false;
				s.useDelimiter("\\s*\\z");
				if (s.hasNext("\\s*\\)\\s*")) ris = ris && true;
				else ris = ris && false;
				}
			else ris = ris && true;
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce un oggetto ActionRate secondo le informazioni
	 * dettate da specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionRate.
	 * @throws ScanException
	 */
	private static ActionRate scanRate_(String specifiche)
		throws ScanException
		{
		MyScanner s = new MyScanner(specifiche);
		Rate_ r = new Rate_();
		try {
			s.skip("\\s*_\\s*");
			if (s.hasNext("\\s*\\((.)*"))
				{
				s.useDelimiter("\\s*,\\s*");
				s.skip("\\s*\\(\\s*");
				// st corrisponde alla priorit√† dell'azione
				String st = s.next();
				r.setPrio(ScanExp.scanEa(st));
				s.useDelimiter("\\s*\\)\\s*\\z");
				s.skip("\\s*,\\s*");
				// st1 corrisponde al peso dell'azione
				String st1 = s.next();
				r.setWeight(ScanExp.scanEa(st1));
				s.skip("\\s*\\)\\s*\\z");
				}
			else
				{
				r.setPrio(new specificheAEmilia.Integer(1));
				r.setWeight(new specificheAEmilia.Real(1));
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionRate object",e);
			}
		return r;
		}

	/**
	 * Restituisce true se e solo se in specifiche Ë presente il
	 * tasso di un'azione immediata, secondo la grammatica
	 * AEmilia
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isRateInf(String specifiche)
		{
		boolean ris = true;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*inf\\s*");
			if (s.hasNext("\\s*\\((.)*"))
				{
				s.useDelimiter("\\s*,\\s*");
				s.skip("\\s*\\(\\s*");
				// st corrisponde alla priorit√† dell'azione
				String st = s.next();
				if (ScanExp.isEa(st)) ris = ris && true;
				else ris = ris && false;
				s.useDelimiter("\\s*\\)\\s*\\z");
				s.skip("\\s*,\\s*");
				// st1 corrisponde al peso dell'azione
				String st1 = s.next();
				if (ScanExp.isEa(st1)) ris = ris && true;
				else ris = ris && false;
				s.useDelimiter("\\s*\\z");
				if (s.hasNext("\\s*\\)\\s*")) ris = ris && true;
				else ris = ris && false;
				return ris;
				}
			else ris = ris && true;
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Produce un'oggetto ActionRate secondo le informazioni
	 * contenute in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionRate.
	 * @throws ScanException
	 */
	private static ActionRate scanRateInf(String specifiche) throws ScanException
		{
		MyScanner s = new MyScanner(specifiche);
		RateInf r = new RateInf();
		try {
			s.skip("\\s*inf\\s*");
			if (s.hasNext("\\s*\\((.)*"))
				{
				s.useDelimiter("\\s*,\\s*");
				s.skip("\\s*\\(\\s*");
				// st corrisponde alla priorit√† dell'azione
				String st = s.next();
				r.setPrio(ScanExp.scanEa(st));
				s.useDelimiter("\\s*\\)\\s*\\z");
				s.skip("\\s*,\\s*");
				// st1 corrisponde al peso dell'azione
				String st1 = s.next();
				r.setWeight(ScanExp.scanEa(st1));
				s.skip("\\s*\\)\\s*\\z");
				}
			else
				{
				r.setPrio(new specificheAEmilia.Integer(1));
				r.setWeight(new specificheAEmilia.Real(1));
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
					" is not ActionRate object",e);
			}
		return r;
		}

	/**
	 * Restituisce true se e solo se specifiche indica il tasso
	 * di un'azione temporizzata esponenzialmente.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isRateExp(String specifiche)
		{
		boolean ris = true;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*exp\\s*\\(\\s*");
			s.useDelimiter("\\s*\\)\\s*\\z");
			// ea corrisponde alla durata dell'azione
			String ea = s.next();
			if (ScanExp.isEa(ea)) ris = ris && true;
			else ris = ris && false;
			s.useDelimiter("\\s*\\z");
			if (s.hasNext("\\s*\\)\\s*")) ris = ris && true;
			else ris = ris && false;
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ActionRate secondo le informazioni
	 * presenti in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionRate.
	 * @throws ScanException
	 */
	private static ActionRate scanRateExp(String specifiche)
		throws ScanException
		{
		MyScanner s = new MyScanner(specifiche);
		RateExp r = new RateExp();
		try {
			s.skip("\\s*exp\\s*\\(\\s*");
			s.useDelimiter("\\s*\\)\\s*\\z");
			// ea corrisponde alla durata dell'azione
			String ea = s.next();
			r.setExpr(ScanExp.scanEa(ea));
			s.skip("\\s*\\)\\s*\\z");
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ActionRate object",e);
			}
		return r;
		}

	/**
	 * Restituisce true se e solo se specifiche rappresenta un
	 * tipo azione secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isActionType(String specifiche)
		{
		return ScanIdent.isIdent(specifiche) ||
		isActionInput(specifiche) ||
		isActionOutput(specifiche);
		}

	/**
	 * Restituisce un oggetto ActionType secondo le informazioni
	 * contenute in specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ActionType.
	 * @throws ScanException
	 */
	private static ActionType scanActionType(String specifiche)
		throws ScanException
		{
		/*
		 * <action_type> ::= <identifier>
		 * | <identifier> "?" "(" <local_var_sequence> ")"
		 * | <identifier> "!" "(" <expr_sequence> ")"
		 */
		// rendere pi√π leggibile con l'utilizzo di opportuni pattern
		ActionType t = new ActionType();
		if (isActionInput(specifiche))
			t = ScanAction.scanActionInput(specifiche);
		else if (isActionOutput(specifiche))
			t = ScanAction.scanActionOutput(specifiche);
		else if (ScanIdent.isIdent(specifiche))
			t.setName(ScanIdent.scanIdent(specifiche));
		else
			throw new ScanException(specifiche+" is not action type");
		return t;
		}
}
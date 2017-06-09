package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.Const;
import specificheAEmilia.ConstInit;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.Header;
import specificheAEmilia.Local;
import specificheAEmilia.VariableDeclaration;
import specificheAEmilia.VarInit;

/**
 * Classe utilizzata per scannerizzare ogni parte di
 * un'intestazione, secondo la grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * Questa classe viene utilizzata per la definizione di
 * un'intestazione in una specifica AEmilia. Un'intestazione
 * viene utilizzata dopo la parola chiave "ARCHI_TYPE", "ELEM_TYPE"
 * e nella definizione di un'equazione comportamentale.
 */

public class ScanIntestazione {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di costante.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isConst(String specifiche)
		{
		/*
		 * "const" <data_type> <identifier> è una dichiarazione di
		 * parametro formale costante utilizzata nell'intestazione
		 * di un AET, il cui valore è definito nella dichiarazione
		 * delle istanze dell'AET nella sezione di topologia
		 * architetturale.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*const\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeConst è il nome della costante
			String nomeConst = s.next();
			s.skip("\\s*\\z");
			return ScanTipoDato.isTipoDato(tipoDato) &&
			ScanIdent.isIdent(nomeConst);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Const, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Const.
	 * @throws ScanException
	 */

	private static Const scanConst(String specifiche)
		throws ScanException
		{
		Const c = new Const();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*const\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeConst è il nome della costante
			String nomeConst = s.next();
			s.skip("\\s*\\z");
			c.setName(ScanIdent.scanIdent(nomeConst));
			c.setType(ScanTipoDato.scanTipoDato(tipoDato));
			return c;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Const object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di costante inizializzata.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isConstInit(String specifiche)
		{
		/*
		 * "const" <data_type> <identifier> ":=" <expr> è una
		 * dichiarazione di parametri formali costanti e
		 * inizializzati utilizzata nell'intestazione della
		 * definizione di un tipo architetturale.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*const\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*\\:\\s*\\=\\s*" +
					"(.)*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			s.useDelimiter("\\s*\\:\\s*\\=\\s*");
			// nomeConst è il nome della costante
			String nomeConst = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\:\\s*\\=\\s*");
			// exprInit è l'espressione di inizializzazione
			// per la costante
			String exprInit = s.next();
			s.skip("\\s*\\z");
			return ScanTipoDato.isTipoDato(tipoDato) &&
			ScanIdent.isIdent(nomeConst) &&
			ScanExp.isEspressione(exprInit);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ConstInit, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ConstInit.
	 * @throws ScanException
	 */

	private static ConstInit scanConstInit(String specifiche)
		throws ScanException
		{
		ConstInit ci = new ConstInit();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*const\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*\\:\\s*\\=\\s*" +
					"(.)*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			s.useDelimiter("\\s*\\:\\s*\\=\\s*");
			// nomeConst è il nome della costante
			String nomeConst = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\:\\s*\\=\\s*");
			// exprInit è l'espressione di inizializzazione
			// per la costante
			String exprInit = s.next();
			s.skip("\\s*\\z");
			ci.setType(ScanTipoDato.scanTipoDato(tipoDato));
			ci.setName(ScanIdent.scanIdent(nomeConst));
			ci.setExpr(ScanExp.scanEspressione(exprInit));
			return ci;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ConstInit object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di parametro.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	/*
	 * "const" <data_type> <identifier> ":=" <expr> è una
	 * dichiarazione di parametri formali costanti e inizializzati
	 * utilizzata nell'intestazione della definizione di un tipo
	 * architetturale.
	 *
	 * "const" <data_type> <identifier> è una dichiarazione di
	 * parametro formale costante utilizzata nell'intestazione di
	 * un AET, il cui valore è definito nella dichiarazione delle
	 * istanze dell'AET nella sezione di topologia architetturale.
	 *
	 * "local" <normal_type> <identifier> viene di solito
	 * utilizzata quando si sincronizza un'azione di input di
	 * un'istanza dell'AET con un'azione di output di un altro AEI.
	 * Tale dichiarazione viene utilizzata nell'intestazione di
	 * un'equazione comportamentale.
	 *
	 * <normal_type> <identifier> ":=" <expr> viene utilizzata
	 * nell'intestazione della prima equazione comportamentale e
	 * consiste nella dichiarazione di un parametro formale
	 * variabile inizializzata.
	 *
	 * <normal_type> <identifier> viene utilizzata nell'equazioni
	 * comportamentali susseguenti la prima e consiste nella
	 * dichiarazione di un parametro formale variabile. Nessuna
	 * espressione di inizializzazione è necessaria per i parametri
	 * formali variabili di ogni equazione comportamentale
	 * susseguente, poichè, ad essi, saranno assegnati i valori
	 * dei parametri attuali contenuti nell'invocazioni
	 * dell'equazione comportamentale correlata.
	 */

	public static boolean isDeclPar(String specifiche)
		{
		return isConst(specifiche) ||
		isConstInit(specifiche) ||
		isLocal(specifiche) ||
		isVar(specifiche) ||
		isVarInit(specifiche);
		}

	/**
	 * Crea un oggetto ParamDeclaration, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ParamDeclaration.
	 * @throws ScanException
	 */

	public static ParamDeclaration scanDeclPar(String specifiche)
		throws ScanException
		{
		ParamDeclaration dp = null;
		if (isConst(specifiche)) dp = scanConst(specifiche);
		else if (isConstInit(specifiche))
			dp = scanConstInit(specifiche);
		else if (isLocal(specifiche))
			dp = scanLocal(specifiche);
		else if (isVar(specifiche))
			dp = scanVar(specifiche);
		else if (isVarInit(specifiche))
			dp = scanVarInit(specifiche);
		else if ("void".equals(specifiche))
			return dp;
		else throw new ScanException(specifiche+" non è una "+
			"dichiarazione di parametro");
		return dp;
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di parametri.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	/*
	public static boolean isDeclParSeq(String specifiche)
		{
		//
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			while (s.hasNext())
				{
				dic = s.next();
				if (!isDeclPar(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}
	*/
	/**
	 * Scannerizza una sequenza di dichiarazioni di parametri,
	 * generando un array di oggetti ParamDeclaration. I parametri
	 * void vengono sostituiti con null.
	 *
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti ParamDeclaration.
	 * @throws ScanException
	 */

	private static ParamDeclaration[] scanDeclParSeq(String specifiche)
		throws ScanException
		{
		ParamDeclaration[] dics = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\,\\s*");
		try {
			// non vengono accettate sequenze che iniziano
			// con una virgola
			if (specifiche.matches("\\s*\\,(.)*"))
				throw new InputMismatchException();
			// non vengono accettate sequenze che terminano
			// con una virgola
			if (specifiche.matches("(.)*\\,\\s*\\z"))
				throw new InputMismatchException();
			// si conta il numero di dichiarazioni di parametri
			int cont = 0;
			while (s.hasNext())
				{
				s.next();
				cont++;
				}
			dics = new ParamDeclaration[cont];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\,\\s*");
			// si scannerizza la sequenza di dichiarazioni
			// di parametri
			for (int i = 0; i < cont; i++)
				{
				String st = s.next();
				dics[i] = scanDeclPar(st);
				}
			return dics;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ParamDeclaration objects sequence",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di costanti inizializzate.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isConstInitSeq(String specifiche)
		{
		/*
		 * <init_const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da
		 * virgole di parametri formali costanti inizializzati.
		 */
		// non vengono accettate sequenze che iniziano
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// non vengono accettate sequenze che terminano
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		if (s.hasNext("\\s*void\\s*")) return true;
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			// si verifica ogni dichiarazione di costante
			// inizializzata
			while (s.hasNext())
				{
				dic = s.next();
				if (!isConstInit(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di dichiarazioni di costanti
	 * inizializzate, generando un array di oggetti ConstInit.
	 *
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti ConstInit.
	 */

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di costanti.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isConstSeq(String specifiche)
		{
		/*
		 * <const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da virgole
		 * di parametri formali costanti.
		 */
		// non vengono accettate sequenze che iniziano
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// non vengono accettate sequenze che terminano
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		if (s.hasNext("\\s*void\\s*")) return true;
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			// si verifica ogni dichiarazione di costante
			while (s.hasNext())
				{
				dic = s.next();
				if (!isConst(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di variabili inizializzate.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isVarInitSeq(String specifiche)
		{
		/*
		 * <init_var_formal_par_decl_sequence>. Può essere void
		 */
		// non vengono accettate sequenze che iniziano
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// non vengono accettate sequenze che terminano
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		if (s.hasNext("\\s*void\\s*")) return true;
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			// si verifica ogni dichiarazione di variabile
			// inizializzata
			while (s.hasNext())
				{
				dic = s.next();
				if (!isVarInit(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di variabili.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isVarSeq(String specifiche)
		{
		/*
		 * <init_var_formal_par_decl_sequence>. Può essere void
		 */
		// non vengono accettate sequenze che iniziano
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// non vengono accettate sequenze che terminano
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		if (s.hasNext("\\s*void\\s*")) return true;
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			// si verifica ogni dichiarazione di variabile
			while (s.hasNext())
				{
				dic = s.next();
				if (!isVar(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza di
	 * dichiarazioni di variabili locali.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isLocalSeq(String specifiche)
		{
		/*
		 * <local_var_decl_sequence>. Può essere void
		 */
		// non vengono accettate sequenze che iniziano
		// con una virgola
		if (specifiche.matches("\\s*\\,(.)*")) return false;
		// non vengono accettate sequenze che terminano
		// con una virgola
		if (specifiche.matches("(.)*\\,\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		if (s.hasNext("\\s*void\\s*")) return true;
		s.useDelimiter("\\s*\\,\\s*");
		try {
			String dic = new String();
			// si verifica ogni dichiarazione di variabile locale
			while (s.hasNext())
				{
				dic = s.next();
				if (!isLocal(dic)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'intestazione di un tipo architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isIntestARCHI(String specifiche)
		{
		/*
		 * L'intestazione del tipo architetturale all'inizio di
		 * una specifica AEmilia ha la seguente sintassi:
		 *
		 * "ARCHI_TYPE" <identifier>
		 * "(" <init_const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome del tipo architetturale e
		 * <init_const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da virgole
		 * di parametri formali costanti inizializzati.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// i è l'identificatore del tipo architetturale
			String i = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// pars è una sequenza di dichiarazioni di parametri
			// formali costanti inizializzati
			String pars = s.next();
			s.skip("\\s*\\)\\s*\\z");
			return ScanIdent.isIdent(i) && isConstInitSeq(pars);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Header, includendo informazioni
	 * ottenute attraverso la scannerizzazione dell'intestazione
	 * di un tipo architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Header.
	 * @throws ScanException
	 */

	private static Header scanIntestARCHI(String specifiche)
		throws ScanException
		{
		/*
		 * L'intestazione del tipo architetturale all'inizio di
		 * una specifica AEmilia ha la seguente sintassi:
		 *
		 * "ARCHI_TYPE" <identifier>
		 * "(" <init_const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome del tipo architetturale e
		 * <init_const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da
		 * virgole di parametri formali costanti inizializzati.
		 */
		Header i = new Header();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// id è l'identificatore del tipo architetturale
			String id = s.next();
			i.setName(ScanIdent.scanIdent(id));
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// pars è una sequenza di dichiarazioni di parametri
			// formali costanti inizializzati
			String pars = s.next();
			s.skip("\\s*\\)\\s*\\z");
			i.setParameters(scanDeclParSeq(pars));
			return i;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Header object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'intestazione di un tipo di elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isIntestELEM(String specifiche)
		{
		/*
		 * L'intestazione di un AET ha la seguente sintassi:
		 *
		 * "ELEM_TYPE" <identifier>
		 * "(" <const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome dell'AET e
		 * <const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da virgole
		 * di parametri formali costanti.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// i è l'identificatore di un tipo di elemento
			// architetturale
			String i = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// pars è una sequenza di dichiarazioni di parametri
			// formali costanti
			String pars = s.next();
			s.skip("\\s*\\)\\s*\\z");
			return ScanIdent.isIdent(i) && isConstSeq(pars);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Header, includendo informazioni
	 * ottenute attraverso la scannerizzazione dell'intestazione
	 * di un tipo di elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Header.
	 * @throws ScanException
	 */

	public static Header scanIntestELEM(String specifiche)
		throws ScanException
		{
		/*
		 * L'intestazione di un AET ha la seguente sintassi:
		 *
		 * "ELEM_TYPE" <identifier>
		 * "(" <const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome dell'AET e
		 * <const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da
		 * virgole di parametri formali costanti.
		 */
		Header i = new Header();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// id è l'identificatore di un tipo di elemento
			// architetturale
			String id = s.next();
			i.setName(ScanIdent.scanIdent(id));
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\(\\s*");
			// pars è una sequenza di dichiarazioni di parametri
			// formali costanti
			String pars = s.next();
			s.skip("\\s*\\)\\s*\\z");
			i.setParameters(scanDeclParSeq(pars));
			return i;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Header object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'intestazione della prima equazione comportamentale
	 * di un tipo di elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isIntestBEHAV1(String specifiche)
		{
		/*
		 * L'intestazione della prima equazione comportamentale
		 * ha la seguente sintassi:
		 *
		 * <identifier> "(" <init_var_formal_par_decl_sequence> ";"
		 * <local_var_decl_sequence> ")"
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// i è l'identificatore per la prima equazione
			// comportamentale
			String i = s.next();
			s.useDelimiter("\\s*\\;\\s*");
			s.skip("\\s*\\(\\s*");
			// pars1 è una sequenza di dichiarazioni di parametri
			// formali variabili inizializzati
			String pars1 = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\;\\s*");
			// pars2 è una sequenza di variabili locali
			// inizializzate
			String pars2 = s.next();
			s.skip("\\s*\\)\\s*\\z");
			return ScanIdent.isIdent(i) && isVarInitSeq(pars1)
			&& isLocalSeq(pars2);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Header, includendo informazioni
	 * ottenute attraverso la scannerizzazione dell'intestazione
	 * della prima equazione comportamentale di un tipo di
	 * elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Header.
	 * @throws ScanException
	 */

	private static Header scanIntestBEHAV1(String specifiche)
		throws ScanException
		{
		/*
		 * L'intestazione della prima equazione comportamentale ha
		 * la seguente sintassi:
		 *
		 * <identifier> "(" <init_var_formal_par_decl_sequence> ";"
		 *  <local_var_decl_sequence> ")"
		 */
		Header i = new Header();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// id è l'identificatore per la prima equazione
			// comportamentale
			String id = s.next();
			s.skip("\\s*\\(\\s*");
			i.setName(ScanIdent.scanIdent(id));
			s.useDelimiter("\\s*\\;\\s*");
			// pars1 è una sequenza di dichiarazioni di parametri
			// formali variabili inizializzati
			String pars1 = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\;\\s*");
			// pars2 è una sequenza di variabili locali
			// inizializzate
			String pars2 = s.next();
			s.skip("\\s*\\)\\s*\\z");
			String pars = pars1 + "," + pars2;
			i.setParameters(scanDeclParSeq(pars));
			return i;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Header object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'intestazione di un'equazione comportamentale diversa
	 * dalla prima di un tipo di elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isIntestBEHAV2(String specifiche)
		{
		/*
		 * L'intestazione di ogni equazione comportamentale
		 * susseguente ha la seguente sintassi:
		 *
		 * <identifier> "(" <var_formal_par_decl_sequence> ";"
		 * <local_var_decl_sequence> ")"
		 *
		 * dove, ognuno delle precedenti sequenze può essere void.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// i è l'identificatore per una equazione
			// comportamentale diversa dalla prima
			String i = s.next();
			s.useDelimiter("\\s*\\;\\s*");
			s.skip("\\s*\\(\\s*");
			// pars1 è una sequenza di dichiarazioni di parametri
			// formali variabili inizializzati
			String pars1 = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\;\\s*");
			// pars2 è una sequenza di variabili locali
			// inizializzate
			String pars2 = s.next();
			s.skip("\\s*\\)\\s*\\z");
			return ScanIdent.isIdent(i) && isVarSeq(pars1)
			&& isLocalSeq(pars2);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Header, includendo informazioni
	 * ottenute attraverso la scannerizzazione dell'intestazione
	 * di un'equazione comportamentale diversa dalla prima un
	 * tipo di elemento architetturale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Header.
	 * @throws ScanException
	 */

	private static Header scanIntestBEHAV2(String specifiche)
		throws ScanException
		{
		/*
		 * L'intestazione di ogni equazione comportamentale
		 * susseguente ha la seguente sintassi:
		 *
		 * <identifier> "(" <var_formal_par_decl_sequence> ";"
		 * <local_var_decl_sequence> ")"
		 *
		 * dove, ognuno delle precedenti sequenze può essere void.
		 */
		Header i = new Header();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*\\(\\s*");
			// id è l'identificatore per una equazione
			// comportamentale diversa dalla prima
			String id = s.next();
			s.skip("\\s*\\(\\s*");
			i.setName(ScanIdent.scanIdent(id));
			s.useDelimiter("\\s*\\;\\s*");
			// pars1 è una sequenza di dichiarazioni di parametri
			// formali variabili inizializzati
			String pars1 = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\;\\s*");
			// pars2 è una sequenza di variabili locali
			// inizializzate
			String pars2 = s.next();
			s.skip("\\s*\\)\\s*\\z");
			String pars = pars1 + "," + pars2;
			i.setParameters(scanDeclParSeq(pars));
			return i;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Header object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una intestazione secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isIntestazione(String specifiche)
		{
		/*
		 * L'intestazione del tipo architetturale all'inizio di
		 * una specifica AEmilia ha la seguente sintassi:
		 *
		 * "ARCHI_TYPE" <identifier>
		 * "(" <init_const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome del tipo architetturale e
		 * <init_const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da
		 * virgole di parametri formali costanti inizializzati.
		 *
		 * L'intestazione di un AET ha la seguente sintassi:
		 *
		 * "ELEM_TYPE" <identifier>
		 * "(" <const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome dell'AET e
		 * <const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da virgole
		 * di parametri formali costanti. L'intestazione della
		 * prima equazione comportamentale ha la seguente sintassi:
		 *
		 * <identifier> "(" <init_var_formal_par_decl_sequence> ";"
		 *  <local_var_decl_sequence> ")"
		 *
		 * mentre l'intestazione di ogni equazione comportamentale
		 * susseguente ha la seguente sintassi:
		 *
		 * <identifier> "(" <var_formal_par_decl_sequence> ";"
		 * <local_var_decl_sequence> ")"
		 *
		 * dove, ognuno delle precedenti sequenze può essere void.
		 */
		return isIntestARCHI(specifiche) ||
		isIntestELEM(specifiche) ||
		isIntestBEHAV1(specifiche) ||
		isIntestBEHAV2(specifiche);
		}

	/**
	 * Crea un oggetto Header, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Header.
	 * @throws ScanException
	 */

	public static Header scanIntestazione(String specifiche)
		throws ScanException
		{
		/*
		 * L'intestazione del tipo architetturale all'inizio di
		 * una specifica AEmilia ha la seguente sintassi:
		 *
		 * "ARCHI_TYPE" <identifier>
		 * "(" <init_const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome del tipo architetturale e
		 * <init_const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da
		 * virgole di parametri formali costanti inizializzati.
		 *
		 * L'intestazione di un AET ha la seguente sintassi:
		 *
		 * "ELEM_TYPE" <identifier>
		 * "(" <const_formal_par_decl_sequence> ")"
		 *
		 * dove <identifier> è il nome dell'AET e
		 * <const_formal_par_decl_sequence> è o void o una
		 * sequenza non vuota di dichiarazioni separate da virgole
		 * di parametri formali costanti. L'intestazione della
		 * prima equazione comportamentale ha la seguente sintassi:
		 *
		 * <identifier> "(" <init_var_formal_par_decl_sequence> ";"
		 *  <local_var_decl_sequence> ")"
		 *
		 * mentre l'intestazione di ogni equazione comportamentale
		 * susseguente ha la seguente sintassi:
		 *
		 * <identifier> "(" <var_formal_par_decl_sequence> ";"
		 * <local_var_decl_sequence> ")"
		 *
		 * dove, ognuno delle precedenti sequenze può essere void.
		 */
		Header i = new Header();
		if (isIntestARCHI(specifiche))
			i = scanIntestARCHI(specifiche);
		else if (isIntestELEM(specifiche))
			i = scanIntestELEM(specifiche);
		else if (isIntestBEHAV1(specifiche))
			i = scanIntestBEHAV1(specifiche);
		else if (isIntestBEHAV2(specifiche))
			i = scanIntestBEHAV2(specifiche);
		else throw new ScanException(specifiche+" non è un'intestazione ");
		return i;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di variabile locale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isLocal(String specifiche)
		{
		/*
		 * "local" <normal_type> <identifier> viene di solito
		 * utilizzata quando si sincronizza un'azione di input di
		 * un'istanza dell'AET con un'azione di output di un
		 * altro AEI. Tale dichiarazione viene utilizzata
		 * nell'intestazione di un'equazione comportamentale.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*local\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeLoc contiene il nome della variabile locale
			String nomeLoc = s.next();
			s.skip("\\s*\\z");
			return ScanTipoDato.isNormalType(tipoDato) &&
			ScanIdent.isIdent(nomeLoc);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto Local, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Local.
	 * @throws ScanException
	 */

	private static Local scanLocal(String specifiche)
		throws ScanException
		{
		Local l = new Local();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*local\\s*");
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeLoc contiene il nome della variabile locale
			String nomeLoc = s.next();
			s.skip("\\s*\\z");
			l.setName(ScanIdent.scanIdent(nomeLoc));
			l.setType(ScanTipoDato.scanNormalType(tipoDato));
			return l;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Local object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di variabile.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isVar(String specifiche)
		{
		/*
		 * <normal_type> <identifier> viene utilizzata
		 * nell'equazioni comportamentali susseguenti la prima e
		 * consiste nella dichiarazione di un parametro formale
		 * variabile. Nessuna espressione di inizializzazione è
		 * necessaria per i parametri formali variabili di ogni
		 * equazione comportamentale susseguente, poichè, ad essi,
		 * saranno assegnati i valori dei parametri attuali
		 * contenuti nell'invocazioni dell'equazione
		 * comportamentale correlata.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeVar contiene il nome della variabile
			String nomeVar = s.next();
			s.skip("\\s*\\z");
			return ScanTipoDato.isNormalType(tipoDato) &&
			ScanIdent.isIdent(nomeVar);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto VariableDeclaration, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto VariableDeclaration.
	 * @throws ScanException
	 */

	private static VariableDeclaration scanVar(String specifiche)
		throws ScanException
		{
		VariableDeclaration v = new VariableDeclaration();
		MyScanner s = new MyScanner(specifiche);
		try {
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			// nomeVar contiene il nome della variabile
			String nomeVar = s.next();
			s.skip("\\s*\\z");
			v.setName(ScanIdent.scanIdent(nomeVar));
			v.setType(ScanTipoDato.scanNormalType(tipoDato));
			return v;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not VariableDeclaration object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad una dichiarazione di variabile inizializzata.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isVarInit(String specifiche)
		{
		/*
		 * <normal_type> <identifier> ":=" <expr> viene
		 * utilizzata nell'intestazione della prima
		 * equazione comportamentale e consiste nella
		 * dichiarazione di un parametro formale
		 * variabile inizializzato.
		 */
		MyScanner s = new MyScanner(specifiche);
		try {
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.*)\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*\\:\\s*\\=\\s*" +
					"(.)*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*\\:\\s*\\=\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			s.useDelimiter("\\s*\\:\\s*\\=\\s*");
			// nomeVarInit contiene il nome della variabile
			String nomeVarInit = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\:\\s*\\=\\s*");
			// exprInit contiene l'espressione di
			// inizializzazione
			String exprInit = s.next();
			s.skip("\\s*\\z");
			return ScanTipoDato.isNormalType(tipoDato) &&
			ScanIdent.isIdent(nomeVarInit) &&
			ScanExp.isEspressione(exprInit);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto VarInit, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto VarInit.
	 * @throws ScanException
	 */

	public static VarInit scanVarInit(String specifiche)
		throws ScanException
		{
		VarInit vi = new VarInit();
		MyScanner s = new MyScanner(specifiche);
		try {
			// tipodato contiene il nome del tipo dato
			String tipoDato = s.next();
			s.useDelimiter("\\s*\\z");
			// il tipo di dato può essere IntegerRangeType
			if (s.hasNext("\\s*\\((.)*\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*\\:\\s*\\=\\s*" +
					"(.)*"))
				{
				s.useDelimiter("\\)\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				tipoDato = tipoDato + s.next() + ")";
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\)\\s*");
				}
			s.useDelimiter("\\s*\\:\\s*\\=\\s*");
			// nomeVarInit contiene il nome della variabile
			String nomeVarInit = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\:\\s*\\=\\s*");
			// exprInit contiene l'espressione di
			// inizializzazione
			String exprInit = s.next();
			s.skip("\\s*\\z");
			vi.setType(ScanTipoDato.scanNormalType(tipoDato));
			vi.setName(ScanIdent.scanIdent(nomeVarInit));
			vi.setExpr(ScanExp.scanEspressione(exprInit));
			return vi;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not VarInit object",e);
			}
		}
}
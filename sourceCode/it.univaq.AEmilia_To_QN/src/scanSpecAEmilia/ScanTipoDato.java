package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;
import specificheAEmilia.BooleanType;
import specificheAEmilia.IntegerType;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.NormalType;
import specificheAEmilia.PrioType;
import specificheAEmilia.RateType;
import specificheAEmilia.RealType;
import specificheAEmilia.SpecialType;
import specificheAEmilia.DataType;
import specificheAEmilia.WeightType;

/**
 * Classe utilizzata per scannerizzare un tipo di dato,
 * in accordo con la grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * <data_type> ::= <normal_type>
 * | <special_type>
 * <normal_type> ::= "integer"
 * | "integer" "(" <expr> ".." <expr> ")"
 * | "real"
 * | "boolean"
 * | "list" "(" <normal_type> ")"
 * | "array" "(" <expr> "," <normal_type> ")"
 * | "record" "(" <field_decl_sequence> ")"
 * <special_type> ::= "prio"
 * | "rate"
 * | "weight"
 *
 * dove <field_decl_sequence> Ë una sequenza non vuota di dichiarazioni di campi
 * separati da virgole del tipo: <data_type> <identifier>.
 * Il tipo prio denota l'insieme delle priorit√† delle azioni passive e immediate, che
 * coincide con l'insieme degli interi positivi.
 * Il tipo rate denota l'insieme dei tassi azioni temporizzate esponenzialmente, che
 * coincide con l'insieme dei reali positivi.
 * Il tipo weight denota l'insieme dei pesi azioni passive e immediate, che coincidono
 * con l'insieme dei reali positivi.
 *
 * IMPLEMENTARE LE LISTE GLI ARRAY E I RECORDS
 */

public class ScanTipoDato {

	// costanti per i tipi di dato non implementati
	public static final String LISTA = "\\s*list\\s*\\(.*";
	public static final String ARRAY = "\\s*array\\s*\\(.*";
	public static final String RECORD = "\\s*record\\s*\\(.*";

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato weight.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isWeightType(String specifiche)
		{
		// "weight"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*weight\\s*");
		}

	/**
	 * Crea un oggetto Weight attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Weight.
	 * @throws ScanException
	 */

	private static WeightType scanWeightType(String specifiche)
		throws ScanException
		{
		WeightType w = new WeightType();
		if (isWeightType(specifiche))
			{
			return w;
			}
		else throw new ScanException(specifiche+" non Ë un Weight");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato rate.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isRateType(String specifiche)
		{
		// "rate"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*rate\\s*");
		}

	/**
	 * Crea un oggetto Rate attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Rate.
	 * @throws ScanException
	 */

	private static RateType scanRateType(String specifiche)
		throws ScanException
		{
		RateType r = new RateType();
		if (isRateType(specifiche))
			{
			return r;
			}
		else throw new ScanException(specifiche+" is not a Rate");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato prio.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isPrioType(String specifiche)
		{
		// "prio"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*prio\\s*");
		}

	/**
	 * Crea un oggetto Prio attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Prio.
	 * @throws ScanException
	 */

	private static PrioType scanPrioType(String specifiche)
		throws ScanException
		{
		PrioType p = new PrioType();
		if (isPrioType(specifiche))
			{
			return p;
			}
		else throw new ScanException(specifiche+" is not a Prio");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato intervallo di interi.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isIntervalloInt(String specifiche)
		{
		// "integer" "(" <expr> ".." <expr> ")"
		try {
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\.\\.\\s*");
			s.skip("\\s*integer\\s*\\(\\s*");
			String inizio = s.next();
			s.useDelimiter("\\s*\\)\\s*\\z");
			s.skip("\\s*\\.\\.\\s*");
			String fine = s.next();
			s.useDelimiter("\\s*\\z");
			if (!s.hasNext("\\s*\\)\\s*")) return false;
			return ScanExp.isEspressione(inizio) && ScanExp.isEspressione(fine);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto IntegerRangeType attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto IntegerRangeType.
	 * @throws ScanException
	 */

	private static IntegerRangeType scanIntervalloInt(String specifiche)
		throws ScanException
		{
		if (isIntervalloInt(specifiche))
			{
			IntegerRangeType ii = new IntegerRangeType();
			MyScanner s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\.\\.\\s*");
			try {
				s.skip("\\s*integer\\s*\\(\\s*");
				String inizio = s.next();
				s.useDelimiter("\\s*\\)\\s*\\z");
				s.skip("\\s*\\.\\.\\s*");
				String fine = s.next();
				ii.setBeginningInt(ScanExp.scanEspressione(inizio));
				ii.setEndingInt(ScanExp.scanEspressione(fine));
				}
			catch (NoSuchElementException e)
				{
				throw new ScanException(specifiche+
					" is not IntegerRangeType object",e);
				}
			return ii;
			}
		else return null;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato boolean.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isBooleanType(String specifiche)
		{
		// "boolean"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*boolean\\s*");
		}

	/**
	 * Crea un oggetto Boolean attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Boolean.
	 * @throws ScanException
	 */

	private static BooleanType scanBooleanType(String specifiche)
		throws ScanException
		{
		specificheAEmilia.BooleanType b = new specificheAEmilia.BooleanType();
		if (isBooleanType(specifiche))
			{
			return b;
			}
		else throw new ScanException(specifiche+" is not boolean");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato real.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isRealType(String specifiche)
		{
		// "real"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*real\\s*");
		}

	/**
	 * Crea un oggetto Real attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Real.
	 * @throws ScanException
	 */

	private static RealType scanRealType(String specifiche)
		throws ScanException
		{
		specificheAEmilia.RealType r = new specificheAEmilia.RealType();
		if (isRealType(specifiche))
			{
			return r;
			}
		else throw new ScanException(specifiche+" is not real");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * al tipo di dato integer.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isIntegerType(String specifiche)
		{
		// "integer"
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*integer\\s*");
		}

	/**
	 * Crea un oggetto Integer attraverso la scannerizzazione
	 * di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Integer.
	 * @throws ScanException
	 */

	private static IntegerType scanIntegerType(String specifiche)
		throws ScanException
		{
		specificheAEmilia.IntegerType i = new specificheAEmilia.IntegerType();
		if (isIntegerType(specifiche))
			{
			return i;
			}
		else throw new ScanException(specifiche+" is not integer");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un tipo di dato normale, in accordo alla grammatica
	 * AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isNormalType(String specifiche)
		{
		/*
		 * <normal_type> ::= "integer"
		 * | "integer" "(" <expr> ".." <expr> ")"
		 * | "real"
		 * | "boolean"
		 * | "list" "(" <normal_type> ")"
		 * | "array" "(" <expr> "," <normal_type> ")"
		 * | "record" "(" <field_decl_sequence> ")"
		 */
		return isIntegerType(specifiche) || isIntervalloInt(specifiche) ||
		isRealType(specifiche) || isBooleanType(specifiche);
		}

	/**
	 * Crea un oggetto NormalType attraverso la scannerizzazione
	 * di specifiche, in accordo alla grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto NormalType.
	 * @throws ScanException
	 */

	public static NormalType scanNormalType(String specifiche)
		throws ScanException
		{
		NormalType nt = new NormalType();
		if (isNormalType(specifiche))
			{
			if (isIntegerType(specifiche)) nt = scanIntegerType(specifiche);
			else if (isIntervalloInt(specifiche)) nt = scanIntervalloInt(specifiche);
			else if (isRealType(specifiche)) nt = scanRealType(specifiche);
			else if (isBooleanType(specifiche)) nt = scanBooleanType(specifiche);
			else throw new ScanException(specifiche+" is not normal type");
			return nt;
			}
		else throw new ScanException(specifiche+" is not normal type");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un tipo di dato speciale, in accordo alla grammatica
	 * AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isSpecialType(String specifiche)
		{
		/*
		 * <special_type> ::= "prio"
		 * | "rate"
		 * | "weight"
		 */
		return isPrioType(specifiche) || isRateType(specifiche) || isWeightType(specifiche);
		}

	/**
	 * Crea un oggetto SpecialType attraverso la scannerizzazione
	 * di specifiche, in accordo alla grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto SpecialType.
	 * @throws ScanException
	 */

	public static SpecialType scanSpecialType(String specifiche)
		throws ScanException
		{
		SpecialType st = new SpecialType();
		if (isSpecialType(specifiche))
			{
			if (isPrioType(specifiche)) st = scanPrioType(specifiche);
			else if (isRateType(specifiche)) st = scanRateType(specifiche);
			else if (isWeightType(specifiche)) st = scanWeightType(specifiche);
			else throw new ScanException(specifiche+" is not special type");
			return st;
			}
		else throw new ScanException(specifiche+" is not special type");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un tipo di dato, secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isTipoDato(String specifiche)
		{
		return (isNormalType(specifiche) || isSpecialType(specifiche)) &&
		isImplementato(specifiche);
		}

	/**
	 * Crea un oggetto DataType attraverso la scannerizzazione
	 * di specifiche, secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto DataType.
	 * @throws ScanException
	 */

	public static DataType scanTipoDato(String specifiche)
		throws ScanException
		{
		DataType t = new DataType();
		if (isTipoDato(specifiche))
			{
			if (specifiche.matches(ScanTipoDato.ARRAY))
				throw new ScanException("array type not realized");
			if (specifiche.matches(ScanTipoDato.LISTA))
				throw new ScanException("list type not realized");
			if (specifiche.matches(ScanTipoDato.RECORD))
				throw new ScanException("record type not realized");
			if (isNormalType(specifiche)) t = scanNormalType(specifiche);
			else if (isSpecialType(specifiche)) t = scanSpecialType(specifiche);
			else throw new ScanException(specifiche+" is not data type");
			return t;
			}
		else throw new ScanException(specifiche+" is not data type");
		}

	private static boolean isImplementato(String specifiche)
		{
		if (specifiche.matches(ScanTipoDato.ARRAY))
			return false;
		else if (specifiche.matches(ScanTipoDato.LISTA))
			return false;
		else if (specifiche.matches(ScanTipoDato.RECORD))
			return false;
		else
			return true;
		}

}
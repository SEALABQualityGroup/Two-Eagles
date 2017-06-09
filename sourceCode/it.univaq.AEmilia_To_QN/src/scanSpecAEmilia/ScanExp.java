package scanSpecAEmilia;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import personalScanner.MyScanner;
import specificheAEmilia.And;
import specificheAEmilia.Different;
import specificheAEmilia.Divisione;
import specificheAEmilia.Expression;
import specificheAEmilia.IdentExpr;
import specificheAEmilia.Maggiore;
import specificheAEmilia.MaggioreUguale;
import specificheAEmilia.Minore;
import specificheAEmilia.MinoreUguale;
import specificheAEmilia.Moltiplicazione;
import specificheAEmilia.Negazione;
import specificheAEmilia.Or;
import specificheAEmilia.Real;
import specificheAEmilia.Somma;
import specificheAEmilia.Sottrazione;
import specificheAEmilia.Equal;

/**
 * Classe utilizzata per scannerizzare ogni parte di
 * un'espressione, dettata dalla grammatica AEmilia.
 * Le espressioni con un'operatore binario sono tutte
 * associative sinistre.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class ScanExp {

	// funzioni non implementate
	public static final String MODULO = "\\s*mod\\s*\\(.*";
	public static final String VALORE_ASSOLUTO = "\\s*abs\\s*\\(.*";
	public static final String TETTO = "\\s*ceil\\s*\\(.*";
	public static final String BASE = "\\s*floor\\s*\\(.*";
	public static final String MINIMO = "\\s*min\\s*\\(.*";
	public static final String MASSIMO = "\\s*max\\s*\\(.*";
	public static final String POTENZA = "\\s*power\\s*\\(.*";
	public static final String POTENZA_E = "\\s*epower\\s*\\(.*";
	public static final String LOGARITMO_E = "\\s*loge\\s*\\(.*";
	public static final String LOGARITMO_10 = "\\s*log10\\s*\\(.*";
	public static final String RADICE_QUADRATA = "\\s*sqrt\\s*\\(.*";
	public static final String SENO = "\\s*sin\\s*\\(.*";
	public static final String COSENO = "\\s*cos\\s*\\(.*";

	// generatori di numeri pseudo-casuali
	public static final String C_UNIFORM = "\\s*c_uniform\\s*\\(.*";
	public static final String ERLANG = "\\s*erlang\\s*\\(.*";
	public static final String GAMMA = "\\s*gamma\\s*\\(.*";
	public static final String EXPONENTIAL = "\\s*exponential\\s*\\(.*";
	public static final String WEIBULL = "\\s*weibull\\s*\\(.*";
	public static final String BETA = "\\s*beta\\s*\\(.*";
	public static final String NORMAL = "\\s*normal\\s*\\(.*";
	public static final String PARETO = "\\s*pareto\\s*\\(.*";
	public static final String B_PARETO = "\\s*b_pareto\\s*\\(.*";
	public static final String D_UNIFORM = "\\s*d_uniform\\s*\\(.*";
	public static final String BERNOULLI = "\\s*bernoulli\\s*\\(.*";
	public static final String BINOMIAL = "\\s*binomial\\s*\\(.*";
	public static final String POISSON = "\\s*poisson\\s*\\(.*";
	public static final String NEG_BINOMIAL = "\\s*neg_binomial\\s*\\(.*";
	public static final String GEOMETRIC = "\\s*geometric\\s*\\(.*";
	public static final String PASCAL = "\\s*pascal\\s*\\(.*";

	// espressioni per le liste
	public static final String COSTRUZIONE_LISTA = "\\s*list_cons\\s*\\(.*";
	public static final String PRIMO_ELEMENTO_LISTA = "\\s*first\\s*\\(.*";
	public static final String CODA_LISTA = "\\s*tail\\s*\\(.*";
	public static final String CONCATENAZIONE_LISTA = "\\s*concat\\s*\\(.*";
	public static final String INSERIMENTO_ELEMENTO_LISTA = "\\s*insert\\s*\\(.*";
	public static final String RIMOZIONE_ELEMENTO_LISTA = "\\s*remove\\s*\\(.*";
	public static final String LUNGHEZZA_LISTA = "\\s*length\\s*\\(.*";

	// espressioni per gli array
	public static final String COSTRUZIONE_ARRAY = "\\s*array_cons\\s*\\(.*";
	public static final String LETTURA_ELEMENTO_ARRAY = "\\s*read\\s*\\(.*";
	public static final String SCRITTURA_ELEMENTO_ARRAY = "\\s*write\\s*\\(.*";

	// espressioni per i records
	public static final String COSTRUZIONE_RECORD = "\\s*record_cons\\s*\\(.*";
	public static final String LETTURA_CAMPO_RECORD = "\\s*get\\s*\\(.*";
	public static final String SCRITTURA_CAMPO_RECORD = "\\s*put\\s*\\(.*";

	/*
	 * Implementare questa grammatica non ambigua.
	 *
	 * E -> Eb | Ea
	 * Eb -> Eb && Ebt | Eb || Ebt | Ebt
	 * Ebt -> id | true | false | Ea < Ea | Ea <= Ea | Ea > Ea
	 * | Ea >= Ea |
	 * Ea = Ea | Ea != Ea | (Eb) | !Ebt
	 * Ea -> Ea + Eat | Ea - Eat | Eat
	 * Eat -> Eat * Eatf | Eat / Eatf | Eatf
	 * Eatf -> (Ea) | id | digits | digits.digits
	 */

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un numero reale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isReal(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		boolean b =
			s.hasNext("\\s*(-)?(0|([1-9]([0-9])*))\\.(\\d)+\\s*");
		return b;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un numero intero.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isInteger(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*(-)?(0|([1-9]([0-9])*))\\s*");
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un identificatore presente all'interno di un'espressione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isIdentExpr(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNext("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*") && !isBoolean(specifiche)
		&& !isInteger(specifiche) && !isReal(specifiche) && isImplementato(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'espressione tra parentesi tonde.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isParEa(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			if (s.hasNext("\\s*\\(.*\\)(\\s)*\\z"))
				{
				s.skip("\\s*\\(");
				s.useDelimiter("\\)(\\s)*\\z");
				// st contiene l'espressione aritmetica senza
				// parentesi
				String st = s.next();
				return isEa(st);
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un termine di un'espressione aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isEatf(String specifiche)
		{
		return isReal(specifiche) ||
		isInteger(specifiche) ||
		isIdentExpr(specifiche)
		|| isParEa(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione di divisione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isDivisione(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\/\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un / ci sia un Eat e alla
			// destra ci sia un Eatf
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un / si restitusce false
				if (!s.hasNext("\\s*\\/(.)*")) return false;
				s.skip("\\s*\\/\\s*");
				// sd contiene l'operatore di divisione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Eat e op2 è un Eatf si restituisce
				// true
				if (isEat(op1) && isEatf(op2)) return true;
				// si aggiunge a op1 il segno di divisione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una divisione corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\/\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione di moltiplicazione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isMoltiplicazione(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\*\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un * ci sia un Eat e alla
			// destra ci sia un Eatf
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un * si restitusce false
				if (!s.hasNext("\\s*\\*(.)*")) return false;
				s.skip("\\s*\\*\\s*");
				// sd contiene l'operatore di moltiplicazione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Eat e op2 è un Eatf si restituisce
				// true
				if (isEat(op1) && isEatf(op2)) return true;
				// si aggiunge a op1 il segno di moltiplicazione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una moltiplicazione corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\*\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione di moltiplicazione, divisione, o
	 * termine di un'espressione aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isEat(String specifiche)
		{
		return isDivisione(specifiche) ||
		isMoltiplicazione(specifiche)
		|| isEatf(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione di sottrazione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isSottrazione(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\-\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un - ci sia un Ea e alla
			// destra ci sia un Eat
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un - si restitusce false
				if (!s.hasNext("\\s*\\-(.)*")) return false;
				s.skip("\\s*\\-\\s*");
				// sd contiene l'operatore di sottrazione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Eat si restituisce
				// true
				if (isEa(op1) && isEat(op2)) return true;
				// si aggiunge a op1 il segno di sottrazione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una sottrazione corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\-\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione di somma.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
private static boolean isSomma(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\+\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un + ci sia un Ea e alla
			// destra ci sia un Eat
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un + si restitusce false
				if (!s.hasNext("\\s*\\+(.)*")) return false;
				s.skip("\\s*\\+\\s*");
				// sd contiene l'operatore di somma
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Eat si restituisce
				// true
				if (isEa(op1) && isEat(op2)) return true;
				// si aggiunge a op1 il segno di somma
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una somma corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\+\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isEa(String specifiche)
		{
		return isSomma(specifiche) || isSottrazione(specifiche)
		|| isEat(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di diverso.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isDiverso(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\!\\s*\\=\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un != ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un != si restitusce false
				if (!s.hasNext("\\s*\\!\\s*\\=(.)*")) return false;
				s.skip("\\s*\\!\\s*\\=\\s*");
				// sd contiene l'operatore di diverso
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di diverso
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di diverso corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\!\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di uguale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isUguale(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\=\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un = ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un = si restitusce false
				if (!s.hasNext("\\s*\\=(.)*")) return false;
				s.skip("\\s*\\=\\s*");
				// sd contiene l'operatore di uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di uguale corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di maggiore o uguale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isMaggioreUguale(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\>\\s*\\=\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un >= ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un >= si restitusce false
				if (!s.hasNext("\\s*\\>\\s*\\=(.)*")) return false;
				s.skip("\\s*\\>\\s*\\=\\s*");
				// sd contiene l'operatore di maggiore o uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di maggiore o uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di maggiore o uguale corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\>\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di maggiore.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isMaggiore(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\>\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un > ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un > si restitusce false
				if (!s.hasNext("\\s*\\>(.)*")) return false;
				s.skip("\\s*\\>\\s*");
				// sd contiene l'operatore di maggiore
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di maggiore
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di maggiore corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\>\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di minore o uguale.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isMinoreUguale(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\<\\s*\\=\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un <= ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un <= si restitusce false
				if (!s.hasNext("\\s*\\<\\s*\\=(.)*")) return false;
				s.skip("\\s*\\<\\s*\\=\\s*");
				// sd contiene l'operatore di minore o uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di minore o uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di minore o uguale corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\<\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di minore.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isMinore(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\<\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un < ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un < si restitusce false
				if (!s.hasNext("\\s*\\<(.)*")) return false;
				s.skip("\\s*\\<\\s*");
				// sd contiene l'operatore di minore
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Ea e op2 è un Ea si restituisce
				// true
				if (isEa(op1) && isEa(op2)) return true;
				// si aggiunge a op1 il segno di minore
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di minore corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\<\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un valore booleano.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isBoolean(String specifiche)
		{
		Scanner s = new Scanner(specifiche);
		s.useDelimiter("\\s*\\z");
		return s.hasNextBoolean();
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana tra parentesi tonde.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isParEb(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			if (s.hasNext("\\s*\\(.*\\)(\\s)*\\z"))
				{
				s.skip("\\s*\\(");
				s.useDelimiter("\\)(\\s)*\\z");
				// st è un'espressione booleana senza parentesi
				String st = s.next();
				return isEb(st);
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * a termini booleani di un'espressione booleana.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isEbt(String specifiche)
		{
		return isParEb(specifiche) ||
		isIdentExpr(specifiche) ||
		isBoolean(specifiche) ||
		isMinore(specifiche) ||
		isMinoreUguale(specifiche) ||
		isMaggiore(specifiche) ||
		isMaggioreUguale(specifiche) ||
		isUguale(specifiche) ||
		isDiverso(specifiche) ||
		isNegazione(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di or.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isOr(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\|\\s*\\|\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un || ci sia un Eb e alla
			// destra ci sia un Ebt
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un || si restitusce false
				if (!s.hasNext("\\s*\\|\\s*\\|(.)*")) return false;
				s.skip("\\s*\\|\\s*\\|\\s*");
				// sd contiene l'operatore di or
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Eb e op2 è un Ebt si restituisce
				// true
				if (isEb(op1) && isEbt(op2)) return true;
				// si aggiunge a op1 il segno di or
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di or corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\|\\s*\\|\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di and.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isAnd(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\&\\s*\\&\\s*");
		String op1 = new String();
		String op2 = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un && ci sia un Eb e alla
			// destra ci sia un Ebt
			while (s.hasNext())
				{
				s.next();
				// op1 contiene il primo operando
				op1 = op1 + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				// se non è presente un && si restitusce false
				if (!s.hasNext("\\s*\\&\\s*\\&(.)*")) return false;
				s.skip("\\s*\\&\\s*\\&\\s*");
				// sd contiene l'operatore di and
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2 contiene il secondo operando
				op2 = s.next();
				// se op1 è un Eb e op2 è un Ebt si restituisce
				// true
				if (isEb(op1) && isEbt(op2)) return true;
				// si aggiunge a op1 il segno di and
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di and corretta
				op1 = op1 + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1));
				s.useDelimiter("\\s*\\&\\s*\\&\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		return false;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana di negazione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	private static boolean isNegazione(String specifiche)
		{
		MyScanner s = new MyScanner(specifiche);
		try {
			if (s.hasNext("\\s*\\!(.)*"))
				{
				s.skip("\\s*\\!\\s*");
				s.useDelimiter("\\s*\\z");
				// st contiene un Ebt
				String st = s.next();
				return isEbt(st);
				}
			return false;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'operazione booleana.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isEb(String specifiche)
		{
		return isAnd(specifiche) || isOr(specifiche)
		|| isEbt(specifiche);
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un'espressione.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isEspressione(String specifiche)
		{
		return isEa(specifiche) || isEb(specifiche);
		}

	/**
	 * Crea un numero double,  ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un numero double.
	 * @throws ScanException
	 */

	private static double scanDoubleJava(String specifica)
		throws ScanException
		{
		// scannerizza un float in forma puntata
		MyScanner s = new MyScanner(specifica);
		s.useDelimiter("\\s*\\z");
		Double d = new Double(0);
		try {
			String st = s.next();
			d = new Double(st);
			}
		catch (NumberFormatException e)
			{
			throw new ScanException(specifica+
				" is not double",e);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifica+
					" is not double",e);
			}
		return d.doubleValue();
		}

	/**
	 * Crea un oggetto Real, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Real.
	 * @throws ScanException
	 */

	private static Real scanReal(String specifiche)
		throws ScanException
		{
		// digits.digits
		Real r = new Real();
		double f = ScanExp.scanDoubleJava(specifiche);
		r.setValore(f);
		return r;
		}

	/**
	 * Crea un oggetto Integer, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Real.
	 * @throws ScanException
	 */

	private static specificheAEmilia.Integer scanInteger(String specifiche)
		throws ScanException
		{
		// digits
		specificheAEmilia.Integer i =
			new specificheAEmilia.Integer();
		Scanner s = new Scanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			int intero = s.nextInt();
			i.setValore(intero);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Integer object",e);
			}
		return i;
		}

	/**
	 * Crea un oggetto IdentExpr, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto IdentExpr.
	 * @throws ScanException
	 */

	private static IdentExpr scanIdentExpr(String specifiche)
		throws ScanException
		{
		// id
		IdentExpr ie = new IdentExpr();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			String identificatore =
				s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
			ie.setNome(identificatore);
			}
		catch (NoSuchElementException e1)
			{
			throw new ScanException(specifiche+
				" is not IdentExpr object",e1);
			}
		return ie;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un'espressione aritmetica tra
	 * parentesi tonde.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	private static Expression scanParEa(String specifiche)
		throws ScanException
		{
		// (Ea)
		Expression e = null;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*\\(\\s*");
			s.useDelimiter("\\s*\\)\\s*\\z");
			// st è un'espressione aritmetica senza parentesi
			String st = s.next();
			e = ScanExp.scanEa(st);
			}
		catch (NoSuchElementException e1)
			{
			throw new ScanException(specifiche+
				" is not Expression object",e1);
			}
		return e;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un termine di un'espressione
	 * aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	private static Expression scanEatf(String specifiche)
		throws ScanException
		{
		// Eatf -> (Ea) | id | digits | digits.digits
		if (isParEa(specifiche))
			return scanParEa(specifiche);
		else if (isIdentExpr(specifiche))
			return scanIdentExpr(specifiche);
		else if (isInteger(specifiche))
			return scanInteger(specifiche);
		else if (isReal(specifiche))
			return scanReal(specifiche);
		else throw new ScanException(specifiche+ " is not term "
			+" for a arithmetic expression");
		}

	/**
	 * Crea un oggetto Divisione, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Divisione.
	 * @throws ScanException
	 */

	private static Divisione scanDivisione(String specifiche)
		throws ScanException
		{
		// Eat / Eatf
		Divisione a = new Divisione();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\/\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un / ci sia un Eat e alla
			// destra ci sia un Eatf
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\/\\s*");
				// sd contiene l'operatore di divisione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Eat e op2s è un Eatf si restituisce
				// un oggetto Divisione, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEat(op1s) && isEatf(op2s))
					{
					op1 = ScanExp.scanEat(op1s);
					op2 = ScanExp.scanEatf(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di divisione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una divisione corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\/\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Divisione object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Moltiplicazione, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Moltiplicazione.
	 * @throws ScanException
	 */

	private static Moltiplicazione scanMoltiplicazione(String specifiche)
		throws ScanException
		{
		// Eat * Eatf
		Moltiplicazione a = new Moltiplicazione();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\*\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un * ci sia un Eat e alla
			// destra ci sia un Eatf
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\*\\s*");
				// sd contiene l'operatore di moltiplicazione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Eat e op2s è un Eatf si restituisce
				// un oggetto Moltiplicazione, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEat(op1s) && isEatf(op2s))
					{
					op1 = ScanExp.scanEat(op1s);
					op2 = ScanExp.scanEatf(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di moltiplicazione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una moltiplicazione corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\*\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Moltiplicazione object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un termine di un'espressione
	 * aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	private static Expression scanEat(String specifiche)
		throws ScanException
		{
		// Eat -> Eat * Eatf | Eat / Eatf | Eatf
		if (isMoltiplicazione(specifiche))
			return scanMoltiplicazione(specifiche);
		else if (isDivisione(specifiche))
			return scanDivisione(specifiche);
		else if (isEatf(specifiche))
			return scanEatf(specifiche);
		else throw new ScanException(specifiche+ " is not term "+
			"for arithmetic operation");
		}

	/**
	 * Crea un oggetto Sottrazione, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Sottrazione.
	 * @throws ScanException
	 */

	private static Sottrazione scanSottrazione(String specifiche)
		throws ScanException
		{
		// Ea - Eat
		Sottrazione a = new Sottrazione();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\-\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un - ci sia un Ea e alla
			// destra ci sia un Eat
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\-\\s*");
				// sd contiene l'operatore di sottrazione
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Eat si restituisce
				// un oggetto Sottrazione, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEat(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEat(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di sottrazione
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una sottrazione corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\-\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Sottrazione object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Somma, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Somma.
	 * @throws ScanException
	 */

	private static Somma scanSomma(String specifiche)
		throws ScanException
		{
		// Ea + Eat
		Somma a = new Somma();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\+\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un + ci sia un Ea e alla
			// destra ci sia un Eat
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\+\\s*");
				// sd contiene l'operatore di somma
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Eat si restituisce
				// un oggetto Somma, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEat(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEat(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di somma
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// una somma corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\+\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Somma object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un'espressione aritmetica.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	public static Expression scanEa(String specifiche)
		throws ScanException
		{
		// Ea -> Ea + Eat | Ea - Eat | Eat
		if (isSomma(specifiche))
			return scanSomma(specifiche);
		else if (isSottrazione(specifiche))
			return scanSottrazione(specifiche);
		else if (isEat(specifiche))
			return scanEat(specifiche);
		else throw new ScanException(specifiche+ " is not "+
			"a term for arithmetic operation");
		}

	/**
	 * Crea un oggetto Different, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Different.
	 * @throws ScanException
	 */

	private static Different scanDiverso(String specifiche)
		throws ScanException
		{
		// Ea != Ea
		Different a = new Different();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\!\\s*\\=\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un != ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\!\\s*\\=\\s*");
				// sd contiene l'operatore di diverso
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto Different, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di diverso
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di diverso corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\!\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Different object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Equal, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Equal.
	 * @throws ScanException
	 */

	private static Equal scanUguale(String specifiche)
		throws ScanException
		{
		// Ea = Ea
		Equal a = new Equal();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\=\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un = ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\=\\s*");
				// sd contiene l'operatore di uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto Equal, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di uguale corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Equal object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto MaggioreUguale, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto MaggioreUguale.
	 * @throws ScanException
	 */

	private static MaggioreUguale scanMaggioreUguale(String specifiche)
		throws ScanException
		{
		// Ea >= Ea
		MaggioreUguale a = new MaggioreUguale();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\>\\s*\\=\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un >= ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\>\\s*\\=\\s*");
				// sd contiene l'operatore di maggiore o uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto MaggioreUguale, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di maggiore o uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di maggiore o uguale corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\>\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not MaggioreUguale object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Maggiore, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Maggiore.
	 * @throws ScanException
	 */

	private static Maggiore scanMaggiore(String specifiche)
		throws ScanException
		{
		// Ea > Ea
		Maggiore a = new Maggiore();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\>\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un >= ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\>\\s*");
				// sd contiene l'operatore di maggiore o uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto MaggioreUguale, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di maggiore o uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di maggiore o uguale corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\>\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Maggiore object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto MinoreUguale, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto MinoreUguale.
	 * @throws ScanException
	 */

	private static MinoreUguale scanMinoreUguale(String specifiche)
		throws ScanException
		{
		// Ea <= Ea
		MinoreUguale a = new MinoreUguale();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\<\\s*\\=\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un <= ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\<\\s*\\=\\s*");
				// sd contiene l'operatore di minore o uguale
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto MinoreUguale, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di minore o uguale
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di minore o uguale corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\<\\s*\\=\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not MinoreUguale object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Minore, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Minore.
	 * @throws ScanException
	 */

	private static Minore scanMinore(String specifiche)
		throws ScanException
		{
		// Ea < Ea
		Minore a = new Minore();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\<\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un < ci sia un Ea e alla
			// destra ci sia un Ea
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\<\\s*");
				// sd contiene l'operatore di minore
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Ea e op2s è un Ea si restituisce
				// un oggetto Minore, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEa(op1s) && isEa(op2s))
					{
					op1 = ScanExp.scanEa(op1s);
					op2 = ScanExp.scanEa(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di minore
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di minore corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\<\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Minore object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Boolean, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Boolean.
	 * @throws ScanException
	 */

	private static specificheAEmilia.Boolean scanBoolean(String specifiche)
		throws ScanException
		{
		// false | true
		specificheAEmilia.Boolean e = new specificheAEmilia.Boolean();
		Scanner s = new Scanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			boolean b = s.nextBoolean();
			e.setValore(b);
			}
		catch (NoSuchElementException e1)
			{
			throw new ScanException(specifiche+
				" is not Boolean object",e1);
			}
		return e;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un'espressione booleana tra
	 * parentesi tonde.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	private static Expression scanParEb(String specifiche)
		throws ScanException
		{
		// (Eb)
		Expression e = null;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*\\(\\s*");
			s.useDelimiter("\\s*\\)\\s*\\z");
			// st contiene un'espressione booleana senza
			// parentesi
			String st = s.next();
			e = ScanExp.scanEb(st);
			}
		catch (NoSuchElementException e1)
			{
			throw new ScanException(specifiche+
				" is not Expression object",e1);
			}
		return e;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */
	private static Expression scanEbt(String specifiche)
		throws ScanException
		{
		// Ebt -> (Eb) | id | true | false | Ea < Ea | Ea <= Ea | Ea > Ea | Ea >= Ea |
		// Ea = Ea | Ea != Ea | !Ebt
		if (isParEb(specifiche))
			return scanParEb(specifiche);
		else if (isIdentExpr(specifiche))
			return scanIdentExpr(specifiche);
		else if (isBoolean(specifiche))
			return scanBoolean(specifiche);
		else if (isMinore(specifiche))
			return scanMinore(specifiche);
		else if (isMinoreUguale(specifiche))
			return scanMinoreUguale(specifiche);
		else if (isMaggiore(specifiche))
			return scanMaggiore(specifiche);
		else if (isMaggioreUguale(specifiche))
			return scanMaggioreUguale(specifiche);
		else if (isUguale(specifiche))
			return scanUguale(specifiche);
		else if (isNegazione(specifiche))
			return scanNegazione(specifiche);
		else if (isDiverso(specifiche))
			return scanDiverso(specifiche);
		else throw new ScanException(specifiche+" is not "+
			"boolean term");
		}

	/**
	 * Crea un oggetto Or, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Or.
	 * @throws ScanException
	 */

	private static Or scanOr(String specifiche)
		throws ScanException
		{
		// Eb || Ebt
		Or a = new Or();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\|\\s*\\|\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un || ci sia un Eb e alla
			// destra ci sia un Ebt
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\|\\s*\\|\\s*");
				// sd contiene l'operatore di ||
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Eb e op2s è un Ebt si restituisce
				// un oggetto Or, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEb(op1s) && isEbt(op2s))
					{
					op1 = ScanExp.scanEb(op1s);
					op2 = ScanExp.scanEbt(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di or
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di or corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\|\\s*\\|\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Or object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto And, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto And.
	 * @throws ScanException
	 */

	private static And scanAnd(String specifiche)
		throws ScanException
		{
		// Eb && Ebt
		And a = new And();
		Expression op1 = null;
		Expression op2 = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\&\\s*\\&\\s*");
		String op1s = new String();
		String op2s = new String();
		String sd = new String();
		try {
			// si scannerizza specifiche in modo che alla
			// parte sinistra di un && ci sia un Eb e alla
			// destra ci sia un Ebt
			while (s.hasNext())
				{
				s.next();
				// op1s contiene il primo operando
				op1s = op1s + MyScanner.precMatch(s);
				s.useDelimiter("\\s*\\z");
				s.skip("\\s*\\&\\s*\\&\\s*");
				// sd contiene l'operatore di &&
				// incontrato
				sd = MyScanner.precMatch(s);
				// op2s contiene il secondo operando
				op2s = s.next();
				// se op1s è un Eb e op2s è un Ebt si restituisce
				// un oggetto And, ottenuto attraverso
				// la scannerizzazione di op1s e op2s
				if (isEb(op1s) && isEbt(op2s))
					{
					op1 = ScanExp.scanEb(op1s);
					op2 = ScanExp.scanEbt(op2s);
					a.setExpr1(op1);
					a.setExpr2(op2);
					return a;
					}
				// si aggiunge a op1s il segno di and
				// incontrato e si prosegue con la
				// scannerizzazione dell'input rimanente,
				// perchè ancora non abbiamo trovato
				// un'operazione di and corretta
				op1s = op1s + sd;
				s = new MyScanner(specifiche);
				s.useDelimiter("\\s*\\z");
				s.skip(Pattern.quote(op1s));
				s.useDelimiter("\\s*\\&\\s*\\&\\s*");
				}
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
					" is not And object",e);
			}
		return a;
		}

	/**
	 * Crea un oggetto Negazione, ottenuto attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Negazione.
	 * @throws ScanException
	 */

	private static Negazione scanNegazione(String specifiche)
		throws ScanException
		{
		// !Ebt
		Negazione n = new Negazione();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*\\!\\s*");
			s.useDelimiter("\\s*\\z");
			// st contiene un Ebt
			String st = s.next();
			Expression op = ScanExp.scanEbt(st);
			n.setExpr1(op);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not Negazione object",e);
			}
		return n;
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di un'espressione booleana.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	public static Expression scanEb(String specifiche)
		throws ScanException
		{
		// Eb -> Eb && Ebt | Eb || Ebt | Ebt
		if (isAnd(specifiche))
			return scanAnd(specifiche);
		else if (isOr(specifiche))
			return scanOr(specifiche);
		else if (isEbt(specifiche))
			return scanEbt(specifiche);
		else throw new ScanException(specifiche+" is not a boolean expression ");
		}

	/**
	 * Crea un oggetto Expression, ottenuto attraverso la
	 * scannerizzazione di specifiche, secondo la grammatica
	 * AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto Expression.
	 * @throws ScanException
	 */

	public static Expression scanEspressione(String specifiche)
		throws ScanException
		{
		if (specifiche.matches(ScanExp.B_PARETO))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.BASE))
			throw new ScanException("the floor function not realized");
		else if (specifiche.matches(ScanExp.BERNOULLI))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.BETA))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.BINOMIAL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.C_UNIFORM))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.CODA_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.CONCATENAZIONE_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.COSENO))
			throw new ScanException("coseno function not realized");
		else if (specifiche.matches(ScanExp.COSTRUZIONE_ARRAY))
			throw new ScanException("array not realized");
		else if (specifiche.matches(ScanExp.COSTRUZIONE_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.COSTRUZIONE_RECORD))
			throw new ScanException("record not realized");
		else if (specifiche.matches(ScanExp.D_UNIFORM))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.ERLANG))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.EXPONENTIAL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.GAMMA))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.GEOMETRIC))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.INSERIMENTO_ELEMENTO_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.LETTURA_CAMPO_RECORD))
			throw new ScanException("record not realized");
		else if (specifiche.matches(ScanExp.LETTURA_ELEMENTO_ARRAY))
			throw new ScanException("array not realized");
		else if (specifiche.matches(ScanExp.LOGARITMO_10))
			throw new ScanException("base-10 logatithm not realized");
		else if (specifiche.matches(ScanExp.LOGARITMO_E))
			throw new ScanException("natural logarithm not realized");
		else if (specifiche.matches(ScanExp.LUNGHEZZA_LISTA))
			throw new ScanException("list not realized");
		else if (specifiche.matches(ScanExp.MASSIMO))
			throw new ScanException("maximum function not realized");
		else if (specifiche.matches(ScanExp.MINIMO))
			throw new ScanException("minimum function not realized");
		else if (specifiche.matches(ScanExp.MODULO))
			throw new ScanException("modulus function not realized");
		else if (specifiche.matches(ScanExp.NEG_BINOMIAL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.NORMAL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.PARETO))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.PASCAL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.POISSON))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (specifiche.matches(ScanExp.POTENZA))
			throw new ScanException("power function not realized");
		else if (specifiche.matches(ScanExp.POTENZA_E))
			throw new ScanException("power of e function not realized");
		else if (specifiche.matches(ScanExp.PRIMO_ELEMENTO_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.RADICE_QUADRATA))
			throw new ScanException("square root function not realized");
		else if (specifiche.matches(ScanExp.RIMOZIONE_ELEMENTO_LISTA))
			throw new ScanException("lists not realized");
		else if (specifiche.matches(ScanExp.SCRITTURA_CAMPO_RECORD))
			throw new ScanException("records not realized");
		else if (specifiche.matches(ScanExp.SCRITTURA_ELEMENTO_ARRAY))
			throw new ScanException("array not realized");
		else if (specifiche.matches(ScanExp.SENO))
			throw new ScanException("sine function not realized");
		else if (specifiche.matches(ScanExp.TETTO))
			throw new ScanException("floor function not realized");
		else if (specifiche.matches(ScanExp.VALORE_ASSOLUTO))
			throw new ScanException("absolute value function not realized");
		else if (specifiche.matches(ScanExp.WEIBULL))
			throw new ScanException("Pseudo-random number generators not realized");
		else if (isEa(specifiche))
			return scanEa(specifiche);
		else if (isEb(specifiche))
			return scanEb(specifiche);
		else throw new ScanException(specifiche+" is not expression");
		}

	private static boolean isImplementato(String specifiche)
		{
		if (specifiche.matches(ScanExp.B_PARETO)) return false;
		else if (specifiche.matches(ScanExp.BASE)) return false;
		else if (specifiche.matches(ScanExp.BERNOULLI)) return false;
		else if (specifiche.matches(ScanExp.BETA)) return false;
		else if (specifiche.matches(ScanExp.BINOMIAL)) return false;
		else if (specifiche.matches(ScanExp.C_UNIFORM)) return false;
		else if (specifiche.matches(ScanExp.CODA_LISTA)) return false;
		else if (specifiche.matches(ScanExp.CONCATENAZIONE_LISTA)) return false;
		else if (specifiche.matches(ScanExp.COSENO)) return false;
		else if (specifiche.matches(ScanExp.COSTRUZIONE_ARRAY)) return false;
		else if (specifiche.matches(ScanExp.COSTRUZIONE_LISTA)) return false;
		else if (specifiche.matches(ScanExp.COSTRUZIONE_RECORD)) return false;
		else if (specifiche.matches(ScanExp.D_UNIFORM)) return false;
		else if (specifiche.matches(ScanExp.ERLANG)) return false;
		else if (specifiche.matches(ScanExp.EXPONENTIAL)) return false;
		else if (specifiche.matches(ScanExp.GAMMA)) return false;
		else if (specifiche.matches(ScanExp.GEOMETRIC)) return false;
		else if (specifiche.matches(ScanExp.INSERIMENTO_ELEMENTO_LISTA)) return false;
		else if (specifiche.matches(ScanExp.LETTURA_CAMPO_RECORD)) return false;
		else if (specifiche.matches(ScanExp.LETTURA_ELEMENTO_ARRAY)) return false;
		else if (specifiche.matches(ScanExp.LOGARITMO_10)) return false;
		else if (specifiche.matches(ScanExp.LOGARITMO_E)) return false;
		else if (specifiche.matches(ScanExp.LUNGHEZZA_LISTA)) return false;
		else if (specifiche.matches(ScanExp.MASSIMO)) return false;
		else if (specifiche.matches(ScanExp.MINIMO)) return false;
		else if (specifiche.matches(ScanExp.MODULO)) return false;
		else if (specifiche.matches(ScanExp.NEG_BINOMIAL)) return false;
		else if (specifiche.matches(ScanExp.NORMAL)) return false;
		else if (specifiche.matches(ScanExp.PARETO)) return false;
		else if (specifiche.matches(ScanExp.PASCAL)) return false;
		else if (specifiche.matches(ScanExp.POISSON)) return false;
		else if (specifiche.matches(ScanExp.POTENZA)) return false;
		else if (specifiche.matches(ScanExp.POTENZA_E)) return false;
		else if (specifiche.matches(ScanExp.PRIMO_ELEMENTO_LISTA)) return false;
		else if (specifiche.matches(ScanExp.RADICE_QUADRATA)) return false;
		else if (specifiche.matches(ScanExp.RIMOZIONE_ELEMENTO_LISTA)) return false;
		else if (specifiche.matches(ScanExp.SCRITTURA_CAMPO_RECORD)) return false;
		else if (specifiche.matches(ScanExp.SCRITTURA_ELEMENTO_ARRAY)) return false;
		else if (specifiche.matches(ScanExp.SENO)) return false;
		else if (specifiche.matches(ScanExp.TETTO)) return false;
		else if (specifiche.matches(ScanExp.VALORE_ASSOLUTO)) return false;
		else if (specifiche.matches(ScanExp.WEIBULL)) return false;
		else return true;
		}
}
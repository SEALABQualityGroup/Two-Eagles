package scanSpecAEmilia;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.ANDinteractions;
import specificheAEmilia.InputInteractions;
import specificheAEmilia.ORinteractions;
import specificheAEmilia.OutputInteractions;
import specificheAEmilia.UNIinteractions;

/**
 * Classe utilizzata per scannerizzare ogni parte delle
 * interazioni di un tipo di elemento architetturale, dettate
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * "INPUT_INTERACTIONS" <input_interactions> "OUTPUT_INTERACTIONS" <output_interactions>
 *
 * Un'interazione è classificata essere un'interazione di input o
 * un'interazione di output basandosi sulla sua direzione di
 * comunicazione. Poi, un'interazione di input o output è
 * classificata essere una uni-interaction, and-interaction o
 * or-interaction dipendente dalla moltiplicitÃ  delle comunicazioni
 * in cui può essere coinvolta. Sintatticamente parlando, ogni
 * <input_interactions> e <output_interactions> è o void o ha il
 * seguente formato:
 *
 * <uni_interactions> <and_interactions> <or_interactions>
 *
 * con almeno uno dei tre elementi, che rappresenta sequenze di
 * identificatori di tipo azione, essere non vuoti.
 * Una uni-interaction di un'istanza di un AET può comunicare solo
 * con un'interazione di un'altro AEI. Se non è vuota,
 * <uni_interactions> ha la seguente sintassi:
 *
 * "UNI" <identifier_sequence>
 *
 * dove <identifier_sequence> è una sequenza non vuota di
 * identificatori di tipo azione separati da punti e virgole.
 * Una and-interaction di un'istanza di un AET può simultaneamente
 * comunicare con diverse interazioni di altri AEI (comunicazioni
 * broadcast). Se non è vuota, <and_interactions> ha la seguente
 * sintassi:
 *
 * "AND" <identifier_sequence>
 *
 * dove <identifier_sequence> è una sequenza non vuota di
 * identificatori di tipo azione separati da punti e virgole.
 * Un identificatore che si presenta in azioni di input non può
 * essere dichiarato una and-interaction. Una or-interazion di
 * un'istanza di un AET può comunicare con una delle diverse
 * interazioni di altri AEI. Se non è vuota, <or_interactions>
 * ha la seguente sintassi:
 *
 * "OR" <identifier_sequence>
 *
 * dove <identifier_sequence> è una sequenza non vuota di
 * identificatori di tipo azione separati da punti e virgole.
 */

public class ScanAETinteractions {

	/**
	 * Restituisce true se e solo se specifiche corrisponde alle
	 * interazioni di un tipo di elemento architetturale secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isAETinteractions(String specifiche)
		{
		/*
		 * "INPUT_INTERACTIONS" <input_interactions>
		 * "OUTPUT_INTERACTIONS" <output_interactions>
		 *
		 * Un'interazione è classificata essere un'interazione di
		 * input o un'interazione di output basandosi sulla sua
		 * direzione di comunicazione. Poi, un'interazione di input
		 * o output è classificata essere una uni-interaction,
		 * and-interaction o or-interaction dipendente dalla
		 * moltiplicitÃ  delle comunicazioni in cui può essere
		 * coinvolta. Sintatticamente parlando, ogni
		 * <input_interactions> e <output_interactions> è o void
		 * o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta sequenze
		 * di identificatori di tipo azione, essere non vuoti.
		 * Una uni-interaction di un'istanza di un AET può
		 * comunicare solo con un'interazione di un'altro AEI.
		 * Se non è vuota, <uni_interactions> ha la seguente
		 * sintassi:
		 *
		 * "UNI" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Una and-interaction di un'istanza di un AET può
		 * simultaneamente comunicare con diverse interazioni di
		 * altri AEI (comunicazioni broadcast). Se non è vuota,
		 * <and_interactions> ha la seguente sintassi:
		 *
		 * "AND" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Un identificatore che si presenta in azioni
		 * di input non può essere dichiarato una and-interaction.
		 * Una or-interazion di un'istanza di un AET può comunicare
		 * con una delle diverse interazioni di altri AEI. Se non
		 * è vuota, <or_interactions> ha la seguente sintassi:
		 *
		 * "OR" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*OUTPUT_INTERACTIONS\\s*");
		try {
			s.skip("\\s*INPUT_INTERACTIONS\\s*");
			// ii indica le interazioni di input
			String ii = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*OUTPUT_INTERACTIONS\\s*");
			// oi indica le interazioni di output
			String oi = s.next();
			return isInputInteractions(ii) &&
			isOutputInteractions(oi);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto AETinteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AETinteractions.
	 * @throws ScanException
	 */
	public static AETinteractions scanAETinteractions(String specifiche)
		throws ScanException
		{
		/*
		 * "INPUT_INTERACTIONS" <input_interactions>
		 * "OUTPUT_INTERACTIONS" <output_interactions>
		 *
		 * Un'interazione è classificata essere un'interazione di
		 * input o un'interazione di output basandosi sulla sua
		 * direzione di comunicazione. Poi, un'interazione di input
		 * o output è classificata essere una uni-interaction,
		 * and-interaction o or-interaction dipendente dalla
		 * moltiplicitÃ  delle comunicazioni in cui può essere
		 * coinvolta. Sintatticamente parlando, ogni
		 * <input_interactions> e <output_interactions> è o void
		 * o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta
		 * sequenze di identificatori di tipo azione, essere non
		 * vuoti.
		 * Una uni-interaction di un'istanza di un AET può
		 * comunicare solo con un'interazione di un'altro AEI.
		 * Se non è vuota, <uni_interactions> ha la seguente
		 * sintassi:
		 *
		 * "UNI" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Una and-interaction di un'istanza di un AET può
		 * simultaneamente comunicare con diverse interazioni di
		 * altri AEI (comunicazioni broadcast). Se non è vuota,
		 * <and_interactions> ha la seguente sintassi:
		 *
		 * "AND" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Un identificatore che si presenta in azioni di
		 * input non può essere dichiarato una and-interaction.
		 * Una or-interazion di un'istanza di un AET può comunicare
		 * con una delle diverse interazioni di altri AEI. Se non
		 * è vuota, <or_interactions> ha la seguente sintassi:
		 *
		 * "OR" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		AETinteractions ai = new AETinteractions();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*OUTPUT_INTERACTIONS\\s*");
		try {
			s.skip("\\s*INPUT_INTERACTIONS\\s*");
			// ii indica le interazioni di input
			String ii = s.next();
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*OUTPUT_INTERACTIONS\\s*");
			// oi indica le interazioni di output
			String oi = s.next();
			ai.setInIn(scanInputInteractions(ii));
			ai.setOuIn(scanOutputInteractions(oi));
			return ai;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not AETinteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde alle
	 * interazioni di input di un tipo di elemento architetturale
	 * secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isInputInteractions(String specifiche)
		{
		/*
		 * "INPUT_INTERACTIONS" <input_interactions>
		 * Poi, un'interazione di input è classificata essere una
		 * uni-interaction, and-interaction o or-interaction
		 * dipendente dalla moltiplicitÃ  delle
		 * comunicazioni in cui può essere coinvolta.
		 * Sintatticamente parlando, ogni <output_interactions> è o
		 * void o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta
		 * sequenze di identificatori di tipo azione, essere non
		 * vuoti.
		 */
		MyScanner s = new MyScanner(specifiche);
		String unis = new String();
		String ands = new String();
		String ors = new String();
		boolean ris = true;
		try {
			// possono non esserci interazioni di input
			if (s.hasNext("\\s*void\\s*")) return true;
			s.useDelimiter("\\s*((AND)|(OR))\\s*");
			// possono non esserci uni-interazioni
			if (s.hasNext("\\s*UNI\\s*(.)*"))
				unis = s.next();
			s.useDelimiter("\\s*OR\\s*");
			// possono non esserci and-interazioni
			if (s.hasNext("\\s*AND\\s*(.)*"))
				ands = s.next();
			s.useDelimiter("\\s*\\z");
			// possono non esserci or-interazioni
			if (s.hasNext("\\s*OR\\s*(.)*"))
				ors = s.next();
			if (!unis.equals(""))
				ris = isUNIinteractions(unis);
			if (!ands.equals(""))
				ris = ris && isANDinteractions(ands);
			if (!ors.equals(""))
				ris = ris && isORinteractions(ors);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto InputInteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto InputInteractions.
	 * @throws ScanException
	 */
	private static InputInteractions scanInputInteractions(String specifiche)
		throws ScanException
		{
		/*
		 * "INPUT_INTERACTIONS" <input_interactions>
		 * Poi, un'interazione di input è classificata essere una uni-interaction,
		 * and-interaction o or-interaction dipendente dalla moltiplicitÃ  delle
		 * comunicazioni in cui può essere coinvolta.
		 * Sintatticamente parlando, ogni <output_interactions> è o void
		 * o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta sequenze di identificatori di tipo
		 * azione, essere non vuoti.
		 */
		InputInteractions ii = new InputInteractions();
		MyScanner s = new MyScanner(specifiche);
		String unis = new String();
		String ands = new String();
		String ors = new String();
		try {
			// void corrisponde a null
			if (s.hasNext("\\s*void\\s*")) return null;
			s.useDelimiter("\\s*((AND)|(OR))\\s*");
			// possono non esserci uni-interazioni
			if (s.hasNext("\\s*UNI\\s*(.)*"))
				unis = s.next();
			s.useDelimiter("\\s*OR\\s*");
			// possono non esserci and-interazioni
			if (s.hasNext("\\s*AND\\s*(.)*"))
				ands = s.next();
			s.useDelimiter("\\s*\\z");
			// possono non esserci or-interazioni
			if (s.hasNext("\\s*OR\\s*(.)*"))
				ors = s.next();
			if (!unis.equals(""))
				ii.setUni(scanUNIinteractions(unis));
			if (!ands.equals(""))
				ii.setAnd(scanANDinteractions(ands));
			if (!ors.equals(""))
				ii.setOr(scanORinteractions(ors));
			return ii;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not InputInteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde alle
	 * interazioni di output di un tipo di elemento architetturale
	 * secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isOutputInteractions(String specifiche)
		{
		/*
		 * "OUTPUT_INTERACTIONS" <output_interactions>
		 *
		 * Poi, un'interazione di output è classificata essere una
		 * uni-interaction, and-interaction o or-interaction
		 * dipendente dalla moltiplicitÃ  delle comunicazioni
		 * in cui può essere coinvolta.
		 * Sintatticamente parlando, ogni <output_interactions>
		 * è o void o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta
		 * sequenze di identificatori di tipo azione, essere non
		 * vuoti.
		 */
		MyScanner s = new MyScanner(specifiche);
		String unis = new String();
		String ands = new String();
		String ors = new String();
		boolean ris = true;
		try {
			// possono non esserci interazioni di input
			if (s.hasNext("\\s*void\\s*")) return true;
			s.useDelimiter("\\s*((AND)|(OR))\\s*");
			// possono non esserci uni-interazioni
			if (s.hasNext("\\s*UNI\\s*(.)*"))
				unis = s.next();
			s.useDelimiter("\\s*OR\\s*");
			// possono non esserci and-interazioni
			if (s.hasNext("\\s*AND\\s*(.)*"))
				ands = s.next();
			s.useDelimiter("\\s*\\z");
			// possono non esserci or-interazioni
			if (s.hasNext("\\s*OR\\s*(.)*"))
				ors = s.next();
			if (!unis.equals(""))
				ris = isUNIinteractions(unis);
			if (!ands.equals(""))
				ris = ris && isANDinteractions(ands);
			if (!ors.equals(""))
				ris = ris && isORinteractions(ors);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto OutputInteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto OutputInteractions.
	 * @throws ScanException
	 */
	private static OutputInteractions scanOutputInteractions(String specifiche)
		throws ScanException
		{
		/*
		 * "OUTPUT_INTERACTIONS" <output_interactions>
		 *
		 * Poi, un'interazione di output è classificata essere
		 * una uni-interaction, and-interaction o or-interaction
		 * dipendente dalla moltiplicitÃ  delle comunicazioni
		 * in cui può essere coinvolta.
		 * Sintatticamente parlando, ogni <output_interactions> è
		 * o void o ha il seguente formato:
		 *
		 * <uni_interactions> <and_interactions> <or_interactions>
		 *
		 * con almeno uno dei tre elementi, che rappresenta
		 * sequenze di identificatori di tipo azione, essere non
		 * vuoti.
		 */
		OutputInteractions oi = new OutputInteractions();
		MyScanner s = new MyScanner(specifiche);
		String unis = new String();
		String ands = new String();
		String ors = new String();
		try {
			// void corrisponde a null
			if (s.hasNext("\\s*void\\s*")) return null;
			s.useDelimiter("\\s*((AND)|(OR))\\s*");
			// possono non esserci uni-interazioni
			if (s.hasNext("\\s*UNI\\s*(.)*"))
				unis = s.next();
			s.useDelimiter("\\s*OR\\s*");
			// possono non esserci and-interazioni
			if (s.hasNext("\\s*AND\\s*(.)*"))
				ands = s.next();
			s.useDelimiter("\\s*\\z");
			// possono non esserci or-interazioni
			if (s.hasNext("\\s*OR\\s*(.)*"))
				ors = s.next();
			if (!unis.equals(""))
				oi.setUni(scanUNIinteractions(unis));
			if (!ands.equals(""))
				oi.setAnd(scanANDinteractions(ands));
			if (!ors.equals(""))
				oi.setOr(scanORinteractions(ors));
			return oi;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not OutputInteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde a
	 * uni-interazioni secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isUNIinteractions(String specifiche)
		{
		/*
		 * Una uni-interaction di un'istanza di un AET può
		 * comunicare solo con un'interazione di un'altro AEI.
		 * Se non è vuota, <uni_interactions> ha la seguente
		 * sintassi:
		 *
		 * "UNI" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		String IdSeq = new String();
		try {
			s.skip("\\s*UNI\\s*");
			// IdSeq è una sequenza di identificatori di azioni
			// separate da punti e virgole
			IdSeq = s.next();
			return isIdSeq(IdSeq);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto UNIinteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto UNIinteractions.
	 * @throws ScanException
	 */
	private static UNIinteractions scanUNIinteractions(String specifiche)
		throws ScanException
		{
		/*
		 * Una uni-interaction di un'istanza di un AET può
		 * comunicare solo con  un'interazione di un'altro AEI.
		 * Se non è vuota, <uni_interactions> ha la seguente
		 * sintassi:
		 *
		 * "UNI" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		UNIinteractions UNIs = null;
		String[] IdSeq = null;
		String IdSt = new String();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			s.skip("\\s*UNI\\s*");
			// IdSt è una sequenza di identificatori di azioni
			// separati da punti e virgola
			IdSt = s.next();
			// IdSeq è un array di identificatori di azioni
			IdSeq = scanIdSeq(IdSt);
			UNIs = new UNIinteractions();
			UNIs.setActions(IdSeq);
			return UNIs;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not UNIinteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde a
	 * or-interazioni secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isORinteractions(String specifiche)
		{
		/*
		 * Una or-interazion di un'istanza di un AET può comunicare
		 * con una delle diverse interazioni di altri AEI. Se non
		 * è vuota, <or_interactions> ha la seguente sintassi:
		 *
		 * "OR" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		String IdSeq = new String();
		try {
			s.skip("\\s*OR\\s*");
			IdSeq = s.next();
			return isIdSeq(IdSeq);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ORinteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ORinteractions.
	 * @throws ScanException
	 */
	private static ORinteractions scanORinteractions(String specifiche)
		throws ScanException
		{
		/*
		 * Una or-interazion di un'istanza di un AET può comunicare
		 * con una delle diverse interazioni di altri AEI. Se non
		 * è vuota, <or_interactions> ha la seguente sintassi:
		 *
		 * "OR" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota
		 * di identificatori di tipo azione separati da punti e
		 * virgole.
		 */
		ORinteractions ORs = null;
		String[] IdSeq = null;
		String IdSt = new String();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			s.skip("\\s*OR\\s*");
			IdSt = s.next();
			IdSeq = scanIdSeq(IdSt);
			ORs = new ORinteractions();
			ORs.setActions(IdSeq);
			return ORs;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ORinteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde a
	 * and-interazioni secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isANDinteractions(String specifiche)
		{
		/*
		 * Se non è vuota, <and_interactions> ha la seguente
		 * sintassi:
		 *
		 * "AND" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Un identificatore che si presenta in azioni
		 * di input non può essere dichiarato una and-interaction.
		 */
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		String IdSeq = new String();
		try {
			s.skip("\\s*AND\\s*");
			IdSeq = s.next();
			return isIdSeq(IdSeq);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ANDinteractions, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ANDinteractions.
	 * @throws ScanException
	 */
	private static ANDinteractions scanANDinteractions(String specifiche)
		throws ScanException
		{
		/*
		 * Se non è vuota, <and_interactions> ha la seguente
		 * sintassi:
		 *
		 * "AND" <identifier_sequence>
		 *
		 * dove <identifier_sequence> è una sequenza non vuota di
		 * identificatori di tipo azione separati da punti e
		 * virgole. Un identificatore che si presenta in azioni
		 * di input non può essere dichiarato una and-interaction.
		 */
		ANDinteractions ANDs = null;
		String[] IdSeq = null;
		String IdSt = new String();
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\z");
		try {
			s.skip("\\s*AND\\s*");
			IdSt = s.next();
			IdSeq = scanIdSeq(IdSt);
			ANDs = new ANDinteractions();
			ANDs.setActions(IdSeq);
			return ANDs;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ANDinteractions object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche è una sequenza non
	 * vuota di identificatori separati da punti e virgola.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isIdSeq(String specifiche)
		{
		// non sono accettate sequenze che iniziano con un
		// punto e virgola
		if (specifiche.matches("\\s*\\;(.)*")) return false;
		// non sono accettate sequenze che terminano con un
		// punto e virgola
		if (specifiche.matches("(.)*\\;\\s*\\z")) return false;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		try {
			String azione = new String();
			while (s.hasNext())
				{
				azione = s.next();
				if (!ScanIdent.isIdent(azione)) return false;
				}
			return true;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Scannerizza una sequenza di identificatori separati da
	 * punti e virgola, generando un array di oggetti String.
	 * @param specifiche - oggetto String.
	 * @return un array di oggetti String.
	 * @throws ScanException
	 */
	private static String[] scanIdSeq(String specifiche)
		throws ScanException
		{
		String[] seqId = null;
		MyScanner s = new MyScanner(specifiche);
		s.useDelimiter("\\s*\\;\\s*");
		try {
			// non sono accettate sequenze che iniziano con un
			// punto e virgola
			if (specifiche.matches("\\s*\\;(.)*"))
				throw new InputMismatchException();
			// non sono accettate sequenze che terminano con un
			// punto e virgola
			if (specifiche.matches("(.)*\\;\\s*\\z"))
				throw new InputMismatchException();
			String azione = new String();
			// si conta il numero delle azioni
			int c = 0;
			while (s.hasNext())
				{
				azione = s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				c++;
				}
			if (c == 0) throw new ScanException(specifiche+" is not valid identifiers sequence");
			seqId = new String[c];
			s = new MyScanner(specifiche);
			s.useDelimiter("\\s*\\;\\s*");
			// si scannerizzano le azioni
			for (int i = 0; i < c; i++)
				{
				azione = s.next("\\s*[a-zA-Z_&&[^0-9<>=!(),;]](\\w*)\\s*");
				seqId[i] = azione;
				}
			return seqId;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not identifiers sequence",e);
			}
		}
}
package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ElemType;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di un tipo di elemento architetturale, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Una definizione di un AET ha la seguente forma:
 *
 * <AET_header> <AET_behavior> <AET_interactions>
 *
 * L'intestazione di un AET ha la seguente sintassi:
 *
 * "ELEM_TYPE" <identifier> "(" <const_formal_par_decl_sequence> ")"
 *
 * dove <identifier> è il nome dell'AET e
 * <const_formal_par_decl_sequence> è o void o una sequenza non
 * vuota di dichiarazioni separate da virgole di parametri formali
 * costanti.
 *
 * Il comportamento di un AET ha la seguente sintassi:
 * "BEHAVIOR" <behav_equation_sequence> dove
 * <behav_equation_sequence> è una sequenza non vuota di
 * equazioni comportamentali EMPA (oggetti BehavEquation)
 * separate da punti e virgole.
 *
 * Le interazioni AET hanno il seguente formato:
 *
 * "INPUT_INTERACTIONS" <input_interactions>
 * "OUTPUT_INTERACTIONS" <output_interactions>
 *
 * Un'interazione è classificata essere un'interazione di input
 * o un'interazione di output basandosi sulla sua direzione di
 * comunicazione.
 * Sintatticamente parlando, ogni <input_interactions> e
 * <output_interactions> è o void o ha il seguente formato:
 *
 * <uni_interactions> <and_interactions> <or_interactions>
 *
 * con almeno uno dei tre elementi, che rappresenta sequenze di
 * identificatori di tipo azione, essere non vuoti.
 */

public class ScanElemType {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * ad un tipo di elemento architetturale secondo la grammatica
	 * AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isElemType(String specifiche)
		{
		String intestazione = new String();
		String comportamento = new String();
		String interazioni = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*BEHAVIOR\\s*");
			s.skip("\\s*ELEM_TYPE\\s*");
			intestazione = s.next();
			s.useDelimiter("\\s*INPUT_INTERACTIONS\\s*");
			comportamento = s.next();
			s.useDelimiter("\\s*\\z");
			interazioni = s.next();
			return ScanIntestazione.isIntestazione(intestazione) &&
			ScanAETbehavior.isAETbehavior(comportamento) &&
			ScanAETinteractions.isAETinteractions(interazioni);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ElemTypes, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ElemType.
	 * @throws ScanException
	 */
	public static ElemType scanElemType(String specifiche)
		throws ScanException
		{
		ElemType et = new ElemType();
		String intestazione = new String();
		String comportamento = new String();
		String interazioni = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*BEHAVIOR\\s*");
			s.skip("\\s*ELEM_TYPE\\s*");
			intestazione = s.next();
			s.useDelimiter("\\s*INPUT_INTERACTIONS\\s*");
			comportamento = s.next();
			s.useDelimiter("\\s*\\z");
			interazioni = s.next();
			et.setHeader(ScanIntestazione.scanIntestELEM(intestazione));
			et.setBehavior(ScanAETbehavior.scanAETbehavior(comportamento));
			et.setInteractions(ScanAETinteractions.scanAETinteractions(interazioni));
			return et;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not ElemType object",e);
			}
		}
}
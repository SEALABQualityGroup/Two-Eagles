package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;
import specificheAEmilia.ArchiElemTypes;
import specificheAEmilia.ArchiTopology;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Header;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di un tipo architetturale, dettata
 * dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * La descrizione di un tipo tipo architetturale in AEmilia è
 * composta da tre sezioni:
 *
 * ARCHI_TYPE /name and formal parameters.
 * ARCHI_ELEM_TYPES
 * ELEM_TYPE /definition of the first architectural element type.
 * ...
 * ...
 * ELEM_TYPE /definition of the last architectural element type.
 * ARCHI_TOPOLOGY
 * ARCHI_ELEM_INSTANCES /declaration of the architectural element
 * instances.
 * ARCHI_INTERACTIONS /declaration of the architectural
 * interactions.
 * ARCHI_ATTACHMENTS /declaration of the architectural
 * attachments.
 * [BEHAV_VARIATIONS
 * [BEHAV_HIDINGS /declaration of the behavioral hidings.]
 * [BEHAV_RESTRICTIONS /declaration of the behavioral restrictions.]
 * [BEHAV_RENAMINGS /declaration of the behavioral renamings.]]
 * END
 *
 * L'intestazione del tipo architetturale all'inizio di una specifica
 * AEmilia ha la seguente sintassi:
 *
 * "ARCHI_TYPE"
 * <identifier> "(" <init_const_formal_par_decl_sequence> ")"
 *
 * dove <identifier> è il nome del tipo architetturale e
 * <init_const_formal_par_decl_sequence> è o void o una sequenza
 * non vuota di dichiarazioni separate da virgole di parametri
 * formali costanti inizializzati.
 */

public class ScanArchiType {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * alla dichiarazione di un tipo architetturale secondo
	 * la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isArchiType(String specifiche)
		{
		String intestazione = new String();
		String tipiElementi = new String();
		String topologia = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*ARCHI_ELEM_TYPES\\s*");
			s.skip("\\s*ARCHI_TYPE\\s*");
			intestazione = s.next();
			s.useDelimiter("\\s*ARCHI_TOPOLOGY\\s*");
			tipiElementi = s.next();
			s.useDelimiter("\\s*END\\s*");
			topologia = s.next();
			if (topologia.matches("(.)*\\sBEHAV_VARIATIONS\\s(.)*"))
				return false;
			return ScanIntestazione.isIntestazione(intestazione)
			&& ScanArchiElemTypes.isArchiElemTypes(tipiElementi)
			&& ScanArchiTopology.isArchiTopology(topologia);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiType, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiType.
	 * @throws ScanException
	 */
	public static ArchiType scanArchiType(String specifiche)
		throws ScanException
		{
		ArchiType at = new ArchiType();
		Header intestazioneo = new Header();
		ArchiElemTypes tipiElementio = new ArchiElemTypes();
		ArchiTopology topologiao = new ArchiTopology();
		String intestaziones = new String();
		String tipiElementis = new String();
		String topologias = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.useDelimiter("\\s*ARCHI_ELEM_TYPES\\s*");
			s.skip("\\s*ARCHI_TYPE\\s*");
			intestaziones = s.next();
			s.useDelimiter("\\s*ARCHI_TOPOLOGY\\s*");
			tipiElementis = s.next();
			s.useDelimiter("\\s*END\\s*");
			topologias = s.next();
			if (topologias.matches("(.)*\\sBEHAV_VARIATIONS\\s(.)*"))
				throw new ScanException("Behavioral variations not realized");
			intestazioneo = ScanIntestazione.scanIntestazione(intestaziones);
			tipiElementio = ScanArchiElemTypes.scanArchiElemTypes(tipiElementis);
			topologiao = ScanArchiTopology.scanArchiTopology(topologias);
			at.setArchiTypeHeader(intestazioneo);
			at.setArchiElemTypes(tipiElementio);
			at.setTopologia(topologiao);
			return at;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not ArchiType object",e);
			}
		}
}

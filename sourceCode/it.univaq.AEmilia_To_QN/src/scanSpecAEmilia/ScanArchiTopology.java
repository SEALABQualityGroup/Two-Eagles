package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiInteractions;
import specificheAEmilia.ArchiTopology;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione della tipologia di un tipo architetturale,
 * dettata dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

/*
 * La seconda sezione di una specifica AEmilia ha la seguente
 * sintassi:
 *
 * "ARCHI_TOPOLOGY" <AEIs> <architectural_interactions> <architectural_attachments>
 *
 * <AEIs> è la dichiarazione delle Istanze di elementi
 * architetturali ed ha la seguente forma:
 *
 *  "ARCHI_ELEM_INSTANCES" <AEI_decl_sequence>
 *
 *  dove <AEI_decl_sequence> è una sequenza non vuota di
 *  dichiarazioni AEI  separate da punti e virgola, ognuna delle
 *  seguenti forme:
 *
 *  <AEI_decl> ::= <identifier> ["[" <expr> "]"] ":" <identifier> "(" <pe_expr_sequence> ")"
 *  | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 *  <identifier> "[" <expr> "]" ":" <identifier> "(" <pe_expr_sequence> ")"
 *
 *  <architectural_interactions> ha la seguente sintassi:
 *
 *  "ARCHI_INTERACTIONS" <pe_architectural_interaction_decl>
 *
 *  dove <pe architectural interaction decl> è o void o una
 *  sequenza non vuota di  dichiarazioni di interazioni
 *  architetturali separate da punti e virgola,  ognuna della
 *  seguente forma:
 *
 *  <architectural_interaction_decl> ::= <identifier> ["[" <expr> "]"] "." <identifier>
 *  | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 *  <identifier> "[" <expr> "]" "." <identifier>
 *
 *  <architectural_attachments> ha la seguente sintassi:
 *
 *  "ARCHI_ATTACHMENTS" <pe_architectural_attachment_decl>
 *
 *  dove <pe_architectural_attachment_decl> è o void o una
 *  sequenza non vuota di  dichiarazioni di legami architetturali
 *  separate da punti e virgola, ognuna della seguente forma:
 *
 *  <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 *  "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 *  | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 *  ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
 *  "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 *  "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 *
 */

public class ScanArchiTopology {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * alla dichiarazione della tipologia di un tipo architetturale
	 * secondo la grammatica AEmilia.
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */

	public static boolean isArchiTopology(String specifiche)
		{
		String aeis = new String();
		String archiInter = new String();
		String archiAttac = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_TOPOLOGY\\s*");
			s.useDelimiter("\\s*ARCHI_INTERACTIONS\\s*");
			// aeis è una sequenza di istanze di elementi
			// architetturali
			aeis = s.next();
			s.useDelimiter("\\s*ARCHI_ATTACHMENTS\\s*");
			// archiInter è una sequenza di interazioni
			// architetturali
			archiInter = s.next();
			s.useDelimiter("\\s*\\z");
			// archiAttac è una sequenza di collegamenti
			// tra elementi architetturali
			archiAttac = s.next();
			return ScanArchiElemInstances.isArchiElemInstances(aeis)
			&& ScanArchiInteractions.isArchiInteractions(archiInter)
			&& ScanArchiAttac.isArchiAttac(archiAttac);
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto ArchiTopology, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiTopology.
	 * @throws ScanException
	 */
	public static ArchiTopology scanArchiTopology(String specifiche)
		throws ScanException
		{
		ArchiElemInstances aeio = new ArchiElemInstances();
		ArchiInteractions archiIntero = new ArchiInteractions();
		ArchiAttachments archiAttaco = new ArchiAttachments();
		ArchiTopology at = new ArchiTopology();
		String aeis = new String();
		String archiInters = new String();
		String archiAttacs = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*ARCHI_TOPOLOGY\\s*");
			s.useDelimiter("\\s*ARCHI_INTERACTIONS\\s*");
			// aeis è una sequenza di istanze di elementi
			// architetturali
			aeis = s.next();
			s.useDelimiter("\\s*ARCHI_ATTACHMENTS\\s*");
			// archiInters è una sequenza di interazioni
			// architetturali
			archiInters = s.next();
			s.useDelimiter("\\s*\\z");
			// archiAttacs è una sequenza di collegamenti
			// tra elementi architetturali
			archiAttacs = s.next();
			aeio = ScanArchiElemInstances.scanArchiElemInstances(aeis);
			archiIntero = ScanArchiInteractions.scanArchiInteractions(archiInters);
			archiAttaco = ScanArchiAttac.scanArchiAttac(archiAttacs);
			at.setAEIs(aeio);
			at.setArchiInteractions(archiIntero);
			at.setAttachments(archiAttaco);
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
			" is not ArchiTopology object",e);
			}
		return at;
		}
}

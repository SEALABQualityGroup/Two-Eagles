package valutazione;

import java.util.ArrayList;
import java.util.List;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETbehavior;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.ArchiAttachments;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiElemTypes;
import specificheAEmilia.ArchiTopology;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.ElemType;
import specificheAEmilia.Header;
import specificheAEmilia.ProcessTerm;

/**
 * Classe statica che contiene metodi che restituisce una parte
 * di una specifica che è in relazione con altre parti della stessa
 * specifica.
 *
 * @author Mirko
 *
 */
public class Find {

	/**
	 * Restituisce il tipo di elemento architetturale con il nome
	 * fornito come parametro.
	 *
	 * @param nomeAet
	 * @return
	 */
	public static ElemType getElemType(String nomeAet, ArchiType at)
		{
		ArchiElemTypes aets = at.getArchiElemTypes();
		ElemType[] ets = aets.getElementTypes();
		ElemType et = null;
		for (int i = 0; i < ets.length; i++)
			{
			String temp = ets[i].getHeader().getName();
			if (temp.equals(nomeAet)) et = ets[i];
			}
		return et;
		}

	/**
	 * Restituisce il tipo di elemento architetturale, a cui l'istanza
	 * con nome nomeAei appartiene, o null, se nomeAei non ha un tipo di
	 * elemento architetturale associato.
	 *
	 * @param nomeAei
	 * @return
	 */
	public static ElemType getElemTypeFromAei(String nomeAei, ArchiType at)
		{
		// si preleva il nome del tipo di elemento
		// architetturale dall'istanza
		ArchiTopology ato = at.getTopologia();
		ArchiElemInstances aeis = ato.getAEIs();
		AEIdecl[] aeids = aeis.getAEIdeclSeq();
		String nomeAet = new String();
		for (int i = 0; i < aeids.length; i++)
			{
			if (nomeAei.equals(aeids[i].getName()))
				nomeAet = aeids[i].getAET();
			}
		// si preleva il tipo di elemento architetturale
		// dal nome del tipo di elemento dell'istanza
		ArchiElemTypes aets = at.getArchiElemTypes();
		ElemType[] ets = aets.getElementTypes();
		ElemType ris = null;
		for (int i = 0; i < ets.length; i++)
			{
			if (nomeAet.equals(ets[i].getHeader().getName()))
				ris = ets[i];
			}
		return ris;
		}

	/**
	 * Restituisce il tipo di elemento architetturale, a cui pt appartiene, o null, se pt non ha
	 * un ElemType a cui appartiene.
	 *
	 * @param pt
	 * @param at
	 * @return
	 */
	public static ElemType getElemTypeFromProcessTerm(ProcessTerm pt, ArchiType at)
		throws Exception
		{
		// ris è il risultato del metodo
		ElemType ris = null;
		// c conta il numero di pt. Se c > 1 si lancia un'eccezione.
		// Si prelevano i tipi di elementi architetturali.
		int c = 0;
		ElemType[] ets = at.getArchiElemTypes().getElementTypes();
		// si effettua la ricerca di pt all'interno degli elementi
		for (int i = 0; i < ets.length; i++)
			{
			BehavEquation[] bes = ets[i].getBehavior().getBehaviors();
			// si effettua la ricerca di pt all'interno dei comportamenti
			for (int j = 0; j < bes.length; j++)
				{
				if (bes[j].getTermineProcesso().equals(pt))
					{
					ris = ets[i];
					c++;
					}
				}
			}
		if (c > 1) throw new Exception("Process term in more than one behavior");
		return ris;
		}

	/**
	 * Restituisce l'intestazione del comportamento a cui pt appartiene, o null, se pt non ha
	 * un comportamento associato.
	 *
	 * @param pt
	 * @param at
	 * @return
	 * @throws Exception
	 */
	public static Header getBehavHeaderFromProcessTerm(ProcessTerm pt, ArchiType at)
		throws Exception
		{
		// ris è il risultato del metodo
		Header ris = null;
		// c conta il numero di pt. Se c > 1 si lancia un'eccezione.
		// Si prelevano i tipi di elementi architetturali
		int c = 0;
		ElemType[] ets = at.getArchiElemTypes().getElementTypes();
		// si effettua la ricerca di pt all'interno degli elementi
		for (int i = 0; i < ets.length; i++)
			{
			BehavEquation[] bes = ets[i].getBehavior().getBehaviors();
			// si effettua la ricerca di pt all'interno dei comportamenti
			for (int j = 0; j < bes.length; j++)
				{
				if (bes[j].getTermineProcesso().equals(pt))
					{
					ris = bes[j].getBehavHeader();
					c++;
					}
				}
			}
		if (c > 1) throw new Exception("Process term in more than one behavior");
		return ris;
		}

	/**
	 * Restituisce l'equazione comportamentale a cui pt appartiene, o null, se pt non ha
	 * un comportamento associato.
	 *
	 * @param pt
	 * @param at
	 * @return
	 * @throws Exception
	 */
	public static BehavEquation getBehavHequationFromProcessTerm(ProcessTerm pt, ArchiType at)
		throws Exception
		{
		// ris è il risultato del metodo
		BehavEquation ris = null;
		// c conta il numero di pt. Se c > 1 si lancia un'eccezione.
		// Si prelevano i tipi di elementi architetturali
		int c = 0;
		ElemType[] ets = at.getArchiElemTypes().getElementTypes();
		// Si effettua la ricerca di pt all'interno degli elementi
		for (int i = 0; i < ets.length; i++)
			{
			BehavEquation[] bes = ets[i].getBehavior().getBehaviors();
			// Si effettua la ricerca di pt all'interno dei comportamenti
			for (int j = 0; j < bes.length; j++)
				{
				if (bes[j].getTermineProcesso().equals(pt))
					{
					ris = bes[j];
					c++;
					}
				}
			}
		if (c > 1) throw new Exception("Process term in more than one behavior");
		return ris;
		}

	/**
	 * Restituisce il tipo di elemento architetturale a cui i appartiene, o null, se i non ha un
	 * tipo di elemento architetturale di appartenenza.
	 *
	 * @param i
	 * @param at
	 * @return
	 */
	public static ElemType getElemTypeFromBehavHeader(Header i, ArchiType at)
		throws Exception
		{
		// ris è il risultato del metodo
		ElemType ris = null;
		ElemType[] ets = at.getArchiElemTypes().getElementTypes();
		// si effettua la ricerca di i all'interno degli elementi
		int cont = 0;
		for (int j = 0; j < ets.length; j++)
			{
			BehavEquation[] bes = ets[j].getBehavior().getBehaviors();
			// si effettua la ricerca di i all'interno dei comportamenti
			for (int k = 0; k < bes.length; k++)
				{
				if (bes[k].getBehavHeader().equals(i))
					{
					ris = ets[j];
					cont++;
					}
				}
			}
		if (cont > 1) throw new Exception(
				"Behavior header in more than one architectural element type");
		return ris;
		}

	/**
	 * Restituisce i termini di processo del tipo di elemento architetturale dell'istanza dichiarata in aeid.
	 *
	 * @param aeid
	 * @param at
	 * @return
	 */
	public static ProcessTerm[] getProcessTermsFromAEI(AEIdecl aeid, ArchiType at)
		{
		// si preleva il tipo di elemento architetturale dalla dichiarazione di istanza
		ElemType et = getElemTypeFromAei(aeid.getName(), at);
		// si prelevano le equazioni comportamentali
		BehavEquation[] bes = et.getBehavior().getBehaviors();
		// si alloca memoria per l'array risultato
		ProcessTerm[] ris = new ProcessTerm[bes.length];
		// si prelevano i termini di processo
		for (int i = 0; i < bes.length; i++)
			{
			ris[i] = bes[i].getTermineProcesso();
			}
		return ris;
		}

	/**
	 * Restituisce le intestazioni dei comportamenti del tipo di elemento architetturale
	 * dell'istanza dichiarata in aeid.
	 *
	 * @param aeid
	 * @param at
	 * @return
	 */
	public static Header[] getBehavHeadersFromAEI(AEIdecl aeid, ArchiType at)
		{
		// si preleva il tipo di elemento architetturale dalla dichiarazione di istanza
		ElemType et = getElemTypeFromAei(aeid.getName(), at);
		// si prelevano le equazioni comportamentali
		BehavEquation[] bes = et.getBehavior().getBehaviors();
		// si alloca memoria per l'array risultato
		Header[] ris = new Header[bes.length];
		// si prelevano le intestazioni dei comportamenti
		for (int i = 0; i < bes.length; i++)
			{
			ris[i] = bes[i].getBehavHeader();
			}
		return ris;
		}

	/**
	 * Restituisce true se e solo se i è l'intestazione di un'equazione comportamentale
	 * appartenente a et.
	 *
	 * @param i
	 * @param et
	 * @return
	 */
	public static boolean isIntestazioneInElemType(Header i,ElemType et)
		{
		boolean ris = false;
		BehavEquation[] bes =
			et.getBehavior().getBehaviors();
		for (int j = 0; j < bes.length; j++)
			{
			Header in = bes[j].getBehavHeader();
			if (in.equals(i)) ris = true;
			}
		return ris;
		}

	/**
	 * Restituisce le dichiarazioni di collegamenti di aa in cui NomeAEI risulta essere l'istanza
	 * di output e azioneOutput è l'interazione di output.
	 * 
	 * @param NomeAEI
	 * @param azioneOutput
	 * @param aa
	 * @return
	 */
	// ricordarsi le or e and interazioni
	public static List<AttacDecl> getAttacsForAEIOutput(String NomeAEI, String azioneOutput, ArchiAttachments aa)
		{
		// si prelevano le dichiarazioni di collegamenti architetturali
		AttacDecl[] ads = aa.getAttachments();
		// si preleva la dichiarazione di collegamento architetturale, che ha azioneOutput
		// come interazione di output
		List<AttacDecl> ris = new ArrayList<AttacDecl>(0);
		for (int i = 0; i < ads.length; i++)
			{
			// si preleva il nome dell'istanza, da cui parte il collegamento architetturale.
			String nomeAei = ads[i].getOutputAei();
			if (NomeAEI.equals(nomeAei))
				{
				// si preleva l'interazione di output dall'i-esima dichiarazione di collegamento
				// architetturale
				String nomeIntOut = ads[i].getOutputInteraction();
				// se il nome dell'interazione di output è uguale a azioneOutput,
				// allora si aggiorna ris
				if (nomeIntOut.equals(azioneOutput))
					ris.add(ads[i]);
				}
			}
		return ris;
		}

	/**
	 * A partire da aa, restituisce una lista di dichiarazioni di collegamenti,
	 *  che hanno: NomeAEI come nome dell'istanza
	 * di output, azioniOutputs come interazioni di outputs.
	 *
	 * @param NomeAEI
	 * @param azioniOutputs
	 * @param aa
	 * @return
	 */
	public static List<AttacDecl> getAttacsForAEIOutputs(String NomeAEI, String[] azioniOutputs, ArchiAttachments aa)
		{
		// si prelevano le dichiarazioni di collegamenti architetturali
		AttacDecl[] ads = aa.getAttachments();
		// si prelevano le dichiarazioni di collegamenti architetturali che comprendono le
		// azioniOutputs come interazioni di output
		ArrayList<AttacDecl> ris = new ArrayList<AttacDecl>(0);
		for (int i = 0; i < ads.length; i++)
			{
			// si preleva il nome dell'istanza da cui parte il collegamento architetturale
			String nomeAei = ads[i].getOutputAei();
			if (NomeAEI.equals(nomeAei))
				{
				// si preleva l'interazione di output dall'i-esima dichiarazione di collegamento
				// architetturale
				String nomeIntOut = ads[i].getOutputInteraction();
				for (int j = 0; j < azioniOutputs.length; j++)
					{
					// se il nome dell'interazione di output è uguale alla j-esima azione di output,
					// allora si aggiunge la dichiarazione a ris
					if (nomeIntOut.equals(azioniOutputs[j]))
						ris.add(ads[i]);
					}
				}
			}
		return ris;
		}

	/**
	 * Restituisce la lista di dichiarazioni di collegamenti di aa in cui NomeAEI è il nome
	 * dell'istanza di input, azioneInput è il nome dell'interazione di input.
	 * 
	 * @param NomeAEI
	 * @param azioneInput
	 * @param aa
	 * @return
	 */
	// ricordarsi le or e and interazioni
	public static List<AttacDecl> getAttacsForAEIInput(String NomeAEI, String azioneInput, ArchiAttachments aa)
		{
		// si prelevano le dichiarazioni di collegamenti architetturali
		AttacDecl[] ads = aa.getAttachments();
		// si preleva la dichiarazione di collegamento architetturale, che ha azioneInput
		// come interazione di input
		List<AttacDecl> ris = new ArrayList<AttacDecl>(0);
		for (int i = 0; i < ads.length; i++)
			{
			// si preleva il nome dell'istanza, a cui arriva il collegamento architetturale.
			String nomeAei = ads[i].getInputAei();
			if (NomeAEI.equals(nomeAei))
				{
				// si preleva l'interazione di input dall'i-esima dichiarazione di collegamento
				// architetturale
				String nomeIntInt = ads[i].getInputInteraction();
				// se il nome dell'interazione di input è uguale a azioneInput,
				// allora si aggiorna ris
				if (nomeIntInt.equals(azioneInput))
					ris.add(ads[i]);
				}
			}
		return ris;
		}

	/**
	 * A partire da aa, restituisce una lista di dichiarazioni di collegamenti,
	 * che hanno: NomeAEI come nome dell'istanza
	 * di input, azioniInputs come interazioni di inputs.
	 *
	 * @param NomeAEI
	 * @param azioniInputs
	 * @param aa
	 * @return
	 */
	public static List<AttacDecl> getAttacsForAEIInputs(String NomeAEI, String[] azioniInputs, ArchiAttachments aa)
		{
		// si prelevano le dichiarazioni di collegamenti architetturali
		AttacDecl[] ads = aa.getAttachments();
		// si prelevano le dichiarazioni di collegamenti architetturali che comprendono le
		// azioniInputs come interazioni di input
		ArrayList<AttacDecl> ris = new ArrayList<AttacDecl>(0);
		for (int i = 0; i < ads.length; i++)
			{
			// si preleva il nome dell'istanza, a cui arriva il collegamento architetturale.
			String nomeAei = ads[i].getInputAei();
			if (NomeAEI.equals(nomeAei))
				{
				// si preleva l'interazione di input dall'i-esima dichiarazione di collegamento
				// architetturale
				String nomeIntInt = ads[i].getInputInteraction();
				for (int j = 0; j < azioniInputs.length; j++)
					{
					// se il nome dell'interazione di input è uguale alla j-esima azione di input,
					// allora si aggiunge la dichiarazione a ris
					if (nomeIntInt.equals(azioniInputs[j]))
						ris.add(ads[i]);
					}
				}
			}
		return ris;
		}

	/**
	 * Restituisce una dichiarazione di istanza che hanno nomeAEI come nome di istanza.
	 *
	 * @param nomeAEI
	 * @param at
	 * @return
	 */
	public static AEIdecl getAEIdeclFromAei(String nomeAEI, ArchiType at)
		{
		AEIdecl ris = null;
		// si prelevano le dichiarazioni di istanze
		AEIdecl[] aeids = at.getTopologia().getAEIs().getAEIdeclSeq();
		// si cercano tutte quelle dichiarazioni che hanno nomeAEI come
		// nome di istanza
		for (int i = 0; i < aeids.length; i++)
			{
			if (aeids[i].equals(nomeAEI))
				ris = aeids[i];
			}
		return ris;
		}

	/**
	 * Restituisce una lista di tassi delle azioni con nome nameAction, presenti all'interno delle equazioni
	 * comportamentali di un tipo di elemento architetturale.
	 *
	 * @param nomeAction
	 * @param elemType
	 * @return
	 */
	public static List<ActionRate> getRatesFromActionName(String nameAction, ElemType elemType)
		{
		List<ActionRate> list = new ArrayList<ActionRate>();
		AETbehavior tbehavior = elemType.getBehavior();
		BehavEquation[] behavEquations = tbehavior.getBehaviors();
		for (int i = 0; i < behavEquations.length; i++)
			{
			ProcessTerm processTerm = behavEquations[i].getTermineProcesso();
			if (processTerm instanceof ActionProcess)
				{
				ActionProcess actionProcess = (ActionProcess) processTerm;
				Action action = actionProcess.getAzione();
				ActionType actionType = action.getType();
				ActionRate actionRate = action.getRate();
				if (actionType.getName().equals(nameAction))
					list.add(actionRate);
				}
			}
		return list;
		}

	/**
	 * Restituisce una lista di tutti gli oggetti ChoiceProcess presenti all'interno
	 * del comportamento di un tipo di elemento architetturale.
	 *
	 * @param elemType
	 * @return
	 */
//	public static List<ChoiceProcess> getChoiceProcess(ElemType elemType)
//		{
//		List<ChoiceProcess> list = new ArrayList<ChoiceProcess>();
//		AETbehavior tbehavior = elemType.getComportamento();
//		BehavEquation[] behavEquations = tbehavior.getComportamenti();
//		for (int i = 0; i < behavEquations.length; i++)
//			{
//			ProcessTerm processTerm = behavEquations[i].getTermineProcesso();
//
//			}
//		}
//
//	private List<ChoiceProcess> getChoiceProcess(ProcessTerm processTerm)
//		{
//		List<ChoiceProcess> list = new ArrayList<ChoiceProcess>();
//		if (processTerm instanceof ActionProcess)
//			{
//			ActionProcess actionProcess = (ActionProcess) processTerm;
//			list.addAll(getChoiceProcess(actionProcess.getProcesso()));
//			}
//		if (processTerm instanceof ChoiceProcess)
//			{
//			ChoiceProcess choiceProcess = (ChoiceProcess) processTerm;
//			list.add(choiceProcess);
//			for (int i = 0; i < choiceProcess.getProcessi().length; i++)
//				{
//
//				}
//			}
//		}
}

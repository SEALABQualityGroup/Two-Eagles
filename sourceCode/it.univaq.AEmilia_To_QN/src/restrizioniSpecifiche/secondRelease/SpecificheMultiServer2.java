package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.Equals;
import restrizioniSpecifiche.Interaction;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheMultiServer;
import restrizioniSpecifiche.interfaces.ISpecificheSP;
import restrizioniSpecifiche.interfaces.ISpecificheSPNB;
import restrizioniSpecifiche.interfaces.ISpecificheSPWB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiType;
import specificheAEmilia.AttacDecl;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.ORinteractions;
import specificheAEmilia.OutputInteractions;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaFork;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

/**
 * Il caso di un centro di servizio composto di diversi server identici ed indipendenti è regolato da
 * restrizioni di sintassi aggiuntive. Richiede prima di tutto che i processi di servizio
 * che costituiscono il centro di servizio multi-server sono istanze dello stesso AET con la stessa
 * distribuzione del tempo di servizio individuale e interazioni attaccate agli stessi AEI. Tre casi si
 * sollevano. Nel primo caso, i processi di servizio condividono un buffer, dal quale prendono tutti i
 * loro clienti. In questo caso, le azioni put<sub>1</sub>,...,put<sub>n</sub> del buffer devono essere
 * or-interazioni di output.
 * Nel secondo caso, i processi servizio non hanno alcun buffer e ricevono qualcuno dei loro clienti
 * direttamente dai processi di arrivo per popolazione illimitata, processi fork, processi join, o processi
 * di servizio. Similmente al caso precedente, le interazioni di output dei processi di arrivo upstream
 * per popolazioni illimitate, processi fork, processi join, o processi di servizio che sono relativi al
 * centro di servizio multi-server, devono essere or-interazioni di output. Nel terzo caso, i processi di
 * servizio non hanno nessun buffer e ricevono qualcuno dei loro clienti da processi di arrivo per
 * popolazioni finite. Per ogni tale processo di arrivo upstream, l'azione tra deliver<sub>1</sub>,...,deliver<sub>n</sub>
 * che è relativo al centro di servizio multi-server deve essere sostituito nella specifica dal processo di arrivo
 * con molte copie alternative quante sono i processi di servizio nel centro di servizio multi-server.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class SpecificheMultiServer2 
	implements ISpecificheMultiServer 
	{

	protected ArchiType archiType;
	protected List<IEquivalenza> listEquivalenzeArchiType;
	
	/**
	 * Contiene una mappa del centro di servizio multiServer in cui la chiave è il
	 * nome del tipo di elemento architetturale, che ha istanze nel centro di servizio
	 * multiserver i cui processi di servizio sono gli elementi della lista valore
	 */
	protected HashMap<String, List<ISpecificheSP>> multiServerMap;

	public SpecificheMultiServer2(ArchiType archiType)
			throws RestrizioniSpecException
		{
		this.archiType = archiType;
		inizializzazioneEquivalenze();
		// si crea una lista di SpecificheSP che contiene tutti i tipi di elementi architetturali
		// equivalenti a processi di servizio
		List<ISpecificheSP> list = getSpecificheSP();
		// dalla lista specificheSPList si prelevano istanze dello stesso aet
		// il risultato deve essere una mappa di liste di tipo SpecificheSP
		// la cui chiave è il nome di un tipo di elemento architetturale
		HashMap<String, List<ISpecificheSP>> hashMap = generateSPListForAET(list);
		// per ognuna delle liste precedenti si verifica che le componenti di tale lista
		// hanno la stessa distribuzione di tempo esponenziale
		// il risultato di tale verifica viene messo in una HashMap
		HashMap<String, Boolean> hashMap2 = verificaDistribuzioni(hashMap);
		// se non hanno distribuzione di tempo esponenziale uguale si cancella l'elemento
		// dalla mappa
		hashMap = deleteElements(hashMap, hashMap2);
		// per ognuna delle liste precedenti si verifica che le componenti di tale lista
		// abbiano gli stessi aei attaccati
		hashMap2 = verificaAEIInteractions(hashMap);
		// se una delle liste non verifica la proprietà precedente la si cancella dalla mappa
		hashMap = deleteElements(hashMap, hashMap2);
		multiServerMap = hashMap;
		}

	/**
	 * I processi di servizio che costituiscono il centro di servizio multi-server devono essere
	 * istanze dello stesso aet, con la stessa distribuzione del tempo di servizio individuale e
	 * interazioni attaccate agli stessi aei.
	 *
	 * @param specificheSPList
	 * @throws RestrizioniSpecException
	 */
	public SpecificheMultiServer2 (List<ISpecificheSP> specificheSPList)
		throws RestrizioniSpecException
		{
		// dalla lista specificheSPList si prelevano istanze dello stesso aet
		// il risultato deve essere una mappa di liste di tipo SpecificheSP
		// la cui chiave è il nome di un tipo di elemento architetturale
		HashMap<String, List<ISpecificheSP>> hashMap = generateSPListForAET(specificheSPList);
		// per ognuna delle liste precedenti si verifica che le componenti di tale lista
		// hanno la stessa distribuzione di tempo esponenziale
		// il risultato di tale verifica viene messo in una HashMap
		HashMap<String, Boolean> hashMap2 = verificaDistribuzioni(hashMap);
		// se non hanno distribuzione di tempo esponenziale uguale si cancella l'elemento
		// dalla mappa
		hashMap = deleteElements(hashMap, hashMap2);
		// per ognuna delle liste precedenti si verifica che le componenti di tale lista
		// abbiano gli stessi aei attaccati
		hashMap2 = verificaAEIInteractions(hashMap);
		// se una delle liste non verifica la proprietà precedente la si cancella dalla mappa
		hashMap = deleteElements(hashMap, hashMap2);
		multiServerMap = hashMap;
		}

	/**
	 * Restrizione1: se i processi di servizio condividono un buffer,
	 * le azioni put<sub>1</sub>,...,put<sub>n</sub>
	 * del buffer devono essere or-interazioni di output.
	 *
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public boolean restrizioneIstanze3()
		throws RestrizioniSpecException
		{
		boolean b = true;
		// per ogni centro di servizio multiserver:
		Collection<List<ISpecificheSP>> collection = this.multiServerMap.values();
		for (List<ISpecificheSP> list : collection)
			{
			if (list.size() > 1)
				{
				// 1) si verifica se condividono un buffer
				if (shareBuffer(list))
					{
					List<IEquivalenza> list2 = list.get(0).getEquivalenzeInput();
					IEquivalenzaBuffer iEquivalenzaBuffer = (IEquivalenzaBuffer)list2.get(0);
					// si verifica se in tale buffer le azioni put sono or-interazioni
					// di output
					b = b && orInputPut(iEquivalenzaBuffer);
					}
				// se non condividono un buffer si restituisce true
				else b = true;
				}
			}
		return b;
		}

	/**
	 * Restrizione2: se i processi di servizio non hanno alcun buffer e ricevono i loro clienti dai processi
	 * di arrivo per una popolazione illimitata, processi fork, processi join, processi di servizio,
	 * allora le interazioni di output dei processi di arrivo upstream per popolazioni illimitate,
	 * processi fork, processi join, o processi di servizio attaccate al centro di servizio multi-server
	 * devono essere or-interezioni di output.
	 *
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public boolean restrizioneIstanze4() throws RestrizioniSpecException
		{
		// per ogni centro di servizio multiserver si opera nel seguente modo:
			// si verifica se i processi di servizio che compongono il centro multiserver sono senza buffer
				// se il processo non ha un buffer si verifica che ogni istanza di output
				// attaccata al processo di servizio sia collegata con una or-interazioni di output
		// b contiene il risultato del metodo
		boolean b = true;
		// si prelevano i centri di servizio multiserver
		Collection<List<ISpecificheSP>> collection = this.multiServerMap.values();
		for (List<ISpecificheSP> list : collection)
			{
			for (ISpecificheSP specificheSP : list)
				{
				if (list.size() > 1)
					{
					if (specificheSP instanceof ISpecificheSPNB)
						{
						ISpecificheSPNB specificheSPNB = (ISpecificheSPNB)specificheSP;
						// si prelevano le interazioni in cui specificheSPNB risulta essere
						// istanza di input
						List<AttacDecl> list2 = specificheSPNB.getAttacsDeclInput();
						// si verifica che le istanze di output di list2 siano or-interazioni
						// di output del relativo tipo di elemento architetturale
						b = b && isOrOutputInteractionsForMS(list2);
						}
					}
				}
			}
		return b;
		}

	/**
	 * Restrizione3: se i processi di servizio non hanno nessun buffer e ricevono qualcuno dei loro clienti da
	 * processi di arrivo per una popolazione finita, allora ogni azione tra deliver1,...,delivern,
	 * presente nel processo di arrivi, che è relativa al centro di servizio multiserver, deve essere sostituita da
	 * tante azioni copia, a seconda di quanti sono i processi di servizio nel centro di servizio multi-server.
	 *
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public boolean regola3() throws RestrizioniSpecException
		{
		// questa regola è una conseguenza di verificaAEIInteractions(), utilizzata nel costruttore
		// della classe
		return true;
		}

	@Override
	public boolean isCompliantSpecificRules()
		throws RestrizioniSpecException
		{
		return caratterizzazione();
		}

	public boolean isCompliantFullSpecificRules() throws RestrizioniSpecException
		{
		return caratterizzazione() && restrizioneIstanze3() && restrizioneIstanze4();
		}

	/**
	 * Restituisce true se e solo se list contiene Specifiche che hanno uno stesso buffer
	 * come istanza di output collegato.
	 *
	 * @param list
	 * @return
	 */
	private boolean shareBuffer(List<ISpecificheSP> list)
		{
		// si crea una lista che contiene i nomi delle istanze dei buffer collegati come istanze di output
		// se le istanze di output non sono buffer si restituisce false
		List<String> list2 = new ArrayList<String>();
		for (ISpecificheSP specificheSP : list)
			{
			List<IEquivalenza> list3 = specificheSP.getEquivalenzeInput();
			for (int i = 0; i < list3.size(); i++)
				{
				IEquivalenza iEquivalenza = list3.get(i);
				if (!(iEquivalenza instanceof IEquivalenzaBuffer)) return false;
				AEIdecl idecl = specificheSP.getAEIsDeclInput().get(i);
				list2.add(idecl.getName());
				}
			}
		// la lista risultante deve avere tutti elementi uguali
		if (list2.isEmpty()) return false;
		String string = list2.get(0);
		for (int i = 1; i < list2.size(); i++)
			{
			String string2 = list2.get(i);
			if (!string.equals(string2)) return false;
			}
		return true;
		}

	/**
	 * Restituisce true se e solo se le azioni di put di equivalenzaBuffer sono or-interazioni di output.
	 *
	 * @param iEquivalenzaBuffer
	 * @return
	 */
	private boolean orInputPut(IEquivalenzaBuffer iEquivalenzaBuffer)
		{
		String[] strings = iEquivalenzaBuffer.getPuts();
		AETinteractions tinteractions = iEquivalenzaBuffer.getEt().getInteractions();
		OutputInteractions outputInteractions = tinteractions.getOuIn();
		if (outputInteractions == null) return false;
		ORinteractions rinteractions = outputInteractions.getOr();
		if (rinteractions == null) return false;
		String[] strings2 = rinteractions.getActions();
		CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>(strings);
		CopyOnWriteArrayList<String> copyOnWriteArrayList2 = new CopyOnWriteArrayList<String>(strings2);
		if (!copyOnWriteArrayList2.containsAll(copyOnWriteArrayList)) return false;
		return true;
		}
	
	/**
	 * Restituisce true se e solo se le interazioni di output di list sono or-interazioni di output
	 * di processi di arrivo per una popolazione illimitata, processi fork, 
	 * processi join, processi di servizio.
	 *
	 * @param list
	 * @return
	 * @throws RestrizioniSpecException
	 */
	protected boolean isOrOutputInteractionsForMS(List<AttacDecl> list)
			throws RestrizioniSpecException {
			List<Interaction> list2 = getActionsOutputFromAttacs(list);
			ArchiElemInstances archiElemInstances = this.archiType.getTopologia().getAEIs();
			List<String> list3 = SpecificheRules2.getInstances(list2);
			// list4 contiene le dichiarazioni di istanze che hanno nome presente in list3
			List<AEIdecl> list4 = SpecificheRules2.getAEIsFromNames(list3, archiElemInstances);
			// list5 contiene i nomi degli aet di list4
			List<String> list5 = SpecificheRules2.getAETsFromAEIDecls(list4);
			// si prelevano le equivalenzaFactory corrispondenti a list5
			List<IEquivalenza> list6 = SpecificheRules2.getEquivalenzeFromAETs(listEquivalenzeArchiType, list5);
			// si verifica che le azioni di list2 sono delle or-interazioni di output
			for (Interaction interaction : list2)
				{
				ElemType elemType = null;
				// prelevo l'aet di string
				String string2 = SpecificheRules2.getAETFromAEI(list4, interaction.getInstance());
				// si preleva l'iEquivalenza relativa a string2
				IEquivalenza iEquivalenza = SpecificheRules2.getEquivalenzaFromElemTypeName(list6, string2);
				// iEquivalenza si deve riferire ad un 
				// processo di arrivo per una popolazione illimitata, 
				// processi fork, processi join, processi di servizio.
				if (iEquivalenza instanceof IEquivalenzaArriviInfiniti ||
					iEquivalenza instanceof IEquivalenzaFork ||
					iEquivalenza instanceof IEquivalenzaJoin ||
					iEquivalenza instanceof IEquivalenzaServizio)
					{
					// si assegna a elemType il tipo di elemento dell'iEquivalenza
					elemType = iEquivalenza.getEt();
					// se l'azione di interaction non è una or-interazione di output di elemType si restituisce false
					if (!SpecificheRules2.isOrOutput(elemType, interaction.getAction()))
						return false;
					}
				}
			return true;
			}
	
	/**
	 * Restituisce una lista di oggetti interaction con istanze uguale ai nomi
	 * degli AEI di output contenuti in list, e come azioni le interazioni di output
	 * corrispondenti.
	 *
	 * @param list
	 * @return
	 */
	public List<Interaction> getActionsOutputFromAttacs(List<AttacDecl> list) 
		{
		List<Interaction> list2 = new ArrayList<Interaction>();
		for (AttacDecl attacDecl : list)
			{
			String string = attacDecl.getOutputInteraction();
			String string2 = attacDecl.getOutputAei();
			Interaction interaction = new Interaction(string2,string);
			list2.add(interaction);
			}
		return list2;
		}
	
	/**
	 * Crea una lista di equivalenzaFactory del tipo architetturale.
	 *
	 * @throws RestrizioniSpecException
	 */
	protected void inizializzazioneEquivalenze()
			throws RestrizioniSpecException {
			AEIdecl[] idecls = this.archiType.getTopologia().getAEIs().getAEIdeclSeq();
			CopyOnWriteArrayList<AEIdecl> copyOnWriteArrayList2 = new CopyOnWriteArrayList<AEIdecl>(idecls);
			List<IEquivalenza> list = SpecificheRules2.getEquivalenze(this.archiType, copyOnWriteArrayList2);
			listEquivalenzeArchiType = list;
			}
	
	/**
	 * Restituisce una lista di SpecificheSP a partire dalle equivalenzaFactory di processi di servizio
	 * presenti nella specifica.
	 *
	 * @return
	 * @throws RestrizioniSpecException
	 */
	protected List<ISpecificheSP> getSpecificheSP() 
		throws RestrizioniSpecException 
		{
		List<ISpecificheSP> list = new ArrayList<ISpecificheSP>();
		for (IEquivalenza iEquivalenza : this.listEquivalenzeArchiType)
			{
			AEIdecl idecl = iEquivalenza.getAEIdecl();
			if (iEquivalenza instanceof IEquivalenzaServizioSenzaBuffer)
				{
				ISpecificheSP specificheSPNB = new Specifiche2Factory().createSpecificheSPNB(idecl,
					this.archiType, this.listEquivalenzeArchiType);
				list.add(specificheSPNB);
				}
			if (iEquivalenza instanceof IEquivalenzaServizioConBuffer)
				{
				ISpecificheSPWB specificheSPWB = new Specifiche2Factory().createSpecificheSPWB(idecl,
					this.archiType, this.listEquivalenzeArchiType);
				list.add(specificheSPWB);
				}
			}
		return list;
		}
	
	/**
	 * Restituisce una mappa che ha come chiavi i nomi degli AET degli oggetti SpecificheSP e 
	 * come valori una lista di oggetti
	 * SpecificheSP con nome AET uguale alla chiave relativa.
	 *
	 * @param specificheSPList
	 * @return
	 */
	protected HashMap<String, List<ISpecificheSP>> generateSPListForAET(List<ISpecificheSP> specificheSPList) 
		{
		// dalla lista specificheSPList si prelevano istanze dello stesso aet
		// il risultato deve essere una mappa di liste di tipo SpecificheSP
		// la cui chiave è il nome di un tipo di elemento architetturale
		HashMap<String, List<ISpecificheSP>> hashMap = new HashMap<String, List<ISpecificheSP>>();
		for (ISpecificheSP specificheSP : specificheSPList)
			{
			String string = specificheSP.getNomeTipo();
			// si possono verificare due casi:
			// 1) se string è presente in hashMap allora si aggiunge specificheSP alla lista
			// relativa a String
			// 2) se string non è presente in hashMap allora si aggiunge un elemento
			// alla mappa con chiave uguale a string e come valore una lista con un unico
			// elemento pari a specificheSP
			if (hashMap.containsKey(string))
				{
				List<ISpecificheSP> list = hashMap.get(string);
				list.add(specificheSP);
				}
			else
				{
				List<ISpecificheSP> list = new ArrayList<ISpecificheSP>();
				list.add(specificheSP);
				hashMap.put(string, list);
				}
			}
		return hashMap;
		}
	
	/**
	 * A partire da hashMap, si costruisce una mappa che ha le stesse chiavi di hashMap e 
	 * ha come valore true se e solo se
	 * ogni lista relativa ad una chiave ha oggetti SpecificheSP con la stessa distribuzione 
	 * del tempo di servizio del processo di servizio.
	 *
	 * @param hashMap
	 * @return
	 */
	protected HashMap<String, Boolean> verificaDistribuzioni(HashMap<String, List<ISpecificheSP>> hashMap)
			throws RestrizioniSpecException {
			// per ognuna delle liste precedenti si verifica che le componenti di tale lista
			// hanno la stessa distribuzione di tempo esponenziale
			// il risultato di tale verifica viene messo in una HashMap
			HashMap<String, Boolean> hashMap2 = new HashMap<String, Boolean>();
			Set<Map.Entry<String, List<ISpecificheSP>>> set = hashMap.entrySet();
			for (Map.Entry<String, List<ISpecificheSP>> entry : set)
				{
				List<ISpecificheSP> list = entry.getValue();
				boolean b = true;
				if (list == null || list.isEmpty())
					throw new RestrizioniSpecException("Specific restrictions error" +
						" for multiserver center");
				ISpecificheSP specificheSP = list.get(0);
				HashMap<String, Expression[]> hashMap3 = specificheSP.getEquivalenzaServizio().getTassiServizioFromSel();
				for (int j = 1; j < list.size(); j++)
					{
					ISpecificheSP specificheSP2 = list.get(j);
					HashMap<String, Expression[]> hashMap4 = specificheSP2.getEquivalenzaServizio().getTassiServizioFromSel();
					if (!Equals.equalsServiceTimesMap(hashMap3, hashMap4)) b = false;
					}
				hashMap2.put(entry.getKey(), b);
				}
			return hashMap2;
			}

	/**
	 * Restituisce una sottomappa di hashMap che ha le stesse chiavi hashMap con valore corrispondente 
	 * di hashMap2 uguale a true.
	 * 
	 * @param hashMap
	 * @param hashMap2
	 * @return
	 */
	protected HashMap<String, List<ISpecificheSP>> deleteElements(HashMap<String, 
				List<ISpecificheSP>> hashMap, HashMap<String, Boolean> hashMap2) 
		{
		Set<Map.Entry<String, Boolean>> set2 = hashMap2.entrySet();
		for (Map.Entry<String, Boolean> entry : set2)
			{
			boolean b = entry.getValue();
			String string = entry.getKey();
			if (!b) hashMap.remove(string);
			}
		return hashMap;
		}
	
	/**
	 * Restituisce una mappa con le stesse chiavi di hashMap e che ha valore true se e solo se tutti gli elementi di ognuna delle liste
	 * valore hanno le stesse interazioni collegate al processo di servizio wrappato da un oggetto SpecificheSP.
	 *
	 * @param hashMap
	 * @return
	 */
	protected HashMap<String, Boolean> verificaAEIInteractions(HashMap<String, List<ISpecificheSP>> hashMap)
			throws RestrizioniSpecException {
			// per ognuna delle liste precedenti si verifica che le componenti di tale lista
			// abbiano le stesse interazioni attaccate
			HashMap<String, Boolean> hashMap2 = new HashMap<String, Boolean>();
			Set<Map.Entry<String, List<ISpecificheSP>>> set3 = hashMap.entrySet();
			for (Map.Entry<String, List<ISpecificheSP>> entry : set3)
				{
				List<ISpecificheSP> list = entry.getValue();
				boolean b = true;
				if (list == null || list.isEmpty())
					throw new RestrizioniSpecException("Specific restrictions error for" +
						" multiserver center");
				ISpecificheSP specificheSP = list.get(0);
				for (int j = 1; j < list.size(); j++)
					{
					ISpecificheSP specificheSP2 = list.get(j);
					if (!sameInteractions(specificheSP, specificheSP2)) b = false;
					}
				hashMap2.put(entry.getKey(), b);
				}
			return hashMap2;
			}
	
	/**
	 * Restituisce true se e solo se specifiche e specifiche2 sono collegate alle stesse istanze.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public boolean sameInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) 
		{
		return sameInputInteractions(specifiche, specifiche2) &&
			sameOutputInteractions(specifiche, specifiche2);
		}
	
	/**
	 * Restituisce true se e solo se specifiche e specifiche2 hanno le stesse istanze di input
	 * collegate.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public boolean sameInputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) 
		{
		List<Interaction> list = specifiche.getActionsInputFromAttacs(specifiche.getAttacsDeclOutput());
		List<Interaction> list2 = specifiche2.getActionsInputFromAttacs(specifiche2.getAttacsDeclOutput());
		if (!list.containsAll(list2))
			return false;
		if (!list2.containsAll(list))
			return false;
		return true;
		}
	
	/**
	 * Restituisce true se e solo se specifiche e specifiche2 hanno le stesse istanze di output
	 * collegate.
	 *
	 * @param specifiche
	 * @param specifiche2
	 * @return
	 */
	public boolean sameOutputInteractions(ISpecifiche specifiche, ISpecifiche specifiche2) 
		{
		List<Interaction> list = specifiche.getActionsOutputFromAttacs(specifiche.getAttacsDeclInput());
		List<Interaction> list2 = specifiche2.getActionsOutputFromAttacs(specifiche2.getAttacsDeclInput());
		if (!list.containsAll(list2))
			return false;
		if (!list2.containsAll(list2))
			return false;
		return true;
		}
	
	public HashMap<String, List<ISpecificheSP>> getMultiServerMap() 
		{
		return multiServerMap;
		}

	public void setMultiServerMap(HashMap<String, List<ISpecificheSP>> multiServerMap) 
		{
		this.multiServerMap = multiServerMap;
		}
	
	/*
	 * I processi di servizio che costituiscono il centro di servizio multiserver sono istanze 
	 * dello stesso AET con la stessa distribuzione del tempo di servizio individuale 
	 * e interazioni attaccate agli stessi AEI.
	 */
	public boolean caratterizzazione()
		{
		return true;
		}
	}

package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheArriviFiniti;
import restrizioniSpecifiche.interfaces.ISpecificheSCAP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiElemInstances;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;

public class SpecificheArriviFiniti2 
	implements ISpecificheArriviFiniti 
	{

	protected HashMap<String, List<ISpecificheSCAP>> hashMapSCAP;
	protected ArchiType archiType;
	protected List<IEquivalenza> listEquivalenzeArchiType;

	public SpecificheArriviFiniti2(ArchiType archiType)
		throws RestrizioniSpecException
		{
		this.archiType = archiType;
		inizializzazioneEquivalenze();
		inizializzazioneSCAPMap();
		}

	@Override
	public HashMap<String, List<ISpecificheSCAP>> getHashMapSCAP() 
		{
		return this.hashMapSCAP;
		}

	@Override
	public boolean isCompliantSpecificRules() throws RestrizioniSpecException 
		{
		return caratterizzazione() && restrizioneIstanze1();
		}
	
	public boolean isCompliantFullSpecificRules() throws RestrizioniSpecException
		{
		return caratterizzazione() && restrizioneIstanze1() && restrizioneIstanze2();
		}

	/**
	 * Costruisce la mappa delle liste di tutti i processi di arrivi finiti.
	 *
	 * @throws RestrizioniSpecException
	 */
	protected void inizializzazioneSCAPMap() 
		throws RestrizioniSpecException 
		{
		// si crea una lista che contiene tutti i tipi di elementi architetturali equivalenti
		// ad un processo di arrivi per un singolo cliente
		List<IEquivalenza> list = getProcessiArriviFiniti();
		HashMap<String, List<AEIdecl>> hashMap = getInstancesMap(list);
		// per ogni tipo di processo di arrivi finiti si costruisce una lista di SpecificheSCAP
		HashMap<String, List<ISpecificheSCAP>> hashMap2 = getSpecificheSCAPMap(hashMap);
		this.hashMapSCAP = hashMap2;
		}
	
	/**
	 * Restituisce tutti i processi di arrivi finiti presenti nella lista delle equivalenzaFactory del
	 * tipo architetturale.
	 *
	 * @return
	 */
	protected List<IEquivalenza> getProcessiArriviFiniti() 
		{
		List<IEquivalenza> list = new ArrayList<IEquivalenza>();
		for (IEquivalenza iEquivalenza : listEquivalenzeArchiType)
			{
			if (iEquivalenza instanceof IEquivalenzaArriviFiniti)
				list.add(iEquivalenza);
			}
		return list;
		}

	/**
	 * Restituisce una mappa, in cui le chiavi sono i  nomi dei tipi di elementi architetturali
	 * presenti in list e come valore una lista di istanze del nome del tipo di elemento architetturale
	 * relativo alla chiave.
	 *
	 * @param list
	 * @return
	 * @throws RestrizioniSpecException
	 */
	protected HashMap<String, List<AEIdecl>> getInstancesMap(List<IEquivalenza> list)
			throws RestrizioniSpecException {
			HashMap<String, List<AEIdecl>> hashMap = new HashMap<String, List<AEIdecl>>();
			for (IEquivalenza iEquivalenza : list)
				{
				String string = iEquivalenza.getEt().getHeader().getName();
				ArchiElemInstances archiElemInstances = this.archiType.getTopologia().getAEIs();
				List<AEIdecl> list2 = getAEIsFromAET(string, archiElemInstances);
				hashMap.put(string, list2);
				}
			return hashMap;
			}

	/**
	 * Restituisce la lista di dichiarazioni di istanze del tipo di elemento architetturale di nome string.
	 *
	 * @param string
	 * @param archiElemInstances
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public List<AEIdecl> getAEIsFromAET(String string, ArchiElemInstances archiElemInstances)
			throws RestrizioniSpecException {
			List<AEIdecl> list2 = new ArrayList<AEIdecl>();
			AEIdecl[] idecls = archiElemInstances.getAEIdeclSeq();
			boolean b = false;
			for (int i = 0; i <idecls.length; i++)
				{
				String string2 = idecls[i].getAET();
				if (string.equals(string2))
					{
					b = true;
					list2.add(idecls[i]);
					}
				}
			if (!b) throw new RestrizioniSpecException(string+" is not found");
			return list2;
			}
	
	/**
	 * Restituisce una mappa di oggetti SpecificheSCAP relativi a hashMap.
	 *
	 * @param hashMap
	 * @return
	 * @throws RestrizioniSpecException
	 */
	private HashMap<String, List<ISpecificheSCAP>> getSpecificheSCAPMap(HashMap<String, List<AEIdecl>> hashMap)
			throws RestrizioniSpecException {
			HashMap<String, List<ISpecificheSCAP>> hashMap2 = new HashMap<String, List<ISpecificheSCAP>>();
			Set<Entry<String, List<AEIdecl>>> set = hashMap.entrySet();
			for (Entry<String, List<AEIdecl>> entry : set)
				{
				List<ISpecificheSCAP> list2 = new ArrayList<ISpecificheSCAP>();
				String string = entry.getKey();
				List<AEIdecl> list3 = entry.getValue();
				for (AEIdecl idecl : list3)
					{
					ISpecificheSCAP specificheSCAP = new Specifiche2Factory().createSpecificheSCAP(idecl, this.archiType, 
							this.listEquivalenzeArchiType);
					list2.add(specificheSCAP);
					}
				hashMap2.put(string, list2);
				}
			return hashMap2;
			}
	
	/**
	 * Tutti gli AEI, che modellano i clienti della stessa popolazione finita, 
	 * devono essere istanze dello stesso AET.
	 * 
	 * @return
	 */
	public boolean caratterizzazione()
		{
		// superfluo: viene verificato dal compilatore AEmilia
		return true;
		}
	
	/**
	 * Le istanze di un AET, equivalente ad un processo di arrivi per una popolazione finita, 
	 * devono essere attaccate alle stesse interazioni di input 
	 * (attaccate alle azioni di consegna).
	 * 
	 * @return
	 */
	public boolean restrizioneIstanze1()
		{
		// si verifica che tutti gli oggetti SpecificheSCAP hanno le stesse interazioni di input collegate
		return sameInputInteractions();
		}
	
	/**
	 * Le istanze di un AET, equivalente ad un processo di arrivo per una popolazione finita, 
	 * devono essere attaccate alle stesse interazioni di output 
	 * (attaccate alle azioni di ritorno).
	 * 
	 * @return
	 */
	public boolean restrizioneIstanze2()
		{
		// si verifica che tutti gli oggetti SpecificheSCAP 
		// hanno le stesse interazioni di output collegate
		return sameOutputInteractions(hashMapSCAP);
		}
	
	/**
	 * Restituisce true se e solo se, per ogni chiave, hashMapSCAP contiene una liste di SpecificheSCAP
	 * che hanno le stesse istanze di input collegate.
	 *
	 * @return
	 */
	protected boolean sameInputInteractions() 
		{
		// si verifica che tutti gli oggetti SpecificheSCAP relativi ad un processo di arrivi finiti
		// hanno le stesse interazioni di input collegate
		boolean b = true;
		Set<Entry<String, List<ISpecificheSCAP>>> set2 = this.hashMapSCAP.entrySet();
		for (Entry<String, List<ISpecificheSCAP>> entry : set2)
			{
			List<ISpecificheSCAP> list2 = entry.getValue();
			ISpecificheSCAP specificheSCAP = list2.get(0);
			for (int i = 1; i < list2.size(); i++)
				{
				ISpecificheSCAP specificheSCAP2 = list2.get(i);
				b = b && SpecificheRules2.sameInputInteractions(specificheSCAP, specificheSCAP2);
				}
			}
		return b;
		}
	
	/**
	 * Restituisce true se e solo se, per ogni chiave, 
	 * hashMap contiene una liste di SpecificheSCAP
	 * che hanno le stesse istanze di output collegate.
	 *
	 * @param hashMap
	 * @return
	 */
	protected boolean sameOutputInteractions(HashMap<String, List<ISpecificheSCAP>> hashMap) 
		{
		// si verifica che tutti gli oggetti SpecificheSCAP relativi ad un processo di arrivi finiti
		// hanno le stesse interazioni di output collegate
		boolean b = true;
		Set<Entry<String, List<ISpecificheSCAP>>> set2 = hashMap.entrySet();
		for (Entry<String, List<ISpecificheSCAP>> entry : set2)
			{
			List<ISpecificheSCAP> list2 = entry.getValue();
			ISpecificheSCAP specificheSCAP = list2.get(0);
			for (int i = 1; i < list2.size(); i++)
				{
				ISpecificheSCAP specificheSCAP2 = list2.get(i);
				b = b && SpecificheRules2.sameOutputInteractions(specificheSCAP, specificheSCAP2);
				}
			}
		return b;
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
	}

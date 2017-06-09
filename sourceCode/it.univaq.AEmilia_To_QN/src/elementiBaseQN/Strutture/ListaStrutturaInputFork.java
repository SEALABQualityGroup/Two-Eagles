package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

import elementiBaseQN.ElementoBaseQN;

public class ListaStrutturaInputFork extends ArrayList<StrutturaInputFork> 
	{

	private static final long serialVersionUID = 1L;

	public ListaStrutturaInputFork(HashMap<String, List<ElementoBaseQN>> hashMap)
		{
		super();
		Set<Entry<String, List<ElementoBaseQN>>> set = hashMap.entrySet();
		for (Entry<String, List<ElementoBaseQN>> entry : set)
			{
			String string = entry.getKey();
			List<ElementoBaseQN> list = entry.getValue();
			StrutturaInputFork strutturaInputFork = new StrutturaInputFork(string,list);
			this.add(strutturaInputFork);
			}
		}
	
	public ListaStrutturaInputFork() 
		{
		super();
		}

//	public ListaStrutturaInputFork(HashMap<String, ElementoBaseQN> sorgenti) 
//		{
//		super(sorgenti.size());
//		Set<Entry<String, ElementoBaseQN>> set = sorgenti.entrySet();
//		for (Entry<String, ElementoBaseQN> entry : set)
//			{
//			String string = entry.getKey();
//			ElementoBaseQN elementoBaseQN = entry.getValue();
//			List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>(1);
//			list.add(elementoBaseQN);
//			StrutturaInputFork strutturaInputFork = new StrutturaInputFork(string,list);
//			this.add(strutturaInputFork);
//			}
//		}

	/**
	 * Aggiunge una struttura di input per il processo fork con nome dell'interazione uguale a 
	 * string e elementi connessi uguali a list.
	 * 
	 */
	public void put(String string, List<ElementoBaseQN> list) 
		{
		StrutturaInputFork strutturaInputFork = new StrutturaInputFork(string,list);
		this.add(strutturaInputFork);
		}

	/**
	 * Restituisce l'insieme delle associazioni tra interazioni di input e elementi base connessi.
	 * 
	 * @return
	 */
	public Set<Entry<String, List<ElementoBaseQN>>> entrySet() 
		{
		HashMap<String, List<ElementoBaseQN>> hashMap = getHashMap();
		Set<Entry<String, List<ElementoBaseQN>>> set = hashMap.entrySet();
		return set;
		}
	
	/**
	 * Restituisce una mappa che associa ogni nome di azione di input con 
	 * la lista degli elementi base connessi.
	 * 
	 * @return
	 */
	public HashMap<String, List<ElementoBaseQN>> getHashMap()
		{
		HashMap<String, List<ElementoBaseQN>> hashMap = new HashMap<String, List<ElementoBaseQN>>();
		for (StrutturaInputFork strutturaInputFork : this)
			{
			String string = strutturaInputFork.getInput();
			List<ElementoBaseQN> list = strutturaInputFork.getSorgenti();
			hashMap.put(string, list);
			}
		return hashMap;
		}

	/**
	 * Crea una struttura per le interazioni di input del fork per ogni elemento della mappa
	 * sorgenti. Come risultato, l'interazione di input sarà connesso ad un unico elemento base.
	 * 
	 * @param sorgenti
	 */
	public void putForSingle(HashMap<String, ElementoBaseQN> sorgenti) 
		{
		Set<Entry<String, ElementoBaseQN>> set = sorgenti.entrySet();
		for (Entry<String, ElementoBaseQN> entry : set)
			{
			String string = entry.getKey();
			ElementoBaseQN elementoBaseQN = entry.getValue();
			List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>(1);
			list.add(elementoBaseQN);
			StrutturaInputFork strutturaInputFork = new StrutturaInputFork(string,list);
			this.add(strutturaInputFork);
			}
		}
	
	/**
	 * 
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaFromInput(String string)
		{
		StrutturaInterazioneInput strutturaInterazioneInput = null;
		for (StrutturaInputFork strutturaInputFork : this)
			{
			String string2 = strutturaInputFork.getInput();
			if (string.equals(string2))
				strutturaInterazioneInput = strutturaInputFork;
			}
		return strutturaInterazioneInput;
		}
	
	/**
	 * Restituisce la prima sorgente dell'interazione di input del fork.
	 * 
	 * @return
	 */
	public ElementoBaseQN getPrimaSorgente()
		{
		StrutturaInputFork strutturaInputFork =
			this.get(0);
		return strutturaInputFork.getSorgente();
		}
	
	/**
	 * Restituisce la struttura di input del processo fork con nome dell'interazione uguale a 
	 * string.
	 * 
	 * @param string
	 * @return
	 */
	// restituisce null se string non è stata trovata come azione di input 
	public StrutturaInputFork getStrutturaInputForkForName(String string)
		{
		for (StrutturaInputFork strutturaInputFork : this)
			{
			String string2 = strutturaInputFork.getInput();
			if (string.equals(string2))
				return strutturaInputFork;
			}
		return null;
		}
	
	/**
	 * Sostituisce elementoBaseQN con ogni elemento presente in list.
	 * 
	 * @param elementoBaseQN
	 * @param list
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list)
		{
		for (StrutturaInputFork strutturaInputFork : this)
			{
			// si cerca la sorgente elementoBaseQN
			List<ElementoBaseQN> list2 =
				strutturaInputFork.getSorgenti();
			ElementoBaseQN[] elementoBaseQNs = new ElementoBaseQN[list2.size()];
			list2.toArray(elementoBaseQNs);
			CopyOnWriteArrayList<ElementoBaseQN> copyOnWriteArrayList =
				new CopyOnWriteArrayList<ElementoBaseQN>(elementoBaseQNs);
			for (ElementoBaseQN elementoBaseQN2 : copyOnWriteArrayList)
				{
				if (elementoBaseQN.equals(elementoBaseQN2))
					{
					// si rimuove la vecchia sorgente
					list2.remove(elementoBaseQN2);
					// si aggiungono le nuovi sorgenti, se non sono già presenti
					for (ElementoBaseQN elementoBaseQN3 : list)
						{
						if (!list2.contains(elementoBaseQN3))
							list2.add(elementoBaseQN3);
						}
					}
				}
			}
		}
	}
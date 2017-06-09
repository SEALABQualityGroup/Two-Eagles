package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;

public class ListaStrutturaOutputFork extends ArrayList<StrutturaOutputFork> 
	{

	private static final long serialVersionUID = 1L;

	public void put(String string, List<Destinazione> list) 
		{
		StrutturaOutputFork strutturaOutputFork = new StrutturaOutputFork(string,list);
		this.add(strutturaOutputFork);
		}
	
	public ListaStrutturaOutputFork(HashMap<String, List<Destinazione>> hashMap) {
		super();
		Set<Entry<String, List<Destinazione>>> set = hashMap.entrySet();
		for (Entry<String, List<Destinazione>> entry : set)
			{
			String string = entry.getKey();
			List<Destinazione> list = entry.getValue();
			StrutturaOutputFork strutturaOutputFork = new StrutturaOutputFork(string,list);
			this.add(strutturaOutputFork);
			}
		}

	public ListaStrutturaOutputFork() 
		{
		super();
		}

	/**
	 * Restituisce una mappa che ha come chiavi i nomi delle interazioni di output del
	 * processo fork e come valore una lista di destinazioni connesse alla chiave.
	 * 
	 * @return
	 */
	public HashMap<String, List<Destinazione>> getHashMap()
		{
		HashMap<String, List<Destinazione>> hashMap = new HashMap<String, List<Destinazione>>();
		for (StrutturaOutputFork strutturaOutputFork : this)
			{
			String string = strutturaOutputFork.getOutput();
			List<Destinazione> list = strutturaOutputFork.getDestinazioni();
			hashMap.put(string, list);
			}
		return hashMap;
		}

	/**
	 * Restituisce un'insieme di coppie (stringa,lista), in cui stringa è il nome di un'interazione
	 * di output e lista è la lista delle destinazioni connesse a stringa.
	 * 
	 * @return
	 */
	public Set<Entry<String, List<Destinazione>>> entrySet() 
		{
		HashMap<String, List<Destinazione>> hashMap = getHashMap();
		Set<Entry<String, List<Destinazione>>> set = hashMap.entrySet();
		return set;
		}

	/**
	 * Aggiunge una struttura di interazione di output per ogni associazione presente in destinazioni.
	 * Ogni struttura di output aggiunta avrà una sola destinazione associata all'azione di
	 * output.
	 * 
	 * @param destinazioni
	 */
	public void putForSingle(HashMap<String, Destinazione> destinazioni) 
		{
		Set<Entry<String, Destinazione>> set = destinazioni.entrySet();
		for (Entry<String, Destinazione> entry : set)
			{
			String string = entry.getKey();
			Destinazione destinazione = entry.getValue();
			List<Destinazione> list = new ArrayList<Destinazione>(1);
			list.add(destinazione);
			StrutturaOutputFork strutturaOutputFork = new StrutturaOutputFork(string,list);
			this.add(strutturaOutputFork);
			}
		}
	
	/**
	 * Restituisce la struttura per l'interazione di output relativa all'azione string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaFromOutput(String string)
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput = null;
		for (StrutturaOutputFork strutturaOutputFork : this)
			{
			String string2 = strutturaOutputFork.getOutput();
			if (string.equals(string2))
				strutturaInterazioneOutput = strutturaOutputFork;
			}
		return strutturaInterazioneOutput;
		}
	
	/**
	 * Si sostituisce destinazione con ogni elemento presente in list,
	 * se non è già presente.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list)
		{
		for (StrutturaOutputFork strutturaOutputFork : this)
			{
			// si cerca destinazione
			List<Destinazione> list2 =
				strutturaOutputFork.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si rimuove la vecchia destinazione
					list2.remove(destinazione2);
					// si aggiungono le nuovi destinazioni, se non sono già presenti
					for (Destinazione destinazione3 : list)
						{
						if (!list2.contains(destinazione3))
							list2.add(destinazione3);
						}
					}
				}
			}
		}
	}	

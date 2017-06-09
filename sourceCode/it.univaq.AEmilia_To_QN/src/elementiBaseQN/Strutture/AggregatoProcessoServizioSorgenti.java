package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;

public class AggregatoProcessoServizioSorgenti extends
		ArrayList<DataProcessoServizioSorgente> 
	{
	private static final long serialVersionUID = 1L;

	public AggregatoProcessoServizioSorgenti() 
		{
		super();
		}

	// utilizzato per i processi di servizio con buffer
	// list contiene i nomi delle interazioni di input
	// list2 contiene le priorità di selezione
	// list3 contiene le probabilità di selezione
	// hashMap contiene i tempi di servizio
	// hashMap2 contiene i nomi delle classi di clienti
	// hashMap3 contiene l'ordine delle classi
	public AggregatoProcessoServizioSorgenti(
			List<String> list,
			List<Integer> list2, 
			List<Double> list3,
			HashMap<String, Double> hashMap, 
			HashMap<String, String> hashMap2,
			HashMap<String, Integer> hashMap3)
		throws ElementoBaseException
		{
		super();
		if (list.size() != list2.size() && list2.size() != list3.size() && list3.size() != hashMap.size())
			throw new ElementoBaseException("The input lists and maps have not same size");
		int contatoreNullOrdine = 0;
		for (int i = 0; i < list.size(); i++)
			{
			String string = list.get(i);
			Integer integer = hashMap3.get(string);
			if (integer == null)
				{
				integer = contatoreNullOrdine;
				contatoreNullOrdine++;
				}
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				new DataProcessoServizioSorgente(integer);
			// si assegna il nome dell'azione di selezione
			dataProcessoServizioSorgente.setNomeAzioneSelezione(string);
			// si assegna la priorità dell'azione di selezione
			int j = list2.get(i);
			dataProcessoServizioSorgente.setPriorita(j);
			// si assegna la probabilità dell'azione di selezione
			double d = list3.get(i);
			dataProcessoServizioSorgente.setProbabilita(d);
			// si assegna il relativo tempo di servizio
			if (!hashMap.containsKey(string))
				throw new ElementoBaseException("The selection action "
						+string+" have not relative service time ");
			double e = hashMap.get(string);
			dataProcessoServizioSorgente.setTempoServizio(e);
			// si assegna l'eventuale classe
			// Nel caso in cui non ci sono processi di arrivi di jobs, le interazioni possono non 
			// avere alcuna classe associata
			if (hashMap2.containsKey(string))
				{
				String string2 = hashMap2.get(string);
				dataProcessoServizioSorgente.setClasse(string2);
				}
			this.add(dataProcessoServizioSorgente);
			}
		}

	// utilizzato per i processi di servizio senza buffer
	// list contiene i nomi delle interazioni di input
	// list2 contiene le probabilità di selezione
	// hashMap contiene i tempi di servizio
	// hashMap2 contiene i nomi delle classi di clienti
	// hashMap3 contiene gli ordini di classe
	public AggregatoProcessoServizioSorgenti(
			List<String> list, 
			List<Double> list2,
			HashMap<String, Double> hashMap, 
			HashMap<String, String> hashMap2,
			HashMap<String, Integer> hashMap3)
		throws ElementoBaseException
		{
		super();
		if (list.size() != hashMap.size() || list.size() != list2.size())
			throw new ElementoBaseException("The input lists and maps have not same size");
		int contatoreNullOrdine = 0;
		for (int i = 0; i < list.size(); i++)
			{
			String string = list.get(i);
			Integer integer = hashMap3.get(string);
			if (integer == null)
				{
				integer = contatoreNullOrdine;
				contatoreNullOrdine++;
				}
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				new DataProcessoServizioSorgente(integer);
			// si assegna il nome dell'azione di selezione
			dataProcessoServizioSorgente.setNomeAzioneSelezione(string);
			// si assegna il relativo tempo di servizio
			if (!hashMap.containsKey(string))
				throw new ElementoBaseException("The selection action "
					+string+" have not relative service time ");
			double e = hashMap.get(string);
			dataProcessoServizioSorgente.setTempoServizio(e);
			// si assegna l'eventuale classe
			if (hashMap2.containsKey(string))
				{
				String string2 = hashMap2.get(string);
				dataProcessoServizioSorgente.setClasse(string2);
				}
			// si assegna la probabilità di selezione
			dataProcessoServizioSorgente.setProbabilita(list2.get(i));
			this.add(dataProcessoServizioSorgente);
			}
		}

	/**
	 * Restituisce un array di tutti i nomi delle classi dei canali di clienti che possono essere servite.
	 * Se per una determinata azione di selezione non è associata nessuna classe
	 * il campo della classe relativo è "".
	 * 
	 * @return
	 */
	public List<String> getClassi()
		{
		List<String> list = new ArrayList<String>();
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			String string = dataProcessoServizioSorgente.getClasse();
			list.add(string);
			}
		return list;
		}
	
	/**
	 * Si impostano le sorgenti uguali a list.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setSorgenti(List<ElementoBaseQN> list)
		throws ElementoBaseException
		{
		if (this.isEmpty())
			{
			// se this è vuoto, si aggiungono elementi DataProcessoServizioSorgente con 
			// sorgente uguale ad ogni elemento di list.			
			for (int i = 0; i < list.size(); i++)
				{
				ElementoBaseQN elementoBaseQN = list.get(i);
				DataProcessoServizioSorgente dataProcessoServizioSorgente = new DataProcessoServizioSorgente(i);
				dataProcessoServizioSorgente.setSorgente(elementoBaseQN);
				this.add(dataProcessoServizioSorgente);
				}
			}
		else 
			{
			// se this non è vuoto, si verifica se list e this hanno la stessa lunghezza
			// Se non hanno la stessa lunghezza si solleva un'eccezione ElementoBaseException.
			// Se hanno la stessa lunghezza, si aggiorna l'attributo sorgente di ogni oggetto
			// DataProcessoServizioSorgente presente.
			if (list.size() == this.size())
				{
				for (int i = 0; i < list.size(); i++)
					{
					DataProcessoServizioSorgente dataProcessoServizioSorgente = this.get(i);
					ElementoBaseQN elementoBaseQN = list.get(i);
					dataProcessoServizioSorgente.setSorgente(elementoBaseQN);
					}
				}
			else throw new ElementoBaseException("The sources aggregate and list"+
					" have not same size");
			}
		}
	
	/**
	 * Imposta i nomi delle azioni di consegna e relative probabilità
	 * di routing a seconda delle mappe hashMap3, hashMap4.
	 * hashMap3 contiene i nomi delle azioni di destinazione.
	 * hashMap4 contiene le probabilità di routing per ogni azione di destinazione.
	 * L'ordine tra nomi azione selezione e probabilità selezione conta.  
	 * 
	 * @param hashMap3
	 * @param hashMap4
	 * @throws AEIintoElementiBaseException
	 */
	public void setDestinazioniFromSelectionMaps(
			HashMap<String, List<String>> hashMap3,
			HashMap<String, List<Double>> hashMap4)
		throws ElementoBaseException
		{
		if (hashMap3.size() != hashMap4.size())
			throw new ElementoBaseException("The size of delivery actions names map "+
					" and the size of routing probabilities map have not same size");
		Set<Entry<String, List<String>>> set = hashMap3.entrySet();
		for (Entry<String, List<String>> entry : set)
			{
			String string = entry.getKey();
			List<String> list = entry.getValue();
			if (!hashMap4.containsKey(string))
				throw new ElementoBaseException(string+" have not relative routing probabilities");
			List<Double> list2 = hashMap4.get(string);
			// si preleva l'oggetto DataProcessoServizioSorgente relativo a string
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				getDataSorgenteFromSelectionName(string);
			// si assegnano le destinazioni a dataProcessoServizioSorgente
			// hashMap3 è una mappa che può contenere come valori delle liste che contengono dei null
			dataProcessoServizioSorgente.setDestinazioni(list,list2);
			}
		}

	/**
	 * Restituisce la struttura delle azioni di input dal nome dell'azione di selezione string.
	 * Restituisce null se l'azione di selezione non viene trovata. 
	 * 
	 * @param string
	 * @return
	 */
	private DataProcessoServizioSorgente getDataSorgenteFromSelectionName(
			String string) 
		{
		DataProcessoServizioSorgente dataProcessoServizioSorgente = null;
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente2 : this)
			{
			String string2 = dataProcessoServizioSorgente2.getNomeAzioneSelezione();
			if (string2.equals(string))
				dataProcessoServizioSorgente = dataProcessoServizioSorgente2;
			}
		return dataProcessoServizioSorgente;
		}

	/**
	 * Restituisce la struttura dell'interazione di input di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaFromInput(String string) 
		{
		StrutturaInterazioneInput strutturaInterazioneInput = null;
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			String string2 = dataProcessoServizioSorgente.getNomeAzioneSelezione();
			if (string.equals(string2))
				strutturaInterazioneInput = dataProcessoServizioSorgente;
			}
		return strutturaInterazioneInput;
		}

	/**
	 * Restituisce la struttura per l'interazione di output di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaFromOutput(String string) 
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput = null;
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			StrutturaInterazioneOutput strutturaInterazioneOutput2 
				= dataProcessoServizioSorgente.getStrutturaFromOutput(string);
			if (strutturaInterazioneOutput2 != null)
				strutturaInterazioneOutput = strutturaInterazioneOutput2;
			}
		return strutturaInterazioneOutput;
		}

	/**
	 * Restituisce la prima sorgente connessa al
	 * processo di servizio.
	 * 
	 * @return
	 */
	public ElementoBaseQN getPrimaSorgente() 
		{
		DataProcessoServizioSorgente dataProcessoServizioSorgente =
			this.get(0);
		ElementoBaseQN elementoBaseQN = 
			dataProcessoServizioSorgente.getPrimaSorgente();
		return elementoBaseQN;
		}
	
	/**
	 * Imposta la probabilità di routing di ogni struttura di output relativa ad ogni azione di
	 * consegna nel seguente modo: se i è il numero degli elementi base connessi all'azione di
	 * consegna e p è la probabilità di consegna associata all'azione, allora la nuova probabilità
	 * di routing è p / i.
	 * 
	 * @throws AEIintoElementiBaseException
	 */
	public void proporzionaProbabilita()
		throws ElementoBaseException
		{
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			dataProcessoServizioSorgente.proporzionaProbabilita();
			}
		}
	
	/**
	 * Si sostituisce destinazione con list.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		// si cerca destinazione
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			dataProcessoServizioSorgente.replaceDestination(destinazione, list);
			}
		}

	/**
	 * Si sostituisce la sorgente elementoBaseQN
	 * con list.
	 * 
	 * @param elementoBaseQN
	 * @param list
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		// si cerca la sorgente elementoBaseQN
		for (DataProcessoServizioSorgente dataProcessoServizioSorgente : this)
			{
			List<ElementoBaseQN> list2 = dataProcessoServizioSorgente.getSorgenti();
			ElementoBaseQN[] elementoBaseQNs = new ElementoBaseQN[list2.size()];
			list2.toArray(elementoBaseQNs);
			CopyOnWriteArrayList<ElementoBaseQN> copyOnWriteArrayList = 
				new CopyOnWriteArrayList<ElementoBaseQN>(elementoBaseQNs);
			for (ElementoBaseQN elementoBaseQN2 : copyOnWriteArrayList)
				{
				if (elementoBaseQN.equals(elementoBaseQN2))
					{
					// si rimuove la sorgente vecchia
					list2.remove(elementoBaseQN2);
					// si aggiungono le nuovi sorgenti se non sono già presenti
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

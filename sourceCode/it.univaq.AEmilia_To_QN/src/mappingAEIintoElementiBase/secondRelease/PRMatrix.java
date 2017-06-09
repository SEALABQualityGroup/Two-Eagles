package mappingAEIintoElementiBase.secondRelease;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import elementiBaseQN.Buffer;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;
import elementiBaseQN.ProcessoArrivi;
import elementiBaseQN.ProcessoFork;
import elementiBaseQN.ProcessoJoin;
import elementiBaseQN.ProcessoRouting;
import elementiBaseQN.ProcessoServizio;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoJoinConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoServizioDestinazioni;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;
import elementiBaseQN.Strutture.DataProcessoArriviConsegna;
import elementiBaseQN.Strutture.DataProcessoJoinConsegna;
import elementiBaseQN.Strutture.DataProcessoRoutingConsegna;
import elementiBaseQN.Strutture.DataProcessoServizioDestinazione;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

public class PRMatrix 
	{
	
	// list deve essere una vista della qn riferita ad un particolare carico di lavoro
	// l'ultimo elemento di list si riferisce al flusso emanato da un processo di arrivi
	// list può contenere degli elementi null, che indicano un elemento base che non cura richieste
	// per il carico di lavoro relativo alla vista.
	private List<ElementoBaseQN> list;
	
	public PRMatrix(List<ElementoBaseQN> list) 
		{
		super();
		// mettiamo l'unico processo di arrivi alla fine di list
		List<ElementoBaseQN> list2 = new ArrayList<ElementoBaseQN>();
		ElementoBaseQN termineNotoEB = null;
		int indiceTermineNoto = -1;
		for (int i = 0; i < list.size(); i++)
			{
			ElementoBaseQN elementoBaseQN = list.get(i);
			if (elementoBaseQN instanceof ProcessoArrivi)
				{
				termineNotoEB = elementoBaseQN;
				indiceTermineNoto = i;
				break;
				}
			}
		// nel caso in cui termineNotoEB è null, consideriamo il primo dei processi
		// di servizio incontrati per il vettore dei termini noti
		if (indiceTermineNoto == -1)
			{
			for (int i = 0; i < list.size(); i++)
				{
				ElementoBaseQN elementoBaseQN = list.get(i);
				if (elementoBaseQN instanceof ProcessoServizio)
					{
					termineNotoEB = elementoBaseQN;
					indiceTermineNoto = i;
					break;
					}
				}
			}
		// altrimenti la colonna dei termini noti è relativa al primo elemento di list
		if (indiceTermineNoto == -1) 
			{
			termineNotoEB = list.get(0);
			indiceTermineNoto = 0;
			}
		// si rimuove termineNotoEB da list
		// per precondizione indiceTermineNoto è diverso da -1
		list.remove(indiceTermineNoto);
		for (ElementoBaseQN elementoBaseQN : list)
			{
			// aggiungiamo solo gli elementi diversi da null
			if (elementoBaseQN != null)
				list2.add(elementoBaseQN);
			}
		// aggiungiamo il termine noto a list
		list2.add(termineNotoEB);
		this.list = list2;
		}

	// implementiamo il calcolo di una lista 
	// di probabilità di routing di uscita
	// per ogni elemento base appartenente alla vista della rete di 
	// code per un carico di lavoro
	public List<List<Double>> getMatrixFromEbView()
		{
		List<List<Double>> list = new ArrayList<List<Double>>();
		for (int i = 0; i < this.list.size(); i++)
			{
			ElementoBaseQN elementoBaseQN = this.list.get(i);
			List<Double> list2 = getPRListFromBE(elementoBaseQN);
			list.add(list2);
			}
		// bisogna effettuare la trasposizione di list
		list = traspondi(list);
		List<List<Double>> list21 = new ArrayList<List<Double>>();
		for (int i = 0; i < list.size(); i++)
			{
			List<Double> list2 = list.get(i);
			List<Double> list3 = generateColumn(list2,i);
			list21.add(list3);
			}
		// nella lista da restituire non consideriamo l'ultima riga perchè 
		// corrisponde al vettore di flusso per il processo di arrivi
		list21.remove(list21.size() - 1);
		return list21;
		}
	
	private List<Double> generateColumn(List<Double> list2, int i) 
		{
		/*
		 * di(i) = 1 - di(i)
		 * di(j) = -di(j) per ogni j diverso da i e N
		 * di(N) = di(N)
		 */
		// impostiamo l'i-esimo elemento
		Double double1 = list2.get(i);
		list2.set(i, 1 - double1);
		// impostiamo ogni elemento di indice diverso da i e list2.size()
		for (int j = 0; j < list2.size() - 1; j++)
			{
			if (j != i)
				{
				Double double2 = list2.get(j);
				list2.set(j, -double2);
				}
			}
		return list2;
		}

	// list2 deve corrispondere ad una matrice quadrata
	private List<List<Double>> traspondi(List<List<Double>> list2) 
		{
		Double[][] a = new Double[list2.get(0).size()][list2.size()];
		for (int i = 0; i < list2.size(); i++)
			{
			List<Double> list3 = list2.get(i);
			for (int j = 0; j < list3.size(); j++)
				{
				a[j][i] = list3.get(j);
				}
			}
		List<List<Double>> copyOnWriteArrayList =
			new CopyOnWriteArrayList<List<Double>>();
		for (Double[] doubles : a)
			{
			List<Double> copyOnWriteArrayList2 = new CopyOnWriteArrayList<Double>(doubles);
			copyOnWriteArrayList.add(copyOnWriteArrayList2);
			}
		return copyOnWriteArrayList;
		}

	public List<Double> getPRListFromBE(ElementoBaseQN elementoBaseQN)
		{
		List<Double> list = null;
		if (elementoBaseQN instanceof Buffer<?>)
			{
			Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
			list = getPRListFromBuffer(buffer);
			}
		if (elementoBaseQN instanceof ProcessoArrivi)
			{
			ProcessoArrivi processoArrivi = (ProcessoArrivi)elementoBaseQN;
			list = getPRListFromArrivi(processoArrivi);
			}
		if (elementoBaseQN instanceof ProcessoFork)
			{
			ProcessoFork processoFork = (ProcessoFork)elementoBaseQN;
			list = getPRListFromFork(processoFork);
			}
		if (elementoBaseQN instanceof ProcessoJoin)
			{
			ProcessoJoin processoJoin = (ProcessoJoin)elementoBaseQN;
			list = getPRListFromJoin(processoJoin);
			}
		if (elementoBaseQN instanceof ProcessoRouting)
			{
			ProcessoRouting processoRouting = (ProcessoRouting)elementoBaseQN;
			list = getPRListFromRouting(processoRouting);
			}
		if (elementoBaseQN instanceof ProcessoServizio)
			{
			ProcessoServizio processoServizio = (ProcessoServizio)elementoBaseQN;
			list = getPRListFromServizio(processoServizio);
			}
		return list;
		}
	
	/*
	 * p(j) è 1 se j è connesso al buffer. 
	 * Genera una colonna della matrice di conservazione del flusso.
	 * 
	 */
	public List<Double> getPRListFromBuffer(Buffer<?> buffer)
		{
		List<Double> list = new ArrayList<Double>();
		AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			// buffer ha un unico buffer
			DataBuffer dataBuffer = aggregatoBuffer.get(0);
			List<Destinazione> list2 = dataBuffer.getDestinazioni();
			if (MetodiVari.isViewIn(list2, elementoBaseQN))
				list.add(1d);
			else
				list.add(0d);
			}
		return list;
		}
	
	/*
	 * p(j) = prob(j) dove j è un nodo connesso al processo di arrivi. Rappresenta 
	 * la colonna dei termini noti.
	 * 
	 */
	public List<Double> getPRListFromArrivi(ProcessoArrivi processoArrivi)
		{
		List<Double> list = new ArrayList<Double>();
		AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne =
			processoArrivi.getAggregatoProcessoArriviConsegne();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			Double double1 = 0d;
			for (DataProcessoArriviConsegna dataProcessoArriviConsegna : aggregatoProcessoArriviConsegne)
				{
				List<Destinazione> list2 = dataProcessoArriviConsegna.getDestinazioni();
				Double double2 = dataProcessoArriviConsegna.getProbRouting();
				if (MetodiVari.isViewIn(list2, elementoBaseQN))
					// consideriamo anche il caso in cui ci sono due destinazioni uguali
					double1 = double1 + double2;
				}
			list.add(double1);
			}
		
		return list;
		}
	
	/*
	 * p(j) = 1 se j è un nodo connesso al fork. 
	 * Genera una colonna della matrice di conservazione del flusso.
	 */
	public List<Double> getPRListFromFork(ProcessoFork processoFork)
		{
		List<Double> list = new ArrayList<Double>();
		HashMap<String, List<Destinazione>> hashMap = processoFork.getDestinazioni();
		Set<Entry<String, List<Destinazione>>> set = hashMap.entrySet();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			boolean trovato = false;
			for (Entry<String, List<Destinazione>> entry : set)
				{
				List<Destinazione> list2 = entry.getValue();
				if(MetodiVari.isViewIn(list2, elementoBaseQN))
					trovato = true;
				}
			if (trovato)
				list.add(1d);
			else
				list.add(0d);
				
			}
		return list;
		}
	
	/*
	 * p(j) = prob(j) se j è un nodo connesso al join.
	 * Genera una colonna della matrice di conservazione del flusso.
	 */
	public List<Double> getPRListFromJoin(ProcessoJoin processoJoin)
		{
		List<Double> list = new ArrayList<Double>();
		AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne = 
			processoJoin.getAggregatoProcessoJoinConsegne();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			Double double1 = 0d;
			for (DataProcessoJoinConsegna dataProcessoJoinConsegna : aggregatoProcessoJoinConsegne)
				{
				List<Destinazione> list2 = dataProcessoJoinConsegna.getDestinazioni();
				Double double2 = dataProcessoJoinConsegna.getProbabilita();
				if (MetodiVari.isViewIn(list2, elementoBaseQN))
					// consideriamo anche il caso in cui ci sono due destinazioni uguali
					double1 = double1 + double2;
				}
			list.add(double1);
			}
		return list;
		}
	
	/*
	 * p(j) = prob(j) se j è un nodo connesso al routing.
	 * Genera una colonna della matrice di conservazione del flusso.
	 */
	public List<Double> getPRListFromRouting(ProcessoRouting processoRouting)
		{
		List<Double> list = new ArrayList<Double>();
		AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne = 
			processoRouting.getAggregatoProcessoRoutingConsegne();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			Double double1 = 0d;
			for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : aggregatoProcessoRoutingConsegne)
				{
				List<Destinazione> list2 = dataProcessoRoutingConsegna.getDestinazioni();
				Double double2 = dataProcessoRoutingConsegna.getProbabilita();
				if (MetodiVari.isViewIn(list2, elementoBaseQN))
					// consideriamo anche il caso in cui ci sono due destinazioni uguali
					double1 = double1 + double2;
				}
			list.add(double1);
			}
		return list;
		}
	
	/*
	 * p(j) = prob(j) se j è un nodo connesso al routing.
	 * Genera una colonna della matrice di conservazione del flusso.
	 */
	public List<Double> getPRListFromServizio(ProcessoServizio processoServizio)
		{
		List<Double> list = new ArrayList<Double>();
		AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti = 
			processoServizio.getAggregatoProcessoServizioSorgenti();
		for (ElementoBaseQN elementoBaseQN : this.list)
			{
			Double double1 = 0d;
			// processoServizio ha un unico DataProcessoServizioSorgente
			DataProcessoServizioSorgente dataProcessoServizioSorgente = aggregatoProcessoServizioSorgenti.get(0);
			AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni =
				dataProcessoServizioSorgente.getAggregatoProcessoServizioDestinazioni();
			for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : aggregatoProcessoServizioDestinazioni)
				{
				List<Destinazione> list2 = dataProcessoServizioDestinazione.getDestinazioni();
				Double double2 = dataProcessoServizioDestinazione.getProbabilita();
				if (MetodiVari.isViewIn(list2, elementoBaseQN))
					// consideriamo anche il caso in cui ci sono due destinazioni uguali
					double1 = double1 + double2;
				}
			list.add(double1);
			}
		return list;
		}

	public List<ElementoBaseQN> getList() 
		{
		return list;
		}
	}

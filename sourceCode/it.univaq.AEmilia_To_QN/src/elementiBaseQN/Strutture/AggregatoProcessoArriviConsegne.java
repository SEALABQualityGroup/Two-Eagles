package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class AggregatoProcessoArriviConsegne extends ArrayList<DataProcessoArriviConsegna>
	{

	private static final long serialVersionUID = 1L;

	public AggregatoProcessoArriviConsegne(int initialCapacity)
		{
		super();
		for (int i = 0; i < initialCapacity; i++)
			{
			DataProcessoArriviConsegna dataProcessoArriviConsegna =
				new DataProcessoArriviConsegna();
			dataProcessoArriviConsegna.setOrdineAzioneConsegna(i);
			this.add(dataProcessoArriviConsegna);
			}
		}

	public AggregatoProcessoArriviConsegne()
		{
		super();
		}

	// restituisce null se ordineAzioneConsegna è null oppure non viene trovato
	public String getNomeAzioneConsegnaFromOrdine(Integer ordineAzioneConsegna)
		{
		if (ordineAzioneConsegna == null) return null;
		String string = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			Integer integer = dataProcessoArrivi.getOrdineAzioneConsegna();
			if (ordineAzioneConsegna.equals(integer))
				string = dataProcessoArrivi.getNomeAzioneConsegna();
			}
		return string;
		}

	// restituisce null se ordineAzioneConsegna è null oppure non viene trovato
	public Double getProbRoutingFromOrdine(Integer ordineAzioneConsegna)
		{
		if (ordineAzioneConsegna == null) return null;
		Double double1 = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			Integer integer = dataProcessoArrivi.getOrdineAzioneConsegna();
			if (ordineAzioneConsegna.equals(integer))
				double1 = dataProcessoArrivi.getProbRouting();
			}
		return double1;
		}

	// restituisce null se ordineAzioneConsegna è null oppure non viene trovato
	public List<Destinazione> getDestinazioniFromOrdine(Integer ordineAzioneConsegna)
		{
		if (ordineAzioneConsegna == null) return null;
		List<Destinazione> list = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			Integer integer = dataProcessoArrivi.getOrdineAzioneConsegna();
			if (ordineAzioneConsegna.equals(integer))
				list = dataProcessoArrivi.getDestinazioni();
			}
		return list;
		}

	// restituisce null se nomeAzioneConsegna è null oppure non viene trovato
	public Integer getOrdineConsegnaFromName(String nomeAzioneConsegna)
		{
		if (nomeAzioneConsegna == null) return null;
		Integer integer = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			String string = dataProcessoArrivi.getNomeAzioneConsegna();
			if (nomeAzioneConsegna.equals(string))
				integer = dataProcessoArrivi.getOrdineAzioneConsegna();
			}
		return integer;
		}

	// restituisce null se nomeAzioneConsegna è null oppure non viene trovato
	public Double getProbRoutingFromDeliverName(String nomeAzioneConsegna)
		{
		if (nomeAzioneConsegna == null) return null;
		Double double1 = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			String string = dataProcessoArrivi.getNomeAzioneConsegna();
			if (nomeAzioneConsegna.equals(string))
				double1 = dataProcessoArrivi.getProbRouting();
			}
		return double1;
		}

	// restituisce null se nomeAzioneConsegna è null oppure non viene trovato
	public Destinazione getDestinazioneFromDeliverName(String nomeAzioneConsegna)
		{
		if (nomeAzioneConsegna == null) return null;
		Destinazione destinazione = null;
		for (DataProcessoArriviConsegna dataProcessoArrivi : this)
			{
			String string = dataProcessoArrivi.getNomeAzioneConsegna();
			if (nomeAzioneConsegna.equals(string))
				destinazione = dataProcessoArrivi.getDestinazione();
			}
		return destinazione;
		}

	public AggregatoProcessoArriviConsegne(List<String> list, List<Double> list2)
		throws ElementoBaseException
		{
		super();
		if (list.size() != list2.size())
			throw new ElementoBaseException("The delivery actions names list " +
					"and the routing probabilities list have not " +
				"same size");
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoArriviConsegna dataProcessoArriviConsegna =
				new DataProcessoArriviConsegna();
			dataProcessoArriviConsegna.setOrdineAzioneConsegna(i);
			dataProcessoArriviConsegna.setNomeAzioneConsegna(list.get(i));
			dataProcessoArriviConsegna.setProbRouting(list2.get(i));
			this.add(dataProcessoArriviConsegna);
			}
		}

	/**
	 * Restituisce la struttura per l'interazione di output relativa a string, dove
	 * string è il nome di un'azione di consegna.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaInterazioneOutputFromDeliver(String string)
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput =
			null;
		for (DataProcessoArriviConsegna dataProcessoArriviConsegna : this)
			{
			String string2 = dataProcessoArriviConsegna.getNomeAzioneConsegna();
			if (string.equals(string2))
				strutturaInterazioneOutput = dataProcessoArriviConsegna;
			}
		return strutturaInterazioneOutput;
		}

	/**
	 * Imposta la probabilità di routing di ogni connessione uguale a p / i, dove p è la probabilità
	 * di routing dell'azione di consegna e i è il numero di elementi base connessi all'azione di
	 * consegna.
	 * 
	 * @throws AEIintoElementiBaseException
	 */
	public void proporzionaProbabilita()
		throws ElementoBaseException
		{
		for (DataProcessoArriviConsegna dataProcessoArriviConsegna : this)
			{
			dataProcessoArriviConsegna.proporzionaProbabilita();
			}
		}
	
	/**
	 * Sostituisce destinazione con la lista list, ricalcolando
	 * le probabilità di routing per le connessioni.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list)
		{
		// si trova destinazione
		for (DataProcessoArriviConsegna dataProcessoArriviConsegna : this)
			{
			List<Destinazione> list2 = dataProcessoArriviConsegna.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si calcola la nuova probabilità di routing
					double d = dataProcessoArriviConsegna.getProbRouting();
					int i = list2.size();
					// si rimuove la destinazione vecchia
					list2.remove(destinazione2);
					// si aggiungono le nuovi destinazioni se non sono già presenti
					for (Destinazione destinazione3 : list)
						{
						if (!list2.contains(destinazione3))
							list2.add(destinazione3);
						}
					// si calcola la nuova probabilità di routing
					int j = list2.size();
					double e = (d * j) / i;
					dataProcessoArriviConsegna.setProbRouting(e);
					}
				}
			}
		}
	}
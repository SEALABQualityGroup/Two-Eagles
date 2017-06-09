package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class AggregatoProcessoJoinConsegne extends
		ArrayList<DataProcessoJoinConsegna> 
	{
	
	private static final long serialVersionUID = 1L;

	public AggregatoProcessoJoinConsegne() 
		{
		super();
		}

	public AggregatoProcessoJoinConsegne(int initialCapacity) 
		{
		super();
		for (int i = 0; i < initialCapacity; i++)
			{
			DataProcessoJoinConsegna dataProcessoJoinConsegna =
				new DataProcessoJoinConsegna();
			ProbRouting probRouting = new ProbRouting();
			dataProcessoJoinConsegna.setProbRouting(probRouting);
			this.add(dataProcessoJoinConsegna);
			}
		}

	public AggregatoProcessoJoinConsegne(List<String> list, List<Double> list2)
		throws ElementoBaseException
		{
		super();
		if (list == null) 
			throw new ElementoBaseException("The delivery actions names list is null");
		if (list2 == null) 
			throw new ElementoBaseException("The routing probabilities list is null");
		if (list.size() != list2.size())
			throw new ElementoBaseException("The input lists have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoJoinConsegna dataProcessoJoinConsegna =
				new DataProcessoJoinConsegna();
			ProbRouting probRouting = new ProbRouting(list2.get(i),list.get(i));
			dataProcessoJoinConsegna.setProbRouting(probRouting);
			this.add(dataProcessoJoinConsegna);
			}
		}
	
	/**
	 * Restituisce la probabilità di routing per l'azione di consegna di nome nomeAzioneConsegna.
	 * Restituisce null se nomeAzioneConsegna è null, oppure non viene trovato nella lista.
	 */
	public Double getProbFromNomeConsegna(String nomeAzioneConsegna)
		{
		if (nomeAzioneConsegna == null) return null;
		Double double1 = null;
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			ProbRouting probRouting = dataProcessoJoinConsegna.getProbRouting();
			String string = probRouting.getNomeAzioneConsegna();
			if (nomeAzioneConsegna.equals(string))
				double1 = probRouting.getProbabilita();
			}
		return double1;
		}
	
	/**
	 * Restituisce la prima destinazione attaccata all'azione di consegna nomeAzioneConsegna.
	 * Restituisce null se nomeAzioneConsegna è null, oppure non viene trovato nella lista.
	 */
	public Destinazione getDestinazioneFromNomeConsegna(String nomeAzioneConsegna)
		{
		if (nomeAzioneConsegna == null) return null;
		Destinazione destinazione = null;
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			ProbRouting probRouting = dataProcessoJoinConsegna.getProbRouting();
			String string = probRouting.getNomeAzioneConsegna();
			if (nomeAzioneConsegna.equals(string))
				destinazione = dataProcessoJoinConsegna.getDestinazione();
			}
		return destinazione;
		}
	
	/**
	 * Restituisce la struttura per l'interazione di output associata all'azione con nome
	 * string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaFromOutput(String string)
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput = null;
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			String string2 = dataProcessoJoinConsegna.getNomeAzioneConsegna();
			if (string.equals(string2))
				strutturaInterazioneOutput = dataProcessoJoinConsegna;
			}
		return strutturaInterazioneOutput;
		}
	
	/**
	 * Imposta la probabilità di routing per le azioni di consegna uguale a p / i dove p
	 * è la probabilità di routing associata ad un'azione di consegna e i è il numero di 
	 * elementi base connessi all'azione di consegna.
	 * 
	 * @throws AEIintoElementiBaseException
	 */
	public void proporzionaProbabilita()
		throws ElementoBaseException
		{
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			dataProcessoJoinConsegna.proporzionaProbabilita();
			}
		}
	
	/**
	 * Sostituisce destinazione con list, riproporzionando le probabilità di routing.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list)
		{
		// si cerca destinazione come destinazione
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			List<Destinazione> list2 = dataProcessoJoinConsegna.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si calcola la nuova probabilità di routing
					double d = dataProcessoJoinConsegna.getProbabilita();
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
					dataProcessoJoinConsegna.setProbabilita(e);
					}
				}
			}
		}

	// restituisce true se e solo se c'è un'alternativa in cui un job esce dalla rete
	public boolean verificaNoJobExit() 
		{
		boolean ris = true;
		if (this.size() == 0) return false;
		for (DataProcessoJoinConsegna dataProcessoJoinConsegna : this)
			{
			ris = ris && dataProcessoJoinConsegna.verificaNoJobExit();
			}
		return false;
		}
	}

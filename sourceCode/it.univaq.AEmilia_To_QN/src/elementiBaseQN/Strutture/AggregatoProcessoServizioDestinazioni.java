package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class AggregatoProcessoServizioDestinazioni extends
		ArrayList<DataProcessoServizioDestinazione> 
	{
	private static final long serialVersionUID = 1L;
	
	public AggregatoProcessoServizioDestinazioni(List<String> list,
			List<Double> list2)
		throws ElementoBaseException
		{
		super();
		if (list.size() != list2.size())
			throw new ElementoBaseException("The delivery actions list and "+
					"the routing probabilities list have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoServizioDestinazione dataProcessoServizioDestinazione =
				new DataProcessoServizioDestinazione();
			String string = list.get(i);
			Double double1 = list2.get(i);
			dataProcessoServizioDestinazione.setOrdineDestinazione(i);
			ProbRouting probRouting = new ProbRouting(double1,string);
			dataProcessoServizioDestinazione.setProbRouting(probRouting);
			this.add(dataProcessoServizioDestinazione);
			}
		}

	public AggregatoProcessoServizioDestinazioni() 
		{
		super();
		}

	/**
	 * Restituisce la lista di tutte le destinazione del processo di servizio.
	 * 
	 * @return
	 */
	public List<Destinazione> getDestinazioni()
		{
		List<Destinazione> list = new ArrayList<Destinazione>();
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			List<Destinazione> list2 = dataProcessoServizioDestinazione.getDestinazioni();
			list.addAll(list2);
			}
		return list;
		}
	
	/**
	 * Restituisce il numero di destinazioni, comprese le destinazioni null.
	 *  
	 * @return
	 */
	public int getNumeroDestinazioni()
		{
		return this.size();
		}
	
	/**
	 * 	Restituisce l'array delle probabilit� di routing presenti nell'oggetto ProbRouting 
	 * contenuto in ogni elemento DataProcessoServizioDestinazione. 
	 * Tale ProbRouting deve essere diverso da null.
	 * Se non ci sono destinazioni, si restituisce un array vuoto.
	 * 
	 * @return
	 */
	public List<Double> getProbsRouting()
		{
		List<Double> list = new ArrayList<Double>();
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			ProbRouting probRouting = dataProcessoServizioDestinazione.getProbRouting();
			if (probRouting != null)
				{
				Double double1 = probRouting.getProbabilita();
				list.add(double1);
				}
			}
		return list;
		}
	
	/**
	 * Restituisce la struttura dell'interazione di output relativa a string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaFromOutput(String string)
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput = null;
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			String string2 = dataProcessoServizioDestinazione.getNomeAzioneConsegna();
			if (string.equals(string2))
				strutturaInterazioneOutput = dataProcessoServizioDestinazione;
			}
		return strutturaInterazioneOutput;
		}
	
	/**
	 * Imposta la probabilit� di routing della struttura di output relativa ad ogni azione di
	 * consegna nel seguente modo: se i � il numero degli elementi base connessi all'azione di
	 * consegna e p � la probabilit� di consegna associata all'azione, allora la nuova probabilit�
	 * di routing � p / i.
	 * 
	 * @throws ElementoBaseException
	 */
	public void proporzionaProbabilita()
	 	throws ElementoBaseException
	 	{
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			dataProcessoServizioDestinazione.proporzionaProbabilita();
			}
	 	}

	/**
	 * Si sostituisce destinazione con list, riproporzionando
	 * le probabilit� di routing.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		// si cerca la destinazione elementoBaseQN
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			List<Destinazione> list2 = dataProcessoServizioDestinazione.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si calcola la nuova probabilit� di routing
					double d = dataProcessoServizioDestinazione.getProbabilita();
					int i = list2.size();
					// si rimuove la destinazione vecchia
					list2.remove(destinazione2);
					// si aggiungono le nuovi destinazioni se non sono gi� presenti
					for (Destinazione destinazione3 : list)
						{
						if (!list2.contains(destinazione3))
							list2.add(destinazione3);
						}
					// si calcola la nuova probabilit� di routing
					int j = list2.size();
					double e = (d * j) / i;
					dataProcessoServizioDestinazione.setProbabilita(e);
					}
				}
			}
		}

	public boolean verificaNoJobExit() 
		{
		boolean ris = true;
		for (DataProcessoServizioDestinazione dataProcessoServizioDestinazione : this)
			{
			ris = ris && dataProcessoServizioDestinazione.verificaNoJobExit();
			}
		return ris;
		}
	}
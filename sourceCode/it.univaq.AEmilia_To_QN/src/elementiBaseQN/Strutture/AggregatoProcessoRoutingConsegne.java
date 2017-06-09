/**
 * 
 */
package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

/**
 * @author Mirko
 *
 */
public class AggregatoProcessoRoutingConsegne extends
		ArrayList<DataProcessoRoutingConsegna> 
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AggregatoProcessoRoutingConsegne(List<String> list, List<Double> list2)
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
			DataProcessoRoutingConsegna dataProcessoRoutingConsegna =
				new DataProcessoRoutingConsegna();
			// list.get(i) può essere null
			ProbRouting probRouting = new ProbRouting(list2.get(i),list.get(i));
			dataProcessoRoutingConsegna.setProbRouting(probRouting);
			this.add(dataProcessoRoutingConsegna);
			}
		}

	public AggregatoProcessoRoutingConsegne() 
		{
		super();
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
		for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : this)
			{
			String string2 = dataProcessoRoutingConsegna.getNomeAzioneConsegna();
			if (string.equals(string2))
				strutturaInterazioneOutput = dataProcessoRoutingConsegna;
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
		for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : this)
			{
			dataProcessoRoutingConsegna.proporzionaProbabilita();
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
		// si cerca destinazione
		for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : this)
			{
			List<Destinazione> list2 = dataProcessoRoutingConsegna.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si calcola la nuova probabilità di routing
					double d = dataProcessoRoutingConsegna.getProbabilita();
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
					dataProcessoRoutingConsegna.setProbabilita(e);
					}
				}
			}
		}
	
	public List<Destinazione> getDestinazioni() 
		{
		List<Destinazione> list = new ArrayList<Destinazione>();
		for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : this)
			{
			List<Destinazione> list2 = dataProcessoRoutingConsegna.getDestinazioni();
			list.addAll(list2);
			}
		return list;
		}

	public boolean verificaNoJobExit() 
		{
		boolean ris = true;
		if (this.size() == 0) return false;
		for (DataProcessoRoutingConsegna dataProcessoRoutingConsegna : this)
			{
			ris = ris && dataProcessoRoutingConsegna.verificaNoJobExit();
			}
		return ris;
		}
	}

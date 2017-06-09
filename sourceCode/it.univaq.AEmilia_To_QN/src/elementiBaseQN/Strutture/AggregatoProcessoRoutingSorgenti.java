package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

public class AggregatoProcessoRoutingSorgenti extends
		ArrayList<DataProcessoRoutingSorgente> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AggregatoProcessoRoutingSorgenti(List<String> list)
		throws ElementoBaseException
		{
		super();
		if (list == null) throw new ElementoBaseException("The input list is null");
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoRoutingSorgente dataProcessoRoutingSorgente =
				new DataProcessoRoutingSorgente();
			dataProcessoRoutingSorgente.setNomeAzioneSorgente(list.get(i));
			this.add(dataProcessoRoutingSorgente);
			}
		}

	public AggregatoProcessoRoutingSorgenti() 
		{
		super();
		}

	/**
	 * Restituisce la struttura per l'azione di input di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaFromInput(String string)
		{
		StrutturaInterazioneInput strutturaInterazioneInput = null;
		for (DataProcessoRoutingSorgente dataProcessoRoutingSorgente : this)
			{
			String string2 = dataProcessoRoutingSorgente.getNomeAzioneSorgente();
			if (string.equals(string2))
				strutturaInterazioneInput = dataProcessoRoutingSorgente;
			}
		return strutturaInterazioneInput;
		}
	
	/**
	 * Sostituisce elementoBaseQN con list. Se list contiene un elemento base sorgente,
	 * non viene aggiunto alla lista delle sorgenti. 
	 * 
	 * @param elementoBaseQN
	 * @param list
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list)
		{
		// si cerca elementoBaseQN come sorgente
		for (DataProcessoRoutingSorgente dataProcessoRoutingSorgente : this)
			{
			List<ElementoBaseQN> list2 = dataProcessoRoutingSorgente.getSorgenti();
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
	
	/**
	 * Restituisce gli elementi base connessi come sorgenti di job al processo di routing.
	 * 
	 */
	public List<ElementoBaseQN> getSorgenti()
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		for (DataProcessoRoutingSorgente dataProcessoRoutingSorgente : this)
			{
			List<ElementoBaseQN> list2 = dataProcessoRoutingSorgente.getSorgenti();
			list.addAll(list2);
			}
		return list;
		}
	}

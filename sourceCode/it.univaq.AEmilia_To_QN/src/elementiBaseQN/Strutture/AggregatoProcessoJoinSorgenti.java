package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

import elementiBaseQN.Buffer;
import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;

public class AggregatoProcessoJoinSorgenti extends
		ArrayList<DataProcessoJoinSorgente> 
	{

	private static final long serialVersionUID = 1L;
	
	public AggregatoProcessoJoinSorgenti(List<String> list)
		throws ElementoBaseException
		{
		super();
		if (list == null) throw new ElementoBaseException("The input list is null");
		for (int i = 0; i < list.size(); i++)
			{
			DataProcessoJoinSorgente dataProcessoJoinSorgente =
				new DataProcessoJoinSorgente();
			dataProcessoJoinSorgente.setNomeAzioneSorgente(list.get(i));
			this.add(dataProcessoJoinSorgente);
			}
		}
	
	public AggregatoProcessoJoinSorgenti() 
		{
		super();
		}



	/**
	 * Restituisce il primo elemento base connesso all'azione nomeAzioneSorgente.
	 * Se nomeAzioneSorgente è null oppure non viene trovato all'interno della lista,
	 * si restituisce null.
	 */
	public ElementoBaseQN getSorgenteFromNomeAzioneSorgente(String nomeAzioneSorgente)
		{
		if (nomeAzioneSorgente == null) return null;
		ElementoBaseQN elementoBaseQN = null;
		for (DataProcessoJoinSorgente dataProcessoJoinSorgente : this)
			{
			String string = dataProcessoJoinSorgente.getNomeAzioneSorgente();
			if (nomeAzioneSorgente.equals(string))
				elementoBaseQN = dataProcessoJoinSorgente.getSorgente();
			}
		return elementoBaseQN;
		}
	
	/**
	 * Restituisce gli elementi base connessi come sorgenti di job al processo join.
	 * 
	 */
	public List<ElementoBaseQN> getSorgenti()
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		for (DataProcessoJoinSorgente dataProcessoJoinSorgente : this)
			{
			List<ElementoBaseQN> list2 = dataProcessoJoinSorgente.getSorgenti();
			list.addAll(list2);
			}
		return list;
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
		for (DataProcessoJoinSorgente dataProcessoJoinSorgente : this)
			{
			String string2 = dataProcessoJoinSorgente.getNomeAzioneSorgente();
			if (string.equals(string2))
				strutturaInterazioneInput = dataProcessoJoinSorgente;
			}
		return strutturaInterazioneInput;
		}
	
	/**
	 * Restituisce gli elementi base sorgenti connessi al processo join come buffers.
	 * 
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	public List<Buffer<?>> getBuffers()
		throws ElementoBaseException
		{
		List<ElementoBaseQN> list = getSorgenti();
		List<Buffer<?>> list2 = new ArrayList<Buffer<?>>(list.size());
		int i = 0;
		for (ElementoBaseQN elementoBaseQN : list)
			{
			if (!(elementoBaseQN instanceof Buffer<?>))
				throw new ElementoBaseException("The basic element "+
					elementoBaseQN.getNome()+" is not a buffer");
			Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
			list2.add(buffer);
			i++;
			}
		return list2;
		}
	
	/**
	 * Metodo utilizzato per il testing. Restituisce gli elementi base sorgente come buffer,
	 * impostando il nome del buffer al nome dell'elemento base sorgente.
	 * 
	 * @return
	 * @throws AEIintoElementiBaseException
	 */
	public List<Buffer<?>> getSorgentiAsBuffers()
		throws ElementoBaseException
		{
		List<ElementoBaseQN> list = getSorgenti();
		List<Buffer<?>> list2 = new ArrayList<Buffer<?>>(list.size());
		int i = 0;
		for (ElementoBaseQN elementoBaseQN : list)
			{
			Buffer<DataBuffer> buffer = new Buffer<DataBuffer>();
			buffer.setNome(elementoBaseQN.getNome());
			list2.add(buffer);
			i++;
			}
		return list2;
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
		for (DataProcessoJoinSorgente dataProcessoJoinSorgente : this)
			{
			List<ElementoBaseQN> list2 = dataProcessoJoinSorgente.getSorgenti();
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

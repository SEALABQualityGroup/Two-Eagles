package elementiBaseQN.Strutture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;

public class AggregatoBuffer<E extends DataBuffer> extends ArrayList<E> 
	{
	
	private static final long serialVersionUID = 1L;

	public AggregatoBuffer() 
		{
		super();
		}

	// restituisce null se getAction è null oppure non viene trovata
	public String getPutFromGet(String getAction)
		{
		if (getAction == null) return null;
		String string = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getGetAction();
			if (getAction.equals(string2))
				string = dataBuffer.getPutAction();
			}
		return string;
		}
	
	// restituisce null se getAction è null oppure non viene trovata
	public ElementoBaseQN getSorgenteFromGet(String getAction)
		{
		if (getAction == null) return null;
		ElementoBaseQN elementoBaseQN = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getGetAction();
			if (getAction.equals(string2))
				elementoBaseQN = dataBuffer.getSorgente();
			}
		return elementoBaseQN;
		}
	
	// restituisce null se getAction è null oppure non viene trovata
	public Destinazione getDestinazioneFromGet(String getAction)
		{
		if (getAction == null) return null;
		Destinazione destinazione = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getGetAction();
			if (getAction.equals(string2))
				destinazione = dataBuffer.getDestinazione();
			}
		return destinazione;
		}
	
	// restituisce null se getAction è null oppure non viene trovata
	public String getClasseFromGet(String getAction)
		{
		if (getAction == null) return null;
		String string = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getGetAction();
			if (getAction.equals(string2))
				string = dataBuffer.getClasse();
			}
		return string;
		}
	
	// restituisce null se putAction è null oppure non viene trovata
	public String getGetFromPut(String putAction)
		{
		if (putAction == null) return null;
		String string = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getPutAction();
			if (putAction.equals(string2))
				string = dataBuffer.getGetAction();
			}
		return string;
		}
	
	// restituisce null se putAction è null oppure non viene trovata
	public ElementoBaseQN getSorgenteFromPut(String putAction)
		{
		if (putAction == null) return null;
		ElementoBaseQN elementoBaseQN = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getPutAction();
			if (putAction.equals(string2))
				elementoBaseQN = dataBuffer.getSorgente();
			}
		return elementoBaseQN;
		}
	
	// restituisce null se putAction è null oppure non viene trovata
	public Destinazione getDestinazioneFromPut(String putAction)
		{
		if (putAction == null) return null;
		Destinazione destinazione = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getPutAction();
			if (putAction.equals(string2))
				destinazione = dataBuffer.getDestinazione();
			}
		return destinazione;
		}
	
	// restituisce null se putAction è null oppure non viene trovata
	public String getClasseFromPut(String putAction)
		{
		if (putAction == null) return null;
		String string = null;
		for (DataBuffer dataBuffer : this)
			{
			String string2 = dataBuffer.getPutAction();
			if (putAction.equals(string2))
				string = dataBuffer.getClasse();
			}
		return string;
		}
	
	/**
	 * Restituisce le destinazioni dei job che attengono in questo buffer.
	 * 
	 * @return
	 */
	public List<Destinazione> getDestinazioni()
		{
		List<Destinazione> list = new ArrayList<Destinazione>();
		for (DataBuffer dataBuffer : this)
			{
			Destinazione destinazione = dataBuffer.getDestinazione();
			list.add(destinazione);
			}
		return list;
		}
	
	/**
	 * Restituisce una lista di nomi di classi di jobs che questo buffer può ospitare.
	 * 
	 * @return
	 */
	public List<String> getClassi()
		{
		List<String> list = new ArrayList<String>();
		for (DataBuffer dataBuffer : this)
			{
			String string = dataBuffer.getClasse();
			list.add(string);
			}
		return list;
		}
	
	/**
	 * Restituisce una lista di elementi base che sono le sorgenti di job.
	 * 
	 * @return
	 */
	public List<ElementoBaseQN> getSorgenti()
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		for (StrutturaInterazioneInput dataBuffer : this)
			{
			ElementoBaseQN elementoBaseQN = dataBuffer.getSorgente();
			list.add(elementoBaseQN);
			}
		return list;
		}
	
	/**
	 * Restituisce la struttura per l'interazione di input associata ad un'azione di get.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaInterazioneInputFromGet(String string)
		{
		StrutturaInterazioneInput strutturaInterazioneInput =
			null;
		for (E e : this)
			{
			String string2 = e.getGetAction();
			if (string.equals(string2))
				strutturaInterazioneInput = e;
			}
		return strutturaInterazioneInput;
		}
	
	/**
	 * Restituisce la struttura per l'interazione di output associata ad un'azione di put.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaInterazioneOutputFromPut(String string)
		{
		StrutturaInterazioneOutput strutturaInterazioneOutput =
			null;
		for (E e : this)
			{
			String string2 = e.getPutAction();
			if (string.equals(string2))
				strutturaInterazioneOutput = e;
			}
		return strutturaInterazioneOutput;
		}
	
	/**
	 * Si sostituisce destinazione con una lista di destinazioni.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list)
		{
		// si cerca destinazione
		for (E e : this)
			{
			List<Destinazione> list2 =
				e.getDestinazioni();
			Destinazione[] destinaziones = new Destinazione[list2.size()];
			list2.toArray(destinaziones);
			CopyOnWriteArrayList<Destinazione> copyOnWriteArrayList =
				new CopyOnWriteArrayList<Destinazione>(destinaziones);
			for (Destinazione destinazione2 : copyOnWriteArrayList)
				{
				if (destinazione.equals(destinazione2))
					{
					// si rimuove il vecchio elemento
					list2.remove(destinazione2);
					// si aggiungono le nuove destinazioni se non sono già presenti
					for (Destinazione destinazione3 : list)
						{
						if (!list2.contains(destinazione3))
							list2.add(destinazione3);
						}
					}
				}
			}
		}
	
	/**
	 * Si sostituisce elementoBaseQN con una lista di elementi base.
	 * 
	 * @param elementoBaseQN
	 * @param list
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list)
		{
		// si cerca elementoBase come origine
		for (StrutturaInterazioneInput e : this)
			{
			List<ElementoBaseQN> list2 =
				e.getSorgenti();
			ElementoBaseQN[] elementoBaseQNs = new ElementoBaseQN[list2.size()];
			list2.toArray(elementoBaseQNs);
			CopyOnWriteArrayList<ElementoBaseQN> copyOnWriteArrayList = 
				new CopyOnWriteArrayList<ElementoBaseQN>(elementoBaseQNs);
			for (ElementoBaseQN elementoBaseQN2 : copyOnWriteArrayList)
				{
				if (elementoBaseQN.equals(elementoBaseQN2))
					{
					// si rimuove il vecchio elemento
					list2.remove(elementoBaseQN2);
					// si aggiungono le nuove origini se non sono già presenti
					for (ElementoBaseQN elementoBaseQN3 : list)
						{
						if (!list2.contains(elementoBaseQN3))
							list2.add(elementoBaseQN3);
						}
					}
				}
			}
		}

	public void proporzionaProbabilita() 
		{
		for (E e : this)
			{
			e.proporzionaProbabilita();
			}
		}
	}
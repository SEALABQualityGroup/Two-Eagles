package mappingAEIintoElementiBase.struttura;

import java.util.List;

import elementiBaseQN.Destinazione;

/**
 * Rappresenta la struttura per la gestione di un
 * interazione di output di un elemento base
 * 
 * @author Mirko
 *
 */
public interface StrutturaInterazioneOutput 
	{
	
	/**
	 * Aggiunge destinazione se non è già presente.
	 * 
	 * @param destinazione
	 */
	public void addDestinazione(Destinazione destinazione);

	/**
	 * Restituisce la prima destinazione attaccata.
	 * 
	 * @return
	 */
	public Destinazione getDestinazione();

	/**
	 * Restituisce la lista delle destinazioni connesse
	 * all'interazione di output.
	 * 
	 * @return
	 */
	public List<Destinazione> getDestinazioni();

	/**
	 * Imposta un'unica destinazione connessa all'interazione di output
	 * 
	 * @param destinazione
	 */
	public void setDestinazione(Destinazione destinazione);

	/**
	 * Imposta come destinazioni connesse all'interazione di output 
	 * gli oggetti Destinazione contenuti in destinazioni.
	 * 
	 * @param destinazioni
	 */
	public void setDestinazioni(List<Destinazione> destinazioni);
	
	}

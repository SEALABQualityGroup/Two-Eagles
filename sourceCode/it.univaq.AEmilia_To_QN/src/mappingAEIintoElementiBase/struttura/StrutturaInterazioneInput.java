package mappingAEIintoElementiBase.struttura;

import java.util.List;

import elementiBaseQN.ElementoBaseQN;

/**
 * Restituisce la struttura per la gestione di un
 * interazione di input di un elemento base
 * 
 * @author Mirko
 *
 */
public interface StrutturaInterazioneInput 
	{
	
	/**
	 * Aggiunge la sorgente elementoBaseQN, se tale elemento base non è già presente come
	 * sorgente.
	 * 
	 */
	public void addSorgente(ElementoBaseQN elementoBaseQN);

	/**
	 * Restituisce la prima sorgente connessa all'azione di input dell'elemento base.
	 * 
	 * @return
	 */
	public ElementoBaseQN getSorgente();

	/**
	 * Restituisce una lista di elementi base attaccati all'interazione di input.
	 * 
	 * @return
	 */
	public List<ElementoBaseQN> getSorgenti();

	/**
	 * Assegna come unica sorgente di job connessa
	 * all'interazione di input l'elemento
	 * sorgente.
	 * 
	 * @param sorgente
	 */
	public void setSorgente(ElementoBaseQN sorgente);

	/**
	 * Assegna come sorgenti all'interazione di input gli elementi
	 * base della lista sorgenti.
	 * 
	 * @param sorgenti
	 */
	public void setSorgenti(List<ElementoBaseQN> sorgenti);

	public Integer getOrdineClasse();
	
	}

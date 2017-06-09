package mappingAEIintoElementiBase.struttura;

import java.util.List;

import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;

/**
 * Rappresenta l'interfaccia che gli elementi base devono
 * implementare secondo specifica.
 * 
 * @author Mirko
 *
 */
public interface StrutturaInterazione 
	{
	
	/**
	 * Restituisce la struttura per un'interazione di input associata al nome di un'azione di input.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneInput getStrutturaInput(String string);
	
	/**
	 * Restituisce la struttura per un'interazione di output associata al nome di un'azione
	 * di output.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaOutput(String string);
	
	/**
	 * Proporziona la probabilità di routing per
	 * un'azione di consegna di jobs a seconda
	 * del numero di elementi base connessi all'azione
	 * di destinazione.
	 * @throws ElementoBaseException 
	 */
	public void proporzionaProbabilita() throws ElementoBaseException;
	
	/**
	 * Associa i buffers associati ad un generico elemento
	 * base che ne prevede. Il buffer viene impostato a seconda della prima sorgente 
	 * dell'elemento base connesso al buffer.
	 * 
	 */
	public void setBuffers()
		throws ElementoBaseException;

	/**
	 * Sostituisce elementoBaseQN con ogni elemento base contenuto in list.
	 * 
	 */
	public void replaceSource(ElementoBaseQN elementoBaseQN, List<ElementoBaseQN> list);

	/**
	 * Sostituisce destinazione con ogni elemento base contenuto in list.
	 * 
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione, List<Destinazione> list);
	}
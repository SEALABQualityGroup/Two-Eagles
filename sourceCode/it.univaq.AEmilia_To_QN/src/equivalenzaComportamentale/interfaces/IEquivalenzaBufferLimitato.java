package equivalenzaComportamentale.interfaces;

import java.util.List;

import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ConditionalGetBehavior;
import specificheAEmilia.Expression;

/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un buffer con
 * capacità finita.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un buffer a capacita limitata.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaBufferLimitato 
	extends IEquivalenzaBuffer 
	{

	/**
	 * Restituisce un array di espressioni che indicano il numero
	 * massimo di clienti di una certa classe che possono
	 * risiedere nel buffer, o null, se il tipo di elemento architetturale non è equivalente ad un buffer
	 * con capacità limitata. Restituisce un array non vuoto.
	 *
	 * @return
	 */
	public Expression[] getLimitiClassi();
	
	/**
	 * Restituisce un'espressione indicante il numero massimo di clienti della classe la cui
	 * interazione di input associata è string, o null, se il tipo di elemento architetturale non è equivalente ad un buffer
	 * con capacità limitata.
	 *  
	 * @param string
	 * @return
	 */
	public Expression getLimiteClasse(String string);
	
	public List<ConditionalGetBehavior> getConditionalGetBehaviors(); 

	}
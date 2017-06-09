package equivalenzaComportamentale.interfaces;


/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un processo fork con buffer per
 * i clienti di una certa classe.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un processo fork.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaForkConBuffer 
	extends IEquivalenzaFork 
	{

	/**
	 * Restituisce il nome dell'azione di arrivo o select di un cliente dal buffer, o null, se il tipo di elemento
	 * architetturale non è equivalente ad un processo fork con buffer.
	 *
	 * @return
	 */
	public abstract String getSelect();

	}

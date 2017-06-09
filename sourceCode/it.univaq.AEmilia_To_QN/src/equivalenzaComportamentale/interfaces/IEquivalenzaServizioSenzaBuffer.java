package equivalenzaComportamentale.interfaces;

/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un processo di servizio
 * senza buffer.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un processo di servizio senza buffer.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaServizioSenzaBuffer 
	extends IEquivalenzaServizio 
	{

	/**
	 * Restituisce i nomi delle azioni di o arrivo di un cliente
	 * per il suo servizio, o null, se il tipo di elemento architetturale non è processo
	 * di servizio.
	 *
	 * @return
	 */
	public abstract String[] getArrivesNames();

	}

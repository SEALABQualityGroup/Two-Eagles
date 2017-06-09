package equivalenzaComportamentale.interfaces;


import specificheAEmilia.Expression;

/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un processo di servizio
 * con buffer.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un processo di servizio con buffer.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaServizioConBuffer 
	extends IEquivalenzaServizio 
	{

	/**
	 * Restituisce un array di priorità  di selezione di un cliente
	 * tra un insieme di classi, o null, se il tipo di elemento architetturale non è processo di servizio
	 * con buffer. L'array restituito è non vuoto.
	 *
	 * @return
	 */
	public Expression[] getPrioSelezione();

	/**
	 * Restituisce un array di probabilità  di selezione di un cliente
	 * tra un insieme di classi, o null, se il tipo di elemento architetturale non è processo di servizio
	 * con buffer.
	 *
	 * @return
	 */
	public Expression[] getProbSelezione();
	
	/**
	 * Restituisce i nomi delle azioni di selezione di un cliente
	 * per il suo servizio, o null, se il tipo di elemento architetturale non è processo
	 * di servizio con buffer.
	 *
	 * @return
	 */
	public abstract String[] getSelectionsNames();

}

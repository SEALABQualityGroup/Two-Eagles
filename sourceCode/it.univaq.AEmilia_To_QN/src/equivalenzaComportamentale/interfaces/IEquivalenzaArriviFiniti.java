package equivalenzaComportamentale.interfaces;

/**
 * Questa interfaccia definisce i metodi da implementare
 * per determinare se un tipo di elemento architetturale
 * ha un camportamento equivalente ad un processo di arrivo
 * per una popolazione di clienti finita.
 * Contiene anche metodi che restituiscono le informazioni
 * che caratterizzano un processo di arrivi limitati.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaArriviFiniti extends IEquivalenzaArrivi
	{

	/**
	 * Restituisce un'array composto dai nomi dei tipi azione per
	 * il ritorno di un cliente, o null, se il tipo di elemento architetturale non è
	 * un processo di arrivi di clienti finito.
	 *
	 * @return
	 */
	public String[] getReturns();

	/**
	 * Restituisce il numero di ritorni di un cliente, dopo aver
	 * subito servizio, o 0, se il tipo di elemento architetturale non è un processo di arrivi.
	 *
	 * @return
	 */
	public int getNumReturns();
}

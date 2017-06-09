package mappingAEIintoElementiBase.struttura;

/**
 * Viene utilizzata per indicare una disciplina di scheduling.
 * 
 * @author Mirko
 *
 */
public enum Disciplina 
	{
	FCFS, 
	NP; // indica una politica di scheduling senza prelazione, in cui 
	// il centro di servizio estrae dalla coda il cliente con priorità
	// maggiore. I clienti con uguale priorità vengono serviti con una politica FIFO.
	}

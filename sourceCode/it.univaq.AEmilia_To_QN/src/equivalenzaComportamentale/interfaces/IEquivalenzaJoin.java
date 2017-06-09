package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Action;
import specificheAEmilia.Expression;

/**
 * Espone i metodi da implementare per verificare se un tipo di elemento architetturale è
 * equivalente ad un processo join.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 */

public interface IEquivalenzaJoin extends IEquivalenza {

	/**
	 * Restituisce un'array di probabilità di routing di
	 * ogni cliente tra centri di servizio come destinazioni, o null,
	 * se il tipo di elemento architetturale non è
	 * equivalente ad un processo join.
	 *
	 * @return
	 */
	public abstract Expression[] getProbRouting();

	/**
	 * Restituisce l'array di espressioni per le priorità 
	 * delle azioni di consegna di clienti a centri
	 * di servizio, o null, se il tipo di elemento architetturale non è
	 * equivalente ad un processo join.
	 *
	 * @return
	 */
	public abstract Expression[] getPrioConsegna();

	/**
	 * Restituisce l'array di espressioni per i pesi
	 * delle azioni di consegna di clienti a centri di
	 * servizio, o null, se il tipo di elemento architetturale non è
	 * equivalente ad un processo join.
	 *
	 * @return
	 */
	public abstract Expression[] getPesiConsegna();

	/**
	 * Restituisce il nome dell'azione, che fonde sottorischieste
	 * di clienti di una certa classe, o null, se il tipo di elemento
	 * architetturale non è equivalente ad un processo join.
	 *
	 * @return
	 */
	public abstract String getJoin();

	/**
	 * Restituisce un array di stringhe contenente i nomi delle
	 * azioni di consegna di un cliente, o null,
	 * se il tipo di elemento architetturale non è equivalente ad un
	 * processo join. L'array restituito può contenere null se per quell'alternativa
	 * il cliente esce dalla rete di code.
	 *
	 * @return
	 */
	public abstract String[] getDelivers();

	/**
	 * Restituisce le azioni di consegna.
	 *
	 * @return
	 */
	public abstract Action[] getDeliverActions();
	
	/**
	 * Restituisce un array di stringhe contenente i nomi delle azioni di scelta di un cliente, o null,
	 * se il tipo di elemento architetturale non è equivalente ad un processo join. L'array restituito
	 * è null se ogni cliente esce dalla rete di code.
	 * 
	 * @return
	 */
	public abstract String[] getChooses();
	
	/**
	 * Restituisce true se e solo se il tipo di elemento
	 * architetturale è equivalente ad un processo
	 * join.
	 * 
	 * @return
	 */
	public abstract boolean isJoin();
	}
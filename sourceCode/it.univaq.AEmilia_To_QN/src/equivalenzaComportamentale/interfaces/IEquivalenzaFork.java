package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Action;
import specificheAEmilia.Expression;

/**
 * Espone i metodi da implementare per verificare se un tipo
 * di elemento architetturale è equivalente ad un processo
 * fork.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 *
 */
public interface IEquivalenzaFork extends IEquivalenza {

	/**
	 * Restituisce il numero di splitting di ogni richiesta, o 0, se il tipo di elemento architetturale non è
	 * un processo fork per una classe di clienti.
	 *
	 * @return
	 */
	public abstract int getNumeroForks();

	/**
	 * Restituisce l'array di espressioni per le priorità 
	 * delle azioni di fork, o null, se il tipo di elemento architetturale non è
	 * un processo fork per una classe di clienti.
	 *
	 * @return
	 */
	public abstract Expression[] getPrioConsegna();

	/**
	 * Restituisce l'array di espressioni per i pesi
	 * delle azioni di fork, o null, se il tipo di elemento architetturale non è
	 * un processo fork per una classe di clienti.
	 *
	 * @return
	 */
	public abstract Expression[] getPesiConsegna();

	/**
	 * Restituisce i nomi delle azioni di forks, per la suddivisione di una richiesta in sottorichieste, o null, se
	 * il tipo di elemento architetturale non è equivalente ad un processo fork per una classe di
	 * clienti.
	 *
	 * @return
	 */
	public abstract String[] getForks();

	/**
	 * Restituisce le azioni di consegna.
	 *
	 * @return
	 */
	public abstract Action[] getDeliverActions();
}
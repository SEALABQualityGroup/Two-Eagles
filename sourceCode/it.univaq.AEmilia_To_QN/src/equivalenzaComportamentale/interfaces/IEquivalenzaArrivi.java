package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Action;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;

/**
 * Interfaccia utilizzata per esporre i metodi di un processo di arrivi ad una rete di code.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 *
 */
public interface IEquivalenzaArrivi 
	extends IEquivalenza 
	{

	/**
	 * Restituisce un array di espressioni per il tempo di interarrivo dei clienti, o null, 
	 * se il tipo di elemento architetturale non è un processo di arrivi di clienti. 
	 * Restituisce un array ordinato secondo come appaiono i tassi 
	 * esponenziali in una visita in profondità del comportamento di fase. L'array restituito ha 
	 * almeno un elemento.
	 *
	 * @return
	 */
	public abstract Expression[] getTassiProcesso();

	/**
	 * Restituisce un'array di probabilità  di routing di
	 * ogni cliente tra centri di servizio come destinazioni, o null, 
	 * se il tipo di elemento architetturale non è
	 * un processo di arrivi di clienti. L'array restituito ha almeno un elemento.
	 *
	 * @return
	 */
	public abstract Expression[] getProbRouting();

	/**
	 * Restituisce l'array di espressioni per le priorità 
	 * delle azioni di consegna di clienti a centri
	 * di servizio, o null, se il tipo di elemento architetturale non è
	 * un processo di arrivi di clienti.
	 *
	 * @return
	 */
	public abstract Expression[] getPrioConsegna();

	/**
	 * Restituisce l'array di espressioni per i pesi
	 * delle azioni di consegna di clienti a centri di
	 * servizio, o null, se il tipo di elemento architetturale non è
	 * un processo di arrivi di clienti.
	 *
	 * @return
	 */
	public abstract Expression[] getPesiConsegna();

	/**
	 * Restituisce un'array composto dai nomi dei tipi azione per
	 * la consegna di un cliente, o null, se il tipo di elemento architetturale non è
	 * un processo di arrivi di clienti.
	 *
	 * @return
	 */
	public abstract String[] getDelivers();

	/**
	 * Restituisce le azioni di consegna. Se il tipo di elemento architetturale
	 * non è un processo di arrivo restituisce null.
	 *
	 * @return
	 */
	public abstract Action[] getDeliverActions();
	
	/**
	 * Restituisce un'array composto dai nomi dei tipi azione per la scelta di una consegna di
	 * un cliente, o null, se il tipo di elemento architetturale non è un processo di arrivi
	 * di clienti.
	 * 
	 * @return
	 */
	public abstract String[] getChooses();
	
	/**
	 * Restituisce il termine di processo che definisce il comportamento di fase
	 * del processo degli arrivi. Restituisce null se il tipo di elemento architetturale
	 * non è equivalente ad un processo di arrivi.
	 * 
	 * @return
	 */
	public abstract BehavEquation getPhaseBehavior();
	
	}
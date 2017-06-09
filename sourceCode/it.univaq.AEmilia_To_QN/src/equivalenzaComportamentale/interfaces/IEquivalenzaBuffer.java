package equivalenzaComportamentale.interfaces;

import java.util.List;

import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PutBehavior;
import specificheAEmilia.Expression;
import specificheAEmilia.Header;
import specificheAEmilia.VarInit;

/**
 * Espone i metodi da implementare per verificare se un tipo di elemento architetturale è un buffer.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 *
 */
public interface IEquivalenzaBuffer extends IEquivalenza {

	/**
	 * Restituisce il numero delle classi cliente, che
	 * possono risiedere nel buffer,
	 * o 0 se il tipo di elemento architetturale non è
	 * equivalente ad un buffer.
	 *
	 * @return
	 */
	public abstract int getNumeroClassiCliente();

	/**
	 * Restituisce le espressioni di inizializzazioni
	 * delle variabili inizializzate, che contengono il numero
	 * dei clienti di ogni classe, che si trovano nel buffer
	 * all'inizio dell'esecuzione, o null, se il tipo di elemento
	 * architetturale non è equivalente ad un buffer.
	 *
	 * @return
	 */
	public abstract Expression[] getCapacitaIniziali();

	/**
	 * Restituisce un'array composto dai nomi dei tipi azione per
	 * la memorizzazione di un cliente nel buffer, o null, se il tipo di elemento architetturale
	 * non è equivalente ad un buffer.
	 *
	 * @return
	 */
	public abstract String[] getGets();

	/**
	 * Restituisce un'array composto dai nomi dei tipi azione per
	 * la prelazione di un cliente dal buffer, o null,
	 * se il tipo di elemento architetturale non è equivalente ad un
	 * buffer.
	 *
	 * @return
	 */
	public abstract String[] getPuts();

	/**
	 * Restituisce il nome dell'azione di put che ha la stessa classe di clienti associata all'azione 
	 * get il cui nome è string. Restrituisce null se il tipo di elemento architetturale non è
	 * equivalente ad un buffer. Se string non è un'azione get allora si restituisce null.
	 * 
	 * @param string deve essere non null.
	 * @return
	 */
	public abstract String getPutFromGet(String string);
	
	/**
	 * Restituisce l'array con la dichiarazione dei parametri inizializzati.
	 * 
	 * @param i
	 * @return
	 */
	public abstract VarInit[] capDecl(Header i); 
	
	public List<PutBehavior> getPutBehaviors(); 

	}
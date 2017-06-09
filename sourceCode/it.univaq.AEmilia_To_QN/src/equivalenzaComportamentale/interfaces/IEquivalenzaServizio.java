package equivalenzaComportamentale.interfaces;

import java.util.HashMap;
import java.util.List;

import specificheAEmilia.Action;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;

/**
 * Espone i metodi per verifica se un tipo di elemento architetturale
 * è equivalente ad un processo di servizio.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 *
 */
public interface IEquivalenzaServizio extends IEquivalenza {

	/**
	 * Restituisce i tassi di servizio per ogni classe di cliente, o null,
	 * se il tipo di elemento architetturale
	 * non è un processo di servizio. Le chiavi della mappa restituita
	 * corrispondono ai nomi delle azioni di selezione.
	 *
	 * @return
	 */
	// viene utilizzata per il confronto di tassi di servizio e quindi la naturale struttura ad
	// albero per i tassi di servizio può essere appiattita
	public abstract HashMap<String, Expression[]> getTassiServizioFromSel();

	/**
	 * Restituisce un array di probabilità  di routing di un cliente
	 * dopo il suo servizio, o null, se il tipo di elemento architetturale non è un processo di servizio.
	 * Le chiavi della mappa restituita corrispondono ai nomi delle azioni di selezione associate
	 * alle probabilità di routing e che sono associate ognuna ad una classe di clienti.
	 * I valori della mappa possono essere null.
	 *
	 * @return
	 */
	public abstract HashMap<String, Expression[]> getProbRoutingFromSel();

	/**
	 * Restituisce un array per le priorità  di scelta destinazione
	 * di un cliente, che lascia il centro di servizio, o null, se il tipo di elemento architetturale non è processo di
	 * servizio. Le chiavi della mappa restituita corrispondono ai nomi delle azioni di selezione
	 * associate alle priorità di scelta e che sono associate ad una classe di clienti.
	 *
	 * @return
	 */
	public abstract HashMap<String, Expression[]> getPrioDestinazioniFromSel();

	/**
	 * Restituisce un array per i pesi dei tassi di scelta
	 * destinazione di un cliente, che lascia il centro di servizio, o null,
	 * se il tipo di elemento architetturale non è un processo
	 * di servizio. Le chiavi della mappa restituita corrispondono ai nomi delle azioni
	 * di selezione. 
	 *
	 * @return
	 */
	public abstract HashMap<String, Expression[]> getPesiDestinazioniFromSel();


	/**
	 * Restituisce i nomi delle azioni di partenza dal centro di servizio, o null, se il tipo di elemento
	 * architetturale non è processo di servizio.
	 *
	 * @return
	 */
	public abstract String[] getDelivers();

	/**
	 * Restituisce le azioni di consegna. Le chiavi della mappa restituita
	 * corrispondono ai nomi delle azioni di consegna che si riferiscono alla stessa classe di clienti
	 * dell'azione di selezione. Restituisce null se l'istanza non è equivalente ad un processo di servizio.
	 *
	 * @return
	 */
	public abstract HashMap<String, Action[]> getDeliverActionsFromSelections();
	
	/**
	 * Restituisce una mappa in cui le chiavi corrispondono ai nomi delle azioni di selezione,
	 * mentre il valore corrispondente è una lista di nomi di azioni di consegna che si riferiscono
	 * alla stessa classe di clienti dell'azione di selezione. Restituisce null se 
	 * l'istanza non è equivalente ad un processo di servizio.
	 * 
	 * @return
	 */
	public abstract HashMap<String, List<String>> getDeliversFromSelection();
	
	/**
	 * Restituisce la lista delle equazioni comportamentali di servizio presenti
	 * all'interno del tipo di elemento architetturale. Restutuisce null se l'istanza
	 * wrappata non è equivalente ad un processo di servizio.
	 * 
	 * @return
	 */
	public abstract List<BehavEquation> getServiceEquations();
	
	/**
	 * Restituisce una mappa in cui le chiavi sono i nomi delle
	 * interazioni di input e i valori corrispondono ai nomi delle azioni di servizio
	 * corripondenti all'azione di selezione chiave. Restituisce null se l'istanza
	 * wrappata non è equivalente ad un processo di servizio.
	 * 
	 * @return
	 */
	public abstract HashMap<String, String> getServicesNamesFromSelections();
	
	/**
	 * Restituisce una mappa in cui le chiavi corrispondono ai nomi delle azioni di selezione,
	 * mentre il valore corrispondente è una lista di nomi di azioni di scelta che si riferiscono
	 * alla stessa classe di clienti dell'azione di selezione. Restituisce null se 
	 * l'istanza non è equivalente ad un processo di servizio.
	 * 
	 * @return
	 */
	public abstract HashMap<String, List<String>> getChoosesFromSelection();
	
	/**
	 * Restituisce un array di priorità  di selezione di un cliente
	 * tra un insieme di classi, o null, se il tipo di elemento architetturale non è processo di servizio
	 * con buffer.
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
}
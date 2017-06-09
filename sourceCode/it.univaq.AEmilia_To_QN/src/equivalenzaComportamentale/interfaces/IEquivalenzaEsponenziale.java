package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Expression;

/**
 * Definisce i metodi da implementare per: determinare l'iEquivalenza di un
 * comportamento con una distribuzione esponenziale; prelevare i dati
 * che caratterizzano una distribuzione esponenziale.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaEsponenziale extends IEquivalenzaDistribuzioneFase
	{

	/**
	 * Restituisce true se e solo se be è un comportamento
	 * equivalente ad una distribuzione esponenziale. In questo caso, l'ultimo ProcessTerm
	 * può essere diverso da stop.
	 *
	 * @param be
	 * @return
	 */
	public boolean isEsponenzialeSenzaStop();

	/**
	 * Restituisce true se e solo se be è un comportamento
	 * equivalente ad una distribuzione esponenziale. In questo caso, l'ultimo ProcessTerm è stop.
	 *
	 * @param be
	 * @return
	 */
	public boolean isEsponenzialeConStop();

	/**
	 * Restituisce il tasso della distribuzione esponenziale equivalente
	 * a be, o null, se il comportamento non è equivalente ad una distribuzione esponenziale.
	 *
	 * @param be
	 * @return
	 */
	public Expression getTassoEsponenziale();

	/**
	 * Restituisce il nome del BehavProcess finale. Se il comportamento non è equivalente
	 * ad una distribuzione esponenziale con un BehavProcess finale si restituisce null.
	 *
	 * @return
	 */
	public String getNomeProcessoFinale();
	
	/**
	 * Restituisce true se e solo se be è un comportamento equivalente ad una distribuzione
	 * esponenziale.
	 * 
	 * @return
	 */
	public boolean isEsponenziale();
	
}
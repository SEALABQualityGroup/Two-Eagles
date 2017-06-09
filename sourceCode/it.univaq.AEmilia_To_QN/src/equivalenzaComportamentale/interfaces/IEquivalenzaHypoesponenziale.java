package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Expression;

/**
 * Definisce i metodi da implementare per: determinare l'iEquivalenza di un
 * comportamento con una distribuzione hypoesponenziale; prelevare i dati
 * che caratterizzano una distribuzione hypoesponenziale.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public interface IEquivalenzaHypoesponenziale extends IEquivalenzaDistribuzioneFase
	{

	/**
	 * Restituisce true se e solo se be è un comportamento equivalente
	 * alla distribuzione Hypoesponenziale. In questo caso, l'ultimo ProcessTerm è un BehavProcess.
	 *
	 * @param be
	 * @return
	 */
	public boolean isHypoesponenzialeSenzaStop();

	/**
	 * Restituisce true se e solo se be è un comportamento equivalente
	 * alla distribuzione Hypoesponenziale. In questo caso, l'ultimo ProcessTerm è stop.
	 *
	 * @param be
	 * @return
	 */
	public boolean isHypoesponenzialeConStop();

	/**
	 * Restituisce i tassi della distribuzione Hypoesponenziale, o null, se il comportamento non è equivalente
	 * alla distribuzione Hypoesponenziale.
	 *
	 * @param be
	 * @return
	 */
	public Expression[] getTassiHypoesponenziale();

	/**
	 * Restituisce il nome del BehavProcess finale. Se il comportamento non è equivalente
	 * ad una distribuzione hyperesponenziale con BehavProcess finale si restituisce null.
	 *
	 * @return
	 */
	public String getNomeProcessoFinale();
	
	/**
	 * Restituisce true se e solo se be è equivalente
	 * ad una distribuzione hypoesponenziale.
	 * 
	 * @return
	 */
	public boolean isHypoesponenziale();
	}
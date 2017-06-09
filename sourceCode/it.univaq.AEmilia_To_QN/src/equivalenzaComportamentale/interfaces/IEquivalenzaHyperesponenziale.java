package equivalenzaComportamentale.interfaces;

import specificheAEmilia.Expression;

/**
 * Definisce i metodi da implementare per: determinare l'iEquivalenza di un
 * comportamento con una distribuzione hyperesponenziale; prelevare i dati
 * che caratterizzano una distribuzione hyperesponenziale.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public interface IEquivalenzaHyperesponenziale extends IEquivalenzaDistribuzioneFase
	{

	/**
	 * Restituisce true se e solo se be � un comportamento equivalente
	 * alla distribuzione Hyperesponenziale. In questo caso,
	 * l'ultimo ProcessTerm dei termini di processo alternativi � un BehavProcess.
	 *
	 * @param be
	 * @return
	 */
	public boolean isHyperesponenzialeSenzaStop();

	/**
	 * Restituisce true se e solo se be � un comportamento equivalente
	 * alla distribuzione Hyperesponenziale. In questo caso,
	 * l'ultimo ProcessTerm dei termini di processo alternativi � stop.
	 *
	 * @param be
	 * @return
	 */
	public boolean isHyperesponenzialeConStop();

	/**
	 * Restituisce i tassi della distribuzione Hyperesponenziale, o null, se il comportamento non � equivalente
	 * alla distribuzione Hypersponenziale.
	 *
	 * @param be
	 * @return
	 */
	public Expression[] getTassiHyperesponenziale();

	/**
	 * Restituisce le probabilit� di diramazione della distribuzione
	 * Hyperesponenziale, o null, se il comportamento non � equivalente
	 * alla distribuzione Hypersponenziale.
	 *
	 * @param be
	 * @return
	 */
	public Expression[] getProbDirHyperesponenziale();

	/**
	 * Restituisce i nomi dei BehavProcess finali. Se il comportamento non � equivalente
	 * ad una distribuzione hyperesponenziale con BehavProcess finali si restituisce null.
	 *
	 * @return
	 */
	public String[] getNomiProcessiFinali();

	/**
	 * Restituisce true se e solo se be � equivalente ad
	 * una distribuzione hypoesponenziale.
	 * 
	 * @return
	 */
	public boolean isHyperesponenziale();
}

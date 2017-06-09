package equivalenzaComportamentale.interfaces;

import specificheAEmilia.BehavEquation;

public interface IEquivalenzaDistribuzioneFase 
	{

	/**
	 * Restituisce l'equazione comportamentale wrappata dall'oggetto EquivalenzaDistribuzione,
	 * sostituendo ogni occorrenza di una chiamata di comportamento con stop. Restituisce null
	 * se l'equazione wrappata non è equivalente ad una distribuzione fase.
	 * 
	 * @return
	 */
	public BehavEquation getBeWithStop();
	
	/**
	 * Restituisce l'equazione comportamentale per la quale
	 * si vuole verificare l'iEquivalenza.
	 *
	 * @return
	 */
	public BehavEquation getBe();

	/**
	 * Imposta l'equazione comportamentale per la quale
	 * si vuole verificare l'iEquivalenza.
	 *
	 * @return
	 */
	public void setBe(BehavEquation be);
	
	}
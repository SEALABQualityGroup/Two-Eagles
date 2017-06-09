package restrizioniGenerali;

public interface IGeneraliRules {

	/**
	 * Restituisce true e e sole se la specifica AEmilia rispetta le regole di restrizioni
	 * sintattiche generali.
	 *
	 * @return
	 * @throws RestrizioniGenException
	 */
	public abstract boolean isCompliantGeneralRules()
			throws RestrizioniGenException;

}
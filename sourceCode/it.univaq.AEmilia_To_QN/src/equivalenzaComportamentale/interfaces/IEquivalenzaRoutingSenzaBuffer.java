package equivalenzaComportamentale.interfaces;


public interface IEquivalenzaRoutingSenzaBuffer 
	extends IEquivalenzaRouting 
	{
	
	/**
	 * Restituisce il nome dell'azione di arrivo, o null, se il tipo di elemento
	 * architetturale non è equivalente ad un processo di routing senza buffer.
	 *
	 * @return
	 */
	public abstract String getArrive();
	}

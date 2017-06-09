package equivalenzaComportamentale.interfaces;


public interface IEquivalenzaRoutingConBuffer 
	extends IEquivalenzaRouting 
	{
	
	/**
	 * Restituisce il nome dell'azione di selezione, o null, se il tipo di elemento
	 * architetturale non è equivalente ad un processo di routing con buffer.
	 *
	 * @return
	 */
	public abstract String getSelect();
		
	}

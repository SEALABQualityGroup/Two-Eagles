package elementiBaseQN.Strutture;

public class AggregatoBufferLimitato extends
		AggregatoBuffer<DataBufferLimitato> 
	{

	private static final long serialVersionUID = 1L;

	public AggregatoBufferLimitato() 
		{
		super();
		}

	// restituisce null se getAction è null oppure non viene trovata
	public Integer getCapacitaFromGet(String getAction)
		{
		if (getAction == null) return null;
		Integer integer = null;
		for (DataBufferLimitato dataBufferLimitato : this)
			{
			String string2 = dataBufferLimitato.getGetAction();
			if (getAction.equals(string2))
				integer = dataBufferLimitato.getCapacita();
			}
		return integer;
		}
	
	// restituisce null se putAction è null oppure non viene trovata
	public Integer getCapacitaFromPut(String putAction)
		{
		if (putAction == null) return null;
		Integer integer = null;
		for (DataBufferLimitato dataBufferLimitato : this)
			{
			String string2 = dataBufferLimitato.getPutAction();
			if (putAction.equals(string2))
				integer = dataBufferLimitato.getCapacita();
			}
		return integer;
		}
	}
package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoProcessoJoinConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoJoinSorgenti;

public class ProcessoJoinSenzaBuffer 
	extends ProcessoJoin 
	{
	
	private static final long serialVersionUID = 1L;

	public ProcessoJoinSenzaBuffer()
		{
		super();
		}

	public ProcessoJoinSenzaBuffer(String nome, AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti,
			AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne, String canale)
			throws ElementoBaseException
		{
		super(nome, aggregatoProcessoJoinSorgenti, aggregatoProcessoJoinConsegne, canale);
		}

	public ProcessoJoinSenzaBuffer(String string) 
		{
		super(string);
		}
	
	
	}
package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoProcessoServizioDestinazioni;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;

public class ProcessoServizioSenzaBuffer extends ProcessoServizio 
	{
	private static final long serialVersionUID = 1L;

	public ProcessoServizioSenzaBuffer(String nome, AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti,
			AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni, int numeroServers) 
		throws ElementoBaseException 
		{
		super(nome, aggregatoProcessoServizioSorgenti, numeroServers);
		}

	public ProcessoServizioSenzaBuffer(String string) 
		{
		super(string);
		}

	public ProcessoServizioSenzaBuffer() 
		{
		super();
		}
	}

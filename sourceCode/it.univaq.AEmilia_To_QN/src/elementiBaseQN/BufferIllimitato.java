package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.DataBuffer;

public class BufferIllimitato extends Buffer<DataBuffer> 
	{

	private static final long serialVersionUID = 1L;

	public BufferIllimitato()
		{
		super();
		}

	public BufferIllimitato(String nome, AggregatoBuffer<DataBuffer> aggregatoBuffer)
		throws ElementoBaseException 
		{
		super(nome, aggregatoBuffer);
		}

	public BufferIllimitato(String string) 
		{
		super(string);
		}
	}

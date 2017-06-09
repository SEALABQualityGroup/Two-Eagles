package elementiBaseQN;

import java.util.List;

import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.DataBufferLimitato;

public class BufferLimitato extends Buffer<DataBufferLimitato> 
	{

//	// la mappa capacità contiene come chiavi il nome dell'interazione di input della classe cliente 
//	// a cui si riferisce la copacità
//	private AggregatoBufferLimitato aggregatoBufferLimitato;

	private static final long serialVersionUID = 1L;

	public BufferLimitato()
		{
		super();
		}

	public BufferLimitato(String nome, AggregatoBuffer<DataBufferLimitato> aggregatoBufferLimitato) 
		throws ElementoBaseException 
		{
		super(nome,aggregatoBufferLimitato);
		}

	public BufferLimitato(String string) 
		{
		super(string);
		}

	public void setCapacita(List<Integer> list)
		throws ElementoBaseException
		{
		// si controlla se list e aggregatoBufferLimitato hanno la stessa dimensione,
		// se non è così si restituisce una ElementoBaseException.
		if (list.size() != this.getAggregatoBuffer().size())
			throw new ElementoBaseException("The list sizing and jobs classes number serviced from buffer " +
					"are different");
		for (int i = 0; i < list.size(); i++)
			{
			DataBufferLimitato dataBufferLimitato = this.getAggregatoBuffer().get(i);
			Integer integer = list.get(i);
			dataBufferLimitato.setCapacita(integer);
			}
		}
	}

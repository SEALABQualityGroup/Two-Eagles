/**
 * 
 */
package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingSorgenti;

/**
 * @author Mirko
 *
 */
public class ProcessoRoutingSenzaBuffer 
	extends ProcessoRouting 
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessoRoutingSenzaBuffer()
		{
		super();
		}

	public ProcessoRoutingSenzaBuffer(String nome, AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti,
		AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne, String canale)
		throws ElementoBaseException
		{
		super(nome, aggregatoProcessoRoutingSorgenti, aggregatoProcessoRoutingConsegne, canale);
		}

	public ProcessoRoutingSenzaBuffer(String string) 
		{
		super(string);
		}
	}

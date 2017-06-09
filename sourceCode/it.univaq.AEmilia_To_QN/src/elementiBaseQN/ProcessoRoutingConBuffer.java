/**
 * 
 */
package elementiBaseQN;

import java.util.List;

import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingSorgenti;


/**
 * @author Mirko
 *
 */
public class ProcessoRoutingConBuffer 
	extends ProcessoRouting 
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Buffer<?> buffer;
	
	public ProcessoRoutingConBuffer()
		{
		super();
		}

	public ProcessoRoutingConBuffer(String nome, AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti,
		AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne,
		Buffer<?> buffer, String canale)
		throws ElementoBaseException
		{
		super(nome, aggregatoProcessoRoutingSorgenti, aggregatoProcessoRoutingConsegne, canale);
		this.buffer = buffer;
		if (!verificaAttacJoinBuffer())
			throw new ElementoBaseException("The join process and the buffer "+
				"are not exactly attached");
		}

	public ProcessoRoutingConBuffer(String string) 
		{
		super(string);
		}

	public Buffer<?> getBuffer() 
		{
		return buffer;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoRoutingConBuffer))
			return false;
		ProcessoRoutingConBuffer processoRoutingConBuffer =
			(ProcessoRoutingConBuffer)obj;
		if (this.getBuffer() == null && processoRoutingConBuffer.getBuffer() != null)
			return false;
		if (this.getBuffer() != null && processoRoutingConBuffer.getBuffer() == null)
			return false;
		if (this.getBuffer() != null && processoRoutingConBuffer.getBuffer() != null &&
				!this.getBuffer().isContainedName(
				processoRoutingConBuffer.getBuffer()))
			return false;
		return super.equals(obj);
		}

	@Override
	public void setBuffers()
		throws ElementoBaseException
		{
		ElementoBaseQN elementoBaseQN =
			this.getAggregatoProcessoRoutingSorgenti().get(0).getSorgente();
		if (!(elementoBaseQN instanceof Buffer<?>))
			throw new ElementoBaseException("The source "+
					elementoBaseQN.getNome()+" of "+getNome()+" is not a buffer");
		Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
		this.buffer = buffer;
		}
	
	/**
	 * Restituisce true se e solo se il buffer è correttamente connesso al processo di routing.
	 * 
	 * @return
	 */
	private boolean verificaAttacJoinBuffer()
		{
		// si verifica che le destinazioni del buffer collegato sono relative a questo oggetto
		List<Destinazione> list = this.buffer.getDestinazioni();
		for (Destinazione destinazione : list)
			{
			if (!destinazione.equals(this))
				return false;
			}
		// si verifica che le sorgenti siano uguali ai buffer collegati
		List<ElementoBaseQN> sorgentiJoin = this.getSorgenti();
		if (!sorgentiJoin.contains(this.buffer)) return false;
		// si verifica che questo canale sia uguale ad ogni canale dei buffers collegati
		List<String> canaliBuffer = this.buffer.getClassi();
		String string = getCanale();
		for (String string2 : canaliBuffer)
			{
			if (!string.equals(string2)) return false;
			}
		return true;
		}
	
	public void setBuffer(Buffer<?> buffer) 
		{
		this.buffer = buffer;
		}

	}

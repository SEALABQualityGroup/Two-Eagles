package elementiBaseQN;

import java.util.ArrayList;
import java.util.List;

import elementiBaseQN.Strutture.AggregatoProcessoJoinConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoJoinSorgenti;

public class ProcessoJoinConBuffer 
	extends ProcessoJoin 
	{

	private static final long serialVersionUID = 1L;

	// ad un processo join deve essere associato più di un buffer.
	// Ogni buffer deve essere un buffer a singola classe.
	private List<Buffer<?>> buffers = new ArrayList<Buffer<?>>();

	public ProcessoJoinConBuffer()
		{
		super();
		}

	public ProcessoJoinConBuffer(String nome, AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti,
			AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne,
			List<Buffer<?>> buffers, String canale)
			throws ElementoBaseException
		{
		super(nome, aggregatoProcessoJoinSorgenti, aggregatoProcessoJoinConsegne, canale);
		this.buffers = buffers;
		if (!verificaAttacJoinBuffer())
			throw new ElementoBaseException("The join process and the buffer "+
					"are not exactly attached");
		}

	public ProcessoJoinConBuffer(String string) 
		{
		super(string);
		}

	/**
	 * Restituisce true se e solo se il buffer è correttamente connesso al processo join.
	 * 
	 * @return
	 */
	private boolean verificaAttacJoinBuffer()
		{
		// si verifica che le destinazioni di ogni buffer collegato sono relative a questo oggetto
		for (Buffer<?> buffer : buffers)
			{
			List<Destinazione> list = buffer.getDestinazioni();
			for (Destinazione destinazione : list)
				{
				if (!destinazione.equals(this))
					return false;
				}
			}
		// si verifica che le sorgenti siano uguali ai buffer collegati
		List<ElementoBaseQN> sorgentiJoin = this.getSorgenti();
		if (!buffers.containsAll(sorgentiJoin)) return false;
		if (!sorgentiJoin.containsAll(buffers)) return false;
		// si verifica che questo canale sia uguale ad ogni canale dei buffers collegati
		for (Buffer<?> buffer : buffers)
			{
			List<String> canaliBuffer = buffer.getClassi();
			String string = getCanale();
			for (String string2 : canaliBuffer)
				{
				if (!string.equals(string2)) return false;
				}
			}
		return true;
		}

	public List<Buffer<?>> getBuffers() 
		{
		return buffers;
		}

	public void setBuffers(List<Buffer<?>> buffers) 
		{
		this.buffers = buffers;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoJoinConBuffer))
			return false;
		ProcessoJoinConBuffer processoJoinConBuffer =
			(ProcessoJoinConBuffer)obj;
		if (this.getBuffers() == null && processoJoinConBuffer.getBuffers() != null)
			return false;
		if (this.getBuffers() != null && processoJoinConBuffer.getBuffers() == null)
			return false;
		if (this.getBuffers() != null && processoJoinConBuffer.getBuffers() != null &&
				this.getBuffers().size() != processoJoinConBuffer.getBuffers().size())
			return false;
		for (int i = 0; i < this.getBuffers().size(); i++)
			{
			ElementoBaseQN elementoBaseQN = this.getBuffers().get(i);
			boolean b = true;
			ElementoBaseQN elementoBaseQN2 = processoJoinConBuffer.getBuffers().get(i);
			if (elementoBaseQN.isContainedName(elementoBaseQN2))
				b = b && true;
			else
				b = b && false;
			if (!b) return false;
			}
		return super.equals(obj);
		}

	@Override
	public void setBuffers()
		throws ElementoBaseException
		{
		List<Buffer<?>> list = 
			this.getAggregatoProcessoJoinSorgenti().getBuffers();
		this.buffers = list;
		}
	}

package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataBuffer;

public class ProcessoServizioConBuffer extends ProcessoServizio {

	private static final long serialVersionUID = 1L;

	private Buffer<?> buffer;

	public ProcessoServizioConBuffer(String nome, AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti,
			Buffer<?> buffer, int numeroServers)
		throws ElementoBaseException 
		{
		super(nome, aggregatoProcessoServizioSorgenti, numeroServers);
		this.buffer = buffer;
		}

	public ProcessoServizioConBuffer(String nome) 
		{
		super(nome);
		}



	public ProcessoServizioConBuffer() 
		{
		super();
		}

	public Buffer<?> getBuffer() 
		{
		return buffer;
		}

	public void setBuffer(Buffer<?> buffer) 
		{
		this.buffer = buffer;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoServizioConBuffer))
			return false;
		ProcessoServizioConBuffer processoServizioConBuffer =
			(ProcessoServizioConBuffer)obj;
		if (this.getBuffer() == null && processoServizioConBuffer.getBuffer() != null)
			return false;
		if (this.getBuffer() != null && processoServizioConBuffer.getBuffer() == null)
			return false;
		if (this.getBuffer() != null && processoServizioConBuffer.getBuffer() != null &&
				!this.getBuffer().isContainedName(
				processoServizioConBuffer.getBuffer()))
			return false;
		return super.equals(obj);
		}

	@Override
	public void setBuffers()
		throws ElementoBaseException
		{
		ElementoBaseQN elementoBaseQN = this.getAggregatoProcessoServizioSorgenti().getPrimaSorgente();
		if (!(elementoBaseQN instanceof Buffer<?>))
			throw new ElementoBaseException("The service process source "
					+getNome()+" is not a buffer");
		this.buffer = (Buffer<?>)elementoBaseQN;
		}

	public void setBuffer(ElementoBaseQN elementoBaseQN)
		{
		Buffer<?> buffer = 
			new Buffer<DataBuffer>();
		buffer.setNome("PROVABuffer");
		this.buffer = buffer;
		}
	
//	private boolean verificaAttacServizioBuffer()
//		{
//		// si verifica che ogni destinazione del buffer sia uguale a questo oggetto
//		HashMap<String,ElementoBaseQN> destinazioniBuffer = buffer.getDestinazioni();
//		Set<Map.Entry<String, ElementoBaseQN>> set = destinazioniBuffer.entrySet();
//		for (Map.Entry<String, ElementoBaseQN> entry : set)
//			{
//			ElementoBaseQN elementoBaseQN = entry.getValue();
//			if (!elementoBaseQN.equals(this)) return false; 
//			}
//		// si verifica che ogni sorgente sia uguale al buffer
//		HashMap<String,ElementoBaseQN> sorgentiServizio = this.getSorgenti();
//		Set<Map.Entry<String, ElementoBaseQN>> set2 = sorgentiServizio.entrySet();
//		for (Map.Entry<String, ElementoBaseQN> entry : set2)
//			{
//			ElementoBaseQN elementoBaseQN = entry.getValue();
//			if (!elementoBaseQN.equals(buffer)) return false;
//			}
//		// si verifica che i canali del buffer siano uguali ai canali di questo oggetto
//		HashMap<String,String> canaliBuffer = buffer.getCanali();
//		Collection<String> collection = canaliBuffer.values();
//		Collection<String> collection2 = this.getCanali();
//		if (!collection.containsAll(collection2)) return false;
//		if (!collection2.containsAll(collection)) return false;
//		return true;
//		}
//
//	public Buffer getBuffer() 
//		{
//		return buffer;
//		}
//
//	public void setBuffer(Buffer buffer) 
//		{
//		this.buffer = buffer;
//		}
//
//	public int getNumeroClassi() 
//		{
//		return this.getCanali().size();
//		}
	
	
}

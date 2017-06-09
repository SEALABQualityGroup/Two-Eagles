package elementiBaseQN;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import elementiBaseQN.Strutture.AggregatoBuffer;

public class ProcessoForkConBuffer extends ProcessoFork {

	private static final long serialVersionUID = 1L;

	private Buffer<?> buffer;

	public ProcessoForkConBuffer()
		{
		super();
		}

	public ProcessoForkConBuffer(String nome, HashMap<String,ElementoBaseQN> sorgenti,
		HashMap<String,Destinazione> destinazioni, Buffer<?> buffer, String canale)
		throws ElementoBaseException
		{
		super(nome, sorgenti, destinazioni, canale);
		this.buffer = buffer;
		if (!verificaAttacForkBuffer())
			throw new ElementoBaseException("The fork process and the buffer "+
					" are not exactly attached");
		}

	public ProcessoForkConBuffer(String string) 
		{
		super(string);
		}

	/**
	 * Restituisce true se e solo se il buffer del processo fork sia
	 * correttamente connesso al fork. 
	 * 
	 * @return
	 */
	private boolean verificaAttacForkBuffer()
		{
		AggregatoBuffer<?> aggregatoBuffer = buffer.getAggregatoBuffer();
		int i = aggregatoBuffer.size();
		HashMap<String,ElementoBaseQN> sorgentiFork = this.getSorgentiForOne();
		int j = sorgentiFork.size();
		// il numero di destinazioni del buffer e il numero di sorgenti fork deve essere uguale
		if (i != j)
			return false;
		// si verifica che ogni destinazione del buffer sia uguale a questo oggetto
		List<Destinazione> list = this.buffer.getAggregatoBuffer().getDestinazioni();
		for (Destinazione destinazione : list)
			{
			if (!(destinazione.equals(this)))
				return false;
			}
		// si verifica che ogni sorgente sia uguale al buffer
		Set<Map.Entry<String, ElementoBaseQN>> set2 = sorgentiFork.entrySet();
		for (Map.Entry<String, ElementoBaseQN> entry : set2)
			{
			ElementoBaseQN elementoBaseQN = entry.getValue();
			if (!(elementoBaseQN.equals(buffer)))
				return false;
			}
		// si verifica che questo canale sia uguale ad ogni classe del buffer
		List<String> list2 = this.buffer.getAggregatoBuffer().getClassi();
		for (String string : list2)
			{
			String string2 = this.getCanale();
			if (!string.equals(string2))
				return false;
			}
		return true;
		}

	public Buffer<?> getBuffer() 
		{
		return buffer;
		}

	public void setBuffer(Buffer<?> buffer) 
		{
		this.buffer = buffer;
		}

	public void setBuffer(ElementoBaseQN elementoBaseQN)
		throws ElementoBaseException
		{
		if (!(elementoBaseQN instanceof Buffer<?>))
			throw new ElementoBaseException(elementoBaseQN.getNome()+" is not a buffer");
		this.buffer = (Buffer<?>)elementoBaseQN;
		}
	
	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoForkConBuffer))
			return false;
		ProcessoForkConBuffer processoForkConBuffer =
			(ProcessoForkConBuffer)obj;
		if (this.getBuffer() == null && processoForkConBuffer.getBuffer() != null)
			return false;
		if (this.getBuffer() != null && processoForkConBuffer.getBuffer() == null)
			return false;
		if (this.getBuffer() != null && processoForkConBuffer.getBuffer() != null &&
				!this.getBuffer().isContainedName(
				processoForkConBuffer.getBuffer()))
			return false;
		return super.equals(obj);
		}

	@Override
	public void setBuffers()
		throws ElementoBaseException
		{
		ElementoBaseQN elementoBaseQN =
			this.getStrutturaSorgenti().getPrimaSorgente();
		if (!(elementoBaseQN instanceof Buffer<?>))
			throw new ElementoBaseException("The source "+
					elementoBaseQN.getNome()+" of "+getNome()+" is not a buffer");
		Buffer<?> buffer = (Buffer<?>)elementoBaseQN;
		this.buffer = buffer;
		}

	/**
	 * Restituisce una mappa che ha come chiavi i nomi delle azioni fork e come valori, il primo
	 * elemento base attaccato all'azione fork.
	 * 
	 * @return
	 */
	public HashMap<String, ElementoBaseQN> getSorgentiForOne() 
		{
		HashMap<String, ElementoBaseQN> hashMap = new HashMap<String, ElementoBaseQN>();
		Set<Entry<String, List<ElementoBaseQN>>> set = this.sorgenti.entrySet();
		for (Entry<String, List<ElementoBaseQN>> entry : set)
			{
			String string = entry.getKey();
			List<ElementoBaseQN> list = entry.getValue();
			hashMap.put(string, list.get(0));
			}
		return hashMap;
		}
	}
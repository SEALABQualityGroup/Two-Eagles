package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.Disciplina;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Strutture.AggregatoBuffer;
import elementiBaseQN.Strutture.DataBuffer;

public class Buffer<E extends DataBuffer> extends ElementoBaseQN
	{
	private static final long serialVersionUID = 1L;

	private AggregatoBuffer<E> aggregatoBuffer = new AggregatoBuffer<E>();
	private Disciplina disciplinaScheduling;

	public Buffer() 
		{
		super();
		}

	public Buffer(String nome, AggregatoBuffer<E> aggregatoBuffer) 
		{
		super(nome);
		this.aggregatoBuffer = aggregatoBuffer;
		}

	public AggregatoBuffer<E> getAggregatoBuffer() 
		{
		return aggregatoBuffer;
		}

	public Buffer(String nome) 
		{
		super(nome);
		}
	
	/**
	 * Restituisce una lista di nomi delle classi di clienti
	 * che il buffer può ospitare.
	 * 
	 * @return
	 */
	public List<String> getClassi() 
		{
		return aggregatoBuffer.getClassi();
		}


	public List<ElementoBaseQN> getSorgenti() 
		{
		return aggregatoBuffer.getSorgenti();
		}
	
	/**
	 * Restituisce una lista di destinazioni di job che attendono in questo buffer.
	 * 
	 * @return
	 */
	public List<Destinazione> getDestinazioni() 
		{
		return aggregatoBuffer.getDestinazioni();
		}

	/**
	 * Restituisce la disciplina di scheduling del buffer.
	 * 
	 * @return
	 */
	public Disciplina getDisciplinaScheduling() 
		{
		return disciplinaScheduling;
		}

	/**
	 * Imposta la disciplina di scheduling del buffer.
	 * 
	 * @param disciplinaScheduling
	 */
	public void setDisciplinaScheduling(Disciplina disciplinaScheduling) 
		{
		this.disciplinaScheduling = disciplinaScheduling;
		}

	/**
	 * Imposta l'aggrgato del buffer.
	 * 
	 * @param aggregatoBuffer
	 */
	public void setAggregatoBuffer(AggregatoBuffer<E> aggregatoBuffer) 
		{
		this.aggregatoBuffer = aggregatoBuffer;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof Buffer))
			return false;
		Buffer<?> buffer = (Buffer<?>)obj;
		if (!this.getAggregatoBuffer().equals(
				buffer.getAggregatoBuffer()))
			return false;
		if (this.disciplinaScheduling == null && 
				buffer.getDisciplinaScheduling() != null)
			return false;
		if (this.disciplinaScheduling != null && 
				buffer.getDisciplinaScheduling() == null)
			return false;
		if (this.disciplinaScheduling != null && 
				buffer.getDisciplinaScheduling() != null && 
				!this.getDisciplinaScheduling().equals(
				buffer.getDisciplinaScheduling()))
			return false;
		return super.equals(obj);
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.aggregatoBuffer.getStrutturaInterazioneInputFromGet(string);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return this.aggregatoBuffer.getStrutturaInterazioneOutputFromPut(string);
		}

	@Override
	public void proporzionaProbabilita() 
		throws ElementoBaseException 
		{
		this.aggregatoBuffer.proporzionaProbabilita();
		}
	
	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.getAggregatoBuffer().replaceSource(elementoBaseQN, list);
		}
	
	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.getAggregatoBuffer().replaceDestination(destinazione, list);
		}	
	}
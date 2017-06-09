package elementiBaseQN.Strutture;

import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;

public class DataBufferLimitato extends DataBuffer 
	{

	private static final long serialVersionUID = 1L;

	private Integer capacita;
	
	public DataBufferLimitato(String getAction, String putAction,
			ElementoBaseQN sorgente, Destinazione destinazione, String classe, 
			Integer capacita, Integer ordineClasse) 
		{
		super(getAction, putAction, sorgente, destinazione, classe, ordineClasse);
		this.capacita = capacita;
		}

	public DataBufferLimitato(Integer ordineClasse) 
		{
		super(ordineClasse);
		}

	public Integer getCapacita() 
		{
		return capacita;
		}

	public void setCapacita(Integer capacita) 
		{
		this.capacita = capacita;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataBufferLimitato))
			return false;
		DataBufferLimitato dataBufferLimitato = 
			(DataBufferLimitato)obj;
		if (this.getCapacita() != dataBufferLimitato.getCapacita())
			return false;
		return super.equals(obj);
		}
	}

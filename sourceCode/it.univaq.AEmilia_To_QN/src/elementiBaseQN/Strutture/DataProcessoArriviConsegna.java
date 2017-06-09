package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class DataProcessoArriviConsegna 
	implements StrutturaInterazioneOutput, Serializable
	{
	
	private static final long serialVersionUID = 1L;

	private Integer ordineAzioneConsegna;
	private String nomeAzioneConsegna;
	private Double probRouting;
	// le restrizioni di sintassi specifiche indicano che
	// possono esserci più di una destinazione
	private List<Destinazione> destinazioni = new ArrayList<Destinazione>();
	
	public DataProcessoArriviConsegna() 
		{
		super();
		}

	public DataProcessoArriviConsegna(Integer ordineAzioneConsegna,
			String nomeAzioneConsegna, Double probRouting,
			Destinazione destinazione) 
		{
		super();
		this.ordineAzioneConsegna = ordineAzioneConsegna;
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		this.probRouting = probRouting;
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		}

	
	
	public DataProcessoArriviConsegna(Integer ordineAzioneConsegna,
			String nomeAzioneConsegna, Double probRouting,
			List<Destinazione> destinazioni) 
		{
		super();
		this.ordineAzioneConsegna = ordineAzioneConsegna;
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		this.probRouting = probRouting;
		this.destinazioni = destinazioni;
		}

	public Integer getOrdineAzioneConsegna() 
		{
		return ordineAzioneConsegna;
		}

	public String getNomeAzioneConsegna() 
		{
		return nomeAzioneConsegna;
		}

	public Double getProbRouting() 
		{
		return probRouting;
		}

	public Destinazione getDestinazione() 
		{
		return destinazioni.get(0);
		}

	public void setProbRouting(Double probRouting) 
		{
		this.probRouting = probRouting;
		}

	public void setOrdineAzioneConsegna(Integer ordineAzioneConsegna) 
		{
		this.ordineAzioneConsegna = ordineAzioneConsegna;
		}

	public void setNomeAzioneConsegna(String nomeAzioneConsegna) 
		{
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		}

	/**
	 * Imposta la probabilità di routing nel seguente modo:
	 * se i è il numero delle destinazione connesse all'azione di consegna e p è la
	 * probabilità di routing relativa all'azione di consegna, allora la probabilità di routing
	 * è uguale a p / i.
	 * 
	 * @throws ElementoBaseException
	 */
	public void proporzionaProbabilita()
		throws ElementoBaseException
		{
		int i = this.destinazioni.size();
		if (getProbRouting() == null)
			throw new ElementoBaseException("The action probability "
					+this.nomeAzioneConsegna+
					" have not setting2");
		// è possibile che non ci siano destinazioni
		if (i != 0)
			setProbRouting(getProbRouting() / i);
		}

	public List<Destinazione> getDestinazioni() 
		{
		return destinazioni;
		}



	public void setDestinazioni(List<Destinazione> destinazioni) 
		{
		this.destinazioni = destinazioni;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoArriviConsegna))
			return false;
		DataProcessoArriviConsegna dataProcessoArriviConsegna = 
			(DataProcessoArriviConsegna)obj;
		if (this.getOrdineAzioneConsegna() != 
			dataProcessoArriviConsegna.getOrdineAzioneConsegna())
			return false;
		if (!(this.getNomeAzioneConsegna().equals(
			dataProcessoArriviConsegna.getNomeAzioneConsegna())))
			return false;
		if (!this.getProbRouting().equals(dataProcessoArriviConsegna.getProbRouting()))
			return false;
		if (this.getDestinazioni().size() !=
			dataProcessoArriviConsegna.getDestinazioni().size())
			return false;
		if (!this.getDestinazioni().equals(dataProcessoArriviConsegna.getDestinazioni()))
			return false;
		return true;
		}


	public void setDestinazione(Destinazione destinazione)
		{
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		}

	@Override
	public void addDestinazione(Destinazione destinazione) 
		{
		if (!this.destinazioni.contains(destinazione))
			this.destinazioni.add(destinazione);
		}
	}

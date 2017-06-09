package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class DataProcessoServizioDestinazione
	implements StrutturaInterazioneOutput,
	Serializable
	{
	
	private static final long serialVersionUID = 1L;

	// le restrizioni di sintassi specifiche indicano che possono esserci più destinazioni
	private List<Destinazione> destinazioni = new ArrayList<Destinazione>();
	private Integer ordineDestinazione;
	private ProbRouting probRouting;
	
	public DataProcessoServizioDestinazione() 
		{
		super();
		}
	
	public DataProcessoServizioDestinazione(Destinazione destinazione,
			Integer ordineDestinazione, ProbRouting probRouting) 
		{
		super();
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		this.ordineDestinazione = ordineDestinazione;
		this.probRouting = probRouting;
		}

	public DataProcessoServizioDestinazione(List<Destinazione> destinazioni,
			Integer ordineDestinazione, ProbRouting probRouting) 
		{
		super();
		this.destinazioni = destinazioni;
		this.ordineDestinazione = ordineDestinazione;
		this.probRouting = probRouting;
		}

	/**
	 * Restituisce la prima destinazione connessa all'azione di consegna.
	 * 
	 * @return
	 */
	public Destinazione getDestinazione() 
		{
		return destinazioni.get(0);
		}
	
	public Integer getOrdineDestinazione() 
		{
		return ordineDestinazione;
		}

	public ProbRouting getProbRouting() 
		{
		return probRouting;
		}

	public void setProbRouting(ProbRouting probRouting) 
		{
		this.probRouting = probRouting;
		}

	public List<Destinazione> getDestinazioni() 
		{
		return destinazioni;
		}

	public void setDestinazioni(List<Destinazione> destinazioni) 
		{
		this.destinazioni = destinazioni;
		}

	public void setOrdineDestinazione(Integer ordineDestinazione) 
		{
		this.ordineDestinazione = ordineDestinazione;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoServizioDestinazione))
			return false;
		DataProcessoServizioDestinazione dataProcessoServizioDestinazione =
			(DataProcessoServizioDestinazione)obj;
		if (this.getDestinazioni().size() != 
			dataProcessoServizioDestinazione.getDestinazioni().size())
			return false;
		if (!this.getDestinazioni().equals(dataProcessoServizioDestinazione.getDestinazioni()))
			return false;
		if (this.getOrdineDestinazione() != 
			dataProcessoServizioDestinazione.getOrdineDestinazione())
			return false;
		if (!(this.getProbRouting().equals(
				dataProcessoServizioDestinazione.getProbRouting())))
			return false;
		return true;
		}

	public String getNomeAzioneConsegna() 
		{
		return probRouting.getNomeAzioneConsegna();
		}

	@Override
	public void addDestinazione(Destinazione destinazione) 
		{
		if (!this.destinazioni.contains(destinazione))
			this.destinazioni.add(destinazione);
		}
	
	public void setDestinazione(Destinazione destinazione)
		{
		List<Destinazione> list =
			new ArrayList<Destinazione>();
		list.add(destinazione);
		this.destinazioni = list;
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
		Double double1 = this.probRouting.getProbabilita();
		String string = this.probRouting.getNomeAzioneConsegna();
		if (double1 == null)
			throw new ElementoBaseException("The action probability "
					+string+
				" have not setting2");
		if (i != 0)
			this.probRouting.setProbabilita(double1 / i);
		}

	public double getProbabilita() 
		{
		return probRouting.getProbabilita();
		}

	public void setProbabilita(double probabilita) 
		{
		probRouting.setProbabilita(probabilita);
		}

	public boolean verificaNoJobExit() 
		{
		String string = this.getNomeAzioneConsegna();
		if (string == null)
			return false;
		else
			return true;
		}
	}
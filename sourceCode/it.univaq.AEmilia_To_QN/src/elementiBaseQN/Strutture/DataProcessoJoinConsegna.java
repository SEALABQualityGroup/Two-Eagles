package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;

public class DataProcessoJoinConsegna
	implements StrutturaInterazioneOutput, Serializable
	{
	
	private static final long serialVersionUID = 1L;

	// le restrizioni di sintassi specifiche indicano che possono esserci più di una destinazione.
	private List<Destinazione> destinazioni = new ArrayList<Destinazione>();
	
	private ProbRouting probRouting;
	
	public DataProcessoJoinConsegna() 
		{
		super();
		}
	
	public DataProcessoJoinConsegna(Destinazione destinazione,
			ProbRouting probRouting) 
		{
		super();
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		this.probRouting = probRouting;
		}

	public DataProcessoJoinConsegna(List<Destinazione> destinazioni,
			ProbRouting probRouting) 
		{
		super();
		this.destinazioni = destinazioni;
		this.probRouting = probRouting;
		}
	
	public Destinazione getDestinazione() 
		{
		return destinazioni.get(0);
		}

	public ProbRouting getProbRouting() 
		{
		return probRouting;
		}

	public void setProbRouting(ProbRouting probRouting) 
		{
		this.probRouting = probRouting;
		}

	public void setNomeAzioneConsegna(String nomeAzioneConsegna) 
		{
		probRouting.setNomeAzioneConsegna(nomeAzioneConsegna);
		}

	public void setProbabilita(double probabilita) 
		{
		probRouting.setProbabilita(probabilita);
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
		if (!(obj instanceof DataProcessoJoinConsegna))
			return false;
		DataProcessoJoinConsegna dataProcessoJoinConsegna =
			(DataProcessoJoinConsegna)obj;
		if (this.getDestinazioni().size() != 
			dataProcessoJoinConsegna.getDestinazioni().size())
			return false;
		if (!this.getDestinazioni().equals(dataProcessoJoinConsegna.getDestinazioni()))
			return false;
		if (!(this.getProbRouting().equals(dataProcessoJoinConsegna.getProbRouting())))
			return false;
		return true;
		}

	@Override
	public void addDestinazione(Destinazione destinazione) 
		{
		if (!this.destinazioni.contains(destinazione))
			this.destinazioni.add(destinazione);
		}

	public String getNomeAzioneConsegna() 
		{
		return probRouting.getNomeAzioneConsegna();
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
		Double double1 = this.probRouting.getProbabilita();
		String string = this.probRouting.getNomeAzioneConsegna();
		int i = this.destinazioni.size();
		if (double1 == null)
			throw new ElementoBaseException("The action probability "
					+string+
					" have not setting2");
		if (i != 0)
			setProbabilita(double1 / i);
		}

	public double getProbabilita() 
		{
		return probRouting.getProbabilita();
		}

	public void setDestinazione(Destinazione destinazione) 
		{
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		}

	public boolean verificaNoJobExit() 
		{
		// prelevo il nome dell'azione di consegna
		String string = this.getNomeAzioneConsegna();
		// se string è null restituisce false, true altrimenti
		if (string == null)
			return false;
		else
			return true;
		}
	}

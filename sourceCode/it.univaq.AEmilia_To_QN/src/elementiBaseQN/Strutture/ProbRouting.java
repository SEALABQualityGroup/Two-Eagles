package elementiBaseQN.Strutture;

import java.io.Serializable;

// probsRouting ha come campi; il nome dell'interazione di output relativa all'elemento;
// la probabilità di routing di un cliente;

public class ProbRouting implements Serializable
	{
	private static final long serialVersionUID = 1L;

	private double probabilita;
	private String nomeAzioneConsegna;
	
	public ProbRouting() 
		{
		super();
		}

	public ProbRouting(double probabilita, String nomeAzioneConsegna) 
		{
		super();
		this.probabilita = probabilita;
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		}

	public ProbRouting(double probabilita)
		{
		super();
		this.probabilita = probabilita;
		}
	
	public ProbRouting(String nomeAzioneConsegna) 
		{
		super();
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		}

	public double getProbabilita() 
		{
		return probabilita;
		}

	public void setProbabilita(double probabilita) 
		{
		this.probabilita = probabilita;
		}

	public String getNomeAzioneConsegna() 
		{
		return nomeAzioneConsegna;
		}

	public void setNomeAzioneConsegna(String nomeAzioneConsegna) 
		{
		this.nomeAzioneConsegna = nomeAzioneConsegna;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProbRouting))
			return false;
		ProbRouting probRouting = (ProbRouting)obj;
		if (this.getProbabilita() != 
			probRouting.getProbabilita())
			return false;
		if (this.getNomeAzioneConsegna() == null && probRouting.getNomeAzioneConsegna() != null)
			return false;
		if (this.getNomeAzioneConsegna() != null && probRouting.getNomeAzioneConsegna() == null)
			return false;
		if (this.getNomeAzioneConsegna() != null && probRouting.getNomeAzioneConsegna() != null &&
				!this.getNomeAzioneConsegna().equals(probRouting.getNomeAzioneConsegna()))
			return false;
		return true;
		}
	}

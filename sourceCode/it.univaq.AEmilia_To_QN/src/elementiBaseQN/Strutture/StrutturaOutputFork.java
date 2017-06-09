package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Destinazione;

public class StrutturaOutputFork 
	implements StrutturaInterazioneOutput,
	Serializable
	{
	
	private static final long serialVersionUID = 1L;

	private String output;
	private List<Destinazione> destinazioni = new ArrayList<Destinazione>();
	
	public StrutturaOutputFork() 
		{
		super();
		}

	public StrutturaOutputFork(String output, List<Destinazione> destinazioni) 
		{
		super();
		this.output = output;
		this.destinazioni = destinazioni;
		}

	public String getOutput() 
		{
		return output;
		}

	public List<Destinazione> getDestinazioni() 
		{
		return destinazioni;
		}

	@Override
	public void addDestinazione(Destinazione destinazione) 
		{
		if (!this.destinazioni.contains(destinazione))
			this.destinazioni.add(destinazione);
		}
	
	public void setOutput(String output) 
		{
		this.output = output;
		}

	public void setDestinazioni(List<Destinazione> destinazioni) 
		{
		this.destinazioni = destinazioni;
		}

	public void setDestinazione(Destinazione destinazione)
		{
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		}
	
	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof StrutturaInputFork))
			return false;
		StrutturaInputFork strutturaInputFork = (StrutturaInputFork)obj;
		if (!this.getOutput().equals(strutturaInputFork.getInput()))
			return false;
		if (this.getDestinazioni().size() != strutturaInputFork.getSorgenti().size())
			return false;
		if (!this.getDestinazioni().equals(strutturaInputFork.getDestinazioni()))
			return false;
		return true;
		}

	public Destinazione getDestinazione() 
		{
		return this.destinazioni.get(0);
		}	
}

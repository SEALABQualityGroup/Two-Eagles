package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

import elementiBaseQN.ElementoBaseQN;

public class StrutturaInputFork 
	implements StrutturaInterazioneInput,
	Serializable
	{
	
	private static final long serialVersionUID = 1L;

	private String input;
	private List<ElementoBaseQN> sorgenti = new ArrayList<ElementoBaseQN>();
	
	public StrutturaInputFork() 
		{
		super();
		}

	public StrutturaInputFork(String input, List<ElementoBaseQN> sorgenti) 
		{
		super();
		this.input = input;
		this.sorgenti = sorgenti;
		}

	public String getInput() 
		{
		return input;
		}

	public List<ElementoBaseQN> getSorgenti() 
		{
		return sorgenti;
		}

	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		if (!this.sorgenti.contains(elementoBaseQN))
			this.sorgenti.add(elementoBaseQN);
		}
	
	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof StrutturaInputFork))
			return false;
		StrutturaInputFork strutturaInputFork = (StrutturaInputFork)obj;
		if (!this.getInput().equals(strutturaInputFork.getInput()))
			return false;
		if (this.getSorgenti().size() != strutturaInputFork.getSorgenti().size())
			return false;
		for (ElementoBaseQN elementoBaseQN : this.sorgenti)
			{
			if (!elementoBaseQN.isContainedName(strutturaInputFork.getSorgenti()))
				return false;
			}
		return true;
		}

	public void setInput(String input) 
		{
		this.input = input;
		}

	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = sorgenti;
		}
	
	public void setSorgente(ElementoBaseQN elementoBaseQN)
		{
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(elementoBaseQN);
		}

	public ElementoBaseQN getSorgente() 
		{
		return this.sorgenti.get(0);
		}

	public ElementoBaseQN getDestinazione() {
		return null;
	}

	public List<ElementoBaseQN> getDestinazioni() {
		return null;
	}

	public void setDestinazioni(List<ElementoBaseQN> destinazioni) {
	}

	@Override
	public Integer getOrdineClasse() 
		{
		return 0;
		}
	}

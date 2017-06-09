package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

import elementiBaseQN.ElementoBaseQN;

public class DataProcessoJoinSorgente 
	implements StrutturaInterazioneInput, Serializable
	{
	
	private static final long serialVersionUID = 1L;

	private String nomeAzioneSorgente;
	
	// le restrizioni di sintassi specifiche indicano
	// che possono esserci più di una sorgente
	private List<ElementoBaseQN> sorgenti = new ArrayList<ElementoBaseQN>();
	
	public DataProcessoJoinSorgente() 
		{
		super();
		}

	public DataProcessoJoinSorgente(String nomeAzioneSorgente,
			ElementoBaseQN sorgente) 
		{
		super();
		this.nomeAzioneSorgente = nomeAzioneSorgente;
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		}

	public String getNomeAzioneSorgente() 
		{
		return nomeAzioneSorgente;
		}

	public void setNomeAzioneSorgente(String nomeAzioneSorgente) 
		{
		this.nomeAzioneSorgente = nomeAzioneSorgente;
		}

	public ElementoBaseQN getSorgente() 
		{
		return sorgenti.get(0);
		}

	public List<ElementoBaseQN> getSorgenti() 
		{
		return sorgenti;
		}

	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = sorgenti;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoJoinSorgente))
			return false;
		DataProcessoJoinSorgente dataProcessoJoinSorgente =
			(DataProcessoJoinSorgente)obj;
		if (!(this.getNomeAzioneSorgente().equals(
				dataProcessoJoinSorgente.getNomeAzioneSorgente())))
			return false;
		if (this.getSorgenti().size() != 
			dataProcessoJoinSorgente.getSorgenti().size())
			return false;
		for (ElementoBaseQN elementoBaseQN : this.getSorgenti())
			{
			if (!elementoBaseQN.isContainedName(dataProcessoJoinSorgente.getSorgenti()))
				return false;
			}
		return true;
		}

	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		if (!this.sorgenti.contains(elementoBaseQN))
			this.sorgenti.add(elementoBaseQN);
		}

	public void setSorgente(ElementoBaseQN sorgente) 
		{
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
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
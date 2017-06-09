/**
 * 
 */
package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import elementiBaseQN.ElementoBaseQN;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;

/**
 * @author Mirko
 *
 */
public class DataProcessoRoutingSorgente 
	implements StrutturaInterazioneInput,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomeAzioneSorgente;
	
	// le restrizioni di sintassi specifiche indicano
	// che possono esserci più di una sorgente
	private List<ElementoBaseQN> sorgenti = new ArrayList<ElementoBaseQN>();
	
	public DataProcessoRoutingSorgente() 
		{
		super();
		}

	public DataProcessoRoutingSorgente(String nomeAzioneSorgente,
			ElementoBaseQN sorgente) 
		{
		super();
		this.nomeAzioneSorgente = nomeAzioneSorgente;
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		}
	
	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput#addSorgente(elementiBaseQN.ElementoBaseQN)
	 */
	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		if (!this.sorgenti.contains(elementoBaseQN))
			this.sorgenti.add(elementoBaseQN);
		}

	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput#getSorgente()
	 */
	@Override
	public ElementoBaseQN getSorgente() 
		{
		return sorgenti.get(0);
		}

	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput#getSorgenti()
	 */
	@Override
	public List<ElementoBaseQN> getSorgenti() 
		{
		return sorgenti;
		}

	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput#setSorgente(elementiBaseQN.ElementoBaseQN)
	 */
	@Override
	public void setSorgente(ElementoBaseQN sorgente) 
		{
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		}

	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput#setSorgenti(java.util.List)
	 */
	@Override
	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = sorgenti;
		}
	
	public String getNomeAzioneSorgente() 
		{
		return nomeAzioneSorgente;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoRoutingSorgente))
			return false;
		DataProcessoRoutingSorgente dataProcessoRoutingSorgente =
			(DataProcessoRoutingSorgente)obj;
		if (!(this.getNomeAzioneSorgente().equals(
				dataProcessoRoutingSorgente.getNomeAzioneSorgente())))
			return false;
		if (this.getSorgenti().size() != 
			dataProcessoRoutingSorgente.getSorgenti().size())
			return false;
		for (ElementoBaseQN elementoBaseQN : this.getSorgenti())
			{
			if (!elementoBaseQN.isContainedName(dataProcessoRoutingSorgente.getSorgenti()))
				return false;
			}
		return true;
		}
	
	public void setNomeAzioneSorgente(String nomeAzioneSorgente) 
		{
		this.nomeAzioneSorgente = nomeAzioneSorgente;
		}

	@Override
	public Integer getOrdineClasse() 
		{
		return 0;
		}
	}

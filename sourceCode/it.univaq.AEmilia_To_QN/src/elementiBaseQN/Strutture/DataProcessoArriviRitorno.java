package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import elementiBaseQN.ElementoBaseQN;

public class DataProcessoArriviRitorno 
	implements StrutturaInterazioneInput, Serializable 
	{
	
	private static final long serialVersionUID = 1L;

	private ElementoBaseQN ritorno;
	private Integer ordineRitorno;
	private String nomeAzioneRitorno;
	
	
	
	public DataProcessoArriviRitorno() 
		{
		super();
		}

	public DataProcessoArriviRitorno(ElementoBaseQN ritorno,
			Integer ordineRitorno, String nomeAzioneRitorno) 
		{
		super();
		this.ritorno = ritorno;
		this.ordineRitorno = ordineRitorno;
		this.nomeAzioneRitorno = nomeAzioneRitorno;
		}
	
	public Integer getOrdineRitorno() 
		{
		return ordineRitorno;
		}
	
	public String getNomeAzioneRitorno() 
		{
		return nomeAzioneRitorno;
		}

	public void setOrdineRitorno(Integer ordineRitorno) 
		{
		this.ordineRitorno = ordineRitorno;
		}

	public void setNomeAzioneRitorno(String nomeAzioneRitorno) 
		{
		this.nomeAzioneRitorno = nomeAzioneRitorno;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoArriviRitorno))
			return false;
		DataProcessoArriviRitorno dataProcessoArriviRitorno =
			(DataProcessoArriviRitorno)obj;
		if (this.getSorgente() == null && dataProcessoArriviRitorno.getSorgente() != null)
			return false;
		if (this.getSorgente() != null && dataProcessoArriviRitorno.getSorgente() == null)
			return false;
		if (this.getSorgente() != null && dataProcessoArriviRitorno.getSorgente() == null &&
				!(this.getSorgente().isContainedName(
			dataProcessoArriviRitorno.getSorgente())))
			return false;
		if (this.getOrdineRitorno() != dataProcessoArriviRitorno.getOrdineRitorno())
			return false;
		if (!(this.getNomeAzioneRitorno().equals(dataProcessoArriviRitorno.getNomeAzioneRitorno())))
			return false;
		return true;
		}

	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		this.ritorno = elementoBaseQN;
		}

	public ElementoBaseQN getSorgente() 
		{
		return ritorno;
		}

	public List<ElementoBaseQN> getSorgenti() 
		{
		List<ElementoBaseQN> list = new ArrayList<ElementoBaseQN>();
		list.add(this.getSorgente());
		return list;
		}

	public void setSorgente(ElementoBaseQN sorgente) 
		{
		this.ritorno = sorgente;
		}

	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.ritorno = sorgenti.get(0);
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

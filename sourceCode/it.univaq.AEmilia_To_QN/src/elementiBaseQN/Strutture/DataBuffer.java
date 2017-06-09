package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;

import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseQN;

public class DataBuffer implements StrutturaInterazioneInput, StrutturaInterazioneOutput, Serializable
	{
	private static final long serialVersionUID = 1L;

	private String getAction;
	private String putAction;
	// le restrizioni di sintassi specifiche indicano
	// che le sorgenti possono essere più di una
	private List<ElementoBaseQN> sorgenti = new ArrayList<ElementoBaseQN>();
	// le restrizioni di sintassi specifiche indicano
	// che le destinazioni possono essere più di una
	private List<Destinazione> destinazioni = new ArrayList<Destinazione>();
	
	// se per l'azione get non è associata nessuna classe di clienti si inserisce la stringa ""
	private String classe = "";
	
	// introducimo il seguente campo perchè possiamo avere più DataBuffer per una stessa
	// classe di clienti, perchè consentiamo il feedback
	private Integer ordineClasse;
	
	private Double numberOfVisits;
	
	public DataBuffer(String getAction, String putAction,ElementoBaseQN sorgente, 
			Destinazione destinazione,String classe, Integer ordineClasse) 
		{
		super();
		this.getAction = getAction;
		this.putAction = putAction;
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione); 
		this.classe = classe;
		this.ordineClasse = ordineClasse;
		}

	public DataBuffer(String getAction, String putAction,
			List<ElementoBaseQN> sorgenti, Destinazione destinazione,
			String classe, Integer ordineClasse) 
		{
		super();
		this.getAction = getAction;
		this.putAction = putAction;
		this.sorgenti = sorgenti;
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		this.classe = classe;
		this.ordineClasse = ordineClasse;
		}

	public DataBuffer(String getAction, String putAction,
			List<ElementoBaseQN> sorgenti, List<Destinazione> destinazioni,
			String classe, Integer ordineClasse) 
		{
		super();
		this.getAction = getAction;
		this.putAction = putAction;
		this.sorgenti = sorgenti;
		this.destinazioni = destinazioni;
		this.classe = classe;
		this.ordineClasse = ordineClasse;
		}

	public DataBuffer(Integer ordineClasse) 
		{
		super();
		this.ordineClasse = ordineClasse;
		}
	
	/**
	 * Assegna come unica sorgente di job connessa
	 * all'interazione di input l'elemento
	 * sorgente.
	 * 
	 * @param sorgente
	 */
	public void setSorgente(ElementoBaseQN sorgente) 
		{
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		}

	public String getGetAction() 
		{
		return getAction;
		}

	/**
	 * Restituisce la prima sorgente connessa all'azione get del buffer.
	 * 
	 * @return
	 */
	public ElementoBaseQN getSorgente() 
		{
		return sorgenti.get(0);
		}

	public String getClasse() 
		{
		return classe;
		}

	/**
	 * Restituisce la prima destinazione attaccata all'azione di put.
	 * 
	 * @return
	 */
	public Destinazione getDestinazione() 
		{
		return destinazioni.get(0);
		}

	public String getPutAction() 
		{
		return putAction;
		}

	/**
	 * Restituisce una lista di elementi base attaccati all'interazione di input.
	 * 
	 * @return
	 */
	public List<ElementoBaseQN> getSorgenti() 
		{
		return sorgenti;
		}

	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = sorgenti;
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
		if (!(obj instanceof DataBuffer))
			return false;
		DataBuffer dataBuffer = (DataBuffer)obj;
		if (!(this.getGetAction().equals(dataBuffer.getGetAction())))
			return false;
		if (!(this.getPutAction().equals(dataBuffer.getPutAction())))
			return false;
		if (!(this.getClasse().equals(dataBuffer.getClasse())))
			return false;
		if (this.getDestinazioni().size() != dataBuffer.getDestinazioni().size())
			return false;
		if (!this.getDestinazioni().equals(dataBuffer.getDestinazioni()))
			return false;
		if (this.getSorgenti().size() != dataBuffer.getSorgenti().size())
			return false;
		if (!this.getSorgenti().equals(dataBuffer.getSorgenti()))
			return false;
		if (!this.ordineClasse.equals(dataBuffer.ordineClasse))
			return false;
		return true;
		}

	public void setGetAction(String getAction) 
		{
		this.getAction = getAction;
		}

	public void setPutAction(String putAction) 
		{
		this.putAction = putAction;
		}

	public void setClasse(String classe) 
		{
		this.classe = classe;
		}

	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		if (!this.sorgenti.contains(elementoBaseQN))
			this.sorgenti.add(elementoBaseQN);
		}

	@Override
	public void addDestinazione(Destinazione destinazione) 
		{
		if (!this.destinazioni.contains(destinazione))
			this.destinazioni.add(destinazione);
		}
	
	public void setDestinazione(Destinazione destinazione)
		{
		this.destinazioni = new ArrayList<Destinazione>();
		this.destinazioni.add(destinazione);
		}
	
	public void proporzionaProbabilita() 
		{
		}

	public Integer getOrdineClasse() 
		{
		return ordineClasse;
		}

	public Double getNumberOfVisits() 
		{
		return numberOfVisits;
		}

	public void setNumberOfVisits(Double numberOfVisits) 
		{
		this.numberOfVisits = numberOfVisits;
		}
	}
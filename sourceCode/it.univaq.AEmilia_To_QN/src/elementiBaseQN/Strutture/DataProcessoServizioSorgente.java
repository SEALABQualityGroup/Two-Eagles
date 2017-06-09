package elementiBaseQN.Strutture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;

import elementiBaseQN.Destinazione;
import elementiBaseQN.ElementoBaseException;
import elementiBaseQN.ElementoBaseQN;

public class DataProcessoServizioSorgente
	implements StrutturaInterazioneInput,
	Serializable
	{
	private static final long serialVersionUID = 1L;

	private Integer ordineClasse;
	private String nomeAzioneSelezione;
	// se si riferisce ad un processo di servizio senza buffer
	// viene impostato a 1 di default
	private Integer priorita = 1;
	// viene impostato a 1d
	private Double probabilita = 1d;
	private Double tempoServizio;
	// le restrizioni di sintassi specifica indicano che possono esserci più sorgenti
	private List<ElementoBaseQN> sorgenti = new ArrayList<ElementoBaseQN>();
	
	// se non è stata associata nessuna classe, si pone la stringa "".
	private String classe = "";
	private AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni;
	
	private Double numberOfVisits;

	public DataProcessoServizioSorgente(
			Integer ordineAzioneSelezione,
			String nomeAzioneSelezione,
			Integer priorita,
			Double probabilita,
			Double tempoServizio,
			ElementoBaseQN sorgente,
			String classe,
			AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni) 
		{
		super();
		this.ordineClasse = ordineAzioneSelezione;
		this.nomeAzioneSelezione = nomeAzioneSelezione;
		this.priorita = priorita;
		this.probabilita = probabilita;
		this.tempoServizio = tempoServizio;
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		this.classe = classe;
		this.aggregatoProcessoServizioDestinazioni = aggregatoProcessoServizioDestinazioni;
		}

	public DataProcessoServizioSorgente(
			Integer ordineAzioneSelezione,
			String nomeAzioneSelezione,
			Integer priorita,
			Double probabilita,
			Double tempoServizio,
			List<ElementoBaseQN> sorgenti,
			String classe,
			AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni) 
		{
		super();
		this.ordineClasse = ordineAzioneSelezione;
		this.nomeAzioneSelezione = nomeAzioneSelezione;
		this.priorita = priorita;
		this.probabilita = probabilita;
		this.tempoServizio = tempoServizio;
		this.sorgenti = sorgenti;
		this.classe = classe;
		this.aggregatoProcessoServizioDestinazioni = aggregatoProcessoServizioDestinazioni;
		}

	public DataProcessoServizioSorgente(Integer ordineAzioneSelezione) 
		{
		super();
		this.ordineClasse = ordineAzioneSelezione;
		}

	public String getNomeAzioneSelezione() 
		{
		return nomeAzioneSelezione;
		}

	public Integer getPriorita() 
		{
		return priorita;
		}

	public Double getProbabilita() 
		{
		return probabilita;
		}

	public Double getTempoServizio() 
		{
		return tempoServizio;
		}

	/**
	 * Restituisce la prima sorgente attaccata all'interazione di input.
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

	public AggregatoProcessoServizioDestinazioni getAggregatoProcessoServizioDestinazioni() 
		{
		return aggregatoProcessoServizioDestinazioni;
		}
	
	public void setSorgente(ElementoBaseQN sorgente) 
		{
		this.sorgenti = new ArrayList<ElementoBaseQN>();
		this.sorgenti.add(sorgente);
		}

	public int getNumeroDestinazioni() 
		{
		return aggregatoProcessoServizioDestinazioni.getNumeroDestinazioni();
		}
		
	public List<Double> getProbsRouting() 
		{
		return aggregatoProcessoServizioDestinazioni.getProbsRouting();
		}

	public void setTempoServizio(Double tempoServizio) 
		{
		this.tempoServizio = tempoServizio;
		}
	
	/**
	 * Imposta le probabilità di routing a secondo dell'ordine e contenuto di list.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setProbsRouting(List<Double> list)
		throws ElementoBaseException
		{
		// se list e aggregatoProcessoServizioDestinazioni non hanno la stessa lunghezza
		// si restituisce un'eccezione ElementoBaseException
		if (list.size() != this.aggregatoProcessoServizioDestinazioni.size())
			throw new ElementoBaseException("The size of routing probabilities list and destinations aggragate "
				+"have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			Double double1 = list.get(i);
			DataProcessoServizioDestinazione dataProcessoServizioDestinazione =
				this.aggregatoProcessoServizioDestinazioni.get(i);
			ProbRouting probRouting = dataProcessoServizioDestinazione.getProbRouting();
			if (probRouting == null)
				probRouting = new ProbRouting(double1);
			dataProcessoServizioDestinazione.setProbRouting(probRouting);
			}
		}

	public void setOrdineAzioneSelezione(Integer ordineAzioneSelezione) 
		{
		this.ordineClasse = ordineAzioneSelezione;
		}

	public void setNomeAzioneSelezione(String nomeAzioneSelezione) 
		{
		this.nomeAzioneSelezione = nomeAzioneSelezione;
		}

	public List<ElementoBaseQN> getSorgenti() 
		{
		return sorgenti;
		}

	public void setSorgenti(List<ElementoBaseQN> sorgenti) 
		{
		this.sorgenti = sorgenti;
		}

	public void setPriorita(Integer priorita) 
		{
		this.priorita = priorita;
		}

	public void setProbabilita(Double probabilita) 
		{
		this.probabilita = probabilita;
		}

	public void setClasse(String classe) 
		{
		this.classe = classe;
		}

	public void setAggregatoProcessoServizioDestinazioni(
			AggregatoProcessoServizioDestinazioni aggregatoProcessoServizioDestinazioni) 
		{
		this.aggregatoProcessoServizioDestinazioni = aggregatoProcessoServizioDestinazioni;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof DataProcessoServizioSorgente))
			return false;
		DataProcessoServizioSorgente dataProcessoServizioSorgente =
			(DataProcessoServizioSorgente)obj;
		if (this.getOrdineClasse() != 
			dataProcessoServizioSorgente.getOrdineClasse())
			return false;
		if (!(this.getNomeAzioneSelezione().equals(
				dataProcessoServizioSorgente.getNomeAzioneSelezione())))
			return false;
		if (this.getPriorita() != 
			dataProcessoServizioSorgente.getPriorita())
			return false;
		if (!this.getProbabilita().equals(dataProcessoServizioSorgente.getProbabilita()))
			return false;
		if (!this.getTempoServizio().equals(dataProcessoServizioSorgente.getTempoServizio()))
			return false;
		if (this.getSorgenti().size() !=
			dataProcessoServizioSorgente.getSorgenti().size())
			return false;
		for (ElementoBaseQN elementoBaseQN : this.getSorgenti())
			{
			if (!elementoBaseQN.isContainedName(dataProcessoServizioSorgente.getSorgenti()))
				return false;
			}
		if (!this.getClasse().equals(
				dataProcessoServizioSorgente.getClasse()))
			return false;
		if (!this.getAggregatoProcessoServizioDestinazioni().equals(
				dataProcessoServizioSorgente.getAggregatoProcessoServizioDestinazioni()))
			return false;
		return true;
		}

	/**
	 * Imposta l'aggragato delle destinazioni a seconda del contenuto e ordine di list e list2.
	 * 
	 * @param list
	 * @param list2
	 * @throws AEIintoElementiBaseException
	 */
	// list contiene i nomi delle azioni di consegna
	// list2 contiene le probebilità di routing
	public void setDestinazioni(List<String> list, List<Double> list2)
		throws ElementoBaseException
		{
		// se list e list2 sono null vuol dire che ogni job della classe
		// di clienti esce dalla rete.
		if (list == null && list2 != null)
			throw new ElementoBaseException("The delivery actions list is null "+
				"but the routing probabilities list is not null");
		else if (list != null && list2 == null)
			throw new ElementoBaseException("The routing probabilities list "+
				"is null but the delivery actions list is not null");
		else if (list == null && list2 == null)
			this.aggregatoProcessoServizioDestinazioni =
				new AggregatoProcessoServizioDestinazioni();
		else 
			{
			if (list.size() != list2.size())
				throw new ElementoBaseException("The delivery actions and "+
					"routing probabilities lists have different size");
			// list può contenere dei null
			this.aggregatoProcessoServizioDestinazioni =
				new AggregatoProcessoServizioDestinazioni(list,list2);
			}
		}

	/**
	 * Restituisce la struttura per l'interazione di output di nome string.
	 * 
	 * @param string
	 * @return
	 */
	public StrutturaInterazioneOutput getStrutturaFromOutput(String string) 
		{
		return aggregatoProcessoServizioDestinazioni
				.getStrutturaFromOutput(string);
		}



	@Override
	public void addSorgente(ElementoBaseQN elementoBaseQN) 
		{
		if (!this.sorgenti.contains(elementoBaseQN))
			this.sorgenti.add(elementoBaseQN);
		}

	public ElementoBaseQN getPrimaSorgente() 
		{
		return this.getSorgente();
		}

	/**
	 * Imposta la probabilità di routing della struttura di output relativa ad ogni azione di
	 * consegna nel seguente modo: se i è il numero degli elementi base connessi all'azione di
	 * consegna e p è la probabilità di consegna associata all'azione, allora la nuova probabilità
	 * di routing è p / i.
	 * 
	 * @throws ElementoBaseException
	 */
	public void proporzionaProbabilita() 
		throws ElementoBaseException 
		{
		aggregatoProcessoServizioDestinazioni.proporzionaProbabilita();
		}
	
	/**
	 * Sostituisce destinazione con ogni elemento presente in list, se non è già presente.
	 *  
	 * @param destinazione
	 * @param list
	 */
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.aggregatoProcessoServizioDestinazioni.replaceDestination(destinazione,list);
		}

	public boolean verificaNoJobExit() 
		{
		return this.aggregatoProcessoServizioDestinazioni.verificaNoJobExit();
		}

	@Override
	public Integer getOrdineClasse() 
		{
		return this.ordineClasse;
		}

	public List<Destinazione> getDestinazioni() 
		{
		List<Destinazione> list = this.aggregatoProcessoServizioDestinazioni.getDestinazioni();
		return list;
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

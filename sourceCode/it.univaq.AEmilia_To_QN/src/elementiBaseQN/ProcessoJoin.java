package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;

import elementiBaseQN.Strutture.AggregatoProcessoJoinConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoJoinSorgenti;
import elementiBaseQN.Strutture.DataProcessoJoinConsegna;
import elementiBaseQN.Strutture.ProbRouting;

public class ProcessoJoin extends ElementoBaseQN 
	{

	private static final long serialVersionUID = 1L;

	private AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti = new AggregatoProcessoJoinSorgenti();
	private AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne = new AggregatoProcessoJoinConsegne();
	private String canale = "";
	private Double numberOfVisits;

	public ProcessoJoin()
		{
		super();
		}

	public ProcessoJoin(String nome, AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti, 
			AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne,
			String canale) throws ElementoBaseException 
		{
		super(nome);
		this.aggregatoProcessoJoinSorgenti = aggregatoProcessoJoinSorgenti;
		this.aggregatoProcessoJoinConsegne = aggregatoProcessoJoinConsegne;
		this.canale = canale;
		}

	public ProcessoJoin(String string) 
		{
		super(string);
		}

	public AggregatoProcessoJoinSorgenti getAggregatoProcessoJoinSorgenti() 
		{
		return aggregatoProcessoJoinSorgenti;
		}

	public AggregatoProcessoJoinConsegne getAggregatoProcessoJoinConsegne() 
		{
		return aggregatoProcessoJoinConsegne;
		}

	public String getCanale() 
		{
		return canale;
		}
	
	public List<ElementoBaseQN> getSorgenti() 
		{
		return aggregatoProcessoJoinSorgenti.getSorgenti();
		}

	/**
	 * Assegna le probabilità di routing secondo l'ordine e contenuto di list ad ogni 
	 * azione di consegna.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setProbsRouting(List<Double> list)
		throws ElementoBaseException
		{
		// se list e aggregatoProcessoJoinConsegne non hanno la stessa lunghezza si restituisce
		// un'eccezione ElementoBaseException
		if (list.size() != this.aggregatoProcessoJoinConsegne.size())
			throw new ElementoBaseException("The routing probabilities list and the delivery aggregate "+
					"have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			Double double1 = list.get(i);
			DataProcessoJoinConsegna dataProcessoJoinConsegna =
				this.aggregatoProcessoJoinConsegne.get(i);
			ProbRouting probRouting = dataProcessoJoinConsegna.getProbRouting();
			if (probRouting == null)
				probRouting = new ProbRouting(double1);
			else 
				probRouting.setProbabilita(double1);
			}
		}
	
	/**
	 * Imposta i nomi delle azioni di consegna a seconda dell'ordine e contenuto di list. 
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setAzioniConsegna(List<String> list)
		throws ElementoBaseException
		{
		// se list e aggregatoProcessoJoinConsegne non hanno la stessa lunghezza si restituisce
		// un'eccezione ElementoBaseException
		if (list.size() != this.aggregatoProcessoJoinConsegne.size())
			throw new ElementoBaseException("The delivery actions list and the delivery aggregate "+
					"have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			String string = list.get(i);
			DataProcessoJoinConsegna dataProcessoJoinConsegna =
				this.aggregatoProcessoJoinConsegne.get(i);
			ProbRouting probRouting = dataProcessoJoinConsegna.getProbRouting();
			if (probRouting == null)
				probRouting = new ProbRouting(string);
			else
				probRouting.setNomeAzioneConsegna(string);
			}
		}

	public void setAggregatoProcessoJoinConsegne(
			AggregatoProcessoJoinConsegne aggregatoProcessoJoinConsegne) 
		{
		this.aggregatoProcessoJoinConsegne = aggregatoProcessoJoinConsegne;
		}

	
	
	public void setAggregatoProcessoJoinSorgenti(
			AggregatoProcessoJoinSorgenti aggregatoProcessoJoinSorgenti) {
		this.aggregatoProcessoJoinSorgenti = aggregatoProcessoJoinSorgenti;
	}
	
	public void setCanale(String canale) {
		this.canale = canale;
	}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoJoin))
			return false;
		ProcessoJoin processoJoin =
			(ProcessoJoin)obj;
		if (this.getAggregatoProcessoJoinConsegne() == null && 
				processoJoin.getAggregatoProcessoJoinConsegne() != null)
			return false;
		if (this.getAggregatoProcessoJoinConsegne() != null &&
				processoJoin.getAggregatoProcessoJoinConsegne() == null)
			return false;
		if (this.getAggregatoProcessoJoinConsegne() != null &&
				processoJoin.getAggregatoProcessoJoinConsegne() != null &&
				!this.getAggregatoProcessoJoinConsegne().equals(
				processoJoin.getAggregatoProcessoJoinConsegne()))
			return false;
		if (this.getAggregatoProcessoJoinSorgenti() == null &&
				processoJoin.getAggregatoProcessoJoinSorgenti() != null)
			return false;
		if (this.getAggregatoProcessoJoinSorgenti() != null && 
				processoJoin.getAggregatoProcessoJoinSorgenti() == null)
			return false;
		if (this.getAggregatoProcessoJoinSorgenti() != null &&
				processoJoin.getAggregatoProcessoJoinSorgenti() != null &&
				!this.getAggregatoProcessoJoinSorgenti().equals(
				processoJoin.getAggregatoProcessoJoinSorgenti()))
			return false;
		if (!this.getCanale().equals(
				processoJoin.getCanale()))
			return false;
		return super.equals(obj);
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.aggregatoProcessoJoinSorgenti.getStrutturaFromInput(string);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return this.aggregatoProcessoJoinConsegne.getStrutturaFromOutput(string);
		}

	public void proporzionaProbabilita() 
		throws ElementoBaseException 
		{
		aggregatoProcessoJoinConsegne.proporzionaProbabilita();
		}
	
	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.aggregatoProcessoJoinConsegne.replaceDestination(destinazione, list);
		}

	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.aggregatoProcessoJoinSorgenti.replaceSource(elementoBaseQN, list);
		}

	// restituisce true se e solo se c'è unalternativa in cui un job esce dalla rete
	public boolean verificaNoJobExit() 
		{
		return this.aggregatoProcessoJoinConsegne.verificaNoJobExit();
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
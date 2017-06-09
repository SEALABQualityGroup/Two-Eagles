package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;
import elementiBaseQN.Strutture.DataProcessoArriviConsegna;

/**
 * Rappresenta un processo di arrivi e definisce un canale di clienti. Per poter essere istanziata,
 * bisogna fornire una lista delle probabilità  di routing del canale di clienti e una lista degli elementi
 * base, che rappresentano le possibili destinazioni di ogni cliente.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ProcessoArrivi extends ElementoBaseQN
	{
	
	private static final long serialVersionUID = 1L;

	private AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne = new AggregatoProcessoArriviConsegne();

	public ProcessoArrivi()
		{
		super();
		}

	public ProcessoArrivi(String nome, AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne)
		throws ElementoBaseException
		{
		super(nome);
		this.aggregatoProcessoArriviConsegne = aggregatoProcessoArriviConsegne;
		}

	public ProcessoArrivi(String string)
		{
		super(string);
		}

	public AggregatoProcessoArriviConsegne getAggregatoProcessoArriviConsegne()
		{
		return aggregatoProcessoArriviConsegne;
		}

	/**
	 * Restituisce il numero delle azioni di consegna presenti nel processo di arrivi.
	 * 
	 * @return
	 */
	public int getNumeroConsegne()
		{
		return this.aggregatoProcessoArriviConsegne.size();
		}

	/**
	 * Restituisce la lista delle destinazioni connesse all'i-esima azione di destinazione.
	 * 
	 * @param i
	 * @return
	 */
	public List<Destinazione> getDestinazioni(int i)
		{
		return this.aggregatoProcessoArriviConsegne.getDestinazioniFromOrdine(i);
		}

	/**
	 * Restituisce la probabilità di routing dell'i-esima azione di consegna.
	 * 
	 * @param i
	 * @return
	 */
	public Double getProbRouting(int i)
		{
		return this.aggregatoProcessoArriviConsegne.getProbRoutingFromOrdine(i);
		}

	public void setAggregatoProcessoArriviConsegne(
			AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne)
		{
		this.aggregatoProcessoArriviConsegne = aggregatoProcessoArriviConsegne;
		}

	/**
	 * Imposta le probabilità di routing associate ad ogni azione di consegna.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setProbsRouting(List<Double> list)
		throws ElementoBaseException
		{
		// se list e aggregatoProcessoArriviConsegne hanno una lunghezza diversa si solleva
		// un'eccezione
		if (list.size() != this.aggregatoProcessoArriviConsegne.size())
			throw new ElementoBaseException("The routing probabilities list and "+
					"the delivery aggregate " +
				" have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			Double double1 = list.get(i);
			DataProcessoArriviConsegna dataProcessoArriviConsegna =
				this.aggregatoProcessoArriviConsegne.get(i);
			dataProcessoArriviConsegna.setProbRouting(double1);
			}
		}

	/**
	 * Imposta i nomi delle azioni di consegna.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setAzioniConsegna(List<String> list)
		throws ElementoBaseException
		{
		// se list e aggregatoProcessoArriviConsegne hanno una lunghezza diversa si solleva
		// un'eccezione
		if (list.size() != this.aggregatoProcessoArriviConsegne.size())
			throw new ElementoBaseException("The delivery actions list and the delivery aggregate" +
				" have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			String string = list.get(i);
			DataProcessoArriviConsegna dataProcessoArriviConsegna =
				this.aggregatoProcessoArriviConsegne.get(i);
			dataProcessoArriviConsegna.setNomeAzioneConsegna(string);
			}
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof ProcessoArrivi))
			return false;
		ProcessoArrivi processoArrivi =
			(ProcessoArrivi)obj;
		if (!(this.getAggregatoProcessoArriviConsegne().equals(
				processoArrivi.getAggregatoProcessoArriviConsegne())))
			return false;
		return super.equals(obj);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string)
		{
		return this.aggregatoProcessoArriviConsegne.getStrutturaInterazioneOutputFromDeliver(string);
		}

	@Override
	public void proporzionaProbabilita()
		throws ElementoBaseException
		{
		this.aggregatoProcessoArriviConsegne.proporzionaProbabilita();
		}

	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.aggregatoProcessoArriviConsegne.replaceDestination(destinazione, list);
		}
	}
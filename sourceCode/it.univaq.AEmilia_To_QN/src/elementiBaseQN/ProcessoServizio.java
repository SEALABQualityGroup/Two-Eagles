package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Strutture.AggregatoProcessoServizioSorgenti;
import elementiBaseQN.Strutture.DataProcessoServizioSorgente;

public class ProcessoServizio extends ElementoBaseQN {
	
	private static final long serialVersionUID = 1L;

	private AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti = new AggregatoProcessoServizioSorgenti();
	private int numeroServers = 1;

	public ProcessoServizio(String nome) 
		{
		super(nome);
		}

	public ProcessoServizio(String nome, AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti, int numeroServers)
		throws ElementoBaseException
		{
		super(nome);
		this.aggregatoProcessoServizioSorgenti = aggregatoProcessoServizioSorgenti;
		}

	public ProcessoServizio() 
		{
		super();
		}

	public AggregatoProcessoServizioSorgenti getAggregatoProcessoServizioSorgenti() 
		{
		return aggregatoProcessoServizioSorgenti;
		}

	public int getNumeroServers() 
		{
		return numeroServers;
		}

	
//	public int getNumeroServers() 
//		{
//		return numeroServers;
//		}
//
//	public void setNumeroServers(int numeroServers) 
//		{
//		this.numeroServers = numeroServers;
//		}
//
//	public HashMap<String, Integer> getPrioritaSelezione() 
//		{
//		return prioritaSelezione;
//		}
//
//	public HashMap<String, Double> getProbabilitaSelezione() 
//		{
//		return probabilitaSelezione;
//		}
//
//	public HashMap<String, Double> getTempiServizio() 
//		{
//		return tempiServizio;
//		}
//
//	public HashMap<String, ElementoBaseQN> getSorgenti() 
//		{
//		return sorgenti;
//		}
//
//	public HashMap<String, HashMap<String, ElementoBaseQN>> getDestinazioni() 
//		{
//		return destinazioni;
//		}
//
//	public HashMap<String, List<ProbRouting>> getProbsRouting() 
//		{
//		return probsRouting;
//		}
//	
//	public HashMap<String, String> getCanaliMap() 
//		{
//		return canali;
//		}
//	
//	public List<String> getCanali()
//		{
//		List<String> list = new ArrayList<String>();
//		Set<Map.Entry<String, String>> set = this.canali.entrySet();
//		for (Map.Entry<String, String> entry : set)
//			{
//			String string2 = entry.getValue();
//			if (string2 != null)
//				list.add(string2);
//			}
//		return list;
//		}
//	
//	public List<ElementoBaseQN> getDestinazioni(int i)
//		{
//		// si preleva l'azione di selezione relativa ad i
//		String string = this.ordineAzioniSelezione.get(i);
//		// si preleva la mappa delle destinazioni relativa a string
//		HashMap<String, ElementoBaseQN> hashMap = this.destinazioni.get(string);
//		Collection<ElementoBaseQN> set = hashMap.values();
//		List<ElementoBaseQN> list = new CopyOnWriteArrayList<ElementoBaseQN>(set);
//		return list;
//		}
//	
//	public Double getTempoServizio(int i)
//		{
//		// si preleva l'azione di selezione relativa ad i
//		String string = this.ordineAzioniSelezione.get(i);
//		// si preleva il tempo di servizio relativo a string
//		Double double1 = this.tempiServizio.get(string);
//		return double1;
//		}
//	
//	public int getNumeroDestinazioni(int i)
//		{
//		// si preleva l'azione di selezione relativa ad i
//		String string = this.ordineAzioniSelezione.get(i);
//		// si preleva la mappa delle destinazioni relative a string
//		HashMap<String, ElementoBaseQN> hashMap = this.destinazioni.get(string);
//		// si contano quelle entry della mappa, che non hanno valore nullo (per costruzione tutte hanno valore
//		// non nullo)
//		Set<Map.Entry<String, ElementoBaseQN>> set = hashMap.entrySet();
//		int j = set.size();
//		return j;
//		}
//	
//	public List<Double> getProbsRouting(int i)
//		{
//		// si preleva l'azione di selezione relativa ad i
//		String string = this.ordineAzioniSelezione.get(i);
//		// prelevo la lista delle probabilità di routing relativa a string
//		List<ProbRouting> list = this.probsRouting.get(string);
//		// costruisco la lista delle probabilità di routing relative a list
//		List<Double> list2 = new ArrayList<Double>();
//		for (ProbRouting probRouting : list)
//			{
//			Double double1 = probRouting.getProbabilita();
//			list2.add(double1);
//			}
//		return list2;
//		}
	
	/**
	 * Restituisce una lista contenente il nome delle classi che il processo di servizio serve.
	 * 
	 */
	public List<String> getClassi() 
		{
		return this.aggregatoProcessoServizioSorgenti.getClassi();
		}
	
	/**
	 * Imposta le sorgenti del centro di servizio.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setSorgenti(List<ElementoBaseQN> list) throws ElementoBaseException 
		{
		aggregatoProcessoServizioSorgenti.setSorgenti(list);
		}
	
	public void setAggregatoProcessoServizioSorgenti(
			AggregatoProcessoServizioSorgenti aggregatoProcessoServizioSorgenti) 
		{
		this.aggregatoProcessoServizioSorgenti = aggregatoProcessoServizioSorgenti;
		}

	public void setNumeroServers(int numeroServers) 
		{
		this.numeroServers = numeroServers;
		}

	/**
	 * Imposta i tempi di servizio di ogni classe di clienti secondo list3.
	 * 
	 * @param list3
	 * @throws ElementoBaseException
	 */
	public void setTempiServizio(List<Double> list3)
		throws ElementoBaseException
		{
		// Si verifica se list3 e aggregatoProcessoServizioSorgenti hanno la stessa lunghezza.
		// Se è no si solleva un'eccezione.
		if (list3.size() != this.aggregatoProcessoServizioSorgenti.size())
			throw new ElementoBaseException("The service times list and the aggregate "+
					"have not same size");
		for (int i = 0; i < list3.size(); i++)
			{
			Double double1 = list3.get(i);
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				this.aggregatoProcessoServizioSorgenti.get(i);
			dataProcessoServizioSorgente.setTempoServizio(double1);
			}
		}
	
	/**
	 * Assegna la lista delle azioni di selezione secondo l'ordine stabilito da list.
	 * 
	 * @param list
	 * @throws ElementoBaseException
	 */
	public void setAzioniSelezione(List<String> list)
		throws ElementoBaseException
		{
		// Si verifica se list e aggregatoProcessoServizioSorgenti hanno la stessa lunghezza.
		// Se è no si solleva un'eccezione.
		if (list.size() != this.aggregatoProcessoServizioSorgenti.size())
			throw new ElementoBaseException("The selection actions list and the aggregate "+
					" have not same size");
		for (int i = 0; i < list.size(); i++)
			{
			String string = list.get(i);
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				this.aggregatoProcessoServizioSorgenti.get(i);
			dataProcessoServizioSorgente.setNomeAzioneSelezione(string);
			}
		}

	/**
	 * Imposta le probabilità di routing secondo la lista di liste di list2.
	 *  
	 * @param list2
	 * @throws ElementoBaseException
	 */
	public void setProbsRouting(List<List<Double>> list2)
		throws ElementoBaseException
		{
		// se list2 e aggregatoProcessoServizioSorgenti non hanno la stessa lunghezza
		// si restituisce una ElementoBaseException
		if (list2.size() != this.aggregatoProcessoServizioSorgenti.size())
			throw new ElementoBaseException("The routing probabilities list and "+
					"the sources aggregate have not same size");
		for (int i = 0; i < list2.size(); i++)
			{
			List<Double> list = list2.get(i);
			DataProcessoServizioSorgente dataProcessoServizioSorgente =
				this.aggregatoProcessoServizioSorgenti.get(i);
			dataProcessoServizioSorgente.setProbsRouting(list);
			}
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoServizio))
			return false;
		ProcessoServizio processoServizio =
			(ProcessoServizio)obj;
		if (!this.getAggregatoProcessoServizioSorgenti().equals(
				processoServizio.getAggregatoProcessoServizioSorgenti()))
			return false;
		if (this.getNumeroServers() != processoServizio.getNumeroServers())
			return false;
		return super.equals(obj);
		}

	/**
	 * Restituisce true se e solo se this e processoServizio2 sono uguali nome a parte.
	 * 
	 * @param processoServizio2
	 * @return
	 */
	public boolean equalsType(ProcessoServizio processoServizio2) 
		{
		if (!this.getAggregatoProcessoServizioSorgenti().equals(
				processoServizio2.getAggregatoProcessoServizioSorgenti()))
			return false;
		return true;
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.aggregatoProcessoServizioSorgenti.getStrutturaFromInput(string);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return this.aggregatoProcessoServizioSorgenti.getStrutturaFromOutput(string);
		}

	/**
	 * Restituisce il numero di sorgenti.
	 * 
	 * @return
	 */
	public int getNumeroSorgenti() 
		{
		return aggregatoProcessoServizioSorgenti.size();
		}

	public void proporzionaProbabilita() throws ElementoBaseException 
		{
		aggregatoProcessoServizioSorgenti.proporzionaProbabilita();
		}
	
	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.aggregatoProcessoServizioSorgenti.replaceDestination(destinazione, list);
		}

	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.aggregatoProcessoServizioSorgenti.replaceSource(elementoBaseQN, list);
		}
	}
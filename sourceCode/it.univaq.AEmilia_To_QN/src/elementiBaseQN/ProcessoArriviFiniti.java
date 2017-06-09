package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoArriviRitorni;

/**
 * Rappresenta un processo di arrivi per una pololazione infinita di clienti e definisce un canale di clienti.
 * Per poter essere istanziata, bisogna fornire una lista delle probabilità di routing delle classi di clienti 
 * e una lista degli elementi
 * base, che rappresentano le possibili destinazioni di ogni cliente. Inoltre, bisogna fornire il tempo di pensiero
 * e la lista dei ritorni possibili di ogni cliente.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ProcessoArriviFiniti extends ProcessoArrivi {

	private static final long serialVersionUID = 1L;

	private double tempoPensiero;
	private int numeroClienti;
	private AggregatoProcessoArriviRitorni aggregatoProcessoArriviRitorni = new AggregatoProcessoArriviRitorni();

	public ProcessoArriviFiniti(String nome, AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne,
			double tempoPensiero, int numeroClienti, AggregatoProcessoArriviRitorni aggregatoProcessoArriviRitorni) 
		throws ElementoBaseException 
		{
		super(nome, aggregatoProcessoArriviConsegne);
		this.tempoPensiero = tempoPensiero;
		this.numeroClienti = numeroClienti;
		this.aggregatoProcessoArriviRitorni = aggregatoProcessoArriviRitorni;
		}

	public ProcessoArriviFiniti() 
		{
		super();
		}

	public ProcessoArriviFiniti(String string) 
		{
		super(string);
		}

	public double getTempoPensiero() 
		{
		return tempoPensiero;
		}

	public int getNumeroClienti() 
		{
		return numeroClienti;
		}

	public AggregatoProcessoArriviRitorni getAggregatoProcessoArriviRitorni() 
		{
		return aggregatoProcessoArriviRitorni;
		}

	/**
	 * Restituisce il numero delle azioni di ritorno presenti nell'elemento base.
	 * 
	 * @return
	 */
	public int getNumeroRitorni() 
		{
		return this.aggregatoProcessoArriviRitorni.size();
		}

	/**
	 * Restituisce l'elemento base attaccato all'azione di ritorno.
	 * 
	 * @param i
	 * @return
	 */
	public ElementoBaseQN getRitorno(int i) 
		{
		return this.aggregatoProcessoArriviRitorni.getRitornoFromOrdine(i);
		}

	public void setTempoPensiero(double tempoPensiero) 
		{
		this.tempoPensiero = tempoPensiero;
		}

	public void setNumeroClienti(int numeroClienti) 
		{
		this.numeroClienti = numeroClienti;
		}

	public void setAggregatoProcessoArriviRitorni
		(AggregatoProcessoArriviRitorni aggregatoProcessoArriviRitorni) 
		{
		this.aggregatoProcessoArriviRitorni = aggregatoProcessoArriviRitorni;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoArriviFiniti))
			return false;
		ProcessoArriviFiniti processoArriviFiniti =
			(ProcessoArriviFiniti)obj;
		if (this.getTempoPensiero() != 
			processoArriviFiniti.getTempoPensiero())
			return false;
		if (this.getNumeroClienti() !=
			processoArriviFiniti.getNumeroClienti())
			return false;
		if (!this.getAggregatoProcessoArriviRitorni().equals(
				processoArriviFiniti.getAggregatoProcessoArriviRitorni()))
			return false;
		return super.equals(obj);
		}
	
	/**
	 * Restituisce true se e solo se this e processoArriviFiniti
	 * sono uguali, nomi a parte.
	 * 
	 * @param processoArriviFiniti
	 * @return
	 */
	public boolean equalsType(ProcessoArriviFiniti processoArriviFiniti)
		{
		if (this.getTempoPensiero() != 
			processoArriviFiniti.getTempoPensiero())
			return false;
		if (!this.getAggregatoProcessoArriviRitorni().equals(
				processoArriviFiniti.getAggregatoProcessoArriviRitorni()))
			return false;
		if (!this.getAggregatoProcessoArriviConsegne().equals(
				processoArriviFiniti.getAggregatoProcessoArriviConsegne()))
			return false;
		return true;
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.aggregatoProcessoArriviRitorni.getStrutturaInterazioneInputFromRitorno(string);
		}

	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.aggregatoProcessoArriviRitorni.replaceSource(elementoBaseQN, list);
		}
	}

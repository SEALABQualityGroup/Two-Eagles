package elementiBaseQN;

import elementiBaseQN.Strutture.AggregatoProcessoArriviConsegne;

/**
 * Rappresenta un processo di arrivi infiniti e definisce un canale di clienti. Per poter essere istanziata,
 * bisogna fornire una lista delle probabilità  di routing del canale di clienti e una lista degli elementi
 * base, che rappresentano le possibili destinazioni di ogni cliente. Inoltre, bisogna fornire anche il tempo
 * di interarrivo di ogni cliente che appartiene al canale.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class ProcessoArriviInfiniti extends ProcessoArrivi 
	{

	private static final long serialVersionUID = 1L;

	private double tempoInterarrivo;

	public ProcessoArriviInfiniti(String nome, AggregatoProcessoArriviConsegne aggregatoProcessoArriviConsegne,
			double tempoInterarrivo)
		throws ElementoBaseException 
		{
		super(nome, aggregatoProcessoArriviConsegne);
		this.tempoInterarrivo = tempoInterarrivo;
		}

	public ProcessoArriviInfiniti() 
		{
		super();
		}

	public ProcessoArriviInfiniti(String string) 
		{
		super(string);
		}

	public double getTempoInterarrivo() 
		{
		return tempoInterarrivo;
		}
	
	public void setTempoInterarrivo(double tempoInterarrivo) 
		{
		this.tempoInterarrivo = tempoInterarrivo;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoArriviInfiniti))
			return false;
		ProcessoArriviInfiniti processoArriviInfiniti =
			(ProcessoArriviInfiniti)obj;
		if (this.getTempoInterarrivo() != 
			processoArriviInfiniti.getTempoInterarrivo())
			return false;
		return super.equals(obj);
		}
	}

/**
 * 
 */
package elementiBaseQN;

import java.util.List;

import mappingAEIintoElementiBase.struttura.StrutturaInterazioneInput;
import mappingAEIintoElementiBase.struttura.StrutturaInterazioneOutput;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingConsegne;
import elementiBaseQN.Strutture.AggregatoProcessoRoutingSorgenti;

/**
 * @author Mirko
 *
 */
public class ProcessoRouting 
	extends ElementoBaseQN 
	{

	private static final long serialVersionUID = 1L;
	
	private AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti = new AggregatoProcessoRoutingSorgenti();
	private AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne = new AggregatoProcessoRoutingConsegne();
	
	private String canale = "";
	
	private Double numberOfVisits;

	public ProcessoRouting() 
		{
		super();
		}

	public ProcessoRouting(String nome, AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti, 
			AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne,
			String canale) 
		throws ElementoBaseException 
		{
		super(nome);
		this.aggregatoProcessoRoutingSorgenti = aggregatoProcessoRoutingSorgenti;
		this.aggregatoProcessoRoutingConsegne = aggregatoProcessoRoutingConsegne;
		this.canale = canale;
		}

	public ProcessoRouting(String string) 
		{
		super(string);
		}

	public AggregatoProcessoRoutingSorgenti getAggregatoProcessoRoutingSorgenti() 
		{
		return aggregatoProcessoRoutingSorgenti;
		}

	public AggregatoProcessoRoutingConsegne getAggregatoProcessoRoutingConsegne() 
		{
		return aggregatoProcessoRoutingConsegne;
		}

	@Override
	public boolean equals(Object obj) 
		{
		if (!(obj instanceof ProcessoRouting))
			return false;
		ProcessoRouting processoRouting =
			(ProcessoRouting)obj;
		if (this.getAggregatoProcessoRoutingConsegne() == null && 
				processoRouting.getAggregatoProcessoRoutingConsegne() != null)
			return false;
		if (this.getAggregatoProcessoRoutingConsegne() != null &&
				processoRouting.getAggregatoProcessoRoutingConsegne() == null)
			return false;
		if (this.getAggregatoProcessoRoutingConsegne() != null &&
				processoRouting.getAggregatoProcessoRoutingConsegne() != null &&
				!this.getAggregatoProcessoRoutingConsegne().equals(
				processoRouting.getAggregatoProcessoRoutingConsegne()))
			return false;
		if (this.getAggregatoProcessoRoutingSorgenti() == null &&
				processoRouting.getAggregatoProcessoRoutingSorgenti() != null)
			return false;
		if (this.getAggregatoProcessoRoutingSorgenti() != null && 
				processoRouting.getAggregatoProcessoRoutingSorgenti() == null)
			return false;
		if (this.getAggregatoProcessoRoutingSorgenti() != null &&
				processoRouting.getAggregatoProcessoRoutingSorgenti() != null &&
				!this.getAggregatoProcessoRoutingSorgenti().equals(
				processoRouting.getAggregatoProcessoRoutingSorgenti()))
			return false;
		if (!this.getCanale().equals(
				processoRouting.getCanale()))
			return false;
		return super.equals(obj);
		}

	@Override
	public StrutturaInterazioneInput getStrutturaInput(String string) 
		{
		return this.aggregatoProcessoRoutingSorgenti.getStrutturaFromInput(string);
		}

	@Override
	public StrutturaInterazioneOutput getStrutturaOutput(String string) 
		{
		return this.aggregatoProcessoRoutingConsegne.getStrutturaFromOutput(string);
		}

	@Override
	public void proporzionaProbabilita() 
		throws ElementoBaseException 
		{
		aggregatoProcessoRoutingConsegne.proporzionaProbabilita();
		}

	@Override
	public void replaceDestination(Destinazione destinazione,
			List<Destinazione> list) 
		{
		this.aggregatoProcessoRoutingConsegne.replaceDestination(destinazione, list);
		}

	@Override
	public void replaceSource(ElementoBaseQN elementoBaseQN,
			List<ElementoBaseQN> list) 
		{
		this.aggregatoProcessoRoutingSorgenti.replaceSource(elementoBaseQN, list);
		}
	
	public String getCanale() 
		{
		return canale;
		}
	
	public List<ElementoBaseQN> getSorgenti() 
		{
		return aggregatoProcessoRoutingSorgenti.getSorgenti();
		}
	
	public void setAggregatoProcessoRoutingSorgenti(
			AggregatoProcessoRoutingSorgenti aggregatoProcessoRoutingSorgenti) 
		{
		this.aggregatoProcessoRoutingSorgenti = aggregatoProcessoRoutingSorgenti;
		}
	
	public void setAggregatoProcessoRoutingConsegne(
			AggregatoProcessoRoutingConsegne aggregatoProcessoRoutingConsegne) 
		{
		this.aggregatoProcessoRoutingConsegne = aggregatoProcessoRoutingConsegne;
		}	
	
	public void setCanale(String canale) 
		{
		this.canale = canale;
		}

	public List<Destinazione> getDestinazioni() 
		{
		return aggregatoProcessoRoutingConsegne.getDestinazioni();
		}

	public boolean verificaNoJobExit() 
		{
		return this.aggregatoProcessoRoutingConsegne.verificaNoJobExit();
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

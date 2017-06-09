package pack.model;

import java.util.List;

public interface IWorkload 
	extends IModelElement  
	{

	/*
	 * Restituisce le richieste di servizio che si riferiscono
	 * a questo carico di lavoro. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.WorkloadDeleteCommand.execute().
	 */
	public List<IRequest> getRequests();

	public String getWorkloadName();

	public List<ITransit> getSourceTransits();

	public void setTargetTransits(List<ITransit> list);

	public IQNM getQNM();
		
	}

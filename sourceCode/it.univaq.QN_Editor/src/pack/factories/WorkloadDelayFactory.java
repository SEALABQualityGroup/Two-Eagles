package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.WorkloadDelayModel;

public class WorkloadDelayFactory 
	implements CreationFactory 
	{

	private ClosedWorkloadModel closedWorkloadModel;
	
	
	public WorkloadDelayFactory(ClosedWorkloadModel closedWorkloadModel) 
		{
		super();
		this.closedWorkloadModel = closedWorkloadModel;
		}

	@Override
	public Object getNewObject() 
		{
		return new WorkloadDelayModel(closedWorkloadModel);
		}

	@Override
	public Object getObjectType() 
		{
		return WorkloadDelayModel.class;
		}	
	}

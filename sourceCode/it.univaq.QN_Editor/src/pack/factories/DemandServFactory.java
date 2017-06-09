package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.IWorkload;
import pack.model.structure.DemandServModel;

public class DemandServFactory implements CreationFactory 
	{
	
	private IWorkload workload;
	
	public DemandServFactory(IWorkload workload) 
		{
		super();
		this.workload = workload;
		}

	@Override
	public Object getNewObject() 
		{
		return new DemandServModel(this.workload);
		}

	@Override
	public Object getObjectType() 
		{
		return DemandServModel.class;
		}
	}

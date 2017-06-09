package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.IWorkload;
import pack.model.structure.WorkUnitServModel;

public class WorkUnitServFactory implements CreationFactory 
	{

	private IWorkload workload;
	
	public WorkUnitServFactory(IWorkload workload) 
		{
		super();
		this.workload = workload;
		}

	@Override
	public Object getNewObject() 
		{
		return new WorkUnitServModel(this.workload);
		}

	@Override
	public Object getObjectType() 
		{
		return WorkUnitServModel.class;
		}
	}

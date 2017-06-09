package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.IWorkload;
import pack.model.structure.TimeServModel;

public class TimeServFactory implements CreationFactory 
	{

	private IWorkload workload;
	
	public TimeServFactory(IWorkload workload) 
		{
		super();
		this.workload = workload;
		}

	@Override
	public Object getNewObject() 
		{
		return new TimeServModel(this.workload);
		}

	@Override
	public Object getObjectType() 
		{
		return TimeServModel.class;
		}
	}

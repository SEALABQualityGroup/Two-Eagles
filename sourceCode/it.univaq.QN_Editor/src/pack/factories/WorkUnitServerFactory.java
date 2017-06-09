package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.WorkUnitServerModel;

public class WorkUnitServerFactory implements CreationFactory 
	{

	@Override
	public Object getNewObject() 
		{
		return new WorkUnitServerModel();
		}

	@Override
	public Object getObjectType() 
		{
		return WorkUnitServerModel.class;
		}
	}

package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.SinkNodeModel;

public class SinkNodeFactory implements CreationFactory 
	{

	@Override
	public Object getNewObject() 
		{
		return new SinkNodeModel();
		}

	@Override
	public Object getObjectType() 
		{
		return SinkNodeModel.class;
		}
	}

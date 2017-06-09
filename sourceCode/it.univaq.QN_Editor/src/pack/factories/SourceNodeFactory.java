package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.SourceNodeModel;

public class SourceNodeFactory implements CreationFactory 
	{
	
	@Override
	public Object getNewObject() 
		{
		return new SourceNodeModel();
		}

	@Override
	public Object getObjectType() 
		{
		return SourceNodeModel.class;
		}
	}

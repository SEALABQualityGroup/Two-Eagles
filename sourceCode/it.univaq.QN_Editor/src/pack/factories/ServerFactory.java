package pack.factories;

import org.eclipse.gef.requests.CreationFactory;

import pack.model.structure.ServerModel;

public class ServerFactory implements CreationFactory {
	
	@Override
	public Object getNewObject() 
		{
		return new ServerModel();
		}

	@Override
	public Object getObjectType() 
		{
		return ServerModel.class;
		}
	}

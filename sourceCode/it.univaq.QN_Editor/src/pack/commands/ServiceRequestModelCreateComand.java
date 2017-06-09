package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.structure.QNMModel;
import pack.model.structure.ServiceRequestModel;

public class ServiceRequestModelCreateComand 
	extends Command 
	{

	private QNMModel parent;
	
	private ServiceRequestModel child;

	public ServiceRequestModelCreateComand(QNMModel parent, ServiceRequestModel child) 
		{
		super();
		this.parent = parent;
		this.child = child;
		}

	@Override
	public void execute() 
		{
		parent.addChild(child);
		}

	@Override
	public void redo() 
		{
		parent.addChild(child);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(child);
		}	
	
	}

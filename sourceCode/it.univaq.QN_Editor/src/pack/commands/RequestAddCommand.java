package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.IRequest;
import pack.model.structure.ServiceRequestModel;

public class RequestAddCommand extends Command 
	{
	
	private IRequest newRequest;
	
	private ServiceRequestModel parent;
	
	/**
	 * Create a command that will add a new IRequest to a ServiceRequestModel.
	 * 
	 */
	public RequestAddCommand(IRequest newRequest, ServiceRequestModel parent) 
		{
		this.newRequest = newRequest;
		this.parent = parent;
		setLabel("request addition");
		}

	@Override
	public void execute() 
		{
		redo();
		}

	@Override
	public void redo() 
		{
		parent.addChild(newRequest);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(newRequest);
		}

	@Override
	public boolean canExecute() 
		{
		return newRequest != null && parent != null;
		}
	
	}

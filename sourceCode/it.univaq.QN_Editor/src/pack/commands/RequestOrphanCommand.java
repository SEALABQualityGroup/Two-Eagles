package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IRequest;
import pack.model.structure.ServiceRequestModel;

public class RequestOrphanCommand extends Command 
	{
	
	/** IRequest to remove. */
	private final IRequest child;

	/** ServiceRequestModel to remove from. */
	private final ServiceRequestModel parent;

	/**
	 * Create a command that will remove the request from its parent.
	 * @param parent the ServiceRequestModel containing the child
	 * @param child    the IRequest to remove
	 */
	public RequestOrphanCommand(ServiceRequestModel parent, IRequest child)
		throws Exception
		{
		if (parent == null || child == null) 
			{
			Finestra.mostraIE("parent is null");
			}
		if (child == null)
			{
			Finestra.mostraIE("child is null");
			}
		setLabel("request deletion");
		this.parent = parent;
		this.child = child;
		}

	@Override
	public void execute() 
		{
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child
		parent.removeChild(child);
		}

	@Override
	public void undo() 
		{
		// add the child
		parent.addChild(child);
		}	
	
	}

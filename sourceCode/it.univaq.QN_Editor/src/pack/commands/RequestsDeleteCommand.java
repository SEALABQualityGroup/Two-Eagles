package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.structure.ServiceRequestModel;

public class RequestsDeleteCommand extends Command 
	{

	/** ServiceRequestModel to remove. */
	protected ServiceRequestModel child;
	
	/** QNM to remove from. */
	protected IQNM parent;
	
	/** Holds a copy of the IRequest of child. */
	private List<IRequest> requests;

	/** True, if child was removed from its parent. */
	private boolean wasRemoved;

	public RequestsDeleteCommand(IQNM parent, ServiceRequestModel child)
		throws Exception
		{
		if (parent == null) 
			{
			Finestra.mostraIE("parent is null");
			}
		if (child == null)
			{
			Finestra.mostraIE("child is null");
			}
		setLabel("ServiceRequestType deletion");
		this.parent = parent;
		this.child = child;
		}
	
	@Override
	public boolean canUndo() 
		{
		return wasRemoved;
		}

	@Override
	public void execute() 
		{
		// store a copy of requests before proceeding 
		requests = child.getChildrenRequest();
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child and disconnect its requests
		wasRemoved = parent.removeChild(child);
		if (wasRemoved) 
			removeRequests(requests);
		}

	private void removeRequests(List<IRequest> requests) 
		{
		for (Iterator<IRequest> iter = requests.iterator(); iter.hasNext();) 
			{
			IRequest request = iter.next();
			child.removeChild(request);
			}
		}

	@Override
	public void undo() 
		{
		// add the child and recreate the requests
		if (parent.addChild(child)) 
			addRequests(requests);
		}

	private void addRequests(List<IRequest> requests) 
		{
		for (Iterator<IRequest> iter = requests.iterator(); iter.hasNext();) 
			{
			IRequest request = iter.next();
			child.addChild(request);
			}
		}
	}

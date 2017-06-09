package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.model.IRequest;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public abstract class WorkloadDeleteCommand extends Command {

	/** IWorkload to remove. */
	protected IWorkload child;
	/** WorkloadModel to remove from. */
	protected WorkloadModel parent;
	/** Holds a copy of the IRequests of child. */
	private List<IRequest> requests;
	/** True, if child was removed from its parent. */
	private boolean wasRemoved;


	@Override
	public boolean canUndo() {
	return wasRemoved;
	}

	@Override
	public void execute() {
	// store a copy of requests before proceeding 
	requests = child.getRequests();
	redo();
	}

	@Override
	public void redo() {
	// remove the child and disconnect its requests
	wasRemoved = parent.removeChild(child);
	if (wasRemoved) 
		removeRequests(requests);
	}

	private void removeRequests(List<IRequest> requests) {
	for (Iterator<IRequest> iter = requests.iterator(); iter.hasNext();) 
		{
		IRequest request = iter.next();
		request.getQNM().removeChild(request);
		}
	}

	@Override
	public void undo() {
	// add the child and recreate the requests
	if (parent.addChild(child)) 
		addRequests(requests);
	}

	private void addRequests(List<IRequest> requests) {
	for (Iterator<IRequest> iter = requests.iterator(); iter.hasNext();) 
		{
		IRequest request = iter.next();
		request.getQNM().addChild(request);
		}
	}

}
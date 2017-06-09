package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.RequestDeleteCommand;
import pack.model.IRequest;
import pack.model.IWorkload;

/**
 * This edit policy enables the removal of a IRequest instance from its container.
 *  
 * @see NodoEditPart#createEditPolicies()
 * @see NodoTreeEditPart#createEditPolicies()
 * @author Mirko
 */
public class RequestDeleteEditPolicy 
	extends ComponentEditPolicy 
	{
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof IWorkload && child instanceof IRequest) 
			{
			try {
				return new RequestDeleteCommand(((IRequest)child).getQNM(), (IRequest) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}	
	}

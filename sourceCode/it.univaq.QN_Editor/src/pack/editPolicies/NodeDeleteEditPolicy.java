package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.NodeDeleteCommand;
import pack.model.INode;
import pack.model.IQNM;

public class NodeDeleteEditPolicy 
	extends ComponentEditPolicy 
	{
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof IQNM && child instanceof INode) 
			{
			try {
				return new NodeDeleteCommand((IQNM) parent, (INode) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}	
	}

package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.NodesDeleteCommand;
import pack.model.IQNM;
import pack.model.structure.NodeModel;

public class NodesDeleteEditPolicy 
	extends ComponentEditPolicy 
	{

	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof IQNM && child instanceof NodeModel) 
			{
			try {
				return new NodesDeleteCommand((IQNM) parent, (NodeModel) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}
	
	}

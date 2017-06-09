package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.ClosedWorkloadDeleteCommand;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.WorkloadModel;

public class ClosedWorkloadDeleteEditPolicy 
	extends ComponentEditPolicy 
	{
	
	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof WorkloadModel && child instanceof ClosedWorkloadModel) 
			{
			try {
				return new ClosedWorkloadDeleteCommand((WorkloadModel) parent, (ClosedWorkloadModel) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}
	
	}

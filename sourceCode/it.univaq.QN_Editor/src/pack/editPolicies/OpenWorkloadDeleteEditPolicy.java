package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.OpenWorkloadDeleteCommand;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadModel;

public class OpenWorkloadDeleteEditPolicy 
	extends ComponentEditPolicy 
	{
	
	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof WorkloadModel && child instanceof OpenWorkloadModel) 
			{
			try {
				return new OpenWorkloadDeleteCommand((WorkloadModel) parent, (OpenWorkloadModel) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}
	
	}

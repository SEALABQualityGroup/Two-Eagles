package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.WorkloadsDeleteCommand;
import pack.model.IQNM;
import pack.model.structure.WorkloadModel;

public class WorkloadsDeleteEditPolicy 
	extends ComponentEditPolicy 
	{

	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof IQNM && child instanceof WorkloadModel) 
			{
			try {
				return new WorkloadsDeleteCommand((IQNM) parent, (WorkloadModel) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}

	}

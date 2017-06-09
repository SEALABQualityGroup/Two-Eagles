package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.RequestsDeleteCommand;
import pack.model.IQNM;
import pack.model.structure.ServiceRequestModel;

public class RequestsDeleteEditPolicy 
	extends ComponentEditPolicy 
	{

	protected Command createDeleteCommand(GroupRequest deleteRequest) 
		{
		Object parent = getHost().getParent().getModel();
		Object child = getHost().getModel();
		if (parent instanceof IQNM && child instanceof ServiceRequestModel) 
			{
			try {
				return new RequestsDeleteCommand((IQNM) parent, (ServiceRequestModel) child);
				} 
			catch (Exception e) 
				{}
			}
		return super.createDeleteCommand(deleteRequest);
		}
	
	}

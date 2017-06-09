package pack.editPolicies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import pack.commands.RequestAddCommand;
import pack.model.IRequest;
import pack.model.structure.ServiceRequestModel;

public class ServiceRequestModelTreeContainerEditPolicy extends
		TreeContainerEditPolicy 
	{

	@Override
	protected Command getAddCommand(ChangeBoundsRequest request) 
		{
		List<?> list = request.getEditParts();
		// per semplicità, se la lista è maggiore di uno si restituisce null
		if (list.size() > 1) return null;
		EditPart editPart = (EditPart)list.get(0);
		Object model = editPart.getModel();
		EditPart editPart2 = getHost();
		Object model2 = editPart2.getModel();
		if (model instanceof IRequest && model2 instanceof ServiceRequestModel) 
			{
			IRequest request2 = (IRequest)model;
			ServiceRequestModel serviceRequestModel = (ServiceRequestModel)model2;
			// return a command that can add a IRequest to a ServiceRequestModel
			return new RequestAddCommand(request2,serviceRequestModel);
			}
		return null;
		}

	@Override
	protected Command getCreateCommand(CreateRequest request) 
		{
		return null;
		}

	@Override
	protected Command getMoveChildrenCommand(ChangeBoundsRequest request) 
		{
		return null;
		}

	}

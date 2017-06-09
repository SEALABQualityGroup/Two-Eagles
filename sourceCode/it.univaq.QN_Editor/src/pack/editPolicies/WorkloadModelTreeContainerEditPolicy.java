package pack.editPolicies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import pack.commands.WorkloadAddCommand;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadModelTreeContainerEditPolicy extends
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
		if (model instanceof IWorkload && model2 instanceof WorkloadModel) 
			{
			IWorkload workload = (IWorkload)model;
			WorkloadModel workloadModel = (WorkloadModel)model2;
			// return a command that can add a IWorkload to a WorkloadModel
			return new WorkloadAddCommand(workload,workloadModel);
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

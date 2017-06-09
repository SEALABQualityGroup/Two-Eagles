package pack.editPolicies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.WorkloadOrphanCommand;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadModelContainerEditPolicy extends ContainerEditPolicy 
	{

	@Override
	protected Command getCreateCommand(CreateRequest request) 
		{
		return null;
		}

	@Override
	protected Command getOrphanChildrenCommand(GroupRequest request) 
		{
		List<?> list = request.getEditParts();
		// per semplicità ci può essere una sola parte di edit che effettua la richiesta
		if (list.size() > 1) return null;
		EditPart editPart = (EditPart)list.get(0);
		Object model = editPart.getModel();
		EditPart editPart2 = getHost();
		Object model2 = editPart2.getModel();
		if (model instanceof IWorkload && model2 instanceof WorkloadModel) 
			{
			IWorkload workload = (IWorkload)model;
			WorkloadModel workloadModel = (WorkloadModel)model2;
			try {
				return new WorkloadOrphanCommand(workloadModel, workload);
				} 
			catch (Exception e) 
				{}
			}
		return super.getOrphanChildrenCommand(request);
		}

	}

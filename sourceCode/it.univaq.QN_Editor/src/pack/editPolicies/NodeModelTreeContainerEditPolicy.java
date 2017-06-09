package pack.editPolicies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import pack.commands.NodeAddCommand;
import pack.model.INode;
import pack.model.structure.NodeModel;

public class NodeModelTreeContainerEditPolicy extends TreeContainerEditPolicy 
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
		if (model instanceof INode && model2 instanceof NodeModel) 
			{
			INode node = (INode)model;
			NodeModel nodeModel = (NodeModel)model2;
			// return a command that can add a INode to a NodeModel
			return new NodeAddCommand(node,nodeModel);
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

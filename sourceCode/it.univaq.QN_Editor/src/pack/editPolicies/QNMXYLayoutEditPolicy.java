package pack.editPolicies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import pack.commands.NodeCreateCommand;
import pack.commands.NodeSetConstraintCommand;
import pack.editParts.QNMTypeEditPart;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.structure.ServerModel;
import pack.model.structure.SinkNodeModel;
import pack.model.structure.SourceNodeModel;
import pack.model.structure.WorkUnitServerModel;

public class QNMXYLayoutEditPolicy 
	extends XYLayoutEditPolicy 
	{
	/* (non-Javadoc)
	 * @see ConstrainedLayoutEditPolicy#createChangeConstraintCommand(ChangeBoundsRequest, EditPart, Object)
	 */
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) 
		{
		Object object = child.getModel();
		// Rectangle definisce i limiti della figura
		if ((object instanceof INode) 
				&& constraint instanceof Rectangle) 
			{
			// return a command that can move a INode
			try {
				return new NodeSetConstraintCommand((INode)object, 
						request, (Rectangle) constraint);
				} 
			catch (Exception e) 
				{}
			}
		return super.createChangeConstraintCommand(request, child, constraint);
		}
	
	/* (non-Javadoc)
	 * @see ConstrainedLayoutEditPolicy#createChangeConstraintCommand(EditPart, Object)
	 */
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) 
		{
		// not used in this example
		return null;
		}
	
	/* (non-Javadoc)
	 * @see LayoutEditPolicy#getCreateCommand(CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) 
		{
		Object childClass = request.getNewObjectType();
		if (childClass == ServerModel.class || childClass == SinkNodeModel.class ||
				childClass == SourceNodeModel.class || childClass == WorkUnitServerModel.class) 
			{
			// return a command that can add a INode to a QNMType
			// si imposta l'iqnm per il nodo creato
			EditPart editPart = this.getHost();
			// per precondizione editPart è un QNMTypeEditPart
			QNMTypeEditPart typeEditPart = (QNMTypeEditPart)editPart;
			Object object = typeEditPart.getModel();
			// per precondizione object è un IQNM
			IQNM iqnm = (IQNM)object;
			Object object2 = request.getNewObject();
			// per precondizione object2 è un INode
			INode node = (INode)object2;
			node.setQNM(iqnm);
			return new NodeCreateCommand((INode)request.getNewObject(), 
					(IQNM)getHost().getModel(), (Rectangle)getConstraintFor(request));
			}
		return null;
		}
	}

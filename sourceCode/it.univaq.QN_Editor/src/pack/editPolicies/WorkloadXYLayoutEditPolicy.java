package pack.editPolicies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import pack.commands.RequestCreateCommand;
import pack.commands.RequestSetConstraintCommand;
import pack.editParts.ClosedWorkloadTypeEditPart;
import pack.editParts.OpenWorkloadTypeEditPart;
import pack.model.IRequest;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;
import pack.model.structure.WorkloadDelayModel;
import pack.model.structure.WorkloadSinkModel;
import pack.model.structure.WorkloadSourceModel;

public class WorkloadXYLayoutEditPolicy 
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
		if (object instanceof IRequest && constraint instanceof Rectangle) 
			{
			// return a command that can move a IRequest
			try {
				return new RequestSetConstraintCommand((IRequest)object, 
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
		// bisogna gestire il caso in cui la richiesta di servizio sia relativaad un nodo di delay
		// un nodo source o sink
		if (childClass == DemandServModel.class || childClass == TimeServModel.class ||
				childClass == WorkUnitServModel.class) 
			{			
			// return a command that can add a IRequest
			return new RequestCreateCommand((IRequest)request.getNewObject(), 
					(Rectangle)getConstraintFor(request));
			}
		if (childClass == WorkloadDelayModel.class)
			{
			EditPart editPart = this.getHost();
			if (editPart instanceof ClosedWorkloadTypeEditPart)
				{
				ClosedWorkloadTypeEditPart closedWorkloadTypeEditPart = 
					(ClosedWorkloadTypeEditPart)editPart;
				ClosedWorkloadModel closedWorkloadModel = 
					(ClosedWorkloadModel)closedWorkloadTypeEditPart.getModel();
				if (closedWorkloadModel.hasThinkDevice())
					return new RequestCreateCommand((IRequest)request.getNewObject(), 
					(Rectangle)getConstraintFor(request));
				else
					// non si restituisce nessun comando se già c'è un nodo di delay
					return null;
				}
			}
		if (childClass == WorkloadSinkModel.class)
			{
			EditPart editPart = this.getHost();
			if (editPart instanceof OpenWorkloadTypeEditPart)
				{
				OpenWorkloadTypeEditPart openWorkloadTypeEditPart = 
					(OpenWorkloadTypeEditPart)editPart;
				OpenWorkloadModel openWorkloadModel = 
					(OpenWorkloadModel)openWorkloadTypeEditPart.getModel();
				if (openWorkloadModel.hasDepartsAt())
					return new RequestCreateCommand((IRequest)request.getNewObject(), 
							(Rectangle)getConstraintFor(request));
				else
					// non si restituisce nessun comando se già c'è un nodo sink
					return null;
				}
			}
		if (childClass == WorkloadSourceModel.class)
			{
			EditPart editPart = this.getHost();
			if (editPart instanceof OpenWorkloadTypeEditPart)
				{
				OpenWorkloadTypeEditPart openWorkloadTypeEditPart = 
					(OpenWorkloadTypeEditPart)editPart;
				OpenWorkloadModel openWorkloadModel = 
					(OpenWorkloadModel)openWorkloadTypeEditPart.getModel();
				if (openWorkloadModel.hasArrivesAt())
					return new RequestCreateCommand((IRequest)request.getNewObject(), 
						(Rectangle)getConstraintFor(request));
				else
					// non si restituisce nessun comando se già c'è un nodo source
					return null;
				}
			}
		return null;
		}
	}

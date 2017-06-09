package pack.factories;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractEditPart;

import pack.editParts.ClosedWorkloadTypeTreeEditPart;
import pack.editParts.DemandServTypeTreeEditPart;
import pack.editParts.OpenWorkloadTypeTreeEditPart;
import pack.editParts.TimeServTypeTreeEditPart;
import pack.editParts.WorkUnitServTypeTreeEditPart;
import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;

public class WorkloadTreeEditPartFactory 
	implements EditPartFactory 
	{

	private IQNM type;
	
	public WorkloadTreeEditPartFactory(IQNM type) 
		{
		super();
		this.type = type;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) 
		{
		if (model instanceof ClosedWorkloadModel)
			{
			AbstractEditPart part = new ClosedWorkloadTypeTreeEditPart(type);
			part.setModel(model);
			return part;
			}
		if (model instanceof OpenWorkloadModel)
			{
			AbstractEditPart part = new OpenWorkloadTypeTreeEditPart(type);
			part.setModel(model);
			return part;
			}
		if (model instanceof DemandServModel)
			{
			AbstractEditPart part = new DemandServTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof TimeServModel)
			{
			AbstractEditPart part = new TimeServTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof WorkUnitServModel)
			{
			AbstractEditPart part = new WorkUnitServTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		else
			{
			try {
				Finestra.mostraIE("unexpected model");
				} 
			catch (Exception e) 
				{}
			return null;
			}
		}
	}

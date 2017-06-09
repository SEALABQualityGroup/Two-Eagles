package pack.factories;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import pack.editParts.ClosedWorkloadTypeEditPart;
import pack.editParts.DemandServTypeEditPart;
import pack.editParts.OpenWorkloadTypeEditPart;
import pack.editParts.TimeServTypeEditPart;
import pack.editParts.TransitTypeEditPart;
import pack.editParts.WorkUnitServTypeEditPart;
import pack.editParts.WorkloadDelayEditPart;
import pack.editParts.WorkloadSinkEditPart;
import pack.editParts.WorkloadSourceEditPart;
import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.ITransit;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;
import pack.model.structure.WorkloadDelayModel;
import pack.model.structure.WorkloadSinkModel;
import pack.model.structure.WorkloadSourceModel;

public class WorkloadEditPartFactory 
	implements EditPartFactory 
	{

	private IQNM type;
	
	public WorkloadEditPartFactory(IQNM type) 
		{
		super();
		this.type = type;
		}

	public EditPart createEditPart(EditPart context, Object modelElement) 
		{
		EditPart part = getPartForElement(context, modelElement);
		if (part != null) part.setModel(modelElement);
		return part;
		}

	/**
	 * Maps an object to an EditPart. 
	 * @throws RuntimeException if no match was found (programming error)
	 */
	private EditPart getPartForElement(EditPart editPart, Object modelElement) 
		{
		if (modelElement instanceof OpenWorkloadModel)
			{
			return new OpenWorkloadTypeEditPart(type);
			}
		if (modelElement instanceof ClosedWorkloadModel)
			{
			return new ClosedWorkloadTypeEditPart(type);
			}
		if (modelElement instanceof TimeServModel)
			{
			return new TimeServTypeEditPart();
			}
		if (modelElement instanceof DemandServModel)
			{
			return new DemandServTypeEditPart();
			}
		if (modelElement instanceof WorkUnitServModel)
			{
			return new WorkUnitServTypeEditPart();
			}
		if (modelElement instanceof ITransit)
			{
			return new TransitTypeEditPart();
			}
		if (modelElement instanceof WorkloadDelayModel)
			{
			return new WorkloadDelayEditPart();
			}
		if (modelElement instanceof WorkloadSourceModel)
			{
			return new WorkloadSourceEditPart();
			}
		if (modelElement instanceof WorkloadSinkModel)
			{
			return new WorkloadSinkEditPart();
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

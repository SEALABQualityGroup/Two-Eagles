package pack.factories;


import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;

import pack.editParts.ClosedWorkloadTypeItemEditPart;
import pack.editParts.DemandServTypeTreeEditPart;
import pack.editParts.NodeTypeTreeEditPart;
import pack.editParts.OpenWorkloadTypeItemEditPart;
import pack.editParts.QNMTypeTreeEditPart;
import pack.editParts.ServerTypeTreeEditPart;
import pack.editParts.ServiceRequestTypeTreeEditPart;
import pack.editParts.SinkNodeTypeTreeEditPart;
import pack.editParts.SourceNodeTypeTreeEditPart;
import pack.editParts.TimeServTypeTreeEditPart;
import pack.editParts.WorkUnitServTypeTreeEditPart;
import pack.editParts.WorkUnitServerTypeTreeEditPart;
import pack.editParts.WorkloadTypeTreeEditPart;
import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.NodeModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.ServerModel;
import pack.model.structure.ServiceRequestModel;
import pack.model.structure.SinkNodeModel;
import pack.model.structure.SourceNodeModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;
import pack.model.structure.WorkUnitServerModel;
import pack.model.structure.WorkloadModel;


/**
 * Factory that maps pack.model elements to TreeEditParts.
 * TreeEditParts are used in the outline view of the PMIFEditor.
 * @author Mirko
 */
public class QNMTreeEditPartFactory 
	implements EditPartFactory 
	{
	
	private IQNM iqnm;
	
	private ActionRegistry actionRegistry;
	
	public QNMTreeEditPartFactory(IQNM iqnm, ActionRegistry actionRegistry) 
		{
		super();
		this.iqnm = iqnm;
		this.actionRegistry = actionRegistry;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) 
		{
		if (model instanceof IQNM) 
			{
			AbstractEditPart part = new QNMTypeTreeEditPart(this.actionRegistry);
			part.setModel(model);
			return part;
			}
		if (model instanceof ServiceRequestModel)
			{
			AbstractEditPart part = new ServiceRequestTypeTreeEditPart(iqnm);
			part.setModel(model);
			return part;
			}
		if (model instanceof WorkloadModel)
			{
			AbstractEditPart part = new WorkloadTypeTreeEditPart(this.actionRegistry);
			part.setModel(model);
			return part;
			}
		if (model instanceof NodeModel)
			{
			AbstractEditPart part = new NodeTypeTreeEditPart();
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
		if (model instanceof ServerModel)
			{
			AbstractEditPart part = new ServerTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof SinkNodeModel)
			{
			AbstractEditPart part = new SinkNodeTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof SourceNodeModel)
			{
			AbstractEditPart part = new SourceNodeTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof WorkUnitServerModel)
			{
			AbstractEditPart part = new WorkUnitServerTypeTreeEditPart();
			part.setModel(model);
			return part;
			}
		if (model instanceof ClosedWorkloadModel)
			{
			AbstractEditPart part = new ClosedWorkloadTypeItemEditPart(this.actionRegistry);
			part.setModel(model);
			return part;
			}
		if (model instanceof OpenWorkloadModel)
			{
			AbstractEditPart part = new OpenWorkloadTypeItemEditPart(this.actionRegistry);
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
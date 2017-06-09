package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

import pack.editPolicies.WorkloadXYLayoutEditPolicy;
import pack.model.IModelElement;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.WorkloadDelayModel;

public class ClosedWorkloadTypeEditPart 
	extends AbstractGraphicalEditPart
	implements PropertyChangeListener
	{

	private IQNM type;
	public ClosedWorkloadTypeEditPart(IQNM type) 
		{
		super();
		this.type = type;
		}

	@Override
	protected IFigure createFigure() 
		{
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(3));
		f.setLayoutManager(new FreeformLayout());
		// Create the static router for the connection layer
		ConnectionLayer connLayer = (ConnectionLayer)getLayer(LayerConstants.CONNECTION_LAYER);
		connLayer.setConnectionRouter(new BendpointConnectionRouter());
		return f;
		}

	@Override
	protected void createEditPolicies() 
		{
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes moving of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new WorkloadXYLayoutEditPolicy());
		// La chiamata seguente al metodo installEditPolicy rimuove una politica.
		// Questo serve a prevenire la parte radice dal fornire feedback di selezione 
		// quando l'utente clicca sull'area del diagramma che corrisponde alla radice del modello.
		// installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		}

	@Override
	protected List<IRequest> getModelChildren() 
		{
		String string = null;
		string = getCastedModel().getWorkloadName();
		List<IRequest> list = type.getChildrenRequest(string); 
		// se il carico di lavoro ha un dispositivo di pensamento
		// viene aggiunto anche tale elemento alla lista dei figli
		if (getCastedModel().hasThinkDevice())
			{
			WorkloadDelayModel workloadDelayModel = getCastedModel().getDelay();
			list.add(workloadDelayModel);
			}
		return list;
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// Gli eventi notificati dal modello sono:
		// 1) inserimento di una richiesta
		// (firePropertyChange(REQUEST_ADDED_PROP, null, newRequest);)
		// 2) cancellazione di una richiesta 
		// (firePropertyChange(REQUEST_REMOVED_PROP, null, child);)
		// 3) cambio del carico di lavoro di una richiesta di servizio
		// (firePropertyChange(WORKLOAD, null, value);)
		// 4) inserimento di un centro di delay
		// (firePropertyChange(ClosedWorkloadModel.DELAY_ADDED_PROP, null, newRequest);)
		// 5) rimozione di un centro di delay
		// (firePropertyChange(ClosedWorkloadModel.DELAY_REMOVED_PROP, null, child);)
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.REQUEST_REMOVED_PROP))
			refreshChildren();
		else if (string.equals(IQNM.REQUEST_ADDED_PROP))
			refreshChildren();
		else if (string.equals(ClosedWorkloadModel.DELAY_REMOVED_PROP))
			{
			ClosedWorkloadModel closedWorkloadModel = getCastedModel();
			closedWorkloadModel.setDelayModel(null);
			refreshChildren();
			}
		else if (string.equals(ClosedWorkloadModel.DELAY_ADDED_PROP))
			{
			ClosedWorkloadModel closedWorkloadModel = getCastedModel();
			WorkloadDelayModel workloadDelayModel = (WorkloadDelayModel)arg0.getNewValue();
			closedWorkloadModel.setDelayModel(workloadDelayModel);
			refreshChildren();
			}
		else if (string.equals(IModelElement.WORKLOAD))
			refreshChildren();
		}

	@Override
	public void deactivate() 
		{
		if (isActive()) 
			{
			super.deactivate();
			((IModelElement) getModel()).removePropertyChangeListener(this);
			// si rimuove la parte di edit dagli ascolatori di IQNM
			this.type.removePropertyChangeListener(this);
			}
		}

	@Override
	public void activate() 
		{
		if (!isActive()) 
			{
			super.activate();
			((IModelElement) getModel()).addPropertyChangeListener(this);
			// si ascolta anche i cambiamenti al IQNM
			this.type.addPropertyChangeListener(this);
			}
		}

	private ClosedWorkloadModel getCastedModel() 
		{
		return (ClosedWorkloadModel)getModel();
		}	
	}

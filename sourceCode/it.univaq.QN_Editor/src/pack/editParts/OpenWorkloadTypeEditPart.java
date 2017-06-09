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
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadSinkModel;
import pack.model.structure.WorkloadSourceModel;

public class OpenWorkloadTypeEditPart 
	extends AbstractGraphicalEditPart
	implements PropertyChangeListener
	{

	private IQNM type;
	
	public OpenWorkloadTypeEditPart(IQNM type) 
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

	private OpenWorkloadModel getCastedModel() 
		{
		return (OpenWorkloadModel)getModel();
		}

	@Override
	protected List<IRequest> getModelChildren() 
		{
		String string = null;
		string = getCastedModel().getWorkloadName();
		List<IRequest> list = type.getChildrenRequest(string); 
		// se il carico di lavoro ha il nodo source lo si aggiunge alla lista
		if (getCastedModel().hasArrivesAt())
			{
			list.add(getCastedModel().getWorkloadSourceModel());
			}
		// se il carico di lavoro ha il nodo sink lo si aggiunge alla lista
		if (getCastedModel().hasDepartsAt())
			{
			list.add(getCastedModel().getWorkloadSinkModel());
			}
		return list;
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
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// Gli eventi notificati dal modello sono:
		// 1) inserimento di una richiesta
		// (firePropertyChange(REQUEST_ADDED_PROP, null, newRequest);)
		// 2) cancellazione di una richiesta 
		// (firePropertyChange(REQUEST_REMOVED_PROP, null, child);)
		// 3) cambio del carico di lavoro di una richiesta di servizio
		// (firePropertyChange(WORKLOAD, null, value);)
		// 4) inserimento del nodo sorgente per il carico di lavoro
		// (firePropertyChange(OpenWorkloadModel.SOURCE_ADDED_PROP, null, newRequest);)
		// 5) inserimento del nodo pozzo per il carico di lavoro
		// (firePropertyChange(OpenWorkloadModel.SINK_ADDED_PROP, null, newRequest);)
		// 6) cancellazione del nodo sorgente per il carico di lavoro
		// (firePropertyChange(OpenWorkloadModel.SOURCE_REMOVED_PROP, null, child);)
		// 7) cancellazione del nodo pozzo per il carico di lavoro
		// (firePropertyChange(OpenWorkloadModel.SINK_REMOVED_PROP, null, child);)
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.REQUEST_REMOVED_PROP))
			refreshChildren();
		else if (string.equals(IQNM.REQUEST_ADDED_PROP))
			refreshChildren();
		else if (string.equals(OpenWorkloadModel.SOURCE_REMOVED_PROP))
			{
			OpenWorkloadModel openWorkloadModel = getCastedModel();
			openWorkloadModel.setSourceModel(null);
			refreshChildren();
			}
		else if (string.equals(OpenWorkloadModel.SINK_REMOVED_PROP))
			{
			OpenWorkloadModel openWorkloadModel = getCastedModel();
			openWorkloadModel.setSinkModel(null);
			refreshChildren();
			}
		else if (string.equals(OpenWorkloadModel.SOURCE_ADDED_PROP))
			{
			OpenWorkloadModel openWorkloadModel = getCastedModel();
			WorkloadSourceModel workloadSourceModel = (WorkloadSourceModel)arg0.getNewValue();
			openWorkloadModel.setSourceModel(workloadSourceModel);
			refreshChildren();
			}
		else if (string.equals(OpenWorkloadModel.SINK_ADDED_PROP))
			{
			OpenWorkloadModel openWorkloadModel = getCastedModel();
			WorkloadSinkModel workloadSinkModel = (WorkloadSinkModel)arg0.getNewValue();
			openWorkloadModel.setSinkModel(workloadSinkModel);
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
	
	}

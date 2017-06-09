package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.graphics.Image;

import pack.Utility;
import pack.editPolicies.RequestDeleteEditPolicy;
import pack.editPolicies.RequestTransitEditPolicy;
import pack.errorManagement.Finestra;
import pack.figures.Etichetta;
import pack.model.IModelElement;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadSinkModel;

public class WorkloadSinkEditPart extends AbstractGraphicalEditPart implements
		NodeEditPart, PropertyChangeListener {

	private ConnectionAnchor anchor;
	
	@Override
	protected IFigure createFigure() 
		{
		IFigure f = createFigureForModel();
		f.setOpaque(true); // non-transparent figure
		f.setBackgroundColor(ColorConstants.white);
		return f;
		}

	private IFigure createFigureForModel() 
		{
		// utilizzo una org.eclipse.draw2d.Label
		// per associare un nome
		// ad una figura
		if (getModel() instanceof WorkloadSinkModel) 
			{
			WorkloadSinkModel workloadSinkModel = getCastedModel();
			Image image = Utility.createImage("icons/Queueing Network Sink.bmp");
			Etichetta label = null;
			label = new Etichetta(image);
			label.impostaEtichetta(workloadSinkModel.getServerID());
			getCastedModel().setEtichetta(label);
			return label;
			} 
		else 
			{
			// if Node gets extended the conditions above must be updated
			try {
				Finestra.mostraIE("bad model");
				} 
			catch (Exception e) 
				{}
			return null;
			}
		}

	@Override
	protected void createEditPolicies() 
		{
		// allow removal of the associated pack.model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RequestDeleteEditPolicy());
		// allow the creation of connections and 
		// and the reconnection of connections between instances IRequest
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new RequestTransitEditPolicy());
		}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) 
		{
		return getConnectionAnchor();
		}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) 
		{
		return getConnectionAnchor();
		}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) 
		{
		return getConnectionAnchor();
		}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) 
		{
		return getConnectionAnchor();
		}

	@Override
	public void propertyChange(PropertyChangeEvent evt) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) cambio di posizione
		// firePropertyChange(LOCATION_PROP, null, location);
		// 2) cambio di nome del server
		// firePropertyChange(OpenWorkloadType.SINK_NAME, null, value);
		// 3) inserimento di un transit diretto al nodo pozzo
		// firePropertyChange(TARGET_TRANSIT_PROP, null, value);
		// 4) cancellazione di un transit diretto al nodo pozzo
		// firePropertyChange(TARGET_TRANSIT_PROP, null, value);
		String string = evt.getPropertyName();
		if (string.equals(IRequest.LOCATION_PROP))
			refreshVisuals();
		else if (IRequest.TARGET_TRANSIT_PROP.equals(string))
			refreshTargetConnections();
		else if (string.equals(OpenWorkloadModel.SINK_NAME))
			{
			refreshName();
			refreshVisuals();
			}
		}
	
	@Override
	protected void refreshVisuals() 
		{
		// si riflettono i cambi di posizione del centro di ritardo
		Rectangle bounds = new Rectangle(getCastedModel().getLocation(),
				getCastedModel().getEtichetta().getSize());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);		
		}

	private WorkloadSinkModel getCastedModel() 
		{
		return (WorkloadSinkModel)getModel();
		}
	
	@Override
	protected List<ITransit> getModelSourceConnections() 
		{
		List<ITransit> list = getCastedModel().getSourceTransits();
		return list;
		}

	@Override
	protected List<ITransit> getModelTargetConnections() 
		{
		List<ITransit> list = getCastedModel().getTargetTransits();
		return list;
		}
	
	private ConnectionAnchor getConnectionAnchor() 
		{
		if (anchor == null) 
			{
			if (getModel() instanceof WorkloadSinkModel)
				anchor = new ChopboxAnchor(getFigure());
			else
				{
				// if Nodo gets extended the conditions above must be updated
				try {
					Finestra.mostraIE("unexpected model");
					} 
				catch (Exception e) 
					{}
				}
			}
		return anchor;
		}
	
	@Override
	public void deactivate() 
		{
		if (isActive()) 
			{
			super.deactivate();
			((IModelElement) getModel()).removePropertyChangeListener(this);
			}
		}

	@Override
	public void activate() 
		{
		if (!isActive()) 
			{
			super.activate();
			((IModelElement) getModel()).addPropertyChangeListener(this);
			}
		}
	
	private void refreshName() 
		{
		WorkloadSinkModel workloadSinkModel = getCastedModel();
		String string;
		string = workloadSinkModel.getServerID();
		Etichetta label = (Etichetta)getFigure();
		label.impostaEtichetta(string);
		}
	}

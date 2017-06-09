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
import pack.model.structure.WorkUnitServModel;

public class WorkUnitServTypeEditPart 
	extends AbstractGraphicalEditPart
	implements NodeEditPart, PropertyChangeListener
	{
	
	private ConnectionAnchor anchor;
	
	public WorkUnitServTypeEditPart() 
		{
		super();
		}

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
		if (getModel() instanceof WorkUnitServModel) 
			{
			WorkUnitServModel workUnitServModel = (WorkUnitServModel)getModel();
			Image image = Utility.createImage("icons/WorkUnitServiceRequest_32X32.bmp");
			Etichetta label = null;
			label = new Etichetta(image);
			label.impostaEtichetta(workUnitServModel.getServerID());
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
	protected void refreshVisuals() 
		{
		// notify parent container of changed position & location
		// if this line is removed, the XYLayoutManager used by the parent container 
		// (the Figure of the OpenWorkloadEditPart or ClosedWorkloadEditPart), 
		// will not know the bounds of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(getCastedModel().getLocation(),
				getCastedModel().getEtichetta().getSize());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);		
		}

	private IRequest getCastedModel() 
		{
		return (IRequest) getModel();
		}
	
	protected List<ITransit> getModelSourceConnections() 
		{
		List<ITransit> list = getCastedModel().getSourceTransits();
		return list; 
		}
	
	protected List<ITransit> getModelTargetConnections()
		{
		List<ITransit> list = getCastedModel().getTargetTransits();
		return list;
		}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) 
		{
		return getConnectionAnchor();
		}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getSourceConnectionAnchor(Request request) 
		{
		return getConnectionAnchor();
		}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) 
		{
		return getConnectionAnchor();
		}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
	 */
	public ConnectionAnchor getTargetConnectionAnchor(Request request) 
		{
		return getConnectionAnchor();
		}

	private ConnectionAnchor getConnectionAnchor() 
		{
		if (anchor == null) 
			{
			if (getModel() instanceof WorkUnitServModel)
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
	protected void createEditPolicies() 
		{
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RequestDeleteEditPolicy());
		// allow the creation of transit and 
		// and the reconnection of transit between instances IRequest
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new RequestTransitEditPolicy());
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un transit al server come sorgente
		// firePropertyChange(SOURCE_TRANSIT_PROP, null, transitType);
		// 2) cancellazione di un transit dal server come sorgente
		// firePropertyChange(SOURCE_TRANSIT_PROP, null, transitType);
		// 3) cambio di posizione
		// firePropertyChange(LOCATION_PROP, null, location);
		// 4) cambio di nome del server
		// firePropertyChange(IRequest.NAME, null, value);
		// 5) inserimento di un transit diretto al server
		// firePropertyChange(TARGET_TRANSIT_PROP, null, transitType);
		// 6) cancellazione di un transit diretto al server
		// firePropertyChange(TARGET_TRANSIT_PROP, null, transitType);
		String string = arg0.getPropertyName();
		if (IRequest.SOURCE_TRANSIT_PROP.equals(string))
			refreshSourceConnections();
		else if (IRequest.TARGET_TRANSIT_PROP.equals(string))
			refreshTargetConnections();
		else if (string.equals(IRequest.LOCATION_PROP))
			refreshVisuals();
		else if (string.equals(IRequest.NAME))
			{
			refreshName();
			refreshVisuals();
			}
		}

	private void refreshName() 
		{
		IRequest request = getCastedModel();
		String string;
		string = request.getServerID();
		Etichetta label = (Etichetta)getFigure();
		label.impostaEtichetta(string);
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
	}

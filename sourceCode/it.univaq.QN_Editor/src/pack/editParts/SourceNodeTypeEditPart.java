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
import pack.editPolicies.NodeArcEditPolicy;
import pack.editPolicies.NodeDeleteEditPolicy;
import pack.errorManagement.Finestra;
import pack.figures.Etichetta;
import pack.model.IArc;
import pack.model.IModelElement;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.structure.SourceNodeModel;

public class SourceNodeTypeEditPart 
	extends AbstractGraphicalEditPart 
	implements NodeEditPart, PropertyChangeListener
	{

	private ConnectionAnchor anchor;
	
	private IQNM type;
	
	public SourceNodeTypeEditPart(IQNM type) 
		{
		super();
		this.type = type;
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
		if (getModel() instanceof SourceNodeModel) 
			{
			SourceNodeModel sourceNodeModel = (SourceNodeModel)getModel();
			Image image = Utility.createImage("icons/Queueing Network Source.bmp");
			Etichetta label = null;
			label = new Etichetta(image);
			label.impostaEtichetta(sourceNodeModel.getServerName());
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
		// (the Figure of the QNMType), 
		// will not know the bounds of this figure
		// and will not draw it correctly.
		Rectangle bounds = new Rectangle(getCastedModel().getLocation(),
				getCastedModel().getEtichetta().getSize());
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), bounds);		
		}
	
	private SourceNodeModel getCastedModel() 
		{
		return (SourceNodeModel) getModel();
		}

	protected List<IArc> getModelSourceConnections() 
		{
		List<IArc> list = getCastedModel().getSourceArcs();
		return list;
		}

	protected List<IArc> getModelTargetConnections() 
		{
		List<IArc> list = getCastedModel().getTargetArcs();
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
			if (getModel() instanceof SourceNodeModel)
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeDeleteEditPolicy());
		// allow the creation of arcs and 
		// and the reconnection of arcs between instances INode
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new NodeArcEditPolicy(this.type));
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un elemento arc come sorgente
		// firePropertyChange(SOURCE_ARCS_PROP, null, arcType);
		// 2) cancellazione di un elemento arc come sorgente
		// firePropertyChange(INode.SOURCE_ARCS_PROP, null, arcType);
		// 3) cambio di posizione
		// firePropertyChange(LOCATION_PROP, null, location);
		// 4) cambio di nome
		// firePropertyChange(NAME, null, value);
		// 5) inserimento di un elemento arc come destinazione
		// firePropertyChange(TARGET_ARCS_PROP, null, arcType);
		// 6) cancellazione di un elemento arc come destinazione
		// firePropertyChange(TARGET_ARCS_PROP, null, arcType);
		String string = arg0.getPropertyName();
		if (string.equals(INode.SOURCE_ARCS_PROP))
			refreshSourceConnections();
		else if (INode.TARGET_ARCS_PROP.equals(string))
			refreshTargetConnections();
		else if (string.equals(INode.LOCATION_PROP))
			refreshVisuals();
		else if (string.equals(INode.NAME))
			{
			refreshName();
			refreshVisuals();
			}
		}

	private void refreshName() 
		{
		INode node = getCastedModel();
		String string;
		string = node.getServerName();
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
//			// si ascolta anche i cambiamenti per IQNM per l'editing delle connessioni
//			this.type.removePropertyChangeListener(this);
			}
		}

	@Override
	public void activate() 
		{
		if (!isActive()) 
			{
			super.activate();
			((IModelElement) getModel()).addPropertyChangeListener(this);
//			// si ascolta anche i cambiamenti per IQNM per l'editing delle connessioni
//			this.type.addPropertyChangeListener(this);
			}
		}
	}

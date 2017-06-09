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

import pack.editPolicies.QNMXYLayoutEditPolicy;
import pack.model.IModelElement;
import pack.model.INode;
import pack.model.IQNM;

public class QNMTypeEditPart 
	extends AbstractGraphicalEditPart
	implements PropertyChangeListener
	{

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
	protected List<INode> getModelChildren() 
		{
		return getCastedModel().getChildrenNodes(); // return a list of NodeType
		}


	private IQNM getCastedModel() 
		{
		return (IQNM)getModel();
		}


	@Override
	protected void createEditPolicies() 
		{
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// handles constraint changes moving of model elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new QNMXYLayoutEditPolicy());
		// La chiamata seguente al metodo installEditPolicy rimuove una politica.
		// Questo serve a prevenire la parte radice dal fornire feedback di selezione 
		// quando l'utente clicca sull'area del diagramma che corrisponde alla radice del modello.
		// installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		}


	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un elemento INode
		// firePropertyChange(NODE_ADDED_PROP, null, newNode);
		// 2) cancellazione di un elemento INode
		// firePropertyChange(NODE_REMOVED_PROP, null, newNode);
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.NODE_REMOVED_PROP))
			refreshChildren();
		else if (string.equals(IQNM.NODE_ADDED_PROP))
			refreshChildren();
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

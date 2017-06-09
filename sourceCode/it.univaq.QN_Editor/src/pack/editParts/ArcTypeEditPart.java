package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import pack.commands.ArcTypeDeleteCommand;
import pack.editPolicies.ArcBendpointEditPolicy;
import pack.model.ArcTypeBendpoint;
import pack.model.IArc;
import pack.model.IModelElement;
import pack.model.TypeBendpoint;

public class ArcTypeEditPart 
	extends AbstractConnectionEditPart
	implements PropertyChangeListener
	{

	@Override
	protected void createEditPolicies() 
		{
		// Selection handle edit policy. 
		// Makes the connection show a feedback, when selected by the user.
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy());
		// Allows the removal of the arc model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() 
			{
			protected Command getDeleteCommand(GroupRequest request) 
				{
				try {
					return new ArcTypeDeleteCommand(getCastedModel());
					} 
				catch (Exception e) 
					{
					return null;
					}
				}
			});
		// si installa la politica per le bende
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new ArcBendpointEditPolicy());
		}
	
	private IArc getCastedModel() 
		{
		return (IArc) getModel();
		}
	
	/**
	 * Upon activation, attach to the model element as a property change listener.
	 */
	public void activate() 
		{
		if (!isActive()) 
			{
			super.activate();
			((IModelElement) getModel()).addPropertyChangeListener(this);
			}
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// le proprietà notificate dal modello sono:
		// 1) l'aggiunta di bende
		// firePropertyChange("bendpoint", null, null);
		// 2) il cambio di posizione di una benda
		// firePropertyChange("bendpoint", null, null);
		// 3) la cancellazione di una benda
		// firePropertyChange("bendpoint", null, null);
		String string = arg0.getPropertyName();
		if ("bendpoint".equals(string))
			refreshBendpoints();       
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
	protected IFigure createFigure() 
		{
		PolylineConnection connection = (PolylineConnection) super.createFigure();
		connection.setTargetDecoration(new PolygonDecoration()); // arrow at target endpoint
		connection.setLineStyle(Graphics.LINE_SOLID);  // line drawing style
		return connection;
		}

	public void refreshBendpoints()
		{
		List<ArcTypeBendpoint> modelConstraint = getCastedModel().getBendpoints();
		List<RelativeBendpoint> figureConstraint = new ArrayList<RelativeBendpoint>();
		for (int i=0; i<modelConstraint.size(); i++) 
			{
			TypeBendpoint bendpoint = modelConstraint.get(i);
			RelativeBendpoint rbp = new RelativeBendpoint(getConnectionFigure());
			rbp.setRelativeDimensions(bendpoint.getFirstRelativeDimension(),
										bendpoint.getSecondRelativeDimension());
			rbp.setWeight((i+1) / ((float)modelConstraint.size()+1));
			figureConstraint.add(rbp);
			}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
		}

	@Override
	protected void refreshVisuals() 
		{
		super.refreshVisuals();
		refreshBendpoints();
		}
	}

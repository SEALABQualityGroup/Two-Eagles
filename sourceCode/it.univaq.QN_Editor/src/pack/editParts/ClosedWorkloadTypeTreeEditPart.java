package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.swt.graphics.Image;

import pack.PMIFPlugin;
import pack.model.IModelElement;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.structure.ClosedWorkloadModel;

public class ClosedWorkloadTypeTreeEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener
	{

	private IQNM type;
	
	
	public ClosedWorkloadTypeTreeEditPart(IQNM type) 
		{
		super();
		this.type = type;
		}

	@Override
	protected List<IRequest> getModelChildren() 
		{
		ClosedWorkloadModel closedWorkloadType = (ClosedWorkloadModel)getModel();
		String string = null;
		string = closedWorkloadType.getWorkloadName();
		return type.getChildrenRequest(string);
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) cambio del nome del workload
		// firePropertyChange(NAME, null, value);
		// 2) inserimento di una richiesta
		// (firePropertyChange(REQUEST_ADDED_PROP, null, newRequest);)
		// 3) cancellazione di una richiesta 
		// (firePropertyChange(REQUEST_REMOVED_PROP, null, child);)
		// 4) cambio del carico di lavoro di una richiesta di servizio
		// (firePropertyChange(WORKLOAD, null, value);)
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.REQUEST_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals(IQNM.REQUEST_ADDED_PROP))
			addChild(createChild(arg0.getNewValue()), -1);
		else if (string.equals(IQNM.NAME))
			refreshVisuals();
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
	protected void createEditPolicies() 
		{
		// If this editpart is the root content of the viewer, then disallow removal
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
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

	@Override
	protected Image getImage() 
		{
		InputStream stream = PMIFPlugin.class.getResourceAsStream("icons/ClosedWorkLoad_32X32.bmp");
		Image image = new Image(null, stream);
		try {
			stream.close();
			} 
		catch (IOException ioe) 
			{}
		return image;
		}

	@Override
	protected String getText() 
		{
		ClosedWorkloadModel closedWorkloadModel = (ClosedWorkloadModel)getModel();
		return closedWorkloadModel.getWorkloadName();
		}
	
	/**
	 * Convenience method that returns the EditPart corresponding to a given child.
	 * @param child a model element instance
	 * @return the corresponding EditPart or null
	 */
	private EditPart getEditPartForChild(Object child) 
		{
		return (EditPart) getViewer().getEditPartRegistry().get(child);
		}
	}

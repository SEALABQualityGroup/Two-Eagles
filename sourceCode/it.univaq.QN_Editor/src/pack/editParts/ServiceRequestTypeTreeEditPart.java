package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import pack.editPolicies.RequestsDeleteEditPolicy;
import pack.editPolicies.ServiceRequestModelContainerEditPolicy;
import pack.editPolicies.ServiceRequestModelTreeContainerEditPolicy;
import pack.model.IModelElement;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.structure.ServiceRequestModel;

public class ServiceRequestTypeTreeEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener
	{
	
	@SuppressWarnings("unused")
	private IQNM type;
	
	public ServiceRequestTypeTreeEditPart(IQNM type) 
		{
		super();
		this.type = type;
		}

	@Override
	protected List<IRequest> getModelChildren() 
		{
		return ((ServiceRequestModel)getModel()).getChildrenRequest(); // return a list of IRequest
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) inserimento di una request
		// firePropertyChange(IQNM.REQUEST_ADDED_PROP, null, timeServType);
		// 2) rimozione di una request (firePropertyChange(REQUEST_REMOVED_PROP, null, child);)
		// firePropertyChange(IQNM.REQUEST_REMOVED_PROP, null, demandServType);
		// 3) cambio del carico di lavoro di una richiesta di servizio
		// firePropertyChange(WORKLOAD, null, value);
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.REQUEST_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals(IQNM.REQUEST_ADDED_PROP))
			addChild(createChild(arg0.getNewValue()), -1);
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
//			// si rimuove la parte di edit dagli ascolatori di IQNM
//			this.type.removePropertyChangeListener(this);
			}
		}

	@Override
	protected void createEditPolicies() 
		{
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RequestsDeleteEditPolicy());
		// si installa la politica di edit per aggiungere una richiesta di servizio 
		// appartenente ad un altro contenitore
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new ServiceRequestModelTreeContainerEditPolicy());
		// si installa una politica per rendere orfano questo contenitore perchè viene spostata
		// una richiesta di servizio in un altro contenitore
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new ServiceRequestModelContainerEditPolicy());
		}

	@Override
	public void activate() 
		{
		if (!isActive()) 
			{
			super.activate();
			((IModelElement) getModel()).addPropertyChangeListener(this);
//			// si ascolta anche i cambiamenti al IQNM
//			this.type.addPropertyChangeListener(this);
			}
		}

	@Override
	protected Image getImage() 
		{
		return super.getImage();
		}

	@Override
	protected String getText() 
		{
		return "Requests";
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

package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import pack.editPolicies.NodeModelContainerEditPolicy;
import pack.editPolicies.NodeModelTreeContainerEditPolicy;
import pack.editPolicies.NodesDeleteEditPolicy;
import pack.model.IModelElement;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.structure.NodeModel;

public class NodeTypeTreeEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener
	{

	@Override
	protected List<INode> getModelChildren() 
		{
		return ((NodeModel)getModel()).getNodes(); // return a list of INode
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un nodo
		// firePropertyChange(NODE_ADDED_PROP, null, newNode);
		// 2) rimozione di un nodo
		// firePropertyChange(NODE_REMOVED_PROP, null, newNode);
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.NODE_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals(IQNM.NODE_ADDED_PROP))
			addChild(createChild(arg0.getNewValue()), -1);
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

	@Override
	protected Image getImage() 
		{
		return super.getImage();
		}

	@Override
	protected String getText() 
		{
		return "Nodes";
		}

	@Override
	protected void createEditPolicies() 
		{
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodesDeleteEditPolicy());
		// si installa la politica per aggiungere un nodo appartenente ad un altro contenitore
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new NodeModelTreeContainerEditPolicy());
		// si installa la politica per cancellare un nodo che viene spostato in un altro contenitore
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new NodeModelContainerEditPolicy());
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

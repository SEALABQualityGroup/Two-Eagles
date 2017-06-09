package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.ui.actions.ActionRegistry;

import pack.editPolicies.QNMModelSelectionEditPolicy;
import pack.model.IModelElement;
import pack.model.IQNM;

public class QNMTypeTreeEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener
	{

	private ActionRegistry actionRegistry;

	public QNMTypeTreeEditPart(ActionRegistry actionRegistry) 
		{
		super();
		this.actionRegistry = actionRegistry;
		}

	@Override
	protected List<Object> getModelChildren() 
		{
		return getCastedModel().getChildren();
		}
	
	private IQNM getCastedModel() 
		{
		return (IQNM)getModel();
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un elemento node
		// firePropertyChange(NODES_ADDED_PROP, null, nodeType);
		// 2) cancellazione di un elemento node
		// firePropertyChange(NODES_REMOVED_PROP, null, nodeType);
		// 3) inserimento di un elemento request
		// firePropertyChange(REQUESTS_ADDED_PROP, null, serviceRequestType);
		// 4) cancellazione di un elemento request
		// firePropertyChange(REQUESTS_REMOVED_PROP, null, serviceRequestType);
		// 5) inserimento di un elemento workload
		// firePropertyChange(WORKLOADS_ADDED_PROP, null, workloadType);		
		// 6) cancellazione di un elemento workload
		// firePropertyChange(WORKLOADS_REMOVED_PROP, null, workloadType);
		String string = arg0.getPropertyName();
		if (string.equals(IQNM.REQUESTS_ADDED_PROP))
			// add a child to this edit part
			// causes an additional entry to appear in the tree of the outline view
			addChild(createChild(arg0.getNewValue()), -1);
		else if (string.equals(IQNM.NODES_ADDED_PROP))
			addChild(createChild(arg0.getNewValue()), -1);
		else if (string.equals(IQNM.NODES_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals(IQNM.REQUESTS_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals(IQNM.WORKLOADS_ADDED_PROP))
			addChild(createChild(arg0.getNewValue()), -1);
		else if (string.equals(IQNM.WORKLOADS_REMOVED_PROP))
			removeChild(getEditPartForChild(arg0.getNewValue()));
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
	protected void createEditPolicies() 
		{
		// per inserire elementi nella vista
		// outline installare anche il
		// seguente ruolo TREE_CONTAINER_ROLE
		// con istanza di tipo TreeContainerEditPolicy		
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
		// installiamo anche la politica di edit relativa all'attivazione delle azioni
		// di inserimento dei contenitori
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new QNMModelSelectionEditPolicy());
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
	
	private EditPart getEditPartForChild(Object child) 
		{
		return (EditPart) getViewer().getEditPartRegistry().get(child);
		}
	
	public ActionRegistry getActionRegistry() 
		{
		return actionRegistry;
		}

	@Override
	protected String getText() 
		{
		return "PMIF Model";
		}
	}

package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.graphics.Image;

import pack.editPolicies.WorkloadModelContainerEditPolicy;
import pack.editPolicies.WorkloadModelTreeContainerEditPolicy;
import pack.editPolicies.WorkloadTypeSelectionEditPolicy;
import pack.editPolicies.WorkloadsDeleteEditPolicy;
import pack.model.IModelElement;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadTypeTreeEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener
	{
	
	private ActionRegistry actionRegistry;

	public WorkloadTypeTreeEditPart(ActionRegistry actionRegistry) 
		{
		super();
		this.actionRegistry = actionRegistry;
		}

	@Override
	protected List<IWorkload> getModelChildren() 
		{
		return ((WorkloadModel)getModel()).getChildrenWorkload(); // return a list of IWorkload
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) inserimento di un workload
		// firePropertyChange("WorkloadAdded", null, child);
		// 2) rimozione di un workload
		// firePropertyChange("WorkloadRemoved", null, child);
		String string = arg0.getPropertyName();
		if (string.equals("WorkloadRemoved"))
			removeChild(getEditPartForChild(arg0.getNewValue()));
		else if (string.equals("WorkloadAdded"))
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
	protected void createEditPolicies() 
		{
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new WorkloadsDeleteEditPolicy());
		// si installa la politica di selezione per consentire l'inserimento di un carico di lavoro
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new WorkloadTypeSelectionEditPolicy());
		// si installa la politica per l'inserimento di un carico di lavoro provienente da
		// un altro contenitore
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new WorkloadModelTreeContainerEditPolicy());
		// si installa la politica per rendere orfano questo contenitore a causa dello spostamento
		// di un carico di lavoro in un altro contenitore
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new WorkloadModelContainerEditPolicy());
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
		return "Workloads";
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

	public ActionRegistry getActionRegistry() 
		{
		return actionRegistry;
		}	
	}

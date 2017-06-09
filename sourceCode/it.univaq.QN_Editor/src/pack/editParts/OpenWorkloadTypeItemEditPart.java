package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.graphics.Image;

import pack.PMIFPlugin;
import pack.editPolicies.OpenWorkloadDeleteEditPolicy;
import pack.editPolicies.WorkloadSelectionEditPolicy;
import pack.model.IModelElement;
import pack.model.structure.OpenWorkloadModel;

public class OpenWorkloadTypeItemEditPart 
	extends AbstractTreeEditPart
	implements PropertyChangeListener 
	{

	private ActionRegistry actionRegistry;
	
	public OpenWorkloadTypeItemEditPart(ActionRegistry actionRegistry) 
		{
		this.actionRegistry = actionRegistry;
		}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) cambio del nome del workload
		// firePropertyChange(NAME, null, value);
		String string = arg0.getPropertyName();
		if (string.equals(IModelElement.NAME))
			refreshVisuals(); // this will cause an invocation of getImage() and getText(), see below
		}
	
	@Override
	protected Image getImage() 
		{
		InputStream stream = PMIFPlugin.class.getResourceAsStream("icons/OpenWorkLoad_32X32.bmp");
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
		OpenWorkloadModel type = (OpenWorkloadModel)getModel();
		return type.getWorkloadName();
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
	protected void createEditPolicies() 
		{
		// si installa la politica per la cancellazione
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OpenWorkloadDeleteEditPolicy());
		// si installa la politica di selezione per consentire l'azione di switch
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new WorkloadSelectionEditPolicy());
		}
	
	public ActionRegistry getActionRegistry() 
		{
		return actionRegistry;
		}
	}

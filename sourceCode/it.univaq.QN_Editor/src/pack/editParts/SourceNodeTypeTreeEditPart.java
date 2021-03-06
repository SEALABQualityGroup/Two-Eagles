package pack.editParts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import pack.PMIFPlugin;
import pack.editPolicies.NodeDeleteEditPolicy;
import pack.model.IModelElement;
import pack.model.INode;
import pack.model.structure.SourceNodeModel;

public class SourceNodeTypeTreeEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener 
	{
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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() 
		{
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeDeleteEditPolicy());
		}

	/**
	 * Upon deactivation, detach from the model element as a property change listener.
	 */
	public void deactivate() 
		{
		if (isActive()) 
			{
			super.deactivate();
			((IModelElement) getModel()).removePropertyChangeListener(this);
			}
		}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	protected Image getImage() 
		{
		InputStream stream = PMIFPlugin.class.getResourceAsStream("icons/Queueing Network Source.bmp");
		Image image = new Image(null, stream);
		try {
			stream.close();
			} 
		catch (IOException ioe) 
			{}
		return image;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() 
		{
		SourceNodeModel model = (SourceNodeModel)getModel();
		return model.getServerName();
		}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) 
		{
 		// Gli eventi notificati dal modello sono:
		// 1) cambio del nome del server
		// firePropertyChange(NAME, null, value);
		String string = evt.getPropertyName();
		if (string.equals(INode.NAME))
			refreshVisuals(); // this will cause an invocation of getImage() and getText(), see below
		}
	}

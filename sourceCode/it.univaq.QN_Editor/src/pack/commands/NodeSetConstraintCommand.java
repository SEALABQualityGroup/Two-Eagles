package pack.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import pack.errorManagement.Finestra;
import pack.model.INode;

public class NodeSetConstraintCommand 
	extends Command 
	{
	
	private INode node;
	
	private ChangeBoundsRequest changeBoundsRequest;
	
	private Rectangle newBounds;
	
	/** Stores the old location. */
	private Rectangle oldBounds;
	
	/**
	 * Create a command that can move a INode. 
	 * @param INode	the INode to manipulate
	 * @param req		the move request
	 * @param newBounds the new location
	 */
	public NodeSetConstraintCommand(INode node, ChangeBoundsRequest req, 
			Rectangle newBounds)
		throws Exception
		{
		if (node == null) 
			{
			Finestra.mostraIE("node is null");
			}
		if (req == null)
			{
			Finestra.mostraIE("request is null");
			}
		if (newBounds == null)
			{
			Finestra.mostraIE("bounds is null");
			}
		this.node = node;
		this.changeBoundsRequest = req;
		this.newBounds = newBounds.getCopy();
		setLabel("move");
		}

	@Override
	public boolean canExecute() 
		{
		Object type = changeBoundsRequest.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type));
		}

	@Override
	public void execute() 
		{
		oldBounds = new Rectangle(node.getLocation(), node.getEtichetta().getSize());
		redo();
		}

	@Override
	public void redo() 
		{
		try {
			node.setLocation(newBounds.getLocation());
			} 
		catch (Exception e) 
			{}
		}

	@Override
	public void undo() 
		{
		try {
			node.setLocation(oldBounds.getLocation());
			} 
		catch (Exception e) 
			{}
		}
	}

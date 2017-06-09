package pack.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import pack.model.INode;
import pack.model.IQNM;

public class NodeCreateCommand 
	extends Command 
	{

	private INode newNode;
	
	private IQNM parent;
	
	private Rectangle bounds;
	
	/**
	 * Create a command that will add a new INode to a QNMType.
	 * 
	 * @param newNode the new INode that is to be added
	 * @param parent the QNMType that will hold the new element
	 * @param bounds the bounds of the new node; the size can be (-1, -1) if not known
	 */
	public NodeCreateCommand(INode newNode, IQNM parent, Rectangle bounds) 
		{
		this.newNode = newNode;
		this.parent = parent;
		this.bounds = bounds;
		setLabel("node creation");
		}

	@Override
	public void execute() 
		{
		try {
			newNode.setLocation(bounds.getLocation());
			redo();
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void redo() 
		{
		parent.addChild(newNode);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(newNode);
		}

	@Override
	public boolean canExecute() 
		{
		return newNode != null && parent != null && bounds != null;
		}
	}

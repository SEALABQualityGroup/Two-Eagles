package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.INode;
import pack.model.structure.NodeModel;

public class NodeAddCommand extends Command 
	{
	
	private INode newNode;
	
	private NodeModel parent;
	
	/**
	 * Create a command that will add a new INode to a NodeModel.
	 * 
	 */
	public NodeAddCommand(INode newNode, NodeModel parent) 
		{
		this.newNode = newNode;
		this.parent = parent;
		setLabel("node addition");
		}

	@Override
	public void execute() 
		{
		redo();
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
		return newNode != null && parent != null;
		}
	
	}

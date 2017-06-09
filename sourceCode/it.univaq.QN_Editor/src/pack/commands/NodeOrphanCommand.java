package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.INode;
import pack.model.structure.NodeModel;

public class NodeOrphanCommand extends Command 
	{
	
	/** INode to remove. */
	private final INode child;

	/** NodeModel to remove from. */
	private final NodeModel parent;

	/**
	 * Create a command that will remove the node from its parent.
	 * @param parent the NodeModel containing the child
	 * @param child    the INode to remove
	 */
	public NodeOrphanCommand(NodeModel parent, INode child)
		throws Exception
		{
		if (parent == null || child == null) 
			{
			Finestra.mostraIE("parent is null");
			}
		if (child == null)
			{
			Finestra.mostraIE("child is null");
			}
		setLabel("node deletion");
		this.parent = parent;
		this.child = child;
		}

	@Override
	public void execute() 
		{
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child
		parent.removeChild(child);
		}

	@Override
	public void undo() 
		{
		// add the child
		parent.addChild(child);
		}	

	}

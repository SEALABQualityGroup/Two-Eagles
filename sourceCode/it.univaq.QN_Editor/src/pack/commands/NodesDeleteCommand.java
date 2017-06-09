package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.structure.NodeModel;

public class NodesDeleteCommand extends Command 
	{
	
	/** NodeModel to remove. */
	protected NodeModel child;
	
	/** QNM to remove from. */
	protected IQNM parent;
	
	/** Holds a copy of the INode of child. */
	private List<INode> nodes;

	/** True, if child was removed from its parent. */
	private boolean wasRemoved;

	public NodesDeleteCommand(IQNM parent, NodeModel child)
		throws Exception
		{
		if (parent == null) 
			{
			Finestra.mostraIE("parent is null");
			}
		if (child == null)
			{
			Finestra.mostraIE("child is null");
			}
		setLabel("NodeTypes deletion");
		this.parent = parent;
		this.child = child;
		}
	
	@Override
	public boolean canUndo() 
		{
		return wasRemoved;
		}

	@Override
	public void execute() 
		{
		// store a copy of nodes before proceeding 
		nodes = child.getNodes();
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child and disconnect its nodes
		wasRemoved = parent.removeChild(child);
		if (wasRemoved) 
			removeNodes(nodes);
		}

	private void removeNodes(List<INode> nodes) 
		{
		for (Iterator<INode> iter = nodes.iterator(); iter.hasNext();) 
			{
			INode node = iter.next();
			child.removeChild(node);
			}
		}

	@Override
	public void undo() 
		{
		// add the child and recreate the nodes
		if (parent.addChild(child)) 
			addNodes(nodes);
		}

	private void addNodes(List<INode> nodes) 
		{
		for (Iterator<INode> iter = nodes.iterator(); iter.hasNext();) 
			{
			INode node = iter.next();
			child.addChild(node);
			}
		}
	}

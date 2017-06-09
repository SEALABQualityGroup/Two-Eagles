package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;

public class NodeDeleteCommand 
	extends Command 
	{
	/** INode to remove. */
	private final INode child;

	/** QNMType to remove from. */
	private final IQNM parent;
	
	/** Holds a copy of the outgoing arcs of child. */
	private List<IArc> sourceArcs;

	/** Holds a copy of the incoming arcs of child. */
	private List<IArc> targetArcs;
	
	/** True, if child was removed from its parent. */
	private boolean wasRemoved;
	
	/**
	 * Create a command that will remove the node from its parent.
	 * @param parent the QNMType containing the child
	 * @param child    the INode to remove
	 */
	public NodeDeleteCommand(IQNM parent, INode child)
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
	public boolean canUndo() 
		{
		return wasRemoved;
		}

	@Override
	public void execute() 
		{
		// store a copy of incoming & outgoing arcs before proceeding 
		sourceArcs = parent.getSourceArcsFromID(child.getServerName());
		targetArcs = parent.getTargetArcsFromID(child.getServerName());
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child and disconnect its connections
		wasRemoved = parent.removeChild(child);
		if (wasRemoved) 
			{
			removeArcs(sourceArcs);
			removeArcs(targetArcs);
			}
		}

	private void removeArcs(List<IArc> sourceArcs2) 
		{
		for (Iterator<IArc> iter = sourceArcs2.iterator(); iter.hasNext();) 
			{
			IArc arc = iter.next();
			arc.disconnect();
			}
		}

	@Override
	public void undo() 
		{
		// add the child and reconnect its arcs
		if (parent.addChild(child)) 
			{
			addArcs(sourceArcs);
			addArcs(targetArcs);
			}
		}

	private void addArcs(List<IArc> sourceArcs2) 
		{
		for (Iterator<IArc> iter = sourceArcs2.iterator(); iter.hasNext();) 
			{
			IArc conn = (IArc) iter.next();
			conn.reconnect();
			}
		}
	}

package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.structure.NodeModel;
import pack.model.structure.QNMModel;

public class NodeModelCreateComand extends Command 
	{

	private QNMModel parent;
	
	private NodeModel child;

	public NodeModelCreateComand(QNMModel parent, NodeModel child) 
		{
		super();
		this.parent = parent;
		this.child = child;
		}

	@Override
	public void execute() 
		{
		parent.addChild(child);
		}

	@Override
	public void redo() 
		{
		parent.addChild(child);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(child);
		}	
	}

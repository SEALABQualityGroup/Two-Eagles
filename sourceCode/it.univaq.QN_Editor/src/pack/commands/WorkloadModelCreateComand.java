package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.structure.QNMModel;
import pack.model.structure.WorkloadModel;

public class WorkloadModelCreateComand extends Command 
	{
	
	private QNMModel parent;
	
	private WorkloadModel child;

	public WorkloadModelCreateComand(QNMModel parent, WorkloadModel child) 
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

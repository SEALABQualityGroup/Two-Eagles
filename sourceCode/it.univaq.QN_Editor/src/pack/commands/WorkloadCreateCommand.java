package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadCreateCommand 
	extends Command 
	{

	private WorkloadModel parent;
	
	private IWorkload child;

	public WorkloadCreateCommand(WorkloadModel parent, IWorkload child) 
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

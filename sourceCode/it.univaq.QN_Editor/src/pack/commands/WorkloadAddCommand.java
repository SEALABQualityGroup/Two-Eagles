package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadAddCommand extends Command 
	{
	
	private IWorkload workload;
	
	private WorkloadModel parent;
	
	/**
	 * Create a command that will add a new IWorkload to a WorkloadModel.
	 * 
	 */
	public WorkloadAddCommand(IWorkload newWorkload, WorkloadModel parent) 
		{
		this.workload = newWorkload;
		this.parent = parent;
		setLabel("workload addition");
		}

	@Override
	public void execute() 
		{
		redo();
		}

	@Override
	public void redo() 
		{
		parent.addChild(workload);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(workload);
		}

	@Override
	public boolean canExecute() 
		{
		return workload != null && parent != null;
		}
	
	}

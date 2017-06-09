package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class WorkloadsDeleteCommand extends Command 
	{

	/** WorkloadModel to remove. */
	protected WorkloadModel child;
	
	/** QNM to remove from. */
	protected IQNM parent;
	
	/** Holds a copy of the IWorkload of child. */
	private List<IWorkload> workloads;

	/** True, if child was removed from its parent. */
	private boolean wasRemoved;

	public WorkloadsDeleteCommand(IQNM parent, WorkloadModel child)
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
		setLabel("WorkloadType deletion");
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
		// store a copy of workloads before proceeding 
		workloads = child.getChildrenWorkload();
		redo();
		}

	@Override
	public void redo() 
		{
		// remove the child and disconnect its workloads
		wasRemoved = parent.removeChild(child);
		if (wasRemoved) 
			removeWorkloads(workloads);
		}

	private void removeWorkloads(List<IWorkload> workloads) 
		{
		for (Iterator<IWorkload> iter = workloads.iterator(); iter.hasNext();) 
			{
			IWorkload workload = iter.next();
			child.removeChild(workload);
			}
		}

	@Override
	public void undo() 
		{
		// add the child and recreate the requests
		if (parent.addChild(child)) 
			addWorkloads(workloads);
		}

	private void addWorkloads(List<IWorkload> workloads) 
		{
		for (Iterator<IWorkload> iter = workloads.iterator(); iter.hasNext();) 
			{
			IWorkload workload = iter.next();
			child.addChild(workload);
			}
		}
	}

package pack.commands;



import pack.errorManagement.Finestra;
import pack.model.IWorkload;
import pack.model.structure.WorkloadModel;

public class ClosedWorkloadDeleteCommand extends WorkloadDeleteCommand 
	{

	/**
	 * Create a command that will remove the workload from its parent.
	 * @param parent the WorkloadType containing the child
	 * @param child    the IWorkload to remove
	 */
	public ClosedWorkloadDeleteCommand(WorkloadModel parent, IWorkload child)
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
		setLabel("workload deletion");
		this.parent = parent;
		this.child = child;
		}

	}

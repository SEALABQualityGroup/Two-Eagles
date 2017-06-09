package pack.commands;

import pack.errorManagement.Finestra;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadModel;

public class OpenWorkloadDeleteCommand extends WorkloadDeleteCommand 
	{

	public OpenWorkloadDeleteCommand(WorkloadModel parent, OpenWorkloadModel child)
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

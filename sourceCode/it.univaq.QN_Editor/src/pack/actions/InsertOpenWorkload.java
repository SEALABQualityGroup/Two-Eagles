package pack.actions;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import pack.commands.WorkloadCreateCommand;
import pack.editors.PMIFGraphicalEditor;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.WorkloadModel;

public class InsertOpenWorkload extends Action 
	{
	
	private WorkloadModel workloadModel;
	
	@Override
	public String getText() 
		{
		return "Insert Open Workload";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.InsertOpenWorkload";
		}
	
	public void setWorkloadModel(WorkloadModel workloadModel) 
		{
		this.workloadModel = workloadModel;
		}

	@Override
	public void run() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		PMIFGraphicalEditor editor = (PMIFGraphicalEditor)workbenchPage.getActiveEditor();
		CommandStack commandStack = editor.getCommandStack();
		// si aggiunge un carico di lavoro aperto utilizzando il comando 
		// WorkloadCreateCommand
		OpenWorkloadModel openWorkloadModel = new OpenWorkloadModel(workloadModel.getQNM());
		WorkloadCreateCommand workloadCreateCommand = 
			new WorkloadCreateCommand(workloadModel,openWorkloadModel);
		commandStack.execute(workloadCreateCommand);
		}
	}

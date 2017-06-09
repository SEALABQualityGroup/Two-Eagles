package pack.actions;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import pack.commands.WorkloadModelCreateComand;
import pack.editors.PMIFGraphicalEditor;
import pack.model.structure.QNMModel;
import pack.model.structure.WorkloadModel;

public class InsertWorkloadModel extends Action 
	{
	
	private QNMModel model;
	
	@Override
	public String getText() 
		{
		return "Insert Workloads Container";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.InsertWorkloadModel";
		}

	public void setQNMModel(QNMModel model) 
		{
		this.model = model;
		}

	@Override
	public void run() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		PMIFGraphicalEditor editor = (PMIFGraphicalEditor)workbenchPage.getActiveEditor();
		CommandStack commandStack = editor.getCommandStack();
		// si aggiunge un contenitore di carichi di lavoro utilizzando il comando 
		// WorkloadModelCreateCommand
		WorkloadModel workloadModel = new WorkloadModel(this.model);
		WorkloadModelCreateComand workloadModelCreateComand = 
			new WorkloadModelCreateComand(model,workloadModel);
		commandStack.execute(workloadModelCreateComand);
		}
	
	}

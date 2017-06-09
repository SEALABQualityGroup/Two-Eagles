package pack.actions;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import pack.commands.NodeModelCreateComand;
import pack.editors.PMIFGraphicalEditor;
import pack.model.structure.NodeModel;
import pack.model.structure.QNMModel;

public class InsertNodeModel extends Action 
	{
	
	private QNMModel model;
	
	@Override
	public String getText() 
		{
		return "Insert Nodes Container";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.InsertNodeModel";
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
		// si aggiunge un contenitore di nodi utilizzando il comando 
		// NodeModelCreateCommand
		NodeModel nodeModel = new NodeModel();
		NodeModelCreateComand nodeModelCreateCommand = 
			new NodeModelCreateComand(model,nodeModel);
		commandStack.execute(nodeModelCreateCommand);
		}
	
	}

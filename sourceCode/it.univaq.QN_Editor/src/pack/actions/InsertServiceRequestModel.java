package pack.actions;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import pack.commands.ServiceRequestModelCreateComand;
import pack.editors.PMIFGraphicalEditor;
import pack.model.structure.QNMModel;
import pack.model.structure.ServiceRequestModel;

public class InsertServiceRequestModel 
	extends Action 
	{

	private QNMModel model;
	
	@Override
	public String getText() 
		{
		return "Insert Service Requests Container";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.InsertServiceRequestModel";
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
		// si aggiunge un contenitore di richieste di servizio utilizzando il comando 
		// ServiceRequestModelCreateCommand
		ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
		ServiceRequestModelCreateComand serviceRequestModelCreateComand = 
			new ServiceRequestModelCreateComand(model,serviceRequestModel);
		commandStack.execute(serviceRequestModelCreateComand);
		}
	
	}

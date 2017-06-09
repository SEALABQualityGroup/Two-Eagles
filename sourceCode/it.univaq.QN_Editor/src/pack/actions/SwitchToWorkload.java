package pack.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import pack.editors.PMIFGraphicalEditor;
import pack.model.IWorkload;

public class SwitchToWorkload 
	extends Action 
	{

	private IWorkload workload;
	
	@Override
	public String getText() 
		{
		return "Switch to workload chain";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.SwitchToWorkload";
		}

	public void setWorkload(IWorkload workload) 
		{
		this.workload = workload;
		}

	@Override
	public void run() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		PMIFGraphicalEditor editor = (PMIFGraphicalEditor)workbenchPage.getActiveEditor();
		IEditorInput editorInput = editor.getEditorInput();
		PMIFGraphicalEditor.switchToWorkload = true;
		PMIFGraphicalEditor.workload = this.workload;
		PMIFGraphicalEditor.model = this.workload.getQNM();
		workbenchPage.closeEditor(editor, true);
		try {
			workbenchPage.openEditor(editorInput, "it.univaq.PMIFGraphicalEditor");
			} 
		catch (PartInitException e) 
			{
			e.printStackTrace();
			}
		}
	}

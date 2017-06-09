package pack.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import pack.editors.PMIFGraphicalEditor;

public class SwitchToQN 
	extends Action 
	{

	@Override
	public String getText() 
		{
		return "Switch to QN topology";
		}

	@Override
	public String getId() 
		{
		return "it.univaq.SwitchToQN";
		}
	
	@Override
	public void run() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		PMIFGraphicalEditor editor = (PMIFGraphicalEditor)workbenchPage.getActiveEditor();
		PMIFGraphicalEditor.switchToWorkload = false;
		IEditorInput editorInput = editor.getEditorInput();
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

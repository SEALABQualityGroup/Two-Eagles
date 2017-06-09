package it.univaq.editoractiondelegate;

import it.univaq.wizards.QNSolveWizardDialog;
import it.univaq.wizards.nodes.ServerNode;
import it.univaq.wizards.pages.QNSolveServersWizardPage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class EditorActionDelegate1 implements IEditorActionDelegate {

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) 
		{
		}

	@Override
	public void run(IAction action) 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		// si creano le pagine del wizard ServerNode con la solo
		// pagina QNSolveServersWizardPage
		QNSolveServersWizardPage serversWizardPage = 
			new QNSolveServersWizardPage();
		List<IWizardPage> list = new ArrayList<IWizardPage>();
		list.add(serversWizardPage);
		// si crea la selezione delle pagine
		StructuredSelection structuredSelection =
			new StructuredSelection(list);
		// si aggiungono le pagine al wizard
		ServerNode serverNode = new ServerNode();
		serverNode.init(workbench, structuredSelection);		
		Shell shell = workbenchWindow.getShell();
		// si imposta a false lo stato di finish dei nodi del wizard
		QNSolveWizardDialog.esitoToolsListandValidationNode = false;
		QNSolveWizardDialog.esitoMethodsNode = false;
		QNSolveWizardDialog.esitoParamsNode = false;
		// si crea il dialog del wizard e lo si apre
		QNSolveWizardDialog wizardDialog = 
			new QNSolveWizardDialog(shell,serverNode);
		serversWizardPage.setDialog(wizardDialog);
		wizardDialog.open();		
		}

	@Override
	public void selectionChanged(IAction action, ISelection selection) 
		{
		}
	}

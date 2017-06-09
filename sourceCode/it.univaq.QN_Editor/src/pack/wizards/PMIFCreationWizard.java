package pack.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class PMIFCreationWizard 
	extends Wizard implements INewWizard 
	{

	// fileCount viene utilizzato per generare un nome di default
	// quando si crea un nuovo diagramma
	private static int fileCount = 1;
	
	private CreationPagePMIF page1;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() 
		{
		// add pages to this wizard
		addPage(page1);
		}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) 
		{
		// create pages for this wizard
		page1 = new CreationPagePMIF(workbench, selection, fileCount);
		}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() 
		{
		return page1.finish();
		}
	
	}

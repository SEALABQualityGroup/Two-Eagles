package it.univaq.wizards.nodes;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

public class ToolsListAndValidationNode extends Wizard 
	implements IWorkbenchWizard {
	
	public ToolsListAndValidationNode() 
		{
		super();
		}

	@SuppressWarnings("unchecked")
	public void init(IWorkbench workbench, IStructuredSelection selection) 
		{
		Iterator<IWizardPage> iterator = selection.iterator();
		while (iterator.hasNext())
			{
			IWizardPage wizardPage = iterator.next();
			addPage(wizardPage);
			}
		}

	@Override
	public boolean canFinish() 
		{
		return false;
		}

	@Override
	public boolean performFinish() 
		{
		return true;
		}
	}

package it.univaq.wizards.nodes;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

public class ServerNode 
	extends Wizard 
	implements IWizardNode, 
	IWorkbenchWizard 
	{
	
	public ServerNode() 
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
		return false;		
		}

	@Override
	public Point getExtent() 
		{
		return new Point(-1,-1);
		}

	@Override
	public IWizard getWizard() 
		{
		return this;
		}

	@Override
	public boolean isContentCreated() 
		{
		return true;
		}

	@Override
	public boolean needsPreviousAndNextButtons() 
		{
		return true;
		}
	}

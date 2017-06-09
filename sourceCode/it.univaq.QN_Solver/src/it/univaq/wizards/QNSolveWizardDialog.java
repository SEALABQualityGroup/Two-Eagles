package it.univaq.wizards;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class QNSolveWizardDialog extends WizardDialog 
	{
	
	public static boolean esitoToolsListandValidationNode;
	public static boolean esitoMethodsNode;
	public static boolean esitoParamsNode;

	public QNSolveWizardDialog(Shell parentShell, IWizard newWizard) 
		{
		super(parentShell, newWizard);
		}

	}
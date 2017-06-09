package it.univaq.wizards.pages;

import it.univaq.wizards.nodes.MethodsNode;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class QNSolveValidationWizardPage extends WizardSelectionPage 
	{
	
	private Button synValid;
	
	private Button semValid;
	
	private QNSolveServersWizardPage serversWizardPage;
	
	private QNSolveToolsListWizardPage toolsListWizardPage;
	
	private MethodsNode methodsNode;
	
	private String pmif;
	
	private IFile file;
	
	private WizardDialog wizardDialog;
	
	public QNSolveValidationWizardPage(QNSolveServersWizardPage serversWizardPage, 
			QNSolveToolsListWizardPage wizardPage, String pmif, IFile file) 
		{
		super("QNSolveValidationWizardPage");
		setTitle("PMIF validation");
		setDescription("Sign the validation type to perform");
		this.serversWizardPage = serversWizardPage;
		this.toolsListWizardPage = wizardPage;
		this.pmif = pmif;
		this.file = file;
		}

	@Override
	public void createControl(Composite arg0) 
		{
	   	Composite container = new Composite(arg0, SWT.NULL);
	   	setControl(container);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		container.setLayout(layout);

		this.synValid = new Button(container,SWT.CHECK);

		Label label = new Label(container,SWT.LEFT);
		label.setText("syntactic validation");

		this.semValid = new Button(container,SWT.CHECK);
 		
 		Label label2 = new Label(container,SWT.LEFT);
 		label2.setText("semantics validation");
		}

	public Button getSynValid() 
		{
		return synValid;
		}

	public Button getSemValid() 
		{
		return semValid;
		}

	@Override
	public boolean canFlipToNextPage() 
		{
		return true;
		}

	@Override
	public void dispose() 
		{
		if (this.methodsNode != null)
			this.methodsNode.dispose();
		super.dispose();
		}

	@Override
	public IWizardPage getNextPage() 
		{
		this.methodsNode = new MethodsNode(this.serversWizardPage,this.toolsListWizardPage,this);
		this.methodsNode.setDialog(getDialog());
		return this.methodsNode.getStartingPage();
		}

	@Override
	public IWizardNode getSelectedNode() 
		{
		return this.methodsNode;
		}

	public String getPmif() 
		{
		return pmif;
		}

	public IFile getFile() 
		{
		return file;
		}
	
	private WizardDialog getDialog() 
		{
		return this.wizardDialog;
		}
	
	public void setDialog(WizardDialog dialog) 
		{
		this.wizardDialog = dialog;
		}
	}

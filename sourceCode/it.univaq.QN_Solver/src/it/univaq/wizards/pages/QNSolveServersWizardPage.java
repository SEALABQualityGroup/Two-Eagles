package it.univaq.wizards.pages;

import it.univaq.editors.PMIFEditor;
import it.univaq.wizards.nodes.ToolsListAndValidationNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import qNSolver.WeaselProxy;
import utility.Metodi;
import utility.Properties;

public class QNSolveServersWizardPage 
	extends WizardSelectionPage
	{
	
	private Combo combo;
	
	private WizardDialog wizardDialog;

	public QNSolveServersWizardPage() 
		{
		super("QNSolveServersWizardPage");
		setTitle("Server selection");
		setDescription("Select the server for Queueing Network solving");
		}

	@Override
	public void createControl(Composite arg0) 
		{
		
	   	Composite container = new Composite(arg0, SWT.NULL);
	   	setControl(container);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;

		container.setLayout(layout);
		
		Label label = new Label(container,SWT.LEFT);
		label.setText("server: ");
		
		Combo combo = new Combo(container,SWT.READ_ONLY);
		// si preleva la lista dei servers
		String[] servers = WeaselProxy.servers;
		combo.setItems(servers);
		// si crea il combo in modo tale che abbia almeno
		// un metodo selezionato, se il tool ha almeno un metodo
		if (combo.getItemCount() > 0)
			combo.select(0);		
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		combo.setLayoutData(gridData);
		this.combo = combo;
		}

	public Combo getCombo() 
		{
		return combo;
		}

	@Override
	public IWizardPage getNextPage() 
		{
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		// a seconda dell'editor attivo prelevo il giusto input dell'editor
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		IEditorInput editorInput = null;
		if (editorPart instanceof PMIFEditor)
			{
			PMIFEditor editor = (PMIFEditor)workbenchPage.getActiveEditor();
			editorInput = editor.getEditorInput();
			}
		else
			{
			// siamo nel caso in cui l'editor attivo è l'editor grafico
			IEditorInput input = editorPart.getEditorInput();
			IFile file = ((IFileEditorInput) input).getFile();
			IContainer container = file.getParent();
			IPath path = file.getLocation();
			IPath path2 = path.removeFileExtension();
			// dal container di tale file si preleva un oggetto IFile
			// del .xml
			String string = path2.lastSegment();
			string = string+".xml";
			// come precondizione file3 esiste
			IFile file3 = (IFile)container.findMember(string);
			FileEditorInput fileEditorInput = new FileEditorInput(file3);
			editorInput = fileEditorInput;
			}
		FileEditorInput fileEditorInput = (FileEditorInput)editorInput.getAdapter(Object.class);
		IFile file = fileEditorInput.getFile();
		// bisogna restituire una stringa dallo stream di file
		InputStream inputStream = null;
		try {
			inputStream = file.getContents();
			} 
		catch (CoreException e) 
			{
			e.printStackTrace();
			}
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String string = new String();
		while (true)
			{
			String string2 = null;
			try {
				string2 = bufferedReader.readLine();
				} 
			catch (IOException e) 
				{
				e.printStackTrace();
				}
			if (string2 == null)
				break;
			else
				string = string + string2;
			}
		// si chiudono gli stream
		try {
			inputStream.close();
			inputStreamReader.close();
			bufferedReader.close();
			}
		catch (IOException exception)
			{
			exception.printStackTrace();
			}
		QNSolveToolsListWizardPage selectStringsWizardPage =
			new QNSolveToolsListWizardPage(this);
		if (selectStringsWizardPage.isResponded())
			{
			QNSolveValidationWizardPage solveValidationWizardPage =
				new QNSolveValidationWizardPage(this,selectStringsWizardPage,string,file);
			solveValidationWizardPage.setDialog(getDialog());
			List<IWizardPage> list = new ArrayList<IWizardPage>();
			list.add(selectStringsWizardPage);
			list.add(solveValidationWizardPage);
			// si crea la selezione delle pagine
			StructuredSelection structuredSelection =
				new StructuredSelection(list);
			// si crea il wizard e lo si inizializza
			ToolsListAndValidationNode selectionListWizard =
				new ToolsListAndValidationNode();
			selectionListWizard.init(workbench, structuredSelection);
			return selectionListWizard.getStartingPage();
			}
		else
			{
	   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
	   		this.wizardDialog.close();
	   		return null;
			}
		}

	@Override
	public boolean canFlipToNextPage() 
		{
		return true;
		}
	
	private WizardDialog getDialog() 
		{
		return this.wizardDialog;
		}
	
	public void setDialog(WizardDialog wizardDialog2) 
		{
		this.wizardDialog = wizardDialog2;
		}
	}

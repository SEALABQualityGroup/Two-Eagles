package it.univaq.wizards.pages;

import it.univaq.wizards.nodes.ParamsNode;
import it.univaq.wizards.providers.ExtractedString;
import it.univaq.wizards.providers.ExtractedStringsContentProviderM;
import it.univaq.wizards.providers.ExtractedStringsLabelProvider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import parserReply.Method;
import parserReply.Tool;

// Ha la stessa struttura della pagina QNSolveMethodsWizardPage, ma nel metodo canFlipToNextPage
// c'è l'istanziazzione del nodo wizard ParamsNode
public class QNSolveMethodsWizardPageLast 
	extends WizardSelectionPage
	implements IQNSolveMethodsWizardPage
	{

	private Tool tool;
	
	private CheckboxTableViewer checkboxTableViewer;
	
	private ParamsNode paramsNode;
	
	private QNSolveServersWizardPage serversWizardPage;
	
	private QNSolveToolsListWizardPage toolsListWizardPage;
	
	private QNSolveValidationWizardPage validationWizardPage;
	
	private List<IQNSolveMethodsWizardPage> methodsWizardlist;
	
	private WizardDialog wizardDialog;
		
	public QNSolveMethodsWizardPageLast
			(
			Tool tool, 
			QNSolveServersWizardPage serversWizardPage,
			QNSolveToolsListWizardPage toolsListWizardPage,
			// serve per il finish
			QNSolveValidationWizardPage validationWizardPage,
			List<IQNSolveMethodsWizardPage> list
			) 
		{
		super("QNSolveMethodsWizardPageLast");
		this.tool = tool;
		String string = tool.getName();
		setTitle(string+" methods list");
		setDescription("Select the Methods to be used for "
				+"Queueing Network solving");
		this.serversWizardPage = serversWizardPage;
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		this.methodsWizardlist = list;
		}

	@Override
	public void createControl(Composite parent) 
		{
	   	Composite container = new Composite(parent, SWT.NULL);
	   	container.setLayout(new FormLayout());
	   	setControl(container);
	   	// crea una tabella a due colonne con elementi con
	   	// checkbox
	   	checkboxTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
	   	// il fornitore di contenuto di questa tabella corrisponde ad un'array
	   	// di elementi ExtractedString, che sono dei bean
	   	// che consistono di una chiave ed un valore
	   	// si crea un oggetto ExtractedStringsContentProviderM
	   	// con il nome e la descrizione di ogni methodo di tool
	   	// presente nella lista dei metodi del tool
	   	checkboxTableViewer.setContentProvider(new ExtractedStringsContentProviderM(tool));
	   	// il fornitore di etichetta di questa tabella è un fornitore di etichette per i viewer di
	   	// tabelle e fornisce i metodi per ottenere il testo per un elemento (ExtractedString) 
	   	// della lista con checkbox
	   	checkboxTableViewer.setLabelProvider(new ExtractedStringsLabelProvider());
	   	// restituisce la tabella del viewer
	   	Table table = checkboxTableViewer.getTable();
	   	// il layout di dati per questa tabella
	   	// è un FormData, con la parte inferiore e di destra che occupano il
	   	// 100% del container parente
	   	FormData formData = new FormData();
	   	formData.bottom = new FormAttachment(100, 0);
	   	formData.right = new FormAttachment(100, 0);
	   	formData.top = new FormAttachment(0, 0);
	   	formData.left = new FormAttachment(0, 0);
	   	table.setLayoutData(formData);
	   	table.setHeaderVisible(true);
	   	
	   	TableColumn tableColumn = new TableColumn(table, SWT.NONE);
	   	tableColumn.setWidth(200);
	   	tableColumn.setText("Name");
	   	
	   	TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
	   	tableColumn_1.setWidth(250);
	   	tableColumn_1.setText("Description");
	   	// si popola la tabella
	   	IStructuredContentProvider contentProvider = (IStructuredContentProvider)
   		checkboxTableViewer.getContentProvider();	   	
	   	Object[] objects = contentProvider.getElements(null);
	   	// si aggiungono tutti gli oggetti da visualizzare
	   	checkboxTableViewer.add(objects);
		}
	
	public Tool getTool() 
		{
		return tool;
		}

	public Method[] getMethodsSelection() 
		{
		ExtractedString[] extractedStrings = getSelection();
		List<Method> list = ((ExtractedStringsContentProviderM)
   			checkboxTableViewer.getContentProvider()).getMethodsList();
		List<Method> methods = new ArrayList<Method>();
		for (int i = 0; i < list.size(); i++)
   			{
			String nome = list.get(i).getName();
			for (int j = 0; j < extractedStrings.length; j++)
   				{
				String nome2 = extractedStrings[j].getName();
				if (nome.equals(nome2))
   					{
					methods.add(list.get(i));
   					}
   				}
   			}
		Method[] methods2 = new Method[methods.size()];
		methods.toArray(methods2);
		return methods2;
		}

	/**
	 * Return the currently selected strings.
	 */
	private ExtractedString[] getSelection() 
   		{
	   	Object[] checked = checkboxTableViewer.getCheckedElements();
	   	int count = checked.length;
	   	ExtractedString[] extracted = new ExtractedString[count];
	   	System.arraycopy(checked, 0, extracted, 0, count);
	   	return extracted;
   		}	
	
	@Override
	public boolean canFlipToNextPage() 
		{
		return true;
		}

	@Override
	public void dispose() 
		{
		if (this.paramsNode != null)
			this.paramsNode.dispose();
		super.dispose();
		}

	@Override
	public IWizardPage getNextPage() 
		{
		this.paramsNode = new ParamsNode(
				this.serversWizardPage,
				this.toolsListWizardPage,
				this.validationWizardPage,this.methodsWizardlist);
		this.paramsNode.setDialog(getDialog());
		return this.paramsNode.getStartingPage();
		}

	@Override
	public IWizardNode getSelectedNode() 
		{
		return this.paramsNode;
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
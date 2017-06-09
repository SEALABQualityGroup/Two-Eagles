package it.univaq.wizards.pages;

import factories.ContentProviderToolsFatory;
import it.univaq.wizards.providers.ExtractedString;
import it.univaq.wizards.providers.ExtractedStringsLabelProvider;
import it.univaq.wizards.providers.IExtractedStringsContentProviderTools;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import parserReply.Tool;
import parserReply.ToolsList;

public class QNSolveToolsListWizardPage extends WizardPage
	{
	
	private CheckboxTableViewer checkboxTableViewer;
	
	private QNSolveServersWizardPage serversWizardPage;
	
   	// il fornitore di contenuto di questa tabella corrisponde ad un'array
   	// di elementi ExtractedString, che sono dei bean
   	// che consistono di una chiave ed un valore
   	// si crea un oggetto ExtractedStringsContentProvider
   	// con il nome e la descrizione di ogni tool
   	// presente nella lista dei tool fornita da weasel
   	// per il testing cambiare anche qui il fornitore
   	private IExtractedStringsContentProviderTools contentProviderT;
	
	public QNSolveToolsListWizardPage(QNSolveServersWizardPage serversWizardPage) 
		{
		super("QNSolveToolsListWizardPage");
		setTitle("Weasel tools list");
		setDescription("Select the Tools to be used for "
				+"Queueing Network solving");
		this.serversWizardPage = serversWizardPage;
	   	// il fornitore di contenuto di questa tabella corrisponde ad un'array
	   	// di elementi ExtractedString, che sono dei bean
	   	// che consistono di una chiave ed un valore
	   	// si crea un oggetto ExtractedStringsContentProvider
	   	// con il nome e la descrizione di ogni tool
	   	// presente nella lista dei tool fornita da weasel
	   	// per il testing cambiare anche qui il fornitore
	   	this.contentProviderT = ContentProviderToolsFatory.getExtractedStringsContentProviderTools(
					this.serversWizardPage);
		}	

   /**
    * Creates the top level control for this dialog page under the given parent
    * composite, then calls <code>setControl</code> so that the created control
    * can be accessed via <code>getControl</code>
    * 
    * @param parent
    *           the parent composite
    */
   public void createControl(Composite parent) 
   		{
	   	Composite container = new Composite(parent, SWT.NULL);
	   	container.setLayout(new FormLayout());
	   	setControl(container);
	   	// crea una tabella a due colonne con elementi con
	   	// checkbox
	   	checkboxTableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
	   	checkboxTableViewer.setContentProvider(this.contentProviderT);
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
   
   /**
    * 
    * @return
    */
   public Tool[] getToolsSelection()
   		{
	   	ExtractedString[] extractedStrings = getSelection();
	   	// per il testing cambiare anche qui il fornitore
	   	ToolsList toolsList = ((IExtractedStringsContentProviderTools)
	   			checkboxTableViewer.getContentProvider()).getToolsList();
	   	Tool[] tools = new Tool[extractedStrings.length];
	   	for (int i = 0; i < extractedStrings.length; i++)
	   		{
	   		String nome = extractedStrings[i].getName();
	   		Tool tool = toolsList.getToolFromName(nome);
	   		tools[i] = tool;
	   		}
	   	return tools;
   		}
   
   public boolean isResponded()
	   	{
	   	return this.contentProviderT.isResponded();
	   	}
	}

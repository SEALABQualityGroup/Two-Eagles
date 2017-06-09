package it.univaq.wizards.pages;

import it.univaq.wizards.nodes.SummaryNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import parserReply.Method;
import parserReply.Params;
import parserReply.SolveP;
import parserReply.Tool;

/**
 * @author Mirko
 *
 */
public class QNSolveToolParamsWizardPageLast
	extends WizardSelectionPage
	implements IQNSolveToolParamsWizardPage 
	{

	private SummaryNode summaryNode;

	private QNSolveServersWizardPage serversWizardPage;
	
	private QNSolveToolsListWizardPage toolsListWizardPage;
	
	private QNSolveValidationWizardPage validationWizardPage;
	
	private List<IQNSolveMethodsWizardPage> methodsWizardlist;
	
	private List<IQNSolveToolParamsWizardPage> paramsWizardList;
	
	private Tool tool;
	
	private Method metodo;
	
	private List<Text> parametri;
	
	private Text separatore;
	
	private WizardDialog wizardDialog;

	public QNSolveToolParamsWizardPageLast(
			Tool tool,
			QNSolveServersWizardPage serversWizardPage,
			QNSolveToolsListWizardPage toolsListWizardPage,
			QNSolveValidationWizardPage validationWizardPage,
			List<IQNSolveMethodsWizardPage> methodsWizardlist,
			List<IQNSolveToolParamsWizardPage> paramsWizardList
			) 
		{
		super("QNSolveToolParamsWizardPage "+tool.getName());
		this.tool = tool;
		setTitle(tool.getName()+" tool");
		setDescription("Specify the parameters values " +
				"for the "+tool.getName()+" tool");
		this.serversWizardPage = serversWizardPage;
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		this.methodsWizardlist = methodsWizardlist;
		this.paramsWizardList = paramsWizardList;
		}

	public QNSolveToolParamsWizardPageLast(
			Tool tool,
			Method method,
			QNSolveServersWizardPage serversWizardPage,
			QNSolveToolsListWizardPage toolsListWizardPage,
			QNSolveValidationWizardPage validationWizardPage,
			List<IQNSolveMethodsWizardPage> methodsWizardlist,
			List<IQNSolveToolParamsWizardPage> paramsWizardList
			) 
		{
		super("QNSolveToolParamsWizardPage "+tool.getName());
		this.tool = tool;
		this.metodo = method;
		setTitle(tool.getName()+" tool");
		setDescription("Specify the parameters values " +
				"for the "+tool.getName()+" tool with "+method.getName()+" method.");
		this.serversWizardPage = serversWizardPage;
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		this.methodsWizardlist = methodsWizardlist;
		this.paramsWizardList = paramsWizardList;
		}

	@Override
	public void createControl(Composite parent) 
		{
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
	
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
	
		container.setLayout(layout);
			
		popolaParametri(container);
		}

	// string è il nome del metodo
	private void popolaParametri(Composite composite)
		{
		// si costruisce la tabella per la specifica dei parametri
		// si imposta l'etichetta per i parametri
		Label label = new Label(composite,SWT.LEFT);
		label.setText("PARAMETERS ");
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		label.setLayoutData(data);
			
		// si crea l'intestazione per i parametri
		Label label2 = new Label(composite,SWT.LEFT);
		label2.setText("Name");
		GridData data2 = new GridData();
		data2.horizontalAlignment = GridData.FILL;
		data2.horizontalSpan = 1;
		label2.setLayoutData(data2);
	    			
		Label label3 = new Label(composite,SWT.LEFT);
		label3.setText("Type");
		GridData data3 = new GridData();
		data3.horizontalAlignment = GridData.FILL;
		data3.horizontalSpan = 1;
		label3.setLayoutData(data3);
    			
		Label label4 = new Label(composite,SWT.LEFT);
		label4.setText("Value");
		GridData data4 = new GridData();
		data4.horizontalAlignment = GridData.FILL;
		data4.horizontalSpan = 1;
		label4.setLayoutData(data4);
   			
		this.parametri = new ArrayList<Text>();
		
		// se metod è null ciò che segue non va eseguito
		if (this.metodo != null)
			{
			for (Params params : this.metodo.getParamsList())
				{
				String string3 = params.getName();
				String string4 = params.getType();
	    				
				Label label5 = new Label(composite,SWT.LEFT);
				label5.setText(string3);
				GridData data5 = new GridData();
				data5.horizontalAlignment = GridData.FILL;
				data5.horizontalSpan = 1;
				label5.setLayoutData(data5);
				
				Label label6 = new Label(composite,SWT.LEFT);
				label6.setText(string4);
				GridData data6 = new GridData();
				data6.horizontalAlignment = GridData.FILL;
				data6.horizontalSpan = 1;
				label6.setLayoutData(data6);
				
				Text text = new Text(composite,SWT.LEFT);
				GridData data7 = new GridData();
				data7.horizontalAlignment = GridData.FILL;
				data7.horizontalSpan = 1;
				text.setLayoutData(data7);
				
				this.parametri.add(text);
				}
		
			// si aggiunge una linea per la scelta di un separatore nel caso in cui
			// ci sia più di un parametro
			// come default si considera la stringa vuota come separatore
			if (this.metodo.getParamsList().size() > 1)
				{
				Label label5 = new Label(composite,SWT.LEFT);
				label5.setText("Choose a parameters separator (default is space): ");
				GridData dataGridData = new GridData();
				dataGridData.horizontalAlignment = GridData.FILL;
				dataGridData.horizontalSpan = 2;
				label5.setLayoutData(dataGridData);
    				
				Text text = new Text(composite,SWT.LEFT);
				GridData data7 = new GridData();
				data7.horizontalAlignment = GridData.FILL;
				data7.horizontalSpan = 1;
				text.setLayoutData(data7);
				
				this.separatore = text;
				}
			}
		else
			{
			SolveP solveP = (SolveP)tool.getSolve();
			for (Params params : solveP.getList())
				{
				String string3 = params.getName();
				String string4 = params.getType();
				
				Label label5 = new Label(composite,SWT.LEFT);
				label5.setText(string3);
				GridData data5 = new GridData();
				data5.horizontalAlignment = GridData.FILL;
				data5.horizontalSpan = 1;
				label5.setLayoutData(data5);
				
				Label label6 = new Label(composite,SWT.LEFT);
				label6.setText(string4);
				GridData data6 = new GridData();
				data6.horizontalAlignment = GridData.FILL;
				data6.horizontalSpan = 1;
				label6.setLayoutData(data6);
				
				Text text = new Text(composite,SWT.LEFT);
				GridData data7 = new GridData();
				data7.horizontalAlignment = GridData.FILL;
				data7.horizontalSpan = 1;
				text.setLayoutData(data7);
				
				this.parametri.add(text);
				}
	
			// si aggiunge una linea per la scelta di un separatore nel caso in cui
			// ci sia più di un parametro
			// come default si considera la stringa vuota come separatore
			if (solveP.getList().size() > 1)
				{
				Label label5 = new Label(composite,SWT.LEFT);
				label5.setText("Choose a parameters separator (default is space): ");
				GridData dataGridData = new GridData();
				dataGridData.horizontalAlignment = GridData.FILL;
				dataGridData.horizontalSpan = 2;
				label5.setLayoutData(dataGridData);
				
				Text text = new Text(composite,SWT.LEFT);
				GridData data7 = new GridData();
				data7.horizontalAlignment = GridData.FILL;
				data7.horizontalSpan = 1;
				text.setLayoutData(data7);
				
				this.separatore = text;
				}
			}
		}

	@Override
	public IWizardPage getNextPage() 
		{
		this.summaryNode = 
			new SummaryNode
				(
				this.serversWizardPage,
				this.toolsListWizardPage,
				this.validationWizardPage,
				this.methodsWizardlist,
				this.paramsWizardList
				);
		this.summaryNode.setDialog(getDialog());
		return this.summaryNode.getStartingPage();
		}
	
	private WizardDialog getDialog() 
		{
		return this.wizardDialog;
		}

	@Override
	public boolean canFlipToNextPage() 
		{
		return true;
		}

	@Override
	public IWizardNode getSelectedNode() 
		{
		return this.summaryNode;
		}
	
	public List<Text> getParametri() 
		{
		return parametri;
		}

	public Text getSeparatore() 
		{
		return separatore;
		}

	@Override
	public Method getMetodo() 
		{
		return this.metodo;
		}

	@Override
	public Tool getTool() 
		{
		return this.tool;
		}

	public void setDialog(WizardDialog dialog) 
		{
		this.wizardDialog = dialog;
		}
	}

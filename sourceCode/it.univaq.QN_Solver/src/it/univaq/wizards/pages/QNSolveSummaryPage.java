/**
 * 
 */
package it.univaq.wizards.pages;

import it.univaq.wizards.HeaderTreeNode;
import it.univaq.wizards.NormalTreeNode;
import it.univaq.wizards.WeaselTreeNode;
import it.univaq.wizards.providers.WeaselLabelProvider;
import it.univaq.wizards.providers.WizardContentProvider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import parserReply.Method;
import parserReply.Params;
import parserReply.SolveP;
import parserReply.Tool;

/**
 * @author Mirko
 *
 */
public class QNSolveSummaryPage 
	extends WizardPage 
	{

	private QNSolveToolsListWizardPage toolsListWizardPage;
	
	private QNSolveValidationWizardPage validationWizardPage;
	
	private List<IQNSolveMethodsWizardPage> methodsPagesList;
	
	private List<IQNSolveToolParamsWizardPage> paramsPagesList;
	
	private WeaselTreeNode root;

	// caso in cui è stata generata almeno una pagina per i metodi e una pagina per i parametri
	public QNSolveSummaryPage(QNSolveToolsListWizardPage toolsListWizardPage,
			QNSolveValidationWizardPage validationWizardPage,
			List<IQNSolveMethodsWizardPage> methodsPagesList,
			List<IQNSolveToolParamsWizardPage> paramsPagesList
			) 
		{
		super("QNSolveSummaryPage");
		setTitle("Solving summary");
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		this.methodsPagesList = methodsPagesList;
		this.paramsPagesList = paramsPagesList;
		}

	// caso in cui ogni tool selezionato non ha metodi e parametri
	public QNSolveSummaryPage(QNSolveToolsListWizardPage toolsListWizardPage,
			QNSolveValidationWizardPage validationWizardPage) 
		{
		super("QNSolveSummaryPage");
		setTitle("Solving summary");		
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		}

	// caso in cui non ci sono tools o metodi selezionati con associato un parametro
	public QNSolveSummaryPage(QNSolveToolsListWizardPage toolsListWizardPage2,
			QNSolveValidationWizardPage validationWizardPage2,
			List<IQNSolveMethodsWizardPage> list) 
		{
		super("QNSolveSummaryPage");
		setTitle("Solving summary");		
		this.toolsListWizardPage = toolsListWizardPage2;
		this.validationWizardPage = validationWizardPage2;
		this.methodsPagesList = list;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) 
		{
		Composite container = new Composite(parent, SWT.NULL);
	   	container.setLayout(new FillLayout());
	   	setControl(container);
		TreeViewer treeViewer = new TreeViewer(container);
		this.root = getInitialInput();
		WizardContentProvider wizardContentProvider = new WizardContentProvider();
		treeViewer.setContentProvider(wizardContentProvider);
		treeViewer.setLabelProvider(new WeaselLabelProvider());
		TreeNode[] treeNodes = this.root.getChildren();
		if (treeNodes != null)
			treeViewer.setInput(this.root);
		treeViewer.expandAll();
		}

	private WeaselTreeNode getInitialInput() 
		{
		HeaderTreeNode headerTreeNode = new HeaderTreeNode("Summary");
		WeaselTreeNode[] headerTreeNodeChilds = new WeaselTreeNode[0];
		List<WeaselTreeNode> headerTreeNodeChildsList = new ArrayList<WeaselTreeNode>();
		// 1) si crea un nodo di primo livello per ogni tool e convalida selezionati
		// 2) per ogni nodo di primo livello corrispondente ad un tool si crea un nodo
		// di secondo livello per ogni metodo selezionato o parametro impostato
		// 3) per ogni metodo selezionato si crea un nodo di terzo livello per ogni parametro
		// impostato
		// dapprima procediamo con le operazioni di convalida se ce ne sono.
		// se il button di convalida sintattica è stato checked si procede
		Button button = validationWizardPage.getSynValid();
		boolean c = button.getSelection();
		Button button2 = validationWizardPage.getSemValid();
		boolean b = button2.getSelection();
		if (c || b)
			{
			HeaderTreeNode headerTreeNode2 = new HeaderTreeNode("PMIF Validation Types");
			WeaselTreeNode[] headerTreeNode2Childs = new WeaselTreeNode[0];
			List<WeaselTreeNode> headerTreeNode2ChildsList = new ArrayList<WeaselTreeNode>();
			headerTreeNode2.setParent(headerTreeNode);
			headerTreeNodeChildsList.add(headerTreeNode2);
			if (c)
				{
				// aggiungiamo un elemento a headerTreeNode2 relativo alla convalida sintattica
				NormalTreeNode normalTreeNode = new NormalTreeNode("Syntactic validation");
				normalTreeNode.setParent(headerTreeNode2);
				headerTreeNode2ChildsList.add(normalTreeNode);
				}
			if (b)
				{
				// aggiungiamo un elemento a headerTreeNode2 relativo alla convalida semantica
				NormalTreeNode normalTreeNode = new NormalTreeNode("Semantic validation");
				normalTreeNode.setParent(headerTreeNode2);
				headerTreeNode2ChildsList.add(normalTreeNode);
				}
			headerTreeNode2Childs = headerTreeNode2ChildsList.toArray(headerTreeNode2Childs);
			headerTreeNode2.setChildren(headerTreeNode2Childs);
			}
		// procediamo con i tool selezionati
		Tool[] tools = this.toolsListWizardPage.getToolsSelection();
		if (tools.length > 0)
			{
			HeaderTreeNode headerTreeNode2 = new HeaderTreeNode("Selected tools");
			WeaselTreeNode[] headerTreeNode2Childs = new WeaselTreeNode[0];
			List<WeaselTreeNode> headerTreeNode2ChildsList = new ArrayList<WeaselTreeNode>();
			headerTreeNode2.setParent(headerTreeNode);
			headerTreeNodeChildsList.add(headerTreeNode2);
			for (Tool tool : tools)
				{
				String string = tool.getName();
				NormalTreeNode normalTreeNode = new NormalTreeNode(string);
				WeaselTreeNode[] normalTreeNodeChilds = new WeaselTreeNode[0];
				List<WeaselTreeNode> normalTreeNodeChildsList = new ArrayList<WeaselTreeNode>();
				normalTreeNode.setParent(headerTreeNode2);
				headerTreeNode2ChildsList.add(normalTreeNode);
				if (tool.getMethodsNummber() == 0)
					{
					if (tool.parameters())
						{
						// si preleva la pagina QNSolveToolParamsWizardPage che abbia
						// tool come aggregato. Se esiste è unica per precondizione
						for (IQNSolveToolParamsWizardPage paramsWizardPage : this.paramsPagesList)
							{
							Tool tool2 = paramsWizardPage.getTool();
							if (tool.equals(tool2))
								{
								List<Text> list = paramsWizardPage.getParametri();
								// il separatore può essere null nel caso in cui ci sia
								// solo un parametro
								Text text = paramsWizardPage.getSeparatore();
								SolveP solveP = (SolveP)tool2.getSolve();
								List<Params> list2 = solveP.getList();
								HeaderTreeNode headerTreeNode3 = new HeaderTreeNode("Parameters setting");
								WeaselTreeNode[] headerTreeNode3Childs = new WeaselTreeNode[0];
								List<WeaselTreeNode> headerTreeNode3ChildsList = new ArrayList<WeaselTreeNode>();
								if (list.size() > 0)
									{
									headerTreeNode3.setParent(normalTreeNode);
									normalTreeNodeChildsList.add(headerTreeNode3);
									}
								// per precondizione list e list2 hanno la stessa taglia
								// e sono associati tramite indice
								for (int i = 0; i < list.size(); i++)
									{
									Text text2 = list.get(i);
									String string2 = text2.getText();
									Params params = list2.get(i);
									String string3 = params.getName();
									NormalTreeNode normalTreeNode2 = new NormalTreeNode(string3+" = "+string2);
									normalTreeNode2.setParent(headerTreeNode3);
									headerTreeNode3ChildsList.add(normalTreeNode2);
									}
								if (text != null)
									{
									String string2 = text.getText();
									NormalTreeNode normalTreeNode2 = new NormalTreeNode("separator = "+string2);
									normalTreeNode2.setParent(headerTreeNode3);
									headerTreeNode3ChildsList.add(normalTreeNode2);
									}
								headerTreeNode3Childs = headerTreeNode3ChildsList.toArray(headerTreeNode3Childs);
								headerTreeNode3.setChildren(headerTreeNode3Childs);
								}
							}
						}
					}
				else
					{
					// caso in cui il tool selezionato ha almeno un metodo di risoluzione
					for (IQNSolveMethodsWizardPage methodsWizardPage : this.methodsPagesList)
						{
						Tool tool2 = methodsWizardPage.getTool();
						if (tool.equals(tool2))
							{
							// prelevo tutti i metodi selezionati di methodsWizardPage
							Method[] methods = methodsWizardPage.getMethodsSelection();
							HeaderTreeNode headerTreeNode3 = new HeaderTreeNode("Selected methods");
							WeaselTreeNode[] headerTreeNode3Child = new WeaselTreeNode[0];
							List<WeaselTreeNode> headerTreeNode3ChildList = new ArrayList<WeaselTreeNode>();
							if (methods.length > 0)
								{
								headerTreeNode3.setParent(normalTreeNode);
								normalTreeNodeChildsList.add(headerTreeNode3);
								}
							else
								{
								normalTreeNode.setText(normalTreeNode.getText()+ " (The tool will not be used: " +
										"no selected method)");
								}
							for (Method method : methods)
								{
								String string2 = method.getName();
								NormalTreeNode normalTreeNode2 = new NormalTreeNode(string2);
								WeaselTreeNode[] normalTreeNode2Childs = new WeaselTreeNode[0];
								List<WeaselTreeNode> normalTreeNode2ChildsList = new ArrayList<WeaselTreeNode>();
								normalTreeNode2.setParent(headerTreeNode3);
								headerTreeNode3ChildList.add(normalTreeNode2);
								// caso in cui il metodo selezionato ha almeno un parametro associato 
								if (method.getParamsList().size() > 0)
									{
									// tra tutte le pagine contenute in this.paramsPagesList
									// cerco quella che hanno method e tool come aggregato
									for (IQNSolveToolParamsWizardPage paramsWizardPage : this.paramsPagesList)
										{
										Method method2 = paramsWizardPage.getMetodo();
										Tool tool3 = paramsWizardPage.getTool();
										if (method.equals(method2) && tool.equals(tool3))
											{
											List<Text> list = paramsWizardPage.getParametri();
											// il separatore può essere null nel caso in cui ci sia
											// solo un parametro
											Text text = paramsWizardPage.getSeparatore();
											List<Params> list2 = method2.getParamsList();
											HeaderTreeNode headerTreeNode4 = new HeaderTreeNode("Parameters setting");
											WeaselTreeNode[] headerTreeNode4Childs = new WeaselTreeNode[0];
											List<WeaselTreeNode> headerTreeNode4ChildsList = new ArrayList<WeaselTreeNode>();
											if (list.size() > 0)
												{
												headerTreeNode4.setParent(normalTreeNode2);
												normalTreeNode2ChildsList.add(headerTreeNode4);
												}
											// per precondizione list e list2 hanno la stessa taglia
											// e sono associati tramite indice
											for (int i = 0; i < list.size(); i++)
												{
												Text text2 = list.get(i);
												Params params = list2.get(i);
												String string3 = text2.getText();
												String string4 = params.getName();
												NormalTreeNode normalTreeNode3 = 
													new NormalTreeNode(string4+" = "+string3);
												normalTreeNode3.setParent(headerTreeNode4);
												headerTreeNode4ChildsList.add(normalTreeNode3);
												}
											if (text != null)
												{
												String string3 = text.getText();
												NormalTreeNode normalTreeNode3 = 
													new NormalTreeNode("separator = "+string3);
												normalTreeNode3.setParent(headerTreeNode4);
												headerTreeNode4ChildsList.add(normalTreeNode3);
												}
											headerTreeNode4Childs = headerTreeNode4ChildsList.toArray(headerTreeNode4Childs);
											headerTreeNode4.setChildren(headerTreeNode4Childs);
											}
										}
									}
								normalTreeNode2Childs = normalTreeNode2ChildsList.toArray(normalTreeNode2Childs);
								normalTreeNode2.setChildren(normalTreeNode2Childs);
								}
							headerTreeNode3Child = headerTreeNode3ChildList.toArray(headerTreeNode3Child);
							headerTreeNode3.setChildren(headerTreeNode3Child);
							}
						}
					}
				normalTreeNodeChilds = normalTreeNodeChildsList.toArray(normalTreeNodeChilds);
				normalTreeNode.setChildren(normalTreeNodeChilds);
				}
			headerTreeNode2Childs = headerTreeNode2ChildsList.toArray(headerTreeNode2Childs);
			headerTreeNode2.setChildren(headerTreeNode2Childs);
			}
		headerTreeNodeChilds = headerTreeNodeChildsList.toArray(headerTreeNodeChilds);
		headerTreeNode.setChildren(headerTreeNodeChilds);
		return headerTreeNode;
		}

	public WeaselTreeNode getRoot() 
		{
		return root;
		}
	}

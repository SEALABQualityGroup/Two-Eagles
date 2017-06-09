package it.univaq.wizards.nodes;

import it.univaq.wizards.QNSolveWizardDialog;
import it.univaq.wizards.WeaselTreeNode;
import it.univaq.wizards.pages.IQNSolveMethodsWizardPage;
import it.univaq.wizards.pages.IQNSolveToolParamsWizardPage;
import it.univaq.wizards.pages.QNSolveMethodsWizardPage;
import it.univaq.wizards.pages.QNSolveMethodsWizardPageLast;
import it.univaq.wizards.pages.QNSolveServersWizardPage;
import it.univaq.wizards.pages.QNSolveSummaryPage;
import it.univaq.wizards.pages.QNSolveToolParamsWizardPage;
import it.univaq.wizards.pages.QNSolveToolParamsWizardPageLast;
import it.univaq.wizards.pages.QNSolveToolsListWizardPage;
import it.univaq.wizards.pages.QNSolveValidationWizardPage;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;

import parserReply.Method;
import parserReply.ParserWeaselReply;
import parserReply.Tool;
import qNSolver.QNSolverPortProxy;
import qNSolver.WeaselProxy;
import utility.Metodi;
import utility.Properties;

public class MethodsNode 
	extends Wizard 
	implements IWizardNode,
		IWorkbenchWizard {

	private QNSolveServersWizardPage serversWizardPage;
	
	private QNSolveToolsListWizardPage toolsListWizardPage;
	
	private QNSolveValidationWizardPage validationWizardPage;
	
	private List<IQNSolveMethodsWizardPage> methodsWizardPages;
	
	private List<IQNSolveToolParamsWizardPage> toolParamsWizardPages;

	private QNSolveSummaryPage solveSummaryPage;
	
	private WizardDialog wizardDialog;
	
	public MethodsNode(QNSolveServersWizardPage serversWizardPage,
			QNSolveToolsListWizardPage toolsListWizardPage, 
			QNSolveValidationWizardPage validationWizardPage) 
		{
		super();
		// si genera una pagina QNSolveMethodsWizardPage per ogni tool selezionato
		// che abbia almeno un metodo selezionabile
		Tool[] tools = toolsListWizardPage.getToolsSelection();
		List<IQNSolveMethodsWizardPage> list1 = new ArrayList<IQNSolveMethodsWizardPage>();
		List<IQNSolveToolParamsWizardPage> list2 = new ArrayList<IQNSolveToolParamsWizardPage>();
		for (Tool tool : tools)
			{
			if (tool.getMethodsNummber() > 0)
				{
				QNSolveMethodsWizardPage methodsWizardPage = new QNSolveMethodsWizardPage(tool);
				list1.add(methodsWizardPage);
				}
			}
		// bisogna considerare il caso in cui ogni tool selezionato non ha nessun metodo
		// ed esiste almeno un tool con almeno un parametro
		if (creazioneParamsNode(tools))
			{
			for (int i = 0; i < tools.length; i++)
				{
				Tool tool = tools[i];
				// si verifica se tool abbia almeno un parametro
				if (tool.parameters())
					{
					// in questo caso si usa un'altra selezione
					QNSolveToolParamsWizardPage paramsWizardPage = 
						new QNSolveToolParamsWizardPage(tool);
					list2.add(paramsWizardPage);
					}
				}
			}
		// l'ultimo elemento di list1 deve essere una pagina Last
		if (!list1.isEmpty())
			{
			IQNSolveMethodsWizardPage solveMethodsWizardPage = list1.get(list1.size() - 1);
			Tool tool = solveMethodsWizardPage.getTool();
			QNSolveMethodsWizardPageLast solveMethodsWizardPageLast = 
				new QNSolveMethodsWizardPageLast(tool,
						serversWizardPage,
						toolsListWizardPage,
						validationWizardPage,
						list1);
			list1.remove(list1.size() - 1);
			solveMethodsWizardPageLast.setDialog(getDialog());
			list1.add(solveMethodsWizardPageLast);
			}
		// l'ultimo elemento di list2 deve essere una pagina Last
		if (!list2.isEmpty())
			{
			IQNSolveToolParamsWizardPage solveToolParamsWizardPage = list2.get(list2.size() - 1);
			Tool tool = solveToolParamsWizardPage.getTool();
			QNSolveToolParamsWizardPageLast solveToolParamsWizardPageLast =
				new QNSolveToolParamsWizardPageLast(tool,
						serversWizardPage,
						toolsListWizardPage,
						validationWizardPage,
						list1,
						list2);
			list2.remove(list2.size() - 1);
			solveToolParamsWizardPageLast.setDialog(getDialog());
			list2.add(solveToolParamsWizardPageLast);
			}
		// se list1 e list2 sono vuoti, istanziamo una pagina di sommario
		List<QNSolveSummaryPage> list3 = new ArrayList<QNSolveSummaryPage>();
		if (list1.isEmpty() && list2.isEmpty())
			{
			QNSolveSummaryPage solveSummaryPage = new QNSolveSummaryPage(toolsListWizardPage, 
					validationWizardPage);
			this.solveSummaryPage = solveSummaryPage;
			list3.add(solveSummaryPage);
			}
		// a seconda dei tool selezionati
		// si imposta questo wizard con le nuove pagine
		// si istanzia un oggetto StructuredSelection
		// la selezione dipende dalla taglia di list1, list2, list3 ha taglia maggiore di zero
		StructuredSelection structuredSelection = null;
		if (list1.size() > 0)
			structuredSelection = new StructuredSelection(list1);
		else if (list2.size() > 0)
			structuredSelection = new StructuredSelection(list2);
		else
			structuredSelection = new StructuredSelection(list3);
		// si chiama il metodo init
		IWorkbench workbench = PlatformUI.getWorkbench();
		init(workbench, structuredSelection);
		this.serversWizardPage = serversWizardPage;
		this.toolsListWizardPage = toolsListWizardPage;
		this.validationWizardPage = validationWizardPage;
		this.methodsWizardPages = list1;
		this.toolParamsWizardPages = list2; 
		}

	// restituisce true se e solo se ogni elemento di tools non ha nessun metodo
	private boolean creazioneParamsNode(Tool[] tools) 
		{
		for (Tool tool : tools)
			{
			if (tool.getMethodsNummber() > 0) return false;
			}
		return true;
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

	@SuppressWarnings("unchecked")
	@Override
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
	public Point getExtent() 
		{
		return new Point(-1,-1);
		}

	@Override
	public boolean performFinish() 
		{
		// per il testing bisogna ricordarsi 
		// l'impostazione del server selezionato
		// quando si chiama il server

		// se è stato eseguito il performFinish del ParamsNode si restituisce true
		if (QNSolveWizardDialog.esitoParamsNode)
			return true;
		
		// tale metodo deve restituire false se non viene eseguita nessuna
		// validazione e nessuna risoluzione della QN
		boolean b = false;
		StringWriter stringWriter = new StringWriter();
		stringWriter.write("\n - "+this.serversWizardPage.getCombo().getText().toUpperCase()
				+" WEASEL RESPONSE \n\n");
		// dapprima procediamo con le operazioni di convalida se ce ne sono.
		// se il button di convalida sintattica è stato checked si procede con
		// la convalida sintattica
		Button button = validationWizardPage.getSynValid();
		boolean c = button.getSelection();
		// effettuiamo l'eventuale convalida sintattica
		QNSolverPortProxy proxy = WeaselProxy.getProxy(this.serversWizardPage.getCombo().getText());		
		if (c)
			{
			try {
				String string = proxy.validateSyntax_(this.validationWizardPage.getPmif(), WeaselProxy.tipoModello);
				boolean d = proxy.responded();
				if (!d)
					{
			   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000 +" seconds");
			   		this.wizardDialog.close();
			   		return true;
					}
				ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
				string = parserWeaselReply.getMessage();
				stringWriter.write(string+"\n\n SYNTACTIC VALIDATION DONE \n\n\n");
				} 
			catch (RemoteException e) 
				{
				e.printStackTrace();
				return false;
				}
			// il finish può essere accettato
			b = true;
			}
		// se il button di convalida semantica è stato checked si procede con
		// la convalida semantica
		Button button2 = validationWizardPage.getSemValid();
		boolean d = button2.getSelection();
		// effetuiamo l'eventuale convalida semantica
		if (d)
			{
			try {
				String string = proxy.validateSemantic_(validationWizardPage.getPmif(), WeaselProxy.tipoModello);
				boolean e = proxy.responded();
				if (!e)
					{
			   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
			   		this.wizardDialog.close();
			   		return true;
					}
				ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
				string = parserWeaselReply.getMessage();
				stringWriter.write(string+"\n\n SEMANTIC VALIDATION DONE \n\n\n");
				} 
			catch (RemoteException e) 
				{
				e.printStackTrace();
				return false;
				}
			// il finish può essere accettato
			b = true;
			}
		
		// si esegue il seguente pezzo di codice se e solo se toolParamsWizardPages.size() > 0
		if (toolParamsWizardPages.size() == 0)
			{
			// si esegue una risoluzione per ogni tool selezionato nel seguente modo:
			// 1) se non ha nessun metodo di risoluzione devo procedere nel seguente modo:
			// 1.1) se non ha nessun parametro eseguo la funzione solve con metodo e parametro
			// uguale alla stringa vuota
			// 1.2) se ha parametri restituisco false
			// 2) se ha almeno un metodo di risoluzione procedo nel seguente modo
			// 2.1) sia m un suo metodo di risoluzione selezionato, allora procedo nel seguente modo:
			// 2.1.1) se m ha parametri restituisco false
			// 2.1.2) se m non ha parametri eseguo la funzione solve con metodo uguale a m e
			// parametri uguale alla stringa vuota
		
			// si prelevano i tools selezionati da toolsListWizardPage
			Tool[] tools = this.toolsListWizardPage.getToolsSelection();
			for (Tool tool : tools)
				{
				if (tool.getMethodsNummber() == 0)
					{
					if (!tool.parameters())
						{
						// 1.1) se non ha nessun parametro eseguo la funzione solve con metodo e parametro
						// uguale alla stringa vuota
						String string = null;
						try {
							string = proxy.solve_(validationWizardPage.getPmif(), 
									WeaselProxy.tipoModello, tool.getName(), "", "");
							boolean e = proxy.responded();
							if (!e)
								{
						   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
						   		this.wizardDialog.close();
						   		return true;
								}
							} 
						catch (RemoteException e) 
							{
							e.printStackTrace();
							return false;
							}
						ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
						string = parserWeaselReply.getMessage();
						stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING DONE \n\n\n");
						}
					else
						return false;
					}
				else
					{
					// tra tutte le pagine IQNSolveMethodsWizardPage prelevo quelle che hanno
					// tool come aggregato
					for (IQNSolveMethodsWizardPage methodsWizardPage : this.methodsWizardPages)
						{
						Tool tool2 = methodsWizardPage.getTool();
						if (tool.equals(tool2))
							{
							// prelevo tutti i metodi selezionati di methodsWizardPage
							Method[] methods = methodsWizardPage.getMethodsSelection();
							for (Method method : methods)
								{
								// tra tutte le pagine contenute in QNSolveToolParamsWizardPage
								// cerco quella che hanno method e tool come aggregato
								if (method.getParamsList().size() > 0)
									return false;
								else
									{
									String string = null;
									try {
										string = proxy.solve_(validationWizardPage.getPmif(), 
												WeaselProxy.tipoModello, tool.getName(), method.getName(), "");
										boolean e = proxy.responded();
										if (!e)
											{
									   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
									   		this.wizardDialog.close();
									   		return true;
											}
										} 
									catch (RemoteException e) 
										{
										e.printStackTrace();
										return false;
										}
									ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
									string = parserWeaselReply.getMessage();
									stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING WITH "+method.getName()+" METHOD DONE \n\n\n");
									}
								}
							}
						}
					}
				b = true;			
				}
			}
		else
			{
			// il seguente metodo è simile a quello di performFinish di ParamsNode
			
			// si esegue una risoluzione per ogni tool selezionato nel seguente modo:
			// 1) se non ha nessun metodo di risoluzione devo procedere nel seguente modo:
			// 1.1) se non ha nessun parametro eseguo la funzione solve con metodo e parametro
			// uguale alla stringa vuota
			// 1.2) se ha parametri eseguo la funzione solve con metodo uguale alla stringa vuota
			// e parametro uguale alla concatenazione dei valori dei parametri con il delimitatore
			// selezionato
			// 2) se ha almeno un metodo di risoluzione procedo nel seguente modo
			// 2.1) sia m un suo metodo di risoluzione selezionato, allora procedo nel seguente modo:
			// 2.1.1) se m ha parametri eseguo la funzione solve con metodo uguale a m e
			// parametri uguale alla concatenazione dei valori dei parametri con il delimitatore selezionato
			// 2.1.2) se m non ha parametri eseguo la funzione solve con metodo uguale a m e
			// parametri uguale alla stringa vuota
			
			// si prelevano i tools selezionati da toolsListWizardPage
			Tool[] tools = this.toolsListWizardPage.getToolsSelection();
			for (Tool tool : tools)
				{
				if (tool.getMethodsNummber() == 0)
					{
					if (!tool.parameters())
						{
						String string = null;
						try {
							string = proxy.solve_(validationWizardPage.getPmif(), 
								WeaselProxy.tipoModello, tool.getName(), "", "");
							boolean e = proxy.responded();
							if (!e)
								{
						   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
						   		this.wizardDialog.close();
						   		return true;
								}
							} 
						catch (RemoteException e) 
							{
							e.printStackTrace();
							return false;
							}
						ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
						string = parserWeaselReply.getMessage();
						stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING DONE \n\n\n");
						}
					else
						{
						// si preleva la pagina QNSolveToolParamsWizardPage che abbia
						// tool come aggregato. Se esiste è unica per precondizione
						for (IQNSolveToolParamsWizardPage paramsWizardPage : this.toolParamsWizardPages)
							{
							Tool tool2 = paramsWizardPage.getTool();
							if (tool.equals(tool2))
								{
								// si prelevano i parametri nell'ordine di visualizzazione
								// e il delimitatore scelto
								String parametroSolve = new String();
								List<Text> list = paramsWizardPage.getParametri();
								// se il separatore è la stringa vuota si considera
								// lo spazio come separatore
								Text text = paramsWizardPage.getSeparatore();
								String separatore = new String();
								if (text != null)
									separatore = text.getText();
								if (separatore.equals(new String()))
									separatore = " ";
								for (Text text2 : list)
									{
									parametroSolve = parametroSolve.concat(text2.getText()+separatore);
									}
								String string = null;
								try {
									string = proxy.solve_(validationWizardPage.getPmif(), 
											WeaselProxy.tipoModello, tool.getName(), "", parametroSolve);
									boolean e = proxy.responded();
									if (!e)
										{
								   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
								   		this.wizardDialog.close();
								   		return true;
										}
									} 
								catch (RemoteException e) 
									{
									e.printStackTrace();
									return false;
									}
								ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
								string = parserWeaselReply.getMessage();
								stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING DONE \n\n\n");
								}
							}
						}
					}
				else
					{
					// tra tutte le pagine IQNSolveMethodsWizardPage prelevo quelle che hanno
					// tool come aggregato
					for (IQNSolveMethodsWizardPage methodsWizardPage : this.methodsWizardPages)
						{
						Tool tool2 = methodsWizardPage.getTool();
						if (tool.equals(tool2))
							{
							// prelevo tutti i metodi selezionati di methodsWizardPage
							Method[] methods = methodsWizardPage.getMethodsSelection();
							for (Method method : methods)
								{
								// tra tutte le pagine contenute in QNSolveToolParamsWizardPage
								// cerco quella che hanno method e tool come aggregato
								if (method.getParamsList().size() > 0)
									{
									for (IQNSolveToolParamsWizardPage paramsWizardPage : this.toolParamsWizardPages)
										{
										Method method2 = paramsWizardPage.getMetodo();
										Tool tool3 = paramsWizardPage.getTool();
										if (method.equals(method2) && tool.equals(tool3))
											{
											// si prelevano i parametri nell'ordine di visualizzazione
											// e il delimitatore scelto
											String parametroSolve = new String();
											List<Text> list = paramsWizardPage.getParametri();
											Text text = paramsWizardPage.getSeparatore();
											// se il separatore è null si considera
											// lo spazio come separatore
											String separatore = new String();
											if (text != null)
												separatore = text.getText();
											if (separatore.equals(new String()))
												separatore = " ";
											for (Text text2 : list)
												{
												parametroSolve = parametroSolve.concat(text2.getText()+separatore);
												}
											String string = null;
											try {
												string = proxy.solve_(validationWizardPage.getPmif(), 
													WeaselProxy.tipoModello, tool.getName(), method.getName(), parametroSolve);
												boolean e = proxy.responded();
												if (!e)
													{
											   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
											   		this.wizardDialog.close();
											   		return true;
													}
												} 
											catch (RemoteException e) 
												{
												e.printStackTrace();
												return false;
												}
											ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
											string = parserWeaselReply.getMessage();
											stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING WITH "+method.getName()+" METHOD DONE \n\n\n");
											}
										}
									}
								else
									{
									String string = null;
									try {
										string = proxy.solve_(validationWizardPage.getPmif(), 
											WeaselProxy.tipoModello, tool.getName(), method.getName(), "");
										boolean e = proxy.responded();
										if (!e)
											{
									   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
									   		this.wizardDialog.close();
									   		return true;
											}
										} 
									catch (RemoteException e) 
										{
										e.printStackTrace();
										return false;
										}
									ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string);
									string = parserWeaselReply.getMessage();
									stringWriter.write(string+"\n\n "+tool.getName()+" SOLVING WITH "+method.getName()+" METHOD DONE \n\n\n");
									}
								}
							}
						}
					}
				b = true;			
				}
			}
		// si stampa lo stringWriter su console e lo si memorizza
		if (b)
			{
			IFile file = validationWizardPage.getFile();
			Metodi.stampaEstoricizza(stringWriter, file);
			}
		QNSolveWizardDialog.esitoMethodsNode = b;
		return b;
		}
	
	@Override
	public boolean canFinish() 
		{
		// this.solveSummaryPage può essere null
		if (this.solveSummaryPage == null)
			return false;
		else
			{
			WeaselTreeNode weaselTreeNode = this.solveSummaryPage.getRoot();
			TreeNode[] treeNodes =  weaselTreeNode.getChildren();
			if (treeNodes == null)
				return false;
			else
				{
				if (treeNodes.length > 0)
					return true;
				else
					return false;
				}
			}
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

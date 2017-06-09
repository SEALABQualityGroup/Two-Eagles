package it.univaq.editors;

import java.rmi.RemoteException;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;


import parserReply.ParserWeaselReply;
import qNSolver.WeaselProxy;
import qNSolver.qNSolverWin.QNSolverPortProxyWin;
import utility.Metodi;
import utility.Properties;



public class PMIFEditor extends TextEditor 
	{

	private ColorManager colorManager;
	
	public PMIFEditor() 
		{
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
		}
	
	public void dispose() 
		{
		colorManager.dispose();
		super.dispose();
		}

	@Override
	public void createPartControl(Composite parent) 
		{
		super.createPartControl(parent);
		}

	@Override
	public void setFocus() 
		{
		super.setFocus();
		// disattiviamo il controllo di attivazione
//		controlloAttivazione();
		}
	
	@SuppressWarnings("unused")
	private void controlloAttivazione()
		{
		// si utilizza weasel per verificare se l'input
		// dell'editor è sintatticamente
		// corretto
		// si ottiene la stringa relativa a resource
		IDocumentProvider documentProvider = this.getDocumentProvider();
		IEditorInput editorInput = this.getEditorInput();
		IDocument document = documentProvider.getDocument(editorInput.getAdapter(Object.class));
		String string = document.get();
		IWorkbenchPartSite workbenchPartSite = this.getSite();
		IWorkbenchPage page = workbenchPartSite.getPage();
		QNSolverPortProxyWin portProxy = new QNSolverPortProxyWin();
		try {
			String string2 = portProxy.validateSyntax_(string, WeaselProxy.tipoModello);
			boolean b1 = portProxy.responded();
			if (!b1)
				{
		   		Metodi.stampaSuConsole("Timeout: Weasel do not responded within "+Properties.timeout / 1000+" seconds");
		   		return;
				}
			// si preleva il messaggio della convalida sintattica e si stampa su console
			ParserWeaselReply parserWeaselReply = new ParserWeaselReply(string2);
			String string3 = parserWeaselReply.getMessage();
			Metodi.stampaSuConsole(string3);
			// se la convalida sintattica non è andata a buon fine si disattiva 
			// l'azione di risoluzione della rete di code
			boolean b = parserWeaselReply.getEsito();
			IEditorSite editorSite = getEditorSite();
			IActionBars actionBars = editorSite.getActionBars();
			IMenuManager menuManager = actionBars.getMenuManager();
			IMenuManager menuManager2 = menuManager.findMenuUsingPath("it.univaq.TTEP.menu.twoTowers/it.univaq.TTEP.architecturalAssistantMenu");
			IContributionItem contributionItem = menuManager2.findUsingPath("it.univaq.action1");
			contributionItem.setVisible(b);
			menuManager.updateAll(true);
			} 
		catch (RemoteException e) 
			{
			String string2 = "WEASEL connecting problem: "+e.getMessage();
			Metodi.stampaSuConsole(string2);
			}
		}
	}

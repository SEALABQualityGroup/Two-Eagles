package it.univaq.from_aemilia_to_qn_plug_in.listeners;


import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener3;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class TwoTowerPerspectiveListener implements IPerspectiveListener3 {

	private AemEditorPartListener aemEditorPartListener;
	
	public TwoTowerPerspectiveListener(IWorkbenchPage workbenchPage,
			IPerspectiveDescriptor perspectiveDescriptor) 
		{
		// consideriamo solo il caso in cui la prospettiva aperta sia quella relativa
		// a twotower
		String string = perspectiveDescriptor.getId();
		if ("it.univaq.TTEP.perspectives.TwoTowersPerspective".equals(string))
			{
			if (aemEditorPartListener == null)
				{
				// bisogna considerare il caso in cui ci sia almeno un editor aem aperto.
				// In questo caso si istanzia un oggetto AemEditorPartListener, fornendo
				// un oggetto NoExtChangeListener come parametro
				// tale condizione si può verificare utilizzando il metodo getActiveEditor
				IEditorPart editorPart = workbenchPage.getActiveEditor();
				if (editorPart == null)
					{
					aemEditorPartListener = new AemEditorPartListener();
					workbenchPage.addPartListener(aemEditorPartListener);
					}
				else
					{
					aemEditorPartListener = new AemEditorPartListener(editorPart);
					workbenchPage.addPartListener(aemEditorPartListener);
					}
				}
			}
		}

	public TwoTowerPerspectiveListener() 
		{}

	@Override
	public void perspectiveClosed(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) 
		{
		// consideriamo solo il caso in cui la prospettiva chiusa sia quella relativa
		// a twotower
		String string = perspective.getId();
		if ("it.univaq.TTEP.perspectives.TwoTowersPerspective".equals(string))
			{
			if (aemEditorPartListener != null)
				{
				page.removePartListener(aemEditorPartListener);
				aemEditorPartListener = null;
				}
			}
		}

	@Override
	public void perspectiveDeactivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) 
		{
		// consideriamo solo il caso in cui la prospettiva deattivata sia quella relativa
		// a twotower
		String string = perspective.getId();
		if ("it.univaq.TTEP.perspectives.TwoTowersPerspective".equals(string))
			{
			if (aemEditorPartListener != null)
				{
				page.removePartListener(aemEditorPartListener);
				aemEditorPartListener = null;
				}
			}
		}

	@Override
	public void perspectiveOpened(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) 
		{
		// consideriamo solo il caso in cui la prospettiva aperta sia quella relativa
		// a twotower
		String string = perspective.getId();
		if ("it.univaq.TTEP.perspectives.TwoTowersPerspective".equals(string))
			{
			if (aemEditorPartListener == null)
				{
				// bisogna considerare il caso in cui ci sia almeno un editor aem aperto.
				// In questo caso si istanzia un oggetto AemEditorPartListener, fornendo
				// un oggetto NoExtChangeListener come parametro
				// tale condizione si può verificare utilizzando il metodo getActiveEditor
				IEditorPart editorPart = page.getActiveEditor();
				if (editorPart == null)
					{
					aemEditorPartListener = new AemEditorPartListener();
					page.addPartListener(aemEditorPartListener);
					}
				else
					{
					aemEditorPartListener = new AemEditorPartListener(editorPart);
					page.addPartListener(aemEditorPartListener);
					}
				}
			}
		}

	@Override
	public void perspectiveSavedAs(IWorkbenchPage page,
			IPerspectiveDescriptor oldPerspective,
			IPerspectiveDescriptor newPerspective) 
		{}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) 
		{}

	@Override
	public void perspectiveActivated(IWorkbenchPage page,
			IPerspectiveDescriptor perspective) 
		{
		// consideriamo solo il caso in cui la prospettiva attivata sia quella relativa
		// a twotower
		String string = perspective.getId();
		if ("it.univaq.TTEP.perspectives.TwoTowersPerspective".equals(string))
			{
			if (aemEditorPartListener == null)
				{
				// bisogna considerare il caso in cui ci sia almeno un editor aem aperto.
				// In questo caso si istanzia un oggetto AemEditorPartListener, fornendo
				// un oggetto NoExtChangeListener come parametro
				// tale condizione si può verificare utilizzando il metodo getActiveEditor
				IEditorPart editorPart = page.getActiveEditor();
				if (editorPart == null)
					{
					aemEditorPartListener = new AemEditorPartListener();
					page.addPartListener(aemEditorPartListener);
					}
				else
					{
					aemEditorPartListener = new AemEditorPartListener(editorPart);
					page.addPartListener(aemEditorPartListener);
					}
				}
			}
		}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,
			IPerspectiveDescriptor perspective, String changeId) 
		{}
	
	public static void stampaSuConsole(String string)
		{
		ConsolePlugin consolePlugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = consolePlugin.getConsoleManager();			
		MessageConsole messageConsole = new MessageConsole("Console TwoTower",null);
		MessageConsoleStream messageConsoleStream = messageConsole.newMessageStream();
		messageConsoleStream.println(string);
		// prima di aggiungere la nuova console, rimuoviamo quelle vecchie
		IConsole[] consoles = consoleManager.getConsoles();
		consoleManager.removeConsoles(consoles);
		// si mostra la console nuova con il messaggio
		consoleManager.addConsoles(new MessageConsole[]{messageConsole});
		consoleManager.showConsoleView(messageConsole);
		}
	}
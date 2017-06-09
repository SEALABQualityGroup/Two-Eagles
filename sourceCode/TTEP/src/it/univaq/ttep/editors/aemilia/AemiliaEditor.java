package it.univaq.ttep.editors.aemilia;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.editors.text.TextEditor;

public class AemiliaEditor extends TextEditor {

	private ColorManager colorManager;

	public AemiliaEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new AemiliaConfiguration(colorManager));
		setDocumentProvider(new AemiliaDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	@Override
	protected void performSave(boolean overwrite,
			IProgressMonitor progressMonitor) 
		{
		super.performSave(overwrite, progressMonitor);
		// compiliamo la specifica aperta nell'editor, utile per l'attivazione
		// della voce di menu relativa alla generazione della rete di code
		IWorkbench workbench = PlatformUI.getWorkbench();
		ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
		Command command = commandService.getCommand("it.univaq.TTEP.parser");
		try {
			command.executeWithChecks(new ExecutionEvent());
			} 
		catch (ExecutionException e) 
			{
			e.printStackTrace();
			} 
		catch (NotDefinedException e) 
			{
			e.printStackTrace();
			} 
		catch (NotEnabledException e) 
			{
			e.printStackTrace();
			} 
		catch (NotHandledException e) 
			{
			e.printStackTrace();
			}
		}
	}

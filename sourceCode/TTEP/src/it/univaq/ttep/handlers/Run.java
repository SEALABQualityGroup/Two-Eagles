package it.univaq.ttep.handlers;

import it.univaq.ttep.preference.TwoTowersPreferencePage;
import it.univaq.ttep.utility.Utility;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Classe che implementa l'handler della voce
 * del menù "Run" per lanciare il programma "Seal".
 * 
 */
public class Run extends AbstractHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		String java = "java";
		String source = TwoTowersPreferencePage.getSealPreference();
		
		/* Verifica che il path del programma Seal è stato inserito nelle "Preferenze" */
		if(!Utility.checkProgramPreferences(window, source))
			return null;

		Utility.execute(window.getShell(), "Run Seal...", java, "-jar", source);
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}

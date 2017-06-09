package it.univaq.ttep.handlers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import it.univaq.ttep.preference.TwoTowersPreferencePage;
import it.univaq.ttep.utility.Utility;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class TransientProbability extends AbstractHandler {

	/* Parametro da passare a TTKernel per lanciare la funzionalità opportuna */
	private final static String TRANSIENT_PROBABILITY_DISTRIBUTION_CALCULATOR_UNIFORMIZATION = "-r";
	/*Espressione regolare che identifica solo numeri interi positivi o numeri decimali con il .*/
	private final static String REGEX = "^([0-9]+)|([0-9]+(\\.)[0-9]+)$";
	
	private IInputValidator _inputValidator = new IInputValidator() {
		public String isValid(String newText) { return null; }
	};

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IResource file = (IResource)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput().getAdapter(IResource.class);
		IPath sourcePath = file.getFullPath();
		IPath destPath = sourcePath.removeFileExtension();
		String source = myWorkspaceRoot.getLocation() + sourcePath.toString();
		String dest = myWorkspaceRoot.getLocation() + destPath.toString();
		String twoTowers = TwoTowersPreferencePage.getTwoTowersPreference();
		boolean exit=false;
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher;

		/* Verifica che il path del TTKernel è stato inserito */
		if(!Utility.checkProgramPreferences(window, twoTowers))
			return null;
		
		/* Input dialog per selezionare il trans time value */
		String value = "";
		while(!exit){
			try {
				InputDialog input = new InputDialog(window.getShell(), "Transient time",
						"Select a transient time value >= 0",
						"", _inputValidator);
				input.open();
				//Se è stato premuto il tasto "Cancel"
				if(input.getReturnCode() == 1)
					return null;
				
				value = input.getValue();
				matcher = pattern.matcher(value);
				
				//Se è stato immesso un numero
				if(matcher.matches())
					exit = true;
				
			}
			catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}

		Utility.execute(window.getShell(), "Calculating...", twoTowers, TRANSIENT_PROBABILITY_DISTRIBUTION_CALCULATOR_UNIFORMIZATION, source, value, dest);
		Utility.printOutput(dest);

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

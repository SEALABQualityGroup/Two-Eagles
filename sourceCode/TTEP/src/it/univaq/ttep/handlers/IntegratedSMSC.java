package it.univaq.ttep.handlers;

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
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class IntegratedSMSC extends AbstractHandler {

	/* Parametro da passare a TTKernel per lanciare la funzionalità opportuna */
	private final static String INTEGRATED_SEMANTIC_MODEL_SIZE_CALCULATOR = "-b";

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

		/* Verifica che il path del TTKernel è stato inserito nelle "Preferenze" */
		if(!Utility.checkProgramPreferences(window, twoTowers))
			return null;

		/* Integrated semantic model size calculator */
		Utility.execute(window.getShell(), "Compiling...", twoTowers, INTEGRATED_SEMANTIC_MODEL_SIZE_CALCULATOR, source, dest);
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

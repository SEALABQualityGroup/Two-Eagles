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

public class NonDeducibility extends AbstractHandler {

	private final static String NON_DEDUCIBILITY_ON_COMPOSITION_ANALYZER = "-o";
	private final static String[] EXTENSIONS = {"*.aem"};

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
		final String source = myWorkspaceRoot.getLocation() + sourcePath.toString();
		final String dest = myWorkspaceRoot.getLocation() + destPath.toString();
		final String twoTowers = TwoTowersPreferencePage.getTwoTowersPreference();

		/* Verifica che il path del TTKernel è stato inserito */
		if(!Utility.checkProgramPreferences(window, twoTowers))
			return null;

		/* Non deducibility on composition analyzer */
		String source_2 = Utility.queryFile(window.getShell(), EXTENSIONS, myWorkspaceRoot.getLocation().toString()+sourcePath.removeLastSegments(1).toString()).toString();
		Utility.execute(window.getShell(), twoTowers, NON_DEDUCIBILITY_ON_COMPOSITION_ANALYZER, source, source_2, dest);
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

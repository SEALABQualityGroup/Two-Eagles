package it.univaq.from_aemilia_to_qn_plug_in.handlers;

import it.univaq.from_aemilia_to_qn_plug_in.listeners.TwoTowerPerspectiveListener;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JTextArea;

import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import scanSpecAEmilia.ScanNoExtFile;
import transformerFases.ITrasformazione;
import transformerFases.ITrasformazioneFactory;
import transformerFases.RisultatoFase;
import utilities.MetodiVari;
import config.ServiceTimeFactory;

/**
 * 
 */

/**
 * @author Mirko
 *
 */
public class GeneratoreReteDiCode implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#addHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#dispose()
	 */
	@Override
	public void dispose() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) 
		throws ExecutionException 
		{
		// 1) si preleva l'aem dell'editor in primo piano
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot myWorkspaceRoot = workspace.getRoot();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		IEditorInput editorInput = editorPart.getEditorInput();
		IResource file = (IResource)editorInput.getAdapter(IResource.class);
		// si trasforma file in un oggetto File con path assoluto
		String pathWorkspace = myWorkspaceRoot.getLocation().toString(); 
		IPath pathAem = file.getFullPath();
		String pathString = pathWorkspace +
			pathAem.toString();
		File file2 = new File(pathString);
		// 2) si genera la stringa relativa all'aem, utilizzando il metodo
		// ScanNoExtFile.generaStringa
		String string = new String();
		try {
			string = ScanNoExtFile.generaStringa(file2);
			} 
		catch (FileNotFoundException e) 
			{
			e.printStackTrace();
			}
		// 3) si istanzia un oggetto RisultatoFase con esito uguale a true e 
		// risultato uguale alla stringa generata in precedenza
		RisultatoFase risultatoFase = new RisultatoFase();
		risultatoFase.setEsito(true);
		risultatoFase.setRisultato(string);
		// 4) si istanzia un oggetto JTextArea
		JTextArea textArea = new JTextArea();
		// preleviamo il file assoluto per il calcolo del workspace
		String string21 = MetodiVari.namespaceCompute(file2);
		// 5) si chiama il metodo Trasformazione.trasforma con gli oggetti istanziati in precedenza
		ITrasformazioneFactory trasformazioneFactory = ServiceTimeFactory.getTrasformazioneFactory();
		ITrasformazione trasformazione = trasformazioneFactory.createTrasformazione();
		RisultatoFase risultatoFase2 = trasformazione.trasforma(textArea, risultatoFase, string21);
		// 6) si stampa su standard output il contenuto di textArea
		TwoTowerPerspectiveListener.stampaSuConsole(textArea.getText());
		// 7) se l'esito del risultato fase ottenuto in precedenza è true
		// si salva la rete di code nella stessa directory del file aem trasformato
		if (risultatoFase2.isEsito())
			{
			// si preleva il path assoluto del file xml
			IPath pathQN = pathAem.removeFileExtension().addFileExtension("xml");
			String string2 = pathWorkspace + pathQN.toString();
			// si preleva la rete di code generata
			ISalvaElementiBase salvaElementiBase = (ISalvaElementiBase)risultatoFase2.getRisultato();
			// si salva su fileSystem l'xml corrispondente alla rete di code
			try {
				salvaElementiBase.salvaInXML(string2);
				} 
			catch (MappingElementiBaseException e1) 
				{
				e1.printStackTrace();
				}
			// si monitorizza il refresh dell'xml generato dalla trasformazione
			// si preleva l'oggetto IResource relativo a pathQN.
			IContainer container = file.getParent();
			try {
				// si effettua il refresh del container dell'xml generato
				container.refreshLocal(IResource.DEPTH_INFINITE, null);
				} 
			catch (CoreException e) 
				{
				e.printStackTrace();
				}
//			IRefreshResult refreshResult = null;
//			RefreshProvider refreshProvider = new RefreshProviderQN();
//			refreshProvider.installMonitor(resource2, refreshResult);
			}
		return null;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isEnabled()
	 */
	@Override
	public boolean isEnabled() 
		{
		return true;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isHandled()
	 */
	@Override
	public boolean isHandled() 
		{
		return true;
		}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#removeHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {

	}

}

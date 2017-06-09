package it.univaq.from_aemilia_to_qn_plug_in.listeners;

import it.univaq.from_aemilia_to_qn_plug_in.handlers.GeneratoreReteDiCode;
import it.univaq.from_aemilia_to_qn_plug_in.listeners.data.AemParsingToken;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.commands.Command;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import scanSpecAEmilia.ScanNoExtFile;

public class NoExtChangeListener 
	implements IResourceChangeListener 
	{
	
	@Override
	public void resourceChanged(IResourceChangeEvent event) 
		{
		// la registrazione del cambio di risorsa
		// è relativa ad ogni risorsa
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		IEditorPart editorPart = workbenchPage.getActiveEditor();
		// le seguenti istruzioni vanno eseguite se e solo se il sito di editorPart è 
		// relativo ad un editor aem
		IEditorSite editorSite = editorPart.getEditorSite();
		String string = editorSite.getId();
		if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
			{
			// bisogna controllare se è stata effettuata una compilazione
			if (AemParsingToken.isParsing)
				{
				IEditorInput editorInput = editorPart.getEditorInput();
				IResource file = (IResource)editorInput.getAdapter(IResource.class);
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot workspaceRoot = workspace.getRoot();
				// il confronto delle risorse si effettua tramite path
				IPath pathAem = file.getFullPath();
				// si elimina l'estensione aem all'aem aperto nell'editor
				IPath pathNoExt = pathAem.removeFileExtension();
				// se i due cammini sono uguali si gestisce ogni tipo di evento di modifica
				IResourceDelta resourceDelta = event.getDelta();
				int i = resourceDelta.getKind();
				switch (i)
					{
					case IResourceDelta.ADDED:
						{
						System.out.println("Unexpected interaction. "+pathNoExt.lastSegment()+" added");
						break;
						}
					case IResourceDelta.ADDED_PHANTOM:
						{
						System.out.println("Unexpected interaction. "+pathNoExt.lastSegment()+" phantom added");
						break;
						}
					case IResourceDelta.CHANGED:
						{
						ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
						Command command = commandService.getCommand("it.univaq.TTEP.queueingNetworkGenerator");
						// si trova la risorsa che è stata modificata
						IResourceDelta resourceDelta2 = resourceDelta.findMember(pathNoExt);
						// se resourceDelta2 è null la risorsa modificata non è il NoExt
						if (resourceDelta2 != null)
							{
							String pathString = workspaceRoot.getLocation().toString() +
								resourceDelta2.getFullPath().toString();
							File file2 = new File(pathString);
							if (command.isDefined())
								{
								try {
									if (ScanNoExtFile.scanNoExtFile(file2))
										{
										// si aggiungono le istruzioni relative all'attivazione 
										// dell'handler relativo alla generazione della rete di code
										// Si utilizza 
										// una variabile di classe per verificare se l'handler
										// sia stato impostato
										command.setHandler(new GeneratoreReteDiCode());
										}
									else
										{
										// si aggiungono le istruzioni relative alla disattivazione 
										// dell'handler relativo alla generazione della rete di code.
										command.setHandler(null);
										}
									} 
								catch (FileNotFoundException e) 
									{
									// si imposta l'handler a null perchè vuol dire che il
									// file è stato cancellato
									command.setHandler(null);
									}
								}
							}
						// altrimenti si verifica se è stato cambiato l'aem aperto nell'editor.
						// In questo caso si deve disattivare l'handler perchè
						// l'aem va ricompilato.
						else
							{
							// si trova la risorsa che è stata modificata
							IResourceDelta resourceDelta3 = resourceDelta.findMember(pathAem);
							// se resourceDelta3 è null la risorsa modificata non è l'aem aperto nell'editor
							if (resourceDelta3 != null)
								{
	//							command.setHandler(null);
								TwoTowerPerspectiveListener.stampaSuConsole("Parse the aem file !");
	//							Command command2 = commandService.getCommand("it.univaq.TTEP.parser");
	//							try {
	//								command2.execute(new ExecutionEvent());
	//								}
	//							catch (ExecutionException e) 
	//								{
	//								e.printStackTrace();
	//								} 
	//							catch (NotHandledException e) 
	//								{
	//								e.printStackTrace();
	//								}
								}
							}
						break;
						}
					case IResourceDelta.REMOVED:
						{
						System.out.println("Unexpected interaction. "+pathNoExt.lastSegment()+" removed");
						break;
						}
					case IResourceDelta.REMOVED_PHANTOM:
						{
						System.out.println("Unexpected interaction. "+pathNoExt.lastSegment()+" phantom removed");
						break;
						}
					}
				}
			}
		}
	}
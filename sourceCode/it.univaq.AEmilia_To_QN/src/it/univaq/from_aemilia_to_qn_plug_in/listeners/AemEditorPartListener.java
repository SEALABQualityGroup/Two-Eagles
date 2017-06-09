package it.univaq.from_aemilia_to_qn_plug_in.listeners;

import it.univaq.from_aemilia_to_qn_plug_in.handlers.GeneratoreReteDiCode;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.commands.Command;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import scanSpecAEmilia.ScanNoExtFile;

public class AemEditorPartListener implements IPartListener 
	{
	
	private NoExtChangeListener noExtChangeListener;
	private AemParserExecListener aemParserExecListener;
		
	public AemEditorPartListener(IWorkbenchPart workbenchPart) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = workbenchPart.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			String string = workbenchPartSite.getId();
			// se è stato attivato l'editor aem si registra un listener ad un file noext
			if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
				{
				aemParserExecListenerActivation(workbenchPart);
				noExtListenerActivation(workbenchPart);
				}
			else
				{
				aemParserExecListenerDeactivation(workbenchPart);
				noExtListenerDeactivation(workbenchPart);
				}
			}
		}

	public AemEditorPartListener() 
		{}
	
	/**
	 * Consente l'attivazione dell'handler per la generazione della rete di code nel caso in cui
	 * il noext sia presente e non contenga errori.
	 * 
	 * @param resource - corrisponde al file .aem aperto nell'editor
	 */
	private void controlloNoExt(IResource resource)
		{
		// si verifica se il noext di resource esiste. In questo caso bisogna verificare
		// se contiene errori o warning, ed in tale caso attivare la voce di menu.
		// si verifica se il noext esiste
		IWorkspace workspace = resource.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();
		// il confronto delle risorse si effettua tramite path
		IPath pathAem = resource.getFullPath();
		// si elimina l'estensione aem all'aem aperto nell'editor
		IPath pathNoExt = pathAem.removeFileExtension();
		IContainer container = resource.getParent();
		IResource resource2 = container.findMember(pathNoExt.lastSegment());
		// il noext esiste se resource2 è diverso da null
		IWorkbench workbench = PlatformUI.getWorkbench();
		ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
		Command command = commandService.getCommand("it.univaq.TTEP.queueingNetworkGenerator");
		if (command.isDefined())
			{
			if (resource2 != null)
				{
				// si verifica se contiene errori o warning
				IPath path = workspaceRoot.getLocation();
				if (path != null)
					{
					String pathString = path.toString() +
						resource2.getFullPath().toString();
					File file2 = new File(pathString);
					try {
						if (ScanNoExtFile.scanNoExtFile(file2))
							{
							command.setHandler(new GeneratoreReteDiCode());
							}
						else
							{
							command.setHandler(null);
							}
						} 
					catch (FileNotFoundException e) 
						{
						e.printStackTrace();
						}
					}
				else
					{
					System.out.println("The workspace root path can not"+
							" be determined");
					}
				}
			// se resource2 è null il camando di generazione della rete di code va disattivato
			else 
				{
				command.setHandler(null);
				}
			}
		else
			System.out.println("The command it.univaq.TTEP.queueingNetworkGenerator is undefined");		
		}
	
	private void noExtListenerActivation(IWorkbenchPart workbenchPart)
		{
		if (workbenchPart instanceof IEditorPart)
			{
			// si aggiunge un listener ai cambi del workspace se non è stato già istanziato e registrato
			IEditorInput editorInput = ((IEditorPart)workbenchPart).getEditorInput();
			IResource resource = (IResource)editorInput.getAdapter(IResource.class);
			if (resource == null)
				{
				System.out.println("The aem editor resource is null");
				}
			else
				{
				// il seguente codice va eseguito solo se il file aperto nell'editor ha estensione
				// aem
				String string2 = resource.getFileExtension();
				if ("aem".equals(string2))
					{					
					IWorkspace workspace = resource.getWorkspace();			
					if (noExtChangeListener == null)
						{
						noExtChangeListener = new NoExtChangeListener();
						workspace.addResourceChangeListener(noExtChangeListener);
						}
					controlloNoExt(resource);
					}
				}
			}
		}
	
	private void aemParserExecListenerActivation(IWorkbenchPart workbenchPart) 
		{
		if (workbenchPart instanceof IEditorPart)
			{
			// si aggiunge un listener ai cambi del workspace se non è stato già istanziato e registrato
			IEditorInput editorInput = ((IEditorPart)workbenchPart).getEditorInput();
			IResource resource = (IResource)editorInput.getAdapter(IResource.class);
			if (resource == null)
				{
				System.out.println("The aem editor resource is null");
				}
			else
				{
				// il seguente codice va eseguito solo se il file aperto nell'editor ha estensione
				// aem
				String string2 = resource.getFileExtension();
				if ("aem".equals(string2))
					{
					if (aemParserExecListener == null)
						{
						IWorkbench workbench = PlatformUI.getWorkbench();
						ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
						Command command = commandService.getCommand("it.univaq.TTEP.parser");
						aemParserExecListener = new AemParserExecListener();
						command.addExecutionListener(aemParserExecListener);
						}
					}
				}
			}
		}

	private void noExtListenerDeactivation(IWorkbenchPart part)
		{
		if (part instanceof IEditorPart)
			{
			// si cancella il listener ai cambi del workspace se non è stato già cancellato
			IEditorInput editorInput = ((IEditorPart)part).getEditorInput();
			IResource resource = (IResource)editorInput.getAdapter(IResource.class);
			if (resource == null)
				{
				System.out.println("The aem editor resource is null");
				}
			else
				{
				IWorkspace workspace = resource.getWorkspace();			
				if (noExtChangeListener != null)
					{
					workspace.removeResourceChangeListener(noExtChangeListener);
					noExtChangeListener = null;
					}
				// si disattiva la voce di menu relativa alla generazione della rete di code
				IWorkbench workbench = PlatformUI.getWorkbench();
				ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
				Command command = commandService.getCommand("it.univaq.TTEP.queueingNetworkGenerator");
				if (command.isDefined())
					{
					command.setHandler(null);
					}
				else
					System.out.println("The command it.univaq.TTEP.queueingNetworkGenerator is undefined");
				}
			}
		}
	
	private void aemParserExecListenerDeactivation(IWorkbenchPart workbenchPart) 
		{
		if (workbenchPart instanceof IEditorPart)
			{
			// si cancella il listener per l'esecuzione del parser se non è già null
			IEditorInput editorInput = ((IEditorPart)workbenchPart).getEditorInput();
			IResource resource = (IResource)editorInput.getAdapter(IResource.class);
			if (resource == null)
				{
				System.out.println("The aem editor resource is null");
				}
			else
				{
				if (aemParserExecListener != null)
					{
					IWorkbench workbench = PlatformUI.getWorkbench();
					ICommandService commandService = (ICommandService)workbench.getService(ICommandService.class);
					Command command = commandService.getCommand("it.univaq.TTEP.parser");
					command.removeExecutionListener(aemParserExecListener);
					aemParserExecListener = null;
					}
				}
			}
		}

	@Override
	public void partActivated(IWorkbenchPart part) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = part.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			String string = workbenchPartSite.getId();
			// se è stato attivato l'editor aem si registra un listener ad un file noext
			if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
				{
				noExtListenerActivation(part);
				aemParserExecListenerActivation(part);
				}
			else
				{
				noExtListenerDeactivation(part);
				aemParserExecListenerDeactivation(part);
				}
			}
		}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = part.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			String string = workbenchPartSite.getId();
			// se è stato portato in primo piano l'editor aem si registra un listener ad un file noext
			if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
				{
				noExtListenerActivation(part);
				aemParserExecListenerActivation(part);
				}
			else
				{
				noExtListenerDeactivation(part);
				aemParserExecListenerDeactivation(part);
				}			
			}		
		}

	@Override
	public void partClosed(IWorkbenchPart part) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = part.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			// affinchè vengano eseguite le seguenti linee di codice bisogna che non ci siano
			// altri editor aem aperti
			IWorkbenchPage workbenchPage = workbenchPartSite.getPage();
			IEditorReference[] editorReferences = workbenchPage.findEditors(null, 
					"it.univaq.TTEP.editors.AemiliaEditor", IWorkbenchPage.MATCH_ID);
			if (editorReferences.length == 0)
				{
				String string = workbenchPartSite.getId();
				// se è stato chiuso l'editor aem si cancella il listener ai cambi del workspace
				if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
					{
					noExtListenerDeactivation(part);
					aemParserExecListenerDeactivation(part);
					}
				}
			}		
		}

	@Override
	public void partDeactivated(IWorkbenchPart part) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = part.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			// affinchè vengano eseguite le seguenti linee di codice bisogna che non ci siano
			// altri editor aem aperti
			IWorkbenchPage workbenchPage = workbenchPartSite.getPage();
			IEditorReference[] editorReferences = workbenchPage.findEditors(null, 
					"it.univaq.TTEP.editors.AemiliaEditor", IWorkbenchPage.MATCH_ID);
			if (editorReferences.length == 0)
				{
				String string = workbenchPartSite.getId();
				// se è stato chiuso l'editor aem si cancella il listener ai cambi del workspace
				if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
					{
					noExtListenerDeactivation(part);
					aemParserExecListenerDeactivation(part);
					}
				}
			}		
		}

	@Override
	public void partOpened(IWorkbenchPart part) 
		{
		// part deve essere relativo all'editor aem. Altrimenti si hanno delle 
		// risposte indesiderate di getAdapter.
		IWorkbenchPartSite workbenchPartSite = part.getSite();
		if (workbenchPartSite == null)
			{
			System.out.println("The part site is null");
			}
		else
			{
			String string = workbenchPartSite.getId();
			// se è stato aperto l'editor aem si registra un listener ad un file noext
			if ("it.univaq.TTEP.editors.AemiliaEditor".equals(string))
				{
				noExtListenerActivation(part);
				aemParserExecListenerActivation(part);
				}
			else
				{
				noExtListenerDeactivation(part);
				aemParserExecListenerDeactivation(part);
				}
			}		
		}	
	}

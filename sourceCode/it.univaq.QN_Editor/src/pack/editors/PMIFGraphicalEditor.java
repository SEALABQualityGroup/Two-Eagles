package pack.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import pack.contextMenus.PMIFContextMenuProvider;
import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.factories.QNMEditPartFactory;
import pack.factories.QNMPaletteFactory;
import pack.factories.WorkloadEditPartFactory;
import pack.factories.WorkloadPaletteFactory;
import pack.model.IQNM;
import pack.model.IWorkload;
import pack.model.schema.QNMType;
import pack.model.schema.pmifschemaDoc;
import pack.model.structure.QNMModel;
import pack.outlinePages.QNMOutlinePage;
import pack.outlinePages.WorkloadOutlinePage;
import pack.palettes.QNMPaletteViewerProvider;
import pack.palettes.WorkloadPaletteViewerProvider;

public class PMIFGraphicalEditor extends GraphicalEditorWithFlyoutPalette {

	/** This is the root of the editor's model. */
	public static IQNM model;
	
	// contiene il carico di lavoro correntemente 
	// visualizzato. Quando si visualizza
	// un carico di lavoro, vengono visualizzati:
	// - il nodo di delay se è un ClosedWorkloadType;
	// - i nodi source e sink se è un OpenWorkloadType. 
	public static IWorkload workload;
	
	// indica se c'è stato uno switch per la visualizzazione di un workload
	public static boolean switchToWorkload = false;
	
	private TransferDropTargetListener transferDropTargetListener;

	public PMIFGraphicalEditor() 
		{
//		System.out.println("Costruttore");
		setEditDomain(new DefaultEditDomain(this));
		}

	@Override
	protected PaletteRoot getPaletteRoot() 
		{
//		System.out.println("getPaletteRoot()");
		if (!switchToWorkload)
			{
			QNMPaletteFactory factory = new QNMPaletteFactory();
			return factory.createPalette();
			}
		else
			{
			WorkloadPaletteFactory factory = new WorkloadPaletteFactory(workload);
			return factory.createPalette();
			}
		}

	@Override
	public void doSave(IProgressMonitor monitor) 
		{
//		System.out.println("doSave(IProgressMonitor monitor)");
		// il salvataggio si effettua soltanto se la generazione del dom del modello 
		// è avvenuta con successo
		QNMType type = null;
		try {
			type = model.generateDom();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			try {
				createOutputStream(out);
				file.setContents(
						new ByteArrayInputStream(out.toByteArray()), 
						true,  // keep saving, even if IFile is out of sync with the Workspace
						false, // don't keep history
						monitor); // progress monitor
				getCommandStack().markSaveLocation();
				// si chiudono gli stream
				out.close();
				} 
			catch (CoreException ce) 
				{ 
				ce.printStackTrace();
				} 
			catch (IOException ioe) 
				{
				ioe.printStackTrace();
				}
			pmifschemaDoc doc = new pmifschemaDoc();
			doc.setRootElementName("", "QueueingNetworkModel");
			// si salva nella stessa directory del file pmif aperto con l'editor 
			// con lo stesso nome ma con estensione xml
			IPath path = file.getLocation();
			IPath path2 = path.removeFileExtension();
			String string2 = path2.toString()+".xml";
			doc.save(string2, type);
			// si effettua il refresh del file su workspace
			// secondo le seguenti istruzioni
			IContainer container = file.getParent();
			try {
				container.refreshLocal(IFile.DEPTH_INFINITE, null);
				} 
			catch (CoreException e) 
				{
				e.printStackTrace();
				}
			}
		catch (SavingException exception)
			{
			monitor.setCanceled(true);
			}
		}

	@Override
	protected PaletteViewerProvider createPaletteViewerProvider() 
		{
//		System.out.println("createPaletteViewerProvider()");
		// si differenzia il PaletteViewerProvider da restituire
		// a seconda del tipo di modello impostato
		if (!switchToWorkload)
			return new QNMPaletteViewerProvider(getEditDomain());
		else
			return new WorkloadPaletteViewerProvider(getEditDomain());
		}
	
	/**
	 * Set up the editor's inital content (after creation).
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() 
		{
//		System.out.println("initializeGraphicalViewer()");
		super.initializeGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		if (switchToWorkload)
			viewer.setContents(workload); // set the contents of this editor
		else
			viewer.setContents(model);
		// listen for dropped parts
		transferDropTargetListener =  new PMIFTransferDropTargetListener(getGraphicalViewer());
		viewer.addDropTargetListener(transferDropTargetListener);
		}
	
	@Override
	protected void configureGraphicalViewer() 
		{
//		System.out.println("configureGraphicalViewer()");
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		if (switchToWorkload)
			viewer.setEditPartFactory(new WorkloadEditPartFactory(model));
		else
			viewer.setEditPartFactory(new QNMEditPartFactory(model));
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));
		// configure the context menu provider
		try {
			ContextMenuProvider cmProvider =
				new PMIFContextMenuProvider(viewer, getActionRegistry());
			viewer.setContextMenu(cmProvider);
			getSite().registerContextMenu(cmProvider, viewer);
			}
		catch (Exception exception)
			{}
		}

	/**
	 * Create a transfer drop target listener. When using a CombinedTemplateCreationEntry
	 * tool in the palette, 
	 * this will enable model element creation by dragging from the palette.
	 * 
	 * @see #createPaletteViewerProvider()
	 */
//	private TransferDropTargetListener createTransferDropTargetListener() 
//		{
//		transferDropTargetListener =  new PMIFTransferDropTargetListener(getGraphicalViewer());
//		return transferDropTargetListener;
//		System.out.println("createTransferDropTargetListener()");
//		return new TemplateTransferDropTargetListener(getGraphicalViewer()) 
//			{
//			protected CreationFactory getFactory(Object template) 
//				{
//				return new SimpleFactory((Class<?>) template);
//				}
//			};
//		}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class type) 
		{
//		System.out.println("getAdapter(Class type)");
		if (type == IContentOutlinePage.class)
			{
			if (!switchToWorkload)
				return new QNMOutlinePage(
					new TreeViewer(),
					this.getActionRegistry(),
					this.getEditDomain(),
					model,
					this.getSelectionSynchronizer());
			else
				return new WorkloadOutlinePage(
					new TreeViewer(),
					this.getActionRegistry(),
					this.getEditDomain(),
					workload,
					this.getSelectionSynchronizer());
			}
		else
			return super.getAdapter(type);
		}
	
	protected void setInput(IEditorInput input) 
		{
//		System.out.println("setInput(IEditorInput input)");
		// il metodo viene chimato quando viene aperto l'editor, per assegnargli un 
		// file da visualizzare
		super.setInput(input);
		try {
			IFile file = ((IFileEditorInput) input).getFile();
			String ext = file.getFileExtension();
			if ("pmif".equals(ext))
				{
				// per motivi di sincronizzazione si effettua il refresh
				IContainer container = file.getParent();
				try {
					container.refreshLocal(IFile.DEPTH_INFINITE, null);
					} 
				catch (CoreException e) 
					{
					e.printStackTrace();
					}
				// se il file ha estensione pmif, procedo nel seguente modo:
				// bisogna prelevare il contenuto degli attributi non identificativi
				// dal file xml associato al file pmif, se non è stato ancora effettuato lo switch.
				if (!switchToWorkload)
					{
					IPath path = file.getLocation();
					IPath path2 = path.removeFileExtension();
					String string = path2.toString()+".xml";
					pmifschemaDoc doc = new pmifschemaDoc();
					QNMType type = new QNMType(doc.load(string));
					ObjectInputStream in = new ObjectInputStream(file.getContents());
					model = (IQNM)in.readObject();
					model.setQNMType(type);
					model.loadingPMIF();
					in.close();
					}
				setPartName(file.getName());
				}
			else if ("xml".equals(ext))
				{
				// se file ha estensione xml allora:
				// 1) si crea il .pmif nella stessa directory dell'.xml
				IPath path = file.getLocation();
				IPath path2 = path.removeFileExtension();
				String string2 = path2.toString()+".pmif";
				File file2 = new File(string2);
				file2.createNewFile();
				// si effettua il refresh del file su workspace
				// secondo le seguenti istruzioni
				IContainer container = file.getParent();
				try {
					container.refreshLocal(IFile.DEPTH_INFINITE, null);
					} 
				catch (CoreException e) 
					{
					e.printStackTrace();
					}
				// 1.1) dal container di tale file si preleva un oggetto IFile
				// del .pmif
				String string = path2.lastSegment();
				string = string+".pmif";
				// come precondizione file3 esiste
				IFile file3 = (IFile)container.findMember(string);
				// 2) si carica l'xml in model
				pmifschemaDoc doc = new pmifschemaDoc();
				model = new QNMModel();
				model.setQNMType(new QNMType(doc.load(path.toString())));
				// 3) si chiama la funzione loading su model, per l'assegnazione di attributi di default
				model.loadingXML();
				// 4) si serializza nel file pmif il contenuto di model
				FileOutputStream fileOutputStream = new FileOutputStream(string2);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(model);
				// 5) si imposta l'input dell'editor con il file .pmif creato
				FileEditorInput fileEditorInput = new FileEditorInput(file3);
				// si richiama la funzione setInput
				setInput(fileEditorInput);
				// si chiudono gli stream
				fileOutputStream.close();
				objectOutputStream.close();
				}
			else
				Finestra.mostraIE("Malformed extension");
			}
		catch (LoadingException exception)
			{
			handleLoadException(exception);
			}
		catch (IOException e) 
			{ 
			handleLoadException(e); 
			} 
		catch (CoreException e) 
			{ 
			handleLoadException(e); 
			} 
		catch (ClassNotFoundException e) 
			{ 
			handleLoadException(e); 
			}
		catch (Exception exception)
			{
			handleLoadException(exception);
			}
		}
	
	private void handleLoadException(Exception e) 
		{
//		System.out.println("handleLoadException(Exception e)");
		System.err.println("** Load failed. Using default model. **");
		e.printStackTrace();
		model = new QNMModel();
		switchToWorkload = false;
		}

	private void createOutputStream(OutputStream os) throws IOException 
		{
//		System.out.println("createOutputStream(OutputStream os)");
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(model);
		oos.close();
		}
	
	public void commandStackChanged(EventObject event) 
		{
//		System.out.println("commandStackChanged(EventObject event)");
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
		}

	// i seguenti metodi vengono sovrascritti per consentire il giusto setting
	// nella chiusura e apertura dell'editor.
	// Ci sono problemi di impostazione dello stato nel caso in cui si chiude
	// l'editor se non è stato prima ottenuto il focus.

	@Override
	public void setFocus() 
		{
//		System.out.println("setFocus()");
		ActionRegistry actionRegistry = getActionRegistry();
		IAction action2 = actionRegistry.getAction("it.univaq.InsertNodeModel");
		IAction action3 = actionRegistry.getAction("it.univaq.InsertWorkloadModel");
		IAction action4 = actionRegistry.getAction("it.univaq.InsertServiceRequestModel");
		if (switchToWorkload)
			{
			switchToWorkload = false;
			// si consente l'azione SwitchToQN
			IAction action = actionRegistry.getAction("it.univaq.SwitchToQN");
			action.setEnabled(true);
			// non si consentono le azioni di inserimento di nodi, carichi di lavoro e 
			// richieste di servizio
			action2.setEnabled(false);
			action3.setEnabled(false);
			action4.setEnabled(false);
			}
		else
			{
			// si consentono le azioni di inserimento di nodi, carichi di lavoro e 
			// richieste di servizio
			action2.setEnabled(true);
			action3.setEnabled(true);
			action4.setEnabled(true);
			}
		super.setFocus();
		}

	// il seguente override è stato effettuato per rendere publico il metodo
	@Override
	public CommandStack getCommandStack() 
		{
//		System.out.println("getCommandStack()");
		return super.getCommandStack();
		}

	@Override
	public void dispose() 
		{
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.removeDropTargetListener(transferDropTargetListener);
		}
	}
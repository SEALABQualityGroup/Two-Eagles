package pack.wizards;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import pack.model.schema.NodeType;
import pack.model.schema.QNMType;
import pack.model.schema.ServiceRequestType;
import pack.model.schema.WorkloadType;
import pack.model.schema.pmifschemaDoc;
import pack.model.structure.NodeModel;
import pack.model.structure.QNMModel;
import pack.model.structure.ServiceRequestModel;
import pack.model.structure.WorkloadModel;

/**
 * Questa WizardPage può creare un file .pmif vuoto per il PMIFGraphicalEditor
 */

public class CreationPagePMIF extends WizardNewFileCreationPage {

		private static final String DEFAULT_EXTENSION = ".pmif";
		
		private int fileCount;
		
		private final IWorkbench workbench;
		
		private String xmlPath;
		
		/**
		 * Crea una nuova istanza della pagina del wizard.
		 * @param workbench the current workbench
		 * @param selection the current object selection
		 * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
		 */
		public CreationPagePMIF(IWorkbench workbench, IStructuredSelection selection, int i) 
			{
			super("PMIFCreationPage1", selection);
			this.workbench = workbench;
			this.fileCount = i;
			setTitle("Create a new " + DEFAULT_EXTENSION + " file");
			setDescription("Create a new " + DEFAULT_EXTENSION + " file");
			}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) 
			{
			super.createControl(parent);
			setFileName("PMIFDiagram" + fileCount + DEFAULT_EXTENSION);
			setPageComplete(validatePage());
			}
		
		/**
		 * This method will be invoked, when the "Finish" button is pressed.
		 * @see ShapesCreationWizard#performFinish()
		 */
		boolean finish() 
			{
			// create a new file, result != null if successful
			IFile newFile = createNewFile();
			// viene aggiornato il contatore di diagrammi
			fileCount++;
			// open newly created file in the editor
			IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			if (newFile != null && page != null) 
				{
				try {
					IDE.openEditor(page, newFile, true);
					} 
				catch (PartInitException e) 
					{
					e.printStackTrace();
					return false;
					}
				}
			return true;
			}

		/**
		 * Return true, if the file name entered in this page is valid.
		 */
		private boolean validateFilename() 
			{
			if (getFileName() != null && getFileName().endsWith(DEFAULT_EXTENSION)) 
				{
				return true;
				}
			setErrorMessage("The 'file' name must end with " + DEFAULT_EXTENSION);
			return false;
			}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
		 */
		protected boolean validatePage() 
			{
			return super.validatePage() && validateFilename();
			}

		@Override
		protected InputStream getInitialContents() 
			{
			ByteArrayInputStream bais = null;
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(createDefaultContent()); // argument must be Serializable
				oos.flush();
				oos.close();
				bais = new ByteArrayInputStream(baos.toByteArray());
				} 
			catch (IOException ioe) 
				{}
			catch (Exception exception)
				{}
			return bais;
			}
		
		private Object createDefaultContent()
			throws Exception
			{
			// 1) si crea l'xml
			createXml();
			// 2) si carica nel modello il file pmif tramite le seguenti istruzioni:
			pmifschemaDoc doc = new pmifschemaDoc();
			QNMType root = new QNMType(doc.load(xmlPath));
			QNMModel model = new QNMModel();
			// aggiungo i contenitori di default per i nodi, i carichi di lavoro
			// e richieste di servizio, sincronizzando il contenuto
			// presente nel file xml di default
			model.addChild(new NodeModel());
			model.addChild(new ServiceRequestModel());
			model.addChild(new WorkloadModel(model));
			model.setQNMType(root);
			return model;
			}
		
		private String createXml()
			{
			// si crea un file nella stessa directory del file pmif digitato dall'utente 
			// ma con estensione xml
			String string = getFileName();
			IPath path = getContainerFullPath();
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			IPath path2 = root.getLocation();
			// path3 contiene il path assoluto per la creazione del nuovo file pmif. 
			IPath path3 = path2.append(path);
			// path4 contiene il path del file pmif
			IPath path4 = path3.append(string);
			String string2 = path4.toString();
			// si elimina l'estensione pmif e si concatena xml
			String string3 = string2.substring(0, string2.length() - 4);
			// string4 contiene il path assoluto del file xml creato
			String string4 = string3.concat("xml");
			pmifschemaDoc doc = new pmifschemaDoc();
			// si memorizza il path del file xml
			this.xmlPath = string4;
			doc.setRootElementName("", "QueueingNetworkModel");
			// si aggiungono l'inserimento di 
			// un contenitore per i nodi NodeType, un contenitore
			// per le richieste di servizio ServiceRequestType,
			// un contenitore per i carichi di lavoro WorkloadType
			// a QNMType, in modo da non sollevare popup nella creazione.
			QNMType rootQNM = new QNMType();
			NodeType nodeType = new NodeType();
			ServiceRequestType serviceRequestType = new ServiceRequestType();
			WorkloadType workloadType = new WorkloadType();
			rootQNM.addNode(nodeType);
			rootQNM.addServiceRequest(serviceRequestType);
			rootQNM.addWorkload(workloadType);
			// si salva nella stessa directory del file pmif aperto con l'editor 
			// con lo stesso nome ma con estensione xml
			doc.save(string4, rootQNM);
			// si effettua il refresh dell'xml
			IContainer container = root.getContainerForLocation(path3);
			try {
				container.refreshLocal(IFile.DEPTH_INFINITE, null);
				} 
			catch (CoreException e) 
				{
				e.printStackTrace();
				}
			return string2;
			}
		}	

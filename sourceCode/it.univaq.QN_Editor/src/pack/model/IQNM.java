package pack.model;

import java.util.List;

import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.model.schema.ArcType;
import pack.model.schema.ClosedWorkloadType;
import pack.model.schema.DemandServType;
import pack.model.schema.NodeType;
import pack.model.schema.OpenWorkloadType;
import pack.model.schema.QNMType;
import pack.model.schema.ServerType;
import pack.model.schema.ServiceRequestType;
import pack.model.schema.SinkNodeType;
import pack.model.schema.SourceNodeType;
import pack.model.schema.TimeServType;
import pack.model.schema.TransitType;
import pack.model.schema.WorkUnitServType;
import pack.model.schema.WorkUnitServerType;
import pack.model.schema.WorkloadType;
import pack.model.structure.ArcModel;
import pack.model.structure.ClosedWorkloadModel;
import pack.model.structure.DemandServModel;
import pack.model.structure.NodeModel;
import pack.model.structure.OpenWorkloadModel;
import pack.model.structure.ServerModel;
import pack.model.structure.ServiceRequestModel;
import pack.model.structure.SinkNodeModel;
import pack.model.structure.SourceNodeModel;
import pack.model.structure.TimeServModel;
import pack.model.structure.WorkUnitServModel;
import pack.model.structure.WorkUnitServerModel;
import pack.model.structure.WorkloadModel;

public interface IQNM extends IModelElement
	{

	public static final String REQUEST_REMOVED_PROP = "IQNM.RequestRemoved";
	
	public static final String REQUESTS_ADDED_PROP = "IQNM.RequestsAdded";
	
	public static final String REQUESTS_REMOVED_PROP = "IQNM.RequestsRemoved";
	
	public static final String REQUEST_ADDED_PROP = "IQNM.RequestAdded";
	
	public static final String NODE_REMOVED_PROP = "IQNM.NodeRemoved";
	
	public static final String NODES_ADDED_PROP = "IQNM.NodesAdded";
	
	public static final String NODES_REMOVED_PROP = "IQNM.NodesRemoved";
	
	public static final String NODE_ADDED_PROP = "IQNM.NodeAdded";
	
	public static final String WORKLOADS_ADDED_PROP = "IQNM.WorkloadsAdded";
	
	public static final String WORKLOADS_REMOVED_PROP = "IQNM.WorkloadsRemoved";
	
	public static final String ARC_ADDED_PROP = "IQNM.ArcAdded";
	
	public static final String ARC_REMOVED_PROP = "IQNM.ArcRemoved";

	/*
	 * Restituisce la lista delle richieste di servizio relative
	 * al carico di lavoro di nome string e viene utilizzato
	 * nei seguenti metodi:
	 * pack.editParts.ClosedWorkloadTypeEditPart.getModelChildren(),
	 * pack.editParts.ClosedWorkloadTypeTreeEditPart.getModelChildren(),
	 * pack.editParts.OpenWorkloadTypeEditPart.getModelChildren(),
	 * pack.editParts.OpenWorkloadTypeTreeEditPart.getModelChildren(),
	 * pack.model.structure.ClosedWorkloadModel.getRequests(),
	 * pack.model.structure.OpenWorkloadModel.getRequests().
	 */
	public List<IRequest> getChildrenRequest(String string);

	/*
	 * Restituisce i transit che hanno come nome del carico di lavoro della richiesta di servizio
	 * a cui appartengono uguale a string2 e come attributo to uguale a string. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.RequestDeleteCommand.execute(),
	 * pack.editParts.DemandServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.TimeServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadDelayEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSinkEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSourceEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkUnitServTypeEditPart.getModelTargetConnections(),
	 * pack.model.structure.QNMModel.iRequestPMIFLoading(IRequest),
	 * pack.model.structure.QNMModel.iWorkloadPMIFLoading(ClosedWorkloadModel),
	 * pack.model.structure.QNMModel.iWorkloadPMIFLoading(OpenWorkloadModel),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	// string corrisponde al nome del server, destinazione
	// del transit
	// string2 corrisponde al nome del carico di lavoro
	public List<ITransit> getTransitsFromTarget(String string, String string2);
	
	/*
	 * Restituisce l'oggetto modello del nodo con nome string.
	 * Viene utilizzato dai seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel),
	 * pack.model.structure.QNMModel.arcTypeXMLLoading(ArcType).
	 */
	public INode getStructureNodeFromName(String string);
	
	/*
	 * Restituisce l'oggetto modello della richiesta con nome string.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.transitTypeXMLLoading(TransitType, ClosedWorkloadType),
	 * pack.model.structure.QNMModel.transitTypeXMLLoading(TransitType, DemandServType),
	 * pack.model.structure.QNMModel.transitTypeXMLLoading(TransitType, OpenWorkloadType),
	 * pack.model.structure.QNMModel.transitTypeXMLLoading(TransitType, TimeServType),
	 * pack.model.structure.QNMModel.transitTypeXMLLoading(TransitType, WorkUnitServType).
	 */
	public IRequest getTargetRequest(String name, String workloadName);

	/*
	 * Restituisce tutti gli oggetti modello corrispondenti
	 * ai nodi definiti. Viene utilizzato nei seguenti metodi:
	 * pack.editParts.QNMTypeEditPart.getModelChildren().
	 */
	public List<INode> getChildrenNodes();
	
	/*
	 * Restituisce tutte le richieste di servizio, nodi e
	 * carichi di lavoro definiti. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.editParts.QNMTypeTreeEditPart.getModelChildren(),
	 * pack.model.structure.QNMModel.equals(Object).
	 */
	public List<Object> getChildren();

	/*
	 * Restituisce la lista degli archi che hanno string come
	 * nome del nodo di origine dell'arco. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.NodeDeleteCommand.execute(),
	 * pack.model.structure.QNMModel.iNodePMIFLoading(INode),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public List<IArc> getSourceArcsFromID(String string);

	/*
	 * Restituisce la lista degli archi che hanno string come
	 * nome del nodo destinazione dell'arco. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.NodeDeleteCommand.execute(),
	 * pack.model.structure.QNMModel.iNodePMIFLoading(INode),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public List<IArc> getTargetArcsFromID(String string);
	
	/*
	 * Aggiunge la definizione di un nodo e notifica tale
	 * evento. Viene utilizzato nei seguenti metodi:
	 * pack.commands.NodeCreateCommand.redo(),
	 * pack.commands.NodeDeleteCommand.undo().
	 */
	public boolean addChild(INode newNode);

	/*
	 * Rimuovo un nodo e notifica tale evento. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.NodeCreateCommand.undo(),
	 * pack.commands.NodeDeleteCommand.redo().
	 */
	public boolean removeChild(INode newNode);
	
	/**
	 * Imposta gli attributi grafici di 
	 * default per gli elementi dell'xml.
	 * 
	 * @throws LoadingException
	 */
	/*
	 * Viene utilizzato nei seguenti metodi:
	 * pack.editors.PMIFGraphicalEditor.setInput(IEditorInput).
	 */
	public void loadingXML() throws LoadingException;

	/*
	 * Carica gli attributi transienti del modello dal
	 * file xml. Viene utilizzati nei seguenti metodi:
	 * pack.model.structure.QNMModel.readObject(ObjectInputStream).
	 */
	public void loadingPMIF() throws LoadingException;

	/*
	 * Crea un oggetto modello per l'arco dom fornito come parametro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public ArcModel arcTypeXMLLoading(ArcType arc) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per la richiesta dom fornita come parametro. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.QNMModel.iRequestsXMLLoading(ServiceRequestType).
	 */
	public DemandServModel iRequestXMLLoading(DemandServType request) throws LoadingException;

	/*
	 * Crea un oggetto modello per la richiesta dom fornita come parametro. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.QNMModel.iRequestsXMLLoading(ServiceRequestType).
	 */
	public TimeServModel iRequestXMLLoading(TimeServType timeServType) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per la richiesta dom fornita come parametro. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.QNMModel.iRequestsXMLLoading(ServiceRequestType).
	 */
	public WorkUnitServModel iRequestXMLLoading(WorkUnitServType workUnitServType) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per un contenitore dom di richieste di servizio. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public ServiceRequestModel iRequestsXMLLoading(ServiceRequestType newParam) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per il nodo fornito come parametro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodesXMLLoading(NodeType)
	 */
	public ServerModel iNodeXMLLoading(ServerType node) throws LoadingException;

	/*
	 * Crea un oggetto modello per il nodo fornito come parametro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodesXMLLoading(NodeType)
	 */
	public SinkNodeModel iNodeXMLLoading(SinkNodeType sinkNodeType) throws LoadingException;

	/*
	 * Crea un oggetto modello per il nodo fornito come parametro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodesXMLLoading(NodeType)
	 */
	public SourceNodeModel iNodeXMLLoading(SourceNodeType sourceNodeType) throws LoadingException;

	/*
	 * Crea un oggetto modello per il nodo fornito come parametro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodesXMLLoading(NodeType)
	 */
	public WorkUnitServerModel iNodeXMLLoading(WorkUnitServerType workUnitServerType) throws LoadingException;

	/*
	 * Crea un oggetto modello per un contenitore dom di nodi. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public NodeModel iNodesXMLLoading(NodeType newParam) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per un contenitore dom di carichi di lavoro. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public WorkloadModel iWorkloadsXMLLoading(WorkloadType workload) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per un carico di lavoro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iWorkloadsXMLLoading(WorkloadType).
	 */
	public ClosedWorkloadModel iWorkloadXMLLoading(ClosedWorkloadType workload) throws LoadingException;
	
	/*
	 * Crea un oggetto modello per un carico di lavoro. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iWorkloadsXMLLoading(WorkloadType).
	 */
	public OpenWorkloadModel iWorkloadXMLLoading(OpenWorkloadType workload) throws LoadingException;

	/*
	 * Crea un oggetto modello corrispondente a transitType presente in request. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public ITransit transitTypeXMLLoading(TransitType transitType) throws LoadingException;

	/*
	 * Carica gli attributi transienti di un modello di arco dal file xml. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.QNMModel.loadingPMIF().
	 */
	public void arcTypePMIFLoading(IArc arc) throws LoadingException;
	
	public void sourceTransitsPMIFLoading(DemandServModel demandServModel) throws LoadingException;
	
	public void sourceTransitsPMIFLoading(TimeServModel timeServModel) throws LoadingException;
	
	public void sourceTransitsPMIFLoading(WorkUnitServModel workUnitServModel) throws LoadingException;
	
	public void sourceTransitsPMIFLoading(ClosedWorkloadModel closedWorkloadModel) throws LoadingException;
	
	public void sourceTransitsPMIFLoading(OpenWorkloadModel openWorkloadModel) throws LoadingException;
	
	public void targetTransitsPMIFLoading(IRequest request) throws LoadingException;
	
	public void targetTransitsPMIFLoading(IWorkload workload) throws LoadingException;
	
	public void iRequestPMIFLoading(DemandServModel request) throws LoadingException;
	
	public void iRequestPMIFLoading(TimeServModel request) throws LoadingException;
	
	public void iRequestPMIFLoading(WorkUnitServModel request) throws LoadingException;
	
	/*
	 * Carica gli attributi transienti di un modello di contenitore di richieste dal file xml.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingPMIF().
	 */
	public void iRequestsPMIFLoading(ServiceRequestModel newParam) throws LoadingException;
	
	public void iNodePMIFLoading(ServerModel serverModel) throws LoadingException;
	
	public void iNodePMIFLoading(SinkNodeModel sinkNodeModel) throws LoadingException;
	
	public void iNodePMIFLoading(SourceNodeModel sourceNodeModel) throws LoadingException;
	
	public void iNodePMIFLoading(WorkUnitServerModel workUnitServerModel) throws LoadingException;
	
	/*
	 * Carica gli attributi transienti di un modello di contenitore di nodi dal file xml.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingPMIF()
	 */
	public void iNodesPMIFLoading(NodeModel newParam) throws LoadingException;
	
	/*
	 * Carica gli attributi transienti di un modello di contenitore di carichi di lavoro
	 * dal file xml. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.loadingPMIF().
	 */
	public void iWorkloadsPMIFLoading(WorkloadModel workload) throws LoadingException;
	
	/*
	 * Carica gli attributi transienti di un carico di lavoro dal file xml. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.model.structure.QNMModel.iWorkloadsPMIFLoading(WorkloadModel).
	 */
	public void iWorkloadPMIFLoading(ClosedWorkloadModel workload) throws LoadingException;

	/*
	 * Carica gli attributi transienti di un carico di lavoro dal file xml. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.model.structure.QNMModel.iWorkloadsPMIFLoading(WorkloadModel).
	 */
	public void iWorkloadPMIFLoading(OpenWorkloadModel workload) throws LoadingException;

	/*
	 * Rimuove una richiesta di servizio dal modello e notifica tale cancellazione. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.RequestCreateCommand.undo(),
	 * pack.commands.RequestDeleteCommand.redo(),
	 * pack.commands.WorkloadDeleteCommand.removeRequests(List<IRequest>)
	 */
	public boolean removeChild(IRequest child);
	
	/*
	 * Aggiunge una richiesta di servizio al modello e notifica tale inserimento. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.RequestCreateCommand.redo(),
	 * pack.commands.RequestDeleteCommand.undo(),
	 * pack.commands.WorkloadDeleteCommand.addRequests(List<IRequest>).
	 */
	public boolean addChild(IRequest child);

	/*
	 * Rimuove un contenitore di nodi dal modello e notifica tale cancellazione. Viene
	 * utilizzato nei seguenti metodi:
	 * pack.commands.NodesDeleteCommand.redo().
	 */
	public boolean removeChild(NodeModel child);

	/*
	 * Aggiunge un contenitore di nodi al modello e notifica tale inserimento. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.NodesDeleteCommand.undo().
	 */
	public boolean addChild(NodeModel child);

	/*
	 * Rimuove un contenitore di richieste di servizio dal modello e notifica tale cancellazione.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.RequestsDeleteCommand.redo().
	 */
	public boolean removeChild(ServiceRequestModel child);

	/*
	 * Aggiunge un contenitore di richieste di servizio al modello e notifica tale inserimento.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.RequestsDeleteCommand.undo().
	 */
	public boolean addChild(ServiceRequestModel child);

	/*
	 * Rimuove un contenitore di carichi di lavoro dal modello e notifica tale cancellazione.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.WorkloadsDeleteCommand.redo().
	 */
	public boolean removeChild(WorkloadModel child);

	/*
	 * Aggiunge un contenitore di carichi di lavoro dal modello e notifica inserimento.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.WorkloadsDeleteCommand.undo().
	 */
	public boolean addChild(WorkloadModel child);
	
	/*
	 * Genera una rappresentazione dom del modello. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.editors.PMIFGraphicalEditor.doSave(IProgressMonitor).
	 */
	public QNMType generateDom() throws SavingException;
	
	/*
	 * Imposta la rappresentazione dom da caricare e visualizzare. Viene utilizzato nei seguenti metodi:
	 * pack.editors.PMIFGraphicalEditor.setInput(IEditorInput).
	 */
	public void setQNMType(QNMType type) throws Exception;

	/*
	 * all'interno della rappresentazione dom trova l'arco con nodo from uguale a string e
	 * nodo to uguale a string2.
	 */
	public ArcType findArcType(String string, String string2) throws LoadingException;
	
	/*
	 * all'interno della rappresentazione dom trova la richiesta di servizio corrispondente
	 * a demandServModel.
	 */
	public DemandServType findRequestType(DemandServModel demandServModel) throws LoadingException;
	
	public TimeServType findRequestType(TimeServModel timeServModel) throws LoadingException;
	
	public WorkUnitServType findRequestType(WorkUnitServModel workUnitServModel) throws LoadingException;
	
	public ClosedWorkloadType findClosedWorkloadType(ClosedWorkloadModel closedWorkloadModel)
		throws LoadingException;
	
	public OpenWorkloadType findOpenWorkloadType(OpenWorkloadModel openWorkloadType)
		throws LoadingException;
	
	public ServerType findNodeType(ServerModel serverModel) throws LoadingException;
	
	public SourceNodeType findNodeType(SourceNodeModel sourceNodeModel) throws LoadingException;
	
	public SinkNodeType findNodeType(SinkNodeModel sinkNodeModel) throws LoadingException;
	
	public WorkUnitServerType findNodeType(WorkUnitServerModel workUnitServerModel) throws LoadingException;
	
	public boolean addChild(IArc arc);
	
	public boolean removeChild(IArc arc);
	
	// restituisce true se e solo se esiste un nodo con nome string
	public boolean existsNodeFromPMIF(String string);
	
	// restituisce true se e solo se esiste nelworkload due workload con nome string
	public boolean duplicateWorkloadFromXML(String string) throws LoadingException;
	
	public boolean duplicateWorkloadFromPMIF(String string);

	public boolean existsWorkloadFromXML(String wname);

	public boolean existsWorkloadFromPMIF(String string);
	
	public boolean existsNodeFromXML(String string);

	public boolean duplicateNodeFromXML(String string) throws LoadingException;

	public boolean duplicateNodeFromPMIF(String name);
	
	public List<IRequest> getRequestsFromNodeName(String string);
	
	// restituisce tutti i transit con nome string, compresi
	// i delay, i source e sink
	public List<ITransit> getTransitsFromTo(String string);

	}
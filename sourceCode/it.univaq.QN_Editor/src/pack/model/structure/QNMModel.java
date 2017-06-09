package pack.model.structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import pack.errorManagement.Finestra;
import pack.errorManagement.LoadingException;
import pack.errorManagement.SavingException;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.IWorkload;
import pack.model.altova.xml.types.SchemaDateTime;
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

public class QNMModel implements IQNM {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Delegate used to implemenent property-change-support. */
	private transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);
	
	private static IPropertyDescriptor[] descriptors  = 
		new IPropertyDescriptor[] 
		                        { 
								new TextPropertyDescriptor(NAME, "Name"),
								new TextPropertyDescriptor("QNM.description", "Description"),
								new TextPropertyDescriptor("QNM.dataTime", "Data time (format: \"yyyy-MM-dd'T'HH:mm:ss\")")
		                        };
	
	private Point pos = new Point(100,100);
	
	private List<NodeModel> nodes = new ArrayList<NodeModel>();
	
	private List<IArc> arcs = new ArrayList<IArc>();
	
	private List<WorkloadModel> workloads = new ArrayList<WorkloadModel>();
	
	private List<ServiceRequestModel> serviceRequests = new ArrayList<ServiceRequestModel>();
	
	// i seguenti attributi derivano dall'xml e vengono
	// utilizzati per il caricamento PMIF
	private transient String name;
	
	private transient String description;
	
	private transient SchemaDateTime dataTime;
	
	private transient List<ServiceRequestType> serviceRequestTypes;
	
	private transient List<WorkloadType> workloadTypes;
	
	private transient List<ArcType> arcTypes;
	
	private transient List<NodeType> nodeTypes;

	@Override
	public boolean addChild(INode newNode) 
		{
		if (nodes == null || nodes.size() == 0)
			{
			nodes = new ArrayList<NodeModel>();
			NodeModel nodeModel = new NodeModel();
			nodes.add(nodeModel);
			firePropertyChange(NODES_ADDED_PROP, null, newNode);
			}
		NodeModel nodeModel = nodes.get(0);
		nodeModel.addChild(newNode);
		// si notifica il cambio di proprietà
		firePropertyChange(NODE_ADDED_PROP, null, newNode);
		return true;
		}

	@Override
	public boolean addChild(IRequest newRequest) 
		{
		// bisogna verificare se newRequest è un centro di delay, un nodo source o un nodo sink
		if (newRequest instanceof WorkloadDelayModel)
			{
			WorkloadDelayModel workloadDelayModel = (WorkloadDelayModel)newRequest;
			workloadDelayModel.setServerID("");
			firePropertyChange(ClosedWorkloadModel.DELAY_ADDED_PROP, null, newRequest);
			return true;
			}
		else if (newRequest instanceof WorkloadSourceModel)
			{
			WorkloadSourceModel workloadSourceModel = (WorkloadSourceModel)newRequest;
			workloadSourceModel.setServerID("");
			firePropertyChange(OpenWorkloadModel.SOURCE_ADDED_PROP, null, newRequest);
			return true;
			}
		else if (newRequest instanceof WorkloadSinkModel)
			{
			WorkloadSinkModel workloadSinkModel = (WorkloadSinkModel)newRequest;
			workloadSinkModel.setServerID("");
			firePropertyChange(OpenWorkloadModel.SINK_ADDED_PROP, null, newRequest);
			return true;
			}
		else
			{
			if (serviceRequests == null || serviceRequests.size() == 0)
				{
				serviceRequests = new ArrayList<ServiceRequestModel>();
				ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
				serviceRequests.add(serviceRequestModel);
				firePropertyChange(REQUESTS_ADDED_PROP, null, newRequest);
				}
			ServiceRequestModel serviceRequestModel = serviceRequests.get(0);
			serviceRequestModel.addChild(newRequest);
			// si notifica il cambio di proprietà
			firePropertyChange(REQUEST_ADDED_PROP, null, newRequest);
			return true;
			}
		}

	@Override
	public boolean addChild(NodeModel child) 
		{
		nodes.add(child);
		firePropertyChange(NODES_ADDED_PROP, null, child);
		return true;
		}

	@Override
	public boolean addChild(ServiceRequestModel child) 
		{
		serviceRequests.add(child);
		firePropertyChange(REQUESTS_ADDED_PROP, null, child);
		return true;
		}

	@Override
	public boolean addChild(WorkloadModel child) 
		{
		workloads.add(child);
		firePropertyChange(WORKLOADS_ADDED_PROP, null, child);
		return true;
		}

	@Override
	public void arcTypePMIFLoading(IArc arc) throws LoadingException 
		{
		// in questa funzione si impostano i campi transienti
		String string = arc.getFromNode();
		String string2 = arc.getToNode();
		// 1) si preleva il nodo sorgente e destinazione dal suo nome con la funzione getNodeFromName;
		INode node = getStructureNodeFromName(string);
		if (node == null)
			Finestra.mostraLE(string+" è un nodo indefinito");
		INode node2 = getStructureNodeFromName(string2);
		if (node2 == null)
			Finestra.mostraLE(string2+" è un nodo indefinito");
		// 2) si impostano i nodi sorgente e destinazione dell'arco.
		arc.setSource(node);
		arc.setTarget(node2);
		// 3) si imposta il PropertyChangeSupport
		arc.setPcsDelegate(new PropertyChangeSupport(arc));
		// 4) si imposta isConnected a true
		arc.setConnected(true);
		// 5) si carica la descrizione dell'arco
		ArcType arcType = findArcType(string, string2);
		String string3 = null;
		try {
			string3 = arcType.getDescription().asString();
			} 
		catch (Exception e) 
			{
			Finestra.mostraLE(e.getMessage());
			}
		arc.setDescription(string3);
		// 6) si aggiunge arc come arco sorgente di node
		node.addSourceArc(arc);
		// 7) si aggiunge arc come arco destinazione di node2
		node2.addTargetArc(arc);
		// 8) si imposta il QNM
		arc.setIqnm(this);
		}

	@Override
	public ArcModel arcTypeXMLLoading(ArcType arc) 
		throws LoadingException 
		{
		// in questa funzione si istanzia un ArcModel a partire da un ArcType
		ArcModel arcModel = new ArcModel(arc,this);
		return arcModel;
		}

	public List<Object> getChildren() 
		{
		List<Object> list = new ArrayList<Object>();
		list.addAll(this.nodes);
		list.addAll(this.serviceRequests);
		list.addAll(this.workloads);
		return list;
		}

	public List<INode> getChildrenNodes() 
		{
		List<INode> list = new ArrayList<INode>();
		for (int i = 0; i < this.nodes.size(); i++)
			{
			NodeModel nodeModel = this.nodes.get(i);
			list.addAll(nodeModel.getNodes());
			}
		return list;
		}

	// string corrisponde al nome del carico di lavoro
	public List<IRequest> getChildrenRequest(String string) 
		{
		List<IRequest> list = new ArrayList<IRequest>();
		for (int i = 0; i < this.serviceRequests.size(); i++)
			{
			ServiceRequestModel serviceRequestModel = this.serviceRequests.get(i);
			list.addAll(serviceRequestModel.getChildrenRequestFromWorkload(string));
			}
		return list;
		}

	/**
	 * Restituisce la lista degli archi che hanno sorgente uguale a string.
	 * 
	 * @param string
	 * @return
	 */	
	public List<IArc> getSourceArcsFromID(String string) 
		{
		// come precondizione abbiamo che gli elementi IArc esistono
		List<IArc> list = new ArrayList<IArc>();
		for (IArc arc : this.arcs)
			{
			String string2 = arc.getFromNode();
			if (string2.equals(string))
				list.add(arc);
			}
		return list;
		}

	// come precondizione abbiamo che gli elementi IArc esistono
	public List<IArc> getTargetArcsFromID(String string) 
		{
		List<IArc> list = new ArrayList<IArc>();
		for (IArc arc : this.arcs)
			{
			String string2 = arc.getToNode();
			if (string2.equals(string))
				list.add(arc);
			}
		return list;
		}

	// string corrisponde al nome della richiesta.
	// stringW corrisponde al nome del carico di lavoro.
	// come precondizione abbiamo che i source transit di ogni richiesta esistono.
	public List<ITransit> getTransitsFromTarget(String string,String stringW) 
		{
		List<ITransit> list = new ArrayList<ITransit>();
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (IRequest request : serviceRequestModel.getChildrenRequest())
				{
				// si esegue il seguente ciclo for solo se il carico di lavoro
				// di demandServType è uguale a stringW
				String string3 = request.getWorkloadName();
				if (string3.equals(stringW))
					{
					for (ITransit transit : request.getSourceTransits())
						{
						String string2 = transit.getTo();
						if (string2.equals(string))
							list.add(transit);
						}
					}
				}
			}
		// si considerano anche gli elementi transit presenti nella definizione
		// di ogni IWorkload
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (IWorkload workload : workloadModel.getChildrenWorkload())
				{
				String string2W = workload.getWorkloadName();
				if (string2W.equals(stringW))
					{
					for (ITransit transit : workload.getSourceTransits())
						{
						String string2 = transit.getTo();
						if (string2.equals(string))
							list.add(transit);
						}
					}
				}
			}
		return list;
		}

	@Override
	public ServerModel iNodeXMLLoading(ServerType node) 
		throws LoadingException 
		{
		// 1) il primo INode viene impostato con point uguale a pos;
		// 2) ogni INode successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		// public ServerModel(ServerType serverType, Point point, IQNM iqnm)
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		ServerModel serverModel = new ServerModel(node,point,this);
		return serverModel;
		}

	@Override
	public void iNodesPMIFLoading(NodeModel newParam) throws LoadingException 
		{
		for (INode node : newParam.getNodes())
			{
			if (node instanceof ServerModel)
				iNodePMIFLoading((ServerModel)node);
			if (node instanceof SourceNodeModel)
				iNodePMIFLoading((SourceNodeModel)node);
			if (node instanceof SinkNodeModel)
				iNodePMIFLoading((SinkNodeModel)node);
			if (node instanceof WorkUnitServerModel)
				iNodePMIFLoading((WorkUnitServerModel)node);
			}
		// si imposta il PropertyChangeSupport
		newParam.setPcsDelegate(new PropertyChangeSupport(newParam));
		}

	@Override
	public NodeModel iNodesXMLLoading(NodeType newParam) 
		throws LoadingException 
		{
		NodeModel nodeModel = new NodeModel();
		for (int i = 0; i < newParam.getServerCount(); i++)
			{
			ServerType serverType = null;
			try {
				serverType = newParam.getServerAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ServerModel serverModel = iNodeXMLLoading(serverType);
			nodeModel.addChild(serverModel);
			}
		for (int i = 0; i < newParam.getSinkNodeCount(); i++)
			{
			SinkNodeType sinkNodeType = null;
			try {
				sinkNodeType = newParam.getSinkNodeAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			SinkNodeModel sinkNodeModel = iNodeXMLLoading(sinkNodeType);
			nodeModel.addChild(sinkNodeModel);
			}
		for (int i = 0; i < newParam.getSourceNodeCount(); i++)
			{
			SourceNodeType sourceNodeType = null;
			try {
				sourceNodeType = newParam.getSourceNodeAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			SourceNodeModel sourceNodeModel = iNodeXMLLoading(sourceNodeType);
			nodeModel.addChild(sourceNodeModel);
			}
		for (int i = 0; i < newParam.getWorkUnitServerCount(); i++)
			{
			WorkUnitServerType workUnitServerType = null;
			try {
				workUnitServerType = newParam.getWorkUnitServerAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			WorkUnitServerModel workUnitServerModel = iNodeXMLLoading(workUnitServerType);
			nodeModel.addChild(workUnitServerModel);
			}
		return nodeModel;
		}

	@Override
	public DemandServModel iRequestXMLLoading(DemandServType request) 
		throws LoadingException 
		{
		// 1) il primo IRequest viene impostato con point uguale a pos;
		// 2) ogni IRequest successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		// public DemandServModel(Point point, DemandServType demandServType)
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		DemandServModel demandServModel = new DemandServModel(point,request,this);
		return demandServModel;
		}

	@Override
	public void iRequestsPMIFLoading(ServiceRequestModel newParam)
			throws LoadingException 
		{
		for (IRequest request : newParam.getChildrenRequest())
			{
			if (request instanceof DemandServModel)
				iRequestPMIFLoading((DemandServModel)request);
			if (request instanceof TimeServModel)
				iRequestPMIFLoading((TimeServModel)request);
			if (request instanceof WorkUnitServModel)
				iRequestPMIFLoading((WorkUnitServModel)request);
			}
		// si imposta il PropertyChangeSupport
		newParam.setPcsDelegate(new PropertyChangeSupport(newParam));
		}

	@Override
	public ServiceRequestModel iRequestsXMLLoading(ServiceRequestType newParam) 
		throws LoadingException 
		{
		ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
		for (int i = 0; i < newParam.getDemandServiceRequestCount(); i++)
			{
			DemandServType demandServType = null;
			try {
				demandServType = newParam.getDemandServiceRequestAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			DemandServModel demandServModel = iRequestXMLLoading(demandServType);
			serviceRequestModel.addChild(demandServModel);
			}
		for (int i = 0; i < newParam.getTimeServiceRequestCount(); i++)
			{
			TimeServType timeServType = null;
			try {
				timeServType = newParam.getTimeServiceRequestAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			TimeServModel timeServModel = iRequestXMLLoading(timeServType);
			serviceRequestModel.addChild(timeServModel);
			}
		for (int i = 0; i < newParam.getWorkUnitServiceRequestCount(); i++)
			{
			WorkUnitServType workUnitServType = null;
			try {
				workUnitServType = newParam.getWorkUnitServiceRequestAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			WorkUnitServModel workUnitServModel = iRequestXMLLoading(workUnitServType);
			serviceRequestModel.addChild(workUnitServModel);
			}
		return serviceRequestModel;
		}

	@Override
	public void iWorkloadPMIFLoading(ClosedWorkloadModel workload)
			throws LoadingException 
		{
		workload.setQNM(this);
		workload.setPcsDelegate(new PropertyChangeSupport(workload));
		// si trova l'elemento dom corrispondente a workload
		ClosedWorkloadType closedWorkloadType = findClosedWorkloadType(workload);
		// si caricano gli attributi non identificativo di workload: numbersOfJobs, thinkTime,
		// timeUnits, thinkDevice.
		if (closedWorkloadType.hasNumberOfJobs())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(closedWorkloadType.getNumberOfJobs().asString());
				workload.setNumbersOfJobs(bigInteger);
				}
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il numero di jobs non è un BigInteger
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has not integer number of job");
				}
			catch (Exception e) 
				{
				Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: "+e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				// corrisponde al caso in cui il numero di jobs è una quantità negativa
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has negative number of job");
				}
			}
		else
			{
			// siamo nel caso in cui il carico di lavoro non ha un numero di jobs associato
			Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: " +
					"have not number of jobs");
			}	
		if (closedWorkloadType.hasThinkTime())
			{
			Float float1 = null;
			try {
				float1 = new Float(closedWorkloadType.getThinkTime().asString());
				workload.setThinkTime(float1);
				} 
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il tempo di pensiero non è un BigInteger
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has not float thinking time");
				}
			catch (Exception e) 
				{
				Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: "+e.getMessage());
				}
			if (float1.floatValue() < 0)
				{
				// corrisponde al caso in cui il tempo di pensiero è una quantità negativa
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has negative thinking time");
				}
			}
		else
			{
			// siamo nel caso in cui il carico di lavoro non ha un tempo di pensiero associato
			Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: " +
					"have not thinking time");
			}
		if (closedWorkloadType.hasTimeUnits())
			try {
				TimeUnitsModel timeUnitsModel = TimeUnitsModel.valueOf(closedWorkloadType.getTimeUnits().asString()); 
				workload.setTimeUnits(timeUnitsModel);
				}
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
				// dell'enumerazione TimeUnitsModel
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has not time units between: "+
					TimeUnitsModel.stampa());
				}
			catch (Exception e) 
				{
				Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: "+e.getMessage());
				}
		if (closedWorkloadType.hasThinkDevice())
			{
			try {
				workload.setThinkDevice(closedWorkloadType.getThinkDevice().asString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			workload.delayPMIFLoading();
			}
		}

	@Override
	public void iWorkloadPMIFLoading(OpenWorkloadModel workload)
			throws LoadingException 
		{
		workload.setQNM(this);
		workload.setPcsDelegate(new PropertyChangeSupport(workload));
		// si trova l'elemento dom corrispondente a workload
		OpenWorkloadType openWorkloadType = findOpenWorkloadType(workload);
		// si caricano gli attributi non identificativi di workload: arrivalRate, timeUnits, arrivesAt,
		// departsAt.
		if (openWorkloadType.hasArrivalRate())
			{
			Float float1 = null;
			try {
				float1 = new Float(openWorkloadType.getArrivalRate().asString());
				workload.setArrivalRate(float1);
				} 
			catch (NumberFormatException e) 
				{
				Finestra.mostraLE("Loading error: the "+workload.getWorkloadName()+" workload"+
					" has not float arrival rate");
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (float1.floatValue() < 0)
				{
				// corrisponde al caso in cui il tasso di arrivo è negativo
				Finestra.mostraLE("Loading error: the "+workload.getWorkloadName()+
					" workload has negative arrival rate");
				}
			}
		else
			{
			// siamo nel caso in cui il carico di lavoro aperto non ha un tasso di arrivo
			Finestra.mostraLE("Loading error for "+workload.getWorkloadName()+" workload: " +
					"have not arrival rate");
			}
		if (openWorkloadType.hasTimeUnits())
			try {
				workload.setTimeUnits(TimeUnitsModel.valueOf(openWorkloadType.getTimeUnits().asString()));
				}
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
				// dell'enumerazione TimeUnitsModel
				Finestra.mostraLE("The "+workload.getWorkloadName()+" workload has not time units between: "+
					TimeUnitsModel.stampa());
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (openWorkloadType.hasArrivesAt())
			{
			try {
				workload.setArrivesAt(openWorkloadType.getArrivesAt().asString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			workload.sourcePMIFLoading();
			}
		if (openWorkloadType.hasDepartsAt())
			{
			try {
				workload.setDepartsAt(openWorkloadType.getDepartsAt().asString());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			workload.sinkPMIFLoading();
			}
		}

	@Override
	public ClosedWorkloadModel iWorkloadXMLLoading(ClosedWorkloadType workload)
			throws LoadingException 
		{
		if (!workload.hasWorkloadName())
			{
			// siamo nel caso in cui il carico di lavoro non ha un nome
			Finestra.mostraLE("XML Loading error: closed workload without name");
			}
		String nome = null;
		try {
			nome = workload.getWorkloadNameAt(0).asString();
			} 
		catch (Exception e) 
			{
			Finestra.mostraLE(e.getMessage());
			}
		if (duplicateWorkloadFromXML(nome))
			{
			// siamo nel caso in cui ci sono due carichi di lavoro con nome uguale
			Finestra.mostraLE("XML Loading error: closed workload with duplicate name");
			}
		// 1) si assegna la posizione del centro di delay tramite il campo pos
		// public ClosedWorkloadModel(ClosedWorkloadType closedWorkloadType)
		ClosedWorkloadModel closedWorkloadModel = new ClosedWorkloadModel(workload);
		if (workload.hasThinkDevice())
			{
			String string = null;
			try {
				string = workload.getThinkDevice().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE("XML Loading error: "+e.getMessage());
				}
			if (existsNodeFromPMIF(string))
				{
				Point point = new Point(pos.x,pos.y);
				pos.x = pos.x + 100;
				WorkloadDelayModel workloadDelayModel = new WorkloadDelayModel(closedWorkloadModel,point);
				closedWorkloadModel.setDelayModel(workloadDelayModel);
				}
			else
				{
				// corrispode al caso in cui il dispositivo di pensamento non
				// corrisponde ad un nodo definito
				Finestra.mostraLE("XML Loading error:" +
						" the thinking device for "+nome+" workload don't exist");
				}
			}
		else
			{
			// corrisponde al caso in cui non è stato impostato un dispositivo di ritardo
			Finestra.mostraLE("XML Loading error:"+
					" the thinking device for "+nome+" workload don't setted");
			}
		return closedWorkloadModel;
		}

	@Override
	public OpenWorkloadModel iWorkloadXMLLoading(OpenWorkloadType workload)
			throws LoadingException 
		{
		if (!workload.hasWorkloadName())
			{
			// siamo nel caso in cui il carico di lavoro non ha un nome
			Finestra.mostraLE("XML Loading error: open workload without name");
			}
		String nome = null;
		try {
			nome = workload.getWorkloadNameAt(0).asString();
			} 
		catch (Exception e1) 
			{
			Finestra.mostraLE(e1.getMessage());
			}
		if (duplicateWorkloadFromXML(nome))
			{
			// siamo nel caso in cui ci sono due carichi di lavoro con nome uguale
			Finestra.mostraLE("XML Loading error: open workloads with duplicate name");
			}
		// 1) si assegna la posizione del nodo source e sink tramite il campo pos
		// public OpenWorkloadModel(OpenWorkloadType openWorkloadType)
		OpenWorkloadModel openWorkloadModel = new OpenWorkloadModel(workload);
		if (workload.hasArrivesAt())
			{
			String string = null;
			try {
				string = workload.getArrivesAt().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (existsNodeFromPMIF(string))
				{
				Point point = new Point(pos.x,pos.y);
				pos.x = pos.x + 100;
				WorkloadSourceModel workloadSourceModel = new WorkloadSourceModel(openWorkloadModel,point);
				openWorkloadModel.setSourceModel(workloadSourceModel);
				}
			else
				{
				// corrispode al caso in cui il nodo sorgente è indefinito
				Finestra.mostraLE("XML loading error: the source node "+string+" don't exists");
				}
			}
		else
			{
			// siamo nel caso in cui il carico di lavoro aperto non ha la sorgente di job
			Finestra.mostraLE("XML loading error: the "+nome+" workload has not source node");
			}
		if (workload.hasDepartsAt())
			{
			String string = null;
			try {
				string = workload.getDepartsAt().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (existsNodeFromPMIF(string))
				{
				Point point2 = new Point(pos.x,pos.y);
				pos.x = pos.x + 100;
				WorkloadSinkModel workloadSinkModel = new WorkloadSinkModel(openWorkloadModel,point2);
				openWorkloadModel.setSinkModel(workloadSinkModel);
				}
			else
				{
				// corrisponde al caso in cui il nodo pozzo è indefinito
				Finestra.mostraLE("XML loading error: the sink node "+string+" don't exists");
				}
			}
		else
			{
			// siamo nel caso in cui il carico di lavoro aperto non ha il nodo pozzo
			Finestra.mostraLE("XML loading error: the "+nome+" workload has not sink node");
			}
		return openWorkloadModel;
		}

	@Override
	public void iWorkloadsPMIFLoading(WorkloadModel workload)
			throws LoadingException 
		{
		for (ClosedWorkloadModel closedWorkloadModel : workload.getClosedWorkloads())
			{
			iWorkloadPMIFLoading(closedWorkloadModel);
			}
		for (OpenWorkloadModel openWorkloadModel : workload.getOpenWorkloads())
			{
			iWorkloadPMIFLoading(openWorkloadModel);
			}
		// si imposta il PropertyChangeSupport
		workload.setPcsDelegate(new PropertyChangeSupport(workload));
		// si imposta il QNM
		workload.setQNM(this);
		}

	@Override
	public WorkloadModel iWorkloadsXMLLoading(WorkloadType workload)
			throws LoadingException 
		{
		// public WorkloadModel(WorkloadType workloadType, List<IWorkload> list)
		List<IWorkload> list = new ArrayList<IWorkload>();
		for (int i = 0; i < workload.getClosedWorkloadCount(); i++)
			{
			ClosedWorkloadType closedWorkloadType = null;
			try {
				closedWorkloadType = workload.getClosedWorkloadAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			list.add(iWorkloadXMLLoading(closedWorkloadType));
			}
		for (int i = 0; i < workload.getOpenWorkloadCount(); i++)
			{
			OpenWorkloadType openWorkloadType = null;
			try {
				openWorkloadType = workload.getOpenWorkloadAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			list.add(iWorkloadXMLLoading(openWorkloadType));
			}
		WorkloadModel workloadModel = new WorkloadModel(list,this);
		return workloadModel;
		}

	@Override
	public void loadingPMIF() throws LoadingException 
		{
		// si caricano i nodi
		for (NodeModel nodeModel : this.nodes)
			{
			iNodesPMIFLoading(nodeModel);
			}
		// si caricano i carichi di lavoro
		for (WorkloadModel workloadModel : this.workloads)
			{
			iWorkloadsPMIFLoading(workloadModel);
			}
		// si caricano le richieste di servizio
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			iRequestsPMIFLoading(serviceRequestModel);
			}
		// si caricano gli archi
		for (IArc arc : this.arcs)
			{
			arcTypePMIFLoading(arc);
			}
		// si caricano i transit sorgenti dei carichi di lavoro e richieste di servizio
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (ClosedWorkloadModel closedWorkloadModel : workloadModel.getClosedWorkloads())
				{
				sourceTransitsPMIFLoading(closedWorkloadModel);
				}
			for (OpenWorkloadModel openWorkloadModel : workloadModel.getOpenWorkloads())
				{
				sourceTransitsPMIFLoading(openWorkloadModel);
				}
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (DemandServModel demandServModel : serviceRequestModel.getDemandServs())
				sourceTransitsPMIFLoading(demandServModel);
			for (TimeServModel timeServModel : serviceRequestModel.getTimeServs())
				sourceTransitsPMIFLoading(timeServModel);
			for (WorkUnitServModel workUnitServModel : serviceRequestModel.getWorkUnitServs())
				sourceTransitsPMIFLoading(workUnitServModel);
			}
		// si caricano i transit destinazione dei carichi di lavoro e richieste di servizio
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (IWorkload workload : workloadModel.getChildrenWorkload())
				targetTransitsPMIFLoading(workload);
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (IRequest request : serviceRequestModel.getChildrenRequest())
				targetTransitsPMIFLoading(request);
			}
		// si cancellano i reference alla struttura dom presenti nel modello
		this.arcTypes = null;
		this.nodeTypes = null;
		this.serviceRequestTypes = null;
		this.workloadTypes = null;
		}

	@Override
	public void loadingXML() 
		throws LoadingException 
		{
		// si caricano i nodeModel
		this.nodes = new ArrayList<NodeModel>();
		for (int i = 0; i < this.nodeTypes.size(); i++)
			{
			NodeType nodeType;
			nodeType = this.nodeTypes.get(i);
			NodeModel nodeModel = iNodesXMLLoading(nodeType);
			this.nodes.add(nodeModel);
			}
		// si caricano i workloadModel
		pos = new Point(100,100);
		this.workloads = new ArrayList<WorkloadModel>();
		for (int i = 0; i < this.workloadTypes.size(); i++)
			{
			WorkloadType workloadType;
			workloadType = this.workloadTypes.get(i);
			WorkloadModel workloadModel = iWorkloadsXMLLoading(workloadType);
			this.workloads.add(workloadModel);
			}
		// si caricano i serviceRequestModel
		this.serviceRequests = new ArrayList<ServiceRequestModel>();
		for (int i = 0; i < this.serviceRequestTypes.size(); i++)
			{
			ServiceRequestType serviceRequestType = null;
			serviceRequestType = this.serviceRequestTypes.get(i);
			ServiceRequestModel serviceRequestModel = iRequestsXMLLoading(serviceRequestType);
			this.serviceRequests.add(serviceRequestModel);
			}
		// si caricano gli arcModel
		this.arcs = new ArrayList<IArc>();
		for (int i = 0; i < this.arcTypes.size(); i++)
			{
			ArcType arcType;
			arcType = this.arcTypes.get(i);
			ArcModel arcModel = arcTypeXMLLoading(arcType);
			this.arcs.add(arcModel);
			}
		// si caricano i TransitModel sorgente 
		// (public TransitModel(IRequest request, IRequest request2, TransitType transitType))
		for (WorkloadModel workloadModel : this.workloads)
			{
			// per ogni carico di lavoro chiuso:
			// per ogni TransitType contenuto nel modello xml si chiama la funzione
			// transitTypeXMLLoading(TransitType transitType, ClosedWorkloadType request)
			for (ClosedWorkloadModel closedWorkloadModel : workloadModel.getClosedWorkloads())
				{
				ClosedWorkloadType closedWorkloadType = closedWorkloadModel.getXMLmodel();
				for (int i = 0; i < closedWorkloadType.getTransitCount(); i++)
					{
					TransitType transitType = null;
					try {
						transitType = closedWorkloadType.getTransitAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraLE(e.getMessage());
						}
					ITransit transit = transitTypeXMLLoading(transitType);
					closedWorkloadModel.addSourceTransit(transit);
					}
				// si cancellano i transitType storicizzati in closedWorkloadModel
				closedWorkloadModel.setTransitTypesToNull();
				}
			// per ogni carico di lavoro aperto:
			// per ogni TransitType contenuto nel modello xml si chiama la funzione
			// transitTypeXMLLoading(TransitType transitType, OpenWorkloadType request)
			for (OpenWorkloadModel openWorkloadModel : workloadModel.getOpenWorkloads())
				{
				OpenWorkloadType openWorkloadType = openWorkloadModel.getXMLmodel();
				for (int i = 0; i < openWorkloadType.getTransitCount(); i++)
					{
					TransitType transitType = null;
					try {
						transitType = openWorkloadType.getTransitAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraLE(e.getMessage());
						}
					ITransit transit = transitTypeXMLLoading(transitType);
					openWorkloadModel.addSourceTransit(transit);
					}
				// si cancellano i transitType storicizzati in openWorkloadModel
				openWorkloadModel.setTransitTypesToNull();
				}
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			// per ogni richiesta DemandServModel:
			// per ogni TransitType contenuto nel modello xml si chiama la funzione
			// transitTypeXMLLoading(TransitType transitType, DemandServType request)
			for (DemandServModel demandServModel : serviceRequestModel.getDemandServs())
				{
				DemandServType demandServType = demandServModel.getXMLmodel();
				for (int i = 0; i < demandServType.getTransitCount(); i++)
					{
					TransitType transitType = null;
					try {
						transitType = demandServType.getTransitAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraLE(e.getMessage());
						}
					ITransit transit = transitTypeXMLLoading(transitType);
					demandServModel.addSourceTransit(transit);
					}
				// si cancellano i transitType storicizzati in demandServModel
				demandServModel.setTransitTypesToNull();
				}
			// per ogni richiesta TimeServModel:
			// per ogni TransitType contenuto nel modello xml si chiama la funzione
			// transitTypeXMLLoading(TransitType transitType, TimeServType request)
			for (TimeServModel timeServModel : serviceRequestModel.getTimeServs())
				{
				TimeServType timeServType = timeServModel.getXMLmodel();
				for (int i = 0; i < timeServType.getTransitCount(); i++)
					{
					TransitType transitType = null;
					try {
						transitType = timeServType.getTransitAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraLE(e.getMessage());
						}
					ITransit transit = transitTypeXMLLoading(transitType);
					timeServModel.addSourceTransit(transit);
					}
				// si cancellano i transitType storicizzati in timeServModel
				timeServModel.setTransitTypesToNull();
				}
			// per ogni richiesta WorkUnitServModel:
			// per ogni TransitType contenuto nel modello xml si chiama la funzione
			// transitTypeXMLLoading(TransitType transitType, WorkUnitServType request)
			for (WorkUnitServModel workUnitServModel : serviceRequestModel.getWorkUnitServs())
				{
				WorkUnitServType workUnitServType = workUnitServModel.getXMLmodel();
				for (int i = 0; i < workUnitServType.getTransitCount(); i++)
					{
					TransitType transitType = null;
					try {
						transitType = workUnitServType.getTransitAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraLE(e.getMessage());
						}
					ITransit transit = transitTypeXMLLoading(transitType);
					workUnitServModel.addSourceTransit(transit);
					}
				// si cancellano i transitType storicizzati in workUnitServModel
				workUnitServModel.setTransitTypesToNull();
				}
			}
		}

	@Override
	public boolean removeChild(INode newNode) 
		{
		for (NodeModel nodeModel : this.nodes)
			{
			if (nodeModel.removeChild(newNode))
				{
				// si notifica il cambio di proprietà
				firePropertyChange(IQNM.NODE_REMOVED_PROP, null, newNode);
				return true;
				}
			}
		return false;
		}

	@Override
	public boolean removeChild(IRequest child) 
		{
		// quando si rimuove una richiesta, viene cancellato il relativo modello
		// a meno che non si tratti di un server source, sink o delay. In questo caso, bisogna aggiornare
		// anche il modello xml
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			if (serviceRequestModel.removeChild(child)) 
				{
				// si deve notificare l'evento di 
				// richiesta rimossa perchè le parti di edit per i carichi di lavoro non ascoltano
				// gli eventi del modello ServiceRequestModel
				firePropertyChange(IQNM.REQUEST_REMOVED_PROP, null, child); 
				return true;
				}
			}
		// bisogna considerare il caso in cui child sia un server di delay,
		// un nodo source o un nodo sink
		if (child instanceof WorkloadDelayModel)
			{
			WorkloadDelayModel workloadDelayModel = (WorkloadDelayModel)child;
			workloadDelayModel.setServerID(null);
			firePropertyChange(ClosedWorkloadModel.DELAY_REMOVED_PROP, null, child);
			return true;
			}
		if (child instanceof WorkloadSourceModel)
			{
			WorkloadSourceModel workloadSourceModel = (WorkloadSourceModel)child;
			workloadSourceModel.setServerID(null);
			firePropertyChange(OpenWorkloadModel.SOURCE_REMOVED_PROP, null, child);
			return true;			
			}
		if (child instanceof WorkloadSinkModel)
			{
			WorkloadSinkModel workloadSinkModel = (WorkloadSinkModel)child;
			workloadSinkModel.setServerID(null);
			firePropertyChange(OpenWorkloadModel.SINK_REMOVED_PROP, null, child);
			return true;			
			}
		return false;
		}

	@Override
	public boolean removeChild(NodeModel child) 
		{
		this.nodes.remove(child);
		// si notifica il cambio di proprietà
		firePropertyChange(NODES_REMOVED_PROP, null, child);
		return true;
		}

	@Override
	public boolean removeChild(ServiceRequestModel child) 
		{
		this.serviceRequests.remove(child);
		// si notifica il cambio di proprietà
		firePropertyChange(REQUESTS_REMOVED_PROP, null, child);
		return true;
		}

	@Override
	public boolean removeChild(WorkloadModel child) 
		{
		this.workloads.remove(child);
		// si notifica il cambio di proprietà
		firePropertyChange(WORKLOADS_REMOVED_PROP, null, child);
		return true;
		}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) 
		{
		try {
			if (l == null) 
				{
				Finestra.mostraIE("listener is null");
				}
			getPcsDelegate().addPropertyChangeListener(l);
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void firePropertyChange(String property, Object oldValue,
			Object newValue) 
		{
		if (getPcsDelegate().hasListeners(property)) 
			{
			getPcsDelegate().firePropertyChange(property, oldValue, newValue);
			}
		}

	@Override
	public Object getEditableValue() 
		{
		return this;
		}

	@Override
	public PropertyChangeSupport getPcsDelegate() 
		{
		if (this.pcsDelegate == null)
			{
			this.pcsDelegate = new PropertyChangeSupport(this);
			return this.pcsDelegate;
			}
		else
			return this.pcsDelegate;
		}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() 
		{
		return descriptors;
		}

	@Override
	public Object getPropertyValue(Object id) 
		{
		if (NAME.equals(id))
			{
			if (this.name != null)
				return this.name;
			else
				return "";
			}
		if ("QNM.description".equals(id))
			{
			if (this.description != null)
				return this.description;
			else
				return "";
			}
		if ("QNM.dataTime".equals(id))
			{
			if (this.dataTime != null)
				return this.dataTime.asString();
			else
				return "";
			}
		return null;
		}

	@Override
	public boolean isPropertySet(Object id) 
		{
		return false;
		}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) 
		{
		if (l != null) 
			{
			pcsDelegate.removePropertyChangeListener(l);
			}
		}

	@Override
	public void resetPropertyValue(Object id) 
		{}

	@Override
	public void setPcsDelegate(PropertyChangeSupport pcsDelegate) 
		{
		this.pcsDelegate = pcsDelegate;
		}

	@Override
	public void setPropertyValue(Object id, Object value) 
		{
		if (NAME.equals(id))
			{
			this.name = value.toString();
			// si notifica il cambio di proprietà
			firePropertyChange(NAME, null, value);
			}
		else if ("QNM.description".equals(id))
			{
			this.description = value.toString();
			// si notifica il cambio di proprietà
			firePropertyChange("QNM.description", null, value);
			}
		else if ("QNM.dataTime".equals(id))
			{
			this.dataTime = new SchemaDateTime(value.toString());
			// si notifica il cambio di proprietà
			firePropertyChange("QNM.dataTime", null, value);
			}
		else
			{
			try {
				Finestra.mostraIE("bad property");
				} 
			catch (Exception e) 
				{}
			}
		}
	
	@Override
	public INode getStructureNodeFromName(String string) 
		{
		for (NodeModel nodeModel : this.nodes)
			{
			for (INode node : nodeModel.getNodes())
				{
				String string2 = node.getServerName();
				if (string.equals(string2))
					return node;
				}
			}
		return null;
		}

	@Override
	public IRequest getTargetRequest(String name, String workloadName) 
		{
		// si considerano anche il centro di pensamento e il nodo source dei carichi di lavoro
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (IRequest request : serviceRequestModel.getChildrenRequest())
				{
				String string2 = request.getServerID();
				String string = request.getWorkloadName();
				if (name.equals(string2) && workloadName.equals(string))
					return request;
				}
			}
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (IWorkload workload : workloadModel.getChildrenWorkload())
				{
				String string = workload.getWorkloadName();
				if (workload instanceof ClosedWorkloadModel)
					{
					ClosedWorkloadModel closedWorkloadModel = (ClosedWorkloadModel)workload;
					String string2 = closedWorkloadModel.getThinkDevice();
					if (name.equals(string2) && string.equals(workloadName))
						return closedWorkloadModel.getDelay();
					}
				if (workload instanceof OpenWorkloadModel)
					{
					OpenWorkloadModel openWorkloadModel = (OpenWorkloadModel)workload;
					String string2 = openWorkloadModel.getSinkName();
					if (name.equals(string2) && string.equals(workloadName))
						return openWorkloadModel.getWorkloadSinkModel();
					}
				}
			}
		return null;
		}

	@Override
	public SinkNodeModel iNodeXMLLoading(SinkNodeType sinkNodeType)
			throws LoadingException 
		{
		// 1) il primo INode viene impostato con point uguale a pos;
		// 2) ogni INode successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		// public SinkNodeModel(SinkNodeType sinkNodeType2, Point point)
		SinkNodeModel sinkNodeModel = new SinkNodeModel(sinkNodeType,point,this);
		return sinkNodeModel;
		}

	@Override
	public SourceNodeModel iNodeXMLLoading(SourceNodeType sourceNodeType)
			throws LoadingException 
		{
		// 1) il primo INode viene impostato con point uguale a pos;
		// 2) ogni INode successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		// public SourceNodeModel(SourceNodeType sourceNodeType2, Point point)
		SourceNodeModel sourceNodeModel = new SourceNodeModel(sourceNodeType,point,this);
		return sourceNodeModel;
		}

	@Override
	public WorkUnitServerModel iNodeXMLLoading(
			WorkUnitServerType workUnitServerType) 
			throws LoadingException 
		{
		// 1) il primo INode viene impostato con point uguale a pos;
		// 2) ogni INode successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		// public WorkUnitServerModel(WorkUnitServerType workUnitServerType2, Point point)
		WorkUnitServerModel workUnitServerModel = new WorkUnitServerModel(workUnitServerType,point,this);
		return workUnitServerModel;
		}

	@Override
	public TimeServModel iRequestXMLLoading(TimeServType timeServType)
			throws LoadingException 
		{
		// 1) il primo IRequest viene impostato con point uguale a pos;
		// 2) ogni IRequest successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		// public TimeServModel(TimeServType timeServType, Point point)
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		TimeServModel timeServModel = new TimeServModel(timeServType,point,this);
		return timeServModel;
		}

	@Override
	public WorkUnitServModel iRequestXMLLoading(
			WorkUnitServType workUnitServType) 
		throws LoadingException 
		{
		// 1) il primo IRequest viene impostato con point uguale a pos;
		// 2) ogni IRequest successivo viene impostato con un point uguale a new Point(pos.x + 100, pos.y).
		// public WorkUnitServModel(WorkUnitServType workUnitServType, Point point)
		Point point = new Point(pos.x,pos.y);
		pos.x = pos.x + 100;
		WorkUnitServModel workUnitServModel = new WorkUnitServModel(workUnitServType,point,this);
		return workUnitServModel;
		}

	@Override
	public ITransit transitTypeXMLLoading(TransitType transitType) 
		throws LoadingException 
		{
		// public TransitModel(TransitType transitType)
		TransitModel transitModel = new TransitModel(transitType,this);
		return transitModel;
		}

	@Override
	public void setQNMType(QNMType type) throws Exception 
		{
		if (type.hasDate_Time())
			try {
				this.dataTime = type.getDate_Time();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE("Loading error: the qnm have bad data time");
				}
		if (type.hasDescription())
			try {
				this.description = type.getDescription().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
		if (type.hasName())
			try {
				this.name = type.getName().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
		this.arcTypes = new ArrayList<ArcType>();
		for (int i = 0; i < type.getArcCount(); i++)
			{
			ArcType arcType = null;
			try {
				arcType = type.getArcAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			this.arcTypes.add(arcType);
			}
		this.nodeTypes = new ArrayList<NodeType>();
		if (type.getNodeCount() == 0)
			{
			// siamo nel caso in cui la rete di code non ha un contenitore di nodi
			Finestra.mostraLE("Loading error: the qnm has no nodes container");
			}
		for (int i = 0; i < type.getNodeCount(); i++)
			{
			NodeType nodeType = null;
			try {
				nodeType = type.getNodeAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			this.nodeTypes.add(nodeType);
			}
		this.serviceRequestTypes = new ArrayList<ServiceRequestType>();
		if (type.getServiceRequestCount() == 0)
			{
			// siamo nel caso in cui il modello pmif non ha nessun contenitore di richieste di servizio
			Finestra.mostraLE("Loading error: the qnm has no service requests container");
			}
		for (int i = 0; i < type.getServiceRequestCount(); i++)
			{
			ServiceRequestType serviceRequestType = null;
			try {
				serviceRequestType = type.getServiceRequestAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			this.serviceRequestTypes.add(serviceRequestType);
			}
		this.workloadTypes = new ArrayList<WorkloadType>();
		if (type.getWorkloadCount() == 0)
			{
			// siamo nel caso in cui il modello pmif non ha cotenitori di carichi di lavoro
			Finestra.mostraLE("Loading error: the qnm has not workloads container");
			}
		for (int i = 0; i < type.getWorkloadCount(); i++)
			{
			WorkloadType workloadType = null;
			try {
				workloadType = type.getWorkloadAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraIE(e.getMessage());
				}
			this.workloadTypes.add(workloadType);
			}
		}

	@Override
	public QNMType generateDom() throws SavingException
		{
		// si carica la struttura QNMType a seconda del
		// contenuto degli associati e degli attributi di
		// type
		QNMType type2 = new QNMType();
		if (this.name != null)
			{
			try {
				type2.addName(this.name);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.description != null)
			{
			try {
				type2.addDescription(this.description);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		if (this.dataTime != null)
			{
			try {
				type2.addDate_Time(this.dataTime);
				} 
			catch (Exception e) 
				{
				Finestra.mostraSE(e.getMessage());
				}
			}
		// si generano gli elementi annidati
		if (this.serviceRequests.size() == 0)
			{
			// siamo nel caso in cui il modello pmif non ha nessun contenitore di richieste di servizio
			Finestra.mostraSE("Loading error: the qnm has no service requests container");
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			ServiceRequestType serviceRequestType = serviceRequestModel.generaDom();
			type2.addServiceRequest(serviceRequestType);
			}
		if (this.workloads.size() == 0)
			{
			// siamo nel caso in cui il modello pmif non ha cotenitori di carichi di lavoro
			Finestra.mostraSE("Loading error: the qnm has not workloads container");
			}
		for (WorkloadModel workloadModel : this.workloads)
			{
			WorkloadType workloadType = workloadModel.generaDom();
			type2.addWorkload(workloadType);
			}
		for (IArc arc : this.arcs)
			{
			ArcType arcType = arc.generaDom();
			type2.addArc(arcType);
			}
		if (this.nodes.size() == 0)
			{
			// siamo nel caso in cui il modello pmif non ha nessun contenitore di nodi
			Finestra.mostraSE("Saving error: the qnm has no nodes container");
			}
		for (NodeModel nodeModel : this.nodes)
			{
			NodeType nodeType = nodeModel.generaDom();
			type2.addNode(nodeType);
			}
		return type2;
		}

	@Override
	public boolean equals(Object arg0) 
		{
		if (!(arg0 instanceof QNMModel) || arg0 == null)
			return false;
		QNMModel model = (QNMModel)arg0;
		if (!(this.getChildren().equals(model.getChildren())))
			return false;
		if (!this.arcs.equals(model.arcs))
			return false;
		return true;
		}

	@Override
	public ArcType findArcType(String string, String string2) throws LoadingException
		{
		for (ArcType arcType : this.arcTypes)
			{
			String string3 = null;
			try {
				string3 = arcType.getFromNode().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string4 = null;
			try {
				string4 = arcType.getToNode().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (string.equals(string3) && string2.equals(string4))
				return arcType;
			}
		return null;
		}

	@Override
	public void iRequestPMIFLoading(DemandServModel request)
			throws LoadingException 
		{
		// si caricano gli attributi grafici di request
		request.setQNM(this);
		request.setPcsDelegate(new PropertyChangeSupport(request));
		// si caricano gli attributi non identificativi: timeUnits, serviceDemand, numberOfVisits
		// si preleva l'elemento dom corrispondente a request
		DemandServType demandServType = findRequestType(request);
		if (demandServType.hasTimeUnits())
			try {
				request.setTimeUnits(TimeUnitsModel.valueOf(demandServType.getTimeUnits().asString()));
				}
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
				// dell'enumerazione TimeUnitsModel
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+
						string+" and workload equal to "+string2+" has not time units between: "+
						TimeUnitsModel.stampa());
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (demandServType.hasServiceDemand())
			{
			Float float1 = null;
			try {
				float1 = new Float(demandServType.getServiceDemand().asString());
				request.setServiceDemand(float1);
				} 
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui la domanda di servizio non sia un float
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				try {
					Finestra.mostraLE("Loading error: the service request with server equal to "+
							string+" and workload equal to "+string2+" has not valid service demand: "+
						demandServType.getServiceDemand().asString());
					} 
				
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (float1.floatValue() < 0)
				{
				// corrisponde al caso in cui la domanda di servizio sia minore di zero
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+
					string+" and workload equal to "+string2+" has not valid service demand: "+
					float1.toString());
				}
			}
		else
			{
			String string = request.getServerID();
			String string2 = request.getWorkloadName();
			Finestra.mostraLE("Loading error: the service request with "+
					" server equal to "+string+" and workload equal to "+string2
					+" has not valid service demand");
			}	
		if (demandServType.hasNumberOfVisits())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(demandServType.getNumberOfVisits().asString());
				request.setNumberOfVisits(bigInteger);
				}
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il numero di visite non è un BigInteger
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				try {
					Finestra.mostraLE("Loading error: the service request with server equal to "+string+
							" and workload equal to "+
					string2+" has not valid number of visits: "+
					demandServType.getNumberOfVisits().asString());
					} 
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				// corrisponde al caso in cui il numero di visite non è positivo
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+string+
						" and workload equal to "+
						string2+" has not valid number of visits: "+bigInteger.toString());
				}	
			}
		}

	@Override
	public void iRequestPMIFLoading(TimeServModel request)
			throws LoadingException 
		{
		// si caricano gli attributi grafici di request
		request.setQNM(this);
		request.setPcsDelegate(new PropertyChangeSupport(request));
		// si caricano gli attributi non identificativi: timeUnits, serviceTime, numberOfVisits
		// si preleva l'elemento dom corrispondente a request
		TimeServType timeServType = findRequestType(request);
		if (timeServType.hasTimeUnits())
			try {
				request.setTimeUnits(TimeUnitsModel.valueOf(timeServType.getTimeUnits().asString()));
				} 
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
				// dell'enumerazione TimeUnitsModel
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+
					string+" and workload equal to "+string2+" has not time units between: "+
					TimeUnitsModel.stampa());
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		if (timeServType.hasServiceTime())
			{
			Float float1 = null;
			try {
				float1 = new Float(timeServType.getServiceTime().asString()); 
				request.setServiceTime(float1);
				} 
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il tempo di servizio non sia un float
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				try {
					Finestra.mostraLE("Loading error: the service request with server equal to "+
						string+" and workload equal to "+string2+" has not valid service time: "+
					timeServType.getServiceTime().asString());
					} 
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (float1.floatValue() < 0)
				{
				// corrisponde al caso in cui il tempo di servizio sia minore di zero
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+
						string+" and workload equal to "+string2+" has not valid service time: "+
						float1.toString());
				}
			}
		else
			{
			String string = request.getServerID();
			String string2 = request.getWorkloadName();
			Finestra.mostraLE("Loading error: the service request with server equal to "+string+
					" and workload equal to "+
				string2+" has not a service time");
			}	
		if (timeServType.hasNumberOfVisits())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(timeServType.getNumberOfVisits().asString());
				request.setNumberOfVisits(bigInteger);
				}
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il numero di visite non è un BigInteger
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				try {
					Finestra.mostraLE("Loading error: the service request with server equal to "+string+
							" and workload equal to "+
					string2+" has not valid number of visits: "+
					timeServType.getNumberOfVisits().asString());
					} 
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				// corrisponde al caso in cui il numero di visite non è positivo
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+string+
						" and workload equal to "+
						string2+" has not valid number of visits: "+bigInteger.toString());
				}	
			}
		}

	@Override
	public void iRequestPMIFLoading(WorkUnitServModel request)
			throws LoadingException 
		{
		// si caricano gli attributi grafici di request
		request.setQNM(this);
		request.setPcsDelegate(new PropertyChangeSupport(request));
		// si caricano gli attributi non identificativi: numberOfVisits
		// si preleva l'elemento dom corrispondente a request
		WorkUnitServType workUnitServType = findRequestType(request);
		if (workUnitServType.hasNumberOfVisits())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(workUnitServType.getNumberOfVisits().asString());
				request.setNumberOfVisits(bigInteger);
				}
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il numero di visite non è un BigInteger
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				try {
					Finestra.mostraLE("Loading error: the service request with server equal to "+string+
							" and workload equal to "+
							string2+" has not valid number of visits: "+
							workUnitServType.getNumberOfVisits().asString());
					} 
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				// corrisponde al caso in cui il numero di visite non è positivo
				String string = request.getServerID();
				String string2 = request.getWorkloadName();
				Finestra.mostraLE("Loading error: the service request with server equal to "+string+
						" and workload equal to "+
					string2+" has not valid number of visits: "+bigInteger.toString());
				}	
			}
		}

	@Override
	public DemandServType findRequestType(DemandServModel demandServModel)
			throws LoadingException 
		{
		String string = demandServModel.getServerID();
		String string2 = demandServModel.getWorkloadName();
		for (ServiceRequestType serviceRequestType : this.serviceRequestTypes)
			{
			for (int i = 0; i < serviceRequestType.getDemandServiceRequestCount(); i++)
				{
				DemandServType demandServType2 = null;
				try {
					demandServType2 = serviceRequestType.getDemandServiceRequestAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string3 = null;
				try {
					string3 = demandServType2.getServerID().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string4 = null;
				try {
					string4 = demandServType2.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string3) && string2.equals(string4))
					return demandServType2;
				}
			}
		return null;
		}

	@Override
	public TimeServType findRequestType(TimeServModel timeServModel)
			throws LoadingException 
		{
		String string = timeServModel.getServerID();
		String string2 = timeServModel.getWorkloadName();
		for (ServiceRequestType serviceRequestType : this.serviceRequestTypes)
			{
			for (int i = 0; i < serviceRequestType.getTimeServiceRequestCount(); i++)
				{
				TimeServType timeServType = null;
				try {
					timeServType = serviceRequestType.getTimeServiceRequestAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string3 = null;
				try {
					string3 = timeServType.getServerID().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string4 = null;
				try {
					string4 = timeServType.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string3) && string2.equals(string4))
					return timeServType;
				}
			}
		return null;
		}

	@Override
	public WorkUnitServType findRequestType(WorkUnitServModel workUnitServModel)
			throws LoadingException 
		{
		String string = workUnitServModel.getServerID();
		String string2 = workUnitServModel.getWorkloadName();
		for (ServiceRequestType serviceRequestType : this.serviceRequestTypes)
			{
			for (int i = 0; i < serviceRequestType.getWorkUnitServiceRequestCount(); i++)
				{
				WorkUnitServType workUnitServType = null;
				try {
					workUnitServType = serviceRequestType.getWorkUnitServiceRequestAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string3 = null;
				try {
					string3 = workUnitServType.getServerID().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string4 = null;
				try {
					string4 = workUnitServType.getWorkloadName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string3) && string2.equals(string4))
					return workUnitServType;
				}
			}
		return null;
		}

	@Override
	public ClosedWorkloadType findClosedWorkloadType(
			ClosedWorkloadModel closedWorkloadModel) throws LoadingException 
		{
		String string = closedWorkloadModel.getWorkloadName();
		for (WorkloadType workloadType : this.workloadTypes)
			{
			for (int i = 0; i < workloadType.getClosedWorkloadCount(); i++)
				{
				ClosedWorkloadType closedWorkloadType = null;
				try {
					closedWorkloadType = workloadType.getClosedWorkloadAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = closedWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return closedWorkloadType;
				}
			}
		return null;
		}

	@Override
	public OpenWorkloadType findOpenWorkloadType(
			OpenWorkloadModel openWorkloadModel) throws LoadingException 
		{
		String string = openWorkloadModel.getWorkloadName();
		for (WorkloadType workloadType : this.workloadTypes)
			{
			for (int i = 0; i < workloadType.getOpenWorkloadCount(); i++)
				{
				OpenWorkloadType openWorkloadType = null;
				try {
					openWorkloadType = workloadType.getOpenWorkloadAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = openWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return openWorkloadType;
				}
			}
		return null;
		}

	@Override
	public ServerType findNodeType(ServerModel serverModel)
			throws LoadingException 
		{
		String string = serverModel.getServerName();
		for (NodeType nodeType : this.nodeTypes)
			{
			for (int i = 0; i < nodeType.getServerCount(); i++)
				{
				ServerType serverType = null;
				try {
					serverType = nodeType.getServerAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = serverType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return serverType;
				}
			}
		return null;
		}

	@Override
	public SourceNodeType findNodeType(SourceNodeModel sourceNodeModel)
			throws LoadingException 
		{
		String string = sourceNodeModel.getServerName();
		for (NodeType nodeType : this.nodeTypes)
			{
			for (int i = 0; i < nodeType.getSourceNodeCount(); i++)
				{
				SourceNodeType sourceNodeType = null;
				try {
					sourceNodeType = nodeType.getSourceNodeAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = sourceNodeType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return sourceNodeType;
				}
			}
		return null;
		}

	@Override
	public SinkNodeType findNodeType(SinkNodeModel sinkNodeModel)
			throws LoadingException 
		{
		String string = sinkNodeModel.getServerName();
		for (NodeType nodeType : this.nodeTypes)
			{
			for (int i = 0; i < nodeType.getSinkNodeCount(); i++)
				{
				SinkNodeType sinkNodeType = null;
				try {
					sinkNodeType = nodeType.getSinkNodeAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = sinkNodeType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return sinkNodeType;
				}
			}
		return null;
		}

	@Override
	public WorkUnitServerType findNodeType(
			WorkUnitServerModel workUnitServerModel) 
			throws LoadingException 
		{
		String string = workUnitServerModel.getServerName();
		for (NodeType nodeType : this.nodeTypes)
			{
			for (int i = 0; i < nodeType.getWorkUnitServerCount(); i++)
				{
				WorkUnitServerType workUnitServerType = null;
				try {
					workUnitServerType = nodeType.getWorkUnitServerAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = workUnitServerType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					return workUnitServerType;
				}
			}
		return null;
		}

	@Override
	public void iNodePMIFLoading(ServerModel serverModel)
			throws LoadingException 
		{
		// si impostare l'iqnm
		serverModel.setQNM(this);
		// si imposta il PropertyChangeSupport
		serverModel.setPcsDelegate(new PropertyChangeSupport(serverModel));
		// si inizializzano gli archi
		serverModel.setSourceArcs(new ArrayList<IArc>());
		serverModel.setTargetArcs(new ArrayList<IArc>());
		// si impostano gli attributi non identificativi: quantity, schedulingPolicy.
		ServerType serverType = findNodeType(serverModel);
		if (serverType.hasQuantity())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(serverType.getQuantity().asString());
				serverModel.setQuantity(bigInteger);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				Finestra.mostraLE("Loading error: the "+serverModel.getServerName()+" server has negative quantity");
				}
			}
		else
			{
			// siamo nel caso in cui il server non ha una quantità
			Finestra.mostraLE("Loading error: the "+serverModel.getServerName()+" has not quantity");
			}
		if (serverType.hasSchedulingPolicy())
			try {
				serverModel.setSchedulingPolicy(SchedulingModel.valueOf(serverType.getSchedulingPolicy().asString()));
				} 
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui il valore impostato per la politica di scheduling
				// non appartiene
				// ad uno dei valori dell'enumerazione SchedulingModel
				Finestra.mostraLE("Loading error: the server node with name equal to "+
						this.name+" has not scheduling policy between: "+
						SchedulingModel.stampa());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		else
			{
			// siamo nel caso in cui il server non ha una politica di scheduling
			Finestra.mostraLE("Loading error: the "+serverModel.getServerName()+" has not scheduling policy");
			}	
		}

	@Override
	public void iNodePMIFLoading(SinkNodeModel sinkNodeModel)
			throws LoadingException 
		{
		// si imposta l'iqnm
		sinkNodeModel.setQNM(this);
		// si imposta il PropertyChangeSupport
		sinkNodeModel.setPcsDelegate(new PropertyChangeSupport(sinkNodeModel));
		// non ci sono attributi non identificativi
		// si inizializzano gli archi
		sinkNodeModel.setSourceArcs(new ArrayList<IArc>());
		sinkNodeModel.setTargetArcs(new ArrayList<IArc>());
		}

	@Override
	public void iNodePMIFLoading(SourceNodeModel sourceNodeModel)
			throws LoadingException 
		{
		// si imposta l'iqnm
		sourceNodeModel.setQNM(this);
		// si imposta il PropertyChangeSupport
		sourceNodeModel.setPcsDelegate(new PropertyChangeSupport(sourceNodeModel));
		// non ci sono attributi non identificativi
		// si inizializzano gli archi
		sourceNodeModel.setSourceArcs(new ArrayList<IArc>());
		sourceNodeModel.setTargetArcs(new ArrayList<IArc>());
		}

	@Override
	public void iNodePMIFLoading(WorkUnitServerModel workUnitServerModel)
			throws LoadingException 
		{
		// si imposta l'iqnm
		workUnitServerModel.setQNM(this);
		// si imposta il PropertyChangeSupport
		workUnitServerModel.setPcsDelegate(new PropertyChangeSupport(workUnitServerModel));
		// si impostano gli attributi non identificativi: quantity, schedulingPolicy, timeUnits,
		// serviceTime
		// si inizializzano gli archi
		workUnitServerModel.setSourceArcs(new ArrayList<IArc>());
		workUnitServerModel.setTargetArcs(new ArrayList<IArc>());
		WorkUnitServerType workUnitServerType = findNodeType(workUnitServerModel);
		if (workUnitServerType.hasQuantity())
			{
			BigInteger bigInteger = null;
			try {
				bigInteger = new BigInteger(workUnitServerType.getQuantity().asString());
				workUnitServerModel.setQuantity(bigInteger);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (bigInteger.compareTo(new BigInteger("0")) < 0)
				{
				Finestra.mostraLE("Loading error: the "+workUnitServerModel.getServerName()+" server has negative quantity");
				}
			}
		else
			{
			// siamo nel caso in cui il server non ha una quantità
			Finestra.mostraLE("Loading error: the "+workUnitServerModel.getServerName()+" has not quantity");
			}
		if (workUnitServerType.hasSchedulingPolicy())
			try {
				workUnitServerModel.setSchedulingPolicy(SchedulingModel.
						valueOf(workUnitServerType.getSchedulingPolicy().asString()));
				} 
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui il valore impostato per la politica di scheduling
				// non appartiene
				// ad uno dei valori dell'enumerazione SchedulingModel
				Finestra.mostraLE("Loading error: the server node with name equal to "+
						workUnitServerModel.getServerName()+" has not scheduling policy between: "+
						SchedulingModel.stampa());
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		else
			{
			// siamo nel caso in cui il server non ha una politica di scheduling
			Finestra.mostraLE("Loading error: the "+workUnitServerModel.getServerName()+" has not scheduling policy");
			}	
		if (workUnitServerType.hasServiceTime())
			{
			Float float1 = null;
			try {
				float1 = new Float(workUnitServerType.getServiceTime().asString()); 
				workUnitServerModel.setServiceTime(float1);
				} 
			catch (NumberFormatException e) 
				{
				// corrisponde al caso in cui il tempo di servizio non sia un float
				try {
					Finestra.mostraLE("Loading error: the server node with name equal to "+
							workUnitServerModel.getServerName()+" has not valid service time: "+
							workUnitServerType.getServiceTime().asString()
							);
					} 
				catch (Exception e1) 
					{
					Finestra.mostraLE(e1.getMessage());
					}
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			if (float1.floatValue() < 0)
				{
				// corrisponde al caso in cui il tempo di servizio sia minore di zero
				Finestra.mostraLE("Loading error: the server node with name equal to "+
						workUnitServerModel.getServerName()+" has not valid service time: "+
						float1.toString());
				}
			}
		else
			{
			// caso in cui il server non ha tempo di servizio
			Finestra.mostraLE("Loading error: the "+workUnitServerModel.getServerName()+" has not "+
				"service time");
			}
		if (workUnitServerType.hasTimeUnits())
			try {
				workUnitServerModel.setTimeUnits(TimeUnitsModel.valueOf(workUnitServerType.getTimeUnits().asString()));
				}
			catch (IllegalArgumentException e) 
				{
				// corrisponde al caso in cui l'unità di tempo non appartiene ad uno dei valori
				// dell'enumerazione TimeUnitsModel
				Finestra.mostraLE("Loading error: the server "+
						workUnitServerModel.getServerName()+
						" has not time units between: "+
						TimeUnitsModel.stampa());
				}
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
		}

	@Override
	public void sourceTransitsPMIFLoading(DemandServModel demandServModel)
			throws LoadingException 
		{
		String workloadName = demandServModel.getWorkloadName();
		DemandServType demandServType = findRequestType(demandServModel);
		for (int i = 0; i < demandServType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = demandServType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string = null;
			try {
				string = transitType.getTo().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ITransit transit = demandServModel.findSourceTransit(string);
			// si caricano gli attributi non identificativi di ogni transit: probability
			if (transitType.hasProbability())
				{
				Float float1 = null;
				try {
					float1 = new Float(transitType.getProbability().asString());
					transit.setProbability(float1);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
						demandServModel.getWorkloadName()+" and node equal to "+
						demandServModel.getServerID()+" has transit with bad probability");
					}
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (float1 < 0)
					{
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
						demandServModel.getWorkloadName()+" and node equal to "+
						demandServModel.getServerID()+" has transit with negative probability");
					}
				}
			else
				{
				// caso in cui il transit non ha la probabilità
				Finestra.mostraLE("Loading error: the transit with server equal to "+demandServModel.getServerID()+
						" and workload equal to "+workloadName+" has no probability");
				}	
			// si imposta il PropertyChangeSupport
			transit.setPcsDelegate(new PropertyChangeSupport(transit));
			// si imposta la richiesta sorgente e la richiesta destinazione
			// request è la richiesta destinazione
			IRequest request = getTargetRequest(string, workloadName);
			transit.setTarget(request);
			// demandServModel è la richiesta sorgente
			transit.setSource(demandServModel);
			// si connette l'arco impostando lo stato di connessione
			transit.setConnected(true);
			}
		}

	@Override
	public void sourceTransitsPMIFLoading(TimeServModel timeServModel)
			throws LoadingException 
		{
		String workloadName = timeServModel.getWorkloadName();
		TimeServType timeServType = findRequestType(timeServModel);
		for (int i = 0; i < timeServType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = timeServType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string = null;
			try {
				string = transitType.getTo().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ITransit transit = timeServModel.findSourceTransit(string);
			// si caricano gli attributi non identificativi di ogni transit: probability
			if (transitType.hasProbability())
				{
				Float float1 = null;
				try {
					float1 = new Float(transitType.getProbability().asString());
					transit.setProbability(float1);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
						timeServModel.getWorkloadName()+" and node equal to "+
						timeServModel.getServerID()+" has transit with bad probability");
					}
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (float1 < 0)
					{
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
						timeServModel.getWorkloadName()+" and node equal to "+
						timeServModel.getServerID()+" has transit with negative probability");
					}
				}
			else
				{
				// caso in cui il transit non ha la probabilità
				Finestra.mostraLE("Loading error: the transit with server equal to "+timeServModel.getServerID()+
						" and workload equal to "+workloadName+" has no probability");
				}	
			// si imposta il PropertyChangeSupport
			transit.setPcsDelegate(new PropertyChangeSupport(transit));
			// si imposta la richiesta sorgente e la richiesta destinazione
			// request è la richiesta destinazione
			IRequest request = getTargetRequest(string, workloadName);
			transit.setTarget(request);
			// timeServModel è la richiesta sorgente
			transit.setSource(timeServModel);
			// si connette l'arco impostando lo stato di connessione
			transit.setConnected(true);
			}
		}

	@Override
	public void sourceTransitsPMIFLoading(WorkUnitServModel workUnitServModel)
			throws LoadingException 
		{
		String workloadName = workUnitServModel.getWorkloadName();
		WorkUnitServType workUnitServType = findRequestType(workUnitServModel);
		for (int i = 0; i < workUnitServType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = workUnitServType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string = null;
			try {
				string = transitType.getTo().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ITransit transit = workUnitServModel.findSourceTransit(string);
			// si caricano gli attributi non identificativi di ogni transit: probability
			if (transitType.hasProbability())
				{
				Float float1 = null;
				try {
					float1 = new Float(transitType.getProbability().asString()); 
					transit.setProbability(float1);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
							workUnitServModel.getWorkloadName()+" and node equal to "+
							workUnitServModel.getServerID()+" has transit with bad probability");
					}
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (float1 < 0)
					{
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
							workUnitServModel.getWorkloadName()+" and node equal to "+
							workUnitServModel.getServerID()+" has transit with negative probability");
					}
				}
			else
				{
				// caso in cui il transit non ha la probabilità
				Finestra.mostraLE("Loading error: the transit with server equal to "+workUnitServModel.getServerID()+
						" and workload equal to "+workloadName+" has no probability");
				}	
			// si imposta il PropertyChangeSupport
			transit.setPcsDelegate(new PropertyChangeSupport(transit));
			// si imposta la richiesta sorgente e la richiesta destinazione
			// request è la richiesta destinazione
			IRequest request = getTargetRequest(string, workloadName);
			transit.setTarget(request);
			// workUnitServModel è la richiesta sorgente
			transit.setSource(workUnitServModel);
			// si connette l'arco impostando lo stato di connessione
			transit.setConnected(true);
			}
		}

	@Override
	public void sourceTransitsPMIFLoading(
			ClosedWorkloadModel closedWorkloadModel) 
		throws LoadingException 
		{
		String workloadName = closedWorkloadModel.getWorkloadName();
		ClosedWorkloadType closedWorkloadType = findClosedWorkloadType(closedWorkloadModel);
		for (int i = 0; i < closedWorkloadType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = closedWorkloadType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string = null;
			try {
				string = transitType.getTo().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ITransit transit = closedWorkloadModel.findSourceTransit(string);
			// si caricano gli attributi non identificativi di ogni transit: probability
			if (transitType.hasProbability())
				{
				Float float1 = null;
				try {
					float1 = new Float(transitType.getProbability().asString()); 
					transit.setProbability(float1);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
							closedWorkloadModel.getWorkloadName()+" and node equal to "+
							closedWorkloadModel.getThinkDevice()+" has transit with bad probability");
					}
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (float1 < 0)
					{
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
							closedWorkloadModel.getWorkloadName()+" and node equal to "+
							closedWorkloadModel.getThinkDevice()+" has transit with negative probability");
					}
				}
			else
				{
				// caso in cui il transit non ha la probabilità
				Finestra.mostraLE("Loading error: the "+workloadName+" transit without probability");
				}	
			// si imposta il PropertyChangeSupport
			transit.setPcsDelegate(new PropertyChangeSupport(transit));
			// si imposta la richiesta sorgente e la richiesta destinazione
			// request è la richiesta destinazione
			IRequest request = getTargetRequest(string, workloadName);
			transit.setTarget(request);
			// il delay di closedWorkloadModel è la richiesta sorgente
			if (closedWorkloadModel.hasThinkDevice())
				{
				WorkloadDelayModel workloadDelayModel = closedWorkloadModel.getDelay();
				transit.setSource(workloadDelayModel);
				}
			else Finestra.mostraLE("The closed workload "+closedWorkloadModel.getWorkloadName()+
					" have not a thinking device");
			// si connette l'arco impostando lo stato di connessione
			transit.setConnected(true);			
			}
		}

	@Override
	public void sourceTransitsPMIFLoading(OpenWorkloadModel openWorkloadModel)
		throws LoadingException 
		{
		String workloadName = openWorkloadModel.getWorkloadName();
		OpenWorkloadType openWorkloadType = findOpenWorkloadType(openWorkloadModel);
		for (int i = 0; i < openWorkloadType.getTransitCount(); i++)
			{
			TransitType transitType = null;
			try {
				transitType = openWorkloadType.getTransitAt(i);
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			String string = null;
			try {
				string = transitType.getTo().asString();
				} 
			catch (Exception e) 
				{
				Finestra.mostraLE(e.getMessage());
				}
			ITransit transit = openWorkloadModel.findSourceTransit(string);
			// si caricano gli attributi non identificativi di ogni transit: probability
			if (transitType.hasProbability())
				{	
				Float float1 = null;
				try {
					float1 = new Float(transitType.getProbability().asString()); 
					transit.setProbability(float1);
					} 
				catch (NumberFormatException e) 
					{
					// caso in cui la probabilità non è un float
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
						openWorkloadModel.getWorkloadName()+" and node equal to "+
						openWorkloadModel.getSourceName()+" has transit with bad probability");
					}
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (float1 < 0)
					{
					Finestra.mostraLE("Setting error: the service request with workload equal to "+
							openWorkloadModel.getWorkloadName()+" and node equal to "+
							openWorkloadModel.getSourceName()+" has transit with negative probability");
					}
				}
			else
				{
				// caso in cui il transit non ha la probabilità
				Finestra.mostraLE("Loading error: the "+workloadName+" transit without probability");
				}	
			// si imposta il PropertyChangeSupport
			transit.setPcsDelegate(new PropertyChangeSupport(transit));
			// si imposta la richiesta sorgente e la richiesta destinazione
			// request è la richiesta destinazione
			IRequest request = getTargetRequest(string, workloadName);
			transit.setTarget(request);
			// openWorkloadModel è la richiesta sorgente
			if (openWorkloadModel.hasArrivesAt())
				{
				WorkloadSourceModel workloadSourceModel = openWorkloadModel.getWorkloadSourceModel();
				transit.setSource(workloadSourceModel);
				}
			// si connette l'arco impostando lo stato di connessione
			transit.setConnected(true);			
			}
		}

	@Override
	public void targetTransitsPMIFLoading(IRequest request)
		throws LoadingException 
		{
		// si deve utilizzare il metodo getTransitsFromTarget. Attenzione ai nodi source, sink e delay
		String workload = request.getWorkloadName();
		String server = null; 
		server = request.getServerID();
		List<ITransit> list = getTransitsFromTarget(server, workload);
		request.setTargetTransits(list);
		}
	
	@Override
	public void targetTransitsPMIFLoading(IWorkload workload)
		throws LoadingException 
		{
		// si deve utilizzare il metodo getTransitsFromTarget. Attenzione ai nodi source, sink e delay
		String workload2 = workload.getWorkloadName();
		String server = null; 
		if (workload instanceof ClosedWorkloadModel)
			{
			ClosedWorkloadModel closedWorkloadModel = (ClosedWorkloadModel)workload;
			server = closedWorkloadModel.getThinkDevice();
			}
		else 
			{
			OpenWorkloadModel openWorkloadModel = (OpenWorkloadModel)workload;
			server = openWorkloadModel.getSinkName();
			}
		List<ITransit> list = getTransitsFromTarget(server, workload2);
		workload.setTargetTransits(list);
		}

	@Override
	public boolean addChild(IArc arc) 
		{
		this.arcs.add(arc);
		firePropertyChange(ARC_ADDED_PROP, null, arc);
		return true;
		}

	@Override
	public boolean removeChild(IArc arc) 
		{
		this.arcs.remove(arc);
		firePropertyChange(ARC_REMOVED_PROP, null, arc);
		return true;
		}

	@Override
	public boolean existsNodeFromPMIF(String string) 
		{
		// come precondizione ho il caricamento
		// dei NodeModel
		for (NodeModel nodeModel : this.nodes)
			{
			for (INode node : nodeModel.getNodes())
				{
				String string2 = node.getServerName();
				if (string.equals(string2))
					return true;
				}
			}
		return false;
		}

	@Override
	public boolean duplicateWorkloadFromPMIF(String string) 
		{
		List<IWorkload> list = new ArrayList<IWorkload>();
		for (WorkloadModel workloadModel : this.workloads)
			{
			list.addAll(workloadModel.getChildrenWorkload());
			}
		for (int i = 0; i < list.size(); i++)
			{
			IWorkload workload = list.get(i);
			String string2 = workload.getWorkloadName();
			if (string.equals(string2))
				{
				for (int j = i + 1; j < list.size(); j++)
					{
					IWorkload workload2 = list.get(j);
					String string3 = workload2.getWorkloadName();
					if (string.equals(string3))
						return true;
					}
				}
			}
		return false;
		}

	@Override
	public boolean duplicateWorkloadFromXML(String string) throws LoadingException
		{
		List<Object> list = new ArrayList<Object>();
		for (WorkloadType workloadType : this.workloadTypes)
			{
			for (int i = 0; i < workloadType.getClosedWorkloadCount(); i++)
				{
				ClosedWorkloadType closedWorkloadType = null;
				try {
					closedWorkloadType = workloadType.getClosedWorkloadAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = closedWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					list.add(closedWorkloadType);
					}
				}
			for (int i = 0; i < workloadType.getOpenWorkloadCount(); i++)
				{
				OpenWorkloadType openWorkloadType = null;
				try {
					openWorkloadType = workloadType.getOpenWorkloadAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				String string2 = null;
				try {
					string2 = openWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					list.add(openWorkloadType);
					}
				}
			}
		// si verifica se in list non ci sono duplicati con nome string
		for (int i = 0; i < list.size(); i ++)
			{
			Object object = list.get(i);
			if (object instanceof ClosedWorkloadType)
				{
				ClosedWorkloadType closedWorkloadType = (ClosedWorkloadType)object;
				String string2 = null;
				try {
					string2 = closedWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					for (int j = i + 1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ClosedWorkloadType)
							{
							ClosedWorkloadType closedWorkloadType2 = (ClosedWorkloadType)object2;
							String string3 = null;
							try {
								string3 = closedWorkloadType2.getWorkloadNameAt(0).asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof OpenWorkloadType)
							{
							OpenWorkloadType openWorkloadType = (OpenWorkloadType)object2;
							String string3 = null;
							try {
								string3 = openWorkloadType.getWorkloadNameAt(0).asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			if (object instanceof OpenWorkloadType)
				{
				OpenWorkloadType openWorkloadType = (OpenWorkloadType)object;
				String string2 = null;
				try {
					string2 = openWorkloadType.getWorkloadNameAt(0).asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					for (int j = i + 1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ClosedWorkloadType)
							{
							ClosedWorkloadType closedWorkloadType = (ClosedWorkloadType)object2;
							String string3 = null;
							try {
								string3 = closedWorkloadType.getWorkloadNameAt(0).asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof OpenWorkloadType)
							{
							OpenWorkloadType openWorkloadType2 = (OpenWorkloadType)object2;
							String string3 = null;
							try {
								string3 = openWorkloadType2.getWorkloadNameAt(0).asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			}
		return false;
		}

	@Override
	public boolean existsWorkloadFromXML(String wname) 
		{
		try {
			for (WorkloadType workloadType : this.workloadTypes)
				{
				for (int i = 0; i < workloadType.getClosedWorkloadCount(); i++)
					{
					ClosedWorkloadType closedWorkloadType = null;
					try {
						closedWorkloadType = workloadType.getClosedWorkloadAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string = null;
					try {
						string = closedWorkloadType.getWorkloadNameAt(0).asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (wname.equals(string))
						return true;
					}
				for (int i = 0; i < workloadType.getOpenWorkloadCount(); i++)
					{
					OpenWorkloadType openWorkloadType = null;
					try {
						openWorkloadType = workloadType.getOpenWorkloadAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string = null;
					try {
						string = openWorkloadType.getWorkloadNameAt(0).asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (wname.equals(string))
						return true;
					}
				}
			}
		catch (Exception exception)
			{}
		return false;
		}

	@Override
	public boolean existsWorkloadFromPMIF(String string) 
		{
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (IWorkload workload : workloadModel.getChildrenWorkload())
				{
				String string2 = workload.getWorkloadName();
				if (string.equals(string2))
					return true;
				}
			}
		return false;
		}

	@Override
	public boolean existsNodeFromXML(String string) 
		{
		try {
			for (NodeType nodeType : this.nodeTypes)
				{
				for (int i = 0; i < nodeType.getServerCount(); i++)
					{
					ServerType serverType = null;
					try {
						serverType = nodeType.getServerAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string2 = null;
					try {
						string2 = serverType.getName().asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (string.equals(string2))
						return true;
					}
				for (int i = 0; i < nodeType.getSinkNodeCount(); i++)
					{
					SinkNodeType sinkNodeType = null;
					try {
						sinkNodeType = nodeType.getSinkNodeAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string2 = null;
					try {
						string2 = sinkNodeType.getName().asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (string.equals(string2))
						return true;
					}
				for (int i = 0; i < nodeType.getSourceNodeCount(); i++)
					{
					SourceNodeType sourceNodeType = null;
					try {
						sourceNodeType = nodeType.getSourceNodeAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string2 = null;
					try {
						string2 = sourceNodeType.getName().asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (string.equals(string2))
						return true;
					}
				for (int i = 0; i < nodeType.getWorkUnitServerCount(); i++)
					{
					WorkUnitServerType workUnitServerType = null;
					try {
						workUnitServerType = nodeType.getWorkUnitServerAt(i);
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					String string2 = null;
					try {
						string2 = workUnitServerType.getName().asString();
						} 
					catch (Exception e) 
						{
						Finestra.mostraIE(e.getMessage());
						}
					if (string.equals(string2))
						return true;
					}
				}
			}
		catch (Exception exception)
			{}
		return false;
		}

	@Override
	public boolean duplicateNodeFromXML(String string) throws LoadingException 
		{
		// si mettono in una stessa lista tutti i nodi
		List<Object> list = new ArrayList<Object>();
		for (NodeType nodeType : this.nodeTypes)
			{
			for (int i = 0; i < nodeType.getServerCount(); i++)
				{
				ServerType serverType = null;
				try {
					serverType = nodeType.getServerAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				list.add(serverType);
				}
			for (int i = 0; i < nodeType.getSinkNodeCount(); i++)
				{
				SinkNodeType sinkNodeType = null;
				try {
					sinkNodeType = nodeType.getSinkNodeAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				list.add(sinkNodeType);
				}
			for (int i = 0; i < nodeType.getSourceNodeCount(); i++)
				{
				SourceNodeType sourceNodeType = null;
				try {
					sourceNodeType = nodeType.getSourceNodeAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				list.add(sourceNodeType);
				}
			for (int i = 0; i < nodeType.getWorkUnitServerCount(); i++)
				{
				WorkUnitServerType workUnitServerType = null;
				try {
					workUnitServerType = nodeType.getWorkUnitServerAt(i);
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				list.add(workUnitServerType);
				}
			}
		// si verifica se in list ci siano due nodi con nome string
		for (int i = 0; i < list.size(); i++)
			{
			Object object = list.get(i);
			if (object instanceof ServerType)
				{
				ServerType serverType = (ServerType)object;
				String string2 = null;
				try {
					string2 = serverType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					// si verifica se ci sia un secondo nodo con nome string
					for (int j = i +1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ServerType)
							{
							ServerType serverType2 = (ServerType)object2;
							String string3 = null;
							try {
								string3 = serverType2.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SinkNodeType)
							{
							SinkNodeType sinkNodeType = (SinkNodeType)object2;
							String string3 = null;
							try {
								string3 = sinkNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SourceNodeType)
							{
							SourceNodeType sourceNodeType = (SourceNodeType)object2;
							String string3 = null;
							try {
								string3 = sourceNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof WorkUnitServerType)
							{
							WorkUnitServerType workUnitServerType = (WorkUnitServerType)object2;
							String string3 = null;
							try {
								string3 = workUnitServerType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			if (object instanceof SinkNodeType)
				{
				SinkNodeType sinkNodeType = (SinkNodeType)object;
				String string2 = null;
				try {
					string2 = sinkNodeType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					// si verifica se ci sia un secondo nodo con nome string
					for (int j = i +1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ServerType)
							{
							ServerType serverType = (ServerType)object2;
							String string3 = null;
							try {
								string3 = serverType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SinkNodeType)
							{
							SinkNodeType sinkNodeType2 = (SinkNodeType)object2;
							String string3 = null;
							try {
								string3 = sinkNodeType2.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SourceNodeType)
							{
							SourceNodeType sourceNodeType = (SourceNodeType)object2;
							String string3 = null;
							try {
								string3 = sourceNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof WorkUnitServerType)
							{
							WorkUnitServerType workUnitServerType = (WorkUnitServerType)object2;
							String string3 = null;
							try {
								string3 = workUnitServerType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			if (object instanceof SourceNodeType)
				{
				SourceNodeType sourceNodeType = (SourceNodeType)object;
				String string2 = null;
				try {
					string2 = sourceNodeType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					// si verifica se ci sia un secondo nodo con nome string
					for (int j = i +1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ServerType)
							{
							ServerType serverType = (ServerType)object2;
							String string3 = null;
							try {
								string3 = serverType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SinkNodeType)
							{
							SinkNodeType sinkNodeType = (SinkNodeType)object2;
							String string3 = null;
							try {
								string3 = sinkNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SourceNodeType)
							{
							SourceNodeType sourceNodeType2 = (SourceNodeType)object2;
							String string3 = null;
							try {
								string3 = sourceNodeType2.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof WorkUnitServerType)
							{
							WorkUnitServerType workUnitServerType = (WorkUnitServerType)object2;
							String string3 = null;
							try {
								string3 = workUnitServerType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			if (object instanceof WorkUnitServerType)
				{
				WorkUnitServerType workUnitServerType = (WorkUnitServerType)object;
				String string2 = null;
				try {
					string2 = workUnitServerType.getName().asString();
					} 
				catch (Exception e) 
					{
					Finestra.mostraLE(e.getMessage());
					}
				if (string.equals(string2))
					{
					// si verifica se ci sia un secondo nodo con nome string
					for (int j = i +1; j < list.size(); j++)
						{
						Object object2 = list.get(j);
						if (object2 instanceof ServerType)
							{
							ServerType serverType2 = (ServerType)object2;
							String string3 = null;
							try {
								string3 = serverType2.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SinkNodeType)
							{
							SinkNodeType sinkNodeType = (SinkNodeType)object2;
							String string3 = null;
							try {
								string3 = sinkNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof SourceNodeType)
							{
							SourceNodeType sourceNodeType = (SourceNodeType)object2;
							String string3 = null;
							try {
								string3 = sourceNodeType.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						if (object2 instanceof WorkUnitServerType)
							{
							WorkUnitServerType workUnitServerType2 = (WorkUnitServerType)object2;
							String string3 = null;
							try {
								string3 = workUnitServerType2.getName().asString();
								} 
							catch (Exception e) 
								{
								Finestra.mostraLE(e.getMessage());
								}
							if (string.equals(string3))
								return true;
							}
						}
					}
				}
			}
		return false;
		}

	@Override
	public boolean duplicateNodeFromPMIF(String name) 
		{
		// si inseriscono in una stessa lista tutti i nodi
		List<INode> list = new ArrayList<INode>();
		for (NodeModel nodeModel : this.nodes)
			{
			list.addAll(nodeModel.getNodes());
			}
		for (int i = 0; i < list.size(); i++)
			{
			INode node = list.get(i);
			String string = node.getServerName();
			if (name.equals(string))
				{
				// si verifica se ci sia un secondo nodo con nome name
				for (int j = i + 1; j < list.size(); j++)
					{
					INode node2 = list.get(j);
					String string2 = node2.getServerName();
					if (name.equals(string2))
						return true;
					}
				}
			}
		return false;
		}

	@Override
	public List<IRequest> getRequestsFromNodeName(String string) 
		{
		// nella lista di ritorno, oltre alle richieste di
		// servizio vanno inserite anche tutti i WorkloadDelayModel
		// WorkloadSinkModel, WorkloadSourceModel
		List<IRequest> list = new ArrayList<IRequest>();
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (ClosedWorkloadModel workload : workloadModel.getClosedWorkloads())
				{
				WorkloadDelayModel workloadDelayModel = workload.getDelay();
				if (workloadDelayModel != null)
					{
					String string2 = workloadDelayModel.getServerID();
					if (string2.equals(string))
						list.add(workloadDelayModel);
					}
				else
					{
					Finestra.mostraSW("Setting warning: delay model for "+workload.getWorkloadName()+" is not setted");
					}
				}
			for (OpenWorkloadModel openWorkloadModel : workloadModel.getOpenWorkloads())
				{
				WorkloadSinkModel workloadSinkModel = openWorkloadModel.getWorkloadSinkModel();
				WorkloadSourceModel workloadSourceModel = openWorkloadModel.getWorkloadSourceModel();
				if (workloadSinkModel != null && workloadSourceModel != null)
					{
					String string2 = workloadSinkModel.getServerID();
					String string3 = workloadSourceModel.getServerID();
					if (string2.equals(string))
						list.add(workloadSinkModel);
					if (string3.equals(string))
						list.add(workloadSourceModel);
					}
				else if (workloadSinkModel == null)
					{
					Finestra.mostraSW("Setting warning: sink model for "+openWorkloadModel.getWorkloadName()+" is not setted");
					}
				else if (workloadSourceModel == null)
					{
					Finestra.mostraSW("Setting warning: source model for "+openWorkloadModel.getWorkloadName()+" is not setted");
					}
				}
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (IRequest request : serviceRequestModel.getChildrenRequest())
				{
				String string2 = request.getServerID();
				if (string2.equals(string))
					list.add(request);
				}
			}
		return list;
		}

	@Override
	public List<ITransit> getTransitsFromTo(String string) 
		{
		// nella lista di ritorno, oltre ai transit presenti nelle richieste di servizio
		// venno inseriti anche i transit relativi ai carichi di lavoro
		List<ITransit> list = new ArrayList<ITransit>();
		for (WorkloadModel workloadModel : this.workloads)
			{
			for (IWorkload workload : workloadModel.getChildrenWorkload())
				{
				List<ITransit> list2 = workload.getSourceTransits();
				for (ITransit transit : list2)
					{
					String string2 = transit.getTo();
					if (string2.equals(string))
						list.add(transit);
					}
				}
			}
		for (ServiceRequestModel serviceRequestModel : this.serviceRequests)
			{
			for (IRequest request : serviceRequestModel.getChildrenRequest())
				{
				List<ITransit> list2 = request.getSourceTransits();
				for (ITransit transit : list2)
					{
					String string2 = transit.getTo();
					if (string2.equals(string))
						list.add(transit);
					}
				}
			}
		return list;
		}
	}

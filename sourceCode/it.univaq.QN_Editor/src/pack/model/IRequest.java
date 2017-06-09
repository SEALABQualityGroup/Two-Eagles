package pack.model;

import java.io.Serializable;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;

import pack.figures.Etichetta;
import pack.model.schema.TransitType;


public interface IRequest extends IModelElement,
	Serializable
	{
	
	/** Property ID to use when the list of outgoing transits is modified. */
	public static final String SOURCE_TRANSIT_PROP = "Request.SourceTransit";
	
	public static final String TARGET_TRANSIT_PROP = "Request.TargetTransit";
	
	public static String LOCATION_PROP = "Request.location";
	
	/*
	 * Restituisce l'id del server contenuto nella richiesta di servizio. Viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.RequestDeleteCommand.execute(),
	 * pack.editParts.DemandServTypeEditPart.createFigureForModel(),
	 * pack.editParts.DemandServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.DemandServTypeEditPart.refreshName(),
	 * pack.editParts.DemandServTypeTreeEditPart.getText(),
	 * pack.editParts.TimeServTypeEditPart.createFigureForModel(),
	 * pack.editParts.TimeServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.TimeServTypeEditPart.refreshName(),
	 * pack.editParts.TimeServTypeTreeEditPart.getText(),
	 * pack.editParts.WorkloadDelayEditPart.createFigureForModel(),
	 * pack.editParts.WorkloadDelayEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadDelayEditPart.refreshVisuals(),
	 * pack.editParts.WorkloadSinkEditPart.createFigureForModel(),
	 * pack.editParts.WorkloadSinkEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSinkEditPart.refreshVisuals(),
	 * pack.editParts.WorkloadSourceEditPart.createFigureForModel(),
	 * pack.editParts.WorkloadSourceEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSourceEditPart.refreshVisuals(),
	 * pack.editParts.WorkUnitServTypeEditPart.createFigureForModel(),
	 * pack.editParts.WorkUnitServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkUnitServTypeEditPart.refreshName(),
	 * pack.editParts.WorkUnitServTypeTreeEditPart.getText(),
	 * pack.model.structure.QNMModel.getStructureRequestFromName(String),
	 * pack.model.structure.QNMModel.iRequestPMIFLoading(IRequest),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public String getServerID();

	/*
	 * Assegna un nuovo id del server alla richiesta di servizio.
	 * Viene utilizzato dai seguenti metodi:
	 * pack.model.structure.QNMModel.addChild(IRequest),
	 * pack.model.structure.QNMModel.removeChild(IRequest),
	 * pack.model.structure.WorkloadDelayModel.setPropertyValue(Object, Object).
	 */
	public void setServerID(String string);
	
	/*
	 * Restituisce il nome del carico di lavoro associato
	 * a questa richiesta di servizio. Viene utilizzato
	 * dai seguenti metodi:
	 * pack.commands.RequestDeleteCommand.execute(),
	 * pack.editParts.ClosedWorkloadTypeEditPart.getModelChildren(),
	 * pack.editParts.ClosedWorkloadTypeItemEditPart.getText(),
	 * pack.editParts.ClosedWorkloadTypeTreeEditPart.getModelChildren(),
	 * pack.editParts.ClosedWorkloadTypeTreeEditPart.getText(),
	 * pack.editParts.DemandServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.OpenWorkloadTypeEditPart.getModelChildren(),
	 * pack.editParts.OpenWorkloadTypeItemEditPart.getText(),
	 * pack.editParts.OpenWorkloadTypeTreeEditPart.getModelChildren(),
	 * pack.editParts.OpenWorkloadTypeTreeEditPart.getText(),
	 * pack.editParts.TimeServTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadDelayEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSinkEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkloadSourceEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkUnitServTypeEditPart.getModelTargetConnections(),
	 * pack.model.structure.QNMModel.getTransitsFromTarget(String, String),
	 * pack.model.structure.QNMModel.iRequestPMIFLoading(IRequest),
	 * pack.model.structure.QNMModel.iWorkloadPMIFLoading(ClosedWorkloadModel),
	 * pack.model.structure.QNMModel.iWorkloadPMIFLoading(OpenWorkloadModel),
	 * pack.model.structure.QNMModel.loadingXML(),
	 * pack.model.structure.WorkloadSinkModel.getWorkloadName(),
	 * pack.model.structure.WorkloadDelayModel.getWorkloadName(),
	 * pack.model.structure.WorkloadSourceModel.getWorkloadName().
	 */
	public String getWorkloadName();
	
	/*
	 * restituisce la radice del modello a cui questa richiesta 
	 * appartiene. Viene utilizzato nei seguenti metodi:
	 * pack.commands.RequestCreateCommand.RequestCreateCommand(IRequest, Rectangle),
	 * pack.commands.WorkloadDeleteCommand.addRequests(List<IRequest>),
	 * pack.commands.WorkloadDeleteCommand.removeRequests(List<IRequest>),
	 * pack.editPolicies.RequestDeleteEditPolicy.createDeleteCommand(GroupRequest),
	 * pack.model.structure.ClosedWorkloadModel.getRequests(),
	 * pack.model.structure.OpenWorkloadModel.getRequests(),
	 * pack.model.structure.WorkloadDelayModel.getQNM(),
	 * pack.model.structure.WorkloadSinkModel.getQNM(),
	 * pack.model.structure.WorkloadSourceModel.getQNM(),
	 * pack.outlinePages.WorkloadOutlinePage.createControl(Composite).
	 */
	public IQNM getQNM();
	
	public void setQNM(IQNM iqnm);

	public void removeSourceTransit(ITransit transit);

	public boolean hasTransit(ITransit transit);

	public void addSourceTransit(ITransit transit);

	public void setLocation(Point location) throws Exception;
	
	// la differenza tra setLocatione e setPoint è che il primo notifica il cambio di posizione
	// mentre il secondo no
	public void setPoint(Point point);
	
	public Point getLocation();

	public List<ITransit> getSourceTransits();

	public List<ITransit> getTargetTransits();
	
	// viene utilizzato per il caricamento della struttura
	public void setSourceTransits(List<ITransit> list);
	
	// viene utilizzato per il caricamento della struttura
	public void setTargetTransits(List<ITransit> list);
	
	public void removeTargetTransit(ITransit transitModel);

	public void addTargetTransit(ITransit transitModel);
	
	public List<TransitType> getTransitTypes();
	
	public ITransit findSourceTransit(String string);
	
	public void setTransitTypesToNull();
	
	public Etichetta getEtichetta();
		
	public void setEtichetta(Etichetta etichetta);
	
	// restituisce una copia dei transits sorgenti della richiesta
	public List<ITransit> getSourceTransitsCloned() throws Exception;
	
	public List<ITransit> getTargetTransitsCloned() throws Exception;
	
	public void setWorkloadName(String string);
	
	}

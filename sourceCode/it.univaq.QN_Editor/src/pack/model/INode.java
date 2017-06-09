package pack.model;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;

import pack.figures.Etichetta;



public interface INode extends IModelElement

	{
	
	public static String SOURCE_ARCS_PROP = "Node.sourceArcs";
	
	public static String TARGET_ARCS_PROP = "Node.targetArcs";
	
	public static String LOCATION_PROP = "Node.location";
		
	/*
	 * restituisce il nome di questo nodo e viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.NodeDeleteCommand.execute(),
	 * pack.editParts.ServerTypeEditPart.createFigureForModel(),
	 * pack.editParts.ServerTypeEditPart.refreshName(),
	 * pack.editParts.ServerTypeTreeEditPart.getText(),
	 * pack.editParts.SinkNodeTypeEditPart.createFigureForModel(),
	 * pack.editParts.SinkNodeTypeEditPart.refreshName(),
	 * pack.editParts.SinkNodeTypeTreeEditPart.getText(),
	 * pack.editParts.SourceNodeTypeEditPart.createFigureForModel(),
	 * pack.editParts.SourceNodeTypeEditPart.refreshName(),
	 * pack.editParts.SourceNodeTypeTreeEditPart.getText(),
	 * pack.editParts.WorkUnitServerTypeEditPart.createFigureForModel(),
	 * pack.editParts.WorkUnitServerTypeEditPart.refreshName(),
	 * pack.editParts.WorkUnitServerTypeTreeEditPart.getText(),
	 * pack.model.structure.ArcModel.ArcModel(INode, INode),
	 * pack.model.structure.QNMModel.getStructureNodeFromName(String),
	 * pack.model.structure.QNMModel.iNodePMIFLoading(INode),
	 * pack.model.structure.QNMModel.loadingXML(),
	 * pack.model.structure.ServerModel.equals(Object),
	 * pack.model.structure.SinkNodeModel.equals(Object),
	 * pack.model.structure.SourceNodeModel.equals(Object),
	 * pack.model.structure.WorkUnitServerModel.equals(Object).
	 */
	public String getServerName();

	/*
	 * Rimuove arc dagli archi in cui questo nodo risulta
	 * essere il nodo di destinazione, notificando
	 * tale cambio. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.model.structure.ArcModel.disconnect().
	 */
	public void removeSourceArc(IArc arc);

	/*
	 * Aggiunge arc agli archi in cui questo nodo risulta 
	 * essere l'origine dell'arco, notificando tale cambio.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.ArcModel.reconnect().
	 */
	public void addSourceArc(IArc arc);

	/*
	 * Assegna una posizione grafica a questo nodo ed 
	 * effettua la notifica del cambio. Viene
	 * utilizzato nei seguenti metodi:
	 * pack.commands.NodeCreateCommand.execute(),
	 * pack.commands.NodeSetConstraintCommand.redo(),
	 * pack.commands.NodeSetConstraintCommand.undo(),
	 * pack.model.structure.ServerModel.setPropertyValue(Object, Object),
	 * pack.model.structure.SinkNodeModel.setPropertyValue(Object, Object),
	 * pack.model.structure.SourceNodeModel.setPropertyValue(Object, Object),
	 * pack.model.structure.WorkUnitServerModel.setPropertyValue(Object, Object).
	 */
	public void setLocation(Point location) throws Exception;
	
	/*
	 * Restituisce la posizione grafica di questo elemento.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.NodeSetConstraintCommand.execute().
	 */
	public Point getLocation();

	/*
	 * Restituisce una lista di archi in cui questo nodo
	 * risulta essere la sorgente e viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.ArcTypeCreateCommand.canExecute(),
	 * pack.commands.ArcTypeReconnectCommand.checkSourceReconnection(),
	 * pack.editParts.ServerTypeEditPart.getModelSourceConnections(),
	 * pack.editParts.SinkNodeTypeEditPart.getModelSourceConnections(),
	 * pack.editParts.SourceNodeTypeEditPart.getModelSourceConnections(),
	 * pack.editParts.WorkUnitServerTypeEditPart.getModelSourceConnections().
	 */
	public List<IArc> getSourceArcs();

	/*
	 * Restituisce una lista di archi in cui questo nodo
	 * risulta essere la destinazione e viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.ArcTypeReconnectCommand.checkTargetReconnection(),
	 * pack.editParts.ServerTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.SinkNodeTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.SourceNodeTypeEditPart.getModelTargetConnections(),
	 * pack.editParts.WorkUnitServerTypeEditPart.getModelTargetConnections().
	 */
	public List<IArc> getTargetArcs();
	
	/*
	 * Assegna a this la lista degli archi in cui questo nodo è la sorgente. 
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodePMIFLoading(INode),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public void setSourceArcs(List<IArc> list);
	
	/*
	 * Assegna a this la lista degli archi in cui questo nodo è
	 * la destinazione. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.iNodePMIFLoading(INode),
	 * pack.model.structure.QNMModel.loadingXML().
	 */
	public void setTargetArcs(List<IArc> list);

	/*
	 * Aggiunge un arco in cui questo nodo è la destinazione,
	 * notificando la modifica. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.ArcModel.reconnect().
	 */
	public void addTargetArc(IArc arcModel);

	/*
	 * Rimuove un arco in cui questo nodo è la destinazione,
	 * notificando la modifica. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.model.structure.ArcModel.disconnect().
	 */
	public void removeTargetArc(IArc arcModel);
	
	public Etichetta getEtichetta();
	
	public void setEtichetta(Etichetta etichetta);
	
	public IQNM getQNM();
	
	public void setQNM(IQNM iqnm);
	
	// aggiorna le richieste di servizio, gli elementi transit
	// e gli archi che hanno string come vecchio nome e string2 come nuovo
	public void updateNameElements(String string, String string2);
	
	}

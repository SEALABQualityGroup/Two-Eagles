package pack.model;

import java.util.List;

import pack.errorManagement.SavingException;
import pack.model.schema.ArcType;

public interface IArc extends IModelElement
	{

	// restituisce il nodo sorgente dell'arco e viene utilizzato nel
	// comando di riconnessione dell'arco:
	// ArcTypeReconnectCommand(IArc), checkTargetReconnection()
	public INode getSource();
	
	/*
	 * restituisce il nodo target dell'arco e viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.ArcTypeCreateCommand.canExecute(),
	 * pack.commands.ArcTypeReconnectCommand.ArcTypeReconnectCommand(IArc),
	 * pack.commands.ArcTypeReconnectCommand.checkSourceReconnection().
	 */
	public INode getTarget();
	
	/*
	 * Imposta il nodo sorgente dell'arco e viene utilizzato
	 * nei seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel).
	 */
	public void setSource(INode source);
	
	/*
	 * Imposta il nodo target dell'arco e viene utilizzato
	 * nei seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel).
	 */
	public void setTarget(INode target);

	/*
	 * Disconnette l'arco
	 * dal nodo sorgente e destinazione. Viene utilizzato
	 * nei seguenti metodi:
	 * pack.commands.ArcTypeCreateCommand.undo(),
	 * pack.commands.ArcTypeDeleteCommand.execute(),
	 * pack.commands.NodeDeleteCommand.removeArcs(List<IArc>),
	 * pack.model.structure.ArcModel.reconnect(INode, INode). 
	 */
	public void disconnect();

	/*
	 * Riconnette l'arco al suo
	 * nodo sorgente e destinazione e viene utilizzato
	 * dai seguenti metodi:
	 * pack.commands.ArcTypeCreateCommand.redo(),
	 * pack.commands.ArcTypeDeleteCommand.undo(),
	 * pack.commands.NodeDeleteCommand.addArcs(List<IArc>),
	 * pack.model.structure.ArcModel.reconnect(INode, INode).
	 */
	public void reconnect();

	/*
	 * Riconnettere l'arco ai nodi
	 * forniti come parametri e viene utilizzato nei seguenti
	 * metodi:
	 * pack.commands.ArcTypeReconnectCommand.execute(),
	 * pack.commands.ArcTypeReconnectCommand.undo().
	 */
	public void reconnect(INode newSource, INode oldTarget) throws Exception;

	/*
	 * Inserisce un vincolo di benda
	 * all'arco e viene utilizzato nei seguenti metodi:
	 * pack.commands.ArcCreateBendpointCommand.execute(),
	 * pack.commands.ArcDeleteBendpointCommand.undo().
	 */
	public void insertBendpoint(int index, ArcTypeBendpoint bendpoint);
	
	/*
	 * Restituisce le bende dell'arco e e viene utilizzato nei seguenti metodi:
	 * pack.commands.ArcDeleteBendpointCommand.execute(),
	 * pack.commands.ArcMoveBendpointCommand.execute(),
	 * pack.editParts.ArcTypeEditPart.refreshBendpoints(),
	 * pack.model.structure.ArcModel.insertBendpoint(int, ArcTypeBendpoint),
	 * pack.model.structure.ArcModel.removeBendpoint(int),
	 * pack.model.structure.ArcModel.setBendpoint(int, ArcTypeBendpoint).
	 */
	public List<ArcTypeBendpoint> getBendpoints();

	/*
	 * Rimuove la benda di posizione index tra tutte
	 * le bende dell'arco e viene utilizzato nei seguenti
	 * metodi:
	 * pack.commands.ArcCreateBendpointCommand.undo(),
	 * pack.commands.ArcDeleteBendpointCommand.execute().
	 */
	public void removeBendpoint(int index);

	/*
	 * Aggiunge la benda bendpoint alla posizione index
	 * tra tutte le bende dell'arco e viene utilizzato nei
	 * seguenti metodi:
	 * pack.commands.ArcMoveBendpointCommand.execute(),
	 * pack.commands.ArcMoveBendpointCommand.undo().
	 */
	public void setBendpoint(int index, ArcTypeBendpoint bendpoint);
	
	/*
	 * Restituisce il nome del nodo che origina l'arco e viene
	 * utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel),
	 * pack.model.structure.QNMModel.getSourceArcsFromID(String).
	 */
	public String getFromNode();
	
	/*
	 * Restituisce il nome del nodo a cui giunge l'arco e
	 * viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel),
	 * pack.model.structure.QNMModel.getTargetArcsFromID(String).
	 */
	public String getToNode();
	
	/*
	 * Impostare lo stato di connessione dell'arco. Se
	 * b è true l'arco viene considerato conesso. Viene
	 * utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.arcTypePMIFLoading(ArcModel).
	 */
	public void setConnected(boolean b);

	/*
	 * Genera l'elemento dom corrispondente a questo arco
	 * e viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.generateDom().
	 */
	public ArcType generaDom() throws SavingException;
		
	public void setDescription(String string);
	
	public boolean isConnected();
	
	public void setIqnm(IQNM iqnm);
	
	public void setFromNode(String string);
	
	public void setToNode(String string);
	
	}

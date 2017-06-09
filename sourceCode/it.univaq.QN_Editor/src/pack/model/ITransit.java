package pack.model;

import java.util.List;

import pack.errorManagement.SavingException;
import pack.model.schema.TransitType;

public interface ITransit extends IModelElement, Cloneable
	{

	/*
	 * Restituisce la richiesta sorgente di questo transit.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.TransitTypeReconnectCommand.checkTargetReconnection(),
	 * pack.commands.TransitTypeReconnectCommand.TransitTypeReconnectCommand(ITransit).
	 */
	public IRequest getSource();

	/*
	 * Restituisce la richiesta destinazione di questo transit.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.TransitTypeCreateCommand.canExecute(),
	 * pack.commands.TransitTypeReconnectCommand.checkSourceReconnection(),
	 * pack.commands.TransitTypeReconnectCommand.TransitTypeReconnectCommand(ITransit).
	 */
	public IRequest getTarget();

	/*
	 * Disconnette il transit dai suoi nodi sorgente e 
	 * destinazione. Viene utilizzato nei seguenti metodi:
	 * pack.commands.RequestDeleteCommand.removeTransits(List<ITransit>),
	 * pack.commands.TransitTypeCreateCommand.undo(),
	 * pack.commands.TransitTypeDeleteCommand.execute(),
	 * pack.model.structure.TransitModel.reconnect(IRequest, IRequest).
	 */
	public void disconnect();

	/*
	 * Riconnette il transit ai suoi nodi sorgente e 
	 * destinazione. Viene utilizzato nei seguenti metodi:
	 * pack.commands.RequestDeleteCommand.addTransits(List<ITransit>),
	 * pack.commands.TransitTypeCreateCommand.redo(),
	 * pack.commands.TransitTypeDeleteCommand.undo(),
	 * pack.model.structure.TransitModel.reconnect(IRequest, IRequest).
	 */
	public void reconnect();

	/*
	 * Assegna newSource come sorgente di questo transit e 
	 * oldTarget come destinazione. Viene utilizzato nei seguenti
	 * metodi:
	 * pack.commands.TransitTypeReconnectCommand.execute(),
	 * pack.commands.TransitTypeReconnectCommand.undo(),
	 * pack.model.structure.TransitModel.TransitModel(IRequest, IRequest).
	 */
	public void reconnect(IRequest newSource, IRequest oldTarget) throws Exception;

	/*
	 * Inserisce bendpoint nella lista dei bendpoint di
	 * questo transit alla posizione index. Viene utilizzato
	 * dai seguenti metodi:
	 * pack.commands.TransitCreateBendpointCommand.execute(),
	 * pack.commands.TransitDeleteBendpointCommand.undo().
	 */
	public void insertBendpoint(int index, TransitTypeBendpoint bendpoint);
	
	/*
	 * Restituisce la lista dei bendpoint di questo transit.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.commands.TransitDeleteBendpointCommand.execute(),
	 * pack.commands.TransitMoveBendpointCommand.execute(),
	 * pack.editParts.TransitTypeEditPart.refreshBendpoints(),
	 * pack.model.structure.TransitModel.insertBendpoint(int, TransitTypeBendpoint),
	 * pack.model.structure.TransitModel.removeBendpoint(int),
	 * pack.model.structure.TransitModel.setBendpoint(int, TransitTypeBendpoint).
	 */
	public List<TransitTypeBendpoint> getBendpoints();

	/*
	 * Rimuove il bendpoint di posizione index da questo
	 * transit. Viene utilizzato dai seguenti metodi:
	 * pack.commands.TransitCreateBendpointCommand.undo(),
	 * pack.commands.TransitDeleteBendpointCommand.execute().
	 */
	public void removeBendpoint(int index);

	/*
	 * Imposta il bendpoint di posizione index di questo transit
	 * uguale a bendpoint. Viene utilizzato dai seguenti
	 * metodi:
	 * pack.commands.TransitMoveBendpointCommand.execute(),
	 * pack.commands.TransitMoveBendpointCommand.undo().
	 */
	public void setBendpoint(int index, TransitTypeBendpoint bendpoint);
	
	/*
	 * Assegna uno stato di connessione alla sua sorgente
	 * e destinazione di questo transit. Viene utilizzato
	 * dai seguenti metodi:
	 * pack.model.structure.QNMModel.transitTypePMIFLoading(ITransit).
	 */
	public void setConnected(boolean isConnected);
	
	/*
	 * Restituisce l'id del server destinazione di questo
	 * transit. Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.QNMModel.getTransitsFromTarget(String, String) 
	 */
	public String getTo();

	/*
	 * Genera la rappresentazione dom di questo oggetto.
	 * Viene utilizzato nei seguenti metodi:
	 * pack.model.structure.ClosedWorkloadModel.generaDom(),
	 * pack.model.structure.DemandServModel.generaDom(),
	 * pack.model.structure.OpenWorkloadModel.generaDom(),
	 * pack.model.structure.TimeServModel.generaDom(),
	 * pack.model.structure.WorkUnitServModel.generaDom().
	 */
	public TransitType generaDom() throws SavingException;
	
	public void setProbability(Float float1);
	
	public void setSource(IRequest source);
	
	public void setTarget(IRequest target);
	
	public Object clone() throws CloneNotSupportedException;
	
	public void setTo(String string);

	}

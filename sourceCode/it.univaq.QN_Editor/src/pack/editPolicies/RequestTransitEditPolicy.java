package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import pack.commands.TransitTypeCreateCommand;
import pack.commands.TransitTypeReconnectCommand;
import pack.model.IRequest;
import pack.model.ITransit;

// allow the creation of connections and 
// and the reconnection of connections between instances IRequest

public class RequestTransitEditPolicy 
	extends GraphicalNodeEditPolicy 
	{
	
	/* Returns the Command that will create the connection. 
	 * This is second part of creation. CreateConnectionRequest.getStartCommand() 
	 * is used here to obtain the contribution from the EditPart 
	 * from which the User started the creation.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) 
		{
		TransitTypeCreateCommand cmd	= (TransitTypeCreateCommand) request.getStartCommand();
		try {
			cmd.setTarget((IRequest)getHost().getModel());
			} 
		catch (Exception e) 
			{}
		return cmd;
		}

	/* Returns the Command that represents the first half of creating a connection. 
	 * This Command will be passed to the target node EditPart. 
	 * The target node may do anything necessary to create a Command 
	 * that represents the entire creation.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) 
		{
		IRequest source = (IRequest)getHost().getModel();
		TransitTypeCreateCommand cmd = null;
		try {
			cmd = new TransitTypeCreateCommand(source);
			request.setStartCommand(cmd);
			} 
		catch (Exception e) 
			{}
		return cmd;
		}
	
	/* Returns the Command to reconnect a connection's source end to the host.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) 
		{
		ITransit conn = (ITransit) request.getConnectionEditPart().getModel();
		IRequest newSource = (IRequest)getHost().getModel();
		TransitTypeReconnectCommand cmd = null;
		try {
			cmd = new TransitTypeReconnectCommand(conn);
			cmd.setNewSource(newSource);
			}
		catch (Exception exception)
			{}
		return cmd;
		}
	
	/* Returns the Command to reconnect a connection's target end to the host.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */	
	protected Command getReconnectTargetCommand(ReconnectRequest request) 
		{
		ITransit conn = (ITransit) request.getConnectionEditPart().getModel();
		IRequest newTarget = (IRequest) getHost().getModel();
		TransitTypeReconnectCommand cmd = null;
		try {
			cmd = new TransitTypeReconnectCommand(conn);
			cmd.setNewTarget(newTarget);
			} 
		catch (Exception e) 
			{
			}
		return cmd;
		}
	}

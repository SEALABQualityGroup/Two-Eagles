package pack.editPolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import pack.commands.ArcTypeCreateCommand;
import pack.commands.ArcTypeReconnectCommand;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;

public class NodeArcEditPolicy 
	extends GraphicalNodeEditPolicy 
	{

	private IQNM iqnm;
	
	public NodeArcEditPolicy(IQNM type) 
		{
		this.iqnm = type;
		}

	/* Returns the Command that will create the arc. 
	 * This is second part of creation. CreateConnectionRequest.getStartCommand() 
	 * is used here to obtain the contribution from the EditPart 
	 * from which the User started the creation.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) 
		{
		ArcTypeCreateCommand cmd	= (ArcTypeCreateCommand) request.getStartCommand();
		try {
			cmd.setTarget((INode)getHost().getModel());
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
		INode source = (INode)getHost().getModel();
		ArcTypeCreateCommand cmd = null;
		try {
			cmd = new ArcTypeCreateCommand(source,iqnm);
			} 
		catch (Exception e) 
			{}
		request.setStartCommand(cmd);
		return cmd;
		}
	
	/* Returns the Command to reconnect a connection's source end to the host.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) 
		{
		IArc conn = (IArc) request.getConnectionEditPart().getModel();
		INode newSource = (INode)getHost().getModel();
		ArcTypeReconnectCommand cmd = new ArcTypeReconnectCommand(conn);
		try {
			cmd.setNewSource(newSource);
			} 
		catch (Exception e) 
			{}
		return cmd;
		}
	
	/* Returns the Command to reconnect a connection's target end to the host.
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */	
	protected Command getReconnectTargetCommand(ReconnectRequest request) 
		{
		IArc conn = (IArc) request.getConnectionEditPart().getModel();
		INode newTarget = (INode) getHost().getModel();
		ArcTypeReconnectCommand cmd = new ArcTypeReconnectCommand(conn);
		try {
			cmd.setNewTarget(newTarget);
			} 
		catch (Exception e) 
			{}
		return cmd;
		}
	}

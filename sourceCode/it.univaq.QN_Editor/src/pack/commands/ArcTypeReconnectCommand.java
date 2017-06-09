package pack.commands;

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IArc;
import pack.model.INode;

public class ArcTypeReconnectCommand 
	extends Command 
	{

	/** The IArc instance to reconnect. */
	private IArc arc;

	/** The new source endpoint. */
	private INode newSource;
	
	/** The new target endpoint. */
	private INode newTarget;
	
	/** The original source endpoint. */
	private INode oldSource;
	
	/** The original target endpoint. */
	private INode oldTarget;
	
	/**
	 * Instantiate a command that can reconnect a IArc instance to a different source
	 * or target endpoint.
	 * @param conn the ArcType instance to reconnect (non-null)
	 */
	public ArcTypeReconnectCommand(IArc conn) 
		{
		try {
			if (conn == null) 
				{
				Finestra.mostraIE("arc is null");
				}
			this.arc = conn;
			this.oldSource = conn.getSource();
			this.oldTarget = conn.getTarget();
			}
		catch (Exception exception)
			{}
		}
	
	/**
	 * Set a new source endpoint for this IArc.
	 * When execute() is invoked, the source endpoint of the arc will be attached
	 * to the supplied INode instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i> endpoint.
	 * A single instance of this command can only reconnect either the source or the target 
	 * endpoint.
	 * </p>
	 * @param connectionSource a non-null INode instance, to be used as a new source endpoint
	 */
	public void setNewSource(INode connectionSource)
		throws Exception
		{
		if (connectionSource == null) 
			{
			Finestra.mostraIE("source is null");
			}
		setLabel("move arc startpoint");
		newSource = connectionSource;
		newTarget = null;
		}
	
	/**
	 * Set a new target endpoint for this IArc
	 * When execute() is invoked, the target endpoint of the arc will be attached
	 * to the supplied INode instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i> endpoint.
	 * A single instance of this command can only reconnect either the source or the target 
	 * endpoint.
	 * </p>
	 * @param connectionTarget a non-null INode instance, to be used as a new target endpoint
	 */
	public void setNewTarget(INode connectionTarget)
		throws Exception
		{
		if (connectionTarget == null) 
			{
			Finestra.mostraIE("target is null");
			}
		setLabel("move arc endpoint");
		newSource = null;
		newTarget = connectionTarget;
		}

	@Override
	public boolean canExecute() 
		{
		if (newSource != null) 
			{
			return checkSourceReconnection();
			} 
		else if (newTarget != null) 
			{
			return checkTargetReconnection();
			}
		return false;
		}

	private boolean checkTargetReconnection() 
		{
		// return false, if the arc exists already
		for (Iterator<IArc> iter = newTarget.getTargetArcs().iterator(); iter.hasNext();) 
			{
			IArc arc = iter.next();
			// return false if a oldSource -> newTarget arc exists already
			// and it is a differenct instance that the arc-field
			if (arc.getSource().equals(oldSource) && !arc.equals(this.arc)) 
				{
				return false;
				}
			}
		return true;
		}

	private boolean checkSourceReconnection() 
		{
		// return false, if the arc exists already
		for (Iterator<IArc> iter = newSource.getSourceArcs().iterator(); iter.hasNext();) 
			{
			IArc arc = iter.next();
			// return false if a newSource -> oldTarget transit exists already
			// and it is a different instance than the arc-field
			if (arc.getTarget().equals(oldTarget) &&  !arc.equals(this.arc)) 
				{
				return false;
				}
			}
		return true;
		}

	@Override
	public void execute() 
		{
		try {
			if (newSource != null) 
				arc.reconnect(newSource, oldTarget);
			else if (newTarget != null) 
				arc.reconnect(oldSource, newTarget);
			else 
				throw new IllegalStateException("Should not happen");
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void undo() 
		{
		try {
			arc.reconnect(oldSource, oldTarget);
			}
		catch (Exception exception)
			{}
		}
	}

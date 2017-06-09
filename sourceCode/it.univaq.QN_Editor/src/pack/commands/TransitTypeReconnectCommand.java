package pack.commands;

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IRequest;
import pack.model.ITransit;

public class TransitTypeReconnectCommand extends Command {

	/** The TransitType instance to reconnect. */
	private ITransit transit;
	
	/** The new source endpoint. */
	private IRequest newSource;
	
	/** The new target endpoint. */
	private IRequest newTarget;
	
	/** The original source endpoint. */
	private final IRequest oldSource;
	
	/** The original target endpoint. */
	private final IRequest oldTarget;
	
	/**
	 * Instantiate a command that can reconnect a TransitType instance to a different source
	 * or target endpoint.
	 * @param conn the TransitType instance to reconnect (non-null)
	 */
	public TransitTypeReconnectCommand(ITransit conn)
		throws Exception
		{
		if (conn == null) 
			{
			Finestra.mostraIE("transit is null");
			}
		this.transit = conn;
		this.oldSource = conn.getSource();
		this.oldTarget = conn.getTarget();
		}

	/**
	 * Set a new source endpoint for this TransitType.
	 * When execute() is invoked, the source endpoint of the transit will be attached
	 * to the supplied IRequest instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>target</i> endpoint.
	 * A single instance of this command can only reconnect either the source or the target 
	 * endpoint.
	 * </p>
	 * @param connectionSource a non-null IRequest instance, to be used as a new source endpoint
	 */
	public void setNewSource(IRequest connectionSource)
		throws Exception
		{
		if (connectionSource == null) 
			{
			Finestra.mostraIE("source is null");
			}
		setLabel("move transit startpoint");
		newSource = connectionSource;
		newTarget = null;
		}
	
	/**
	 * Set a new target endpoint for this TransitType
	 * When execute() is invoked, the target endpoint of the transit will be attached
	 * to the supplied IRequest instance.
	 * <p>
	 * Note: Calling this method, deactivates reconnection of the <i>source</i> endpoint.
	 * A single instance of this command can only reconnect either the source or the target 
	 * endpoint.
	 * </p>
	 * @param connectionTarget a non-null IRequest instance, to be used as a new target endpoint
	 */
	public void setNewTarget(IRequest connectionTarget)
		throws Exception
		{
		if (connectionTarget == null) 
			{
			Finestra.mostraIE("target is null");
			}
		setLabel("move transit endpoint");
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
		// return false, if the transit exists already
		for (Iterator<ITransit> iter = newTarget.getTargetTransits().iterator(); iter.hasNext();) 
			{
			ITransit transit = iter.next();
			// return false if a oldSource -> newTarget transit exists already
			// and it is a differenct instance that the transit-field
			if (transit.getSource().equals(oldSource) && !transit.equals(this.transit)) 
				{
				return false;
				}
			}
		return true;
		}

	private boolean checkSourceReconnection() 
		{
		// return false, if the transit exists already
		for (Iterator<ITransit> iter = newSource.getSourceTransits().iterator(); iter.hasNext();) 
			{
			ITransit transit = iter.next();
			// return false if a newSource -> oldTarget transit exists already
			// and it is a different instance than the transit-field
			if (transit.getTarget().equals(oldTarget) &&  !transit.equals(this.transit)) 
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
				transit.reconnect(newSource, oldTarget);
			else if (newTarget != null) 
				transit.reconnect(oldSource, newTarget);
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
			transit.reconnect(oldSource, oldTarget);
			} 
		catch (Exception e) 
			{}
		}
	}

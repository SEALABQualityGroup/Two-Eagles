package pack.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IQNM;
import pack.model.IRequest;
import pack.model.ITransit;

public class RequestDeleteCommand 
	extends Command 
	{

	private IQNM iqnm;
	
	/** IRequest to remove. */
	private final IRequest child;

	/** Holds a copy of the outgoing transits of child. */
	private List<ITransit> sourceTransits;
	
	/** Holds a copy of the incoming transits of child. */
	private List<ITransit> targetTransits;
	
	/** True, if child was removed from its parent. */
	private boolean wasRemoved;
	
	/**
	 * Create a command that will remove the request from its parent.
	 * @param parent the IWorkload containing the child
	 * @param child    the IRequest to remove
	 */
	public RequestDeleteCommand(IQNM iqnm, IRequest child)
		throws Exception
		{
		if (child == null) 
			{
			Finestra.mostraIE("child is null");
			}
		setLabel("request deletion");
		this.iqnm = iqnm;
		this.child = child;
		}

	@Override
	public void execute() 
		{
		// store a copy of incoming & outgoing connections before proceeding
		// i transit sorgenti e destinazione vanno clonati perchè altrimenti non saranno più
		// raggiungibili dopo la loro rimozione
		try {
			sourceTransits = child.getSourceTransitsCloned();
			targetTransits = child.getTargetTransitsCloned();
			redo();
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void redo() 
		{
		// remove the child and disconnect its connections
		wasRemoved = this.iqnm.removeChild(child);
		if (wasRemoved) 
			{
			removeTransits(sourceTransits);
			removeTransits(targetTransits);
			}
		}

	private void removeTransits(List<ITransit> sourceTransits2) 
		{
		for (Iterator<ITransit> iter = sourceTransits2.iterator(); iter.hasNext();) 
			{
			ITransit transit = iter.next();
			transit.disconnect();
			}
		}

	@Override
	public void undo() 
		{
		// add the child and reconnect its transit
		if (iqnm.addChild(child)) 
			{
			addTransits(sourceTransits);
			addTransits(targetTransits);
			}
		}

	private void addTransits(List<ITransit> sourceTransits2) 
		{
		for (Iterator<ITransit> iter = sourceTransits2.iterator(); iter.hasNext();) 
			{
			ITransit transit = iter.next();
			transit.reconnect();
			}
		}

	@Override
	public boolean canUndo() 
		{
		return wasRemoved;
		}
	}

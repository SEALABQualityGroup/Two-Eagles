package pack.commands;

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IRequest;
import pack.model.ITransit;
import pack.model.structure.TransitModel;

public class TransitTypeCreateCommand 
	extends Command 
	{
	
	/** Start endpoint for the transit. */
	private final IRequest source;
	
	/** Target endpoint for the TransitType. */
	private IRequest target;
	
	private ITransit transit;
	
	public TransitTypeCreateCommand(IRequest source)
		throws Exception
		{
		if (source == null) 
			{
			Finestra.mostraIE("source is null");
			}
		setLabel("transit creation");
		this.source = source;
		}

	/**
	 * Set the target endpoint for the TransitType.
	 * @param target that target endpoint (a non-null IRequest instance)
	 */
	public void setTarget(IRequest target)
		throws Exception
		{
		if (target == null) 
			{
			Finestra.mostraIE("target is null");
			}
		this.target = target;
		}

	@Override
	public boolean canExecute() 
		{
		// return false, if the source -> target transit exists already
		for (Iterator<ITransit> iter = source.getSourceTransits().iterator(); iter.hasNext();) 
			{
			ITransit conn = iter.next();
			if (conn.getTarget().equals(target)) 
				return false;
			}
		return true;
		}

	@Override
	public void execute() 
		{
		// create a new transit between source and target
		try {
			transit = new TransitModel(source,target);
			} 
		catch (Exception e) 
			{}
		}

	@Override
	public void redo() 
		{
		transit.reconnect();
		}

	@Override
	public void undo() 
		{
		transit.disconnect();
		}
	}

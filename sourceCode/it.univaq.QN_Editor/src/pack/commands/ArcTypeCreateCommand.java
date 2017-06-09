package pack.commands;

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IArc;
import pack.model.INode;
import pack.model.IQNM;
import pack.model.structure.ArcModel;

public class ArcTypeCreateCommand 
	extends Command 
	{
	
	/** Start endpoint for the arc. */
	private final INode source;

	/** Target endpoint for the ArcType. */
	private INode target;
	
	/** The connection instance. */
	private IArc arc;
	
	private IQNM iqnm;
	
	public ArcTypeCreateCommand(INode source, IQNM iqnm)
		throws Exception
		{
		if (source == null) 
			{
			Finestra.mostraIE("source is null");
			}
		setLabel("arc creation");
		this.source = source;
		this.iqnm = iqnm;
		}

	/**
	 * Set the target endpoint for the ArcType.
	 * @param target that target endpoint (a non-null INode instance)
	 */
	public void setTarget(INode target)
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
		// return false, if the source -> target arc exists already
		for (Iterator<IArc> iter = source.getSourceArcs().iterator(); iter.hasNext();) 
			{
			IArc conn = iter.next();
			if (conn.getTarget().equals(target)) 
				return false;
			}
		return true;
		}

	@Override
	public void execute() 
		{
		// create a new arc between source and target
		try {
			arc = new ArcModel(source,target,this.iqnm);
			} 
		catch (Exception e) 
			{}
		}

	@Override
	public void redo() 
		{
		arc.reconnect();
		}

	@Override
	public void undo() 
		{
		arc.disconnect();
		}
	}

package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.IArc;

public class ArcTypeDeleteCommand extends Command 
	{

	/** Arco instance to disconnect. */
	private IArc arc;

	public ArcTypeDeleteCommand(IArc castedModel)
		throws Exception
		{
		if (castedModel == null) 
			{
			Finestra.mostraIE("arc is null");
			}
		setLabel("arc deletion");
		this.arc = castedModel;
		}

	@Override
	public void execute() 
		{
		arc.disconnect();
		}

	@Override
	public void undo() 
		{
		arc.reconnect();
		}
	}	

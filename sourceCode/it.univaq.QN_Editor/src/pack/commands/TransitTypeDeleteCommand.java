package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.errorManagement.Finestra;
import pack.model.ITransit;

public class TransitTypeDeleteCommand 
	extends Command 
	{

	private ITransit transit;
	
	public TransitTypeDeleteCommand(ITransit castedModel)
		throws Exception
		{
		if (castedModel == null) 
			{
			Finestra.mostraIE("transit is null");
			}
		setLabel("transit deletion");
		this.transit = castedModel;
		}

	@Override
	public void execute() 
		{
		transit.disconnect();
		}

	@Override
	public void undo() 
		{
		transit.reconnect();
		}
	}

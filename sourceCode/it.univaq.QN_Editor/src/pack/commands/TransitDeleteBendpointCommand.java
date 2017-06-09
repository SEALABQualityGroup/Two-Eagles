package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.ITransit;
import pack.model.TransitTypeBendpoint;

public class TransitDeleteBendpointCommand extends Command 
	{
	private ITransit transit;
	
	private int index;
	
	private TransitTypeBendpoint bendpoint;
	
	public void setTransit(ITransit model) 
		{
		this.transit = model;
		}

	public void setIndex(int index) 
		{
		this.index = index;
		}
	
	public void execute() 
		{
		bendpoint = transit.getBendpoints().get(index);
		transit.removeBendpoint(index);
		super.execute();
		}

	public void undo() 
		{
		super.undo();
		transit.insertBendpoint(index, bendpoint);
		}
	}

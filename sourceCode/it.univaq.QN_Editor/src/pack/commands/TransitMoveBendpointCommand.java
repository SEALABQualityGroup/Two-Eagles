package pack.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import pack.model.ITransit;
import pack.model.TransitTypeBendpoint;

public class TransitMoveBendpointCommand extends Command {

	private Dimension d1, d2;
	
	private ITransit transit;
	
	private int index;
	
	private TransitTypeBendpoint oldBendpoint;
	
	public void setRelativeDimensions(Dimension difference,
			Dimension difference2) 
		{
		this.d1 = difference;
		this.d2 = difference2;
		}

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
		TransitTypeBendpoint bendpoint = new TransitTypeBendpoint();
		bendpoint.setRelativeDimensions(d1,d2);
		setOldBendpoint(transit.getBendpoints().get(index));
		transit.setBendpoint(index, bendpoint);
		super.execute();
		}

	private void setOldBendpoint(TransitTypeBendpoint bendpoint) 
		{
		this.oldBendpoint = bendpoint;
		}

	public void undo() 
		{
		super.undo();
		transit.setBendpoint(index, oldBendpoint);
		}
	}

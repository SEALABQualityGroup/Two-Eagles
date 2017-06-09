package pack.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import pack.model.ITransit;
import pack.model.TransitTypeBendpoint;

public class TransitCreateBendpointCommand extends Command {

	private Dimension d1, d2;
	
	private ITransit transit;
	
	private int index;
	
	public void setRelativeDimensions(Dimension difference,
			Dimension difference2) 
		{
		d1 = difference;
		d2 = difference2;
		}

	public void setTransit(ITransit transit) 
		{
		this.transit = transit;
		}

	public void setIndex(int index) 
		{
		this.index = index;
		}
	
	public void redo() 
		{
		execute();
		}

	public void execute() 
		{
		TransitTypeBendpoint bendpoint = new TransitTypeBendpoint();
		bendpoint.setRelativeDimensions(getFirstRelativeDimension(), 
					getSecondRelativeDimension());
		transit.insertBendpoint(index, bendpoint);
		super.execute();
		}

	private Dimension getSecondRelativeDimension() 
		{
		return d2;
		}

	private Dimension getFirstRelativeDimension() 
		{
		return d1;
		}

	public void undo() 
		{
		super.undo();
		transit.removeBendpoint(index);
		}
	}

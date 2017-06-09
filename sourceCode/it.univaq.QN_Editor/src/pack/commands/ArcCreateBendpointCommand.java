package pack.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;
import pack.model.ArcTypeBendpoint;
import pack.model.IArc;


public class ArcCreateBendpointCommand extends Command {
	
	private Dimension d1, d2;
	
	private IArc arc;
	
	private int index;

	public void setRelativeDimensions(Dimension difference,
			Dimension difference2) 
		{
		d1 = difference;
		d2 = difference2;
		}

	public void setArc(IArc model) 
		{
		arc = model;
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
		ArcTypeBendpoint bendpoint = new ArcTypeBendpoint();
		bendpoint.setRelativeDimensions(getFirstRelativeDimension(), 
						getSecondRelativeDimension());
		getArc().insertBendpoint(getIndex(), bendpoint);
		super.execute();
		}

	public void undo() 
		{
		super.undo();
		getArc().removeBendpoint(getIndex());
		}
	
	private Dimension getFirstRelativeDimension() 
		{
		return d1;
		}

	private Dimension getSecondRelativeDimension() 
		{
		return d2;
		}
	
	public IArc getArc() 
		{
		return arc;
		}
	
	public int getIndex() 
		{
		return index;
		}
	}

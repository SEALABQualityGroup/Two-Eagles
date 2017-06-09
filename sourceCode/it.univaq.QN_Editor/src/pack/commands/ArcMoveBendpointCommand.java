package pack.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;

import pack.model.ArcTypeBendpoint;
import pack.model.IArc;

public class ArcMoveBendpointCommand extends Command {

	private Dimension d1,d2;
	
	private IArc arc;
	
	private int index;
	
	private ArcTypeBendpoint oldBendpoint;
	
	public void setRelativeDimensions(Dimension difference,
			Dimension difference2) 
		{
		d1 = difference;
		d2 = difference2;
		}

	public void setArc(IArc model) 
		{
		this.arc = model;
		}

	public void setIndex(int index) 
		{
		this.index = index;
		}
	
	public void execute() 
		{
		ArcTypeBendpoint bendpoint = new ArcTypeBendpoint();
		bendpoint.setRelativeDimensions(d1,d2);
		setOldBendpoint(arc.getBendpoints().get(index));
		arc.setBendpoint(index, bendpoint);
		super.execute();
		}

	private void setOldBendpoint(ArcTypeBendpoint bendpoint) 
		{
		this.oldBendpoint = bendpoint;
		}
	
	public void undo() 
		{
		super.undo();
		arc.setBendpoint(index, oldBendpoint);
		}
	}

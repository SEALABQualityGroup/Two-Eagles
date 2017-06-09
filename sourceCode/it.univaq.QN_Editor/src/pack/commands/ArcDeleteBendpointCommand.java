package pack.commands;

import org.eclipse.gef.commands.Command;

import pack.model.ArcTypeBendpoint;
import pack.model.IArc;

public class ArcDeleteBendpointCommand extends Command {

	private IArc arc;
	
	private int index;
	
	private ArcTypeBendpoint bendpoint;
	
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
		bendpoint = arc.getBendpoints().get(index);
		arc.removeBendpoint(index);
		super.execute();
		}

	public void undo() 
		{
		super.undo();
		arc.insertBendpoint(index, bendpoint);
		}
	}

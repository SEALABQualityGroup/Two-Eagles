package pack.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import pack.model.IQNM;
import pack.model.IRequest;

public class RequestCreateCommand 
	extends Command 
	{

	private IRequest newRequest;
	
	private IQNM parent;
	
	private Rectangle bounds;
	
	/**
	 * Create a command that will add a new IRequest to a IWorkload.
	 * 
	 * @param newRequest the new IRequest that is to be added
	 * @param parent the IWorkload that will hold the new element
	 * @param bounds the bounds of the new request; the size can be (-1, -1) if not known
	 */
	public RequestCreateCommand(IRequest newRequest, Rectangle bounds) 
		{
		this.newRequest = newRequest;
		this.parent = newRequest.getQNM();
		this.bounds = bounds;
		setLabel("request creation");
		}

	@Override
	public void execute() 
		{
		try {
			newRequest.setLocation(bounds.getLocation());
			redo();
			}
		catch (Exception exception)
			{}
		}

	@Override
	public void redo() 
		{
		parent.addChild(newRequest);
		}

	@Override
	public void undo() 
		{
		parent.removeChild(newRequest);
		}

	@Override
	public boolean canExecute() 
		{
		return newRequest != null && parent != null && bounds != null;
		}
	}

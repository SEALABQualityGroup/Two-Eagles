package pack.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import pack.errorManagement.Finestra;
import pack.model.IRequest;

public class RequestSetConstraintCommand 
	extends Command 
	{
	
	private IRequest request;
	
	private ChangeBoundsRequest changeBoundsRequest;
	
	private Rectangle newBounds;
	
	/** Stores the old location. */
	private Rectangle oldBounds;
	
	/**
	 * Create a command that can move a IRequest. 
	 * @param IRequest	the IRequest to manipulate
	 * @param req		the move request
	 * @param newBounds the new location
	 */
	public RequestSetConstraintCommand(IRequest request, ChangeBoundsRequest req,
			Rectangle newBounds) 
		throws Exception
		{
		if (request == null) 
			{
			Finestra.mostraIE("request is null");
			}
		if (req == null)
			{
			Finestra.mostraIE("request is null");
			}
		if (newBounds == null)
			{
			Finestra.mostraIE("bounds is null");
			}
		this.request = request;
		this.changeBoundsRequest = req;
		this.newBounds = newBounds.getCopy();
		setLabel("move");
		}

	@Override
	public boolean canExecute() 
		{
		Object type = changeBoundsRequest.getType();
		// make sure the Request is of a type we support:
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type));
		}

	@Override
	public void execute() 
		{
		oldBounds = new Rectangle(request.getLocation(), request.getEtichetta().getSize());
		redo();
		}

	@Override
	public void redo() 
		{
		try {
			request.setLocation(newBounds.getLocation());
			} 
		catch (Exception e) 
			{}
		}

	@Override
	public void undo() 
		{
		try {
			request.setLocation(oldBounds.getLocation());
			} 
		catch (Exception e) 
			{}
		}
	}

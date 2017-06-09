package pack.editPolicies;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import pack.commands.TransitCreateBendpointCommand;
import pack.commands.TransitDeleteBendpointCommand;
import pack.commands.TransitMoveBendpointCommand;
import pack.model.ITransit;

public class TransitBendpointEditPolicy extends BendpointEditPolicy {

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) 
		{
		TransitCreateBendpointCommand com = new TransitCreateBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();
		
		conn.translateToRelative(p);
		
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();
		
		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);
		
		
		com.setRelativeDimensions(p.getDifference(ref1),
						p.getDifference(ref2));
		com.setTransit((ITransit)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}

	@Override
	protected Command getDeleteBendpointCommand(BendpointRequest request) 
		{
		TransitDeleteBendpointCommand com = new TransitDeleteBendpointCommand();
		com.setTransit((ITransit)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}

	@Override
	protected Command getMoveBendpointCommand(BendpointRequest request) 
		{
		TransitMoveBendpointCommand com = new TransitMoveBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();
		
		conn.translateToRelative(p);
		
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();
		
		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);
		
		com.setRelativeDimensions(p.getDifference(ref1),
						p.getDifference(ref2));
		com.setTransit((ITransit)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}
	}

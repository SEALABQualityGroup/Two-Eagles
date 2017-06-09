package pack.editPolicies;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import pack.commands.ArcCreateBendpointCommand;
import pack.commands.ArcDeleteBendpointCommand;
import pack.commands.ArcMoveBendpointCommand;
import pack.model.IArc;

public class ArcBendpointEditPolicy extends BendpointEditPolicy {

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) 
		{
		ArcCreateBendpointCommand com = new ArcCreateBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();
		
		conn.translateToRelative(p);
		
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();
		
		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);
		
		
		com.setRelativeDimensions(p.getDifference(ref1),
						p.getDifference(ref2));
		com.setArc((IArc)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}

	@Override
	protected Command getDeleteBendpointCommand(BendpointRequest request) 
		{
		ArcDeleteBendpointCommand com = new ArcDeleteBendpointCommand();
		com.setArc((IArc)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}

	@Override
	protected Command getMoveBendpointCommand(BendpointRequest request) 
		{
		ArcMoveBendpointCommand com = new ArcMoveBendpointCommand();
		Point p = request.getLocation();
		Connection conn = getConnection();
		
		conn.translateToRelative(p);
		
		Point ref1 = getConnection().getSourceAnchor().getReferencePoint();
		Point ref2 = getConnection().getTargetAnchor().getReferencePoint();
		
		conn.translateToRelative(ref1);
		conn.translateToRelative(ref2);
		
		com.setRelativeDimensions(p.getDifference(ref1),
						p.getDifference(ref2));
		com.setArc((IArc)request.getSource().getModel());
		com.setIndex(request.getIndex());
		return com;
		}
	}

package pack.model;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class TypeBendpoint 
	implements java.io.Serializable, Bendpoint
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension d1;
	private Dimension d2;

	public TypeBendpoint() 
		{
		super();
		}

	@Override
	public Point getLocation() 
		{
		return null;
		}

	public void setRelativeDimensions(Dimension dim1, Dimension dim2) 
		{
		d1 = dim1;
		d2 = dim2;
		}

	public Dimension getFirstRelativeDimension() 
		{
		return d1;
		}

	public Dimension getSecondRelativeDimension() 
		{
		return d2;
		}
	}
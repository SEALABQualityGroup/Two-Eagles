package pack.model;

public class TransitTypeBendpoint extends TypeBendpoint implements Cloneable
	{

	private static final long serialVersionUID = 1L;

	@Override
	public Object clone() throws CloneNotSupportedException 
		{
		TransitTypeBendpoint transitTypeBendpoint = new TransitTypeBendpoint();
		transitTypeBendpoint.setRelativeDimensions(this.getFirstRelativeDimension().getCopy(), 
				this.getSecondRelativeDimension().getCopy());
		return transitTypeBendpoint;
		}
	}

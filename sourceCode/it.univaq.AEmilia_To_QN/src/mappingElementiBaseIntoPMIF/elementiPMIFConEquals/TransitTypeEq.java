package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.TransitType;

public class TransitTypeEq extends TransitType {

	private static final long serialVersionUID = 1L;

	public TransitTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public TransitTypeEq()
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof TransitType))
			return false;
		TransitType transitType = (TransitType)obj;
		if (!this.hasTo() && transitType.hasTo())
			return false;
		if (this.hasTo() && !transitType.hasTo())
			return false;
		if (this.hasTo() && transitType.hasTo())
			{
			try {
				if (!this.getTo().asString().equals(transitType.getTo().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasProbability() && transitType.hasProbability())
			return false;
		if (this.hasProbability() && !transitType.hasProbability())
			return false;
		if (this.hasProbability() && transitType.hasProbability())
			{
			try {
				if (!this.getProbability().asString().equals(transitType.getProbability().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		return true;
		}
}

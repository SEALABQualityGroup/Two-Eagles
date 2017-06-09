package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;
import com.schema.TimeServType;

public class TimeServTypeEq extends TimeServType {

	private static final long serialVersionUID = 1L;

	public TimeServTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public TimeServTypeEq()
		{
		super();
		}

	public TransitTypeEq getTransitAt(int index) throws Exception
		{
		return new TransitTypeEq(getDomChildAt(Element, null, "Transit", index));
		}

	public TransitTypeEq getTransit() throws Exception
		{
		return getTransitAt(0);
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof TimeServType))
			return false;
		TimeServType timeServType = (TimeServType)obj;
		if (!this.hasTransit() && timeServType.hasTransit())
			return false;
		if (this.hasTransit() && !timeServType.hasTransit())
			return false;
		if (this.hasTransit() && timeServType.hasTransit())
			{
			if (this.getTransitCount() != timeServType.getTransitCount())
				return false;
			for (int i = 0; i < this.getTransitCount(); i++)
				{
				try {
					if (!this.getTransitAt(i).equals(timeServType.getTransitAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (this.hasWorkloadName() && !timeServType.hasWorkloadName())
			return false;
		if (!this.hasWorkloadName() && timeServType.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && timeServType.hasWorkloadName())
			{
			try {
				if (!this.getWorkloadName().asString().equals(timeServType.getWorkloadName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServerID() && timeServType.hasServerID())
			return false;
		if (this.hasServerID() && !timeServType.hasServerID())
			return false;
		if (this.hasServerID() && timeServType.hasServerID())
			{
			try {
				if (!this.getServerID().asString().equals(timeServType.getServerID().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasTimeUnits() && timeServType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && !timeServType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && timeServType.hasTimeUnits())
			{
			try {
				if (!this.getTimeUnits().asString().equals(timeServType.getTimeUnits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServiceTime() && timeServType.hasServiceTime())
			return false;
		if (this.hasServiceTime() && !timeServType.hasServiceTime())
			return false;
		if (this.hasServiceTime() && timeServType.hasServiceTime())
			{
			try {
				if (!this.getServiceTime().asString().equals(timeServType.getServiceTime().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasNumberOfVisits() && timeServType.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && !timeServType.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && timeServType.hasNumberOfVisits())
			{
			try {
				if (!this.getNumberOfVisits().asString().equals(timeServType.getNumberOfVisits().asString()))
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

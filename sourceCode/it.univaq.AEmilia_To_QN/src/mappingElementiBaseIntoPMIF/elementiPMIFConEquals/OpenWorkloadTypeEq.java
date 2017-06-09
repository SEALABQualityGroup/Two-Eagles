package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;
import com.schema.OpenWorkloadType;

public class OpenWorkloadTypeEq extends OpenWorkloadType {

	private static final long serialVersionUID = 1L;

	public OpenWorkloadTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public OpenWorkloadTypeEq()
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
		if (!(obj instanceof OpenWorkloadType))
			return false;
		OpenWorkloadType openWorkloadType = (OpenWorkloadType)obj;
		if (!this.hasTransit() && openWorkloadType.hasTransit())
			return false;
		if (this.hasTransit() && !openWorkloadType.hasTransit())
			return false;
		if (this.hasTransit() && openWorkloadType.hasTransit())
			{
			if (this.getTransitCount() != openWorkloadType.getTransitCount())
				return false;
			for (int i =0; i < this.getTransitCount(); i++)
				{
				try {
					if (!this.getTransitAt(i).equals(openWorkloadType.getTransitAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkloadName() && openWorkloadType.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && !openWorkloadType.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && openWorkloadType.hasWorkloadName())
			{
			try {
				if (!this.getWorkloadName().asString().equals(openWorkloadType.getWorkloadName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasArrivalRate() && openWorkloadType.hasArrivalRate())
			return false;
		if (this.hasArrivalRate() && !openWorkloadType.hasArrivalRate())
			return false;
		if (this.hasArrivalRate() && openWorkloadType.hasArrivalRate())
			{
			try {
				if (!this.getArrivalRate().asString().equals(openWorkloadType.getArrivalRate().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasTimeUnits() && openWorkloadType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && !openWorkloadType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && openWorkloadType.hasTimeUnits())
			{
			try {
				if (!this.getTimeUnits().asString().equals(openWorkloadType.getTimeUnits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasArrivesAt() && openWorkloadType.hasArrivesAt())
			return false;
		if (this.hasArrivesAt() && !openWorkloadType.hasArrivesAt())
			return false;
		if (this.hasArrivesAt() && openWorkloadType.hasArrivesAt())
			{
			try {
				if (!this.getArrivesAt().asString().equals(openWorkloadType.getArrivesAt().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasDepartsAt() && openWorkloadType.hasDepartsAt())
			return false;
		if (this.hasDepartsAt() && !openWorkloadType.hasDepartsAt())
			return false;
		if (this.hasDepartsAt() && openWorkloadType.hasDepartsAt())
			{
			try {
				if (!this.getDepartsAt().asString().equals(openWorkloadType.getDepartsAt().asString()))
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
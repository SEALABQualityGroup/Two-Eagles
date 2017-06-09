package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.ClosedWorkloadType;

public class ClosedWorkloadTypeEq extends ClosedWorkloadType {

	private static final long serialVersionUID = 1L;

	public ClosedWorkloadTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public ClosedWorkloadTypeEq()
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
		if (!(obj instanceof ClosedWorkloadType))
			return false;
		ClosedWorkloadType closedWorkloadTypeEq = (ClosedWorkloadType)obj;
		if (!this.hasTransit() && closedWorkloadTypeEq.hasTransit())
			return false;
		if (this.hasTransit() && !closedWorkloadTypeEq.hasTransit())
			return false;
		if (this.hasTransit() && closedWorkloadTypeEq.hasTransit())
			{
			if (this.getTransitCount() != closedWorkloadTypeEq.getTransitCount())
				return false;
			for (int i = 0; i < this.getTransitCount(); i++)
				{
				try {
					if (!this.getTransitAt(i).equals(closedWorkloadTypeEq.getTransitAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkloadName() && closedWorkloadTypeEq.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && !closedWorkloadTypeEq.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && closedWorkloadTypeEq.hasWorkloadName())
			{
			try {
				if (!this.getWorkloadName().asString().equals(closedWorkloadTypeEq.getWorkloadName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasNumberOfJobs() && closedWorkloadTypeEq.hasNumberOfJobs())
			return false;
		if (this.hasNumberOfJobs() && !closedWorkloadTypeEq.hasNumberOfJobs())
			return false;
		if (this.hasNumberOfJobs() && closedWorkloadTypeEq.hasNumberOfJobs())
			{
			try {
				if (!this.getNumberOfJobs().asString().equals(closedWorkloadTypeEq.getNumberOfJobs().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasThinkTime() && closedWorkloadTypeEq.hasThinkTime())
			return false;
		if (this.hasThinkTime() && !closedWorkloadTypeEq.hasThinkTime())
			return false;
		if (this.hasThinkTime() && closedWorkloadTypeEq.hasThinkTime())
			{
			try {
				if (!this.getThinkTime().asString().equals(closedWorkloadTypeEq.getThinkTime().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasTimeUnits() && closedWorkloadTypeEq.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && !closedWorkloadTypeEq.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && closedWorkloadTypeEq.hasTimeUnits())
			{
			try {
				if (!this.getTimeUnits().asString().equals(closedWorkloadTypeEq.getTimeUnits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasThinkDevice() && closedWorkloadTypeEq.hasThinkDevice())
			return false;
		if (this.hasThinkDevice() && !closedWorkloadTypeEq.hasThinkDevice())
			return false;
		if (this.hasThinkDevice() && closedWorkloadTypeEq.hasThinkDevice())
			{
			try {
				if (!this.getThinkDevice().asString().equals(closedWorkloadTypeEq.getThinkDevice().asString()))
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

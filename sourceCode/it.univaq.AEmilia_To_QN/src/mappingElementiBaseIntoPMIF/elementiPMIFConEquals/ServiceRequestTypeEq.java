package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.ServiceRequestType;

public class ServiceRequestTypeEq extends ServiceRequestType {

	private static final long serialVersionUID = 1L;

	public ServiceRequestTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public ServiceRequestTypeEq()
		{
		super();
		}

	public TimeServTypeEq getTimeServiceRequestAt(int index) throws Exception
		{
		return new TimeServTypeEq(getDomChildAt(Element, null, "TimeServiceRequest", index));
		}

	public TimeServTypeEq getTimeServiceRequest() throws Exception
		{
		return getTimeServiceRequestAt(0);
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof ServiceRequestType))
			return false;
		ServiceRequestType serviceRequestType = (ServiceRequestType)obj;
		if (!this.hasTimeServiceRequest() && serviceRequestType.hasTimeServiceRequest())
			return false;
		if (this.hasTimeServiceRequest() && !serviceRequestType.hasTimeServiceRequest())
			return false;
		if (this.hasTimeServiceRequest() && serviceRequestType.hasTimeServiceRequest())
			{
			if (this.getTimeServiceRequestCount() != serviceRequestType.getTimeServiceRequestCount())
				return false;
			for (int i = 0; i < this.getTimeServiceRequestCount(); i++)
				{
				try {
					if (!this.getTimeServiceRequestAt(i).equals(serviceRequestType.getTimeServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasDemandServiceRequest() && serviceRequestType.hasDemandServiceRequest())
			return false;
		if (this.hasDemandServiceRequest() && !serviceRequestType.hasDemandServiceRequest())
			return false;
		if (this.hasDemandServiceRequest() && serviceRequestType.hasDemandServiceRequest())
			{
			for (int i = 0; i < this.getDemandServiceRequestCount(); i++)
				{
				try {
					if (!this.getDemandServiceRequestAt(i).equals(serviceRequestType.getDemandServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkUnitServiceRequest() && serviceRequestType.hasWorkUnitServiceRequest())
			return false;
		if (this.hasWorkUnitServiceRequest() && !serviceRequestType.hasWorkUnitServiceRequest())
			return false;
		if (this.hasWorkUnitServiceRequest() && serviceRequestType.hasWorkUnitServiceRequest())
			{
			if (this.getWorkUnitServiceRequestCount() != serviceRequestType.getWorkUnitServiceRequestCount())
				return false;
			for (int i = 0; i < this.getWorkUnitServiceRequestCount(); i++)
				{
				try {
					if (!this.getWorkUnitServiceRequestAt(i).equals(serviceRequestType.getWorkUnitServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		return true;
		}

	@Override
	public DemandServTypeEq getDemandServiceRequest() 
		throws Exception 
		{
		return getDemandServiceRequestAt(0);
		}

	@Override
	public DemandServTypeEq getDemandServiceRequestAt(int index) 
		throws Exception 
		{
		return new DemandServTypeEq(getDomChildAt(Element, null, "DemandServiceRequest", index));
		}
	}

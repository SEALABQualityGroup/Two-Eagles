package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.DemandServType;

public class DemandServTypeEq extends DemandServType {

	private static final long serialVersionUID = 1L;

	public DemandServTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public DemandServTypeEq() 
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof DemandServType))
			return false;
		DemandServType demandServTypeEq = (DemandServType)obj;
		if (!this.hasTransit() && demandServTypeEq.hasTransit())
			return false;
		if (this.hasTransit() && !demandServTypeEq.hasTransit())
			return false;
		if (this.hasTransit() && demandServTypeEq.hasTransit())
			{
			if (this.getTransitCount() != demandServTypeEq.getTransitCount())
				return false;
			for (int i = 0; i < this.getTransitCount(); i++)
				{
				try {
					if (!this.getTransitAt(i).equals(demandServTypeEq.getTransitAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkloadName() && demandServTypeEq.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && !demandServTypeEq.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && demandServTypeEq.hasWorkloadName())
			{
			try {
				if (!this.getWorkloadName().asString().equals(demandServTypeEq.getWorkloadName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServerID() && demandServTypeEq.hasServerID())
			return false;
		if (this.hasServerID() && !demandServTypeEq.hasServerID())
			return false;
		if (this.hasServerID() && demandServTypeEq.hasServerID())
			{
			try {
				if (!this.getServerID().asString().equals(demandServTypeEq.getServerID().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasTimeUnits() && demandServTypeEq.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && !demandServTypeEq.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && demandServTypeEq.hasTimeUnits())
			{
			try {
				if (!this.getTimeUnits().asString().equals(demandServTypeEq.getTimeUnits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServiceDemand() && demandServTypeEq.hasServiceDemand())
			return false;
		if (this.hasServiceDemand() && !demandServTypeEq.hasServiceDemand())
			return false;
		if (this.hasServiceDemand() && demandServTypeEq.hasServiceDemand())
			{
			try {
				if (!this.getServiceDemand().asString().equals(demandServTypeEq.getServiceDemand().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasNumberOfVisits() && demandServTypeEq.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && !demandServTypeEq.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && demandServTypeEq.hasNumberOfVisits())
			{
			try {
				if (!this.getNumberOfVisits().asString().equals(demandServTypeEq.getNumberOfVisits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		return true;
		}
	
	public TransitTypeEq getTransitAt(int index) throws Exception
		{
		return new TransitTypeEq(getDomChildAt(Element, null, "Transit", index));
		}

	public TransitTypeEq getTransit() throws Exception
		{
		return getTransitAt(0);
		}
	}

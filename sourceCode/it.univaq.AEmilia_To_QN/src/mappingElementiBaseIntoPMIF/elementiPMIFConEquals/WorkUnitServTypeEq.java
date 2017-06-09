package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import com.schema.WorkUnitServType;

public class WorkUnitServTypeEq extends WorkUnitServType {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof WorkUnitServType))
			return false;
		WorkUnitServType workUnitServType = (WorkUnitServType)obj;
		if (!this.hasTransit() && workUnitServType.hasTransit())
			return false;
		if (this.hasTransit() && !workUnitServType.hasTransit())
			return false;
		if (this.hasTransit() && workUnitServType.hasTransit())
			{
			if (this.getTransitCount() != workUnitServType.getTransitCount())
				return false;
			for (int i = 0; i < workUnitServType.getTransitCount(); i++)
				{
				try {
					if (!this.getTransitAt(i).equals(workUnitServType.getTransitAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkloadName() && workUnitServType.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && !workUnitServType.hasWorkloadName())
			return false;
		if (this.hasWorkloadName() && workUnitServType.hasWorkloadName())
			{
			try {
				if (!this.getWorkloadName().asString().equals(workUnitServType.getWorkloadName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServerID() && workUnitServType.hasServerID())
			return false;
		if (this.hasServerID() && !workUnitServType.hasServerID())
			return false;
		if (this.hasServerID() && workUnitServType.hasServerID())
			{
			try {
				if (!this.getServerID().asString().equals(workUnitServType.getServerID().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasNumberOfVisits() && workUnitServType.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && !workUnitServType.hasNumberOfVisits())
			return false;
		if (this.hasNumberOfVisits() && workUnitServType.hasNumberOfVisits())
			{
			try {
				if (!this.getNumberOfVisits().asString().equals(workUnitServType.getNumberOfVisits().asString()))
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

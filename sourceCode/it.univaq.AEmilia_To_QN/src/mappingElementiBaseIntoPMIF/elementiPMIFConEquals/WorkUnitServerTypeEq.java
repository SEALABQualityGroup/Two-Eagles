package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import com.schema.WorkUnitServerType;

public class WorkUnitServerTypeEq extends WorkUnitServerType {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof WorkUnitServerType))
			return false;
		WorkUnitServerType workUnitServerType = (WorkUnitServerType)obj;
		if (!this.hasName() && workUnitServerType.hasName())
			return false;
		if (this.hasName() && !workUnitServerType.hasName())
			return false;
		if (this.hasName() && workUnitServerType.hasName())
			{
			try {
				if (!this.getName().asString().equals(workUnitServerType.getName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasQuantity() && workUnitServerType.hasQuantity())
			return false;
		if (this.hasQuantity() && !workUnitServerType.hasQuantity())
			return false;
		if (this.hasQuantity() && workUnitServerType.hasQuantity())
			{
			try {
				if (!this.getQuantity().asString().equals(workUnitServerType.getQuantity().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasSchedulingPolicy() && workUnitServerType.hasSchedulingPolicy())
			return false;
		if (this.hasSchedulingPolicy() && !workUnitServerType.hasSchedulingPolicy())
			return false;
		if (this.hasSchedulingPolicy() && workUnitServerType.hasSchedulingPolicy())
			{
			try {
				if (!this.getSchedulingPolicy().asString().equals(workUnitServerType.getSchedulingPolicy().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasTimeUnits() && workUnitServerType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && !workUnitServerType.hasTimeUnits())
			return false;
		if (this.hasTimeUnits() && workUnitServerType.hasTimeUnits())
			{
			try {
				if (!this.getTimeUnits().asString().equals(workUnitServerType.getTimeUnits().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasServiceTime() && workUnitServerType.hasServiceTime())
			return false;
		if (this.hasServiceTime() && !workUnitServerType.hasServiceTime())
			return false;
		if (this.hasServiceTime() && workUnitServerType.hasServiceTime())
			{
			try {
				if (!this.getServiceTime().asString().equals(workUnitServerType.getServiceTime().asString()))
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
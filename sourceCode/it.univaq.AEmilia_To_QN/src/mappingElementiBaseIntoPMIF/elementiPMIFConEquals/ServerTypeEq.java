package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.ServerType;

public class ServerTypeEq extends ServerType {

	private static final long serialVersionUID = 1L;

	public ServerTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public ServerTypeEq()
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof ServerType))
			return false;
		ServerType serverType = (ServerType)obj;
		if (!this.hasName() && serverType.hasName())
			return false;
		if (!serverType.hasName() && this.hasName())
			return false;
		if (this.hasName() && serverType.hasName())
			{
			try {
				if (!this.getName().asString().equals(serverType.getName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasQuantity() && serverType.hasQuantity())
			return false;
		if (this.hasQuantity() && !serverType.hasQuantity())
			return false;
		if (this.hasQuantity() && serverType.hasQuantity())
			{
			try {
				if (!this.getQuantity().asString().equals(serverType.getQuantity().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasSchedulingPolicy() && serverType.hasSchedulingPolicy())
			return false;
		if (this.hasSchedulingPolicy() && !serverType.hasSchedulingPolicy())
			return false;
		if (this.hasSchedulingPolicy() && serverType.hasSchedulingPolicy())
			{
			try {
				if (!this.getSchedulingPolicy().asString().equals(serverType.getSchedulingPolicy().asString()))
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

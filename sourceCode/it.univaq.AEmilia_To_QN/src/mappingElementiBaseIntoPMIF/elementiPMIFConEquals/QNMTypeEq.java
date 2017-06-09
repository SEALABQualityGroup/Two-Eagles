package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.QNMType;

public class QNMTypeEq extends QNMType {

	private static final long serialVersionUID = 1L;

	public QNMTypeEq(Node node)
		{
		super(node);
		}

	public QNMTypeEq()
		{
		super();
		}

	public ServiceRequestTypeEq getServiceRequestAt(int index) throws Exception
		{
		return new ServiceRequestTypeEq(getDomChildAt(Element, null, "ServiceRequest", index));
		}

	public ServiceRequestTypeEq getServiceRequest() throws Exception
		{
		return getServiceRequestAt(0);
		}

	public WorkloadTypeEq getWorkloadAt(int index) throws Exception
		{
		return new WorkloadTypeEq(getDomChildAt(Element, null, "Workload", index));
		}

	public WorkloadTypeEq getWorkload() throws Exception
		{
		return getWorkloadAt(0);
		}

	@Override
	public NodeTypeEq getNodeAt(int index) throws Exception {
		return new NodeTypeEq(getDomChildAt(Element, null, "Node", index));
	}

	@Override
	public NodeTypeEq getNode() throws Exception
		{
		return getNodeAt(0);
		}

	public ArcTypeEq getArcAt(int index) throws Exception
		{
		return new ArcTypeEq(getDomChildAt(Element, null, "Arc", index));
		}

	public ArcTypeEq getArc() throws Exception
		{
		return getArcAt(0);
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof QNMType))
			return false;
		QNMType type = (QNMType)obj;
		if (!this.hasNode() && type.hasNode())
			return false;
		if (this.hasNode() && !type.hasNode())
			return false;
		if (this.hasNode() && type.hasNode())
			{
			if (this.getNodeCount() != type.getNodeCount())
				return false;
			for (int i = 0; i < this.getNodeCount(); i++)
				{
				try {
					if (!this.getNodeAt(i).equals(type.getNodeAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasArc() && type.hasArc())
			return false;
		if (this.hasArc() && !type.hasArc())
			return false;
		if (this.hasArc() && type.hasArc())
			{
			if (this.getArcCount() != type.getArcCount())
				return false;
			for (int i = 0; i < this.getArcCount(); i++)
				{
				try {
					if (!this.getArcAt(i).equals(type.getArcAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkload() && type.hasWorkload())
			return false;
		if (this.hasWorkload() && !type.hasWorkload())
			return false;
		if (this.hasWorkload() && type.hasWorkload())
			{
			if (this.getWorkloadCount() != type.getWorkloadCount())
				return false;
			for (int i = 0; i < this.getWorkloadCount(); i++)
				{
				try {
					if (!this.getWorkloadAt(i).equals(type.getWorkloadAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasServiceRequest() && type.hasServiceRequest())
			return false;
		if (this.hasServiceRequest() && !type.hasServiceRequest())
			return false;
		if (this.hasServiceRequest() && type.hasServiceRequest())
			{
			if (this.getServiceRequestCount() != type.getServiceRequestCount())
				return false;
			for (int i = 0; i < this.getServiceRequestCount(); i++)
				{
				try {
					if (!this.getServiceRequestAt(i).equals(type.getServiceRequestAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasName() && type.hasName())
			return false;
		if (this.hasName() && !type.hasName())
			return false;
		if (this.hasName() && type.hasName())
			{
			try {
				if (!this.getName().asString().equals(type.getName().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasDescription() && type.hasDescription())
			return false;
		if (this.hasDescription() && !type.hasDescription())
			return false;
		if (this.hasDescription() && type.hasDescription())
			{
			try {
				if (!this.getDescription().asString().equals(type.getDescription().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (!this.hasDate_Time() && type.hasDate_Time())
			return false;
		if (this.hasDate_Time() && !type.hasDate_Time())
			return false;
		if (this.hasDate_Time() && type.hasDate_Time())
			{
			try {
				if (!this.getDate_Time().asString().equals(type.getDate_Time().asString()))
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

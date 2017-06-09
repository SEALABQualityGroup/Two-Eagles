package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;
import com.schema.WorkloadType;

public class WorkloadTypeEq extends WorkloadType {

	private static final long serialVersionUID = 1L;

	public WorkloadTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public WorkloadTypeEq()
		{
		super();
		}

	public OpenWorkloadTypeEq getOpenWorkloadAt(int index) throws Exception
		{
		return new OpenWorkloadTypeEq(getDomChildAt(Element, null, "OpenWorkload", index));
		}

	public OpenWorkloadTypeEq getOpenWorkload() throws Exception
		{
		return getOpenWorkloadAt(0);
		}

	public ClosedWorkloadTypeEq getClosedWorkloadAt(int index) throws Exception
		{
		return new ClosedWorkloadTypeEq(getDomChildAt(Element, null, "ClosedWorkload", index));
		}

	public ClosedWorkloadTypeEq getClosedWorkload() throws Exception
		{
		return getClosedWorkloadAt(0);
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof WorkloadType))
			return false;
		WorkloadType workloadType = (WorkloadType)obj;
		if (!this.hasOpenWorkload() && workloadType.hasOpenWorkload())
			return false;
		if (this.hasOpenWorkload() && !workloadType.hasOpenWorkload())
			return false;
		if (this.hasOpenWorkload() && workloadType.hasOpenWorkload())
			{
			if (this.getOpenWorkloadCount() != workloadType.getOpenWorkloadCount())
				return false;
			for (int i = 0; i < this.getOpenWorkloadCount(); i++)
				{
				try {
					if (!this.getOpenWorkloadAt(i).equals(workloadType.getOpenWorkloadAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasClosedWorkload() && workloadType.hasClosedWorkload())
			return false;
		if (this.hasClosedWorkload() && !workloadType.hasClosedWorkload())
			return false;
		if (this.hasClosedWorkload() && workloadType.hasClosedWorkload())
			{
			try {
				if (!this.getClosedWorkload().equals(workloadType.getClosedWorkload()))
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
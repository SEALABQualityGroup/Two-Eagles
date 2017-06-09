package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.SinkNodeType;

public class SinkNodeTypeEq extends SinkNodeType {

	private static final long serialVersionUID = 1L;

	public SinkNodeTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public SinkNodeTypeEq()
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof SinkNodeType))
			return false;
		SinkNodeType sinkNodeType = (SinkNodeType)obj;
		if (!this.hasName() && sinkNodeType.hasName())
			return false;
		if (this.hasName() && !sinkNodeType.hasName())
			return false;
		if (this.hasName() && sinkNodeType.hasName())
			{
			try {
				if (!this.getName().asString().equals(sinkNodeType.getName().asString()))
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

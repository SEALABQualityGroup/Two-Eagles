package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.SourceNodeType;

public class SourceNodeTypeEq extends SourceNodeType {

	private static final long serialVersionUID = 1L;

	public SourceNodeTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public SourceNodeTypeEq()
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof SourceNodeType))
			return false;
		SourceNodeType sourceNodeType = (SourceNodeType)obj;
		if (!this.hasName() && sourceNodeType.hasName())
			return false;
		if (this.hasName() && !sourceNodeType.hasName())
			return false;
		if (this.hasName() && sourceNodeType.hasName())
			{
			try {
				if (!this.getName().asString().equals(sourceNodeType.getName().asString()))
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

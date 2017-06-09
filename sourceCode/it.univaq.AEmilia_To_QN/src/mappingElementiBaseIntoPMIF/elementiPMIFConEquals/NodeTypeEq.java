package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;
import com.schema.NodeType;

public class NodeTypeEq extends NodeType {

	private static final long serialVersionUID = 1L;


	public NodeTypeEq()
		{
		super();
		}

	public NodeTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public SourceNodeTypeEq getSourceNodeAt(int index) throws Exception
		{
		return new SourceNodeTypeEq(getDomChildAt(Element, null, "SourceNode", index));
		}

	public SourceNodeTypeEq getSourceNode() throws Exception
		{
		return getSourceNodeAt(0);
		}

	public SinkNodeTypeEq getSinkNodeAt(int index) throws Exception
		{
		return new SinkNodeTypeEq(getDomChildAt(Element, null, "SinkNode", index));
		}

	public SinkNodeTypeEq getSinkNode() throws Exception
		{
		return getSinkNodeAt(0);
		}

	public ServerTypeEq getServerAt(int index) throws Exception
		{
		return new ServerTypeEq(getDomChildAt(Element, null, "Server", index));
		}

	public ServerTypeEq getServer() throws Exception
		{
		return getServerAt(0);
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof NodeType))
			return false;
		NodeType nodeType = (NodeType)obj;
		if (!this.hasServer() && nodeType.hasServer())
			return false;
		if (this.hasServer() && !nodeType.hasServer())
			return false;
		if (this.hasServer() && nodeType.hasServer())
			{
			if (this.getServerCount() != nodeType.getServerCount())
				return false;
			for (int i = 0; i < this.getServerCount(); i++)
				{
				try {
					if (!this.getServerAt(i).equals(nodeType.getServerAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasWorkUnitServer() && nodeType.hasWorkUnitServer())
			return false;
		if (this.hasWorkUnitServer() && !nodeType.hasWorkUnitServer())
			return false;
		if (this.hasWorkUnitServer() && nodeType.hasWorkUnitServer())
			{
			if (this.getWorkUnitServerCount() != nodeType.getWorkUnitServerCount())
				return false;
			for (int i = 0; i < this.getWorkUnitServerCount(); i++)
				{
				try {
					if (!this.getWorkUnitServerAt(i).equals(nodeType.getWorkUnitServerAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasSourceNode() && nodeType.hasSourceNode())
			return false;
		if (this.hasSourceNode() && !nodeType.hasSourceNode())
			return false;
		if (this.hasSourceNode() && nodeType.hasSourceNode())
			{
			if (this.getSourceNodeCount() != nodeType.getSourceNodeCount())
				return false;
			for (int i = 0; i < this.getSourceNodeCount(); i++)
				{
				try {
					if (!this.getSourceNodeAt(i).equals(nodeType.getSourceNodeAt(i)))
						return false;
					}
				catch (Exception e)
					{
					return false;
					}
				}
			}
		if (!this.hasSinkNode() && nodeType.hasSinkNode())
			return false;
		if (this.hasSinkNode() && !nodeType.hasSinkNode())
			return false;
		if (this.hasSinkNode() && nodeType.hasSinkNode())
			{
			if (this.getSinkNodeCount() != nodeType.getSinkNodeCount())
				return false;
			for (int i = 0; i < this.getSinkNodeCount(); i++)
				{
				try {
					if (!this.getSinkNodeAt(i).equals(nodeType.getSinkNodeAt(i)))
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
}

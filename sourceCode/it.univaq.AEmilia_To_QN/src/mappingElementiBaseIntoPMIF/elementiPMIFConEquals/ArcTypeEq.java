package mappingElementiBaseIntoPMIF.elementiPMIFConEquals;

import org.w3c.dom.Node;

import com.schema.ArcType;

/**
 * Classe utilizzata per il mapping di elementi base di una rete di
 * code in elementi PMIF.
 *
 * @author Mirko
 *
 */
public class ArcTypeEq extends ArcType {

	private static final long serialVersionUID = 1L;

	public ArcTypeEq(Node domChildAt)
		{
		super(domChildAt);
		}

	public ArcTypeEq()
		{
		super();
		}

	@Override
	public boolean equals(Object obj)
		{
		if (!(obj instanceof ArcType))
			return false;
		ArcType arcType = (ArcType)obj;
		if (this.hasDescription() && !arcType.hasDescription())
			return false;
		if (!this.hasDescription() && arcType.hasDescription())
			return false;
		if (this.hasDescription() && arcType.hasDescription())
			{
			try {
				if (!this.getDescription().asString().equals(arcType.getDescription().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (this.hasFromNode() && !arcType.hasFromNode())
			return false;
		if (!this.hasFromNode() && arcType.hasFromNode())
			return false;
		if (this.hasFromNode() && arcType.hasFromNode())
			{
			try {
				if (!this.getFromNode().asString().equals(arcType.getFromNode().asString()))
					return false;
				}
			catch (Exception e)
				{
				return false;
				}
			}
		if (this.hasToNode() && !arcType.hasToNode())
			return false;
		if (!this.hasToNode() && arcType.hasToNode())
			return false;
		if (this.hasToNode() && arcType.hasToNode())
			{
			try {
				if (!this.getToNode().asString().equals(arcType.getToNode().asString()))
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

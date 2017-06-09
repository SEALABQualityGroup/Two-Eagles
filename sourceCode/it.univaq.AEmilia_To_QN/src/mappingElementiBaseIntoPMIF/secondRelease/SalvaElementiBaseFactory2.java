/**
 * 
 */
package mappingElementiBaseIntoPMIF.secondRelease;

import java.util.Collection;

import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.ISalvaElementiBaseFactory;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;
import elementiBaseQN.ElementoBaseQN;

/**
 * @author Mirko
 *
 */
public class SalvaElementiBaseFactory2 
	implements ISalvaElementiBaseFactory 
	{

	/* (non-Javadoc)
	 * @see mappingElementiBaseIntoPMIF.ISalvaElementiBaseFactory#createSalvaElementiBase(java.util.Collection)
	 */
	@Override
	public ISalvaElementiBase createSalvaElementiBase(Collection<ElementoBaseQN> elementiBase)
		throws MappingElementiBaseException
		{
		return new SalvaElementiBase2(elementiBase);
		}

	}

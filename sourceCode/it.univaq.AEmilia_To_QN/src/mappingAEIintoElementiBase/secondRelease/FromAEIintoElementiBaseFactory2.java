/**
 * 
 */
package mappingAEIintoElementiBase.secondRelease;

import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecifiche;
import mappingAEIintoElementiBase.AEIintoElementiBaseException;
import mappingAEIintoElementiBase.IFromAEIintoElementiBase;
import mappingAEIintoElementiBase.IFromAEIintoElementiBaseFactory;

/**
 * @author Mirko
 *
 */
public class FromAEIintoElementiBaseFactory2 implements
		IFromAEIintoElementiBaseFactory {

	/* (non-Javadoc)
	 * @see mappingAEIintoElementiBase.IFromAEIintoElementiBaseFactory#createFromAEIintoElementiBase(java.util.List)
	 */
	@Override
	public IFromAEIintoElementiBase createFromAEIintoElementiBase(
			List<ISpecifiche> list) 
		throws AEIintoElementiBaseException 
		{
		return new FromAEIintoElementiBase2(list);
		}
	}

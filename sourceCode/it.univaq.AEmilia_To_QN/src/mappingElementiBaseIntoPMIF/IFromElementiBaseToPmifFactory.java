/**
 * 
 */
package mappingElementiBaseIntoPMIF;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;

/**
 * @author Mirko
 *
 */
public interface IFromElementiBaseToPmifFactory 
	{	
	
	public IFromElementiBaseToPmif createFromElementiBaseToPMIF() throws MappingElementiBaseException; 

	public IFromElementiBaseToPmif createFromElementiBaseToPMIF(QNMTypeEq type)  throws MappingElementiBaseException; 
	
	}

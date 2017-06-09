/**
 * 
 */
package mappingElementiBaseIntoPMIF;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import mappingElementiBaseIntoPMIF.secondRelease.FromElementiBaseToPMIF2;

/**
 * Factory utilizzata per la generazione di domande di servizio.
 * 
 * @author Mirko
 *
 */
public class FromElementiBaseToPmifFactory2
	implements IFromElementiBaseToPmifFactory
	{

	public IFromElementiBaseToPmif createFromElementiBaseToPMIF() 
 		throws MappingElementiBaseException
		{
		return new FromElementiBaseToPMIF2();
		}

	public IFromElementiBaseToPmif createFromElementiBaseToPMIF(QNMTypeEq type)
 		throws MappingElementiBaseException
		{
		return new FromElementiBaseToPMIF2(type);
		}
	}

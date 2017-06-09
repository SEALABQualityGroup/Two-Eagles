/**
 * 
 */
package mappingElementiBaseIntoPMIF;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import mappingElementiBaseIntoPMIF.secondRelease.FromElementiBaseToPMIF;

/**
 * Factory utilizzata per la generazione dei tempi di servizio.
 * 
 * @author Mirko
 *
 */
public class FromElementiBaseToPmifFactory implements
		IFromElementiBaseToPmifFactory 
		{

		/* (non-Javadoc)
		 * @see mappingElementiBaseIntoPMIF.IFromElementiBaseToPmifFactory#createFromElementiBaseToPMIF2()
		 */
		@Override
		public IFromElementiBaseToPmif createFromElementiBaseToPMIF() 
		 	throws MappingElementiBaseException
			{
			return new FromElementiBaseToPMIF();
			}
	
		/* (non-Javadoc)
		 * @see mappingElementiBaseIntoPMIF.IFromElementiBaseToPmifFactory#createFromElementiBaseToPMIF2(mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq)
		 */
		@Override
		public IFromElementiBaseToPmif createFromElementiBaseToPMIF(QNMTypeEq type)
		 	throws MappingElementiBaseException
			{
			return new FromElementiBaseToPMIF(type);
			}

		}

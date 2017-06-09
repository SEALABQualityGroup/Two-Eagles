/**
 * 
 */
package mappingElementiBaseIntoPMIF;

import java.text.SimpleDateFormat;
import java.util.Date;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import mappingElementiBaseIntoPMIF.secondRelease.FromElementiBaseToPMIF;

/**
 * Factory utilizzata per la generazione dei tempi di servizio.
 * Nel modello pmif vengono generati gli attributi opzionali
 * nome, descrizione e data. 
 * 
 * @author Mirko
 *
 */
public class FromElementiBaseToPmifFactory3 implements
		IFromElementiBaseToPmifFactory 
		{

		/* (non-Javadoc)
		 * @see mappingElementiBaseIntoPMIF.IFromElementiBaseToPmifFactory#createFromElementiBaseToPMIF2()
		 */
		@Override
		public IFromElementiBaseToPmif createFromElementiBaseToPMIF() 
	 		throws MappingElementiBaseException
			{
			FromElementiBaseToPMIF fromElementiBaseToPMIF = new FromElementiBaseToPMIF();
			QNMTypeEq typeEq = fromElementiBaseToPMIF.getRadice();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String string = simpleDateFormat.format(new Date());
			try {
				typeEq.addDate_Time(string);
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			try {
				typeEq.addDescription("Defaut Description");
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			try {
				typeEq.addName("DefaultName");
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			return fromElementiBaseToPMIF;
			}
	
		/* (non-Javadoc)
		 * @see mappingElementiBaseIntoPMIF.IFromElementiBaseToPmifFactory#createFromElementiBaseToPMIF2(mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq)
		 */
		@Override
		public IFromElementiBaseToPmif createFromElementiBaseToPMIF(QNMTypeEq type)
	 		throws MappingElementiBaseException
			{
			FromElementiBaseToPMIF fromElementiBaseToPMIF = new FromElementiBaseToPMIF(type);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String string = simpleDateFormat.format(new Date());
			try {
				type.addDate_Time(string);
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			try {
				type.addDescription("Defaut Description");
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			try {
				type.addName("DefaultName");
				} 
			catch (Exception e) 
				{
				throw new MappingElementiBaseException(e);
				}
			return fromElementiBaseToPMIF;
			}

		}

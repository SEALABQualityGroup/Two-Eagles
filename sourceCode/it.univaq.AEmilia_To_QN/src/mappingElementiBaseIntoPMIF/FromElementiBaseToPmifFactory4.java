/**
 * 
 */
package mappingElementiBaseIntoPMIF;

import java.text.SimpleDateFormat;
import java.util.Date;

import mappingElementiBaseIntoPMIF.elementiPMIFConEquals.QNMTypeEq;
import mappingElementiBaseIntoPMIF.secondRelease.FromElementiBaseToPMIF2;

/**
 * Factory utilizzata per la generazione di domande di servizio.
 * Nel modello pmif vengono generati gli attributi opzionali
 * nome, descrizione e data. 
 * 
 * @author Mirko
 *
 */
public class FromElementiBaseToPmifFactory4
	implements IFromElementiBaseToPmifFactory
	{

	public IFromElementiBaseToPmif createFromElementiBaseToPMIF()
 		throws MappingElementiBaseException
		{
		FromElementiBaseToPMIF2 fromElementiBaseToPMIF2 = new FromElementiBaseToPMIF2();
		QNMTypeEq typeEq = fromElementiBaseToPMIF2.getRadice();
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
		return fromElementiBaseToPMIF2;
		}

	public IFromElementiBaseToPmif createFromElementiBaseToPMIF(QNMTypeEq type)
 		throws MappingElementiBaseException
		{
		FromElementiBaseToPMIF2 fromElementiBaseToPMIF2 = new FromElementiBaseToPMIF2(type);
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
		return fromElementiBaseToPMIF2;
		}
	}

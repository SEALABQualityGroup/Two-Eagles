/**
 * 
 */
package config;

import mappingElementiBaseIntoPMIF.FromElementiBaseToPmifFactory3;
import mappingElementiBaseIntoPMIF.IFromElementiBaseToPmifFactory;
import transformerFases.ITrasformazioneFactory;
import transformerFases.TrasformazioneFactory;

/**
 * Classe utilizzata come factory per la generazione
 * di tempi di servizio o domande di servizio.
 * Attualmente,
 * 1) TrasformazioneFactory, FromElementiBaseToPmifFactory sono
 * per la generazione dei tempi di servizio
 * 2) TrasformazioneFactory2, FromElementiBaseToPmifFactory2 sono
 * per la generazione delle domande di servizio.
 * 
 * @author Mirko
 *
 */
public class ServiceTimeFactory 
	{

	public static ITrasformazioneFactory getTrasformazioneFactory()
		{
		return new TrasformazioneFactory();
		}
	
	public static IFromElementiBaseToPmifFactory getFromElementiBaseToPmifFactory()
		{
		return new FromElementiBaseToPmifFactory3();
		}
	
	}

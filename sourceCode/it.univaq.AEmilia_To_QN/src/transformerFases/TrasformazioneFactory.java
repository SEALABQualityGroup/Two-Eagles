/**
 * 
 */
package transformerFases;

import transformerFases.integrazioneOld.Trasformazione;

/**
 * @author Mirko
 *
 */
public class TrasformazioneFactory
	implements ITrasformazioneFactory
	{

	public ITrasformazione createTrasformazione() 
		{
		return new Trasformazione();
		}
	
	}

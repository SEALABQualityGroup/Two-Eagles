/**
 * 
 */
package transformerFases;

import transformerFases.integrazioneNew.Trasformazione2;

/**
 * @author Mirko
 *
 */
public class TrasformazioneFactory2 
	implements ITrasformazioneFactory 
	{

	/* (non-Javadoc)
	 * @see fasesTransformer.ITrasformazioneFactory#createTrasformazione()
	 */
	@Override
	public ITrasformazione createTrasformazione() 
		{
		return new Trasformazione2();
		}

	}

package restrizioniSpecifiche.secondRelease;

import java.util.List;

import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

public abstract class SpecificheFP2 
	extends Specifiche2 
	{

	public SpecificheFP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		}

	public SpecificheFP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione9();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione9();
		}

	/**
	 * Ogni azione fork è attaccata ad un buffer 
	 * non relativo ad un processo join o processo di routing, 
	 * un processo fork senza buffer,  
	 * un processo di servizio senza buffer.
	 * 
	 * @return
	 */
	public boolean restrizione9()
		throws RestrizioniSpecException
		{
		boolean b = true;
		// ogni equivalenza di input attaccata deve essere 
		// un buffer non attaccato ad un processo join,
		// un processo fork senza buffer, oppure un processo di servizio senza buffer.
		for (int i = 0; i < this.equivalenzeOutput.size(); i++)
			{
			IEquivalenza iEquivalenza = this.equivalenzeOutput.get(i);
			if (iEquivalenza instanceof IEquivalenzaBuffer)
				{
				AEIdecl idecl = this.AEIsDeclOutput.get(i);
				b = b && isBufferNoJoin(idecl) && isBufferNoRouting(idecl);
				}
			else if (iEquivalenza instanceof IEquivalenzaForkSenzaBuffer)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaServizioSenzaBuffer)
				b = b && true;
			else b = b && false;
			}
		return b;
		}
	}

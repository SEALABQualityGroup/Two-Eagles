package restrizioniSpecifiche.secondRelease;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheSPNB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;

// Restrizione 10;
// Restrizione 12;
// Restrizione 15.

public class SpecificheSPNB2 
	extends SpecificheSP2 
	implements ISpecificheSPNB 
	{

	public SpecificheSPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di servizio senza buffer.
		equivalenza = equivalenzaFactory.getSPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di servizio senza buffer
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a service process with no buffer");
		}

	public SpecificheSPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di servizio senza buffer.
		equivalenza = equivalenzaFactory.getSPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di servizio senza buffer
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a service process with no buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione12() && restrizione14() && 
		restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione10() && restrizione12() && restrizione14() && 
		restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}
	
	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaServizioSenzaBuffer equivalenzaServizioSenzaBuffer = getCastedEquiv();
		String[] strings = equivalenzaServizioSenzaBuffer.getArrivesNames();
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	private IEquivalenzaServizioSenzaBuffer getCastedEquiv()
		{
		return (IEquivalenzaServizioSenzaBuffer)this.equivalenza;
		}
	}

package restrizioniSpecifiche.secondRelease;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheSPWB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;

// Restrizione 8;
// Restrizione 12;
// Restrizione 15.

public class SpecificheSPWB2 
	extends SpecificheSP2 
	implements ISpecificheSPWB 
	{

	public SpecificheSPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di servizio con buffer.
		equivalenza = equivalenzaFactory.getSPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di servizio con buffer
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a service process with buffer");
		}

	public SpecificheSPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di servizio con buffer.
		equivalenza = equivalenzaFactory.getSPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di servizio con buffer
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a service process with buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione12() && restrizioneIstanze5() && 
		restrizioneIstanze6() && restrizioneIstanze7() && restrizioneIstanze15();
		}

	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione8() && restrizione12() && 
		restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7() && restrizioneIstanze15();
		}

	IEquivalenzaServizioConBuffer getCastedEquiv()
		{
		return (IEquivalenzaServizioConBuffer)this.equivalenza;
		}

	@Override
	public Expression[] getProbSelezione() 
		{
		IEquivalenzaServizioConBuffer equivalenzaServizioConBuffer =
			getCastedEquiv();
		return equivalenzaServizioConBuffer.getProbSelezione();
		}
	
	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaServizioConBuffer equivalenzaServizioConBuffer = getCastedEquiv();
		String[] strings = equivalenzaServizioConBuffer.getSelectionsNames();
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	@Override
	public Expression[] getPrioSelezione() 
		{
		IEquivalenzaServizioConBuffer equivalenzaServizioConBuffer =
			getCastedEquiv();
		return equivalenzaServizioConBuffer.getPrioSelezione();
		}
	
	/*
	 * 5) le priorità delle azioni di selezione devono essere Integer
	 */
	public boolean restrizioneIstanze15()
		{
		IEquivalenzaServizioConBuffer equivalenzaServizioConBuffer = getCastedEquiv();
		Expression[] espressiones = equivalenzaServizioConBuffer.getPrioSelezione();
		for (Expression expression : espressiones)
			{
			if (!(MetodiVari.isOnlyInteger(expression)))
				return false;
			}
		return true;
		}
	}

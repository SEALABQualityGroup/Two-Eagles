package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheRPWB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingConBuffer;

// Restrizione 8;
// Restrizione 12;
// Restrizione 15.

public class SpecificheRPWB2 
	extends SpecificheRP2 
	implements ISpecificheRPWB 
	{

	public SpecificheRPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di routing con buffer.
		equivalenza = equivalenzaFactory.getRPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di routing con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a routing process with buffer");
		}

	public SpecificheRPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di routing con buffer.
		equivalenza = equivalenzaFactory.getRPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di routing con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a routing process with buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione12() && restrizioneIstanze7();
		}

	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione8() && restrizione12()  && restrizioneIstanze7();
		}

	IEquivalenzaRoutingConBuffer getCastedEquiv()
		{
		return (IEquivalenzaRoutingConBuffer)this.equivalenza;
		}
	
	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer = getCastedEquiv();
		String string = equivalenzaRoutingConBuffer.getSelect();
		List<String> list = new ArrayList<String>();
		list.add(string);
		return list;
		}
	
	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaRoutingConBuffer equivalenzaRoutingConBuffer = getCastedEquiv();
		String string2 = equivalenzaRoutingConBuffer.getSelect();
		List<String> list = new ArrayList<String>();
		if (!string.equals(string2))
			return list;
		list = new CopyOnWriteArrayList<String>(equivalenzaRoutingConBuffer.getDelivers());
		return list;
		}
	}

package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheRPNB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;

// Restrizione 10;
// Restrizione 12;
// Restrizione 15.

public class SpecificheRPNB2 
	extends SpecificheRP2 
	implements ISpecificheRPNB 
	{

	public SpecificheRPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di routing senza buffer.
		equivalenza = equivalenzaFactory.getRPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di routing senza buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a routing process with no buffer");
		}

	public SpecificheRPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di routing senza buffer.
		equivalenza = equivalenzaFactory.getRPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di routing senza buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a routing process with no buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione12() && restrizione14() && restrizioneIstanze7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione10() && restrizione12() && restrizione14() && restrizioneIstanze7();
		}

	private IEquivalenzaRoutingSenzaBuffer getCastedEquiv()
		{
		return (IEquivalenzaRoutingSenzaBuffer)this.equivalenza;
		}
	
	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer = getCastedEquiv();
		String string = equivalenzaRoutingSenzaBuffer.getArrive();
		List<String> list = new ArrayList<String>();
		list.add(string);
		return list;
		}
	
	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaRoutingSenzaBuffer equivalenzaRoutingSenzaBuffer = getCastedEquiv();
		String string2 = equivalenzaRoutingSenzaBuffer.getArrive();
		List<String> list = new ArrayList<String>();
		if (!string.equals(string2))
			return list;
		list = new CopyOnWriteArrayList<String>(equivalenzaRoutingSenzaBuffer.getDelivers());
		return list;
		}
	}

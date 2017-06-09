package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheFPNB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;

// Restrizione 10;
// Restrizione 9;
// Restrizione 15.
public class SpecificheFPNB2 
	extends SpecificheFP2 
	implements ISpecificheFPNB 
	{

	public SpecificheFPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo fork senza buffer.
		equivalenza = equivalenzaFactory.getFPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo fork senza buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a fork process with no buffer");
		}

	// questo costruttore è utilizzato per il testing 
	public SpecificheFPNB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo fork senza buffer.
		equivalenza = equivalenzaFactory.getFPNB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo fork senza buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a fork process with no buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione9() && restrizione14();
		}

	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione9() && restrizione10() && restrizione14();
		}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaForkSenzaBuffer equivalenzaForkSenzaBuffer = getCastedModel();
		String string = equivalenzaForkSenzaBuffer.getArrive();
		List<String> list = new ArrayList<String>();
		list.add(string);
		return list;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaForkSenzaBuffer equivalenzaForkSenzaBuffer = getCastedModel();
		String[] strings = equivalenzaForkSenzaBuffer.getForks();
		// come precondizione strings è non null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	private IEquivalenzaForkSenzaBuffer getCastedModel()
		{
		return (IEquivalenzaForkSenzaBuffer)this.equivalenza;
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaForkSenzaBuffer equivalenzaForkSenzaBuffer = getCastedModel();
		List<String> list = new ArrayList<String>();
		String string2 = equivalenzaForkSenzaBuffer.getArrive();
		if (!string.equals(string2))
			return list;
		String[] strings = equivalenzaForkSenzaBuffer.getForks();
		list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	}

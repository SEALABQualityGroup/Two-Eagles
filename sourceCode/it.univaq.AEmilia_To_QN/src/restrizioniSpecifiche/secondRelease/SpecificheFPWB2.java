package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheFPWB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;

// Restrizione 8;
// Restrizione 9;
// Restrizione 15.

public class SpecificheFPWB2 
	extends SpecificheFP2 
	implements ISpecificheFPWB 
	{

	
	
	public SpecificheFPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo fork con buffer.
		equivalenza = equivalenzaFactory.getFPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo fork con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a fork process with buffer");
		}

	public SpecificheFPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo fork con buffer.
		equivalenza = equivalenzaFactory.getFPWB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo fork con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a fork process with buffer");
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
		return restrizione8() && restrizione9();
		}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaForkConBuffer equivalenzaForkConBuffer = getCastedEquiv();
		String string = equivalenzaForkConBuffer.getSelect();
		List<String> list = new ArrayList<String>();
		list.add(string);
		return list;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaForkConBuffer equivalenzaForkConBuffer = getCastedEquiv();
		String[] strings = equivalenzaForkConBuffer.getForks();
		// come precondizione strings è non null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	private IEquivalenzaForkConBuffer getCastedEquiv()
		{
		return (IEquivalenzaForkConBuffer)this.equivalenza;
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaForkConBuffer equivalenzaForkConBuffer = getCastedEquiv();
		String string2 = equivalenzaForkConBuffer.getSelect();
		List<String> list = new ArrayList<String>();
		if (!string.equals(string2))
			return list;
		list = new CopyOnWriteArrayList<String>(equivalenzaForkConBuffer.getForks());
		return list;
		}
	}

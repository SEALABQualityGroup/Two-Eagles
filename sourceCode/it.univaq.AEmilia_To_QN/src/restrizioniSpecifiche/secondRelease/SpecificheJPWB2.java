package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheJPWB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;

// Restrizione 11;
// Restrizione 12;
// Restrizione 15.

public class SpecificheJPWB2 
	extends SpecificheJP2 	
	implements ISpecificheJPWB 
	{

	public SpecificheJPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo join con buffer.
		equivalenza = equivalenzaFactory.getJP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo join con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a join process with buffer");
		}

	public SpecificheJPWB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo join con buffer.
		equivalenza = equivalenzaFactory.getJP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo join con buffer si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not a join process with buffer");
		}
	
	@Override
	public IEquivalenzaJoin getEquivalenzaJoin() 
		{
		IEquivalenzaJoin equivalenzaJoin = (IEquivalenzaJoin)this.equivalenza;
		return equivalenzaJoin;
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		// la restrizione 11 è necessaria
		// affinchè il processo join possa essere distinto
		// da quello con il buffer a quello senza buffer
		return restrizione12() && restrizione11() && restrizioneIstanze7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione11() && restrizione12() && restrizioneIstanze7();
		}
	
	private IEquivalenzaJoin getCastedEquiv()
		{
		return (IEquivalenzaJoin)this.equivalenza;
		}
	
	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaJoin equivalenzaJoin = getCastedEquiv();
		Expression[] espressiones = equivalenzaJoin.getProbRouting();
		if (espressiones == null)
			return new ArrayList<Expression>();
		List<Expression> list = new CopyOnWriteArrayList<Expression>(espressiones);
		return list;
		}

	}

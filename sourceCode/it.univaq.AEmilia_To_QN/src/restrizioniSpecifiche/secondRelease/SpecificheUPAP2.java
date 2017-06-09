package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviInfiniti;
import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheUPAP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;

// Restrizione 1;
// Restrizione 15.

public class SpecificheUPAP2 
	extends SpecificheAP2 
	implements ISpecificheUPAP 
	{

	public SpecificheUPAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di arrivi per una popolazione illimitata.
		equivalenza = equivalenzaFactory.getUPAP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di arrivi per una popolazione illimitata
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not arrival process for unbounded population");
		}

	public SpecificheUPAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di arrivi per una popolazione illimitata.
		equivalenza = equivalenzaFactory.getUPAP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di arrivi per una popolazione illimitata
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not arrival process for unbounded population");
		}

	@Override
	public IEquivalenzaArrivi getEquivalenzaArrivi() 
		{
		IEquivalenzaArrivi equivalenzaArrivi = (IEquivalenzaArrivi)this.equivalenza;
		return equivalenzaArrivi;
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione1() && restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione1() && restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		return new ArrayList<String>();
		}
	
	IEquivalenzaArriviInfiniti getCastedEquiv()
		{
		return (IEquivalenzaArriviInfiniti)this.equivalenza;
		}

	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaArriviInfiniti equivalenzaArriviInfiniti = getCastedEquiv();
		Expression[] espressiones = equivalenzaArriviInfiniti.getProbRouting();
		List<Expression> list = new CopyOnWriteArrayList<Expression>(espressiones);
		return list;
		}
	
	@Override
	public BehavEquation getPhaseBehavior() 
		{
		IEquivalenzaArriviInfiniti equivalenzaArriviInfiniti =
			getCastedEquiv();
		BehavEquation behavEquation = 
			equivalenzaArriviInfiniti.getPhaseBehavior();
		return behavEquation;
		}
	}

package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheSCAP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;
import equivalenzaComportamentale.interfaces.IEquivalenzaArriviFiniti;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaRouting;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;

// Restrizione 1;
// Restrizione 2;

public class SpecificheSCAP2 
	extends SpecificheAP2 
	implements ISpecificheSCAP 
	{

	public SpecificheSCAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di arrivi a singolo cliente.
		equivalenza = equivalenzaFactory.getSCAP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di arrivi a singolo cliente
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not an arrival process for sigle customer");
		}

	public SpecificheSCAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un processo di arrivi a singolo cliente.
		equivalenza = equivalenzaFactory.getSCAP();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un processo di arrivi a singolo cliente
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not an arrival process for sigle customer");
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
		return restrizione1() && restrizione14() 
			&& restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione1() && restrizione2()
			&& restrizione14() && restrizioneIstanze5() && restrizioneIstanze6() && restrizioneIstanze7();
		}

	/**
	 * Ogni azione di ritorno è attaccata ad un processo join, 
	 * un processo di servizio o un processo di routing.
	 * 
	 * @return
	 */
	public boolean restrizione2()
		{
		boolean ris = true;
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			if (equivalenza instanceof IEquivalenzaJoin)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaServizio)
				ris = ris && true;
			else if (equivalenza instanceof IEquivalenzaRouting)
				ris = ris && true;
			else 
				ris = ris && false;
			}
		return ris;
		}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaArriviFiniti equivalenzaArriviFiniti = getCastedEquiv();
		String[] strings = equivalenzaArriviFiniti.getReturns();
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	private IEquivalenzaArriviFiniti getCastedEquiv()
		{
		return (IEquivalenzaArriviFiniti)this.equivalenza;
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaArriviFiniti equivalenzaArriviFiniti = getCastedEquiv();
		List<String> list = new CopyOnWriteArrayList<String>(equivalenzaArriviFiniti.getReturns());
		if (!list.contains(string))
			return new ArrayList<String>();
		List<String> list2 = new CopyOnWriteArrayList<String>(equivalenzaArriviFiniti.getDelivers());
		return list2;
		}

	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaArriviFiniti equivalenzaArriviFiniti = getCastedEquiv();
		Expression[] espressiones = equivalenzaArriviFiniti.getProbRouting();
		List<Expression> list = new CopyOnWriteArrayList<Expression>(espressiones);
		return list;
		}

	@Override
	public BehavEquation getPhaseBehavior() 
		{
		IEquivalenzaArriviFiniti equivalenzaArriviFiniti =
			getCastedEquiv();
		BehavEquation behavEquation = 
			equivalenzaArriviFiniti.getPhaseBehavior();
		return behavEquation;
		}
	}

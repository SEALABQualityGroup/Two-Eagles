package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheSP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.ArchiType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;

public abstract class SpecificheSP2 
	extends Specifiche2 
	implements ISpecificheSP 
	{

	public SpecificheSP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType) throws RestrizioniSpecException 
		{
		super(archiType,idecl,equivalenzaFactory,listEquivalenzeArchiType);
		}

	public SpecificheSP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory) throws RestrizioniSpecException 
		{
		super(archiType,idecl,equivalenzaFactory);
		}
	
	private IEquivalenzaServizio getCastedEquiv()
		{
		return (IEquivalenzaServizio)this.equivalenza;
		}

	@Override
	public HashMap<String, List<String>> getDeliversFromSelection() 
		{
		IEquivalenzaServizio equivalenzaServizio =
			getCastedEquiv();
		return equivalenzaServizio.getDeliversFromSelection();
		}

	@Override
	public IEquivalenzaServizio getEquivalenzaServizio() 
		{
		IEquivalenzaServizio equivalenzaServizio = (IEquivalenzaServizio)this.equivalenza;
		return equivalenzaServizio;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		String[] strings = equivalenzaServizio.getDelivers();
		// come precondizione strings non è null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		List<String> list = new ArrayList<String>();
		HashMap<String, List<String>> hashMap = equivalenzaServizio.getDeliversFromSelection();
		if (hashMap.get(string) == null)
			return list;
		else
			list = hashMap.get(string);
		return list;
		}

	@Override
	public HashMap<String, List<Expression>> getProbRouting() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		HashMap<String, Expression[]> hashMap = equivalenzaServizio.getProbRoutingFromSel();
		HashMap<String, List<Expression>> hashMap2 = new HashMap<String, List<Expression>>();
		Set<Entry<String,Expression[]>> set = hashMap.entrySet();
		for (Entry<String,Expression[]> entry : set)
			{
			String string = entry.getKey();
			Expression[] espressiones = entry.getValue();
			List<Expression> list = null;
			if (espressiones != null)
				list = new CopyOnWriteArrayList<Expression>(espressiones);
			hashMap2.put(string, list);
			}
		return hashMap2;
		}

	@Override
	public List<Expression> getProbSelection() 
		{
		IEquivalenzaServizio equivalenzaServizio =
			getCastedEquiv();
		Expression[] espressiones = equivalenzaServizio.getProbSelezione();
		List<Expression> list = new CopyOnWriteArrayList<Expression>(espressiones);
		return list;
		}

	@Override
	public List<BehavEquation> getServiceEquations() 
		{
		IEquivalenzaServizio equivalenzaServizio =
			getCastedEquiv();
		return equivalenzaServizio.getServiceEquations();
		}

	@Override
	public HashMap<String, String> getServicesNamesFromSelections() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		return equivalenzaServizio.getServicesNamesFromSelections();
		}
	
	/*
	 * 1) i pesi delle azioni di scelta di fase devono essere Real; (isOnlyReal)
	 */
	public boolean restrizioneIstanze5() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		List<BehavEquation> behavEquations = equivalenzaServizio.getServiceEquations();
		AETinteractions tinteractions = this.equivalenza.getEt().getInteractions();
		List<Expression> listRis = new ArrayList<Expression>();
		for (BehavEquation behavEquation : behavEquations)
			{
			ProcessTerm processTerm = behavEquation.getTermineProcesso();
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm,tinteractions);
			// per precondizione phaseBehavior è un comportamento di fase
			List<Expression> list = phaseBehavior.getPesiScelta();
			listRis.addAll(list);
			}
		// listRis può essere vuoto
		for (Expression expression : listRis)
			{
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
	
	/*
	 * 2) i tassi delle azioni di fase devono essere Real; (isOnlyReal)
	 */
	public boolean restrizioneIstanze6() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		List<BehavEquation> behavEquations = equivalenzaServizio.getServiceEquations();
		AETinteractions tinteractions = this.equivalenza.getEt().getInteractions();
		List<Expression> listRis = new ArrayList<Expression>();
		for (BehavEquation behavEquation : behavEquations)
			{
			ProcessTerm processTerm = behavEquation.getTermineProcesso();
			PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm,tinteractions);
			// per precondizione phaseBehavior è un comportamento di fase
			List<Expression> list = phaseBehavior.getTassi();
			listRis.addAll(list);
			}
		for (Expression expression : listRis)
			{
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
	
	/*
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 */
	public boolean restrizioneIstanze7() 
		{
		IEquivalenzaServizio equivalenzaServizio = getCastedEquiv();
		HashMap<String, Expression[]> hashMap = equivalenzaServizio.getProbRoutingFromSel();
		// hashMap può contenere dei valori null
		Collection<Expression[]> collection = hashMap.values();
		for (Expression[] espressiones : collection)
			{
			if (espressiones != null)
				{
				for (Expression expression : espressiones)
					{
					if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
						return false;
					}
				}
			}
		return true;
		}	
	
	/*
	 * 4) le priorità delle azioni di selezione devono essere Integer
	 */
	
	}

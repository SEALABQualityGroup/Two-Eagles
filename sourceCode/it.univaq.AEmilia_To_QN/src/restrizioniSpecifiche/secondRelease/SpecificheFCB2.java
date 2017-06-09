package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheFCB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETbehavior;
import specificheAEmilia.ArchiType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import specificheAEmilia.Integer;
import specificheAEmilia.IntegerRangeType;
import specificheAEmilia.Header;
import specificheAEmilia.NormalType;
import specificheAEmilia.Real;
import specificheAEmilia.VarInit;
import valutazione.Interezza;
import valutazione.InterezzaException;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferLimitato;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ConditionalGetBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class SpecificheFCB2 
	extends SpecificheB2 
	implements ISpecificheFCB 
	{
	
	public SpecificheFCB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un buffer a capacità finita.
		equivalenza = equivalenzaFactory.getFCB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un buffer a capacità finita si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+" is not a finite capacity buffer");
		}

	// questo costruttore è per il testing
	public SpecificheFCB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un buffer a capacità finita.
		equivalenza = equivalenzaFactory.getFCB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un buffer a capacità finita si solleva un'eccezione
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+" is not a finite capacity buffer");
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione7() && restrizione14() && restrizioneIstanze12() &&
		restrizioneIstanze8() && restrizioneIstanze13() && restrizioneIstanze9() && 
		restrizioneIstanze10() && restrizioneIstanze14() && restrizioneIstanze11();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione6() && 
		restrizione7() && 
		restrizione14() && restrizioneIstanze12() &&
		restrizioneIstanze8() && restrizioneIstanze13() && restrizioneIstanze9() && 
		restrizioneIstanze10() && restrizioneIstanze14() && restrizioneIstanze11();
		}
	
	private IEquivalenzaBufferLimitato getCastedEquiv()
		{
		return (IEquivalenzaBufferLimitato)this.equivalenza;
		}

	@Override
	public Expression getLimiteClasse(String string) 
		{
		IEquivalenzaBufferLimitato equivalenzaBufferLimitato =
			getCastedEquiv();
		return equivalenzaBufferLimitato.getLimiteClasse(string);
		}

	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaBufferLimitato equivalenzaBufferLimitato = getCastedEquiv();
		int i = equivalenzaBufferLimitato.getNumeroClassiCliente();
		double d = 1.0;
		Real real = new Real(d);
		List<Expression> list = new ArrayList<Expression>(i);
		list.add(real);
		for (int j = 0; j < i; j ++)
			{
			list.add(real);
			}
		return list;
		}

	@Override
	public List<ISpecifiche> getSpecificheOutput()
			throws RestrizioniSpecException 
		{
		List<IEquivalenza> list = getEquivalenzeOutput();
		List<ISpecifiche> list2 = new ArrayList<ISpecifiche>();
		for (IEquivalenza equivalenza : list)
			{
			ISpecifiche specifiche = SpecificheRules2.getSpecificheFromEquivalenza(archiType, 
					listEquivalenzeArchiType, equivalenza);
			list2.add(specifiche);
			}
		return list2;
		}
	
	/*
	 * 4) le capacità di ogni classe deve essere un Integer; (isOnlyInteger)
	 */
	public boolean restrizioneIstanze12()
		{
		IEquivalenzaBufferLimitato equivalenzaBufferLimitato = getCastedEquiv();
		Expression[] espressiones = equivalenzaBufferLimitato.getLimitiClassi();
		for (Expression expression : espressiones)
			{
			if (!MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
			
	// si verificano le condizioni sulle espressioni dei processi get
	// 10) si verificano le condizioni di buffer pieno
	public boolean restrizioneIstanze14()
		{
		IEquivalenzaBufferLimitato equivalenzaBufferLimitato = getCastedEquiv();
		List<ConditionalGetBehavior> list = equivalenzaBufferLimitato.getConditionalGetBehaviors();
		for (ConditionalGetBehavior conditionalGetBehavior : list)
			{
			int n = conditionalGetBehavior.getIdentificationNumber();
			// se la condizione di esecuzione di processTerm non corrisponde
			// alla condizione di buffer pieno si restituisce false
			if (!conditionalGetBehavior.getConditionParameter(n))
				return false;
			}
		return true;
		}
	
	// si verificano le condizioni sulle espressioni dei processi get
	// 11) si verificano le espressioni sulla chiamata comportamentale
	public boolean restrizioneIstanze11()
		{
		IEquivalenzaBufferLimitato equivalenzaBufferLimitato = getCastedEquiv();
		List<ConditionalGetBehavior> list = equivalenzaBufferLimitato.getConditionalGetBehaviors();
		for (ConditionalGetBehavior conditionalGetBehavior : list)
			{
			if (!conditionalGetBehavior.checkBehavProcess())
				return false;
			}
		return true;
		}
	
	// 7) Il parametro sulla capacità attuale del buffer per una determinata classe di clienti 
	// deve essere compreso tra gli estremi dell'intervallo che definisce il parametro di capacità.
	public boolean restrizioneIstanze13() throws RestrizioniSpecException
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		// si rende tail recursion il comportamento
		AETbehavior tbehavior = equivalenzaBuffer.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		
		// per precondizione tbehavior2 ha una sola equazione
		BehavEquation behavEquation = tbehavior2.getBehaviors()[0];
		Header header = behavEquation.getBehavHeader();
		VarInit[] varInits = equivalenzaBuffer.capDecl(header);
		for (VarInit varInit : varInits)
			{
			NormalType normalType = varInit.getType();
			// verifichiamo che il primo estremo dell'intervallo è 0
			IntegerRangeType integerRangeType = (IntegerRangeType)normalType;
			Expression expression = integerRangeType.getBeginningInt();
			Expression espressione2 = integerRangeType.getEndingInt();
			// espressione deve essere un Integer o un reale
			if (!(MetodiVari.isOnlyInteger(expression)) && !(MetodiVari.isOnlyReal(expression)))
				return false;
			if (MetodiVari.isOnlyInteger(expression))
				{
				Integer integer = (Integer)expression;
				// integer deve avere valore zero
				int i = integer.getValore();
				if (i != 0)
					return false;
				}
			if (MetodiVari.isOnlyReal(expression))
				{
				Real real = (Real)expression;
				// real deve avere valore zero
				double d = real.getValore();
				if (d != 0)
					return false;
				// real deve essere un intero
				Interezza interezza = new Interezza();
				if (!interezza.isIntero(real))
					return false;
				}
			// espressione2 deve essere un Integer o Real, poichè per precondizione abbiamo
			// il tipo di elemento architetturale normalizzato
			if (!(MetodiVari.isOnlyInteger(espressione2)) && !(MetodiVari.isOnlyReal(espressione2)))
				return false;
			if (MetodiVari.isOnlyInteger(espressione2))
				{
				Integer integer2 = (Integer)espressione2;
				// integer2 deve avere valore maggiore di zero
				// integer2 è la capacità del buffer
				int j = integer2.getValore();
				if (j <= 0) return false;
				}
			if (MetodiVari.isOnlyReal(espressione2))
				{
				Real real = (Real)espressione2;
				// real deve avere valore maggiore di zero
				// real è la capacità del buffer
				double j = real.getValore();
				if (j <= 0) return false;
				// real deve essere un intero
				Interezza interezza = new Interezza();
				if (!interezza.isIntero(real))
					return false;
				}
			// verifichiamo che l'espressione di inizializzazione di ogni parametro sia 
			// compressa nel range dell'intervallo che definisce il parametro
			Expression espressione3 = varInit.getExpr();
			Interezza interezza = new Interezza();
			Integer integer;
			try {
				integer = interezza.returnIntero(espressione3);
				} 
			catch (InterezzaException e) 
				{
				throw new RestrizioniSpecException(e);
				}
			Integer integer2;
			try {
				integer2 = interezza.returnIntero(espressione2);
				} 
			catch (InterezzaException e) 
				{
				throw new RestrizioniSpecException(e);
				}
			Integer integer3;
			try {
				integer3 = interezza.returnIntero(expression);
				} 
			catch (InterezzaException e) 
				{
				throw new RestrizioniSpecException(e);
				}
			if (integer.getValore() > integer2.getValore())
				return false;
			if (integer.getValore() < integer3.getValore())
				return false;
			}
		return true;
		}
	}

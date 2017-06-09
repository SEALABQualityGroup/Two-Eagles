package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.ArchiType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioSenzaBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PhaseBehavior;

public abstract class SpecificheAP2 
	extends Specifiche2 
	{

	public SpecificheAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		}

	public SpecificheAP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione1() && restrizione14();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione1() && restrizione14();
		}

	/**
	 * Ogni azione di consegna della componente è 
	 * attaccata ad un: buffer, non relativo ad un 
	 * processo join o processo di routing; 
	 * un processo fork senza buffer o un processo di 
	 * servizio senza buffer.
	 * 
	 * @return
	 */
	public boolean restrizione1() throws RestrizioniSpecException {
	boolean b = true;
	// ogni equivalenza di input attaccata deve essere 
	// un buffer non attaccato ad un processo join,
	// un processo fork senza buffer, oppure un processo di servizio senza buffer.
	for (int i = 0; i < this.equivalenzeOutput.size(); i++)
		{
		IEquivalenza iEquivalenza = this.equivalenzeOutput.get(i);
		if (iEquivalenza instanceof IEquivalenzaBuffer)
			{
			AEIdecl idecl = this.AEIsDeclOutput.get(i);
			b = b && isBufferNoJoin(idecl) && isBufferNoRouting(idecl);
			}
		else if (iEquivalenza instanceof IEquivalenzaForkSenzaBuffer)
			b = b && true;
		else if (iEquivalenza instanceof IEquivalenzaServizioSenzaBuffer)
			b = b && true;
		else b = b && false;
		}
	return b;
	}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		return new ArrayList<String>();
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaArrivi equivalenzaArrivi = getCastedEquiv();
		String[] strings = equivalenzaArrivi.getDelivers();
		// come precondizione strings è non null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}

	private IEquivalenzaArrivi getCastedEquiv()
		{
		return (IEquivalenzaArrivi)this.equivalenza;
		}

	// 1)i pesi delle azioni di scelta di fase devono essere Real
	public boolean restrizioneIstanze5() 
		{
		IEquivalenzaArrivi equivalenzaArrivi = getCastedEquiv();
		BehavEquation behavEquation = equivalenzaArrivi.getPhaseBehavior();
		AETinteractions tinteractions = this.equivalenza.getEt().getInteractions();
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm,tinteractions);
		// per precondizione phaseBehavior è un comportamento di fase
		List<Expression> list = phaseBehavior.getPesiScelta();
		// list può essere vuoto
		for (Expression expression : list)
			{
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}

	// i tassi delle azioni di fase devono essere Real
	public boolean restrizioneIstanze6() 
		{
		IEquivalenzaArrivi equivalenzaArrivi = getCastedEquiv();
		Expression[] espressiones = equivalenzaArrivi.getTassiProcesso();
		for (int i = 0; i < espressiones.length; i++)
			{
			Expression expression = espressiones[i];
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}

	// i pesi delle azioni di prosecuzione del percorso devono essere Real
	public boolean restrizioneIstanze7() 
		{
		IEquivalenzaArrivi equivalenzaArrivi = getCastedEquiv();
		Expression[] espressiones = equivalenzaArrivi.getProbRouting();
		for (int i = 0; i < espressiones.length; i++)
			{
			Expression expression = espressiones[i];
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
	}

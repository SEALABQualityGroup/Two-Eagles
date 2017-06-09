package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ExpPhaseAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.PrePhaseAction;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateExp;
import specificheAEmilia.RateNoExp;
import specificheAEmilia.Stop;

public class PhaseBehavior 
	{
	
	private ProcessTerm processTerm;
	private AETinteractions tinteractions;

	public PhaseBehavior(ProcessTerm processTerm,
			AETinteractions tinteractions) 
		{
		super();
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}
	
	public boolean isPhaseBehavior()
		{
		// Il comportamento di una distribuzione di tipo 
		// fase è una combinazione arbitraria di azioni 
		// di fase e azioni di scelta di fase che, 
		// complessivamente, determinano un insieme di 
		// alternative, le quali tutte terminano con lo 
		// stesso comportamento non di tipo fase
		if (!(this.processTerm instanceof ChoiceProcess 
				|| this.processTerm instanceof ActionProcess))
			return false;
		// caso in cui la distribuzione è hyperesponenziale
		if (this.processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)this.processTerm;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				if (!(processTerm instanceof ActionProcess))
					return false;
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action phaseChoiceA = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				PrePhaseAction prePhaseAction = new PrePhaseAction(phaseChoiceA,this.tinteractions);
				// se phaseChoiceA non è un'azione di scelta fase si restituisce false
				if (!prePhaseAction.isPhaseChoice())
					return false;
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
								processTerm3 == null ? null : processTerm3.copy());
				// list deve avere taglia uguale a 1
				if (list.size() != 1)
					return false;
				ProcessTerm processTerm4 = list.get(0);
				// processTerm4 deve essere un ActionProcess con un'azione di fase
				if (!(processTerm4 instanceof ActionProcess))
					return false;
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				Action phaseAction = actionProcess2.getAzione();
				ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseAction,this.tinteractions);
				// si verifica che phaseAction sia un'azione di fase
				if (!expPhaseAction.isPhase())
					return false;
				// si riconosce un eventuale comportamento null
				ProcessTerm processTerm5 = actionProcess2.getProcesso();
				NullBehavior nullBehavior2 = new NullBehavior(processTerm5,this.tinteractions);
				ProcessTerm processTerm6 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm5, processTerm6);
				// list2 deve avere taglia 1
				if (list2.size() != 1)
					return false;
				ProcessTerm processTerm7 = list2.get(0);
				// si richiama ricorsivamente questo metodo
				if (processTerm7 != null && 
						!(processTerm7 instanceof BehavProcess) &&
							!(processTerm7 instanceof Stop))
					{
					PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm7,this.tinteractions);
					return phaseBehavior.isPhaseBehavior();
					}
				}
			}
		// caso in cui la distribuzione è esponenziale
		// o hypoesponenziale
		if (this.processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve avere taglia uguale a 1
			if (list.size() != 1)
				return false;
			ProcessTerm processTerm2 = list.get(0);
			// processTerm2 deve essere un ActionProcess
			if (!(processTerm2 instanceof ActionProcess))
				return false;
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action phaseA = actionProcess2.getAzione();
			ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseA,this.tinteractions);
			if (!expPhaseAction.isPhase())
				return false;
			ProcessTerm processTerm31 = actionProcess2.getProcesso();
			// si riconosce un eventuale comportamento null
			NullBehavior nullBehavior2 = new NullBehavior(processTerm31,this.tinteractions);
			ProcessTerm processTerm3 = nullBehavior2.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(processTerm31, processTerm3);
			// list2 deve avere taglia 1
			if (list2.size() != 1)
				return false;
			ProcessTerm processTerm4 = list2.get(0);
			if (processTerm4 != null && 
					!(processTerm4 instanceof BehavProcess) &&
						!(processTerm4 instanceof Stop))
				{
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm4,this.tinteractions);
				return phaseBehavior.isPhaseBehavior();
				}
			}
		return true;
		}
	
	// restituisce il comportamento di fase massimo di processTerm.
	// Restituisce null se processTerm non inizia con un comportamento di fase
	// può restituire un termine di processo con foglie uguali a null
	public ProcessTerm getMaximalPhaseBehavior()
		{
		ProcessTerm ris = null;
		// caso in cui la distribuzione è hyperesponenziale
		if (this.processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)this.processTerm;
			ris = new ChoiceProcess();
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action phaseChoiceA = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// phaseChoiceA è un'azione di scelta fase
				PrePhaseAction prePhaseAction = new PrePhaseAction(phaseChoiceA,this.tinteractions);
				if (!prePhaseAction.isPhaseChoice())
					return null;
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
								processTerm3 == null ? null : processTerm3.copy());
				// list ha taglia uguale a 1
				if (list.size() != 1)
					return null;
				ProcessTerm processTerm4 = list.get(0);
				// processTerm4 deve essere un ActionProcess con un'azione di fase
				if (!(processTerm4 instanceof ActionProcess))
					return null;
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				Action phaseAction = actionProcess2.getAzione();
				// phaseAction è un'azione di fase
				ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseAction,this.tinteractions);
				if (!expPhaseAction.isPhase())
					return null;
				ProcessTerm processTerm5 = actionProcess2.getProcesso();
				// riconosciamo un eventuale comportamento null
				nullBehavior = new NullBehavior(
						processTerm5 == null ? null : processTerm5.copy(),this.tinteractions);
				ProcessTerm processTerm3b = nullBehavior.getMaximalNullBehavior();
				// si effettua la differenza tra processTerm5 e processTerm3b 
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm5 == null ? null : processTerm5.copy(), 
								processTerm3b == null ? null : processTerm3b.copy());
				// list2 deve avere taglia 1
				if (list2.size() != 1)
					return null;
				processTerm5 = list2.get(0);
				// sommiamo phaseChoiceA + processTerm3 + phaseAction + processTerm3b + processTerm6
				ProcessTerm processTerm6 = null;
				// si richiama ricorsivamente questo metodo wrappando processTerm5
				PhaseBehavior phaseBehavior = new PhaseBehavior(
						processTerm5 == null ? null : processTerm5.copy(),this.tinteractions);
				processTerm6 = phaseBehavior.getMaximalPhaseBehavior();
				// sommiamo phaseChoiceA + processTerm3 + phaseAction + processTerm3b + processTerm6
				ProcessTerm processTerm7 = MetodiVari.somma(phaseChoiceA.copy(), 
						processTerm3 == null ? null : processTerm3.copy());
				ProcessTerm processTerm8 = MetodiVari.somma(phaseAction.copy(), 
						processTerm3b == null ? null : processTerm3b.copy());
				processTerm8 = MetodiVari.somma(
						processTerm8 == null ? null : processTerm8.copy(), 
								processTerm6 == null ? null : processTerm6.copy());
				processTerms2[i] = MetodiVari.somma(
						processTerm7 == null ? null : processTerm7.copy(), 
								processTerm8 == null ? null : processTerm8.copy());
				}
			}
		// caso in cui la distribuzione è esponenziale
		// o hypoesponenziale
		if (this.processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve avere taglia uguale a 1
			if (list.size() != 1)
				return null;
			ProcessTerm processTerm2 = list.get(0);
			// possiamo avere un caso in cui c'è un comportamento null prima di uno ChoiceProcess
			// e quindi processTerm2 può non essere un ActionProcess
			if (processTerm2 instanceof ActionProcess)
				{
				// processTerm2 deve essere un ActionProcess
				ActionProcess actionProcess2 = (ActionProcess)processTerm2;
				Action phaseA = actionProcess2.getAzione();
				ProcessTerm processTerm3 = actionProcess2.getProcesso();
				// phaseA è un'azione di fase
				ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseA,this.tinteractions);
				if (!expPhaseAction.isPhase())
					return null;
				// riconosciamo un eventuale comportamento null
				nullBehavior = new NullBehavior(
					processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
				ProcessTerm processTerm3b = nullBehavior.getMaximalNullBehavior();
				// effettuaiamo la differenza tra processTerm3 e processTerm3b
				List<ProcessTerm> list2 = MetodiVari.differenza(
					processTerm3 == null ? null : processTerm3.copy(), 
							processTerm3b == null ? null : processTerm3b.copy());
				// list2 deve avere taglia 1
				if (list2.size() != 1)
					return null;
				processTerm3 = list2.get(0);
				// si richiama ricorsivamente questa funzione su un wrapper di processTerm3
				ProcessTerm processTerm4 = null;
				PhaseBehavior phaseBehavior = new PhaseBehavior(
					processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
				processTerm4 = phaseBehavior.getMaximalPhaseBehavior();
				// sommiamo processTerm + phaseA + processTerm3b + processTerm4
				ProcessTerm processTerm5 = MetodiVari.somma(processTerm3b, 
						processTerm4 == null ? null : processTerm4.copy());
				processTerm5 = MetodiVari.somma(phaseA.copy(), 
					processTerm5 == null ? null : processTerm5);
				ris = MetodiVari.somma(
					processTerm == null ? null : processTerm.copy(), 
							processTerm5 == null ? null : processTerm5.copy());
				}
			else if (processTerm2 instanceof ChoiceProcess)
				{
				// si richiama ricorsivamente questo metodo
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm2,tinteractions);
				ris = phaseBehavior.getMaximalPhaseBehavior();
				// sommiamo a ris processTerm
				ris = MetodiVari.somma(processTerm, ris);
				}
			else return null;
			}
		return ris;
		}

	// restituisce null se il comportamento non è di fase
	// restituisce una lista di espressioni effettuando una visita in profondità destra
	// dell'albero dei comportamenti.
	public List<Expression> getTassi() 
		{
		List<Expression> list = new ArrayList<Expression>();
		if (!isPhaseBehavior())
			return null;
		// caso in cui la distribuzione è hyperesponenziale
		if (this.processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)this.processTerm;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(processTerm2,this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm2, processTerm3);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm4 = list2.get(0);
				// processTerm4 deve essere un ActionProcess con un'azione di fase
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				Action phaseAction = actionProcess2.getAzione();
				ActionRate actionRate = phaseAction.getRate();
				// per precondizione actionRate deve essere un RateExp
				RateExp rateExp = (RateExp)actionRate;
				Expression expression = rateExp.getExpr();
				list.add(expression);
				ProcessTerm processTerm5 = actionProcess2.getProcesso();
				// si riconosce un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm5,this.tinteractions);
				ProcessTerm processTerm61 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list31 = MetodiVari.differenza(processTerm5, processTerm61);
				// list3 ha taglia 1
				ProcessTerm processTerm62 = list31.get(0);
				// si richiama ricorsivamente questo metodo wrappando processTerm5
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm62,this.tinteractions);
				List<Expression> list3 = phaseBehavior.getTassi();
				if (list3 != null)
					list.addAll(list3);
				}
			}
		// caso in cui la distribuzione è esponenziale
		// o hypoesponenziale
		if (this.processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia uguale a 1
			ProcessTerm processTerm2 = list2.get(0);
			// possiamo avere un caso in cui c'è un comportamento null prima di uno ChoiceProcess
			// e quindi processTerm2 può non essere un ActionProcess
			if (processTerm2 instanceof ActionProcess)
				{
				ActionProcess actionProcess2 = (ActionProcess)processTerm2;
				Action phaseA = actionProcess2.getAzione();
				ActionRate actionRate = phaseA.getRate();
				// per precondizione actionRate è RateExp
				RateExp rateExp = (RateExp)actionRate;
				Expression expression = rateExp.getExpr();
				list.add(expression);
				ProcessTerm processTerm3 = actionProcess2.getProcesso();
				// si riconosce un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm3,this.tinteractions);
				ProcessTerm processTerm41 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list31 = MetodiVari.differenza(processTerm3, processTerm41);
				// list3 deve avere taglia 1
				ProcessTerm processTerm5 = list31.get(0);
				// si richiama ricorsivamente questa funzione su un wrapper di processTerm5
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm5,this.tinteractions);
				ProcessTerm processTerm4 = phaseBehavior.getMaximalPhaseBehavior();
				phaseBehavior = new PhaseBehavior(processTerm4,this.tinteractions);
				List<Expression> list3 = phaseBehavior.getTassi();
				if (list3 != null)
					list.addAll(list3);
				}
			else if (processTerm2 instanceof ChoiceProcess)
				{
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm2,tinteractions);
				return phaseBehavior.getTassi();
				}
			else return null;
			}
		return list;
		}
	
	// restituisce il comportamento di fase di processTerm, senza considerare i comportamenti null.
	// Restituisce null se processTerm non inizia con un comportamento di fase
	// può restituire un termine di processo con foglie uguali a null
	public ProcessTerm getPhaseBehavior()
		{
		ProcessTerm ris = null;
		// caso in cui la distribuzione è hyperesponenziale
		if (this.processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)this.processTerm;
			ris = new ChoiceProcess();
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action phaseChoiceA = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// phaseChoiceA è un'azione di scelta fase
				PrePhaseAction prePhaseAction = new PrePhaseAction(phaseChoiceA,this.tinteractions);
				if (!prePhaseAction.isPhaseChoice())
					return null;
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
								processTerm3 == null ? null : processTerm3.copy());
				// list ha taglia uguale a 1
				if (list.size() != 1)
					return null;
				ProcessTerm processTerm4 = list.get(0);
				// processTerm4 deve essere un ActionProcess con un'azione di fase
				if (!(processTerm4 instanceof ActionProcess))
					return null;
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				Action phaseAction = actionProcess2.getAzione();
				// phaseAction è un'azione di fase
				ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseAction,this.tinteractions);
				if (!expPhaseAction.isPhase())
					return null;
				ProcessTerm processTerm5 = actionProcess2.getProcesso();
				// bisogna riconoscere un eventuale comportamento null ed effettuare la differenza
				NullBehavior nullBehavior2 = new NullBehavior(
						processTerm5 == null ? null : processTerm5.copy(),this.tinteractions);
				ProcessTerm processTerm61 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm5 == null ? null : processTerm5.copy(),
								processTerm61 == null ? null : processTerm61.copy());
				// list2 ha taglia uno
				if (list2.size() != 1)
					return null;
				ProcessTerm processTerm62 = list2.get(0);
				// si richiama ricorsivamente questo metodo wrappando processTerm62
				PhaseBehavior phaseBehavior = new PhaseBehavior(
						processTerm62 == null ? null : processTerm62.copy(),this.tinteractions);
				ProcessTerm processTerm6 = phaseBehavior.getPhaseBehavior();
				// sommiamo phaseChoiceA + phaseAction + processTerm6
				ProcessTerm processTerm7 = MetodiVari.somma(phaseAction.copy(), 
						processTerm6 == null ? null : processTerm6.copy());
				processTerms2[i] = MetodiVari.somma(phaseChoiceA.copy(), 
						processTerm7 == null ? null : processTerm7.copy());
				}
			}
		// caso in cui la distribuzione è esponenziale
		// o hypoesponenziale
		if (this.processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve avere taglia uguale a 1
			if (list.size() != 1)
				return null;
			ProcessTerm processTerm2 = list.get(0);
			// possiamo avere un caso in cui c'è un comportamento null prima di uno ChoiceProcess
			// e quindi processTerm2 può non essere un ActionProcess
			if (processTerm2 instanceof ActionProcess)
				{
				ActionProcess actionProcess2 = (ActionProcess)processTerm2;
				Action phaseA = actionProcess2.getAzione();
				ProcessTerm processTerm3 = actionProcess2.getProcesso();
				// phaseA è un'azione di fase
				ExpPhaseAction expPhaseAction = new ExpPhaseAction(phaseA,this.tinteractions);
				if (!expPhaseAction.isPhase())
					return null;
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm3,this.tinteractions);
				ProcessTerm processTerm41 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm3 == null ? null : processTerm3.copy(), 
							processTerm41 == null ? null : processTerm41.copy());
				// list2 deve avere taglia 1
				if (list2.size() != 1)
					return null;
				ProcessTerm processTerm42 = list2.get(0);
				// si richiama ricorsivamente questa funzione su un wrapper di processTerm3
				ProcessTerm processTerm4 = null;
				PhaseBehavior phaseBehavior = new PhaseBehavior(
						processTerm42,this.tinteractions);
				processTerm4 = phaseBehavior.getPhaseBehavior();
				// sommiamo phaseA + processTerm4
				ProcessTerm processTerm5 = MetodiVari.somma(phaseA.copy(), 
					processTerm4 == null ? null : processTerm4.copy());
				ris = processTerm5;
				}
			else if (processTerm2 instanceof ChoiceProcess)
				{
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm2,tinteractions);
				return phaseBehavior.getPhaseBehavior(); 
				}
			else return null;
			}
		return ris;
		}
	
	// restituisce null se il comportamento non è di fase
	// restituisce una lista di espressioni effettuando una visita in profondità destra
	// dell'albero dei comportamenti.
	// l'array restituito può essere vuoto.
	public List<Expression> getPesiScelta() 
		{
		List<Expression> list = new ArrayList<Expression>();
		if (!isPhaseBehavior())
			return null;
		// caso in cui la distribuzione è hyperesponenziale
		if (this.processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)this.processTerm;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				// actionProcess ha un'azione di scelta di fase
				Action action = actionProcess.getAzione();
				// per precondizione action è un'azione di scelta di fase
				ActionRate actionRate1 = action.getRate();
				// per precondizione actionRate è un RateNoExp
				RateNoExp rateNoExp = (RateNoExp)actionRate1;
				Expression espressione1 = rateNoExp.getWeight();
				list.add(espressione1);
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(processTerm2,this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm2, processTerm3);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm4 = list2.get(0);
				// processTerm4 deve essere un ActionProcess con un'azione di fase
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				ProcessTerm processTerm5 = actionProcess2.getProcesso();
				// si riconosce un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm5,this.tinteractions);
				ProcessTerm processTerm61 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list31 = MetodiVari.differenza(processTerm5, processTerm61);
				// list3 ha taglia 1
				ProcessTerm processTerm62 = list31.get(0);
				// si richiama ricorsivamente questo metodo wrappando processTerm5
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm62,this.tinteractions);
				List<Expression> list3 = phaseBehavior.getPesiScelta();
				if (list3 != null)
					list.addAll(list3);
				}
			}
		// caso in cui la distribuzione è esponenziale
		// o hypoesponenziale
		if (this.processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia uguale a 1
			ProcessTerm processTerm2 = list2.get(0);
			// possiamo avere un caso in cui c'è un comportamento null prima di uno ChoiceProcess
			// e quindi processTerm2 può non essere un ActionProcess
			if (processTerm2 instanceof ActionProcess)
				{
				ActionProcess actionProcess2 = (ActionProcess)processTerm2;
				ProcessTerm processTerm3 = actionProcess2.getProcesso();
				// si riconosce un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm3,this.tinteractions);
				ProcessTerm processTerm41 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list31 = MetodiVari.differenza(processTerm3, processTerm41);
				// list3 deve avere taglia 1
				ProcessTerm processTerm5 = list31.get(0);
				// si richiama ricorsivamente questa funzione su un wrapper di processTerm5
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm5,this.tinteractions);
				ProcessTerm processTerm4 = phaseBehavior.getMaximalPhaseBehavior();
				phaseBehavior = new PhaseBehavior(processTerm4,this.tinteractions);
				List<Expression> list3 = phaseBehavior.getPesiScelta();
				if (list3 != null)
					list.addAll(list3);
				}
			else if (processTerm2 instanceof ChoiceProcess)
				{
				PhaseBehavior phaseBehavior = new PhaseBehavior(processTerm2,tinteractions);
				return phaseBehavior.getPesiScelta();
				}
			else return null;
			}
		return list;
		}
	}

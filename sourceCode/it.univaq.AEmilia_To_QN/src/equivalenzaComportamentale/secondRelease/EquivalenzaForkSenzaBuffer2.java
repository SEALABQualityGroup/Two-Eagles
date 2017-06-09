package equivalenzaComportamentale.secondRelease;

import java.util.List;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.Header;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateInf;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkSenzaBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ArriveAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ForkAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaForkSenzaBuffer2
	extends EquivalenzaFork2
	implements IEquivalenzaForkSenzaBuffer
	{
	
	public EquivalenzaForkSenzaBuffer2() 
		{
		super();
		}

	public EquivalenzaForkSenzaBuffer2(ElemType et) 
		{
		super();
		this.elemType = et;
		}

	public boolean isForkSenzaBuffer() 
		{
		// Il comportamento di un processo fork 
		// senza buffer è definito dalla seguente 
		// sequenza di azioni:
		// 1. un'azione di arrivo;
		// 2. un'azione di fork.
		// 3. l'unica azione di input è quella di arrivo;
		// 4. l'unica azione di output è l'azione di fork.
		// si trasforma il comportamento di this.getEt()
		// in un'unica equazione
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// processTerm deve essere un ActionProcess
		if (!(processTerm instanceof ActionProcess))
			return false;
		ActionProcess actionProcess = (ActionProcess)processTerm;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(actionProcess,tinteractions);
		ProcessTerm processTermn = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(actionProcess, processTermn);
		// list deve avere taglia 1
		if (list.size() != 1)
			return false;
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		if (!(processTerm2 instanceof ActionProcess))
			return false;
		ActionProcess actionProcess2 = (ActionProcess)processTerm2;
		Action action = actionProcess2.getAzione();
		// action deve essere un'azione di arrivo
		ArriveAction arriveAction = new ArriveAction(action,tinteractions);
		if (!arriveAction.isArrive())
			return false;
		// processTerm2 deve essere un Actionprocess
		if (!(processTerm2 instanceof ActionProcess))
			return false;
		ProcessTerm processTerm3 = actionProcess2.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm2n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm3, processTerm2n);
		// list2 deve avere taglia 1
		if (list2.size() != 1)
			return false;
		ProcessTerm processTerm4 = list2.get(0);
		// processTerm4 deve essere un ActionProcess
		if (!(processTerm4 instanceof ActionProcess))
			return false;
		ActionProcess actionProcess3 = (ActionProcess)processTerm4;
		Action action2 = actionProcess3.getAzione();
		// action2 deve essere un'azione di fork
		ForkAction forkAction = new ForkAction(action2,tinteractions);
		if (!forkAction.isFork())
			return false;
		// per precondizione action2 ha tasso RateInf
		ActionRate actionRate = action2.getRate();
		RateInf rateInf = (RateInf)actionRate;
		Expression expression = rateInf.getWeight();
		Expression espressione2 = rateInf.getPrio();
		
		// l'unica azione di input è quella di arrivo
		ActionType actionType = action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list3 = tinteractionsParts.getInputInteractions();
		if (!list3.contains(string))
			return false;
		// l'unica azione di output è l'azione di fork
		ActionType actionType2 = action2.getType();
		String string2 = actionType2.getName();
		List<String> list4 = tinteractionsParts.getOutputInteractions();
		if (!list4.contains(string2))
			return false;
		
		// imposto le azioni di consegna
		this.deliverActions = new Action[1];
		this.deliverActions[0] = action2;
		// imposto i nomi delle azioni fork
		this.forks = new String[1];
		this.forks[0] = string2;
		// imposto il numero di fork
		this.numeroForks = 1;
		// imposto i pesi
		this.pesiConsegna = new Expression[1];
		this.pesiConsegna[0] = expression;
		// imposto le priorità
		this.prioConsegna = new Expression[1];
		this.prioConsegna[0] = espressione2;
		// imposto il nome dell'azione di arrivo
		this.select = string;
		
		return true;
		}
	
	// Restituisce
	// un comportamento con le equazioni standard
	public AETbehavior getNormalizedBehavior()
		{
		if (!isForkSenzaBuffer())
			return null;
		// Il comportamento di un processo fork 
		// senza buffer è definito dalla seguente 
		// sequenza di azioni:
		// 1. un'azione di arrivo;
		// 2. un'azione di fork.
		// 3. l'unica azione di input è quella di arrivo;
		// 4. l'unica azione di output è l'azione di fork.
		// si trasforma il comportamento di this.getEt()
		// in un'unica equazione
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations ha una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// processTerm deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(actionProcess,tinteractions);
		ProcessTerm processTermn = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(actionProcess, processTermn);
		// list deve avere taglia 1
		ProcessTerm processTerm2 = list.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess2 = (ActionProcess)processTerm2;
		Action action = actionProcess2.getAzione();
		// action deve essere un'azione di arrivo
		// processTerm3 deve essere un ActionProcess
		ProcessTerm processTerm3 = actionProcess2.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm2n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm3, processTerm2n);
		// list2 deve avere taglia 1
		ProcessTerm processTerm4 = list2.get(0);
		// processTerm4 deve essere un ActionProcess
		ActionProcess actionProcess3 = (ActionProcess)processTerm4;
		Action action2 = actionProcess3.getAzione();
		// action2 deve essere un'azione di fork
		// impostiamo il comportamento risultato
		// il termine processo risultato è dato da action + action2 + (coda di processTerm)
		BehavProcess behavProcess = MetodiVari.returnTail(processTerm);
		ProcessTerm processTerm5 = MetodiVari.somma(action2, behavProcess);
		processTerm5 = MetodiVari.somma(action, processTerm5);
		Header header = behavEquation.getBehavHeader().copy();
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm5);
		BehavEquation[] behavEquations2 = new BehavEquation[1];
		behavEquations2[0] = behavEquation2;
		AETbehavior tbehavior3 = new AETbehavior(behavEquations2);
		return tbehavior3;
		}

	@Override
	public Action[] getDeliverActions() 
		{
		return this.deliverActions;
		}

	@Override
	public String[] getForks() 
		{
		return this.forks;
		}

	@Override
	public int getNumeroForks() 
		{
		return this.numeroForks;
		}

	@Override
	public Expression[] getPesiConsegna() 
		{
		return this.pesiConsegna;
		}

	@Override
	public Expression[] getPrioConsegna() 
		{
		return this.prioConsegna;
		}

	@Override
	public String getArrive() 
		{
		return this.select;
		}	

	@Override
	public boolean isEquivalente() 
		{
		return isForkSenzaBuffer();
		}
	}

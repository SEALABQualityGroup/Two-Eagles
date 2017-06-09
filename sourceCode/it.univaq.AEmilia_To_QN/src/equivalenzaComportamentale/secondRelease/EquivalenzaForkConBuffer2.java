package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
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
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ForkAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.SelectAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaForkConBuffer2
	extends EquivalenzaFork2
	implements IEquivalenzaForkConBuffer 
	{

	public EquivalenzaForkConBuffer2() 
		{
		super();
		}

	public EquivalenzaForkConBuffer2(ElemType et) 
		{
		super();
		this.elemType = et;
		}

	public boolean isForkConBuffer() 
		{
		// Il comportamento di un processo fork 
		// con buffer è definito dalla seguente 
		// sequenza di azioni:
		// 1.un'azione di selezione;
		// 2.un'azione di fork.
		// 3.l'unica azione di input è quella di selezione;
		// 4.l'unica azione di output è l'azione di fork.
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
		List<ProcessTerm> listn = MetodiVari.differenza(actionProcess, processTermn);
		// listn deve avere taglia uno
		if (listn.size() != 1)
			return false;
		processTermn = listn.get(0);
		// processTermn deve essere un ActionProcess
		if (!(processTermn instanceof ActionProcess))
			return false;
		ActionProcess actionProcessn = (ActionProcess)processTermn;
		Action action = actionProcessn.getAzione();
		// action deve essere un'azione di selezione
		SelectAction selectAction = new SelectAction(action,tinteractions);
		if (!selectAction.isSelect())
			return false;
		ProcessTerm processTerm2 = actionProcessn.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm2,tinteractions);
		ProcessTerm processTerm2n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2n = MetodiVari.differenza(processTerm2, processTerm2n);
		// list2n deve avere taglia 1
		if (list2n.size() != 1)
			return false;
		ProcessTerm processTerm3 = list2n.get(0);
		// processTerm3 deve essere un ActionProcess
		if (!(processTerm3 instanceof ActionProcess))
			return false;
		ActionProcess actionProcess2 = (ActionProcess)processTerm3;
		Action action2 = actionProcess2.getAzione();
		// action2 deve essere un'azione di fork
		ForkAction forkAction = new ForkAction(action2,tinteractions);
		if (!forkAction.isFork())
			return false;
		// per precondizione action2 deve essere un'azione immediata
		ActionRate actionRate = action2.getRate();
		RateInf rateInf = (RateInf)actionRate;
		Expression expression = rateInf.getWeight();
		Expression espressione2 = rateInf.getPrio();
		
		// l'unica azione di input è quella di selezione
		ActionType actionType = action.getType();
		String string = actionType.getName();
		List<String> list = new ArrayList<String>();
		list.add(string);
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		List<String> list2 = tinteractionsParts.getInputInteractions();
		if (!list.containsAll(list2))
			return false;
		if (!list2.containsAll(list))
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
		// imposto i nomi delle azioni di fork
		this.forks = new String[1];
		this.forks[0] = string2;
		// imposto il numero dei forks
		this.numeroForks = 1;
		// imposto i pesi
		this.pesiConsegna = new Expression[1];
		this.pesiConsegna[0] = expression;
		// imposto le priorità
		this.prioConsegna = new Expression[1];
		this.prioConsegna[0] = espressione2;
		// imposto l'azioni di selezione
		this.select = string;
		
		return true;
		}
	
	// Restituisce
	// un comportamento con le equazioni standard
	public AETbehavior getNormalizedBehavior()
		{
		if (!isForkConBuffer())
			return null;
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
		List<ProcessTerm> listn = MetodiVari.differenza(actionProcess, processTermn);
		// listn deve avere taglia uno
		ProcessTerm processTerm2 = listn.get(0);
		// processTerm2 deve essere un ActionProcess
		ActionProcess actionProcess2 = (ActionProcess)processTerm2;
		Action action = actionProcess2.getAzione();
		// action deve essere un'azione di selezione
		ProcessTerm processTerm3 = actionProcess2.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm3,tinteractions);
		ProcessTerm processTerm2n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2n = MetodiVari.differenza(processTerm3, processTerm2n);
		// list2n deve avere taglia 1
		actionProcess2 = (ActionProcess)list2n.get(0);
		Action action2 = actionProcess2.getAzione();
		// action2 deve essere un'azione di fork
		// impostiamo l'unica equazione comportamentale del comportamento risultato
		Header header = behavEquation.getBehavHeader().copy();
		// impostiamo il termine di processo da restiuire uguale a action + action2 + (coda di processTerm)
		BehavProcess behavProcess = MetodiVari.returnTail(processTerm);
		processTerm3 = MetodiVari.somma(action2, behavProcess);
		processTerm3 = MetodiVari.somma(action, processTerm3);
		BehavEquation behavEquation2 = new BehavEquation(header,processTerm3);
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
	public String getSelect() 
		{
		return this.select;
		}

	@Override
	public boolean isEquivalente() 
		{
		return isForkConBuffer();
		}
	}

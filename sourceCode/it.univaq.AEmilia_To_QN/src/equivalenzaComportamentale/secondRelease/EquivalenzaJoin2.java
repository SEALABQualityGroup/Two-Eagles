package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETbehavior;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ParamDeclaration;
import specificheAEmilia.ElemType;
import specificheAEmilia.Expression;
import specificheAEmilia.Header;
import specificheAEmilia.ProcessTerm;
import equivalenzaComportamentale.AETinteractionsParts;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.JoinAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaJoin2
	extends Equivalenza2
	implements IEquivalenzaJoin
	{
	
	private String[] chooses = null;
	private Action[] deliverActions = null;
	private String[] delivers = null;
	private String join = null;
	private Expression[] pesiConsegna = null;
	private Expression[] prioConsegna = null;
	private Expression[] probRouting = null;
	
	public EquivalenzaJoin2() 
		{
		super();
		}

	public EquivalenzaJoin2(ElemType et) 
		{
		super();
		this.elemType = et;
		}

	@Override
	public boolean isJoin() 
		{
		// Il comportamento di un processo join è definito dalla seguente sequenza di comportamenti:
		// 1. un'azione di join;
		// 2. il routing di job.
		// 3. l'unica azione di input è l'azione di join;
		// 4. le uniche azioni di output sono le azioni di consegna.
		// 5. il comportamento di routing è opzionale
		// 6. trasformiamo il comportamento in un comportamento tailRecursion
		AETinteractions tinteractions = this.getEt().getInteractions();
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations deve avere una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// processTerm deve essere un ActionProcess
		if (!(processTerm instanceof ActionProcess))
			return false;
		ActionProcess actionProcess = (ActionProcess)processTerm;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior = new NullBehavior(actionProcess,tinteractions);
		ProcessTerm processTerm2n = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(actionProcess, processTerm2n);
		// list deve avere taglia 1
		if (list.size() != 1)
			return false;
		ProcessTerm processTerm3n = list.get(0);
		// processTerm3n deve essere un ActionProcess
		if (!(processTerm3n instanceof ActionProcess))
			return false;
		ActionProcess actionProcess2n = (ActionProcess)processTerm3n;
		Action action = actionProcess2n.getAzione();
		// action deve essere un'azione di join
		JoinAction joinAction = new JoinAction(action,tinteractions);
		if (!joinAction.isJoin())
			return false;
		ActionType actionType = action.getType();
		String string = actionType.getName();
		// riconosciamo un eventuale comportamento null
		ProcessTerm processTerm2 = actionProcess2n.getProcesso();
		NullBehavior nullBehavior2 = new NullBehavior(processTerm2,tinteractions);
		ProcessTerm processTerm4n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm2, processTerm4n);
		// list2 deve avere taglia 1
		if (list2.size() != 1)
			return false;
		ProcessTerm processTerm5n = list2.get(0);
		// processTerm5n deve essere un processo di routing oppure può essere o una
		// chiamata di comportamento
		ExitBehavior exitBehavior = new ExitBehavior(processTerm5n,tinteractions);
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			{
			// le uniche azioni di output sono le azioni di consegna.
			List<String> list3 = exitBehavior.getDeliverActionNames();
			// list3 può contenere dei null
			list3 = MetodiVari.onlyNotNull(list3);
			List<String> list4 = tinteractionsParts.getOutputInteractions();
			if (!list3.containsAll(list4))
				return false;
			if (!list4.containsAll(list3))
				return false;
			// imposto i nomi delle azioni di chooses
			List<String> listChooses = exitBehavior.getChooseActionNames();
			this.chooses = new String[listChooses.size()];
			listChooses.toArray(this.chooses);
			// imposto memoria per le azioni di consegna
			List<Action> listDeliversAction = exitBehavior.getDeliverActions();
			this.deliverActions = new Action[listDeliversAction.size()];
			listDeliversAction.toArray(this.deliverActions);
			// imposto memoria per i nomi delle azioni di consegna
			List<String> listDelivers = exitBehavior.getDeliverActionNames();
			this.delivers = new String[listDeliversAction.size()];
			listDelivers.toArray(this.delivers);
			// imposto memoria per i pesi di consegna
			List<Expression> listPesi = exitBehavior.getPesiConsegna();
			this.pesiConsegna = new Expression[listPesi.size()];
			listPesi.toArray(this.pesiConsegna);
			// imposto memoria per le priorità di consegna
			List<Expression> listPrio = exitBehavior.getPrioritaConsegna();
			this.prioConsegna = new Expression[listPrio.size()];
			listPrio.toArray(this.prioConsegna);
			// imposto memoria per le probabilità di routing
			List<Expression> listProbRout = exitBehavior.getProbsRouting();
			this.probRouting = new Expression[listProbRout.size()];
			listProbRout.toArray(this.probRouting);
			}
		else if (processTerm5n instanceof BehavProcess)
			{
			// nel caso in cui ((processTerm2 instanceof BehavProcess) || processTerm2 == null)
			// bisogna impostare i campi della classe ad array vuoti
			this.chooses = new String[0];
			// imposto memoria per le azioni di consegna
			this.deliverActions = new Action[0];
			// imposto memoria per i nomi delle azioni di consegna
			this.delivers = new String[0];
			// imposto memoria per i pesi di consegna
			this.pesiConsegna = new Expression[0];
			// imposto memoria per le priorità di consegna
			this.prioConsegna = new Expression[0];
			// imposto memoria per le probabilità di routing
			this.probRouting = new Expression[0];
			}
		else return false;
		// l'unica azione di input è l'azione di join
		List<String> list3 = tinteractionsParts.getInputInteractions();
		if (!list3.contains(string))
			return false;
		// imposto memoria per il nome dell'azione di join
		this.join = string;
		return true;
		}

	@Override
	public String[] getChooses() 
		{
		return this.chooses;
		}

	@Override
	public Action[] getDeliverActions() 
		{
		return this.deliverActions;
		}

	@Override
	public String[] getDelivers() 
		{
		return this.delivers;
	}

	@Override
	public String getJoin() 
		{
		return this.join;
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
	public Expression[] getProbRouting() 
		{
		return this.probRouting;
		}

	@Override
	public boolean isEquivalente() 
		{
		return this.isJoin();
		}
	
	// Restituisce
	// un comportamento con le equazioni standard
	public AETbehavior getNormalizedBehavior()
		{
		if (!isJoin())
			return null;
		// si alloca memoria per il comportamento da restituire
		AETbehavior tbehaviorR = new AETbehavior();
		// c'è un'equazione per il join e una opzionale per il routing
		List<BehavEquation> behavEquationsR = new ArrayList<BehavEquation>();
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations deve avere una sola equazione
		BehavEquation behavEquation = behavEquations[0];
		ProcessTerm processTerm = behavEquation.getTermineProcesso();
		// per precondizione processTerm deve essere un ActionProcess
		ActionProcess actionProcess = (ActionProcess)processTerm;
		// riconosciamo un eventuale comportamento null
		AETinteractions tinteractions = this.elemType.getInteractions();
		NullBehavior nullBehavior = new NullBehavior(actionProcess,tinteractions);
		ProcessTerm processTerm2n = nullBehavior.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(actionProcess, processTerm2n);
		// list deve avere taglia 1
		if (list.size() != 1)
			return null;
		ProcessTerm processTerm3n = list.get(0);
		// processTerm3n deve essere un ActionProcess
		if (!(processTerm3n instanceof ActionProcess))
			return null;
		ActionProcess actionProcess2n = (ActionProcess)processTerm3n;
		Action action = actionProcess2n.getAzione();
		ProcessTerm processTerm21 = actionProcess2n.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm21,tinteractions);
		ProcessTerm processTerm4n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm21, processTerm4n);
		// list2 deve avere taglia uguale a 1
		if (list2.size() != 1)
			return null;
		ProcessTerm processTerm2 = list2.get(0);
		// processTerm2 deve essere un processo di routing oppure può essere una chiamata
		// comportamentale o null
		// costruiamo un'equazione per il join
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Join",declPars);
		ExitBehavior exitBehavior = new ExitBehavior(processTerm2,tinteractions);
		BehavProcess behavProcess = null;
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			behavProcess = new BehavProcess("Routing",new Expression[0]);
		else
			behavProcess = new BehavProcess("Join",new Expression[0]);
		Action action2 = action.copy();
		ActionProcess actionProcess2 = new ActionProcess(action2,behavProcess);
		BehavEquation behavEquation2 = new BehavEquation(header,actionProcess2);
		behavEquationsR.add(behavEquation2);
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			{
			// costruiamo un'equazione per il routing
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			declPars2[0] = null;
			declPars2[1] = null;
			Header intestazione2 = new Header("Routing",declPars2);
			// processTerm2c deve essere un comportamento di routig senza azioni null
			ProcessTerm processTerm2c = exitBehavior.getJobsRoutingBehavior();
			ProcessTerm processTerm3 = processTerm2c == null ? null : processTerm2c.copy();
			List<BehavProcess> list3 = computeLeaf(processTerm3);
			for (BehavProcess behavProcess3 : list3)
				{
				behavProcess3.setName("Join");
				behavProcess3.setExprs(new Expression[0]);
				}
			BehavEquation behavEquation3 = new BehavEquation(intestazione2,processTerm3);
			behavEquationsR.add(behavEquation3);
			}
		BehavEquation[] equations = new BehavEquation[behavEquationsR.size()];
		behavEquationsR.toArray(equations);
		tbehaviorR.setBehaviors(equations);
		return tbehaviorR;
		}
	}

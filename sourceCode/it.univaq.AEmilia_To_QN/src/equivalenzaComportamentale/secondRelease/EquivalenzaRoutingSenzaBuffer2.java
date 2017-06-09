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
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingSenzaBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ArriveAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.ExitBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.NullBehavior;
import equivalenzaComportamentale.secondRelease.riconoscimento.elementiBase.TailRecursion;

public class EquivalenzaRoutingSenzaBuffer2
	extends EquivalenzaRouting2
	implements IEquivalenzaRoutingSenzaBuffer 
	{
	
	public EquivalenzaRoutingSenzaBuffer2() 
		{
		super();
		}

	public EquivalenzaRoutingSenzaBuffer2(ElemType elemType) 
		{
		super();
		this.elemType = elemType;
		}

	public boolean isRoutingConBuffer() 
		{
		// Il comportamento di un processo di routing con buffer è 
		// definito dalla seguente sequenza di comportamenti:
		// 1. un'azione di arrivo;
		// 2. il routing di jobs.
		// 3. le uniche azioni di input è l'azione di arrivo;
		// 4. le uniche azioni di output sono le azioni di consegna.
		// 5. il comportamento di routing è opzionale
		AETbehavior tbehavior = this.getEt().getBehavior();
		AETinteractions tinteractions = this.getEt().getInteractions();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		if (!tailRecursion.isTailRecursion())
			return false;
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations ha una solo equazione
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
		// action deve essere un'azione di arrivo
		ArriveAction arriveAction = new ArriveAction(action,tinteractions);
		if (!arriveAction.isArrive())
			return false;
		ProcessTerm processTerm2 = actionProcess2n.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm2,tinteractions);
		ProcessTerm processTerm4n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm2, processTerm4n);
		// list2 deve avere taglia 1
		if (list2.size() != 1)
			return false;
		ProcessTerm processTerm5n = list2.get(0);
		// processTerm5n deve essere un comportamento di routing o un BehavProcess
		ExitBehavior exitBehavior = new ExitBehavior(processTerm5n,tinteractions);
		AETinteractionsParts tinteractionsParts = new AETinteractionsParts(tinteractions);
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			{
			// le uniche azioni di output sono le azioni di consegna
			// list2 può contenere dei null
			List<String> list3 = exitBehavior.getDeliverActionNames();
			list3 = MetodiVari.onlyNotNull(list3);
			List<String> list4 = tinteractionsParts.getOutputInteractions();
			if (!list3.containsAll(list4))
				return false;
			if (!(list4.containsAll(list3)))
				return false;
			// imposto i nomi delle azioni di scelta
			List<String> listChooses = exitBehavior.getChooseActionNames();
			this.chooses = new String[listChooses.size()];
			listChooses.toArray(this.chooses);
			// imposto le azioni di consegna
			List<Action> listDelivers = exitBehavior.getDeliverActions();
			this.deliverActions = new Action[listDelivers.size()];
			listDelivers.toArray(this.deliverActions);
			// imposto i nomi delle azioni di consegna
			List<String> listDelivNames = exitBehavior.getDeliverActionNames();
			this.delivers = new String[listDelivNames.size()];
			listDelivNames.toArray(this.delivers);
			// imposto i pesi di consegna
			List<Expression> listPesiConsegna = exitBehavior.getPesiConsegna();
			this.pesiConsegna = new Expression[listPesiConsegna.size()];
			listPesiConsegna.toArray(this.pesiConsegna);
			// imposto le priorità di consegna
			List<Expression> listPrioritaConsegna = exitBehavior.getPrioritaConsegna();
			this.prioConsegna = new Expression[listPrioritaConsegna.size()];
			listPrioritaConsegna.toArray(this.prioConsegna);
			// imposto le probabilità di routing
			List<Expression> listProbRouting = exitBehavior.getProbsRouting();
			this.probRouting = new Expression[listProbRouting.size()];
			listProbRouting.toArray(this.probRouting);
			}
		else if (processTerm2 instanceof BehavProcess)
			{
			// nel caso in cui (processTerm2 instanceof BehavProcess)
			// bisogna impostare i campi della classe ad array vuoti
			// imposto i nomi delle azioni di scelta
			this.chooses = new String[0];
			// imposto le azioni di consegna
			this.deliverActions = new Action[0];
			// imposto i nomi delle azioni di consegna
			this.delivers = new String[0];
			// imposto i pesi di consegna
			this.pesiConsegna = new Expression[0];
			// imposto le priorità di consegna
			this.prioConsegna = new Expression[0];
			// imposto le probabilità di routing
			this.probRouting = new Expression[0];
			}
		else return false;
		// l'unica azione di input è quella di arrivo
		ActionType actionType = action.getType();
		String string = actionType.getName();
		List<String> list5 = tinteractionsParts.getInputInteractions();
		if (!list5.contains(string))
			return false;
		// imposto il nome dell'azione di arrivo
		ActionType actionType2 = action.getType();
		String string2 = actionType2.getName();
		this.select = string2;
		
		return true;
		}

	@Override
	public boolean isEquivalente() 
		{
		return isRoutingConBuffer();
		}

	// restituisce
	// un comportamento con le equazioni standard
	public AETbehavior getNormalizedBehavior()
		{
		if (!isRoutingConBuffer())
			return null;
		// allochiamo memoria per il comportamento risultato
		AETbehavior tbehaviorR = new AETbehavior();
		// il comportamento risultato ha due equazioni
		List<BehavEquation> behavEquationsR = new ArrayList<BehavEquation>();
		AETbehavior tbehavior = this.getEt().getBehavior();
		TailRecursion tailRecursion = new TailRecursion(tbehavior);
		// preleviamo il comportamento ad un'unica equazione
		AETbehavior tbehavior2 = tailRecursion.makeOneEquation();
		BehavEquation[] behavEquations = tbehavior2.getBehaviors();
		// per precondizione behavEquations ha una sola equazione
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
		// processTerm2 deve essere un ActionProcess
		if (!(processTerm3n instanceof ActionProcess))
			return null;
		ActionProcess actionProcess2n = (ActionProcess)processTerm3n;
		Action action = actionProcess2n.getAzione();
		// per precondizione action deve essere un'azione di arrivo
		ProcessTerm processTerm2 = actionProcess2n.getProcesso();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior2 = new NullBehavior(processTerm2,tinteractions);
		ProcessTerm processTerm4n = nullBehavior2.getMaximalNullBehavior();
		List<ProcessTerm> list2 = MetodiVari.differenza(processTerm2, processTerm4n);
		// list2 deve avere taglia 1
		if (list2.size() != 1)
			return null;
		ProcessTerm processTerm5n = list2.get(0);
		// per precondizione processTerm5n deve essere un comportamento di routing,
		// null o un BehavProcess
		// impostiamo la prima equazione
		ParamDeclaration[] declPars = new ParamDeclaration[2];
		declPars[0] = null;
		declPars[1] = null;
		Header header = new Header("Arrive",declPars);
		BehavProcess behavProcess = null;
		ExitBehavior exitBehavior = new ExitBehavior(processTerm5n,tinteractions);
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			behavProcess = new BehavProcess("Routing",new Expression[0]);
		else
			behavProcess = new BehavProcess("Arrive",new Expression[0]);
		Action action2 = action.copy();
		ActionProcess actionProcess2 = new ActionProcess(action2,behavProcess);
		BehavEquation behavEquation2 = new BehavEquation(header,actionProcess2);
		behavEquationsR.add(behavEquation2);
		// impostiamo la seconda equazione
		if (exitBehavior.isJobsRoutingBehaviorWithExit() || exitBehavior.isJobsRoutingBehavior())
			{
			ParamDeclaration[] declPars2 = new ParamDeclaration[2];
			declPars2[0] = null;
			declPars2[1] = null;
			Header intestazione2 = new Header("Routing",declPars2);
			ProcessTerm processTerm2c = exitBehavior.getJobsRoutingBehavior(); 
			ProcessTerm processTerm3 = processTerm2c == null ? null : processTerm2c.copy();
			List<BehavProcess> list3 = computeLeaf(processTerm3);
			for (BehavProcess behavProcess3 : list3)
				{
				behavProcess3.setName("Arrive");
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
	public String getArrive() 
		{
		return this.select;
		}
	}

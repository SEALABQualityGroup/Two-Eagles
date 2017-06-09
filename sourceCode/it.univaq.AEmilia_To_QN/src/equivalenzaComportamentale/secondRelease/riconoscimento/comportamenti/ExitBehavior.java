package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.Boolean;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateNoExp;
import specificheAEmilia.Real;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ChooseAction;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ExitAction;

public class ExitBehavior 
	{
	
	private ProcessTerm processTerm;
	private AETinteractions tinteractions;

	public ExitBehavior(ProcessTerm processTerm,
			AETinteractions tinteractions) 
		{
		super();
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}
	
	public boolean isJobsRoutingBehavior()
		{
		// Il comportamento per il routing di jobs può essere 
		// definito in uno dei seguenti modi:
		// - due o più processi alternativi, 
		// in cui ognuno è costituito da un'azione di 
		// scelta non condizionata, seguita da un'azione 
		// di consegna.
		// - un processo costituito da un'azione di consegna.
		// - le azioni di scelta devono avere la stessa priorità;
		// - le azioni di consegna devono avere nomi distinti.
		if (!(this.processTerm instanceof ChoiceProcess || 
				this.processTerm instanceof ActionProcess))
			return false;
		// allochiamo una lista per le azioni di scelta
		List<Action> chooseList = new ArrayList<Action>();
		// allochiamo una lista per le azioni di consegna
		List<Action> deliverList = new ArrayList<Action>();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		if (list1.size() != 1)
			return false;
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			if (processTerms.length < 2)
				return false;
			for (ProcessTerm processTerm : processTerms)
				{
				if (!(processTerm instanceof ActionProcess))
					return false;
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Expression expression = actionProcess.getCondition();
				// l'espressione deve essere incondizionata
				Boolean boolean1 = new Boolean(true);
				if (!boolean1.equals(expression))
					return false;
				Action action = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// action deve essere un'azione di scelta
				ChooseAction chooseAction = new ChooseAction(action,this.tinteractions);
				if (!chooseAction.isChoose()) 
					return false;
				// preleviamo il comportamento null massimo
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
						processTerm3 == null ? null : processTerm3.copy());
				// list deve essere composta da un singolo termine di processo
				if (list.size() != 1)
					return false;
				ProcessTerm processTerm4 = list.get(0);
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				if (!(processTerm4 instanceof ActionProcess))
					return false;
				ActionProcess actionProcess2 = (ActionProcess)processTerm4;
				Action action2 = actionProcess2.getAzione();
				// action2 deve essere un'azione di consegna
				ExitAction exitAction = new ExitAction(action2,this.tinteractions);
				if (!exitAction.isDeliver())
					return false;
				chooseList.add(action);
				deliverList.add(action2);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// preleviamo il comportamento null massimo di actionProcess
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve essere una lista di taglia 1
			if (list.size() != 1)
				return false;
			ProcessTerm processTerm2 = list.get(0);
			// processTerm2 deve essere un ActionProcess
			if (!(processTerm2 instanceof ActionProcess))
				return false;
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			ExitAction exitAction = new ExitAction(action,this.tinteractions);
			if (!exitAction.isDeliver())
				return false;
			deliverList.add(action);
			}
		else return false;
		// le azioni di scelta devono avere la stessa priorità
		if (!chooseList.isEmpty())
			{
			Action action = chooseList.get(0);
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate è un RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getPrio();
			for (int i = 1; i < chooseList.size(); i++)
				{
				Action action2 = chooseList.get(i);
				ActionRate actionRate2 = action2.getRate();
				// per precondizione actionRate2 è un RateNoExp
				RateNoExp rateNoExp2 = (RateNoExp)actionRate2;
				Expression espressione2 = rateNoExp2.getPrio();
				if (!expression.equals(espressione2))
					return false;
				}
			}
		// le azioni di consegna devono avere nomi distinti
		if (!MetodiVari.distinctNames(deliverList))
			return false;
		return true;
		}
	
	public boolean isJobsRoutingBehaviorWithExit()
		{
		// Il comportamento per il routing di jobs può essere 
		// definito in uno dei seguenti modi:
		// - due o più processi alternativi, 
		// in cui ognuno è costituito da un'azione di 
		// scelta non condizionata, seguita da un'eventuale azione 
		// di consegna.
		// - un processo costituito da un'azione di consegna.
		// - le azioni di scelta devono avere la stessa priorità;
		// - le azioni di consegna devono avere nomi distinti.
		// - tra tutte le choose-action, può esserci 
		// una sola di tali azioni che non è seguita 
		// da nessuna deliver-action, che indica l'uscita 
		// dalla rete di code
		if (!(this.processTerm instanceof ChoiceProcess || 
				this.processTerm instanceof ActionProcess))
			return false;
		// allochiamo una lista per le azioni di scelta
		List<Action> chooseList = new ArrayList<Action>();
		// allochiamo una lista per le azioni di consegna
		List<Action> deliverList = new ArrayList<Action>();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		if (list1.size() != 1)
			return false;
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			if (processTerms.length < 2)
				return false;
			for (ProcessTerm processTerm : processTerms)
				{
				if (!(processTerm instanceof ActionProcess))
					return false;
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Expression expression = actionProcess.getCondition();
				// l'espressione deve essere incondizionata
				Boolean boolean1 = new Boolean(true);
				if (!boolean1.equals(expression))
					return false;
				Action action = actionProcess.getAzione();
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// action deve essere un'azione di scelta
				ChooseAction chooseAction = new ChooseAction(action,this.tinteractions);
				if (!chooseAction.isChoose()) 
					return false;
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				// o un BehavProcess, eventualmente preceduto da un comportamento null
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(
						processTerm4 == null ? null : processTerm4.copy(),this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm4 == null ? null : processTerm4.copy(), 
						processTerm5 == null ? null : processTerm5.copy());
				// list2 deve avere taglia uguale a 1
				if (list2.size() != 1) return false;
				ProcessTerm processTerm6 = list2.get(0);
				// processTerm6 può essere un ActionProcess
				if (processTerm6 instanceof ActionProcess)
					{
					ActionProcess actionProcess4 = (ActionProcess)processTerm6;
					Action action2 = actionProcess4.getAzione();
					// action2 deve essere un'azione di consegna
					ExitAction exitAction = new ExitAction(action2,this.tinteractions);
					if (!exitAction.isDeliver())
						return false;
					deliverList.add(action2);
					}
				// processTerm6 deve essere un BehavProcess, altrimenti
				// si potrebbero riconoscere comportamenti in cui 
				// processTerm6 è uno ChoiceProcess (<choose1, inf(1,0.3)>.<deliver1, inf(1,0.4)>)
				// con deliver1 che non è un'interazione
				else if (!(processTerm6 instanceof BehavProcess) && processTerm6 != null)
					return false;
				chooseList.add(action);
				}
			}
		else return false;
		// le azioni di scelta devono avere la stessa priorità
		if (!chooseList.isEmpty())
			{
			Action action = chooseList.get(0);
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate è un RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getPrio();
			for (int i = 1; i < chooseList.size(); i++)
				{
				Action action2 = chooseList.get(i);
				ActionRate actionRate2 = action2.getRate();
				// per precondizione actionRate2 è un RateNoExp
				RateNoExp rateNoExp2 = (RateNoExp)actionRate2;
				Expression espressione2 = rateNoExp2.getPrio();
				if (!expression.equals(espressione2))
					return false;
				}
			}
		// le azioni di consegna devono avere nomi distinti
		if (!MetodiVari.distinctNames(deliverList))
			return false;
		// il numero delle azioni di consegna deve essere uguale 
		// al numero delle azioni di scelta - 1 se il numero delle azioni
		// di scelta è maggiore di 0
		int i = chooseList.size();
		int j = deliverList.size();
		if (i > 0)
			{
			if (!(i - 1 == j))
				return false;
			}
		// il numero delle azioni di consegna deve essere uguale a 1 se il numero delle azioni di
		// scelta è 0
		if (i == 0)
			{
			if (!(j == 1))
				return true;
			}
		return true;
		}

	// restituisce il comportamento di routing massimo di processTerm.
	// Restituisce null se processTerm non inizia con un comportamento di routing.
	// Può restituire un termine di processo con foglie uguali a null.
	public ProcessTerm getMaximalJobsRoutingBehavior()
		{
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// Il comportamento per il routing di jobs può essere 
		// definito in uno dei seguenti modi:
		// - due o più processi alternativi, 
		// in cui ognuno è costituito da un'azione di 
		// scelta non condizionata, seguita da un'eventuale azione 
		// di consegna.
		// - un processo costituito da un'azione di consegna.
		// - le azioni di scelta devono avere la stessa priorità;
		// - le azioni di consegna devono avere nomi distinti.
		ProcessTerm processTermR = null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			processTermR = new ChoiceProcess();
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)processTermR).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// per precondizione processTerm è un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// action deve essere un'azione di scelta
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
						processTerm3 == null ? null : processTerm3.copy());
				// list deve avere taglia uguale a 1
				ProcessTerm processTerm4 = list.get(0);
				// si imposta all'i-esimo termine di processo di processTerms2
				ProcessTerm processTerm5 = MetodiVari.somma(action.copy(), 
						processTerm3 == null ? null : processTerm3.copy());
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna o un BehavProcess, eventualmente preceduto da
				// un comportamento null
				if (processTerm4 instanceof ActionProcess)
					{
					ActionProcess actionProcess2 = (ActionProcess)processTerm4;
					Action action2 = actionProcess2.getAzione();
					ProcessTerm processTerm6 = actionProcess2.getProcesso();
					// action2 deve essere un'azione di consegna
					// riconosciamo un eventuale comportamento null
					NullBehavior nullBehavior2 = new NullBehavior(
							processTerm6 == null ? null : processTerm6.copy(),this.tinteractions);
					ProcessTerm processTerm7 = nullBehavior2.getMaximalNullBehavior();
					// sommiamo l'azione di exit con processTerm6
					ProcessTerm processTerm8 = MetodiVari.somma(action2.copy(), 
							processTerm7 == null ? null : processTerm7.copy());
					// sommiamo processTerm5 a processTerm8 e impostiamo l'i-esimo termine di
					// processo di processTerms2
					ProcessTerm processTerm9 = MetodiVari.somma(
							processTerm5 == null ? null : processTerm5.copy(), 
							processTerm8 == null ? null : processTerm8.copy());
					processTerms2[i] = processTerm9;
					}
				else
					processTerms2[i] = processTerm5;
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm4 = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm4 == null ? null : processTerm4.copy());
			// la taglia di list2 è uguale a 1
			ProcessTerm processTerm5 = list2.get(0);
			// per precondizione processTerm5 è un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm5;	
			Action action = actionProcess2.getAzione();
			ProcessTerm processTerm6 = actionProcess2.getProcesso(); 
			// action deve essere un'azione di consegna
			// riconosciamo un'altro eventuale comportamento null
			NullBehavior nullBehavior2 = new NullBehavior(
					processTerm6 == null ? null : processTerm6.copy(),this.tinteractions);
			ProcessTerm processTerm7 = nullBehavior2.getMaximalNullBehavior();
			// si imposta il termine di processo risultato
			ProcessTerm processTerm8 = MetodiVari.somma(action.copy(), 
					processTerm7 == null ? null : processTerm7.copy());
			// sommiamo processTerm4 a processTerm8
			processTermR = MetodiVari.somma(
					processTerm4 == null ? null : processTerm4.copy(), 
					processTerm8 == null ? null : processTerm8.copy());
			}
		else return null;
		// sommiamo a processTerm1 processTermR
		processTermR = MetodiVari.somma(processTerm1, processTermR);
		return processTermR;
		}
	
	// restituisce i nomi delle azioni di consegna.
	// restituisce null se il comportamento non è un processo di
	// routing di jobs
	// restituisce un array non vuoto.
	public List<String> getDeliverActionNames()
		{
		List<String> list = new ArrayList<String>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				// o un BehavProcess, eventualmente preceduto da un comportamento null
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm4,this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm4, processTerm5);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm6 = list2.get(0);
				// processTerm6 può essere un ActionProcess
				if (processTerm6 instanceof ActionProcess)
					{
					ActionProcess actionProcess4 = (ActionProcess)processTerm6;
					Action action2 = actionProcess4.getAzione();
					// action2 deve essere un'azione di consegna
					ActionType actionType = action2.getType();
					String string = actionType.getName();
					list.add(string);
					}
				else
					list.add(null);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia 1
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			ActionType actionType = action.getType();
			String string = actionType.getName();
			list.add(string);
			}
		else return null;
		return list;
		}
	
	// restituisce i nomi delle azioni di choose.
	// restituisce null se il comportamento non è un processo di
	// routing di jobs
	public List<String> getChooseActionNames()
		{
		List<String> list = new ArrayList<String>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di scelta
				ActionType actionType = action.getType();
				String string = actionType.getName();
				list.add(string);
				}
			}
		return list;
		}
	
	// restituisce le azioni di consegna.
	// restituisce null se il comportamento non è un processo di
	// routing di jobs
	public List<Action> getDeliverActions()
		{
		List<Action> list = new ArrayList<Action>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				// o un BehavProcess, eventualmente preceduto da un comportamento null
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm4,this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm4, processTerm5);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm6 = list2.get(0);
				// processTerm6 può essere un ActionProcess
				if (processTerm6 instanceof ActionProcess)
					{
					ActionProcess actionProcess4 = (ActionProcess)processTerm6;
					Action action2 = actionProcess4.getAzione();
					// action2 deve essere un'azione di consegna
					list.add(action2);
					}
				else
					list.add(null);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia 1
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			list.add(action);
			}
		else return null;
		return list;
		}

	public List<Expression> getPesiConsegna() 
		{
		List<Expression> list = new ArrayList<Expression>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				// o un BehavProcess, eventualmente preceduto da un comportamento null
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm4,this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm4, processTerm5);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm6 = list2.get(0);
				// processTerm6 può essere un ActionProcess
				if (processTerm6 instanceof ActionProcess)
					{
					ActionProcess actionProcess4 = (ActionProcess)processTerm6;
					Action action2 = actionProcess4.getAzione();
					// action2 deve essere un'azione di consegna
					ActionRate actionRate = action2.getRate();
					// per precondizione actionRate deve essere RateNoExp
					RateNoExp rateNoExp = (RateNoExp)actionRate;
					Expression expression = rateNoExp.getWeight();
					list.add(expression);
					}
				else
					list.add(null);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia 1
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate deve essere RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getWeight();
			list.add(expression);
			}
		else return null;
		return list;
		}

	public List<Expression> getPrioritaConsegna() 
		{
		List<Expression> list = new ArrayList<Expression>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			// il numero di processi deve essere maggiore o uguale a due
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna
				// o un BehavProcess, eventualmente preceduto da un comportamento null
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(processTerm4,this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(processTerm4, processTerm5);
				// list2 deve avere taglia uguale a 1
				ProcessTerm processTerm6 = list2.get(0);
				// processTerm6 può essere un ActionProcess
				if (processTerm6 instanceof ActionProcess)
					{
					ActionProcess actionProcess4 = (ActionProcess)processTerm6;
					Action action2 = actionProcess4.getAzione();
					// action2 deve essere un'azione di consegna
					ActionRate actionRate = action2.getRate();
					// per precondizione actionRate deve essere RateNoExp
					RateNoExp rateNoExp = (RateNoExp)actionRate;
					Expression expression = rateNoExp.getPrio();
					list.add(expression);
					}
				else
					list.add(null);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(actionProcess,this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(actionProcess, processTerm);
			// list deve avere taglia 1
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate deve essere RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getPrio();
			list.add(expression);
			}
		else return null;
		return list;
		}

	public List<Expression> getProbsRouting() 
		{
		List<Expression> list = new ArrayList<Expression>();
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action2 = actionProcess.getAzione();
				ActionRate actionRate = action2.getRate();
				// per precondizione actionRate deve essere RateNoExp
				RateNoExp rateNoExp = (RateNoExp)actionRate;
				Expression expression = rateNoExp.getWeight();
				list.add(expression);
				}
			}
		else
			list.add(new Real(1));
		return list;
		}
	
	// restituisce il comportamento di routing di processTerm.
	// Restituisce null se processTerm non inizia con un comportamento di routing.
	// Può restituire un termine di processo con foglie uguali a null.
	// Gli eventuali comportamenti null vengono eliminati dal termine processo risultato.
	public ProcessTerm getJobsRoutingBehavior()
		{
		if (!(isJobsRoutingBehavior() || isJobsRoutingBehaviorWithExit()))
			return null;
		// Il comportamento per il routing di jobs può essere 
		// definito in uno dei seguenti modi:
		// - due o più processi alternativi, 
		// in cui ognuno è costituito da un'azione di 
		// scelta non condizionata, seguita da un'eventuale azione 
		// di consegna.
		// - un processo costituito da un'azione di consegna.
		// - le azioni di scelta devono avere la stessa priorità;
		// - le azioni di consegna devono avere nomi distinti.
		ProcessTerm processTermR = null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			processTermR = new ChoiceProcess();
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)processTermR).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// per precondizione processTerm è un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				ProcessTerm processTerm2 = actionProcess.getProcesso();
				// action deve essere un'azione di scelta
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm2 == null ? null : processTerm2.copy(),this.tinteractions);
				ProcessTerm processTerm3 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm2 == null ? null : processTerm2.copy(), 
						processTerm3 == null ? null : processTerm3.copy());
				// list deve avere taglia uguale a 1
				ProcessTerm processTerm4 = list.get(0);
				// si imposta all'i-esimo termine di processo di processTerms2
				ProcessTerm processTerm5 = MetodiVari.somma(action.copy(), null);
				// per precondizione processTerm4 deve essere un ActionProcess
				// con un'azione di consegna o un BehavProcess, eventualmente preceduto da
				// un comportamento null
				if (processTerm4 instanceof ActionProcess)
					{
					ActionProcess actionProcess2 = (ActionProcess)processTerm4;
					Action action2 = actionProcess2.getAzione();
					// action2 deve essere un'azione di consegna
					// sommiamo l'azione di exit con processTerm6
					ProcessTerm processTerm8 = MetodiVari.somma(action2.copy(), null);
					// sommiamo processTerm5 a processTerm8 e impostiamo l'i-esimo termine di
					// processo di processTerms2
					ProcessTerm processTerm9 = MetodiVari.somma(
							processTerm5 == null ? null : processTerm5.copy(), 
							processTerm8 == null ? null : processTerm8.copy());
					processTerms2[i] = processTerm9;
					}
				else
					processTerms2[i] = processTerm5;
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					actionProcess == null ? null : actionProcess.copy(),this.tinteractions);
			ProcessTerm processTerm4 = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm4 == null ? null : processTerm4.copy());
			// la taglia di list2 è uguale a 1
			ProcessTerm processTerm5 = list2.get(0);
			// per precondizione processTerm5 è un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm5;	
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di consegna
			// si imposta il termine di processo risultato
			ProcessTerm processTerm8 = MetodiVari.somma(action.copy(), null);
			// sommiamo processTerm4 a processTerm8
			processTermR = processTerm8;
			}
		else return null;
		return processTermR;
		}
	}

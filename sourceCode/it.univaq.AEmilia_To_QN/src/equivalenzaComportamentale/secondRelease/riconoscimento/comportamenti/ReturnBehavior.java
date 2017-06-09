package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionType;
import specificheAEmilia.Boolean;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ReturnAction;

public class ReturnBehavior 
	{

	private ProcessTerm processTerm;
	private AETinteractions tinteractions;

	public ReturnBehavior(ProcessTerm processTerm,
			AETinteractions tinteractions) 
		{
		super();
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}
	
	public boolean isReturnBehavior()
		{
		// Il comportamento per il ritorno di un job può 
		// essere definito in uno dei seguenti modi:
		// - due o più processi alternativi, in cui ognuno è 
		// costituito da un'azione di ritorno non 
		// condizionata.
		// - un processo costituito da un'azione di ritorno.
		// Nota:
		// - le azioni di ritorno devono avare nomi distinti.
		if (!(this.processTerm instanceof ChoiceProcess || this.processTerm instanceof ActionProcess))
			return false;
		// allochiamo memoria per le azioni di ritorno
		List<Action> returnList = new ArrayList<Action>();
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
			for (ProcessTerm processTerm : processTerms)
				{
				// processTerm deve avere condizione di esecuzione uguale a true
				Expression expression = processTerm.getCondition();
				Boolean boolean1 = new Boolean(true);
				if (!expression.equals(boolean1))
					return false;
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
								processTerm2 == null ? null : processTerm2.copy());
				// list deve avere taglia 1
				if (list.size() != 1)
					return false;
				ProcessTerm processTerm3 = list.get(0);
				// processTerm3 deve essere un ActionProcess
				if (!(processTerm3 instanceof ActionProcess))
					return false;
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di ritorno
				ReturnAction return1 = new ReturnAction(action,this.tinteractions);
				if (!return1.isReturn())
					return false;
				returnList.add(action);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve avere tagli uguale a 1
			if (list.size() != 1)
				return false;
			ProcessTerm processTerm2 = list.get(0);
			// processTerm2 deve essere un ActionProcess
			if (!(processTerm2 instanceof ActionProcess))
				return false;
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			ReturnAction return1 = new ReturnAction(action,this.tinteractions);
			if (!return1.isReturn())
				return false;
			returnList.add(action);
			}
		else return false;
		// le azioni di ritorno devono avere nomi distinti
		// per precondizione returnList è non vuota
		if (!MetodiVari.distinctNames(returnList))
			return false;
		return true;
		}
	
	// restituisce i nomi delle azioni di ritorno.
	// restituisce null se il comportamento non è un processo di
	// routing di ritorno di jobs
	public List<String> getReturnActionNames()
		{
		List<String> list = new ArrayList<String>();
		if (!isReturnBehavior())
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
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
								processTerm2 == null ? null : processTerm2.copy());
				// list deve avere taglia 1
				ProcessTerm processTerm3 = list2.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di ritorno
				ActionType actionType = action.getType();
				String string = actionType.getName();
				list.add(string);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list deve avere tagli uguale a 1
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di ritorno
			ActionType actionType = action.getType();
			String string = actionType.getName();
			list.add(string);
			}
		else return null;
		return list;
		}
	
	public ProcessTerm getMaximalReturnBehavior()
		{
		if (!isReturnBehavior())
			return null;
		ProcessTerm ris = null;
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
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			ris = new ChoiceProcess();
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
					processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
					processTerm == null ? null : processTerm.copy(), 
							processTerm2 == null ? null : processTerm2.copy());
				// list deve avere taglia 1
				ProcessTerm processTerm3 = list.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di ritorno
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior2 = new NullBehavior(
						processTerm4 == null ? null : processTerm4.copy(),this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				// sommiamo processTerm2 + action + processTerm5
				processTerms2[i] = MetodiVari.somma(action, processTerm5);
				processTerms2[i] = MetodiVari.somma(processTerm2, processTerms2[i]);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ris = new ActionProcess();
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
				processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
				actionProcess == null ? null : actionProcess.copy(), 
				processTerm == null ? null : processTerm.copy());
			// list deve avere tagli uguale a 1
			ProcessTerm processTerm2 = list.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di ritorno
			ProcessTerm processTerm3 = actionProcess2.getProcesso();
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior2 = new NullBehavior(
					processTerm3 == null ? null : processTerm3.copy(),this.tinteractions);
			ProcessTerm processTerm4 = nullBehavior2.getMaximalNullBehavior();
			// sommiamo processTerm + action + processTerm4
			ris = MetodiVari.somma(action, processTerm4);
			ris = MetodiVari.somma(processTerm, ris);
			}
		else return null;
		// sommiamo a ris processTerm1
		ris = MetodiVari.somma(processTerm1, ris);
		return ris;
		}

	public ProcessTerm getReturnBehavior()
		{
		if (!isReturnBehavior())
			return null;
		ProcessTerm ris = null;
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
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			ris = new ChoiceProcess();
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// riconosciamo un eventuale comportamento null
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
						processTerm2 == null ? null : processTerm2.copy());
				// list deve avere taglia 1
				ProcessTerm processTerm3 = list.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di ritorno
				// impostiamo ris come un ActionProcess con azione action
				processTerms2[i] = MetodiVari.somma(action, null);				
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm21;
			// riconosciamo un eventuale comportamento null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list = MetodiVari.differenza(
					actionProcess == null ? null : actionProcess.copy(), 
							processTerm == null ? null : processTerm.copy());
			// list deve avere tagli uguale a 1
			ProcessTerm processTerm2 = list.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess2 = (ActionProcess)processTerm2;
			Action action = actionProcess2.getAzione();
			// action deve essere un'azione di ritorno
			// sommiamo action + null, ottenendo un ActionProcess con action come azione
			ris = MetodiVari.somma(action, null);
			}
		else return null;
		return ris;
		}
	}

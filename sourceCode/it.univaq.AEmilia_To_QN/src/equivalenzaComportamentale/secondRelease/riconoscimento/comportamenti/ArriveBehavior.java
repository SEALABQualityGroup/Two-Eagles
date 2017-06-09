package equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti;

import java.util.ArrayList;
import java.util.List;

import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Boolean;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.Expression;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.RateNoExp;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.secondRelease.riconoscimento.azioni.ArriveAction;

public class ArriveBehavior 
	{
		
	private ProcessTerm processTerm;
	private AETinteractions tinteractions;

	public ArriveBehavior(ProcessTerm processTerm,
			AETinteractions tinteractions) 
		{
		super();
		this.processTerm = processTerm;
		this.tinteractions = tinteractions;
		}
	
	/*
	 * Nella seguente verifica non consideriamo le azioni null
	 */
	public boolean isArrivalBehavior()
		{
		// Il comportamento di arrivi di jobs è 
		// formato da uno o più processi alternativi, 
		// in cui ognuno di tali processi è costituito 
		// da un'azione di arrivo di jobs non condizionata
		if (!(this.processTerm instanceof ActionProcess || 
				this.processTerm instanceof ChoiceProcess))
			return false;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list deve avere taglia 1
		if (list1.size() != 1)
			return false;
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm2 deve essere un ActionProcess o uno ChoiceProcess
		// si alloca memoria per le azioni di arrivo
		List<Action> list = new ArrayList<Action>();
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				// la condizione di processTerm deve essere true
				Boolean boolean1 = new Boolean(true);
				Expression expression = processTerm.getCondition();
				if (!expression.equals(boolean1))
					return false;
				// preleviamo il comportamento null massimo
				NullBehavior nullBehavior = new NullBehavior(processTerm,this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
						processTerm2 == null ? null : processTerm2.copy());
				// list2 deve essere composto da un unico termine di processo
				if (list2.size() != 1)
					return false;
				ProcessTerm processTerm3 = list2.get(0);
				// processTerm3 deve essere un ActionProcess
				if (!(processTerm3 instanceof ActionProcess))
					return false;
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di arrivo
				ArriveAction arriveAction = new ArriveAction(action,this.tinteractions);
				if (!arriveAction.isArrive())
					return false;
				list.add(action);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// la condizione di actionProcess deve essere true
			Boolean boolean1 = new Boolean(true);
			Expression expression = processTerm21.getCondition();
			if (!boolean1.equals(expression))
				return false;
			// action può iniziare con un'azione di arrivo o un'azione null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					processTerm21 == null ? null : processTerm21.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list2 deve essere definita da soltanto un termine di processo
			// e tale termine di processo deve iniziare con un'azione Arrive
			if (list2.size() != 1)
				return false;
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			if (!(processTerm2 instanceof ActionProcess))
				return false;
			ActionProcess actionProcess = (ActionProcess)processTerm2;
			Action action = actionProcess.getAzione();
			// action deve essere un'azione di arrivo
			ArriveAction arriveAction = new ArriveAction(action,this.tinteractions);
			if (!arriveAction.isArrive())
				return false;
			list.add(action);
			}
		else return false;
		// Le azioni di arrivo devono avare nomi distinti
		if (!MetodiVari.distinctNames(list))
			return false;
		return true;
		}
	
	// restituisce i nomi delle azioni 
	// di arrivo. Restituisce null se processTerm non è un comportamento di arrivi.
	public List<String> getArriveNames()
		{
		if (!isArrivalBehavior())
			return null;
		// si alloca memoria per le azioni di arrivo
		List<String> list = new ArrayList<String>();
		// Il comportamento di arrivi di jobs è 
		// formato da uno o più processi alternativi, 
		// in cui ognuno di tali processi è costituito 
		// da un'azione di arrivo di jobs non condizionata
		// si riconosce un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list21 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list2 deve avere taglia 1
		ProcessTerm processTerm21 = list21.get(0);
		// processTerm2 deve essere un ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				// preleviamo il comportamento null massimo
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
						processTerm2 == null ? null : processTerm2.copy());
				// list2 deve essere composto da un unico termine di processo
				ProcessTerm processTerm3 = list2.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di arrivo
				ActionType actionType = action.getType();
				String string = actionType.getName();
				list.add(string);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// action può iniziare con un'azione di arrivo o un'azione null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					processTerm21 == null ? null : processTerm21.copy(), 
							processTerm == null ? null : processTerm.copy());
			// per precondizione list2 è definita da soltanto un termine di processo
			// e inizia con un'azione Arrive
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 è un ActionProcess
			ActionProcess actionProcess = (ActionProcess)processTerm2;
			Action action = actionProcess.getAzione();
			ActionType actionType = action.getType();
			String string = actionType.getName();
			list.add(string);
			}
		else return null;
		return list;
		}
	
	// restituisce null se processTerm non rappresenta un comportamento di arrivo
	public ProcessTerm getMaximalArrivalBehavior()
		{
		// Il comportamento di arrivi di jobs è 
		// formato da uno o più processi alternativi, 
		// in cui ognuno di tali processi è costituito 
		// da un'azione di arrivo di jobs non condizionata
		if (!isArrivalBehavior())
			return null;
		// si alloca memoria per il termine di processo risultato
		ProcessTerm ris = null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(this.processTerm, processTerm1);
		// list ha taglia 1
		ProcessTerm processTerm21 = list.get(0);
		// processTerm21 deve essere uno ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ris = new ChoiceProcess();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// preleviamo il comportamento null massimo
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
						processTerm2 == null ? null : processTerm2.copy());
				// list2 deve essere composto da un unico termine di processo
				ProcessTerm processTerm3 = list2.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di arrivo
				ProcessTerm processTerm4 = actionProcess.getProcesso();
				// preleviamo il comportamento null massimo di processTerm4
				NullBehavior nullBehavior2 = new NullBehavior(
						processTerm4 == null ? null : processTerm4.copy(),this.tinteractions);
				ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
				// l'i-esimo comportamento di processTerms2 da impostare è dato dalla
				// concatenazione processTerm2 + action + processTerm5
				ProcessTerm processTerm6 = MetodiVari.somma(action.copy(), 
						processTerm5 == null ? null : processTerm5.copy());
				ProcessTerm processTerm7 = MetodiVari.somma(
						processTerm2 == null ? null : processTerm2.copy(), 
						processTerm6 == null ? null : processTerm6.copy());
				processTerms2[i] = processTerm7;
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// action può iniziare con un'azione di arrivo o un'azione null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					processTerm21 == null ? null : processTerm21.copy(), 
					processTerm == null ? null : processTerm.copy());
			// list2 deve essere definita da soltanto un termine di processo
			// e tale termine di processo deve iniziare con un'azione Arrive
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess = (ActionProcess)processTerm2;
			Action action = actionProcess.getAzione();
			// action deve essere un'azione di arrivo
			ProcessTerm processTerm4 = actionProcess.getProcesso();
			// preleviamo il comportamento null massimo di processTerm4
			NullBehavior nullBehavior2 = new NullBehavior(
					processTerm4 == null ? null : processTerm4.copy(),this.tinteractions);
			ProcessTerm processTerm5 = nullBehavior2.getMaximalNullBehavior();
			// il comportamento da restituire è dato dalla
			// concatenazione processTerm + action + processTerm5
			ProcessTerm processTerm6 = MetodiVari.somma(action.copy(), 
					processTerm5 == null ? null : processTerm5.copy());
			ris = MetodiVari.somma(
					processTerm == null ? null : processTerm.copy(), 
					processTerm6 == null ? null : processTerm6.copy());
			}
		else return null;
		// sommiamo a ris processTerm1
		ris = MetodiVari.somma(processTerm1, ris);
		return ris;
		}

	// restituisce il comportamento di arrivo senza azioni null
	public ProcessTerm getArrivalBehavior()
		{
		// Il comportamento di arrivi di jobs è 
		// formato da uno o più processi alternativi, 
		// in cui ognuno di tali processi è costituito 
		// da un'azione di arrivo di jobs non condizionata
		if (!isArrivalBehavior())
			return null;
		// si alloca memoria per il termine di processo risultato
		ProcessTerm ris = null;
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list = MetodiVari.differenza(this.processTerm, processTerm1);
		// list ha taglia 1
		ProcessTerm processTerm21 = list.get(0);
		// processTerm21 deve essere uno ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			ris = new ChoiceProcess();
			ProcessTerm[] processTerms2 = new ProcessTerm[processTerms.length];
			((ChoiceProcess)ris).setProcesses(processTerms2);
			for (int i = 0; i < processTerms.length; i++)
				{
				ProcessTerm processTerm = processTerms[i];
				// preleviamo il comportamento null massimo
				NullBehavior nullBehavior = new NullBehavior(
						processTerm == null ? null : processTerm.copy(),this.tinteractions);
				ProcessTerm processTerm2 = nullBehavior.getMaximalNullBehavior();
				List<ProcessTerm> list2 = MetodiVari.differenza(
						processTerm == null ? null : processTerm.copy(), 
						processTerm2 == null ? null : processTerm2.copy());
				// list2 deve essere composto da un unico termine di processo
				ProcessTerm processTerm3 = list2.get(0);
				// processTerm3 deve essere un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm3;
				Action action = actionProcess.getAzione();
				// action deve essere un'azione di arrivo
				processTerms2[i] = new ActionProcess();
				((ActionProcess)processTerms2[i]).setAzione(action);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// action può iniziare con un'azione di arrivo o un'azione null
			NullBehavior nullBehavior = new NullBehavior(
					processTerm21 == null ? null : processTerm21.copy(),this.tinteractions);
			ProcessTerm processTerm = nullBehavior.getMaximalNullBehavior();
			List<ProcessTerm> list2 = MetodiVari.differenza(
					processTerm21 == null ? null : processTerm21.copy(), 
							processTerm == null ? null : processTerm.copy());
			// list2 deve essere definita da soltanto un termine di processo
			// e tale termine di processo deve iniziare con un'azione Arrive
			ProcessTerm processTerm2 = list2.get(0);
			// processTerm2 deve essere un ActionProcess
			ActionProcess actionProcess = (ActionProcess)processTerm2;
			Action action = actionProcess.getAzione();
			// action deve essere un'azione di arrivo
			ris = new ActionProcess();
			((ActionProcess)ris).setAzione(action);
			}
		else return null;
		return ris;
		}

	public List<Expression> getPrioSelezione()
		{
		if (!isArrivalBehavior())
			return null;
		// la prima azione null determina una priorità di selezione
		List<Expression> list = new ArrayList<Expression>();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list ha taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere uno ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				// processTerm è un ActionProcess
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				ActionRate actionRate = action.getRate();
				// per precondizione actionRate è RateNoExp
				RateNoExp rateNoExp = (RateNoExp)actionRate;
				Expression expression = rateNoExp.getPrio();
				list.add(expression);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// processTerm21 è un ActionProcess
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			Action action = actionProcess.getAzione();
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate deve essere RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getPrio();
			list.add(expression);
			}
		else return null;
		return list;
		}

	public List<Expression> getProbSelezione()
		{
		if (!isArrivalBehavior())
			return null;
		// la prima azione null determina una priorità di selezione
		List<Expression> list = new ArrayList<Expression>();
		// riconosciamo un eventuale comportamento null
		NullBehavior nullBehavior1 = new NullBehavior(this.processTerm,this.tinteractions);
		ProcessTerm processTerm1 = nullBehavior1.getMaximalNullBehavior();
		List<ProcessTerm> list1 = MetodiVari.differenza(this.processTerm, processTerm1);
		// list ha taglia 1
		ProcessTerm processTerm21 = list1.get(0);
		// processTerm21 deve essere uno ActionProcess o uno ChoiceProcess
		if (processTerm21 instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm21;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				ActionProcess actionProcess = (ActionProcess)processTerm;
				Action action = actionProcess.getAzione();
				ActionRate actionRate = action.getRate();
				// per precondizione actionRate è RateNoExp
				RateNoExp rateNoExp = (RateNoExp)actionRate;
				Expression expression = rateNoExp.getWeight();
				list.add(expression);
				}
			}
		else if (processTerm21 instanceof ActionProcess)
			{
			// processTerm21 è un ActionProcess
			ActionProcess actionProcess = (ActionProcess)this.processTerm;
			Action action = actionProcess.getAzione();
			ActionRate actionRate = action.getRate();
			// per precondizione actionRate deve essere RateNoExp
			RateNoExp rateNoExp = (RateNoExp)actionRate;
			Expression expression = rateNoExp.getWeight();
			list.add(expression);
			}
		else return null;
		return list;
		}
	}

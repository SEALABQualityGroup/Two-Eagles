package equivalenzaComportamentale.secondRelease;

import java.util.ArrayList;
import java.util.List;

import equivalenzaComportamentale.interfaces.IEquivalenza;

import specificheAEmilia.AEIdecl;
import specificheAEmilia.AETbehavior;
import specificheAEmilia.Action;
import specificheAEmilia.ActionProcess;
import specificheAEmilia.ActionType;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.BehavProcess;
import specificheAEmilia.ChoiceProcess;
import specificheAEmilia.ElemType;
import specificheAEmilia.ProcessTerm;
import specificheAEmilia.Stop;

public abstract class Equivalenza2
	implements IEquivalenza
	{

	protected ElemType elemType;
	protected AEIdecl aEIdecl = null;

	protected List<BehavProcess> computeLeaf(ProcessTerm processTerm2) {
	List<BehavProcess> list = new ArrayList<BehavProcess>();
	// se processTerm2 � ActionProcess e il suo processo � o null o una
	// chiamata di comportamento si imposta un BehavProcess come processo di processTerm2 
	if (processTerm2 instanceof ActionProcess)
		{
		ActionProcess actionProcess = (ActionProcess)processTerm2;
		ProcessTerm processTerm3 = actionProcess.getProcesso();
		if (processTerm3 instanceof BehavProcess)
			{
			BehavProcess behavProcess = (BehavProcess)processTerm3;
			list.add(behavProcess);
			}
		if (processTerm3 == null)
			{
			BehavProcess behavProcess = new BehavProcess();
			actionProcess.setProcesso(behavProcess);
			list.add(behavProcess);
			}
		if (processTerm3 instanceof ActionProcess)
			return computeLeaf(processTerm3);
		if (processTerm3 instanceof ChoiceProcess)
			{
			// chiamiamo computLeaf su ogni termine di processo
			// alternativo
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm3;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm : processTerms)
				{
				List<BehavProcess> list2 = computeLeaf(processTerm);
				list.addAll(list2);
				}
			}
		}
	// se processTerm2 � ChoiceProcess chiamiamo computLeaf su ogni termine di processo
	// alternativo
	if (processTerm2 instanceof ChoiceProcess)
		{
		ChoiceProcess choiceProcess = (ChoiceProcess)processTerm2;
		ProcessTerm[] processTerms = choiceProcess.getProcesses();
		for (ProcessTerm processTerm3 : processTerms)
			{
			List<BehavProcess> list2 = computeLeaf(processTerm3);
			list.addAll(list2);
			}
		}
	// se processTerm2 � BehavProcess, lo aggiungiamo a list
	if (processTerm2 instanceof BehavProcess)
		list.add((BehavProcess)processTerm2);
	return list;
	}
	
	@Override
	public Action getActionFromName(String string) 
		{
		if (!isEquivalente())
			return null;
		Action ris = null;
		AETbehavior tbehavior = this.elemType.getBehavior();
		BehavEquation[] behavEquations = tbehavior.getBehaviors();
		for (BehavEquation behavEquation : behavEquations)
			{
			ProcessTerm processTerm = behavEquation.getTermineProcesso();
			ris = findActionFromName(string,processTerm);
			if (ris != null)
				return ris;
			}
		return null;
		}

	@Override
	public AEIdecl getAEIdecl() 
		{
		return this.aEIdecl;
		}

	@Override
	public ElemType getEt() 
		{
		return this.elemType;
		}

	@Override
	public void setAEIdecl(AEIdecl idecl) 
		{
		this.aEIdecl = idecl;
		}

	@Override
	public void setEt(ElemType et) 
		{
		this.elemType = et;
		}

	private Action findActionFromName(String string, ProcessTerm processTerm) 
		{
		if (processTerm instanceof Stop)
			return null;
		if (processTerm instanceof ActionProcess)
			{
			ActionProcess actionProcess = (ActionProcess)processTerm;
			Action action = actionProcess.getAzione();
			ActionType actionType = action.getType();
			String string2 = actionType.getName();
			if (string.equals(string2))
				return action;
			ProcessTerm processTerm2 = actionProcess.getProcesso();
			return findActionFromName(string, processTerm2);
			}
		if (processTerm instanceof BehavProcess)
			return null;
		if (processTerm instanceof ChoiceProcess)
			{
			ChoiceProcess choiceProcess = (ChoiceProcess)processTerm;
			ProcessTerm[] processTerms = choiceProcess.getProcesses();
			for (ProcessTerm processTerm2 : processTerms)
				{
				Action action = findActionFromName(string, processTerm2);
				if (action != null) return action;
				}
			}
		return null;
		}
	
	}

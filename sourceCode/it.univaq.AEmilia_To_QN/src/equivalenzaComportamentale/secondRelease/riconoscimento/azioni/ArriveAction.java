package equivalenzaComportamentale.secondRelease.riconoscimento.azioni;

import java.util.List;

import equivalenzaComportamentale.AETinteractionsParts;
import specificheAEmilia.AETinteractions;
import specificheAEmilia.Action;
import specificheAEmilia.ActionRate;
import specificheAEmilia.ActionType;
import specificheAEmilia.Rate_;

public class ArriveAction 
	{
	
	private Action action;
	
	private AETinteractions tinteractions;
	
	private boolean isArrive;

	public ArriveAction(Action action, AETinteractions tinteractions) 
		{
		super();
		this.action = action;
		this.tinteractions = tinteractions;
		
		isArrive = true;
		// Un'azione di arrivo è 
		// definita da una uni o or interazione di input
		ActionType actionType = this.action.getType();
		String string = actionType.getName();
		AETinteractionsParts tinteractionsParts =
			new AETinteractionsParts(this.tinteractions);
		List<String> list = tinteractionsParts.getOrInput();
		List<String> list2 = tinteractionsParts.getUniInput();
		if (!(list.contains(string) || list2.contains(string)))
			isArrive = isArrive && false;
		// l'azione di arrive è passiva
		ActionRate actionRate = this.action.getRate();
		if (!(actionRate instanceof Rate_))
			isArrive = isArrive && false;
		isArrive = isArrive && true;
		}
	
	public boolean isArrive()
		{
		return isArrive;
		}
	
	}
